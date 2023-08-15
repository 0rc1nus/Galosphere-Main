package net.orcinus.galosphere.init;

import net.minecraft.world.level.block.ComposterBlock;

public class GVanillaIntegration {

    public static void init() {
        ComposterBlock.COMPOSTABLES.put(GBlocks.LICHEN_MOSS.get().asItem(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(GBlocks.BOWL_LICHEN.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(GBlocks.LICHEN_ROOTS.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(GBlocks.LICHEN_SHELF.get().asItem(), 0.45F);
        ComposterBlock.COMPOSTABLES.put(GBlocks.LICHEN_CORDYCEPS.get().asItem(), 0.4F);
    }

}
