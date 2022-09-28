package net.orcinus.galosphere.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.FayEntity;
import net.orcinus.galosphere.entities.GlowFlareEntity;
import net.orcinus.galosphere.entities.SilverBombEntity;
import net.orcinus.galosphere.entities.SparkleEntity;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Galosphere.MODID);

    public static final RegistryObject<EntityType<SilverBombEntity>> SIVLER_BOMB = ENTITY_TYPES.register("silver_bomb", () -> EntityType.Builder.<SilverBombEntity>of(SilverBombEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("silver_bomb"));
    public static final RegistryObject<EntityType<SparkleEntity>> SPARKLE = ENTITY_TYPES.register("sparkle", () -> EntityType.Builder.of(SparkleEntity::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(1.0F, 0.55F).clientTrackingRange(10).build("sparkle"));
    public static final RegistryObject<EntityType<FayEntity>> FAY = ENTITY_TYPES.register("fay", () -> EntityType.Builder.of(FayEntity::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(0.35F, 0.35F).clientTrackingRange(8).updateInterval(2).build(new ResourceLocation(Galosphere.MODID, "fay").toString()));
    public static final RegistryObject<EntityType<GlowFlareEntity>> GLOW_FLARE = ENTITY_TYPES.register("glow_flare", () -> EntityType.Builder.<GlowFlareEntity>of(GlowFlareEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(Galosphere.MODID, "glow_flare").toString()));

}
