package net.orcinus.galosphere.client.renderer;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.BerserkerModel;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.init.GModelLayers;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class BerserkerRenderer extends MobRenderer<Berserker, BerserkerModel<Berserker>> {
    private static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), map -> {
        map.put(0, new ResourceLocation(Galosphere.MODID, "textures/entity/berserker/berserker.png"));
        map.put(1, new ResourceLocation(Galosphere.MODID, "textures/entity/berserker/half_damaged_berserker.png"));
        map.put(2, new ResourceLocation(Galosphere.MODID, "textures/entity/berserker/high_damaged_berserker.png"));
        map.put(3, new ResourceLocation(Galosphere.MODID, "textures/entity/berserker/stationary_berserker.png"));
    });

    public BerserkerRenderer(EntityRendererProvider.Context context) {
        super(context, new BerserkerModel<>(context.bakeLayer(GModelLayers.BERSERKER)), 0.9F);
    }

    @Override
    public ResourceLocation getTextureLocation(Berserker entity) {
        return TEXTURES.get(entity.getStage());
    }

    @Override
    protected boolean isShaking(Berserker livingEntity) {
        return livingEntity.isShedding();
    }

}
