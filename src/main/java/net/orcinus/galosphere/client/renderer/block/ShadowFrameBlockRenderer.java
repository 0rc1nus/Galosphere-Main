package net.orcinus.galosphere.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
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
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.ModelData;
import net.orcinus.galosphere.blocks.ShadowFrameBlock;
import net.orcinus.galosphere.blocks.blockentities.ShadowFrameBlockEntity;
import net.orcinus.galosphere.init.GBlocks;

import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ShadowFrameBlockRenderer implements BlockEntityRenderer<ShadowFrameBlockEntity> {
    static final Direction[] DIRECTIONS = Direction.values();
    public final BlockColors blockColors;
    private final BlockRenderDispatcher blockRenderer;

    public ShadowFrameBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
        this.blockColors = this.blockRenderer.getModelRenderer().blockColors;
    }

    @Override
    public void render(ShadowFrameBlockEntity blockEntity, float value, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int j) {
        BlockState state = blockEntity.getCopiedState();
        if (state.isAir()) return;
        BakedModel bakedmodel = this.blockRenderer.getBlockModel(state);
        RenderType rendertype = ItemBlockRenderTypes.getMovingBlockRenderType(state);
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(rendertype);
        this.tesselateBlock(blockEntity.getLevel(), bakedmodel, state, blockEntity.getBlockPos(), poseStack, vertexconsumer, false, RandomSource.create(), state.getSeed(blockEntity.getBlockPos()), j);
    }

    public void tesselateBlock(BlockAndTintGetter p_234380_, BakedModel p_234381_, BlockState p_234382_, BlockPos p_234383_, PoseStack p_234384_, VertexConsumer p_234385_, boolean p_234386_, RandomSource p_234387_, long p_234388_, int p_234389_) {
        tesselateBlock(p_234380_, p_234381_, p_234382_, p_234383_, p_234384_, p_234385_, p_234386_, p_234387_, p_234388_, p_234389_, ModelData.EMPTY, null);
    }

    public void tesselateBlock(BlockAndTintGetter p_234380_, BakedModel p_234381_, BlockState p_234382_, BlockPos p_234383_, PoseStack p_234384_, VertexConsumer p_234385_, boolean p_234386_, RandomSource p_234387_, long p_234388_, int p_234389_, ModelData modelData, net.minecraft.client.renderer.RenderType renderType) {
        boolean flag = Minecraft.useAmbientOcclusion() && p_234382_.getLightEmission(p_234380_, p_234383_) == 0 && p_234381_.useAmbientOcclusion(p_234382_, renderType);
        Vec3 vec3 = p_234382_.getOffset(p_234380_, p_234383_);
        p_234384_.translate(vec3.x, vec3.y, vec3.z);

        try {
            if (flag) {
                this.tesselateWithAO(p_234380_, p_234381_, p_234382_, p_234383_, p_234384_, p_234385_, p_234386_, p_234387_, p_234388_, p_234389_, modelData, renderType);
            } else {
                this.tesselateWithoutAO(p_234380_, p_234381_, p_234382_, p_234383_, p_234384_, p_234385_, p_234386_, p_234387_, p_234388_, p_234389_, modelData, renderType);
            }

        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.forThrowable(throwable, "Tesselating block model");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Block model being tesselated");
            CrashReportCategory.populateBlockDetails(crashreportcategory, p_234380_, p_234383_, p_234382_);
            crashreportcategory.setDetail("Using AO", flag);
            throw new ReportedException(crashreport);
        }
    }

    @Deprecated //Forge: Model data and render type parameter
    public void tesselateWithAO(BlockAndTintGetter p_234391_, BakedModel p_234392_, BlockState p_234393_, BlockPos p_234394_, PoseStack p_234395_, VertexConsumer p_234396_, boolean p_234397_, RandomSource p_234398_, long p_234399_, int p_234400_) {
        tesselateWithAO(p_234391_, p_234392_, p_234393_, p_234394_, p_234395_, p_234396_, p_234397_, p_234398_, p_234399_, p_234400_, ModelData.EMPTY, null);
    }
    public void tesselateWithAO(BlockAndTintGetter world, BakedModel p_111080_, BlockState state, BlockPos pos, PoseStack p_111083_, VertexConsumer p_111084_, boolean bl, RandomSource p_111086_, long p_111087_, int p_111088_, ModelData modelData, net.minecraft.client.renderer.RenderType renderType) {
        float[] afloat = new float[DIRECTIONS.length * 2];
        BitSet bitset = new BitSet(3);
        ModelBlockRenderer.AmbientOcclusionFace modelblockrenderer$ambientocclusionface = new ModelBlockRenderer.AmbientOcclusionFace();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        for(Direction direction : DIRECTIONS) {
            p_111086_.setSeed(p_111087_);
            List<BakedQuad> list = p_111080_.getQuads(state, direction, p_111086_, modelData, renderType);
            if (!list.isEmpty()) {
                blockpos$mutableblockpos.setWithOffset(pos, direction);
                if (!skipRendering(world, state, pos, bl, direction, blockpos$mutableblockpos)) {
                    this.renderModelFaceAO(world, state, pos, p_111083_, p_111084_, list, afloat, bitset, modelblockrenderer$ambientocclusionface, p_111088_);
                }
            }
        }

        p_111086_.setSeed(p_111087_);
        List<BakedQuad> list1 = p_111080_.getQuads(state, null, p_111086_, modelData, renderType);
        if (!list1.isEmpty()) {
            this.renderModelFaceAO(world, state, pos, p_111083_, p_111084_, list1, afloat, bitset, modelblockrenderer$ambientocclusionface, p_111088_);
        }

    }

    private static boolean skipRendering(BlockAndTintGetter world, BlockState state, BlockPos pos, boolean bl, Direction direction, BlockPos.MutableBlockPos blockpos$mutableblockpos) {
        BlockState relativeState = world.getBlockState(blockpos$mutableblockpos);
        if (relativeState.is(GBlocks.SHADOW_FRAME.get()) && relativeState.getValue(ShadowFrameBlock.FILLED)) {
            if (world.getBlockEntity(blockpos$mutableblockpos) instanceof ShadowFrameBlockEntity shadowFrameBlockEntity) {
                return shadowFrameBlockEntity.getCopiedState().isCollisionShapeFullBlock(world, blockpos$mutableblockpos);
            }
            return true;
        }
        return bl && !Block.shouldRenderFace(state, world, pos, direction, blockpos$mutableblockpos);
    }

    @Deprecated
    public void tesselateWithoutAO(BlockAndTintGetter p_234402_, BakedModel p_234403_, BlockState p_234404_, BlockPos p_234405_, PoseStack p_234406_, VertexConsumer p_234407_, boolean p_234408_, RandomSource p_234409_, long p_234410_, int p_234411_) {
        tesselateWithoutAO(p_234402_, p_234403_, p_234404_, p_234405_, p_234406_, p_234407_, p_234408_, p_234409_, p_234410_, p_234411_, ModelData.EMPTY, null);
    }

    public void tesselateWithoutAO(BlockAndTintGetter world, BakedModel model, BlockState state, BlockPos pos, PoseStack p_111095_, VertexConsumer p_111096_, boolean bl, RandomSource p_111098_, long p_111099_, int p_111100_, ModelData modelData, net.minecraft.client.renderer.RenderType renderType) {
        BitSet bitset = new BitSet(3);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        for(Direction direction : DIRECTIONS) {
            p_111098_.setSeed(p_111099_);
            List<BakedQuad> list = model.getQuads(state, direction, p_111098_, modelData, renderType);
            if (!list.isEmpty()) {
                blockpos$mutableblockpos.setWithOffset(pos, direction);
                if (!skipRendering(world, state, pos, bl, direction, blockpos$mutableblockpos)) {
                    int i = LevelRenderer.getLightColor(world, state, blockpos$mutableblockpos);
                    this.renderModelFaceFlat(world, state, pos, i, p_111100_, false, p_111095_, p_111096_, list, bitset);
                }
            }
        }

        p_111098_.setSeed(p_111099_);
        List<BakedQuad> list1 = model.getQuads(state, null, p_111098_, modelData, renderType);
        if (!list1.isEmpty()) {
            this.renderModelFaceFlat(world, state, pos, -1, p_111100_, true, p_111095_, p_111096_, list1, bitset);
        }

    }

    private void renderModelFaceAO(BlockAndTintGetter world, BlockState state, BlockPos pos, PoseStack poseStack, VertexConsumer vertexConsumer, List<BakedQuad> list, float[] p_111019_, BitSet p_111020_, ModelBlockRenderer.AmbientOcclusionFace p_111021_, int p_111022_) {
        for (BakedQuad bakedquad : list) {
            this.calculateShape(world, state, pos, bakedquad.getVertices(), bakedquad.getDirection(), p_111019_, p_111020_);
            if (!ForgeHooksClient.calculateFaceWithoutAO(world, state, pos, bakedquad, p_111020_.get(0), p_111021_.brightness, p_111021_.lightmap))
                p_111021_.calculate(world, state, pos, bakedquad.getDirection(), p_111019_, p_111020_, bakedquad.isShade());
            this.putQuadData(world, state, pos, vertexConsumer, poseStack.last(), bakedquad, p_111021_.brightness[0], p_111021_.brightness[1], p_111021_.brightness[2], p_111021_.brightness[3], p_111021_.lightmap[0], p_111021_.lightmap[1], p_111021_.lightmap[2], p_111021_.lightmap[3], p_111022_);
        }

    }

    private void putQuadData(BlockAndTintGetter world, BlockState state, BlockPos pos, VertexConsumer vertexConsumer, PoseStack.Pose pose, BakedQuad quad, float p_111030_, float p_111031_, float p_111032_, float p_111033_, int p_111034_, int p_111035_, int p_111036_, int p_111037_, int p_111038_) {
        float f;
        float f1;
        float f2;
        if (quad.isTinted()) {
            int i = this.blockColors.getColor(state, world, pos, quad.getTintIndex());
            f = (float)(i >> 16 & 255) / 255.0F;
            f1 = (float)(i >> 8 & 255) / 255.0F;
            f2 = (float)(i & 255) / 255.0F;
        } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
        }

        vertexConsumer.putBulkData(pose, quad, new float[]{p_111030_, p_111031_, p_111032_, p_111033_}, f, f1, f2, new int[]{p_111034_, p_111035_, p_111036_, p_111037_}, p_111038_, true);
    }

    private void calculateShape(BlockAndTintGetter world, BlockState state, BlockPos pos, int[] p_111043_, Direction direction, @Nullable float[] p_111045_, BitSet bitSet) {
        float f = 32.0F;
        float f1 = 32.0F;
        float f2 = 32.0F;
        float f3 = -32.0F;
        float f4 = -32.0F;
        float f5 = -32.0F;

        for(int i = 0; i < 4; ++i) {
            float f6 = Float.intBitsToFloat(p_111043_[i * 8]);
            float f7 = Float.intBitsToFloat(p_111043_[i * 8 + 1]);
            float f8 = Float.intBitsToFloat(p_111043_[i * 8 + 2]);
            f = Math.min(f, f6);
            f1 = Math.min(f1, f7);
            f2 = Math.min(f2, f8);
            f3 = Math.max(f3, f6);
            f4 = Math.max(f4, f7);
            f5 = Math.max(f5, f8);
        }

        if (p_111045_ != null) {
            p_111045_[Direction.WEST.get3DDataValue()] = f;
            p_111045_[Direction.EAST.get3DDataValue()] = f3;
            p_111045_[Direction.DOWN.get3DDataValue()] = f1;
            p_111045_[Direction.UP.get3DDataValue()] = f4;
            p_111045_[Direction.NORTH.get3DDataValue()] = f2;
            p_111045_[Direction.SOUTH.get3DDataValue()] = f5;
            int j = DIRECTIONS.length;
            p_111045_[Direction.WEST.get3DDataValue() + j] = 1.0F - f;
            p_111045_[Direction.EAST.get3DDataValue() + j] = 1.0F - f3;
            p_111045_[Direction.DOWN.get3DDataValue() + j] = 1.0F - f1;
            p_111045_[Direction.UP.get3DDataValue() + j] = 1.0F - f4;
            p_111045_[Direction.NORTH.get3DDataValue() + j] = 1.0F - f2;
            p_111045_[Direction.SOUTH.get3DDataValue() + j] = 1.0F - f5;
        }

        switch (direction) {
            case DOWN -> {
                bitSet.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
                bitSet.set(0, f1 == f4 && (f1 < 1.0E-4F || state.isCollisionShapeFullBlock(world, pos)));
            }
            case UP -> {
                bitSet.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
                bitSet.set(0, f1 == f4 && (f4 > 0.9999F || state.isCollisionShapeFullBlock(world, pos)));
            }
            case NORTH -> {
                bitSet.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
                bitSet.set(0, f2 == f5 && (f2 < 1.0E-4F || state.isCollisionShapeFullBlock(world, pos)));
            }
            case SOUTH -> {
                bitSet.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
                bitSet.set(0, f2 == f5 && (f5 > 0.9999F || state.isCollisionShapeFullBlock(world, pos)));
            }
            case WEST -> {
                bitSet.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
                bitSet.set(0, f == f3 && (f < 1.0E-4F || state.isCollisionShapeFullBlock(world, pos)));
            }
            case EAST -> {
                bitSet.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
                bitSet.set(0, f == f3 && (f3 > 0.9999F || state.isCollisionShapeFullBlock(world, pos)));
            }
        }

    }

    private void renderModelFaceFlat(BlockAndTintGetter p_111002_, BlockState p_111003_, BlockPos p_111004_, int p_111005_, int p_111006_, boolean p_111007_, PoseStack p_111008_, VertexConsumer p_111009_, List<BakedQuad> p_111010_, BitSet p_111011_) {
        for(BakedQuad bakedquad : p_111010_) {
            if (p_111007_) {
                this.calculateShape(p_111002_, p_111003_, p_111004_, bakedquad.getVertices(), bakedquad.getDirection(), (float[])null, p_111011_);
                BlockPos blockpos = p_111011_.get(0) ? p_111004_.relative(bakedquad.getDirection()) : p_111004_;
                p_111005_ = LevelRenderer.getLightColor(p_111002_, p_111003_, blockpos);
            }

            float f = p_111002_.getShade(bakedquad.getDirection(), bakedquad.isShade());
            this.putQuadData(p_111002_, p_111003_, p_111004_, p_111009_, p_111008_.last(), bakedquad, f, f, f, f, p_111005_, p_111005_, p_111005_, p_111005_, p_111006_);
        }

    }

}