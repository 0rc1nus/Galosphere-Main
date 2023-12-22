package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.blockentities.CordycepsBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.GildedBeadsBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.GlowInkClumpsBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.MonstrometerBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.PinkSaltChamberBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.ShadowFrameBlockEntity;

import java.util.Map;

public class GBlockEntityTypes {

    public static final Map<ResourceLocation, BlockEntityType<?>> BLOCK_ENTITIES = Maps.newLinkedHashMap();

    public static final BlockEntityType<MonstrometerBlockEntity> MONSTROMETER = registerBlockEntityType("monstrometer", FabricBlockEntityTypeBuilder.create(MonstrometerBlockEntity::new, GBlocks.MONSTROMETER).build(null));
    public static final BlockEntityType<GlowInkClumpsBlockEntity> GLOW_INK_CLUMPS = registerBlockEntityType("glow_ink_clumps", FabricBlockEntityTypeBuilder.create(GlowInkClumpsBlockEntity::new, GBlocks.GLOW_INK_CLUMPS).build(null));
    public static final BlockEntityType<CordycepsBlockEntity> CORDYCEPS = registerBlockEntityType("cordyceps", BlockEntityType.Builder.of(CordycepsBlockEntity::new, GBlocks.LICHEN_CORDYCEPS).build(null));
    public static final BlockEntityType<ShadowFrameBlockEntity> SHADOW_FRAME = registerBlockEntityType("shadow_frame", FabricBlockEntityTypeBuilder.create(ShadowFrameBlockEntity::new, GBlocks.SHADOW_FRAME).build(null));
    public static final BlockEntityType<GildedBeadsBlockEntity> GILDED_BEADS = registerBlockEntityType("gilded_beads", FabricBlockEntityTypeBuilder.create(GildedBeadsBlockEntity::new, GBlocks.GILDED_BEADS).build(null));
    public static final BlockEntityType<PinkSaltChamberBlockEntity> PINK_SALT_CHAMBER = registerBlockEntityType("pink_salt_chamber", FabricBlockEntityTypeBuilder.create(PinkSaltChamberBlockEntity::new, GBlocks.PINK_SALT_CHAMBER).build(null));

    public static <T extends BlockEntity, B extends BlockEntityType<T>> B registerBlockEntityType(String name, B type) {
        BLOCK_ENTITIES.put(Galosphere.id(name), type);
        return type;
    }

    public static void init() {
        for (ResourceLocation id : BLOCK_ENTITIES.keySet()) {
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, BLOCK_ENTITIES.get(id));
        }
    }

}



