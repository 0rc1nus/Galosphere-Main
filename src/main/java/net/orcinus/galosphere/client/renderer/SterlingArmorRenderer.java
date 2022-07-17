package net.orcinus.galosphere.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.init.GModelLayers;

public class SterlingArmorRenderer implements ArmorRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Galosphere.MODID, "textures/entity/sterling_helmet.png");

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        SterlingArmorModel<LivingEntity> model = new SterlingArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(GModelLayers.STERLING_HELMET));
        contextModel.copyPropertiesTo(model);
        contextModel.setAllVisible(false);
        model.head.visible = slot == EquipmentSlot.HEAD;
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, TEXTURE);
    }

}
