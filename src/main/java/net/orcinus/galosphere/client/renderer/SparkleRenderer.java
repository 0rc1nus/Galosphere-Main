package net.orcinus.galosphere.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.SparkleModel;
import net.orcinus.galosphere.entities.SparkleEntity;
import net.orcinus.galosphere.init.GModelLayers;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SparkleRenderer extends MobRenderer<SparkleEntity, EntityModel<SparkleEntity>> {
    private static final Map<SparkleEntity.CrystalType, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for (SparkleEntity.CrystalType type : SparkleEntity.CrystalType.BY_ID) {
            map.put(type, new ResourceLocation(Galosphere.MODID, String.format("textures/entity/sparkle/%s_sparkle.png", type.getName())));
        }
    });
    private static final ResourceLocation TEXTURE = new ResourceLocation(Galosphere.MODID, "textures/entity/sparkle/sparkle.png");

    public SparkleRenderer(EntityRendererProvider.Context context) {
        super(context, new SparkleModel<>(context.bakeLayer(GModelLayers.SPARKLE)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(SparkleEntity entity) {
        return entity.getCrystaltype() == SparkleEntity.CrystalType.NONE ? TEXTURE : TEXTURE_BY_TYPE.get(entity.getCrystaltype());
    }

}
