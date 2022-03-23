package net.orcinus.galosphere.init;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.gen.features.CrystalSpikeFeature;
import net.orcinus.galosphere.world.gen.features.FlutteredLeafFeature;
import net.orcinus.galosphere.world.gen.features.MysteriaTreeFeature;
import net.orcinus.galosphere.world.gen.features.StiffenedVinesFeature;
import net.orcinus.galosphere.world.gen.features.config.CrystalSpikeConfig;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Galosphere.MODID);

    public static final RegistryObject<Feature<CrystalSpikeConfig>> CRYSTAL_SPIKE = FEATURES.register("crystal_spike", () -> new CrystalSpikeFeature(CrystalSpikeConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MYSTERIA_TREE = FEATURES.register("mysteria_tree", () -> new MysteriaTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> STIFFENED_ROOTS = FEATURES.register("stiffened_roots", () -> new StiffenedVinesFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FLUTTERED_LEAF = FEATURES.register("fluttered_leaf", () -> new FlutteredLeafFeature(NoneFeatureConfiguration.CODEC));

}
