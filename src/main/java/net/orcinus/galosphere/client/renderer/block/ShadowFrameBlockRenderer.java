package net.orcinus.galosphere.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.model.data.ModelData;
import net.orcinus.galosphere.blocks.ShadowFrameBlock;
import net.orcinus.galosphere.blocks.blockentities.ShadowFrameBlockEntity;
import net.orcinus.galosphere.init.GBlocks;

import javax.annotation.Nullable;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ShadowFrameBlockRenderer implements BlockEntityRenderer<ShadowFrameBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public ShadowFrameBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(ShadowFrameBlockEntity blockEntity, float value, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int j) {
        BlockState state = blockEntity.getCopiedState();
        if (state.isAir()) return;
        BakedModel bakedmodel = this.blockRenderer.getBlockModel(state);
        int i = Minecraft.getInstance().getBlockColors().getColor(state, null, null, 0);
        float f = (float)(i >> 16 & 255) / 255.0F;
        float f1 = (float)(i >> 8 & 255) / 255.0F;
        float f2 = (float)(i & 255) / 255.0F;
        RenderType renderType = bakedmodel.getRenderTypes(state, RandomSource.create(42), ModelData.EMPTY).asList().get(0);
        this.renderModel(blockEntity.getBlockPos(), blockEntity.getLevel(), poseStack.last(), multiBufferSource.getBuffer(RenderTypeHelper.getEntityRenderType(renderType, false)), state, bakedmodel, f, f1, f2, light, j, ModelData.EMPTY, renderType);
    }

    public void renderModel(BlockPos blockPos, Level world, PoseStack.Pose p_111068_, VertexConsumer p_111069_, @Nullable BlockState p_111070_, BakedModel p_111071_, float p_111072_, float p_111073_, float p_111074_, int p_111075_, int p_111076_, ModelData modelData, RenderType renderType) {
        RandomSource randomsource = RandomSource.create();

        for (Direction direction : Direction.values()) {
            if (world.getBlockState(blockPos.relative(direction)).is(GBlocks.SHADOW_FRAME.get()) && world.getBlockState(blockPos.relative(direction)).getValue(ShadowFrameBlock.FILLED)) {
                continue;
            }
            randomsource.setSeed(42L);
            renderQuadList(p_111068_, p_111069_, p_111072_, p_111073_, p_111074_, p_111071_.getQuads(p_111070_, direction, randomsource, modelData, renderType), p_111075_, p_111076_);
        }

        randomsource.setSeed(42L);
        renderQuadList(p_111068_, p_111069_, p_111072_, p_111073_, p_111074_, p_111071_.getQuads(p_111070_, (Direction)null, randomsource, modelData, renderType), p_111075_, p_111076_);
    }

    private static void renderQuadList(PoseStack.Pose p_111059_, VertexConsumer p_111060_, float p_111061_, float p_111062_, float p_111063_, List<BakedQuad> p_111064_, int p_111065_, int p_111066_) {
        for(BakedQuad bakedquad : p_111064_) {
            float f;
            float f1;
            float f2;
            if (bakedquad.isTinted()) {
                f = Mth.clamp(p_111061_, 0.0F, 1.0F);
                f1 = Mth.clamp(p_111062_, 0.0F, 1.0F);
                f2 = Mth.clamp(p_111063_, 0.0F, 1.0F);
            } else {
                f = 1.0F;
                f1 = 1.0F;
                f2 = 1.0F;
            }

            p_111060_.putBulkData(p_111059_, bakedquad, f, f1, f2, p_111065_, p_111066_);
        }

    }

}