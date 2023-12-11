package net.orcinus.galosphere.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.gen.processors.pink_salt_shrine.CoffinRoomProcessor;
import net.orcinus.galosphere.world.gen.processors.pink_salt_shrine.MainRoomProcessor;
import net.orcinus.galosphere.world.gen.processors.pink_salt_shrine.NoWaterloggedProcessor;
import net.orcinus.galosphere.world.gen.processors.pink_salt_shrine.RawOreProcessor;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GStructureProcessorTypes {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Galosphere.MODID);

    public static final RegistryObject<StructureProcessorType<MainRoomProcessor>> PINK_SALT_MAIN_ROOM = register("pink_salt_main_room", MainRoomProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<NoWaterloggedProcessor>> NO_WATERLOGGED = register("no_waterlogged", NoWaterloggedProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<RawOreProcessor>> RAW_ORES = register("raw_ores", RawOreProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<CoffinRoomProcessor>> COFFIN_ROOM = register("coffin_room", CoffinRoomProcessor.CODEC);

    public static <P extends StructureProcessor> RegistryObject<StructureProcessorType<P>> register(String string, Codec<P> codec) {
        StructureProcessorType<P> type = () -> codec;
        return STRUCTURE_PROCESSOR_TYPES.register(string, () -> type);
    }

}
