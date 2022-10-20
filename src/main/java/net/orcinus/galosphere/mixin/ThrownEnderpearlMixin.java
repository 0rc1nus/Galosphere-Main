package net.orcinus.galosphere.mixin;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GCriteriaTriggers;
import net.orcinus.galosphere.util.DistanceComparator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;isConnected()Z", shift = At.Shift.AFTER), method = "onHit", cancellable = true)
    private void G$onHit(HitResult hitResult, CallbackInfo ci) {
        ThrownEnderpearl $this = (ThrownEnderpearl) (Object) this;
        List<BlockPos> poses = Lists.newArrayList();
        ServerPlayer player = (ServerPlayer) $this.getOwner();
        Level world = player.getLevel();
        int radius = 16;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {
                    BlockPos blockPos = new BlockPos($this.getX() + x, $this.getY() + y, $this.getZ() + z);
                    BlockState blockState = world.getBlockState(blockPos);
                    if (blockState.is(GBlocks.WARPED_ANCHOR) && blockState.getValue(WarpedAnchorBlock.WARPED_CHARGE) > 0) {
                        poses.add(blockPos);
                    }
                }
            }
        }
        if (!poses.isEmpty()) {
            poses.sort(new DistanceComparator($this.blockPosition()));
            for (BlockPos blockPos : poses) {
                ci.cancel();
                GCriteriaTriggers.WARPED_TELEPORT.trigger(player);
                $this.level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                $this.level.playSound(null, blockPos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.teleportTo(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D);
                player.resetFallDistance();
                $this.level.setBlock(blockPos, $this.level.getBlockState(blockPos).setValue(WarpedAnchorBlock.WARPED_CHARGE, $this.level.getBlockState(blockPos).getValue(WarpedAnchorBlock.WARPED_CHARGE) - 1), 2);
                $this.discard();
                break;
            }
        }
    }

}
