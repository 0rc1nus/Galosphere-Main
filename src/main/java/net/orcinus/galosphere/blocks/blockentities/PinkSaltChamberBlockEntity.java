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
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.blocks.PinkSaltChamberBlock;
import net.orcinus.galosphere.entities.Preserved;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GSoundEvents;

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
            List<BlockPos> poses = Lists.newArrayList();
            int maxCount = UniformInt.of(3, 5).sample(level.getRandom());
            if (level.getDifficulty() == Difficulty.HARD) {
                maxCount = UniformInt.of(4, 10).sample(level.getRandom());
            }
            Optional<Player> optional = level.getEntitiesOfClass(Player.class, new AABB(blockPos).inflate(6.0D)).stream().filter(LivingEntity::isAlive).filter(player -> !player.getAbilities().instabuild).toList().stream().findAny();
            optional.ifPresent(player -> {
                int range = 5;
                int yRange = 2;
                for (int y = -yRange; y <= yRange; y++) {
                    for (int x = -range; x <= range; x++) {
                        for (int z = -range; z <= range; z++) {
                            BlockPos position = blockPos.offset(x, y, z);
                            if (level.getBlockState(position.below()).isFaceSturdy(level, position.below(), Direction.UP) && level.getBlockState(position).isAir()) {
                                poses.add(position);
                            }
                        }
                    }
                }
            });
            if (!poses.isEmpty()) {
                for (int count = 0; count < maxCount; count++) {
                    BlockPos randomPos = poses.get(level.getRandom().nextInt(poses.size()));
                    if (level instanceof ServerLevel serverLevel) {
                        blockEntity.addParticles(blockPos, serverLevel, randomPos);
                        blockEntity.handleSpawning(serverLevel, randomPos);
                    }
                }
                blockEntity.resetCooldown();
                level.playSound(null, blockPos, GSoundEvents.CHAMBER_SUMMON, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
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
            serverLevel.sendParticles(ParticleTypes.CRIT, vec34.x, vec34.y, vec34.z, 30, 0.0, 0.0, 0.0, 0.0);
        }
    }

}
