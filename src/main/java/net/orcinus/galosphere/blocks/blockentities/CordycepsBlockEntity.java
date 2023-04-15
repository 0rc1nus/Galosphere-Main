package net.orcinus.galosphere.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.blocks.CordycepsBlock;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GEntityTypes;

import java.util.Optional;

public class CordycepsBlockEntity extends BlockEntity {
    private int growthTick;
    private int delay = 0;

    public CordycepsBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(GBlockEntityTypes.CORDYCEPS, blockPos, blockState);
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, CordycepsBlockEntity te) {
        if (!state.getValue(CordycepsBlock.ALIVE)) {
            return;
        }
        int stage = state.getValue(CordycepsBlock.ALIVE_STAGE);
        int delay = te.getDelay();
        if (te.getGrowthTick() == 6000) {
            Optional.ofNullable(GEntityTypes.SPECTRE.create(world)).ifPresent(spectre -> te.spawnSpectre(world, pos, spectre));
        }
        if (delay > 0) {
            te.setDelay(delay - 1);
        }
        if (stage < 5 && delay == 0) {
            world.setBlock(pos, state.setValue(CordycepsBlock.ALIVE_STAGE, stage + 1), 2);
            te.setDelay(20);
        }
        if (stage == 5) {
            te.setDelay(0);
            if (te.getGrowthTick() < 6000) {
                if (te.getGrowthTick() > 5000) {
                    world.setBlock(pos, state.setValue(CordycepsBlock.BULB, true), 2);
                }
                te.setGrowthTick(te.getGrowthTick() + 1);
            }
        }
    }

    private void spawnSpectre(Level world, BlockPos pos, SpectreEntity spectre) {
        spectre.moveTo(pos.getX() + 0.5D, (double) pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0F, 0.0f);
        spectre.setPersistenceRequired();
        world.addFreshEntity(spectre);
        world.destroyBlock(pos, true);
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setGrowthTick(int growthTick) {
        this.growthTick = growthTick;
    }

    public int getGrowthTick() {
        return growthTick;
    }
}
