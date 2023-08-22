package net.orcinus.galosphere.world.gen.processors.pink_salt_shrine;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.orcinus.galosphere.blocks.GildedBeadsBlock;
import net.orcinus.galosphere.blocks.PinkSaltStrawBlock;
import net.orcinus.galosphere.init.GBlocks;
import org.jetbrains.annotations.Nullable;

public class MainRoomProcessor extends StructureProcessor {
    public static final Codec<MainRoomProcessor> CODEC = Codec.unit(MainRoomProcessor::new);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos blockPos, BlockPos blockPos2, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo structureBlockInfo2, StructurePlaceSettings structurePlaceSettings) {
        BlockState state = structureBlockInfo2.state();
        RandomSource randomSource = structurePlaceSettings.getRandom(structureBlockInfo2.pos());
        if (state.is(Blocks.BLUE_CANDLE)) {
            return new StructureTemplate.StructureBlockInfo(structureBlockInfo2.pos(), state.setValue(CandleBlock.CANDLES, Mth.nextInt(randomSource, 1, 4)), structureBlockInfo2.nbt());
        }
        if (state.is(GBlocks.PINK_SALT_STRAW)) {
            Direction direction = state.getValue(PinkSaltStrawBlock.TIP_DIRECTION);
            int height = UniformInt.of(2, 4).sample(randomSource);
            for (int i = 0; i <= height; i++) {
                BlockPos pos = structureBlockInfo2.pos().relative(direction, i);
                if (!(levelReader.getBlockState(pos).is(GBlocks.PINK_SALT_STRAW) || levelReader.getBlockState(pos).isAir())) {
                    levelReader.getChunk(pos).setBlockState(pos, GBlocks.PINK_SALT_STRAW.defaultBlockState().setValue(PinkSaltStrawBlock.TIP_DIRECTION, direction).setValue(PinkSaltStrawBlock.STRAW_SHAPE, PinkSaltStrawBlock.StrawShape.TOP), false);
                    break;
                }
                PinkSaltStrawBlock.StrawShape strawShape = i == 0 ? PinkSaltStrawBlock.StrawShape.BOTTOM : (i == height ? PinkSaltStrawBlock.StrawShape.TOP : PinkSaltStrawBlock.StrawShape.MIDDLE);
                levelReader.getChunk(pos).setBlockState(pos, GBlocks.PINK_SALT_STRAW.defaultBlockState().setValue(PinkSaltStrawBlock.TIP_DIRECTION, direction).setValue(PinkSaltStrawBlock.STRAW_SHAPE, strawShape), false);
            }
        }
        if (state.is(Blocks.CHAIN)) {
            int height = UniformInt.of(1, 3).sample(randomSource);
                for (int i = 1; i <= height; i++) {
                BlockPos pos = structureBlockInfo2.pos().below(i);
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
        return null;
    }
}
