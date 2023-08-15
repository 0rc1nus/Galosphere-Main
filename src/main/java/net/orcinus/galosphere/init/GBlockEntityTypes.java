package net.orcinus.galosphere.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.GildedBeadsBlock;
import net.orcinus.galosphere.blocks.blockentities.CordycepsBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.GildedBeadsBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.MonstrometerBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.GlowInkClumpsBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.ShadowFrameBlockEntity;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Galosphere.MODID);

    public static final RegistryObject<BlockEntityType<MonstrometerBlockEntity>> MONSTROMETER = BLOCK_ENTITIES.register("monstrometer", () -> BlockEntityType.Builder.of(MonstrometerBlockEntity::new, GBlocks.MONSTROMETER.get()).build(null));
    public static final RegistryObject<BlockEntityType<GlowInkClumpsBlockEntity>> GLOW_INK_CLUMPS = BLOCK_ENTITIES.register("glow_ink_clumps", () -> BlockEntityType.Builder.of(GlowInkClumpsBlockEntity::new, GBlocks.GLOW_INK_CLUMPS.get()).build(null));
    public static final RegistryObject<BlockEntityType<CordycepsBlockEntity>> CORDYCEPS = BLOCK_ENTITIES.register("cordyceps", () -> BlockEntityType.Builder.of(CordycepsBlockEntity::new, GBlocks.LICHEN_CORDYCEPS.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShadowFrameBlockEntity>> SHADOW_FRAME = BLOCK_ENTITIES.register("shadow_frame", () -> BlockEntityType.Builder.of(ShadowFrameBlockEntity::new, GBlocks.SHADOW_FRAME.get()).build(null));
    public static final RegistryObject<BlockEntityType<GildedBeadsBlockEntity>> GILDED_BEADS = BLOCK_ENTITIES.register("gilded_beads", () -> BlockEntityType.Builder.of(GildedBeadsBlockEntity::new, GBlocks.GILDED_BEADS.get()).build(null));

}



