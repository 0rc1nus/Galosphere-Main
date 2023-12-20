package net.orcinus.galosphere.world.gen.processors.pink_salt_shrine;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.orcinus.galosphere.blocks.PinkSaltStrawBlock;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GStructureProcessorTypes;
import org.jetbrains.annotations.Nullable;

public class MainRoomProcessor extends StructureProcessor {
    public static final Codec<MainRoomProcessor> CODEC = Codec.unit(MainRoomProcessor::new);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos blockPos, BlockPos blockPos2, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo structureBlockInfo2, StructurePlaceSettings structurePlaceSettings) {
        BlockState state = structureBlockInfo2.state();
        BlockPos position = structureBlockInfo2.pos();
        RandomSource randomSource = structurePlaceSettings.getRandom(position);
        if (state.getBlock() instanceof CandleBlock) {
            return new StructureTemplate.StructureBlockInfo(position, state.setValue(CandleBlock.CANDLES, Mth.nextInt(randomSource, 1, 4)), structureBlockInfo2.nbt());
        }
        if (state.is(GBlocks.PINK_SALT_STRAW.get())) {
            System.out.println(position.toShortString());
            BlockPos.MutableBlockPos mutableBlockPos = position.mutable();
            Direction direction = state.getValue(PinkSaltStrawBlock.TIP_DIRECTION);
            int height = UniformInt.of(2, 4).sample(randomSource);
            for (int i = 0; i <= height; i++) {
                if (levelReader.isEmptyBlock(mutableBlockPos) || levelReader.getBlockState(mutableBlockPos).is(GBlocks.PINK_SALT_STRAW.get())) {
                    boolean emptyBlock = levelReader.isEmptyBlock(mutableBlockPos.relative(direction)) || levelReader.getBlockState(mutableBlockPos.relative(direction)).is(GBlocks.PINK_SALT_STRAW.get());
                    if (i == height || !emptyBlock) {
                        levelReader.getChunk(mutableBlockPos).setBlockState(mutableBlockPos, GBlocks.PINK_SALT_STRAW.get().defaultBlockState().setValue(PinkSaltStrawBlock.TIP_DIRECTION, direction).setValue(PinkSaltStrawBlock.STRAW_SHAPE, PinkSaltStrawBlock.StrawShape.TOP), false);
                        break;
                    }
                    PinkSaltStrawBlock.StrawShape strawShape = i == 0 ? PinkSaltStrawBlock.StrawShape.BOTTOM : PinkSaltStrawBlock.StrawShape.MIDDLE;
                    levelReader.getChunk(mutableBlockPos).setBlockState(mutableBlockPos, GBlocks.PINK_SALT_STRAW.get().defaultBlockState().setValue(PinkSaltStrawBlock.TIP_DIRECTION, direction).setValue(PinkSaltStrawBlock.STRAW_SHAPE, strawShape), false);
                }
                mutableBlockPos.move(direction);
            }
        }
        if (state.is(Blocks.CHAIN)) {
            int height = UniformInt.of(1, 3).sample(randomSource);
            for (int i = 1; i <= height; i++) {
                BlockPos pos = position.below(i);
                if (!levelReader.getBlockState(pos).isAir()) {
                    break;
                }
                levelReader.getChunk(pos).setBlockState(pos, Blocks.CHAIN.defaultBlockState(), false);
            }
        }
        return structureBlockInfo2;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return GStructureProcessorTypes.PINK_SALT_MAIN_ROOM.get();
    }
}