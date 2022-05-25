package net.orcinus.galosphere.init;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.gen.features.CrystalSpikeFeature;
import net.orcinus.galosphere.world.gen.features.config.CrystalSpikeConfig;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Galosphere.MODID);

    public static final RegistryObject<Feature<CrystalSpikeConfig>> CRYSTAL_SPIKE = FEATURES.register("crystal_spike", () -> new CrystalSpikeFeature(CrystalSpikeConfig.CODEC));

}
