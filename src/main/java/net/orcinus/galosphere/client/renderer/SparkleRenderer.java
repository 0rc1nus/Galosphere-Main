package net.orcinus.galosphere.client.renderer;

import java.util.Map;

import com.google.common.collect.Maps;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.SparkleModel;
import net.orcinus.galosphere.entities.Sparkle;
import net.orcinus.galosphere.init.GModelLayers;

@Environment(EnvType.CLIENT)
public class SparkleRenderer extends MobRenderer<Sparkle, EntityModel<Sparkle>> {
    private static final Map<Sparkle.CrystalType, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for (Sparkle.CrystalType type : Sparkle.CrystalType.BY_ID) {
            map.put(type, Galosphere.id(String.format("textures/entity/sparkle/%s_sparkle.png", type.getName())));
        }
    });
    private static final ResourceLocation TEXTURE = Galosphere.id("textures/entity/sparkle/sparkle.png");

    public SparkleRenderer(EntityRendererProvider.Context context) {
        super(context, new SparkleModel<>(context.bakeLayer(GModelLayers.SPARKLE)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(Sparkle entity) {
        return entity.getCrystaltype() == Sparkle.CrystalType.NONE ? TEXTURE : TEXTURE_BY_TYPE.get(entity.getCrystaltype());
    }

}
