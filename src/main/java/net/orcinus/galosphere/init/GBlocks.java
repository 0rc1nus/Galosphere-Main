package net.orcinus.galosphere.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.GlintedClusterBlock;
import net.orcinus.galosphere.blocks.MonstrometerBlock;
import net.orcinus.galosphere.blocks.ChandelierBlock;
import net.orcinus.galosphere.blocks.CombustionTableBlock;
import net.orcinus.galosphere.blocks.CordycepsBlock;
import net.orcinus.galosphere.blocks.CordycepsPlantBlock;
import net.orcinus.galosphere.blocks.CrystalSlabBlock;
import net.orcinus.galosphere.blocks.CrystalStairsBlock;
import net.orcinus.galosphere.blocks.GlowInkClumpsBlock;
import net.orcinus.galosphere.blocks.LichenMossBlock;
import net.orcinus.galosphere.blocks.LichenMushroomBlock;
import net.orcinus.galosphere.blocks.LichenRootsBlock;
import net.orcinus.galosphere.blocks.LumiereBlock;
import net.orcinus.galosphere.blocks.LumiereComposterBlock;
import net.orcinus.galosphere.blocks.PollinatedClusterBlock;
import net.orcinus.galosphere.blocks.SilverLatticeBlock;
import net.orcinus.galosphere.blocks.SilverLatticeVineBlock;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Galosphere.MODID);

    public static final RegistryObject<Block> MONSTROMETER = registerBlock("monstrometer", () -> new MonstrometerBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).lightLevel(state -> MonstrometerBlock.isActive(state) ? 4 : 0).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(GSoundEvents.MONSTROMETER)));
    public static final RegistryObject<Block> COMBUSTION_TABLE = registerBlock("combustion_table", () -> new CombustionTableBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5F).sound(GSoundEvents.SILVER).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> WARPED_ANCHOR = registerBlock("warped_anchor", () -> new WarpedAnchorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).lightLevel(state -> state.getValue(WarpedAnchorBlock.WARPED_CHARGE) * 3).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(GSoundEvents.SILVER)));

    public static final RegistryObject<Block> SILVER_TILES = registerBlock("silver_tiles", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().sound(GSoundEvents.SILVER).strength(3.0F, 6.0F)));
    public static final RegistryObject<Block> SILVER_TILES_STAIRS = registerBlock("silver_tiles_stairs", () -> new StairBlock(SILVER_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(SILVER_TILES.get())));
    public static final RegistryObject<Block> SILVER_TILES_SLAB = registerBlock("silver_tiles_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(SILVER_TILES.get())));

    public static final RegistryObject<Block> SILVER_PANEL = registerBlock("silver_panel", () -> new Block(BlockBehaviour.Properties.copy(SILVER_TILES.get())));
    public static final RegistryObject<Block> SILVER_PANEL_STAIRS = registerBlock("silver_panel_stairs", () -> new StairBlock(SILVER_PANEL.get().defaultBlockState(), BlockBehaviour.Properties.copy(SILVER_TILES.get())));
    public static final RegistryObject<Block> SILVER_PANEL_SLAB = registerBlock("silver_panel_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(SILVER_PANEL.get())));

    public static final RegistryObject<Block> SILVER_LATTICE = registerBlock("silver_lattice", () -> new SilverLatticeBlock(BlockBehaviour.Properties.copy(SILVER_TILES.get()).sound(GSoundEvents.SILVER_LATTICE).noOcclusion()));
    public static final RegistryObject<Block> GLOW_BERRIES_SILVER_LATTICE = registerNoTabBlock("glow_berries_silver_lattice", () -> new SilverLatticeVineBlock(BlockBehaviour.Properties.copy(SILVER_TILES.get()).sound(GSoundEvents.SILVER_LATTICE).lightLevel(CaveVines.emission(14)).noOcclusion()));

    public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(SILVER_ORE.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(GSoundEvents.SILVER)));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> CHANDELIER = registerNoTabBlock("chandelier", () -> new ChandelierBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3.5f).sound(SoundType.LANTERN).lightLevel(ChandelierBlock::getLightEmission).noOcclusion()));

    public static final RegistryObject<Block> ALLURITE_BLOCK = registerBlock("allurite_block", () -> new AmethystBlock(BlockBehaviour.Properties.of(GMaterials.ALLURITE, MaterialColor.COLOR_CYAN).strength(1.5F).sound(GSoundEvents.ALLURITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LUMIERE_BLOCK = registerBlock("lumiere_block", () -> new LumiereBlock(false, BlockBehaviour.Properties.of(GMaterials.LUMIERE, MaterialColor.COLOR_YELLOW).strength(1.5F).sound(GSoundEvents.LUMIERE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CHARGED_LUMIERE_BLOCK = registerBlock("charged_lumiere_block", () -> new LumiereBlock(true, BlockBehaviour.Properties.copy(LUMIERE_BLOCK.get()).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ALLURITE_CLUSTER = registerBlock("allurite_cluster", () -> new PollinatedClusterBlock(GParticleTypes.ALLURITE_RAIN, BlockBehaviour.Properties.of(GMaterials.ALLURITE).noOcclusion().randomTicks().sound(GSoundEvents.ALLURITE_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));
    public static final RegistryObject<Block> LUMIERE_CLUSTER = registerBlock("lumiere_cluster", () -> new PollinatedClusterBlock(GParticleTypes.LUMIERE_RAIN, BlockBehaviour.Properties.of(GMaterials.LUMIERE).noOcclusion().randomTicks().sound(GSoundEvents.LUMIERE_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));

    public static final RegistryObject<Block> GLINTED_ALLURITE_CLUSTER = registerBlock("glinted_allurite_cluster", () -> new GlintedClusterBlock(GParticleTypes.ALLURITE_RAIN, BlockBehaviour.Properties.of(GMaterials.ALLURITE).noOcclusion().randomTicks().sound(GSoundEvents.ALLURITE_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));
    public static final RegistryObject<Block> GLINTED_LUMIERE_CLUSTER = registerBlock("glinted_lumiere_cluster", () -> new GlintedClusterBlock(GParticleTypes.LUMIERE_RAIN, BlockBehaviour.Properties.of(GMaterials.LUMIERE).noOcclusion().randomTicks().sound(GSoundEvents.LUMIERE_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));
    public static final RegistryObject<Block> GLINTED_AMETHYST_CLUSTER = registerBlock("glinted_amethyst_cluster", () -> new GlintedClusterBlock(GParticleTypes.AMETHYST_RAIN, BlockBehaviour.Properties.of(GMaterials.LUMIERE).noOcclusion().randomTicks().sound(GSoundEvents.LUMIERE_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));

    public static final RegistryObject<Block> AMETHYST_STAIRS = registerBlock("amethyst_stairs", () -> new CrystalStairsBlock(Blocks.AMETHYST_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_SLAB = registerBlock("amethyst_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));

    public static final RegistryObject<Block> ALLURITE_STAIRS = registerBlock("allurite_stairs", () -> new CrystalStairsBlock(ALLURITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(ALLURITE_BLOCK.get())));
    public static final RegistryObject<Block> ALLURITE_SLAB = registerBlock("allurite_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(ALLURITE_BLOCK.get())));

    public static final RegistryObject<Block> LUMIERE_STAIRS = registerBlock("lumiere_stairs", () -> new CrystalStairsBlock(LUMIERE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(LUMIERE_BLOCK.get())));
    public static final RegistryObject<Block> LUMIERE_SLAB = registerBlock("lumiere_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(LUMIERE_BLOCK.get())));

    public static final RegistryObject<Block> SMOOTH_AMETHYST = registerBlock("smooth_amethyst", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_AMETHYST_STAIRS = registerBlock("smooth_amethyst_stairs", () -> new CrystalStairsBlock(SMOOTH_AMETHYST.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_AMETHYST_SLAB = registerBlock("smooth_amethyst_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_AMETHYST.get())));

    public static final RegistryObject<Block> SMOOTH_ALLURITE = registerBlock("smooth_allurite", () -> new AmethystBlock(BlockBehaviour.Properties.copy(ALLURITE_BLOCK.get())));
    public static final RegistryObject<Block> SMOOTH_ALLURITE_STAIRS = registerBlock("smooth_allurite_stairs", () -> new CrystalStairsBlock(SMOOTH_ALLURITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_ALLURITE_SLAB = registerBlock("smooth_allurite_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_ALLURITE.get())));

    public static final RegistryObject<Block> SMOOTH_LUMIERE = registerBlock("smooth_lumiere", () -> new AmethystBlock(BlockBehaviour.Properties.copy(LUMIERE_BLOCK.get())));
    public static final RegistryObject<Block> SMOOTH_LUMIERE_STAIRS = registerBlock("smooth_lumiere_stairs", () -> new CrystalStairsBlock(SMOOTH_LUMIERE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_LUMIERE_SLAB = registerBlock("smooth_lumiere_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_LUMIERE.get())));

    public static final RegistryObject<Block> AMETHYST_BRICKS = registerBlock("amethyst_bricks", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_BRICK_STAIRS = registerBlock("amethyst_brick_stairs", () -> new CrystalStairsBlock(AMETHYST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_BRICK_SLAB = registerBlock("amethyst_brick_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));

    public static final RegistryObject<Block> ALLURITE_BRICKS = registerBlock("allurite_bricks", () -> new AmethystBlock(BlockBehaviour.Properties.copy(ALLURITE_BLOCK.get())));
    public static final RegistryObject<Block> ALLURITE_BRICK_STAIRS = registerBlock("allurite_brick_stairs", () -> new CrystalStairsBlock(ALLURITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> ALLURITE_BRICK_SLAB = registerBlock("allurite_brick_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(ALLURITE_BRICKS.get())));

    public static final RegistryObject<Block> LUMIERE_BRICKS = registerBlock("lumiere_bricks", () -> new AmethystBlock(BlockBehaviour.Properties.copy(LUMIERE_BLOCK.get())));
    public static final RegistryObject<Block> LUMIERE_BRICK_STAIRS = registerBlock("lumiere_brick_stairs", () -> new CrystalStairsBlock(LUMIERE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> LUMIERE_BRICK_SLAB = registerBlock("lumiere_brick_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(LUMIERE_BRICKS.get())));

    public static final RegistryObject<Block> CHISELED_AMETHYST = registerBlock("chiseled_amethyst", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> CHISELED_ALLURITE = registerBlock("chiseled_allurite", () -> new AmethystBlock(BlockBehaviour.Properties.copy(ALLURITE_BLOCK.get())));
    public static final RegistryObject<Block> CHISELED_LUMIERE = registerBlock("chiseled_lumiere", () -> new AmethystBlock(BlockBehaviour.Properties.copy(LUMIERE_BLOCK.get())));

    public static final RegistryObject<Block> AMETHYST_LAMP = registerBlock("amethyst_lamp", () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_PURPLE).lightLevel(state -> 15).strength(0.3F).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> ALLURITE_LAMP = registerBlock("allurite_lamp", () -> new Block(BlockBehaviour.Properties.of(GMaterials.ALLURITE, MaterialColor.COLOR_LIGHT_BLUE).lightLevel(state -> 15).strength(0.3F).sound(GSoundEvents.ALLURITE)));
    public static final RegistryObject<Block> LUMIERE_LAMP = registerBlock("lumiere_lamp", () -> new Block(BlockBehaviour.Properties.of(GMaterials.LUMIERE, MaterialColor.COLOR_YELLOW).lightLevel(state -> 15).strength(0.3F).sound(GSoundEvents.LUMIERE)));

    public static final RegistryObject<Block> LICHEN_MOSS = registerBlock("lichen_moss", () -> new LichenMossBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_CYAN).strength(0.1f).sound(GSoundEvents.LICHEN_MOSS).lightLevel(state -> state.getValue(LichenMossBlock.LIT) ? 12 : 0)));
    public static final RegistryObject<Block> LICHEN_ROOTS = registerBlock("lichen_roots", () -> new LichenRootsBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT, MaterialColor.TERRACOTTA_CYAN).noCollission().instabreak().offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.ROOTS)));
    public static final RegistryObject<Block> BOWL_LICHEN = registerBlock("bowl_lichen", () -> new LichenMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.TERRACOTTA_CYAN).instabreak().noCollission().sound(SoundType.FUNGUS)));
    public static final RegistryObject<Block> LICHEN_SHELF = registerBlock("lichen_shelf", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL_WALL_FAN).noCollission().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> LICHEN_CORDYCEPS = registerNoTabBlock("lichen_cordyceps", () -> new CordycepsBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.TERRACOTTA_CYAN).lightLevel(state -> state.getValue(CordycepsBlock.BULB) ? 8 : 0).noCollission().sound(SoundType.ROOTS)));
    public static final RegistryObject<Block> LICHEN_CORDYCEPS_PLANT = registerNoTabBlock("lichen_cordyceps_plant", () -> new CordycepsPlantBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.TERRACOTTA_CYAN).instabreak().noCollission().sound(SoundType.ROOTS)));
    public static final RegistryObject<Block> GLOW_INK_CLUMPS = registerBlock("glow_ink_clumps", () -> new GlowInkClumpsBlock(BlockBehaviour.Properties.of(Material.SCULK, MaterialColor.COLOR_LIGHT_BLUE).noCollission().strength(0.2F).sound(GSoundEvents.GLOW_INK_CLUMPS).lightLevel(GlowInkClumpsBlock.emission(15, 8))));

    public static final RegistryObject<Block> LUMIERE_COMPOSTER = registerNoTabBlock("lumiere_composter", () -> new LumiereComposterBlock(BlockBehaviour.Properties.copy(Blocks.COMPOSTER)));
    public static final RegistryObject<Block> POTTED_BOWL_LICHEN = registerNoTabBlock("potted_bowl_lichen", () -> new FlowerPotBlock(BOWL_LICHEN.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> POTTED_LICHEN_ROOTS = registerNoTabBlock("potted_lichen_roots", () -> new FlowerPotBlock(LICHEN_ROOTS.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        GItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(Galosphere.GALOSPHERE)));
        return block;
    }

    public static <B extends Block> RegistryObject<B> registerNoTabBlock(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }

}
