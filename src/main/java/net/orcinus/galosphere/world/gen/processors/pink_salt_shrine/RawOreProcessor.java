package net.orcinus.galosphere.world.gen.processors.pink_salt_shrine;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.orcinus.galosphere.init.GStructureProcessorTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RawOreProcessor extends StructureProcessor {
    public static final Codec<RawOreProcessor> CODEC = Codec.unit(RawOreProcessor::new);
    private static final List<Block> LIST = Util.make(Lists.newArrayList(), list -> {
        list.add(Blocks.RAW_IRON_BLOCK);
        list.add(Blocks.RAW_COPPER_BLOCK);
        list.add(Blocks.RAW_GOLD_BLOCK);
    });

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos blockPos, BlockPos blockPos2, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo structureBlockInfo2, StructurePlaceSettings structurePlaceSettings) {
        RandomSource randomSource = structurePlaceSettings.getRandom(structureBlockInfo2.pos());
        if (structureBlockInfo2.state().is(Blocks.RAW_COPPER_BLOCK)) {
            return new StructureTemplate.StructureBlockInfo(structureBlockInfo2.pos(), LIST.get(randomSource.nextInt(LIST.size())).defaultBlockState(), structureBlockInfo2.nbt());
        }
        return super.processBlock(levelReader, blockPos, blockPos2, structureBlockInfo, structureBlockInfo2, structurePlaceSettings);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return GStructureProcessorTypes.RAW_ORES.get();
    }
}