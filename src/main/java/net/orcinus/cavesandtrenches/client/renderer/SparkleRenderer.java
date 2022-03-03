package net.orcinus.cavesandtrenches.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.client.model.SparkleModel;
import net.orcinus.cavesandtrenches.entities.SparkleEntity;
import net.orcinus.cavesandtrenches.init.CTModelLayers;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SparkleRenderer extends MobRenderer<SparkleEntity, EntityModel<SparkleEntity>> {
    private static final Map<SparkleEntity.CrystalType, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for(SparkleEntity.CrystalType type : SparkleEntity.CrystalType.BY_ID) {
            map.put(type, new ResourceLocation(CavesAndTrenches.MODID, String.format("textures/entity/sparkle/%s_sparkle.png", type.getName())));
        }
    });

    public SparkleRenderer(EntityRendererProvider.Context context) {
        super(context, new SparkleModel<>(context.bakeLayer(CTModelLayers.SPARKLE)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(SparkleEntity entity) {
        return TEXTURE_BY_TYPE.get(entity.getCrystaltype());
    }

}
