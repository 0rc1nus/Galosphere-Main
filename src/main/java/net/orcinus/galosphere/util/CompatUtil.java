package net.orcinus.galosphere.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CompatUtil {

    public CompatUtil() {
    }

    public boolean isModInstalled(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean matchesCompatItem(Item item, String modid, String name) {
        return item == this.getCompatItem(modid, name);
    }

    public Item getCompatItem(String modid, String name) {
        return BuiltInRegistries.ITEM.get(new ResourceLocation(modid, name));
    }

}
