package net.orcinus.galosphere.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.orcinus.galosphere.blocks.AuraListenerBlock;
import net.orcinus.galosphere.init.CTBlockEntities;
import net.orcinus.galosphere.init.CTParticleTypes;

public class AuraListenerBlockEntity extends BlockEntity {

    public AuraListenerBlockEntity(BlockPos pos, BlockState state) {
        super(CTBlockEntities.AURA_LISTENER.get(), pos, state);
    }

    public static void listenTick(Level world, BlockPos blockPos, BlockState state, AuraListenerBlockEntity blockEntity) {
        int range = AuraListenerBlock.getRange();
        int height = 3;
        if (state.getValue(AuraListenerBlock.LISTENING)) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    for (int y = -height; y <= range; y++) {
                        BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                        for (Monster entity : world.getEntitiesOfClass(Monster.class, new AABB(pos))) {
                            if (entity.isAlive()) {
                                BlockPos monsterPos = entity.blockPosition();
                                if (x * x + z * z <= range * range) {
                                    entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20));
                                    if (world.getBlockState(monsterPos.below()).isFaceSturdy(world, monsterPos.below(), Direction.UP)) {
                                        ((ServerLevel) world).sendParticles(CTParticleTypes.AURA_LISTENER.get(), monsterPos.getX() + 0.5D, monsterPos.getY(), monsterPos.getZ() + 0.5D, 1, 0.0D, 0.0D, 0.0D, 0.15D);
                                    }
                                } else if (x * x + z * z <= range) {
                                    if (world.getRandom().nextInt(10) == 0) {
                                        ((ServerLevel) world).sendParticles(CTParticleTypes.AURA_EMISSION.get(), entity.getX(), entity.getY(), entity.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.15D);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
