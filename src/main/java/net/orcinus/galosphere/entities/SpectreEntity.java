package net.orcinus.galosphere.entities;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.api.BottlePickable;
import net.orcinus.galosphere.api.SpectreBoundedSpyglass;
import net.orcinus.galosphere.entities.ai.FlyWanderGoal;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GNetwork;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.init.GSoundEvents;

public class SpectreEntity extends PathfinderMob implements FlyingAnimal, BottlePickable {
    private static final EntityDataAccessor<Optional<UUID>> MANIPULATOR = SynchedEntityData.defineId(SpectreEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> CAN_BE_MANIPULATED = SynchedEntityData.defineId(SpectreEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(SpectreEntity.class, EntityDataSerializers.BOOLEAN);

    public SpectreEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MANIPULATOR, Optional.empty());
        this.entityData.define(CAN_BE_MANIPULATED, false);
        this.entityData.define(FROM_BOTTLE, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        UUID uuid;
        if (tag.hasUUID("Manipulator")) {
            uuid = tag.getUUID("Manipulator");
        } else {
            String s = tag.getString("Manipulator");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(this.getServer()), s);
        }
        if (uuid != null) {
            this.setManipulatorUUID(uuid);
        }
        this.setFromBottle(tag.getBoolean("FromBottle"));
        this.setCanBeManipulated(tag.getBoolean("CanBeManipulated"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.getManipulatorUUID() != null) {
            tag.putUUID("Manipulator", this.getManipulatorUUID());
        }
        tag.putBoolean("FromBottle", this.fromBottle());
        tag.putBoolean("CanBeManipulated", this.canBeManipulated());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return GSoundEvents.SPECTRE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return GSoundEvents.SPECTRE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return GSoundEvents.SPECTRE_DEATH;
    }

    @Nullable
    public UUID getManipulatorUUID() {
        return this.entityData.get(MANIPULATOR).orElse(null);
    }

    public void setManipulatorUUID(@Nullable UUID uuid) {
        this.entityData.set(MANIPULATOR, Optional.ofNullable(uuid));
    }

    public boolean canBeManipulated() {
        return this.entityData.get(CAN_BE_MANIPULATED);
    }

    public void setCanBeManipulated(boolean canBeManipulated) {
        this.entityData.set(CAN_BE_MANIPULATED, canBeManipulated);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(true);
        flyingPathNavigation.setCanPassDoors(true);
        return flyingPathNavigation;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.canBeManipulated() && this.random.nextFloat() < 0.05F) {
            for(int i = 0; i < this.random.nextInt(2) + 1; ++i) {
                this.spawnFluidParticle(this.level, this.getX() - (double)0.3F, this.getX() + (double)0.3F, this.getZ() - (double)0.3F, this.getZ() + (double)0.3F, this.getY(0.5D), GParticleTypes.ALLURITE_RAIN);
            }
        }
    }

