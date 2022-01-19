package net.orcinus.cavesandtrenches.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTEntityTypes;
import net.orcinus.cavesandtrenches.init.CTItems;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;

public class SparkleEntity extends Animal {
    private static final EntityDataAccessor<Integer> CRYSTAL_TYPE = SynchedEntityData.defineId(SparkleEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SWING_TICKS = SynchedEntityData.defineId(SparkleEntity.class, EntityDataSerializers.INT);
    private boolean hasCrystal;
    private int growthTicks;

    public SparkleEntity(EntityType<? extends SparkleEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CRYSTAL_TYPE, 0);
        this.entityData.define(SWING_TICKS, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SwingTicks", this.getSwingTicks());
        tag.putInt("CrystalType", this.getCrystalType().getId());
        tag.putInt("GrowthTicks", this.getGrowthTicks());
        tag.putBoolean("HasCrystal", this.hasCrystal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSwingTicks(tag.getInt("SwingTicks"));
        this.setCrystalType(Type.BY_ID[tag.getInt("CrystalType")]);
        this.setGrowthTicks(tag.getInt("GrowthTicks"));
        this.setHasCrystal(tag.getBoolean("HasCrystal"));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return true;
    }

    public boolean hasCrystal() {
        return this.hasCrystal;
    }

    public void setHasCrystal(boolean crystal) {
        this.hasCrystal = crystal;
    }

    public void setGrowthTicks(int growthTicks) {
        this.growthTicks = growthTicks;
    }

    public int getGrowthTicks() {
        return this.growthTicks;
    }

    public int getSwingTicks() {
        return this.entityData.get(SWING_TICKS);
    }

    public void setSwingTicks(int swingTicks) {
        this.entityData.set(SWING_TICKS, swingTicks);
    }

    public SparkleEntity.Type getCrystalType() {
        return SparkleEntity.Type.BY_ID[this.entityData.get(CRYSTAL_TYPE)];
    }

    public void setCrystalType(SparkleEntity.Type type) {
        this.entityData.set(CRYSTAL_TYPE, type.getId());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(CTBlocks.MYSTERIA_VINES.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof PickaxeItem && this.hasCrystal()) {
            if (!this.level.isClientSide()) {
                this.level.playSound(null, this, SoundEvents.DEEPSLATE_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.gameEvent(GameEvent.SHEAR, player);
                this.spawnAtLocation(this.getCrystalType().getShard());
                stack.hurtAndBreak(1, player, (entity) -> {
                    entity.broadcastBreakEvent(hand);
                });
                this.setGrowthTicks(-24000);
                this.setHasCrystal(false);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }
        else if (!this.hasCrystal() && stack.is(CTBlocks.MYSTERIA_CINDERS.get().asItem())) {
            if (random.nextInt(5) == 0) {
                this.setHasCrystal(true);
                if (this.hasCrystal()) {
                    Type crystalType = Type.BY_ID[random.nextInt(Type.BY_ID.length)];
                    this.setCrystalType(crystalType);
                }
            }
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            this.playSound(SoundEvents.BONE_MEAL_USE, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.hasCrystal && this.getGrowthTicks() < 0) {
            this.setGrowthTicks(this.getGrowthTicks() + 1);
        }
        if (!this.hasCrystal() && this.getGrowthTicks() == 0) {
            this.setHasCrystal(true);
            this.setGrowthTicks(0);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(CTBlocks.MYSTERIA_VINES.get().asItem());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob mob) {
        return CTEntityTypes.SPARKLE.get().create(world);
    }

    public static enum Type {
        ALLURITE(0, "allurite", CTItems.ALLURITE_SHARD.get()),
        LUMIERE(1, "lumiere", CTItems.LUMIERE_SHARD.get());
        public static final SparkleEntity.Type[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(SparkleEntity.Type::getId)).toArray(Type[]::new);
        private final int id;
        private final String name;
        private final Item shard;

        Type(int id, String name, Item shard) {
            this.id = id;
            this.name = name;
            this.shard = shard;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Item getShard() {
            return this.shard;
        }
    }
}
