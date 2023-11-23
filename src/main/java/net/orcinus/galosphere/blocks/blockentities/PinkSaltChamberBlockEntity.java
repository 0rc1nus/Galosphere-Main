package net.orcinus.galosphere.blocks.blockentities;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.blocks.PinkSaltChamberBlock;
import net.orcinus.galosphere.entities.Preserved;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;

import java.util.List;
import java.util.Optional;

public class PinkSaltChamberBlockEntity extends BlockEntity {
    private final int maxCooldown = 6000;
    private int cooldown = 0;

    public PinkSaltChamberBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(GBlockEntityTypes.PINK_SALT_CHAMBER, blockPos, blockState);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.cooldown = compoundTag.getInt("Cooldown");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("Cooldown", this.cooldown);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, PinkSaltChamberBlockEntity blockEntity) {
        if (!blockState.getValue(PinkSaltChamberBlock.CHARGED)) {
            return;
        }
        if (blockEntity.cooldown >= blockEntity.maxCooldown) {
            Optional<Player> optional = level.getEntitiesOfClass(Player.class, new AABB(blockPos).inflate(5.0D)).stream().filter(LivingEntity::isAlive).filter(player -> !player.getAbilities().instabuild).toList().stream().findAny();
            List<BlockPos> blockPosList = Lists.newArrayList();
            optional.ifPresent(player -> {
                int index = 0;
                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
                while (index < 34) {
                    RandomSource random = level.getRandom();
                    int range = 5;
                    int yRange = 2;
                    pos.set(blockPos.getX() + Mth.nextInt(random, -range, range), blockPos.getY() + Mth.nextInt(random, -yRange, yRange), blockPos.getZ() + Mth.nextInt(random, -range, range));
                    BlockPos below = pos.below();
                    BlockState state = level.getBlockState(pos);
                    BlockState belowState = level.getBlockState(below);
                    if (level instanceof ServerLevel && state.canBeReplaced() && belowState.isFaceSturdy(level, below, Direction.UP)) {
                        blockPosList.add(pos);
                    }
                    index++;
                }
                blockPosList.forEach(spawnPos -> {
                    if (level instanceof ServerLevel serverLevel) {
                        blockEntity.addParticles(blockPos, serverLevel, spawnPos);
                        blockEntity.handleSpawning(serverLevel, spawnPos);
                    }
                    level.playSound(null, blockPos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(blockPos, blockState.setValue(PinkSaltChamberBlock.CHARGED, false), 2);
                    blockEntity.resetCooldown();
                });
            });
        } else {
            blockEntity.cooldown++;
        }
    }

    private void resetCooldown() {
        this.cooldown = 0;
    }

    private void handleSpawning(ServerLevel serverLevel, BlockPos pos) {
        if (serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
            serverLevel.setBlock(pos, GBlocks.PINK_SALT_CLUSTER.defaultBlockState(), 2);
        } else {
            Preserved preserved = GEntityTypes.PRESERVED.create(serverLevel, null, null, pos, MobSpawnType.TRIGGERED, true, true);
            preserved.setPos(pos.getX(), pos.getY(), pos.getZ());
            preserved.setPersistenceRequired();
            preserved.setFromChamber(true);
            serverLevel.addFreshEntityWithPassengers(preserved);
        }
    }

    private void addParticles(BlockPos blockPos, ServerLevel serverLevel, BlockPos pos) {
        Vec3 vec3 = blockPos.getCenter().add(0, 0.5D, 0);
        Vec3 vec32 = pos.getCenter().add(0, 1, 0).subtract(vec3);
        Vec3 vec33 = vec32.normalize();
        for (int i = 1; i < Mth.floor(vec32.length()) + 7; ++i) {
            Vec3 vec34 = blockPos.getCenter().add(vec33.scale(i));
            serverLevel.sendParticles(ParticleTypes.CRIT, vec34.x, vec34.y, vec34.z, 12, 0.0, 0.0, 0.0, 0.0);
        }
    }

}
