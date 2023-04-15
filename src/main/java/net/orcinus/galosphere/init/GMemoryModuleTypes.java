package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.orcinus.galosphere.Galosphere;

import java.util.Map;
import java.util.Optional;

public class GMemoryModuleTypes {
    public static final Map<ResourceLocation, MemoryModuleType<?>> MEMORY_MODULE_TYPES = Maps.newLinkedHashMap();

    public static final MemoryModuleType<BlockPos> NEAREST_POLLINATED_CLUSTER = register("nearest_pollinated_cluster");
    public static final MemoryModuleType<Integer> POLLINATED_COOLDOWN = register("pollinated_cooldown", Codec.INT);
    public static final MemoryModuleType<Boolean> CAN_BURY = register("can_bury");
    public static final MemoryModuleType<BlockPos> NEAREST_LICHEN_MOSS = register("nearest_lichen_moss");

    private static <U> MemoryModuleType<U> register(String string, Codec<U> codec) {
        MemoryModuleType<U> memoryModuleType = new MemoryModuleType<>(Optional.of(codec));
        MEMORY_MODULE_TYPES.put(Galosphere.id(string), memoryModuleType);
        return memoryModuleType;
    }

    private static <U> MemoryModuleType<U> register(String string) {
        MemoryModuleType<U> memoryModuleType = new MemoryModuleType<>(Optional.empty());
        MEMORY_MODULE_TYPES.put(Galosphere.id(string), memoryModuleType);
        return memoryModuleType;
    }

    public static void init() {
        MEMORY_MODULE_TYPES.forEach((resourceLocation, memoryModuleType) -> Registry.register(Registry.MEMORY_MODULE_TYPE, resourceLocation, memoryModuleType));
    }

}
