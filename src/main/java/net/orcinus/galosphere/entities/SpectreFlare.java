package net.orcinus.galosphere.entities;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GNetwork;
import net.orcinus.galosphere.init.GSoundEvents;
import net.orcinus.galosphere.mixin.access.FireworkRocketEntityAccessor;
import org.jetbrains.annotations.Nullable;

public class SpectreFlare extends ThrowableLaunchedProjectile {

    public SpectreFlare(EntityType<? extends SpectreFlare> type, Level world) {
        super(type, world);
    }

    public SpectreFlare(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        super(world, stack, entity, x, y, z, shotAtAngle);
    }

    public SpectreFlare(Level level, @Nullable Entity entity, ItemStack itemStack) {
        super(GEntityTypes.SPECTRE_FLARE, level);
        if (!itemStack.isEmpty() && itemStack.hasTag()) {
            this.entityData.set(FireworkRocketEntityAccessor.getDATA_ID_FIREWORKS_ITEM(), itemStack.copy());
        }
        this.entityData.set(THROWN, true);
        this.setOwner(entity);
    }

    @Override
    public void handleLaunchedProjectile() {
        Level world = this.level();
        if (!world.isClientSide && ((FireworkRocketEntityAccessor)this).getLife() > ((FireworkRocketEntityAccessor)this).getLifetime()) {
            this.spawnSpectatorVision(this.position());
            world.broadcastEntityEvent(this, (byte)17);
            this.gameEvent(GameEvent.EXPLODE, this.getOwner());
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return GItems.SPECTRE_FLARE;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        Level world = this.level();
        if (!world.isClientSide()) {
            BlockPos hitPos = result.getBlockPos();
            BlockPos placePos = hitPos.relative(result.getDirection());
            if (world.getBlockState(hitPos).isCollisionShapeFullBlock(world, hitPos) && (world.getFluidState(placePos).is(FluidTags.LAVA) || world.isStateAtPosition(placePos, DripstoneUtils::isEmptyOrWater))) {
                this.spawnSpectatorVision(Vec3.atCenterOf(placePos));
            }
            this.discard();
        }
    }

    private void spawnSpectatorVision(Vec3 vec3) {
        if (this.getOwner() instanceof ServerPlayer serverPlayer) {
            if (!((SpectreBoundSpyglass)serverPlayer).isUsingSpectreBoundedSpyglass()) {
                Level world = this.level();
                SpectatorVision spectatorVision = SpectatorVision.create(world, vec3, serverPlayer, 120);
                serverPlayer.playNotifySound(GSoundEvents.SPECTRE_MANIPULATE_BEGIN, getSoundSource(), 1, 1);
                world.addFreshEntity(spectatorVision);
                ((SpectreBoundSpyglass)serverPlayer).setUsingSpectreBoundedSpyglass(true);
                FriendlyByteBuf buf = PacketByteBufs.create();
                buf.writeUUID(serverPlayer.getUUID());
                buf.writeInt(spectatorVision.getId());
                ServerPlayNetworking.send(serverPlayer, GNetwork.SEND_PERSPECTIVE, buf);
            }
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(GItems.SPECTRE_FLARE);
    }

}
