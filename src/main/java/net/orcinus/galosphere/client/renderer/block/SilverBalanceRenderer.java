package net.orcinus.galosphere.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.blocks.blockentities.SilverBalanceBlockEntity;

@Environment(EnvType.CLIENT)
public class SilverBalanceRenderer implements BlockEntityRenderer<SilverBalanceBlockEntity> {
    private final ItemRenderer itemRenderer;

    public SilverBalanceRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(SilverBalanceBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        ItemStack stack = blockEntity.getItem(0);
        int k = (int)blockEntity.getBlockPos().asLong();
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.0F, 0.5F);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            if (stack.getItem() instanceof BlockItem) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(30.0F));
            }
            poseStack.scale(0.75F, 0.75F, 0.75F);
            this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, i, j, poseStack, multiBufferSource, blockEntity.getLevel(), k);
            poseStack.popPose();
        }
    }

}
