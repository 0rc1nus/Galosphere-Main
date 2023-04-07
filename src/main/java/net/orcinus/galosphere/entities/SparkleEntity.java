package net.orcinus.galosphere.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.entities.ai.SparkleAi;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItemTags;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GSensorTypes;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public class SparkleEntity extends Animal {
    private static final EntityDataAccessor<Integer> CRYSTAL_TYPE = SynchedEntityData.defineId(SparkleEntity.class, EntityDataSerializers.INT);
    protected static final ImmutableList<SensorType<? extends Sensor<? super SparkleEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, GSensorTypes.SPARKLE_TEMPTATIONS.get(), GSensorTypes.NEAREST_POLLINATED_CLUSTER.get(), SensorType.IS_IN_WATER);
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.BREED_TARGET, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, GMemoryModuleTypes.POLLINATED_COOLDOWN.get(), MemoryModuleType.IS_TEMPTED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.IS_IN_WATER, MemoryModuleType.IS_PANICKING);
    private static final UniformInt REGROWTH_TICKS = UniformInt.of(6000, 12000);
    private int growthTicks;

    public SparkleEntity(EntityType<? extends SparkleEntity> type, Level world) {
        super(type, world);
        this.setPathfindingMalus(BlockPathTypes.WATER, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.TRAPDOOR, -1.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
    }

    @Override
    protected Brain.Provider<SparkleEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return SparkleAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<SparkleEntity> getBrain() {
        return (Brain<SparkleEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.level.getProfiler().push("sparkleBrain");
        this.getBrain().tick((ServerLevel)this.level, this);
        this.level.getProfiler().pop();
        this.level.getProfiler().push("sparkleActivityUpdate");
        SparkleAi.updateActivity(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    @Override
    public float getStepHeight() {
        return 1.0F;
    }

    @Override
    public void travel(Vec3 deltaMovement) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), deltaMovement);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(deltaMovement);
        }
    }

    @Override
    public boolean canCutCorner(BlockPathTypes pathTypes) {
        return super.canCutCorner(pathTypes) && pathTypes != BlockPathTypes.WATER_BORDER;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 1.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CRYSTAL_TYPE, 0);
    }

    public static boolean checkSparkleSpawnRules(EntityType<? extends LivingEntity> sparkle, LevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return world.getBlockState(pos.below()).is(GBlockTags.SPARKLES_SPAWNABLE_ON);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        CrystalType type = this.getRandomType();
        this.setCrystalType(type);
        return super.finalizeSpawn(world, difficulty, spawnReason, data, tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setCrystalType(CrystalType.BY_ID[tag.getInt("CrystalType")]);
        this.setGrowthTicks(tag.getInt("GrowthTicks"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("CrystalType", this.getCrystaltype().getId());
        tag.putInt("GrowthTicks", this.getGrowthTicks());
    }

    public void setGrowthTicks(int growthTicks) {
        this.growthTicks = growthTicks;
    }

    public int getGrowthTicks() {
        return this.growthTicks;
    }

    public void setCrystalType(CrystalType type) {
        this.entityData.set(CRYSTAL_TYPE, type.getId());
    }

    public CrystalType getCrystaltype() {
        return CrystalType.BY_ID[this.entityData.get(CRYSTAL_TYPE)];
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new AmphibiousPathNavigation(this, world);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(GItemTags.SPARKLE_TEMPT_ITEMS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob mob) {
        SparkleEntity sparkleEntity = GEntityTypes.SPARKLE.get().create(world);
        if (sparkleEntity != null) {
            sparkleEntity.setCrystalType(CrystalType.NONE);
        }
        return sparkleEntity;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide()) {
            if (this.getGrowthTicks() > 0) {
                this.setGrowthTicks(this.getGrowthTicks() - 1);
            }
            if (this.getGrowthTicks() == 0 && this.getCrystaltype() == CrystalType.NONE) {
                CrystalType type = this.getRandomType();
                this.setCrystalType(type);
            }
        }
    }

    public CrystalType getRandomType() {
        return random.nextBoolean() ? CrystalType.ALLURITE : CrystalType.LUMIERE;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.getCrystaltype() != CrystalType.NONE && stack.getItem() instanceof PickaxeItem && !this.isBaby()) {
            this.extractShard(stack);
            stack.hurtAndBreak(1, player, (entity) -> {
                entity.broadcastBreakEvent(hand);
            });
            this.gameEvent(GameEvent.SHEAR, player);
            return InteractionResult.SUCCESS;
        }
        else if (this.getCrystaltype() == CrystalType.NONE && stack.is(GItemTags.SPARKLE_TEMPT_ITEMS)) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            this.setGrowthTicks(this.getGrowthTicks() - Mth.nextInt(random, 20, 40));
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    public void extractShard(ItemStack stack) {
        this.spawnShard(stack);
        this.playSound(SoundEvents.CALCITE_HIT, 1.0F, 1.0F);
        this.setCrystalType(CrystalType.NONE);
        this.setGrowthTicks(REGROWTH_TICKS.sample(this.getRandom()));
    }

    private void spawnShard(ItemStack stack) {
        Item item = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0 ? this.getCrystaltype().getSilktouchItem() : this.getCrystaltype().getItem();
        int rolls = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack) > 0 ? 1 + EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack) : 1;
        for (int i = 0; i < rolls; i++) {
            this.spawnAtLocation(item);
        }
    }

    public enum CrystalType {
        NONE(0, "none", null, null),
        ALLURITE(1, "allurite", GItems.ALLURITE_SHARD.get(), GBlocks.ALLURITE_CLUSTER.get().asItem()),
        LUMIERE(2, "lumiere", GItems.LUMIERE_SHARD.get(), GBlocks.LUMIERE_CLUSTER.get().asItem());

        public static final CrystalType[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(CrystalType::getId)).toArray(CrystalType[]::new);
        private final int id;
        private final String name;
        private final Item item;
        private final Item silktouchItem;

        CrystalType(int id, String name, Item item, Item silktouchItem) {
            this.id = id;
            this.name = name;
            this.item = item;
            this.silktouchItem = silktouchItem;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public Item getItem() {
            return this.item;
        }

        public Item getSilktouchItem() {
            return this.silktouchItem;
        }
    }

}