package net.orcinus.galosphere.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.BerserkerModel;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.init.GModelLayers;

@Environment(EnvType.CLIENT)
public class BerserkerRenderer extends MobRenderer<Berserker, BerserkerModel<Berserker>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Galosphere.MODID, "textures/entity/berserker/berserker.png");

    public BerserkerRenderer(EntityRendererProvider.Context context) {
        super(context, new BerserkerModel<>(context.bakeLayer(GModelLayers.BLIGHTED)), 0.9F);
    }

    @Override
    public ResourceLocation getTextureLocation(Berserker entity) {
        return entity.getStationaryTicks() > 0 ? new ResourceLocation(Galosphere.MODID, "textures/entity/berserker/stationary_berserker.png") : TEXTURE;
    }

    @Override
    protected boolean isShaking(Berserker livingEntity) {
        boolean flag1 = livingEntity.getStationaryTicks() > 0;
        boolean present = livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent();
        boolean flag = flag1 && present;
        return flag;
    }
}
