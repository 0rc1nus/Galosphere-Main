package net.orcinus.cavesandtrenches.client.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.cavesandtrenches.api.IBanner;

@OnlyIn(Dist.CLIENT)
public class BannerLayer<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {

    public BannerLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource source, int packedLight, T entity, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        Item headItem = entity.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item chestItem = entity.getItemBySlot(EquipmentSlot.CHEST).getItem();
        if (!((IBanner)entity).getBanner().isEmpty()) {
            ItemStack itemstack = ((IBanner)entity).getBanner();
            if (itemstack != null) {
                if (!itemstack.isEmpty()) {
                    Item item = itemstack.getItem();
                    stack.pushPose();
                    stack.scale(1.0F, 1.0F, 1.0F);
                    this.getParentModel().getHead().translateAndRotate(stack);
                    if (!(item instanceof ArmorItem) || ((ArmorItem) item).getSlot() != EquipmentSlot.HEAD) {
                        stack.translate(0.0D, -0.25D, 0.0D);
                        stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                        stack.scale(0.625F, -0.625F, -0.625F);
                        Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, itemstack, ItemTransforms.TransformType.HEAD, false, stack, source, packedLight);
                    }
                    stack.popPose();
                }
            }
        }
    }
}
