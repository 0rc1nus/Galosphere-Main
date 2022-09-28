package net.orcinus.galosphere.entities;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
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
import net.orcinus.galosphere.entities.ai.FlyWanderGoal;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GParticleTypes;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class FayEntity extends PathfinderMob implements FlyingAnimal {
    private static final EntityDataAccessor<Optional<UUID>> MANIPULATOR = SynchedEntityData.defineId(FayEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> CAN_BE_MANIPULATED = SynchedEntityData.defineId(FayEntity.class, EntityDataSerializers.BOOLEAN);

    public FayEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MANIPULATOR, Optional.empty());
        this.entityData.define(CAN_BE_MANIPULATED, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        UUID uuid;
        if (tag.hasUUID("Owner")) {
            uuid = tag.getUUID("Owner");
        } else {
            String s = tag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(this.getServer()), s);
        }
        if (uuid != null) {
            this.setManipulatorUUID(uuid);
        }
        this.setCanBeManipulated(tag.getBoolean("CanBeManipulated"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.getManipulatorUUID() != null) {
            tag.putUUID("Manipulator", this.getManipulatorUUID());
        }
        tag.putBoolean("CanBeManipulated", this.canBeManipulated());
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
                this.spawnFluidParticle(this.level, this.getX() - (double)0.3F, this.getX() + (double)0.3F, this.getZ() - (double)0.3F, this.getZ() + (double)0.3F, this.getY(0.5D), GParticleTypes.ALLURITE_RAIN.get());
            }
        }
    }

    private void spawnFluidParticle(Level world, double p_27781_, double p_27782_, double p_27783_, double p_27784_, double p_27785_, ParticleOptions options) {
        world.addParticle(options, Mth.lerp(world.random.nextDouble(), p_27781_, p_27782_), p_27785_, Mth.lerp(world.random.nextDouble(), p_27783_, p_27784_), 0.0D, 0.0D, 0.0D);
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
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (this.level.isClientSide() || (localPlayer != null && localPlayer.getUUID() == this.getManipulatorUUID())) {
            Optional<UUID> manipulatorUUID = this.entityData.get(MANIPULATOR);
            Optional<Player> optional = manipulatorUUID.map(this.level::getPlayerByUUID);
            optional.ifPresent(player -> {
                if (!player.isScoping()) {
                    if (this.level.isClientSide()) {
                        Minecraft minecraft = Minecraft.getInstance();
                        if (minecraft.player == player) {
                            minecraft.setCameraEntity(player);
                        }
                    } else {
                        this.setManipulatorUUID(null);
                        this.playSound(SoundEvents.ALLAY_ITEM_TAKEN, 1.0F, 1.0F);
                    }
                }
            });
            if (!this.level.isClientSide() && manipulatorUUID.isPresent()) {
                if (optional.isEmpty()) {
                    this.entityData.set(MANIPULATOR, Optional.empty());
                }
            }
        }
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
        if (stack.is(Items.GLASS_BOTTLE)) {
            this.level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0f, 1.0f);
            if (!this.level.isClientSide()) {
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                ItemStack itemStack2 = new ItemStack(GItems.BOTTLE_OF_FAY.get());
                CompoundTag compoundTag = itemStack2.getOrCreateTag();
                if (this.hasCustomName()) {
                    itemStack2.setHoverName(this.getCustomName());
                }
                if (this.isNoAi()) {
                    compoundTag.putBoolean("NoAI", this.isNoAi());
                }
                if (this.isSilent()) {
                    compoundTag.putBoolean("Silent", this.isSilent());
                }
                if (this.isNoGravity()) {
                    compoundTag.putBoolean("NoGravity", this.isNoGravity());
                }
                if (this.hasGlowingTag()) {
                    compoundTag.putBoolean("Glowing", this.hasGlowingTag());
                }
                if (this.isInvulnerable()) {
                    compoundTag.putBoolean("Invulnerable", this.isInvulnerable());
                }
                compoundTag.putFloat("Health", this.getHealth());
                player.setItemInHand(interactionHand, ItemUtils.createFilledResult(stack, player, itemStack2));
                this.discard();
            }
            return InteractionResult.SUCCESS;
        }
        else if (stack.is(GItems.ALLURITE_SHARD.get()) && this.getManipulatorUUID() == null && !this.canBeManipulated()) {
            this.setCanBeManipulated(true);
            this.playSound(SoundEvents.ALLAY_ITEM_GIVEN, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, interactionHand);
    }

    public void setCamera(Player player) {
        if (this.level.isClientSide()) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player == player) {
                minecraft.options.setCameraType(CameraType.FIRST_PERSON);
                minecraft.setCameraEntity(this);
            }
        } else {
            this.setManipulatorUUID(player.getUUID());
            this.playSound(SoundEvents.ALLAY_ITEM_GIVEN, 1.0F, 1.0F);
        }
    }

    @Override
    public boolean isFlying() {
        return !this.onGround;
    }

}
