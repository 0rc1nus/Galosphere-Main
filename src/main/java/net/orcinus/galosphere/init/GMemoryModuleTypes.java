package net.orcinus.galosphere.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GMemoryModuleTypes {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, Galosphere.MODID);

    public static final RegistryObject<MemoryModuleType<BlockPos>> NEAREST_POLLINATED_CLUSTER = MEMORY_MODULE_TYPES.register("nearest_pollinated_cluster", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Unit>> POLLINATED_COOLDOWN = MEMORY_MODULE_TYPES.register("pollinated_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.unit(Unit.INSTANCE))));
    public static final RegistryObject<MemoryModuleType<Boolean>> CAN_BURY = MEMORY_MODULE_TYPES.register("can_bury", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<BlockPos>> NEAREST_LICHEN_MOSS = MEMORY_MODULE_TYPES.register("nearest_lichen_moss", () -> new MemoryModuleType<>(Optional.empty()));

}