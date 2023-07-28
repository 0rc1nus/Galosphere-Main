package net.orcinus.galosphere.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.CordycepsBlock;
import net.orcinus.galosphere.blocks.LichenMossBlock;
import net.orcinus.galosphere.blocks.PollinatedClusterBlock;
import net.orcinus.galosphere.init.GBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public class GBlockstateProvider extends BlockStateProvider {

    public GBlockstateProvider(PackOutput packOutput, ExistingFileHelper exFileHelper) {
        super(packOutput, Galosphere.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(GBlocks.ALLURITE_BLOCK.get());
        this.simpleBlock(GBlocks.LUMIERE_BLOCK.get());
        this.simpleBlock(GBlocks.SMOOTH_AMETHYST.get());
        this.simpleBlock(GBlocks.SMOOTH_ALLURITE.get());
        this.simpleBlock(GBlocks.SMOOTH_LUMIERE.get());
        this.simpleBlock(GBlocks.AMETHYST_BRICKS.get());
        this.simpleBlock(GBlocks.ALLURITE_BRICKS.get());
        this.simpleBlock(GBlocks.LUMIERE_BRICKS.get());
        this.simpleBlock(GBlocks.CHISELED_AMETHYST.get());
        this.simpleBlock(GBlocks.CHISELED_ALLURITE.get());
        this.simpleBlock(GBlocks.CHISELED_LUMIERE.get());
        this.simpleBlock(GBlocks.AMETHYST_LAMP.get());
        this.simpleBlock(GBlocks.ALLURITE_LAMP.get());
        this.simpleBlock(GBlocks.LUMIERE_LAMP.get());
        this.simpleBlock(GBlocks.RAW_SILVER_BLOCK.get());
        this.simpleBlock(GBlocks.SILVER_BLOCK.get());
        this.simpleBlock(GBlocks.SILVER_ORE.get());
        this.simpleBlock(GBlocks.DEEPSLATE_SILVER_ORE.get());
        this.simpleBlock(GBlocks.CHARGED_LUMIERE_BLOCK.get());
        this.getVariantBuilder(GBlocks.LICHEN_MOSS.get()).forAllStatesExcept(blockState -> {
            String name = blockState.getValue(LichenMossBlock.LIT) ? "lichen_moss_lit" : "lichen_moss";
            ModelFile modelFile = models().cubeAll(name, new ResourceLocation(Galosphere.MODID, "block/" + name));
            return ConfiguredModel.builder().modelFile(modelFile).build();
        });

        this.crossBlock(GBlocks.LICHEN_ROOTS);
        this.crossBlock(GBlocks.BOWL_LICHEN);
        this.crossBlock(GBlocks.LICHEN_CORDYCEPS_PLANT);

        this.slabBlock(GBlocks.AMETHYST_SLAB.get(), "amethyst_block", true);
        this.slabBlock(GBlocks.ALLURITE_SLAB.get(), "allurite_block");
        this.slabBlock(GBlocks.LUMIERE_SLAB.get(), "lumiere_block");
        this.slabBlock(GBlocks.SMOOTH_AMETHYST_SLAB.get(), "smooth_amethyst");
        this.slabBlock(GBlocks.SMOOTH_ALLURITE_SLAB.get(), "smooth_allurite");
        this.slabBlock(GBlocks.SMOOTH_LUMIERE_SLAB.get(), "smooth_lumiere");
        this.slabBlock(GBlocks.AMETHYST_BRICK_SLAB.get(), "amethyst_bricks");
        this.slabBlock(GBlocks.ALLURITE_BRICK_SLAB.get(), "allurite_bricks");
        this.slabBlock(GBlocks.LUMIERE_BRICK_SLAB.get(), "lumiere_bricks");

        this.stairsBlock(GBlocks.AMETHYST_STAIRS.get(), "amethyst_block", true);
        this.stairsBlock(GBlocks.ALLURITE_STAIRS.get(), "allurite_block");
        this.stairsBlock(GBlocks.LUMIERE_STAIRS.get(), "lumiere_block");
        this.stairsBlock(GBlocks.SMOOTH_AMETHYST_STAIRS.get(), "smooth_amethyst");
        this.stairsBlock(GBlocks.SMOOTH_ALLURITE_STAIRS.get(), "smooth_allurite");
        this.stairsBlock(GBlocks.SMOOTH_LUMIERE_STAIRS.get(), "smooth_lumiere");
        this.stairsBlock(GBlocks.AMETHYST_BRICK_STAIRS.get(), "amethyst_bricks");
        this.stairsBlock(GBlocks.ALLURITE_BRICK_STAIRS.get(), "allurite_bricks");
        this.stairsBlock(GBlocks.LUMIERE_BRICK_STAIRS.get(), "lumiere_bricks");

        this.getVariantBuilder(GBlocks.COMBUSTION_TABLE.get()).forAllStates(state -> {
            return ConfiguredModel.builder().modelFile(models().cube("combustion_table", new ResourceLocation(Galosphere.MODID, "block/combustion_table_bottom"), new ResourceLocation(Galosphere.MODID, "block/combustion_table_top"), new ResourceLocation(Galosphere.MODID, "block/combustion_table_side2"), new ResourceLocation(Galosphere.MODID, "block/combustion_table_side2"), new ResourceLocation(Galosphere.MODID, "block/combustion_table_side1"), new ResourceLocation(Galosphere.MODID, "block/combustion_table_side0")).texture("particle", new ResourceLocation(Galosphere.MODID, "block/combustion_table_side2"))).build();
        });

        this.pollinatedCluster(GBlocks.ALLURITE_CLUSTER.get());
        this.pollinatedCluster(GBlocks.LUMIERE_CLUSTER.get());
        this.pollinatedCluster(GBlocks.GLINTED_ALLURITE_CLUSTER.get());
        this.pollinatedCluster(GBlocks.GLINTED_LUMIERE_CLUSTER.get());
        this.pollinatedCluster(GBlocks.GLINTED_AMETHYST_CLUSTER.get());

        this.simpleBlock(GBlocks.SILVER_TILES.get());
        this.slabBlock(GBlocks.SILVER_TILES_SLAB.get(), "silver_tiles");
        this.stairsBlock(GBlocks.SILVER_TILES_STAIRS.get(), "silver_tiles");

        this.simpleBlock(GBlocks.SILVER_PANEL.get());
        this.slabBlock(GBlocks.SILVER_PANEL_SLAB.get(), "silver_panel");
        this.stairsBlock(GBlocks.SILVER_PANEL_STAIRS.get(), "silver_panel");

    }

    private void pollinatedCluster(@NotNull Block block) {
        this.getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(AmethystClusterBlock.FACING);
            int rotationX = 90;
            int rotationY = 90;
            if (facing == Direction.UP) {
                rotationX = 0;
                rotationY = 0;
            } else if (facing == Direction.DOWN) {
                rotationX = 180;
                rotationY = 0;
            } else if (facing == Direction.WEST) {
                rotationY *= 3;
            } else if (facing == Direction.NORTH) {
                rotationY = 0;
            } else if (facing == Direction.SOUTH) {
                rotationY *= 2;
            }
            String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
            if (state.hasProperty(PollinatedClusterBlock.POLLINATED) && state.getValue(PollinatedClusterBlock.POLLINATED)) {
                path = "glinted_" + path;
            }
            return ConfiguredModel.builder()
                    .modelFile(models().cross(path, new ResourceLocation(Galosphere.MODID, "block/" + path)).renderType("cutout"))
                    .rotationX(rotationX)
                    .rotationY(rotationY)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }

    private void crossBlock(RegistryObject<Block> block) {
        this.getVariantBuilder(block.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(models().cross(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath(), new ResourceLocation(Galosphere.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath())).renderType("cutout")).build());
    }

    private void stairsBlock(Block block, String blockMaterial) {
        this.stairsBlock(block, blockMaterial, false);
    }

    private void stairsBlock(Block block, String blockMaterial, boolean flag) {
        String id = flag ? "minecraft" : Galosphere.MODID;
        ResourceLocation resourceLocation = new ResourceLocation(id, "block/" + blockMaterial);
        this.stairsBlock((StairBlock) block, resourceLocation);
    }

    private void slabBlock(Block block, String blockMaterial) {
        this.slabBlock(block, blockMaterial, false);
    }

    private void slabBlock(Block block, String blockMaterial, boolean flag) {
        String id = flag ? "minecraft" : Galosphere.MODID;
        ResourceLocation resourceLocation = new ResourceLocation(id, "block/" + blockMaterial);
        this.slabBlock((SlabBlock) block, resourceLocation, resourceLocation);
    }

}
