package net.orcinus.galosphere.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.BerserkerModel;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.init.GModelLayers;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class BerserkerRenderer extends MobRenderer<Berserker, BerserkerModel<Berserker>> {
    private static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), map -> {
        map.put(0, Galosphere.id("textures/entity/berserker/berserker.png"));
        map.put(1, Galosphere.id("textures/entity/berserker/half_damaged_berserker.png"));
        map.put(2, Galosphere.id("textures/entity/berserker/high_damaged_berserker.png"));
        map.put(3, Galosphere.id("textures/entity/berserker/stationary_berserker.png"));
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