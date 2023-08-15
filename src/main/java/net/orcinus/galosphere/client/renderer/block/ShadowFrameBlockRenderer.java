package net.orcinus.galosphere.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.blocks.ShadowFrameBlock;
import net.orcinus.galosphere.blocks.blockentities.ShadowFrameBlockEntity;
import net.orcinus.galosphere.init.GBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ShadowFrameBlockRenderer implements BlockEntityRenderer<ShadowFrameBlockEntity> {
    static final Direction[] DIRECTIONS = Direction.values();
    private final BlockRenderDispatcher blockRenderer;

    public ShadowFrameBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(ShadowFrameBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        BlockState state = blockEntity.getCopiedState();
        if (state.isAir()) return;
        this.tesselateBlock(blockEntity.getLevel(), this.blockRenderer.getBlockModel(state), state, blockEntity.getBlockPos(), poseStack, multiBufferSource.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state)), false, RandomSource.create(), state.getSeed(blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY);
    }

    public void tesselateBlock(BlockAndTintGetter blockAndTintGetter, BakedModel bakedModel, BlockState blockState, BlockPos blockPos, PoseStack poseStack, VertexConsumer vertexConsumer, boolean bl, RandomSource randomSource, long l, int i) {
        boolean bl2 = Minecraft.useAmbientOcclusion() && blockState.getLightEmission() == 0 && bakedModel.useAmbientOcclusion();
        Vec3 vec3 = blockState.getOffset(blockAndTintGetter, blockPos);
        poseStack.translate(vec3.x, vec3.y, vec3.z);
        try {
            if (bl2) {
                this.tesselateWithAO(blockAndTintGetter, bakedModel, blockState, blockPos, poseStack, vertexConsumer, bl, randomSource, l, i);
            } else {
                this.tesselateWithoutAO(blockAndTintGetter, bakedModel, blockState, blockPos, poseStack, vertexConsumer, bl, randomSource, l, i);
            }
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.forThrowable(throwable, "Tesselating block model");
            CrashReportCategory crashReportCategory = crashReport.addCategory("Block model being tesselated");
            CrashReportCategory.populateBlockDetails(crashReportCategory, blockAndTintGetter, blockPos, blockState);
            crashReportCategory.setDetail("Using AO", bl2);
            throw new ReportedException(crashReport);
        }
    }

    public void tesselateWithAO(BlockAndTintGetter blockAndTintGetter, BakedModel bakedModel, BlockState blockState, BlockPos blockPos, PoseStack poseStack, VertexConsumer vertexConsumer, boolean bl, RandomSource randomSource, long l, int i) {
        float[] fs = new float[DIRECTIONS.length * 2];
        BitSet bitSet = new BitSet(3);
        ModelBlockRenderer.AmbientOcclusionFace ambientOcclusionFace = new ModelBlockRenderer.AmbientOcclusionFace();
        BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
        for (Direction direction : DIRECTIONS) {
            randomSource.setSeed(l);
            List<BakedQuad> list = bakedModel.getQuads(blockState, direction, randomSource);
            if (list.isEmpty()) continue;
            mutableBlockPos.setWithOffset(blockPos, direction);
            if (skipRendering(blockAndTintGetter, blockState, blockPos, bl, mutableBlockPos, direction)) continue;
            this.renderModelFaceAO(blockAndTintGetter, blockState, blockPos, poseStack, vertexConsumer, list, fs, bitSet, ambientOcclusionFace, i);
        }
        randomSource.setSeed(l);
        List<BakedQuad> list2 = bakedModel.getQuads(blockState, null, randomSource);
        if (!list2.isEmpty()) {
            this.renderModelFaceAO(blockAndTintGetter, blockState, blockPos, poseStack, vertexConsumer, list2, fs, bitSet, ambientOcclusionFace, i);
        }
    }

    public void tesselateWithoutAO(BlockAndTintGetter blockAndTintGetter, BakedModel bakedModel, BlockState blockState, BlockPos blockPos, PoseStack poseStack, VertexConsumer vertexConsumer, boolean bl, RandomSource randomSource, long l, int i) {
        BitSet bitSet = new BitSet(3);
        BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
        for (Direction direction : DIRECTIONS) {
            randomSource.setSeed(l);
            List<BakedQuad> list = bakedModel.getQuads(blockState, direction, randomSource);
            if (list.isEmpty()) continue;
            mutableBlockPos.setWithOffset(blockPos, direction);
            if (skipRendering(blockAndTintGetter, blockState, blockPos, bl, mutableBlockPos, direction)) continue;
            int j = LevelRenderer.getLightColor(blockAndTintGetter, blockState, mutableBlockPos);
            this.renderModelFaceFlat(blockAndTintGetter, blockState, blockPos, j, i, false, poseStack, vertexConsumer, list, bitSet);
        }
        randomSource.setSeed(l);
        List<BakedQuad> list2 = bakedModel.getQuads(blockState, null, randomSource);
        if (!list2.isEmpty()) {
            this.renderModelFaceFlat(blockAndTintGetter, blockState, blockPos, -1, i, true, poseStack, vertexConsumer, list2, bitSet);
        }
    }

    private boolean skipRendering(BlockAndTintGetter blockAndTintGetter, BlockState blockState, BlockPos blockPos, boolean bl, BlockPos.MutableBlockPos mutableBlockPos, Direction direction) {
        BlockState relativeState = blockAndTintGetter.getBlockState(mutableBlockPos);
        if (relativeState.is(GBlocks.SHADOW_FRAME.get()) && relativeState.getValue(ShadowFrameBlock.FILLED)) {
            if (blockAndTintGetter.getBlockEntity(mutableBlockPos) instanceof ShadowFrameBlockEntity shadowFrameBlockEntity) {
                return shadowFrameBlockEntity.getCopiedState().isCollisionShapeFullBlock(blockAndTintGetter, mutableBlockPos);
            }
            return true;
        }
        return bl && !Block.shouldRenderFace(blockState, blockAndTintGetter, blockPos, direction, mutableBlockPos);
    }

    private void renderModelFaceAO(BlockAndTintGetter blockAndTintGetter, BlockState blockState, BlockPos blockPos, PoseStack poseStack, VertexConsumer vertexConsumer, List<BakedQuad> list, float[] fs, BitSet bitSet, ModelBlockRenderer.AmbientOcclusionFace ambientOcclusionFace, int i) {
        for (BakedQuad bakedQuad : list) {
            this.calculateShape(blockAndTintGetter, blockState, blockPos, bakedQuad.getVertices(), bakedQuad.getDirection(), fs, bitSet);
            ambientOcclusionFace.calculate(blockAndTintGetter, blockState, blockPos, bakedQuad.getDirection(), fs, bitSet, bakedQuad.isShade());
            this.putQuadData(blockAndTintGetter, blockState, blockPos, vertexConsumer, poseStack.last(), bakedQuad, ambientOcclusionFace.brightness[0], ambientOcclusionFace.lightmap[1], ambientOcclusionFace.lightmap[2], ambientOcclusionFace.lightmap[3], ambientOcclusionFace.lightmap[0], ambientOcclusionFace.lightmap[1], ambientOcclusionFace.lightmap[2], ambientOcclusionFace.lightmap[3], i);
        }
    }

    private void putQuadData(BlockAndTintGetter blockAndTintGetter, BlockState blockState, BlockPos blockPos, VertexConsumer vertexConsumer, PoseStack.Pose pose, BakedQuad bakedQuad, float f, float g, float h, float i, int j, int k, int l, int m, int n) {
        float r;
        float q;
        float p;
        if (bakedQuad.isTinted()) {
            int o = this.blockRenderer.getModelRenderer().blockColors.getColor(blockState, blockAndTintGetter, blockPos, bakedQuad.getTintIndex());
            p = (float)(o >> 16 & 0xFF) / 255.0f;
            q = (float)(o >> 8 & 0xFF) / 255.0f;
            r = (float)(o & 0xFF) / 255.0f;
        } else {
            p = 1.0f;
            q = 1.0f;
            r = 1.0f;
        }
        vertexConsumer.putBulkData(pose, bakedQuad, new float[]{f, g, h, i}, p, q, r, new int[]{j, k, l, m}, n, true);
    }

    private void calculateShape(BlockAndTintGetter blockAndTintGetter, BlockState blockState, BlockPos blockPos, int[] is, Direction direction, @Nullable float[] fs, BitSet bitSet) {
        float m;
        int l;
        float f = 32.0f;
        float g = 32.0f;
        float h = 32.0f;
        float i = -32.0f;
        float j = -32.0f;
        float k = -32.0f;
        for (l = 0; l < 4; ++l) {
            m = Float.intBitsToFloat(is[l * 8]);
            float n = Float.intBitsToFloat(is[l * 8 + 1]);
            float o = Float.intBitsToFloat(is[l * 8 + 2]);
            f = Math.min(f, m);
            g = Math.min(g, n);
            h = Math.min(h, o);
            i = Math.max(i, m);
            j = Math.max(j, n);
            k = Math.max(k, o);
        }
        if (fs != null) {
            fs[Direction.WEST.get3DDataValue()] = f;
            fs[Direction.EAST.get3DDataValue()] = i;
            fs[Direction.DOWN.get3DDataValue()] = g;
            fs[Direction.UP.get3DDataValue()] = j;
            fs[Direction.NORTH.get3DDataValue()] = h;
            fs[Direction.SOUTH.get3DDataValue()] = k;
            l = DIRECTIONS.length;
            fs[Direction.WEST.get3DDataValue() + l] = 1.0f - f;
            fs[Direction.EAST.get3DDataValue() + l] = 1.0f - i;
            fs[Direction.DOWN.get3DDataValue() + l] = 1.0f - g;
            fs[Direction.UP.get3DDataValue() + l] = 1.0f - j;
            fs[Direction.NORTH.get3DDataValue() + l] = 1.0f - h;
            fs[Direction.SOUTH.get3DDataValue() + l] = 1.0f - k;
        }
        switch (direction) {
            case DOWN -> {
                bitSet.set(1, f >= 1.0E-4f || h >= 1.0E-4f || i <= 0.9999f || k <= 0.9999f);
                bitSet.set(0, g == j && (g < 1.0E-4f || blockState.isCollisionShapeFullBlock(blockAndTintGetter, blockPos)));
            }
            case UP -> {
                bitSet.set(1, f >= 1.0E-4f || h >= 1.0E-4f || i <= 0.9999f || k <= 0.9999f);
                bitSet.set(0, g == j && (j > 0.9999f || blockState.isCollisionShapeFullBlock(blockAndTintGetter, blockPos)));
            }
            case NORTH -> {
                bitSet.set(1, f >= 1.0E-4f || g >= 1.0E-4f || i <= 0.9999f || j <= 0.9999f);
                bitSet.set(0, h == k && (h < 1.0E-4f || blockState.isCollisionShapeFullBlock(blockAndTintGetter, blockPos)));
            }
            case SOUTH -> {
                bitSet.set(1, f >= 1.0E-4f || g >= 1.0E-4f || i <= 0.9999f || j <= 0.9999f);
                bitSet.set(0, h == k && (k > 0.9999f || blockState.isCollisionShapeFullBlock(blockAndTintGetter, blockPos)));
            }
            case WEST -> {
                bitSet.set(1, g >= 1.0E-4f || h >= 1.0E-4f || j <= 0.9999f || k <= 0.9999f);
                bitSet.set(0, f == i && (f < 1.0E-4f || blockState.isCollisionShapeFullBlock(blockAndTintGetter, blockPos)));
            }
            case EAST -> {
                bitSet.set(1, g >= 1.0E-4f || h >= 1.0E-4f || j <= 0.9999f || k <= 0.9999f);
                bitSet.set(0, f == i && (i > 0.9999f || blockState.isCollisionShapeFullBlock(blockAndTintGetter, blockPos)));
            }
        }
    }

    private void renderModelFaceFlat(BlockAndTintGetter blockAndTintGetter, BlockState blockState, BlockPos blockPos, int i, int j, boolean bl, PoseStack poseStack, VertexConsumer vertexConsumer, List<BakedQuad> list, BitSet bitSet) {
        for (BakedQuad bakedQuad : list) {
            if (bl) {
                this.calculateShape(blockAndTintGetter, blockState, blockPos, bakedQuad.getVertices(), bakedQuad.getDirection(), null, bitSet);
                BlockPos blockPos2 = bitSet.get(0) ? blockPos.relative(bakedQuad.getDirection()) : blockPos;
                i = LevelRenderer.getLightColor(blockAndTintGetter, blockState, blockPos2);
            }
            float f = blockAndTintGetter.getShade(bakedQuad.getDirection(), bakedQuad.isShade());
            this.putQuadData(blockAndTintGetter, blockState, blockPos, vertexConsumer, poseStack.last(), bakedQuad, f, f, f, f, i, i, i, i, j);
        }
    }
}