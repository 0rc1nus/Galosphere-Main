package net.orcinus.cavesandtrenches.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.orcinus.cavesandtrenches.blocks.AuraListenerBlock;
import net.orcinus.cavesandtrenches.init.CTBlockEntities;
import net.orcinus.cavesandtrenches.init.CTParticleTypes;

public class AuraListenerBlockEntity extends BlockEntity {

    public AuraListenerBlockEntity(BlockPos pos, BlockState state) {
        super(CTBlockEntities.AURA_LISTENER.get(), pos, state);
    }

    public static void listenTick(Level world, BlockPos blockPos, BlockState state, AuraListenerBlockEntity blockEntity) {
        int radius = 8;
        int height = 3;
        if (state.getValue(AuraListenerBlock.LISTENING)) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = -height; y <= height; y++) {
                        BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                        if (x * x + z * z <= radius * radius) {
                            for (Monster monster : world.getEntitiesOfClass(Monster.class, new AABB(pos))) {
                                if (monster.isAlive()) {
                                    monster.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20));
                                    ((ServerLevel)world).sendParticles(CTParticleTypes.AURA_EMISSION.get(), monster.getX(), monster.getY(), monster.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.15D);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
