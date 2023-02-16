package net.orcinus.galosphere.compat.integration.terrablender;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.util.BiomeReagentHandler;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

import java.util.function.Consumer;

public class GalosphereRegion extends Region {

    public GalosphereRegion() {
        super(new ResourceLocation(Galosphere.MODID, "biome_provider"), RegionType.OVERWORLD, 1);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        mapper.accept(Pair.of(BiomeReagentHandler.CRYSTAL_CANYONS_PARAMETER, BiomeReagentHandler.CRYSTAL_CANYONS));
        mapper.accept(Pair.of(BiomeReagentHandler.LICHEN_CAVES_PARAMETER, BiomeReagentHandler.LICHEN_CAVES));
    }

    public void init(ParallelDispatchEvent event) {
        event.enqueueWork(() -> Regions.register(new GalosphereRegion()));
    }

}
