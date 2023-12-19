package net.orcinus.galosphere.entities;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.init.GEnchantments;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class PinkSaltPillar extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(PinkSaltPillar.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.defineId(PinkSaltPillar.class, EntityDataSerializers.BOOLEAN);
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks = 22;
    public static final float DEFAULT_DAMAGE = 3.0F;
    private float damage = DEFAULT_DAMAGE;
    public AnimationState emergeAnimationState = new AnimationState();
    public AnimationState retractAnimationState = new AnimationState();

    public PinkSaltPillar(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public PinkSaltPillar(Level level, double d, double e, double f, float g, int warmupDelayTicks, LivingEntity livingEntity) {
        this(GEntityTypes.PINK_SALT_PILLAR, level);
        this.warmupDelayTicks = warmupDelayTicks;
        this.setOwner(livingEntity);
        this.setYRot(g * 57.295776f);
        this.setPos(d, e, f);
    }

    public PinkSaltPillar(Level level, double d, double e, double f, float g, int warmupDelayTicks, float damage, LivingEntity livingEntity, ItemStack itemStack) {
        this(GEntityTypes.PINK_SALT_PILLAR, level);
        this.warmupDelayTicks = warmupDelayTicks;
        this.damage = damage;
        this.setOwner(livingEntity);
        this.setYRot(g * 57.295776f);
        this.setPos(d, e, f);
        this.entityData.set(ITEM, itemStack.copy());
        this.lifeTicks = 22 * ((EnchantmentHelper.getItemEnchantmentLevel(GEnchantments.SUSTAIN, itemStack) / 2) + 1);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ACTIVE, false);
        this.entityData.define(ITEM, ItemStack.EMPTY);
    }

    public void setOwner(@Nullable LivingEntity livingEntity) {
        this.owner = livingEntity;
        this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        Entity entity;
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
        }
        return this.owner;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.warmupDelayTicks = compoundTag.getInt("Warmup");
        this.lifeTicks = compoundTag.getInt("LifeTicks");
        this.damage = compoundTag.getFloat("Damage");
        this.setActive(compoundTag.getBoolean("Active"));
        ItemStack itemStack = ItemStack.of(compoundTag.getCompound("TabletItem"));
        if (!itemStack.isEmpty()) {
            this.entityData.set(ITEM, itemStack);
        }
        if (compoundTag.hasUUID("Owner")) {
            this.ownerUUID = compoundTag.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("Warmup", this.warmupDelayTicks);
        compoundTag.putInt("LifeTicks", this.lifeTicks);
        compoundTag.putFloat("Damage", this.damage);
        compoundTag.putBoolean("Active", this.isActive());
        ItemStack itemStack = this.entityData.get(ITEM);
        if (!itemStack.isEmpty()) {
            compoundTag.put("TabletItem", itemStack.save(new CompoundTag()));
        }
        if (this.ownerUUID != null) {
            compoundTag.putUUID("Owner", this.ownerUUID);
        }
    }

    private void setActive(boolean active) {
        this.entityData.set(ACTIVE, active);
    }

    public boolean isActive() {
        return this.entityData.get(ACTIVE);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            List<Arrow> arrows = this.level().getEntitiesOfClass(Arrow.class, this.getBoundingBox().inflate(1.2D));
            for (Arrow arrow : arrows) {
                Vec3 selfPos = this.position().add(0, 1.6f, 0);
                Vec3 enemyPos = arrow.getEyePosition().subtract(selfPos);
                Vec3 normalizedDirection = enemyPos.normalize();
                double knockbackX = 0.15;
                double knockbackY = 0.15;
                arrow.setDeltaMovement(arrow.getDeltaMovement().add(normalizedDirection.x() * knockbackY, normalizedDirection.y() * knockbackX, normalizedDirection.z() * knockbackY));
            }
            if (--this.warmupDelayTicks < 0) {
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2));

                for (LivingEntity livingEntity : list) {
                    if (getOwner() instanceof Berserker && livingEntity instanceof Preserved) continue;
                    this.dealDamageTo(livingEntity);
                }
                if (!this.sentSpikeEvent) {
                    this.setActive(true);
                    this.level().broadcastEntityEvent(this, (byte)4);
                    this.sentSpikeEvent = true;
                }
                ItemStack stack = this.entityData.get(ITEM);
                int ruptureLevel = EnchantmentHelper.getItemEnchantmentLevel(GEnchantments.RUPTURE, stack);
                boolean fracture = ruptureLevel > 0;
                if (!fracture) {
                    if (this.lifeTicks == 4) {
                        this.level().broadcastEntityEvent(this, (byte) 6);
                    }
                    if (--this.lifeTicks < 0) {
                        this.discard();
                    }
                } else {
                    if (--this.lifeTicks < 0) {
                        for (int i = 0; i < ruptureLevel + 2; i++) {
                            PinkSaltShard pinkSaltShard = new PinkSaltShard(this.getOwner(), this.level());
                            pinkSaltShard.moveTo(Vec3.atCenterOf(this.blockPosition()));
                            Vec3 vec3 = new Vec3(this.random.nextGaussian(), this.random.nextGaussian(), this.random.nextGaussian()).normalize();
                            Vec3 vec31 = vec3.scale(0.75D);
                            pinkSaltShard.setDeltaMovement(vec31);
                            this.level().addFreshEntity(pinkSaltShard);
                        }
                        this.discard();
                    }
                }
            }

            if (this.warmupDelayTicks == 0 && level() instanceof ServerLevel server) {
                Vec3 pos = position();
                server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, server.getBlockState(getOnPos())), pos.x, pos.y, pos.z, 12, 0.4, 0, 0.4, 0.1);
            }
        }
    }

    private void dealDamageTo(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = this.getOwner();
        if (livingEntity instanceof Player player && livingEntity2 != null && player.getUUID().equals(livingEntity2.getUUID())) {
            return;
        } else if (!livingEntity.isAlive() || livingEntity.isInvulnerable() || livingEntity == livingEntity2) {
            return;
        }
        if (livingEntity2 == null) {
            livingEntity.hurt(this.damageSources().magic(), this.damage);
        } else {
            if (livingEntity2.isAlliedTo(livingEntity)) return;
            livingEntity.hurt(this.damageSources().indirectMagic(this, livingEntity2), this.damage);
            ItemStack stack = this.entityData.get(ITEM);
            boolean slowness = EnchantmentHelper.getItemEnchantmentLevel(GEnchantments.ENFEEBLE, stack) > 0;
            if (slowness) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));
            }
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getEntity() instanceof Arrow) return false;
        return super.hurt(damageSource, f);
    }

    @Override
    public void handleEntityEvent(byte b) {
        super.handleEntityEvent(b);
        if (b == 4) {
            this.emergeAnimationState.start(this.tickCount);
            if (!this.isSilent()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), GSoundEvents.PINK_SALT_PILLAR_EMERGE, this.getSoundSource(), 1.0f, this.random.nextFloat() * 0.2f + 0.85f, false);
            }
        } else if (b == 6) {
            this.retractAnimationState.start(this.tickCount);
        }
    }
}
