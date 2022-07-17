package net.orcinus.galosphere.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GCriteriaTriggers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin {

    @Inject(at = @At("HEAD"), method = "onHit")
    private void G$onHit(HitResult hitResult, CallbackInfo ci) {
        ThrownEnderpearl $this = (ThrownEnderpearl) (Object) this;
        if (!$this.level.isClientSide() && !$this.isRemoved()) {
            Entity entity = $this.getOwner();
            if (entity instanceof ServerPlayer serverPlayer){
                BlockPos pos = new BlockPos(new Vec3($this.getX(), $this.getY(), $this.getZ()));
                Level world = serverPlayer.getLevel();
                int radius = 16;
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        for (int y = -radius; y <= radius; y++) {
                            BlockPos blockPos = new BlockPos($this.getX() + x, $this.getY() + y, $this.getZ() + z);
                            BlockState blockState = world.getBlockState(blockPos);
                            if (pos.closerThan(blockPos, 12) && blockState.is(GBlocks.WARPED_ANCHOR) && blockState.getValue(WarpedAnchorBlock.WARPED_CHARGE) > 0) {
                                GCriteriaTriggers.WARPED_TELEPORT.trigger(serverPlayer);
                                world.gameEvent(serverPlayer, GameEvent.BLOCK_CHANGE, blockPos);
                                world.setBlockAndUpdate(blockPos, blockState.setValue(WarpedAnchorBlock.WARPED_CHARGE, blockState.getValue(WarpedAnchorBlock.WARPED_CHARGE) - 1));
                                world.playSound(null, blockPos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
                                if (serverPlayer.connection.getConnection().isConnected() && serverPlayer.level == $this.level && !serverPlayer.isSleeping()) {
                                    if ($this.level.getRandom().nextFloat() < 0.05F && $this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                                        Endermite endermite = EntityType.ENDERMITE.create($this.level);
                                        endermite.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                                        $this.level.addFreshEntity(endermite);
                                    }

                                    if (entity.isPassenger()) {
                                        serverPlayer.dismountTo(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D);
                                    } else {
                                        entity.teleportTo(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D);
                                    }

                                    entity.resetFallDistance();
                                    entity.hurt(DamageSource.FALL, 5.0F);
                                    $this.discard();
                                }

                            }
                        }
                    }
                }
            }
        }
    }

}
