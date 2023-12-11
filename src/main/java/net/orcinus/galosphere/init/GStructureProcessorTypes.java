package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.gen.processors.pink_salt_shrine.CoffinRoomProcessor;
import net.orcinus.galosphere.world.gen.processors.pink_salt_shrine.MainRoomProcessor;
import net.orcinus.galosphere.world.gen.processors.pink_salt_shrine.NoWaterloggedProcessor;
import net.orcinus.galosphere.world.gen.processors.pink_salt_shrine.RawOreProcessor;

import java.util.Map;

public class GStructureProcessorTypes {
    private static final Map<ResourceLocation, StructureProcessorType<?>> STRUCTURE_PROCESSORS = Maps.newLinkedHashMap();

    public static final StructureProcessorType<MainRoomProcessor> PINK_SALT_MAIN_ROOM = register("pink_salt_main_room", MainRoomProcessor.CODEC);
    public static final StructureProcessorType<NoWaterloggedProcessor> NO_WATERLOGGED = register("no_waterlogged", NoWaterloggedProcessor.CODEC);
    public static final StructureProcessorType<RawOreProcessor> RAW_ORES = register("raw_ores", RawOreProcessor.CODEC);
    public static final StructureProcessorType<CoffinRoomProcessor> COFFIN_ROOM = register("coffin_room", CoffinRoomProcessor.CODEC);

    public static <P extends StructureProcessor> StructureProcessorType<P> register(String string, Codec<P> codec) {
        StructureProcessorType<P> type = () -> codec;
        STRUCTURE_PROCESSORS.put(Galosphere.id(string), type);
        return type;
    }

    public static void init() {
        STRUCTURE_PROCESSORS.forEach((resourceLocation, processorType) -> Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, resourceLocation, processorType));
    }

}
