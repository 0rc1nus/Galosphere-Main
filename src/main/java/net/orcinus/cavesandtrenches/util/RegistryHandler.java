package net.orcinus.cavesandtrenches.util;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

import java.util.function.Supplier;

public class RegistryHandler {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CavesAndTrenches.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CavesAndTrenches.MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, CavesAndTrenches.MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CavesAndTrenches.MODID);
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, CavesAndTrenches.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, CavesAndTrenches.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, CavesAndTrenches.MODID);

    public <T extends BlockEntityType<?>> RegistryObject<T> registerTileEntity(String key, Supplier<? extends T> tileEntity) {
        return TILE_ENTITIES.register(key, tileEntity);
    }

    public <B extends Block> RegistryObject<B> registerBlock(String key, Supplier<? extends B> block) {
        RegistryObject<B> blocks = BLOCKS.register(key, block);
        ITEMS.register(key, () -> new BlockItem(blocks.get(), new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES)));
        return blocks;
    }

    public <B extends Block> RegistryObject<B> registerNoTabBlock(String key, Supplier<? extends B> block) {
        return BLOCKS.register(key, block);
    }

    public <I extends Item> RegistryObject<I> registerItem(String key, Supplier<? extends I> item) {
        return ITEMS.register(key, item);
    }

    public RegistryObject<Item> registerBaseItem(String key) {
        return ITEMS.register(key, () -> new Item(new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES)));
    }

    public <B extends Biome> RegistryObject<B> registerBiome(String key, Supplier<? extends B> biome) {
        return BIOMES.register(key, biome);
    }

    public <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<F> registerFeature(String key, Supplier<? extends F> feature) {
        return FEATURES.register(key, feature);
    }

    public RegistryObject<SimpleParticleType> registerParticle(String key, boolean alwaysShow) {
        return PARTICLE_TYPES.register(key, () -> new SimpleParticleType(alwaysShow));
    }

    public <C extends FeatureConfiguration, F extends Feature<C>, CF extends ConfiguredFeature<C, F>> CF registerConfiguredFeature(String key, CF configuredFeature) {
        ResourceLocation ID = new ResourceLocation(CavesAndTrenches.MODID, key);
        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Configured Feature " + key + "already exists in the registry");

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }

    public <P extends PlacedFeature> P registerPlacedFeature(String key, P configuredFeature) {
        ResourceLocation ID = new ResourceLocation(CavesAndTrenches.MODID, key);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Placed Feature " + key + "already exists in the registry");

        Registry.register(BuiltinRegistries.PLACED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }

    public <E extends Entity> RegistryObject<EntityType<E>> registerEntity(String key, EntityType.Builder<E> builder) {
        return ENTITY_TYPES.register(key, () -> builder.build(new ResourceLocation(CavesAndTrenches.MODID, key).toString()));
    }

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        FEATURES.register(eventBus);
        PARTICLE_TYPES.register(eventBus);
        BIOMES.register(eventBus);
        ENTITY_TYPES.register(eventBus);
    }


}
