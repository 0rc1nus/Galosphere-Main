package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.PotpourriBlock;
import net.orcinus.galosphere.blocks.SilverBalanceBlock;
import net.orcinus.galosphere.blocks.StrandedMembraneBlock;
import net.orcinus.galosphere.blocks.ChandelierBlock;
import net.orcinus.galosphere.blocks.CombustionTableBlock;
import net.orcinus.galosphere.blocks.CordycepsBlock;
import net.orcinus.galosphere.blocks.CordycepsPlantBlock;
import net.orcinus.galosphere.blocks.CrystalSlabBlock;
import net.orcinus.galosphere.blocks.CrystalStairsBlock;
import net.orcinus.galosphere.blocks.GildedBeadsBlock;
import net.orcinus.galosphere.blocks.GlintedClusterBlock;
import net.orcinus.galosphere.blocks.GlowInkClumpsBlock;
import net.orcinus.galosphere.blocks.LichenMossBlock;
import net.orcinus.galosphere.blocks.LichenMushroomBlock;
import net.orcinus.galosphere.blocks.LichenRootsBlock;
import net.orcinus.galosphere.blocks.LumiereBlock;
import net.orcinus.galosphere.blocks.LumiereComposterBlock;
import net.orcinus.galosphere.blocks.MonstrometerBlock;
import net.orcinus.galosphere.blocks.PinkSaltBlock;
import net.orcinus.galosphere.blocks.PinkSaltClusterBlock;
import net.orcinus.galosphere.blocks.PinkSaltLampBlock;
import net.orcinus.galosphere.blocks.PinkSaltSlabBlock;
import net.orcinus.galosphere.blocks.PinkSaltStairsBlock;
import net.orcinus.galosphere.blocks.PinkSaltStrawBlock;
import net.orcinus.galosphere.blocks.PinkSaltWallBlock;
import net.orcinus.galosphere.blocks.PollinatedClusterBlock;
import net.orcinus.galosphere.blocks.RotatablePinkSaltBlock;
import net.orcinus.galosphere.blocks.ShadowFrameBlock;
import net.orcinus.galosphere.blocks.SilverLatticeBlock;
import net.orcinus.galosphere.blocks.SilverLatticeVineBlock;
import net.orcinus.galosphere.blocks.SoilComposterBlock;
import net.orcinus.galosphere.blocks.SucculentBlock;
import net.orcinus.galosphere.blocks.SucculentCropBlock;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;

import java.util.Map;

public class GBlocks {

    public static final Map<ResourceLocation, Block> BLOCKS = Maps.newLinkedHashMap();
    public static final Map<ResourceLocation, Item> BLOCK_ITEMS = Maps.newLinkedHashMap();

