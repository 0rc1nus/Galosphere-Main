package net.orcinus.galosphere.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.entities.ai.FindClusterGoal;
import net.orcinus.galosphere.entities.ai.SparkleRandomSwimmingGoal;
import net.orcinus.galosphere.entities.ai.WalkAndSwimGoal;
import net.orcinus.galosphere.entities.ai.WalkToGroundGoal;
import net.orcinus.galosphere.entities.ai.control.SmoothSwimmingGroundControl;
import net.orcinus.galosphere.entities.ai.navigation.SwimWalkPathNavigation;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItemTags;
import net.orcinus.galosphere.init.GItems;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public class SparkleEntity extends Animal {
    public static final EntityDataAccessor<Integer> CRYSTAL_TYPE = SynchedEntityData.defineId(SparkleEntity.class, EntityDataSerializers.INT);
    private boolean groundNavigationInuse;
    private int growthTicks;
    public float prevWaterTicks;
    public float waterTicks;
    private int swimTicks = -1000;
    private int eatingCooldownTicks;
    private static final UniformInt REGROWTH_TICKS = UniformInt.of(6000, 12000);
    private static final UniformInt LONG_REGROWTH_TICKS = UniformInt.of(12000, 24000);

    public SparkleEntity(EntityType<? extends SparkleEntity> type, Level world) {
        super(type, world);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.switchNavigator(false);
        this.maxUpStep = 1.0F;
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level);
            this.groundNavigationInuse = true;
        } else {
            this.moveControl = new SmoothSwimmingGroundControl(this, 1.2F, 1.6F);
            this.navigation = new SwimWalkPathNavigation(this, level);
            this.groundNavigationInuse = false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.prevWaterTicks = this.waterTicks;
        if (this.isInWaterOrBubble() && this.waterTicks < 5.0F) {
            this.waterTicks++;
        }
        if (!this.isInWaterOrBubble() && this.waterTicks > 0.0F) {
            this.waterTicks--;
        }
        if (this.isInWaterOrBubble() && this.groundNavigationInuse) {
            this.switchNavigator(false);
        }
        if (!this.isInWaterOrBubble() && !this.groundNavigationInuse) {
            this.switchNavigator(true);
        }
        if (!level.isClientSide) {
            if (this.eatingCooldownTicks < 0) {
                this.eatingCooldownTicks++;
            }
            if (this.isInWater()) {
                this.swimTicks++;
            } else {
                this.swimTicks--;
            }
        }
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
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CRYSTAL_TYPE, 0);
    }

    public static boolean checkSparkleSpawnRules(EntityType<? extends Entity> sparkle, LevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
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
        this.setEatingCooldownTicks(tag.getInt("CooldownTicks"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("CrystalType", this.getCrystaltype().getId());
        tag.putInt("GrowthTicks", this.getGrowthTicks());
        tag.putInt("CooldownTicks", this.getEatingCooldownTicks());
    }

    public void setEatingCooldownTicks(int growthTicks) {
        this.eatingCooldownTicks = growthTicks;
    }

    public int getEatingCooldownTicks() {
        return this.eatingCooldownTicks;
    }

    public void setGrowthTicks(int growthTicks) {
        this.growthTicks = growthTicks;
    }

    public int getGrowthTicks() {
        return this.growthTicks;
    }

    public void setCrystalType(SparkleEntity.CrystalType type) {
        this.entityData.set(CRYSTAL_TYPE, type.getId());
    }

    public CrystalType getCrystaltype() {
        return CrystalType.BY_ID[this.entityData.get(CRYSTAL_TYPE)];
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new WalkAndSwimGoal(this));
        this.goalSelector.addGoal(2, new WalkToGroundGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, Ingredient.of(GItemTags.SPARKLE_TEMPT_ITEMS), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new FindClusterGoal(this));
        this.goalSelector.addGoal(8, new SparkleRandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(GItemTags.SPARKLE_TEMPT_ITEMS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob mob) {
        SparkleEntity sparkleEntity = GEntityTypes.SPARKLE.create(world);
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
            this.setGrowthTicks(this.getGrowthTicks() - Mth.nextInt(random, 20, 40));
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    public void extractShard(ItemStack stack) {
        this.spawnShard(stack);
        this.playSound(SoundEvents.CALCITE_HIT, 1.0F, 1.0F);
        this.setCrystalType(CrystalType.NONE);
        UniformInt growthTicks = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0 ? LONG_REGROWTH_TICKS : REGROWTH_TICKS;
        this.setGrowthTicks(growthTicks.sample(this.getRandom()));
    }

    private void spawnShard(ItemStack stack) {
        Item item = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0 ? this.getCrystaltype().getSilktouchItem() : this.getCrystaltype().getItem();
        int rolls = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack) > 0 ? 1 + EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack) : 1;
        for (int i = 0; i < rolls; i++) {
            this.spawnAtLocation(item);
        }
    }

    public boolean shouldEnterWater() {
        return swimTicks <= -1000;
    }

    public boolean shouldLeaveWater() {
        return swimTicks > 600;
    }

    public enum CrystalType {
        NONE(0, "none", null, null),
        ALLURITE(1, "allurite", GItems.ALLURITE_SHARD, GBlocks.ALLURITE_CLUSTER.asItem()),
        LUMIERE(2, "lumiere", GItems.LUMIERE_SHARD, GBlocks.LUMIERE_CLUSTER.asItem());

        public static final SparkleEntity.CrystalType[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(SparkleEntity.CrystalType::getId)).toArray(CrystalType[]::new);
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
