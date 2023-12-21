package net.orcinus.galosphere.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.entities.PinkSaltPillar;
import net.orcinus.galosphere.entities.PinkSaltShard;
import net.orcinus.galosphere.entities.Preserved;
import net.orcinus.galosphere.entities.SpectatorVision;
import net.orcinus.galosphere.entities.Specterpillar;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.entities.GlowFlare;
import net.orcinus.galosphere.entities.SilverBomb;
import net.orcinus.galosphere.entities.Sparkle;
import net.orcinus.galosphere.entities.SpectreFlare;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Galosphere.MODID);

    public static final RegistryObject<EntityType<SilverBomb>> SILVER_BOMB = ENTITY_TYPES.register("silver_bomb", () -> EntityType.Builder.<SilverBomb>of(SilverBomb::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(Galosphere.id("silver_bomb").toString()));
    public static final RegistryObject<EntityType<Sparkle>> SPARKLE = ENTITY_TYPES.register("sparkle", () -> EntityType.Builder.of(Sparkle::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(1.0F, 0.55F).clientTrackingRange(10).build(Galosphere.id("sparkle").toString()));
    public static final RegistryObject<EntityType<Spectre>> SPECTRE = ENTITY_TYPES.register("spectre", () -> EntityType.Builder.of(Spectre::new, MobCategory.AMBIENT).sized(0.5F, 0.5F).clientTrackingRange(8).updateInterval(2).build(Galosphere.id("spectre").toString()));
    public static final RegistryObject<EntityType<GlowFlare>> GLOW_FLARE = ENTITY_TYPES.register("glow_flare", () -> EntityType.Builder.<GlowFlare>of(GlowFlare::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(Galosphere.id("glow_flare").toString()));
    public static final RegistryObject<EntityType<SpectreFlare>> SPECTRE_FLARE = ENTITY_TYPES.register("spectre_flare", () -> EntityType.Builder.<SpectreFlare>of(SpectreFlare::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(Galosphere.id("spectre_flare").toString()));
    public static final RegistryObject<EntityType<Specterpillar>> SPECTERPILLAR = ENTITY_TYPES.register("specterpillar", () -> EntityType.Builder.of(Specterpillar::new, MobCategory.CREATURE).sized(0.4F, 0.3F).clientTrackingRange(8).updateInterval(2).build(Galosphere.id("specterpillar").toString()));
    public static final RegistryObject<EntityType<SpectatorVision>> SPECTATOR_VISION = ENTITY_TYPES.register("spectator_vision", () -> EntityType.Builder.<SpectatorVision>of(SpectatorVision::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(5).build(Galosphere.id("spectator_vision").toString()));
    public static final RegistryObject<EntityType<Berserker>> BERSERKER = ENTITY_TYPES.register("berserker", () -> EntityType.Builder.of(Berserker::new, MobCategory.MONSTER).sized(1.4F, 2.2F).clientTrackingRange(16).fireImmune().build(Galosphere.id("berserker").toString()));
    public static final RegistryObject<EntityType<Preserved>> PRESERVED = ENTITY_TYPES.register("preserved", () -> EntityType.Builder.of(Preserved::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build(Galosphere.id("preserved").toString()));
    public static final RegistryObject<EntityType<PinkSaltPillar>> PINK_SALT_PILLAR = ENTITY_TYPES.register("pink_salt_pillar", () -> EntityType.Builder.<PinkSaltPillar>of(PinkSaltPillar::new, MobCategory.MISC).sized(0.7F, 1.5F).clientTrackingRange(16).fireImmune().build(Galosphere.id("pink_salt_pillar").toString()));
    public static final RegistryObject<EntityType<PinkSaltShard>> PINK_SALT_SHARD = ENTITY_TYPES.register("pink_salt_shard", () -> EntityType.Builder.<PinkSaltShard>of(PinkSaltShard::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(Galosphere.id("pink_salt_shard").toString()));

}
