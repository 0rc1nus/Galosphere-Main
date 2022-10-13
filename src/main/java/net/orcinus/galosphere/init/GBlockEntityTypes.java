package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.blockentities.AuraRingerBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.GlowInkClumpsBlockEntity;

import java.util.Map;

public class GBlockEntityTypes {

    public static final Map<ResourceLocation, BlockEntityType<?>> BLOCK_ENTITIES = Maps.newLinkedHashMap();

    public static final BlockEntityType<AuraRingerBlockEntity> AURA_RINGER = registerBlockEntityType("aura_ringer", FabricBlockEntityTypeBuilder.create(AuraRingerBlockEntity::new, GBlocks.AURA_RINGER).build(null));
    public static final BlockEntityType<GlowInkClumpsBlockEntity> GLOW_INK_CLUMPS = registerBlockEntityType("glow_ink_clumps", FabricBlockEntityTypeBuilder.create(GlowInkClumpsBlockEntity::new, GBlocks.GLOW_INK_CLUMPS).build(null));

    public static <T extends BlockEntity, B extends BlockEntityType<T>> B registerBlockEntityType(String name, B type) {
        BLOCK_ENTITIES.put(new ResourceLocation(Galosphere.MODID, name), type);
        return type;
    }

    public static void init() {
        for (ResourceLocation id : BLOCK_ENTITIES.keySet()) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, id, BLOCK_ENTITIES.get(id));
        }
    }

}



