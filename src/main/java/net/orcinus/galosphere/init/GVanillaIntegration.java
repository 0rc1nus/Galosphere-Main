package net.orcinus.galosphere.init;

import net.minecraft.world.level.block.ComposterBlock;

public class GVanillaIntegration {

    public static void init() {
        ComposterBlock.COMPOSTABLES.put(GBlocks.LICHEN_MOSS.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(GBlocks.BOWL_LICHEN.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(GBlocks.LICHEN_ROOTS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(GBlocks.LICHEN_SHELF.get(), 0.45F);
    }

}
