package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.orcinus.galosphere.Galosphere;

import java.util.Map;
import java.util.Optional;

public class GMemoryModuleTypes {
    public static final Map<ResourceLocation, MemoryModuleType<?>> MEMORY_MODULE_TYPES = Maps.newLinkedHashMap();

    public static final MemoryModuleType<BlockPos> NEAREST_POLLINATED_CLUSTER = register("nearest_pollinated_cluster");
    public static final MemoryModuleType<Unit> POLLINATED_COOLDOWN = register("pollinated_cooldown", Codec.unit(Unit.INSTANCE));
    public static final MemoryModuleType<Boolean> CAN_BURY = register("can_bury", Codec.BOOL);
    public static final MemoryModuleType<BlockPos> NEAREST_LICHEN_MOSS = register("nearest_lichen_moss");
    public static final MemoryModuleType<Unit> IS_ROARING = register("roaring", Codec.unit(Unit.INSTANCE));
    public static final MemoryModuleType<Unit> IS_SMASHING = register("smashing", Codec.unit(Unit.INSTANCE));
    public static final MemoryModuleType<Unit> IS_IMPALING = register("impaling", Codec.unit(Unit.INSTANCE));
    public static final MemoryModuleType<Unit> IMPALING_COOLDOWN = register("impaling_cooldown", Codec.unit(Unit.INSTANCE));
    public static final MemoryModuleType<Integer> IMPALING_COUNT = register("impaling_count", Codec.INT);

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
        MEMORY_MODULE_TYPES.forEach((resourceLocation, memoryModuleType) -> Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, resourceLocation, memoryModuleType));
    }

}
