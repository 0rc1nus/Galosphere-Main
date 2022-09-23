package net.orcinus.galosphere.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.orcinus.galosphere.entities.ai.FlyWanderGoal;
import net.orcinus.galosphere.init.GItems;

public class FayEntity extends PathfinderMob implements FlyingAnimal {

    public FayEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
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
        return super.mobInteract(player, interactionHand);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround;
    }

}