    private void spawnFluidParticle(Level world, double minX, double maxX, double minZ, double maxZ, double y, ParticleOptions options) {
        world.addParticle(options, Mth.lerp(world.random.nextDouble(), minX, maxX), y, Mth.lerp(world.random.nextDouble(), minZ, maxZ), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
    }

    @Override
    protected void checkFallDamage(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
    }

    @Override
    protected boolean isFlapping() {
        return this.isFlying();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FlyWanderGoal(this));
        this.goalSelector.addGoal(2, new FloatGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0).add(Attributes.FLYING_SPEED, 0.6f).add(Attributes.MOVEMENT_SPEED, 0.3f).add(Attributes.ATTACK_DAMAGE, 2.0).add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide() || this.matchesClientPlayerUUID()) {
            Optional<UUID> optionalUUID = this.entityData.get(MANIPULATOR);
            optionalUUID.ifPresent(this::manualControl);
        }
    }

    private void manualControl(UUID uuid) {
        Player player = this.level.getPlayerByUUID(uuid);
        if (player != null) {
            player.xxa = 0;
            player.zza = 0;
            player.setJumping(false);
            if (!player.isScoping() || this.isDeadOrDying()) {
                this.setManipulatorUUID(null);
                if (this.level.isClientSide) {
                    this.stopUsingSpyglass(player);
                } else {
                    ((SpectreBoundedSpyglass)player).setUsingSpectreBoundedSpyglass(false);
                    player.playNotifySound(GSoundEvents.SPECTRE_MANIPULATE_END, getSoundSource(), 1, 1);
                }
            }
        }
        if (!this.level.isClientSide() && player == null) {
            this.entityData.set(MANIPULATOR, Optional.empty());
        }
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName() && !this.fromBottle();
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBottle();
    }

    @Environment(EnvType.CLIENT)
    public void stopUsingSpyglass(Player player) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == player) {
            client.setCameraEntity(player);
        }
    }

    @Environment(EnvType.CLIENT)
    public boolean matchesClientPlayerUUID() {
        final Minecraft client = Minecraft.getInstance();
        return client.player != null && Minecraft.getInstance().player.getUUID().equals(this.getManipulatorUUID());
    }

    @Override
    public void travel(Vec3 velocity) {
        if (this.getManipulatorUUID() != null) {
            Optional<Player> controller = this.entityData.get(MANIPULATOR).map(this.level::getPlayerByUUID);
            controller.ifPresent(player -> {
                this.setYRot(player.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(player.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.getYRot();
            });
        }
        else {
            super.travel(velocity);
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (this.canBeManipulated() && stack.is(Items.SPYGLASS)) {
            this.playSound(GSoundEvents.SPECTRE_LOCK_TO_SPYGLASS, 1, 1);
            ItemStack spectreBoundedSpyglass = new ItemStack(GItems.SPECTRE_BOUNDED_SPYGLASS);
            if (this.hasCustomName()) {
                spectreBoundedSpyglass.setHoverName(this.getCustomName());
            }
            SpectreBoundedSpyglass.addSpectreBoundedTags(this, spectreBoundedSpyglass.getOrCreateTag());
            player.setItemInHand(interactionHand, spectreBoundedSpyglass);
            this.setCanBeManipulated(false);
            return InteractionResult.SUCCESS;
        }
        if (stack.is(Items.GLASS_BOTTLE)) {
            this.level.playSound(player, player.getX(), player.getY(), player.getZ(), GSoundEvents.SPECTRE_BOTTLE_FILL, SoundSource.NEUTRAL, 1.0f, 1.0f);
            if (!this.level.isClientSide()) {
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                ItemStack itemStack2 = new ItemStack(GItems.BOTTLE_OF_SPECTRE);
                BottlePickable.saveDefaultDataFromBottleTag(this, itemStack2);
                player.setItemInHand(interactionHand, ItemUtils.createFilledResult(stack, player, itemStack2));
                this.discard();
            }
            return InteractionResult.SUCCESS;
        }
        else if (stack.is(GItems.ALLURITE_SHARD) && this.getManipulatorUUID() == null && !this.canBeManipulated()) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            this.setCanBeManipulated(true);
            this.playSound(GSoundEvents.SPECTRE_RECEIVE_ITEM, 1, 1);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, interactionHand);
    }

    public void setCamera(Player player) {
        if (!this.level.isClientSide()) {
            player.zza = 0;
            ((SpectreBoundedSpyglass)player).setUsingSpectreBoundedSpyglass(true);
            this.setManipulatorUUID(player.getUUID());
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeUUID(player.getUUID());
            buf.writeInt(this.getId());
            ServerPlayNetworking.send((ServerPlayer) player, GNetwork.SEND_PERSPECTIVE, buf);
            player.playNotifySound(GSoundEvents.SPECTRE_MANIPULATE_BEGIN, getSoundSource(), 1, 1);
        }
    }

    @Override
    public boolean isFlying() {
        return !this.onGround;
    }

    @Override
    public boolean fromBottle() {
        return this.entityData.get(FROM_BOTTLE);
    }

    @Override
    public void setFromBottle(boolean fromBottle) {
        this.entityData.set(FROM_BOTTLE, fromBottle);
    }
}
