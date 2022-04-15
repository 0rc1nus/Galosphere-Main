package net.orcinus.galosphere.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.AuraTransmitterBlock;
import net.orcinus.galosphere.blocks.CrystalSlabBlock;
import net.orcinus.galosphere.blocks.CrystalStairsBlock;
import net.orcinus.galosphere.blocks.CombustionTableBlock;
import net.orcinus.galosphere.blocks.LumiereComposterBlock;
import net.orcinus.galosphere.blocks.MysteriaVinesBlock;
import net.orcinus.galosphere.blocks.MysteriaVinesPlantBlock;
import net.orcinus.galosphere.blocks.PollinatedClusterBlock;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Galosphere.MODID);

    public static final RegistryObject<Block> ALLURITE_BLOCK = registerBlock("allurite_block", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LUMIERE_BLOCK = registerBlock("lumiere_block", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ALLURITE_CLUSTER = registerBlock("allurite_cluster", () -> new PollinatedClusterBlock(BlockBehaviour.Properties.of(GMaterials.ALLURITE).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));
    public static final RegistryObject<Block> LUMIERE_CLUSTER = registerBlock("lumiere_cluster", () -> new PollinatedClusterBlock(BlockBehaviour.Properties.of(GMaterials.LUMIERE).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 7)));
    public static final RegistryObject<Block> AMETHYST_STAIRS = registerBlock("amethyst_stairs", () -> new CrystalStairsBlock(Blocks.AMETHYST_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_SLAB = registerBlock("amethyst_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> ALLURITE_STAIRS = registerBlock("allurite_stairs", () -> new CrystalStairsBlock(ALLURITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(ALLURITE_BLOCK.get())));
    public static final RegistryObject<Block> ALLURITE_SLAB = registerBlock("allurite_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> LUMIERE_STAIRS = registerBlock("lumiere_stairs", () -> new CrystalStairsBlock(LUMIERE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(LUMIERE_BLOCK.get())));
    public static final RegistryObject<Block> LUMIERE_SLAB = registerBlock("lumiere_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_AMETHYST = registerBlock("smooth_amethyst", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_AMETHYST_STAIRS = registerBlock("smooth_amethyst_stairs", () -> new CrystalStairsBlock(SMOOTH_AMETHYST.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_AMETHYST_SLAB = registerBlock("smooth_amethyst_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_ALLURITE = registerBlock("smooth_allurite", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_ALLURITE_STAIRS = registerBlock("smooth_allurite_stairs", () -> new CrystalStairsBlock(SMOOTH_AMETHYST.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_ALLURITE_SLAB = registerBlock("smooth_allurite_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_LUMIERE = registerBlock("smooth_lumiere", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_LUMIERE_STAIRS = registerBlock("smooth_lumiere_stairs", () -> new CrystalStairsBlock(SMOOTH_AMETHYST.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_LUMIERE_SLAB = registerBlock("smooth_lumiere_slab", () -> new CrystalSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_BRICKS = registerBlock("amethyst_bricks", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> ALLURITE_BRICKS = registerBlock("allurite_bricks", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> LUMIERE_BRICKS = registerBlock("lumiere_bricks", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> CHISELED_AMETHYST = registerBlock("chiseled_amethyst", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> CHISELED_ALLURITE = registerBlock("chiseled_allurite", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> CHISELED_LUMIERE = registerBlock("chiseled_lumiere", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> MYSTERIA_CINDERS = registerBlock("mysteria_cinders", () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_PINK).strength(1.5F).sound(SoundType.SMALL_DRIPLEAF).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MYSTERIA_LOG = registerBlock("mysteria_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.TERRACOTTA_WHITE).strength(2.0F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MYSTERIA_VINES = registerBlock("mysteria_vines", () -> new MysteriaVinesBlock(BlockBehaviour.Properties.of(Material.PLANT).lightLevel((state) -> 8).instabreak().noCollission().randomTicks().sound(SoundType.CAVE_VINES)));
    public static final RegistryObject<Block> MYSTERIA_VINES_PLANTS = registerNoTabBlock("mysteria_vines_plant", () -> new MysteriaVinesPlantBlock(BlockBehaviour.Properties.copy(MYSTERIA_VINES.get())));
    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(SILVER_ORE.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> AURA_TRANSMITTER = registerBlock("aura_transmitter", () -> new AuraTransmitterBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> WARPED_ANCHOR = registerBlock("warped_anchor", () -> new WarpedAnchorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).lightLevel(state -> state.getValue(WarpedAnchorBlock.WARPED_CHARGE) * 4).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> ALLURITE_LAMP = registerBlock("allurite_lamp", () -> new Block(BlockBehaviour.Properties.of(GMaterials.ALLURITE, MaterialColor.COLOR_LIGHT_BLUE).lightLevel(state -> 15).strength(0.3F).sound(SoundType.SHROOMLIGHT)));
    public static final RegistryObject<Block> LUMIERE_LAMP = registerBlock("lumiere_lamp", () -> new Block(BlockBehaviour.Properties.of(GMaterials.LUMIERE, MaterialColor.COLOR_YELLOW).lightLevel(state -> 15).strength(0.3F).sound(SoundType.SHROOMLIGHT)));
    public static final RegistryObject<Block> AMETHYST_LAMP = registerBlock("amethyst_lamp", () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_PURPLE).lightLevel(state -> 15).strength(0.3F).sound(SoundType.SHROOMLIGHT)));
    public static final RegistryObject<Block> LUMIERE_COMPOSTER = registerNoTabBlock("lumiere_composter", () -> new LumiereComposterBlock(BlockBehaviour.Properties.copy(Blocks.COMPOSTER)));
    public static final RegistryObject<Block> COMBUSTION_TABLE = registerBlock("combustion_table", () -> new CombustionTableBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops()));

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        GItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(Galosphere.GALOSPHERE)));
        return block;
    }

    public static <B extends Block> RegistryObject<B> registerNoTabBlock(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }

}
