package net.orcinus.cavesandtrenches.client.entity.renderer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.client.entity.model.SparkleModel;
import net.orcinus.cavesandtrenches.entities.SparkleEntity;
import net.orcinus.cavesandtrenches.init.CTModelLayers;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SparkleRenderer extends MobRenderer<SparkleEntity, SparkleModel<SparkleEntity>> {
    private static final Map<SparkleEntity.Type, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for(SparkleEntity.Type crystalType : SparkleEntity.Type.BY_ID) {
            map.put(crystalType, new ResourceLocation(CavesAndTrenches.MODID, String.format("textures/entity/sparkle/sparkle_%s.png", crystalType.getName())));
        }
    });

    public SparkleRenderer(EntityRendererProvider.Context context) {
        super(context, new SparkleModel<>(context.bakeLayer(CTModelLayers.SPARKLE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SparkleEntity entity) {
        return TEXTURE_BY_TYPE.get(entity.getCrystalType());
    }
}
