package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.entities.SilverBombEntity;
import net.orcinus.cavesandtrenches.entities.SparkleEntity;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, CavesAndTrenches.MODID);

    public static final RegistryObject<EntityType<SilverBombEntity>> SIVLER_BOMB = ENTITY_TYPES.register("silver_bomb", () -> EntityType.Builder.<SilverBombEntity>of(SilverBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("silver_bomb"));
    public static final RegistryObject<EntityType<SparkleEntity>> SPARKLE = ENTITY_TYPES.register("sparkle", () -> EntityType.Builder.of(SparkleEntity::new, MobCategory.MISC).sized(0.75F, 0.42F).clientTrackingRange(10).build("sparkle"));

}