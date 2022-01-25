package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.blocks.AmethystSlabBlock;
import net.orcinus.cavesandtrenches.blocks.AmethystStairsBlock;
import net.orcinus.cavesandtrenches.blocks.AuraListenerBlock;
import net.orcinus.cavesandtrenches.blocks.DebuggerBlock;
import net.orcinus.cavesandtrenches.blocks.LumenBlossomBlock;
import net.orcinus.cavesandtrenches.blocks.LumiereComposterBlock;
import net.orcinus.cavesandtrenches.blocks.LumiereLampBlock;
import net.orcinus.cavesandtrenches.blocks.MysteriaVinesBlock;
import net.orcinus.cavesandtrenches.blocks.MysteriaVinesPlantBlock;
import net.orcinus.cavesandtrenches.blocks.PollinatedClusterBlock;
import net.orcinus.cavesandtrenches.blocks.TestBlock;
import net.orcinus.cavesandtrenches.blocks.WarpedAnchorBlock;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CavesAndTrenches.MODID);

    public static final RegistryObject<Block> ALLURITE_BLOCK                    = registerBlock("allurite_block", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LUMIERE_BLOCK                     = registerBlock("lumiere_block", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ALLURITE_CLUSTER                  = registerBlock("allurite_cluster", () -> new PollinatedClusterBlock(BlockBehaviour.Properties.of(CTMaterials.ALLURITE).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 10)));
    public static final RegistryObject<Block> LUMIERE_CLUSTER                   = registerBlock("lumiere_cluster", () -> new PollinatedClusterBlock(BlockBehaviour.Properties.of(CTMaterials.LUMIERE).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 10)));
    public static final RegistryObject<Block> POLISHED_AMETHYST                 = registerBlock("polished_amethyst", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> POLISHED_AMETHYST_STAIRS          = registerBlock("polished_amethyst_stairs", () -> new AmethystStairsBlock(POLISHED_AMETHYST.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> POLISHED_AMETHYST_SLAB            = registerBlock("polished_amethyst_slab", () -> new AmethystSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_BRICKS                   = registerBlock("amethyst_bricks", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_BRICKS_STAIRS            = registerBlock("amethyst_bricks_stairs", () -> new AmethystStairsBlock(AMETHYST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> AMETHYST_BRICKS_SLAB              = registerBlock("amethyst_bricks_slab", () -> new AmethystSlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> CHISELED_AMETHYST                 = registerBlock("chiseled_amethyst", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> MYSTERIA_CINDERS                  = registerBlock("mysteria_cinders", () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.COLOR_PINK).strength(1.5F).sound(SoundType.SMALL_DRIPLEAF).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MYSTERIA_LOG                      = registerBlock("mysteria_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.AMETHYST, MaterialColor.TERRACOTTA_WHITE).strength(2.0F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MYSTERIA_VINES                    = registerBlock("mysteria_vines", () -> new MysteriaVinesBlock(BlockBehaviour.Properties.of(Material.PLANT).lightLevel((state) -> 8).instabreak().noCollission().randomTicks().sound(SoundType.CAVE_VINES)));
    public static final RegistryObject<Block> MYSTERIA_VINES_PLANTS             = registerNoTabBlock("mysteria_vines_plant", () -> new MysteriaVinesPlantBlock(BlockBehaviour.Properties.copy(MYSTERIA_VINES.get())));
    public static final RegistryObject<Block> SILVER_BLOCK                      = registerBlock("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK                  = registerBlock("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> SILVER_ORE                        = registerBlock("silver_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE              = registerBlock("deepslate_silver_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(SILVER_ORE.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> AURA_LISTENER                     = registerBlock("aura_listener", () -> new AuraListenerBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> WARPED_ANCHOR                     = registerBlock("warped_anchor", () -> new WarpedAnchorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).lightLevel(state -> state.getValue(WarpedAnchorBlock.WARPED_CHARGE) * 4).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> LUMIERE_LAMP                      = registerBlock("lumiere_lamp", () -> new LumiereLampBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.SHROOMLIGHT)));
    public static final RegistryObject<Block> LUMIERE_COMPOSTER                 = registerNoTabBlock("lumiere_composter", () -> new LumiereComposterBlock(BlockBehaviour.Properties.copy(Blocks.COMPOSTER).dropsLike(Blocks.COMPOSTER)));

    public static final RegistryObject<Block> DEBUGGER = registerBlock("debugger", () -> new DebuggerBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)));
    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        CTItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES)));
        return block;
    }

    public static <B extends Block> RegistryObject<B> registerNoTabBlock(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }

}
