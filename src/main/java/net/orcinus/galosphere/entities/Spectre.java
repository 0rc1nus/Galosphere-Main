package net.orcinus.galosphere.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.api.BottlePickable;
import net.orcinus.galosphere.api.Spectatable;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.entities.ai.SpectreAi;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItemTags;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GNetwork;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.init.GSensorTypes;
import net.orcinus.galosphere.init.GSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class Spectre extends Animal implements FlyingAnimal, BottlePickable, Spectatable {
    private static final EntityDataAccessor<Optional<UUID>> MANIPULATOR = SynchedEntityData.defineId(Spectre.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> CAN_BE_MANIPULATED = SynchedEntityData.defineId(Spectre.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(Spectre.class, EntityDataSerializers.BOOLEAN);
    protected static final ImmutableList<SensorType<? extends Sensor<? super Spectre>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.IS_IN_WATER, GSensorTypes.SPECTRE_TEMPTATIONS);
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.BREED_TARGET, MemoryModuleType.IS_PREGNANT, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.IS_PANICKING, GMemoryModuleTypes.NEAREST_LICHEN_MOSS);

    public Spectre(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    public float getWalkTargetValue(BlockPos blockPos, LevelReader levelReader) {
        return 0.0F;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(GItemTags.SPECTRE_TEMPT_ITEMS);
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel serverLevel, Animal animal) {
        ServerPlayer serverPlayer = this.getLoveCause();
        if (serverPlayer == null) {
            serverPlayer = animal.getLoveCause();
        }
        if (serverPlayer != null) {
            serverPlayer.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayer, this, animal, null);
        }
        this.setAge(6000);
        animal.setAge(6000);
        this.resetLove();
        animal.resetLove();
        this.getBrain().setMemory(MemoryModuleType.IS_PREGNANT, Unit.INSTANCE);
        serverLevel.broadcastEntityEvent(this, (byte)18);
        if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            serverLevel.addFreshEntity(new ExperienceOrb(serverLevel, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return GEntityTypes.SPECTRE.create(serverLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MANIPULATOR, Optional.empty());
        this.entityData.define(CAN_BE_MANIPULATED, false);
        this.entityData.define(FROM_BOTTLE, false);
    }

    public static boolean checkSpectreSpawnRules(EntityType<? extends LivingEntity> type, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(GBlockTags.SPECTRES_SPAWNABLE_ON);
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return super.getStandingEyeHeight(pose, entityDimensions) - 0.1F;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        UUID uuid;
        if (tag.hasUUID("Manipulator")) {
            uuid = tag.getUUID("Manipulator");
        } else {
            String s = tag.getString("Manipulator");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return GSoundEvents.SPECTRE_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return GSoundEvents.SPECTRE_HURT;
    }

    @Nullable
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

    @Override
    public void spectateTick(UUID uuid) {
        Player player = this.level().getPlayerByUUID(uuid);
        if (player != null) {
            player.xxa = 0.0F;
            player.zza = 0.0F;
            player.setJumping(false);
            if (!player.isScoping() || this.isDeadOrDying()) {
                this.setManipulatorUUID(null);
                if (this.level().isClientSide) {
                    this.stopUsingSpyglass(player);
                } else {
                    ((SpectreBoundSpyglass)player).setUsingSpectreBoundedSpyglass(false);
                    player.playNotifySound(GSoundEvents.SPECTRE_MANIPULATE_END, getSoundSource(), 1, 1);
                }
            }
        }
        if (!this.level().isClientSide() && player == null) {
            this.entityData.set(MANIPULATOR, Optional.empty());
        }
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
                this.spawnFluidParticle(this.level(), this.getX() - (double)0.3F, this.getX() + (double)0.3F, this.getZ() - (double)0.3F, this.getZ() + (double)0.3F, this.getY(0.5D), GParticleTypes.ALLURITE_RAIN);
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
    protected Brain.Provider<Spectre> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return SpectreAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<Spectre> getBrain() {
        return (Brain<Spectre>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("spectreBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("spectreActivityUpdate");
        SpectreAi.updateActivity(this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0).add(Attributes.FLYING_SPEED, 0.6f).add(Attributes.MOVEMENT_SPEED, 0.3f).add(Attributes.ATTACK_DAMAGE, 2.0).add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide() || this.matchesClientPlayerUUID()) {
            this.entityData.get(MANIPULATOR).ifPresent(this::spectateTick);
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

    @Override
    public void travel(Vec3 velocity) {
        if (this.getManipulatorUUID() != null) {
            this.entityData.get(MANIPULATOR).map(this.level()::getPlayerByUUID).ifPresent(uuid -> this.copyPlayerRotation(this, uuid));
        } else {
            super.travel(velocity);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (this.canBeManipulated() && (stack.is(GItems.SPECTRE_BOUND_SPYGLASS) || stack.is(Items.SPYGLASS))) {
            this.playSound(GSoundEvents.SPECTRE_LOCK_TO_SPYGLASS, 1, 1);
            ItemStack spectreBoundedSpyglass = new ItemStack(GItems.SPECTRE_BOUND_SPYGLASS);
            if (this.hasCustomName()) {
                spectreBoundedSpyglass.setHoverName(this.getCustomName());
            }
            SpectreBoundSpyglass.addSpectreBoundedTags(this, spectreBoundedSpyglass.getOrCreateTag());
            player.setItemInHand(interactionHand, spectreBoundedSpyglass);
            this.setCanBeManipulated(false);
            return InteractionResult.SUCCESS;
        }
        else if (stack.is(Items.GLASS_BOTTLE)) {
            this.level().playSound(player, player.getX(), player.getY(), player.getZ(), GSoundEvents.SPECTRE_BOTTLE_FILL, SoundSource.NEUTRAL, 1.0f, 1.0f);
            if (!this.level().isClientSide()) {
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                ItemStack itemStack2 = new ItemStack(GItems.BOTTLE_OF_SPECTRE);
                CompoundTag compoundTag = new CompoundTag();
                this.save(compoundTag);
                itemStack2.setTag(compoundTag);
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
        if (!this.level().isClientSide()) {
            ((SpectreBoundSpyglass)player).setUsingSpectreBoundedSpyglass(true);
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
        return !this.onGround();
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