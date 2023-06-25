package net.orcinus.galosphere.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.orcinus.galosphere.api.Spectatable;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GNetworkHandler;
import net.orcinus.galosphere.init.GSoundEvents;
import net.orcinus.galosphere.network.SendPerspectivePacket;
import org.jetbrains.annotations.Nullable;

public class SpectreFlare extends FireworkRocketEntity {
    public SpectreFlare(EntityType<? extends SpectreFlare> type, Level world) {
        super(type, world);
    }

    public SpectreFlare(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        super(world, stack, entity, x, y, z, shotAtAngle);
    }

    public SpectreFlare(Level level, @Nullable Entity entity, double d, double e, double f, ItemStack itemStack) {
        this(level, d, e, f, itemStack);
        this.setOwner(entity);
    }

    public SpectreFlare(Level level, double d, double e, double f, ItemStack itemStack) {
        super(GEntityTypes.SPECTRE_FLARE.get(), level);
        this.life = 0;
        this.setPos(d, e, f);
        if (!itemStack.isEmpty() && itemStack.hasTag()) {
            this.entityData.set(DATA_ID_FIREWORKS_ITEM, itemStack.copy());
        }
        this.setDeltaMovement(this.random.triangle(0.0, 0.002297), 0.05, this.random.triangle(0.0, 0.002297));
        this.lifetime = 100;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.life > this.lifetime) {
            this.spawnSpectatorVision(this.position());
            this.level().broadcastEntityEvent(this, (byte)17);
            this.gameEvent(GameEvent.EXPLODE, this.getOwner());
            this.discard();
        }
    }

    @Override
    public EntityType<?> getType() {
        return GEntityTypes.SPECTRE_FLARE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide()) {
            BlockPos hitPos = result.getBlockPos();
            BlockPos placePos = hitPos.relative(result.getDirection());
            if (this.level().getBlockState(hitPos).isCollisionShapeFullBlock(this.level(), hitPos) && (!this.level().getFluidState(placePos).is(FluidTags.LAVA) || this.level().isStateAtPosition(placePos, DripstoneUtils::isEmptyOrWater))) {
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

    @OnlyIn(Dist.CLIENT)
    private boolean isCameraEntitySpectatorVision() {
        return Minecraft.getInstance().getCameraEntity() instanceof Spectatable;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(GItems.SPECTRE_FLARE.get());
    }

}