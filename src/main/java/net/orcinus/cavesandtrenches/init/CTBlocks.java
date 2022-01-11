package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.blocks.AmethystSlabBlock;
import net.orcinus.cavesandtrenches.blocks.AmethystStairsBlock;
import net.orcinus.cavesandtrenches.blocks.AuraListenerBlock;
import net.orcinus.cavesandtrenches.blocks.CombustionTableBlock;
import net.orcinus.cavesandtrenches.blocks.LumenBlossomBlock;
import net.orcinus.cavesandtrenches.blocks.LumiereComposterBlock;
import net.orcinus.cavesandtrenches.blocks.LumiereLampBlock;
import net.orcinus.cavesandtrenches.blocks.MysteriaVinesBlock;
import net.orcinus.cavesandtrenches.blocks.MysteriaVinesPlantBlock;
import net.orcinus.cavesandtrenches.blocks.PollinatedClusterBlock;
import net.orcinus.cavesandtrenches.blocks.TestBlock;
import net.orcinus.cavesandtrenches.blocks.WarpedAnchorBlock;
import net.orcinus.cavesandtrenches.util.RegistryHandler;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTBlocks {

    public static final RegistryHandler REGISTRY = CavesAndTrenches.REGISTRY;

    /**
     * CRYSTALS
     */
    public static final RegistryObject<Block> ALLURITE_BLOCK                    = REGISTRY.registerBlock("allurite_block", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LUMIERE_BLOCK                     = REGISTRY.registerBlock("lumiere_block", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ALLURITE_CLUSTER                  = REGISTRY.registerBlock("allurite_cluster", () -> new PollinatedClusterBlock(BlockBehaviour.Properties.of(CTMaterials.ALLURITE).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5)));
    public static final RegistryObject<Block> LUMIERE_CLUSTER                   = REGISTRY.registerBlock("lumiere_cluster", () -> new PollinatedClusterBlock(BlockBehaviour.Properties.of(CTMaterials.LUMIERE).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5)));
    public static final RegistryObject<Block> POLISHED_AMETHYST                 = REGISTRY.registerBlock("polished_amethyst", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> POLISHED_AMETHYST_STAIRS          = REGISTRY.registerBlock("polished_amethyst_stairs", () -> new AmethystStairsBlock(POLISHED_AMETHYST.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> POLISHED_AMETHYST_SLAB            = REGISTRY.registerBlock("polished_amethyst_slab", () -> new AmethystSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_BRICKS                   = REGISTRY.registerBlock("amethyst_bricks", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_BRICKS_STAIRS            = REGISTRY.registerBlock("amethyst_bricks_stairs", () -> new AmethystStairsBlock(AMETHYST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_BRICKS_SLAB              = REGISTRY.registerBlock("amethyst_bricks_slab", () -> new AmethystSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> CHISELED_AMETHYST                 = REGISTRY.registerBlock("chiseled_amethyst", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> MYSTERIA_CINDERS                  = REGISTRY.registerBlock("mysteria_cinders", () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_PINK).strength(1.5F).sound(SoundType.SMALL_DRIPLEAF).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MYSTERIA_LOG                      = REGISTRY.registerBlock("mysteria_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.TERRACOTTA_WHITE).strength(2.0F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MYSTERIA_VINES                    = REGISTRY.registerBlock("mysteria_vines", () -> new MysteriaVinesBlock(BlockBehaviour.Properties.of(Material.PLANT).lightLevel((state) -> 8).instabreak().noCollission().randomTicks().sound(SoundType.CAVE_VINES)));
    public static final RegistryObject<Block> MYSTERIA_VINES_PLANTS             = REGISTRY.registerNoTabBlock("mysteria_vines_plant", () -> new MysteriaVinesPlantBlock(BlockBehaviour.Properties.copy(MYSTERIA_VINES.get())));

    /**
     * SILVER
     */
    public static final RegistryObject<Block> SILVER_BLOCK                      = REGISTRY.registerBlock("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK                  = REGISTRY.registerBlock("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> SILVER_ORE                        = REGISTRY.registerBlock("silver_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE              = REGISTRY.registerBlock("deepslate_silver_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(SILVER_ORE.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));

    /**
     * GLOW LICHEN
     */
    public static final RegistryObject<Block> GLOW_LICHEN_BLOCK                 = REGISTRY.registerBlock("glow_lichen_block", () -> new Block(BlockBehaviour.Properties.of(Material.PLANT).strength(0.3F).sound(SoundType.MOSS).lightLevel(state -> 7)));
    public static final RegistryObject<Block> LUMEN_BLOSSOM                     = REGISTRY.registerBlock("lumen_blossom", () -> new LumenBlossomBlock(BlockBehaviour.Properties.of(Material.PLANT).strength(0.2F).sound(SoundType.GLOW_LICHEN).lightLevel(state -> 7).noCollission()));
//    public static final RegistryObject<Block> GLOW_LICHEN_VINES                 = REGISTRY.registerBlock("glow_lichen_vines", () -> new GlowLichenVinesBlock(BlockBehaviour.Properties.of(Material.PLANT).randomTicks().noCollission().lightLevel(CaveVines.emission(14)).instabreak().sound(SoundType.CAVE_VINES)));
//    public static final RegistryObject<Block> GLOW_LICHEN_VINES_PLANT           = REGISTRY.registerBlock("glow_lichen_vines_plant", () -> new GlowLichenVinesPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().lightLevel(state -> 7).instabreak().sound(SoundType.CAVE_VINES)));

    /**
     * UTILITIES
     */
    public static final RegistryObject<Block> AURA_LISTENER                     = REGISTRY.registerBlock("aura_listener", () -> new AuraListenerBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> WARPED_ANCHOR                     = REGISTRY.registerBlock("warped_anchor", () -> new WarpedAnchorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> LUMIERE_LAMP                      = REGISTRY.registerBlock("lumiere_lamp", () -> new LumiereLampBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.SHROOMLIGHT)));
    public static final RegistryObject<Block> LUMIERE_COMPOSTER                 = REGISTRY.registerNoTabBlock("lumiere_composter", () -> new LumiereComposterBlock(BlockBehaviour.Properties.copy(Blocks.COMPOSTER)));
    public static final RegistryObject<Block> COMBUSTION_TABLE                  = REGISTRY.registerBlock("combustion_table", () -> new CombustionTableBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(2.5F).sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> TEST_BLOCK = REGISTRY.registerBlock("test_block", () -> new TestBlock(BlockBehaviour.Properties.of(Material.METAL)));

}
