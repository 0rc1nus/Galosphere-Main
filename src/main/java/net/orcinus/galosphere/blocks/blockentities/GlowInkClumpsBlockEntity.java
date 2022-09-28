package net.orcinus.galosphere.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.network.PacketDistributor;
import net.orcinus.galosphere.blocks.GlowInkClumpsBlock;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GNetworkHandler;
import net.orcinus.galosphere.packet.SendParticlesPacket;

public class GlowInkClumpsBlockEntity extends BlockEntity {
    private static int delay = 0;

    public GlowInkClumpsBlockEntity(BlockPos pos, BlockState state) {
        super(GBlockEntityTypes.GLOW_INK_CLUMPS.get(), pos, state);
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, GlowInkClumpsBlockEntity te) {
        if (state.getValue(BlockStateProperties.AGE_15) == 0) return;
        if (delay > 0) {
            delay--;
        } else {
            generateGlowInk(world, pos, state, 2);
        }
    }

    private static void generateGlowInk(Level world, BlockPos pos, BlockState originState, int range) {
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                for (int y = -range; y <= range; y++) {
                    BlockPos offset = pos.offset(x, y, z);
                    if (world.getBlockState(offset).isSolidRender(world, offset) && x * x + z * z + y * y <= range * range) {
                        for (Direction direction : Direction.values()) {
                            BlockPos relative = offset.relative(direction.getOpposite());
                            BlockState state = world.getBlockState(relative);
                            BlockState placeState = GBlocks.GLOW_INK_CLUMPS.get().defaultBlockState().setValue(GlowInkClumpsBlock.getFaceProperty(direction), true);
                            world.setBlock(pos, originState.setValue(BlockStateProperties.AGE_15, Math.max(0, originState.getValue(BlockStateProperties.AGE_15) - (originState.getValue(BlockStateProperties.AGE_15) <= 4 ? 3 : 1))), 2);
                            if (world.isStateAtPosition(relative, DripstoneUtils::isEmptyOrWater) || (world.getBlockState(relative).getMaterial().isReplaceable() && world.getBlockState(relative).getMaterial() != Material.LAVA)) {
                                if (state.getBlock() instanceof GlowInkClumpsBlock && state.getValue(GlowInkClumpsBlock.getFaceProperty(direction))) continue;
                                for (Direction dir : Direction.values()) {
                                    if (world.getBlockState(relative.relative(dir)).isSolidRender(world, relative.relative(dir))) {
                                        placeState = placeState.setValue(GlowInkClumpsBlock.getFaceProperty(dir), true);
                                    }
                                }
                                if (delay == 0) {
                                    GNetworkHandler.sendToAllInRangeClients(offset, (ServerLevel) world, 16, new SendParticlesPacket(offset));
                                    world.setBlock(relative, placeState.setValue(BlockStateProperties.AGE_15, 0).setValue(BlockStateProperties.WATERLOGGED, world.getBlockState(relative).is(Blocks.WATER)), 2);
                                    delay = 2;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        delay = tag.getInt("delay");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("delay", delay);
    }
}