    public static final Block MONSTROMETER = registerBlock("monstrometer", new MonstrometerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).lightLevel(state -> MonstrometerBlock.isActive(state) ? 4 : 0).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(GSoundEvents.MONSTROMETER)));
    public static final Block COMBUSTION_TABLE = registerBlock("combustion_table", new CombustionTableBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(GSoundEvents.COMBUSTION_TABLE).requiresCorrectToolForDrops()));
    public static final Block WARPED_ANCHOR = registerBlock("warped_anchor", new WarpedAnchorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).lightLevel(state -> state.getValue(WarpedAnchorBlock.WARPED_CHARGE) * 3).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(GSoundEvents.SILVER)));

    public static final Block SILVER_ORE = registerBlock("silver_ore", new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Block DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", new DropExperienceBlock(BlockBehaviour.Properties.copy(SILVER_ORE).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
    public static final Block RAW_SILVER_BLOCK = registerBlock("raw_silver_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final Block SILVER_BLOCK = registerBlock("silver_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(GSoundEvents.SILVER)));
    public static final Block CHANDELIER = registerNoTabBlock("chandelier", new ChandelierBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.5f).sound(SoundType.LANTERN).lightLevel(ChandelierBlock::getLightEmission).noOcclusion()));

    public static final Block SILVER_TILES = registerBlock("silver_tiles", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).requiresCorrectToolForDrops().sound(GSoundEvents.SILVER).strength(3.0F, 6.0F)));
    public static final Block SILVER_TILES_STAIRS = registerBlock("silver_tiles_stairs", new StairBlock(SILVER_TILES.defaultBlockState(), BlockBehaviour.Properties.copy(SILVER_TILES)));
    public static final Block SILVER_TILES_SLAB = registerBlock("silver_tiles_slab", new SlabBlock(BlockBehaviour.Properties.copy(SILVER_TILES)));

    public static final Block SILVER_PANEL = registerBlock("silver_panel", new Block(BlockBehaviour.Properties.copy(SILVER_TILES)));
    public static final Block SILVER_PANEL_STAIRS = registerBlock("silver_panel_stairs", new StairBlock(SILVER_PANEL.defaultBlockState(), BlockBehaviour.Properties.copy(SILVER_TILES)));
    public static final Block SILVER_PANEL_SLAB = registerBlock("silver_panel_slab", new SlabBlock(BlockBehaviour.Properties.copy(SILVER_PANEL)));

    public static final Block SILVER_LATTICE = registerBlock("silver_lattice", new SilverLatticeBlock(BlockBehaviour.Properties.copy(SILVER_TILES).sound(GSoundEvents.SILVER_LATTICE).noOcclusion()));
    public static final Block GLOW_BERRIES_SILVER_LATTICE = registerNoTabBlock("glow_berries_silver_lattice", new SilverLatticeVineBlock(BlockBehaviour.Properties.copy(SILVER_TILES).sound(GSoundEvents.SILVER_LATTICE).lightLevel(CaveVines.emission(14)).noOcclusion()));

    public static final Block ALLURITE_BLOCK = registerBlock("allurite_block", new AmethystBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.5F).sound(GSoundEvents.ALLURITE).requiresCorrectToolForDrops()));
    public static final Block LUMIERE_BLOCK = registerBlock("lumiere_block", new LumiereBlock(false, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 0).strength(1.5F).sound(GSoundEvents.LUMIERE).requiresCorrectToolForDrops()));
    public static final Block CHARGED_LUMIERE_BLOCK = registerBlock("charged_lumiere_block", new LumiereBlock(true, BlockBehaviour.Properties.copy(LUMIERE_BLOCK).lightLevel(state -> 6).requiresCorrectToolForDrops()));

    public static final Block ALLURITE_CLUSTER = registerBlock("allurite_cluster", new PollinatedClusterBlock(() -> GParticleTypes.ALLURITE_RAIN, BlockBehaviour.Properties.of().noOcclusion().randomTicks().sound(GSoundEvents.ALLURITE_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));
    public static final Block LUMIERE_CLUSTER = registerBlock("lumiere_cluster", new PollinatedClusterBlock(() -> GParticleTypes.LUMIERE_RAIN, BlockBehaviour.Properties.of().noOcclusion().randomTicks().sound(GSoundEvents.LUMIERE_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));

    public static final Block GLINTED_ALLURITE_CLUSTER = registerBlock("glinted_allurite_cluster", new GlintedClusterBlock(() -> GParticleTypes.ALLURITE_RAIN, BlockBehaviour.Properties.of().noOcclusion().randomTicks().sound(GSoundEvents.ALLURITE_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));
    public static final Block GLINTED_LUMIERE_CLUSTER = registerBlock("glinted_lumiere_cluster", new GlintedClusterBlock(() -> GParticleTypes.LUMIERE_RAIN, BlockBehaviour.Properties.of().noOcclusion().randomTicks().sound(GSoundEvents.LUMIERE_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));
    public static final Block GLINTED_AMETHYST_CLUSTER = registerBlock("glinted_amethyst_cluster", new GlintedClusterBlock(() -> GParticleTypes.AMETHYST_RAIN, BlockBehaviour.Properties.of().noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));

    public static final Block AMETHYST_STAIRS = registerBlock("amethyst_stairs", new CrystalStairsBlock(Blocks.AMETHYST_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block AMETHYST_SLAB = registerBlock("amethyst_slab", new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));

    public static final Block ALLURITE_STAIRS = registerBlock("allurite_stairs", new CrystalStairsBlock(ALLURITE_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(ALLURITE_BLOCK)));
    public static final Block ALLURITE_SLAB = registerBlock("allurite_slab", new CrystalSlabBlock(BlockBehaviour.Properties.copy(ALLURITE_BLOCK)));

    public static final Block LUMIERE_STAIRS = registerBlock("lumiere_stairs", new CrystalStairsBlock(LUMIERE_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(LUMIERE_BLOCK)));
    public static final Block LUMIERE_SLAB = registerBlock("lumiere_slab", new CrystalSlabBlock(BlockBehaviour.Properties.copy(LUMIERE_BLOCK)));

    public static final Block SMOOTH_AMETHYST = registerBlock("smooth_amethyst", new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block SMOOTH_AMETHYST_STAIRS = registerBlock("smooth_amethyst_stairs", new CrystalStairsBlock(SMOOTH_AMETHYST.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block SMOOTH_AMETHYST_SLAB = registerBlock("smooth_amethyst_slab", new CrystalSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_AMETHYST)));

    public static final Block SMOOTH_ALLURITE = registerBlock("smooth_allurite", new AmethystBlock(BlockBehaviour.Properties.copy(ALLURITE_BLOCK)));
    public static final Block SMOOTH_ALLURITE_STAIRS = registerBlock("smooth_allurite_stairs", new CrystalStairsBlock(SMOOTH_ALLURITE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block SMOOTH_ALLURITE_SLAB = registerBlock("smooth_allurite_slab", new CrystalSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_ALLURITE)));

    public static final Block SMOOTH_LUMIERE = registerBlock("smooth_lumiere", new AmethystBlock(BlockBehaviour.Properties.copy(LUMIERE_BLOCK)));
    public static final Block SMOOTH_LUMIERE_STAIRS = registerBlock("smooth_lumiere_stairs", new CrystalStairsBlock(SMOOTH_LUMIERE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block SMOOTH_LUMIERE_SLAB = registerBlock("smooth_lumiere_slab", new CrystalSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_LUMIERE)));

    public static final Block AMETHYST_BRICKS = registerBlock("amethyst_bricks", new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block AMETHYST_BRICK_STAIRS = registerBlock("amethyst_brick_stairs", new CrystalStairsBlock(AMETHYST_BRICKS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block AMETHYST_BRICK_SLAB = registerBlock("amethyst_brick_slab", new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));

    public static final Block ALLURITE_BRICKS = registerBlock("allurite_bricks", new AmethystBlock(BlockBehaviour.Properties.copy(ALLURITE_BLOCK)));
    public static final Block ALLURITE_BRICK_STAIRS = registerBlock("allurite_brick_stairs", new CrystalStairsBlock(ALLURITE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block ALLURITE_BRICK_SLAB = registerBlock("allurite_brick_slab", new CrystalSlabBlock(BlockBehaviour.Properties.copy(ALLURITE_BRICKS)));

    public static final Block LUMIERE_BRICKS = registerBlock("lumiere_bricks", new AmethystBlock(BlockBehaviour.Properties.copy(LUMIERE_BLOCK)));
    public static final Block LUMIERE_BRICK_STAIRS = registerBlock("lumiere_brick_stairs", new CrystalStairsBlock(LUMIERE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block LUMIERE_BRICK_SLAB = registerBlock("lumiere_brick_slab", new CrystalSlabBlock(BlockBehaviour.Properties.copy(LUMIERE_BRICKS)));

    public static final Block CHISELED_AMETHYST = registerBlock("chiseled_amethyst", new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block CHISELED_ALLURITE = registerBlock("chiseled_allurite", new AmethystBlock(BlockBehaviour.Properties.copy(ALLURITE_BLOCK)));
    public static final Block CHISELED_LUMIERE = registerBlock("chiseled_lumiere", new AmethystBlock(BlockBehaviour.Properties.copy(LUMIERE_BLOCK)));

    public static final Block AMETHYST_LAMP = registerBlock("amethyst_lamp", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 15).strength(0.3F).sound(SoundType.AMETHYST)));
    public static final Block ALLURITE_LAMP = registerBlock("allurite_lamp", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 15).strength(0.3F).sound(GSoundEvents.ALLURITE)));
    public static final Block LUMIERE_LAMP = registerBlock("lumiere_lamp", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).lightLevel(state -> 15).strength(0.3F).sound(GSoundEvents.LUMIERE)));

    public static final Block LICHEN_MOSS = registerBlock("lichen_moss", new LichenMossBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.1f).sound(GSoundEvents.LICHEN_MOSS).lightLevel(state -> state.getValue(LichenMossBlock.LIT) ? 12 : 0)));
    public static final Block LICHEN_ROOTS = registerBlock("lichen_roots", new LichenRootsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).noCollission().instabreak().sound(GSoundEvents.LICHEN_ROOTS).offsetType(BlockBehaviour.OffsetType.XZ).replaceable()));
    public static final Block BOWL_LICHEN = registerBlock("bowl_lichen", new LichenMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instabreak().noCollission().sound(GSoundEvents.BOWL_LICHEN)));
    public static final Block LICHEN_SHELF = registerBlock("lichen_shelf", new BaseCoralWallFanBlock(BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL_WALL_FAN).noCollission().sound(GSoundEvents.LICHEN_SHELF)));
    public static final Block LICHEN_CORDYCEPS = registerNoTabBlock("lichen_cordyceps", new CordycepsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).lightLevel(state -> state.getValue(CordycepsBlock.BULB) ? 15 : 0).instabreak().noCollission().sound(GSoundEvents.LICHEN_CORDYCEPS)));
    public static final Block LICHEN_CORDYCEPS_PLANT = registerNoTabBlock("lichen_cordyceps_plant", new CordycepsPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instabreak().noCollission().sound(GSoundEvents.LICHEN_CORDYCEPS)));
    public static final Block GLOW_INK_CLUMPS = registerBlock("glow_ink_clumps", new GlowInkClumpsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().strength(0.2F).sound(GSoundEvents.GLOW_INK_CLUMPS).lightLevel(GlowInkClumpsBlock.emission(15, 8))));

    public static final Block PINK_SALT = registerBlock("pink_salt", new PinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block ROSE_PINK_SALT = registerBlock("rose_pink_salt", new PinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block PASTEL_PINK_SALT = registerBlock("pastel_pink_salt", new PinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block PINK_SALT_STAIRS = registerBlock("pink_salt_stairs", new PinkSaltStairsBlock(PINK_SALT.defaultBlockState(), BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block ROSE_PINK_SALT_STAIRS = registerBlock("rose_pink_salt_stairs", new PinkSaltStairsBlock(ROSE_PINK_SALT.defaultBlockState(), BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block PASTEL_PINK_SALT_STAIRS = registerBlock("pastel_pink_salt_stairs", new PinkSaltStairsBlock(PASTEL_PINK_SALT.defaultBlockState(), BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block PINK_SALT_SLAB = registerBlock("pink_salt_slab", new PinkSaltSlabBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block ROSE_PINK_SALT_SLAB = registerBlock("rose_pink_salt_slab", new PinkSaltSlabBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block PASTEL_PINK_SALT_SLAB = registerBlock("pastel_pink_salt_slab", new PinkSaltSlabBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block PINK_SALT_WALL = registerBlock("pink_salt_wall", new PinkSaltWallBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block ROSE_PINK_SALT_WALL = registerBlock("rose_pink_salt_wall", new PinkSaltWallBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block PASTEL_PINK_SALT_WALL = registerBlock("pastel_pink_salt_wall", new PinkSaltWallBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block POLISHED_PINK_SALT = registerBlock("polished_pink_salt", new PinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block POLISHED_ROSE_PINK_SALT = registerBlock("polished_rose_pink_salt", new PinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block POLISHED_PASTEL_PINK_SALT = registerBlock("polished_pastel_pink_salt", new PinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block POLISHED_PINK_SALT_STAIRS = registerBlock("polished_pink_salt_stairs", new PinkSaltStairsBlock(PINK_SALT.defaultBlockState(), BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block POLISHED_ROSE_PINK_SALT_STAIRS = registerBlock("polished_rose_pink_salt_stairs", new PinkSaltStairsBlock(ROSE_PINK_SALT.defaultBlockState(), BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block POLISHED_PASTEL_PINK_SALT_STAIRS = registerBlock("polished_pastel_pink_salt_stairs", new PinkSaltStairsBlock(PASTEL_PINK_SALT.defaultBlockState(), BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block POLISHED_PINK_SALT_SLAB = registerBlock("polished_pink_salt_slab", new PinkSaltSlabBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block POLISHED_ROSE_PINK_SALT_SLAB = registerBlock("polished_rose_pink_salt_slab", new PinkSaltSlabBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block POLISHED_PASTEL_PINK_SALT_SLAB = registerBlock("polished_pastel_pink_salt_slab", new PinkSaltSlabBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block POLISHED_PINK_SALT_WALL = registerBlock("polished_pink_salt_wall", new PinkSaltWallBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block POLISHED_ROSE_PINK_SALT_WALL = registerBlock("polished_rose_pink_salt_wall", new PinkSaltWallBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block POLISHED_PASTEL_PINK_SALT_WALL = registerBlock("polished_pastel_pink_salt_wall", new PinkSaltWallBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block PINK_SALT_BRICKS = registerBlock("pink_salt_bricks", new PinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block ROSE_PINK_SALT_BRICKS = registerBlock("rose_pink_salt_bricks", new PinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block PASTEL_PINK_SALT_BRICKS = registerBlock("pastel_pink_salt_bricks", new PinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block PINK_SALT_BRICK_STAIRS = registerBlock("pink_salt_brick_stairs", new PinkSaltStairsBlock(PINK_SALT.defaultBlockState(), BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block ROSE_PINK_SALT_BRICK_STAIRS = registerBlock("rose_pink_salt_brick_stairs", new PinkSaltStairsBlock(ROSE_PINK_SALT.defaultBlockState(), BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block PASTEL_PINK_SALT_BRICK_STAIRS = registerBlock("pastel_pink_salt_brick_stairs", new PinkSaltStairsBlock(PASTEL_PINK_SALT.defaultBlockState(), BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block PINK_SALT_BRICK_SLAB = registerBlock("pink_salt_brick_slab", new PinkSaltSlabBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block ROSE_PINK_SALT_BRICK_SLAB = registerBlock("rose_pink_salt_brick_slab", new PinkSaltSlabBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block PASTEL_PINK_SALT_BRICK_SLAB = registerBlock("pastel_pink_salt_brick_slab", new PinkSaltSlabBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block PINK_SALT_BRICK_WALL = registerBlock("pink_salt_brick_wall", new PinkSaltWallBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block ROSE_PINK_SALT_BRICK_WALL = registerBlock("rose_pink_salt_brick_wall", new PinkSaltWallBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block PASTEL_PINK_SALT_BRICK_WALL = registerBlock("pastel_pink_salt_brick_wall", new PinkSaltWallBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block CHISELED_PINK_SALT = registerBlock("chiseled_pink_salt", new RotatablePinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block CHISELED_ROSE_PINK_SALT = registerBlock("chiseled_rose_pink_salt", new RotatablePinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));
    public static final Block CHISELED_PASTEL_PINK_SALT = registerBlock("chiseled_pastel_pink_salt", new RotatablePinkSaltBlock(BlockBehaviour.Properties.of().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block PINK_SALT_LAMP = registerBlock("pink_salt_lamp", new PinkSaltLampBlock(BlockBehaviour.Properties.of().noOcclusion().lightLevel(state -> 11).sound(GSoundEvents.PINK_SALT_LAMP).requiresCorrectToolForDrops().strength(3.5f).pushReaction(PushReaction.DESTROY)));

    public static final Block PINK_SALT_STRAW = registerBlock("pink_salt_straw", new PinkSaltStrawBlock(BlockBehaviour.Properties.of().offsetType(BlockBehaviour.OffsetType.XZ).randomTicks().dynamicShape().sound(GSoundEvents.PINK_SALT).requiresCorrectToolForDrops().strength(0.75f)));

    public static final Block PINK_SALT_CLUSTER = registerBlock("pink_salt_cluster", new PinkSaltClusterBlock(BlockBehaviour.Properties.of().lightLevel(state -> 6).sound(GSoundEvents.PINK_SALT_CLUSTER).requiresCorrectToolForDrops().strength(1.0F)));

    public static final Block CURED_MEMBRANE_BLOCK = registerBlock("cured_membrane_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.SLIME_BLOCK)));
    public static final Block STRANDED_MEMBRANE_BLOCK = registerBlock("stranded_membrane_block", new StrandedMembraneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noOcclusion().sound(SoundType.SLIME_BLOCK).isSuffocating((state, world, pos) -> false).isViewBlocking((state, world, pos) -> false)));
    public static final Block SHADOW_FRAME = registerBlock("shadow_frame", new ShadowFrameBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).lightLevel(state -> state.getValue(ShadowFrameBlock.LEVEL)).noCollission().sound(GSoundEvents.SILVER)));
    public static final Block GILDED_BEADS = registerBlock("gilded_beads", new GildedBeadsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).noCollission().sound(GSoundEvents.GILDED_BEADS)));
    public static final Block SILVER_BALANCE = registerBlock("silver_balance", new SilverBalanceBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(3.0F, 6.0F).noOcclusion().sound(GSoundEvents.SILVER)));
    public static final Block SUCCULENT = registerBlock("succulent", new SucculentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.AZALEA)));
    public static final Block POTPOURRI = registerBlock("potpourri", new PotpourriBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final Block LUMIERE_COMPOSTER = registerNoTabBlock("lumiere_composter", new LumiereComposterBlock(BlockBehaviour.Properties.copy(Blocks.COMPOSTER)));
    public static final Block SALINE_COMPOSTER = registerNoTabBlock("saline_composter", new SoilComposterBlock(BlockBehaviour.Properties.copy(Blocks.COMPOSTER)));
    public static final Block POTTED_BOWL_LICHEN = registerNoTabBlock("potted_bowl_lichen", new FlowerPotBlock(BOWL_LICHEN, BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Block POTTED_LICHEN_ROOTS = registerNoTabBlock("potted_lichen_roots", new FlowerPotBlock(LICHEN_ROOTS, BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Block SUCCULENT_CROP = registerNoTabBlock("succulent_crop", new SucculentCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static <B extends Block> B registerBlock(String name, B block) {
        GBlocks.BLOCK_ITEMS.put(Galosphere.id(name), new BlockItem(block, new Item.Properties()));
        return GBlocks.registerNoTabBlock(name, block);
    }

    public static <B extends Block> B registerNoTabBlock(String name, B block) {
        GBlocks.BLOCKS.put(Galosphere.id(name), block);
        return block;
    }

    public static void init() {
        GBlocks.BLOCKS.forEach((resourceLocation, block) -> Registry.register(BuiltInRegistries.BLOCK, resourceLocation, block));
    }

}
