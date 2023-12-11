package net.orcinus.galosphere.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.*;
import net.orcinus.galosphere.init.GBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
        this.getVariantBuilder(GBlocks.POTPOURRI.get()).forAllStates(blockState -> {
            ModelFile modelFile = models().getExistingFile(new ResourceLocation(Galosphere.MODID, "block/potpourri"));
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

        this.simpleBlock(GBlocks.PINK_SALT.get());
        this.simpleBlock(GBlocks.ROSE_PINK_SALT.get());
        this.simpleBlock(GBlocks.PASTEL_PINK_SALT.get());

        this.slabBlock(GBlocks.PINK_SALT_SLAB.get(), "pink_salt");
        this.slabBlock(GBlocks.ROSE_PINK_SALT_SLAB.get(), "rose_pink_salt");
        this.slabBlock(GBlocks.PASTEL_PINK_SALT_SLAB.get(), "pastel_pink_salt");

        this.stairsBlock(GBlocks.PINK_SALT_STAIRS.get(), "pink_salt");
        this.stairsBlock(GBlocks.ROSE_PINK_SALT_STAIRS.get(), "rose_pink_salt");
        this.stairsBlock(GBlocks.PASTEL_PINK_SALT_STAIRS.get(), "pastel_pink_salt");

        this.simpleBlock(GBlocks.POLISHED_PINK_SALT.get());
        this.simpleBlock(GBlocks.POLISHED_ROSE_PINK_SALT.get());
        this.simpleBlock(GBlocks.POLISHED_PASTEL_PINK_SALT.get());

        this.slabBlock(GBlocks.POLISHED_PINK_SALT_SLAB.get(), "polished_pink_salt");
        this.slabBlock(GBlocks.POLISHED_ROSE_PINK_SALT_SLAB.get(), "polished_rose_pink_salt");
        this.slabBlock(GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB.get(), "polished_pastel_pink_salt");

        this.stairsBlock(GBlocks.POLISHED_PINK_SALT_STAIRS.get(), "polished_pink_salt");
        this.stairsBlock(GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS.get(), "polished_rose_pink_salt");
        this.stairsBlock(GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS.get(), "polished_pastel_pink_salt");

        this.simpleBlock(GBlocks.PINK_SALT_BRICKS.get());
        this.simpleBlock(GBlocks.ROSE_PINK_SALT_BRICKS.get());
        this.simpleBlock(GBlocks.PASTEL_PINK_SALT_BRICKS.get());

        this.slabBlock(GBlocks.PINK_SALT_BRICK_SLAB.get(), "pink_salt_bricks");
        this.slabBlock(GBlocks.ROSE_PINK_SALT_BRICK_SLAB.get(), "rose_pink_salt_bricks");
        this.slabBlock(GBlocks.PASTEL_PINK_SALT_BRICK_SLAB.get(), "pastel_pink_salt_bricks");

        this.stairsBlock(GBlocks.PINK_SALT_BRICK_STAIRS.get(), "pink_salt_bricks");
        this.stairsBlock(GBlocks.ROSE_PINK_SALT_BRICK_STAIRS.get(), "rose_pink_salt_bricks");
        this.stairsBlock(GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS.get(), "pastel_pink_salt_bricks");

        this.simpleBlock(GBlocks.CURED_MEMBRANE_BLOCK.get());
        this.getVariantBuilder(GBlocks.SHADOW_FRAME.get()).forAllStatesExcept(state -> {
            String name = state.getValue(ShadowFrameBlock.POWERED) ? "shadow_frame_filled" : "shadow_frame";
            return ConfiguredModel.builder().modelFile(models().cubeAll(name, new ResourceLocation(Galosphere.MODID, "block/" + name)).renderType("cutout")).build();
        }, BlockStateProperties.WATERLOGGED, BlockStateProperties.LEVEL, ShadowFrameBlock.FILLED);

        this.getVariantBuilder(GBlocks.STRANDED_MEMBRANE_BLOCK.get()).forAllStatesExcept(blockState -> {
            int x = 0;
            int y = 0;
            Direction direction = blockState.getValue(DirectionalBlock.FACING);
            if (direction == Direction.DOWN) {
                x = 180;
            } else if (direction == Direction.EAST) {
                x = 90;
                y = 90;
            } else if (direction == Direction.SOUTH) {
                x = 90;
                y = 180;
            } else if (direction == Direction.NORTH) {
                x = 90;
            } else if (direction == Direction.WEST) {
                x = 90;
                y = 270;
            }
            return ConfiguredModel.builder().rotationX(x).rotationY(y).modelFile(models().cubeBottomTop("stranded_membrane_block", new ResourceLocation(Galosphere.MODID, "block/stranded_membrane_block_side"), new ResourceLocation(Galosphere.MODID, "block/stranded_membrane_block_bottom"), new ResourceLocation(Galosphere.MODID, "block/stranded_membrane_block_top")).renderType("translucent")).build();
        }, BlockStateProperties.WATERLOGGED);

        this.getVariantBuilder(GBlocks.PINK_SALT_CHAMBER.get()).forAllStates(state -> {
            String name = state.getValue(PinkSaltChamberBlock.CHARGED) ? "charged_pink_salt_chamber" : "pink_salt_chamber";
            return ConfiguredModel.builder().modelFile(models().cubeAll(name, new ResourceLocation(Galosphere.MODID, "block/" + name))).build();
        });

        this.wallBlock(GBlocks.PINK_SALT_WALL.get(), "pink_salt");
        this.wallBlock(GBlocks.ROSE_PINK_SALT_WALL.get(), "rose_pink_salt");
        this.wallBlock(GBlocks.PASTEL_PINK_SALT_WALL.get(), "pastel_pink_salt");
        this.wallBlock(GBlocks.POLISHED_PINK_SALT_WALL.get(), "polished_pink_salt");
        this.wallBlock(GBlocks.POLISHED_ROSE_PINK_SALT_WALL.get(), "polished_rose_pink_salt");
        this.wallBlock(GBlocks.POLISHED_PASTEL_PINK_SALT_WALL.get(), "polished_pastel_pink_salt");
        this.wallBlock(GBlocks.PINK_SALT_BRICK_WALL.get(), "pink_salt_bricks");
        this.wallBlock(GBlocks.ROSE_PINK_SALT_BRICK_WALL.get(), "rose_pink_salt_bricks");
        this.wallBlock(GBlocks.PASTEL_PINK_SALT_BRICK_WALL.get(), "pastel_pink_salt_bricks");
        this.getVariantBuilder(GBlocks.GILDED_BEADS.get()).forAllStatesExcept(state -> {
            return ConfiguredModel.builder().modelFile(models().sign("gilded_beads", new ResourceLocation("block/gold_block"))).build();
        }, BlockStateProperties.ROTATION_16, BlockStateProperties.WATERLOGGED, BlockStateProperties.BOTTOM);
        this.getVariantBuilder(GBlocks.SILVER_BALANCE.get()).forAllStatesExcept(state -> {
            return ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(Galosphere.MODID, "block/silver_balance"))).build();
        }, BlockStateProperties.WATERLOGGED);
        this.getVariantBuilder(GBlocks.PINK_SALT_STRAW.get()).forAllStatesExcept(state -> {
            String name = "pink_salt_straw_" + state.getValue(PinkSaltStrawBlock.TIP_DIRECTION).getName() + "_" + state.getValue(PinkSaltStrawBlock.STRAW_SHAPE);
            return ConfiguredModel.builder().modelFile(models().withExistingParent(name, "block/pointed_dripstone").renderType("cutout").texture("cross", "block/" + name)).build();
        }, BlockStateProperties.WATERLOGGED, PinkSaltStrawBlock.FALLABLE);
        this.getVariantBuilder(GBlocks.PINK_SALT_CLUSTER.get()).forAllStatesExcept(state -> {
            Direction facing = state.getValue(BlockStateProperties.FACING);
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
            return ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(Galosphere.MODID, "block/pink_salt_cluster"))).rotationX(rotationX).rotationY(rotationY).build();
        }, BlockStateProperties.WATERLOGGED);
        this.getVariantBuilder(GBlocks.PINK_SALT_LAMP.get()).forAllStatesExcept(state -> {
            Direction facing = state.getValue(BlockStateProperties.FACING);
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
            return ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(Galosphere.MODID, "block/pink_salt_lamp"))).rotationX(rotationX).rotationY(rotationY).build();
        }, BlockStateProperties.WATERLOGGED);
    }

    private void wallBlock(Block block, String name) {
        String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
        this.wallBlock((WallBlock) block, new ResourceLocation(Galosphere.MODID, "block/" + name));
        this.itemModels().getBuilder(path).parent(this.models().wallInventory(path + "_inventory", blockTexture(ForgeRegistries.BLOCKS.getValue(modLoc(name)))));
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
