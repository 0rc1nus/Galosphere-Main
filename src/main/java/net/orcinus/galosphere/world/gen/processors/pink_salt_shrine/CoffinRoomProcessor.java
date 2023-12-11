package net.orcinus.galosphere.world.gen.processors.pink_salt_shrine;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GStructureProcessorTypes;
import org.jetbrains.annotations.Nullable;

public class CoffinRoomProcessor extends StructureProcessor {
    public static final Codec<CoffinRoomProcessor> CODEC = Codec.unit(CoffinRoomProcessor::new);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos blockPos, BlockPos blockPos2, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo structureBlockInfo2, StructurePlaceSettings structurePlaceSettings) {
        if (structurePlaceSettings.getRandom(structureBlockInfo2.pos()).nextFloat() <= 0.4F && structureBlockInfo2.state().is(GBlocks.PINK_SALT_LAMP)) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putInt("Cooldown", 0);
            return new StructureTemplate.StructureBlockInfo(structureBlockInfo2.pos(), GBlocks.PINK_SALT_CHAMBER.defaultBlockState(), compoundTag);
        }
        return super.processBlock(levelReader, blockPos, blockPos2, structureBlockInfo, structureBlockInfo2, structurePlaceSettings);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return GStructureProcessorTypes.COFFIN_ROOM;
    }
}
