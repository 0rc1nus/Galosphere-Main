package net.orcinus.galosphere.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.GildedBeadsBlock;
import net.orcinus.galosphere.blocks.blockentities.GildedBeadsBlockEntity;
import net.orcinus.galosphere.init.GModelLayers;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class GildedBeadsRenderer implements BlockEntityRenderer<GildedBeadsBlockEntity> {
    public static final Function<BlockState, Material> FUNCTION = state -> new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(Galosphere.MODID, "entity/gilded_beads/" + (state.getValue(BlockStateProperties.BOTTOM) ? "gilded_beads_head" : "gilded_beads_body")));
    private final ModelPart gilded_beads;

    public GildedBeadsRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelPart = context.bakeLayer(GModelLayers.GILDED_BEADS);
        this.gilded_beads = modelPart.getChild("gilded_beads");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("gilded_beads", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void render(GildedBeadsBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-RotationSegment.convertToDegrees(blockEntity.getBlockState().getValue(GildedBeadsBlock.ROTATION))));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        VertexConsumer vertexConsumer = FUNCTION.apply(blockEntity.getBlockState()).buffer(multiBufferSource, RenderType::entityCutoutNoCull);
        this.gilded_beads.render(poseStack, vertexConsumer, 0xF000F0, j);
        poseStack.popPose();
    }

}