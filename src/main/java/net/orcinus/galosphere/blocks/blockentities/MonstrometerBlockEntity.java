package net.orcinus.galosphere.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.orcinus.galosphere.blocks.MonstrometerBlock;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GParticleTypes;

import java.util.List;
import java.util.function.Predicate;

public class MonstrometerBlockEntity extends BlockEntity {
    private int monsterCount;
    public int activeTicks = 0;

    public MonstrometerBlockEntity(BlockPos pos, BlockState state) {
        super(GBlockEntityTypes.MONSTROMETER, pos, state);
    }

    public static void createParticles(Level world, BlockPos origin) {
        Predicate<BlockPos> predicate = (pos) -> world.isEmptyBlock(pos) && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, world, pos, EntityType.ZOMBIE);
        if (world instanceof ServerLevel serverLevel) {
            MonstrometerBlock.getIndicatedBlocks(origin, predicate).forEach(pos -> {
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.01F;
                double z = pos.getZ() + 0.5;
                serverLevel.sendParticles(GParticleTypes.AURA_RINGER_INDICATOR, x, y, z, 1, 0, 0, 0, 0.5F);
            });
        }
    }

    public static void applyGlowing(Level world, BlockPos origin) {
        List<LivingEntity> mobs = MonstrometerBlockEntity.getNearbyMonsters(world, origin);
        mobs.forEach(mob -> mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60)));
    }

    private static List<LivingEntity> getNearbyMonsters(Level world, BlockPos origin) {
        return world.getEntitiesOfClass(LivingEntity.class, new AABB(origin).inflate(16, 6, 16), livingEntity -> livingEntity instanceof Monster && !livingEntity.getType().is(EntityTypeTags.RAIDERS));
    }

    public static void tick(Level world, BlockPos origin, BlockState state, MonstrometerBlockEntity entity) {
        int size = MonstrometerBlockEntity.getNearbyMonsters(world, origin).size();
        if (size > 0 && size != entity.monsterCount) {
            entity.monsterCount = size;
            world.updateNeighborsAt(origin, state.getBlock());
        }
        if (MonstrometerBlock.isActive(state)) {
            if (entity.activeTicks % 34 == 0) {
                applyGlowing(world, origin);
                createParticles(world, origin);
            }
            entity.activeTicks++;
        } else {
            entity.activeTicks = 0;
        }
    }

    public int getRedstoneSignal() {
        return Math.max(Math.min(this.monsterCount, 15), 0);
    }
}
