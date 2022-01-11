package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.entities.SilverBombEntity;
import net.orcinus.cavesandtrenches.entities.SparkleEntity;
import net.orcinus.cavesandtrenches.util.RegistryHandler;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTEntityTypes {

    public static final RegistryHandler REGISTRY = CavesAndTrenches.REGISTRY;

    public static final RegistryObject<EntityType<SilverBombEntity>> SIVLER_BOMB = REGISTRY.registerEntity("silver_bomb", EntityType.Builder.<SilverBombEntity>of(SilverBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
    public static final RegistryObject<EntityType<SparkleEntity>> SPARKLE = REGISTRY.registerEntity("sparkle", EntityType.Builder.of(SparkleEntity::new, MobCategory.MISC).sized(0.75F, 0.42F).clientTrackingRange(10));

}
