package net.orcinus.galosphere.entities;

import net.minecraft.core.BlockPos;
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
import net.minecraftforge.network.PacketDistributor;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GNetworkHandler;
import net.orcinus.galosphere.init.GSoundEvents;
import net.orcinus.galosphere.network.SendPerspectivePacket;
import org.jetbrains.annotations.Nullable;

public class SpectreFlare extends ThrowableLaunchedProjectile {
    public SpectreFlare(EntityType<? extends SpectreFlare> type, Level world) {
        super(type, world);
    }

    public SpectreFlare(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        super(world, stack, entity, x, y, z, shotAtAngle);
    }

    public SpectreFlare(Level level, @Nullable Entity entity, ItemStack itemStack) {
        super(GEntityTypes.SPECTRE_FLARE.get(), level);
        if (!itemStack.isEmpty() && itemStack.hasTag()) {
            this.entityData.set(DATA_ID_FIREWORKS_ITEM, itemStack.copy());
        }
        this.entityData.set(THROWN, true);
        this.setOwner(entity);
    }

    @Override
    public void handleLaunchedProjectile() {
        Level world = this.level();
        if (!world.isClientSide && this.life > this.lifetime) {
            this.spawnSpectatorVision(this.position());
            world.broadcastEntityEvent(this, (byte)17);
            this.gameEvent(GameEvent.EXPLODE, this.getOwner());
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return GItems.SPECTRE_FLARE.get();
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
                SpectatorVision spectatorVision = SpectatorVision.create(this.level(), vec3, serverPlayer, 120);
                serverPlayer.playNotifySound(GSoundEvents.SPECTRE_MANIPULATE_BEGIN.get(), getSoundSource(), 1, 1);
                this.level().addFreshEntity(spectatorVision);
                ((SpectreBoundSpyglass)serverPlayer).setUsingSpectreBoundedSpyglass(true);
                GNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SendPerspectivePacket(serverPlayer.getUUID(), spectatorVision.getId()));
            }
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(GItems.SPECTRE_FLARE.get());
    }

}