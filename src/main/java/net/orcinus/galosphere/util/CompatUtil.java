package net.orcinus.galosphere.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
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

    public boolean matchesCompatBlock(Block block, String modid, String name) {
        return block == this.getCompatBlock(modid, name);
    }

    public boolean matchesCompatItem(Item item, String modid, String name) {
        return item == this.getCompatItem(modid, name);
    }

    public boolean matchesCompatParticle(SimpleParticleType particleType, String modid, String name) {
        return particleType == this.getCompatParticle(modid, name);
    }

    public boolean matchesCompatEffect(MobEffect effect, String modid, String name) {
        return effect == this.getCompatEffect(modid, name);
    }

    public Block getCompatBlock(String modid, String name) {
        return Registry.BLOCK.get(new ResourceLocation(modid, name));
    }

    public Item getCompatItem(String modid, String name) {
        return Registry.ITEM.get(new ResourceLocation(modid, name));
    }

    public SimpleParticleType getCompatParticle(String modid, String name) {
        return (SimpleParticleType) Registry.PARTICLE_TYPE.get(new ResourceLocation(modid, name));
    }

    public MobEffect getCompatEffect(String modid, String name) {
        return Registry.MOB_EFFECT.get(new ResourceLocation(modid, name));
    }

}
