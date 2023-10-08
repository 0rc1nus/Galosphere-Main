package net.orcinus.galosphere.world.gen.processors.pink_salt_shrine;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.orcinus.galosphere.init.GStructureProcessorTypes;
import org.jetbrains.annotations.Nullable;

public class NoWaterloggedProcessor extends StructureProcessor {
    public static final Codec<NoWaterloggedProcessor> CODEC = Codec.unit(NoWaterloggedProcessor::new);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos blockPos, BlockPos blockPos2, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo structureBlockInfo2, StructurePlaceSettings structurePlaceSettings) {
        if (structureBlockInfo2.state().hasProperty(BlockStateProperties.WATERLOGGED) && structureBlockInfo2.state().getValue(BlockStateProperties.WATERLOGGED)) {
            return new StructureTemplate.StructureBlockInfo(structureBlockInfo2.pos(), structureBlockInfo2.state().setValue(BlockStateProperties.WATERLOGGED, false), structureBlockInfo2.nbt());
        }
        return super.processBlock(levelReader, blockPos, blockPos2, structureBlockInfo, structureBlockInfo2, structurePlaceSettings);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return GStructureProcessorTypes.NO_WATERLOGGED;
    }
}
