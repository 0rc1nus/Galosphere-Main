package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.Galosphere;
import net.orcinus.cavesandtrenches.entities.SilverBombEntity;
import net.orcinus.cavesandtrenches.entities.SparkleEntity;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Galosphere.MODID);

    public static final RegistryObject<EntityType<SilverBombEntity>> SIVLER_BOMB = ENTITY_TYPES.register("silver_bomb", () -> EntityType.Builder.<SilverBombEntity>of(SilverBombEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("silver_bomb"));
    public static final RegistryObject<EntityType<SparkleEntity>> SPARKLE = ENTITY_TYPES.register("sparkle", () -> EntityType.Builder.of(SparkleEntity::new, MobCategory.CREATURE).sized(0.8F, 0.7F).clientTrackingRange(10).build("sparkle"));

}
