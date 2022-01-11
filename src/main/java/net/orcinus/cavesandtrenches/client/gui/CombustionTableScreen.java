package net.orcinus.cavesandtrenches.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

@OnlyIn(Dist.CLIENT)
public class CombustionTableScreen extends AbstractContainerScreen<CombustionTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CavesAndTrenches.MODID, "textures/misc/gui/container/combustion_table.png");

    public CombustionTableScreen(CombustionTableMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 201;
    }

    public void render(PoseStack source, int mouseX, int mouseY, float delta) {
        this.renderBackground(source);
        super.render(source, mouseX, mouseY, delta);
        this.renderTooltip(source, mouseX, mouseY);
    }

    protected void renderBg(PoseStack stack, float delta, int mouseX, int mouseY) {
//        this.renderBackground(stack);
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, TEXTURE);
//        int i = this.leftPos;
//        int j = this.topPos;
//        this.blit(stack, i, j, 0, 0, this.imageWidth, this.imageHeight);
//    }
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(stack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        ItemStack itemStack = this.menu.getSlot(0).getItem();
        ItemStack itemStack1 = this.menu.getSlot(1).getItem();
//        if (!itemStack.isEmpty()) {
//            if (itemStack.hasTag() && itemStack.getTag() != null) {
//                int i = itemStack.getTag().getInt("Explosion");
//                if (i > 0) {
//                    this.minecraft.getItemRenderer().renderGuiItem(Items.GUNPOWDER.getDefaultInstance(), x + 89 + 2 * i, y + 37);
//                    this.minecraft.getItemRenderer().renderGuiItem(itemStack, x + 87, y + 35);
//                }
//            } else {
//                this.minecraft.getItemRenderer().renderGuiItem(itemStack, x + 87, y + 35);
//            }
//            if (!itemStack1.isEmpty()) {
//                this.minecraft.getItemRenderer().renderGuiItem(itemStack1, x + 89, y + 37);
//            }
//        }
    }
}
