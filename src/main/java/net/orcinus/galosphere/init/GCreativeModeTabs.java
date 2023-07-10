package net.orcinus.galosphere.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Galosphere.MODID);

    public static final RegistryObject<CreativeModeTab> GALOSPHERE = CREATIVE_MODE_TABS.register("galosphere", () -> CreativeModeTab.builder()
            .icon(GItems.ICON_ITEM.get()::getDefaultInstance)
            .title(Component.translatable("itemGroup.galosphere.galosphere"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(GItems.SILVER_UPGRADE_SMITHING_TEMPLATE.get());
                output.accept(GItems.SPARKLE_SPAWN_EGG.get());
                output.accept(GItems.SPECTRE_SPAWN_EGG.get());
                output.accept(GItems.SPECTERPILLAR_SPAWN_EGG.get());
                output.accept(GItems.BOTTLE_OF_SPECTRE.get());
                output.accept(GItems.ALLURITE_SHARD.get());
                output.accept(GItems.LUMIERE_SHARD.get());
                output.accept(GItems.RAW_SILVER.get());
                output.accept(GItems.SILVER_INGOT.get());
                output.accept(GItems.SILVER_NUGGET.get());
                output.accept(GItems.BAROMETER.get());
                output.accept(GItems.SILVER_BOMB.get());
                output.accept(GItems.STERLING_HELMET.get());
                output.accept(GItems.STERLING_CHESTPLATE.get());
                output.accept(GItems.STERLING_LEGGINGS.get());
                output.accept(GItems.STERLING_BOOTS.get());
                output.accept(GItems.STERLING_HORSE_ARMOR.get());
                output.accept(GItems.LICHEN_CORDYCEPS.get());
                output.accept(GItems.GOLDEN_LICHEN_CORDYCEPS.get());
                output.accept(GItems.GLOW_FLARE.get());
                output.accept(GItems.SPECTRE_FLARE.get());
                output.accept(GItems.CHANDELIER.get());
                output.accept(GBlocks.MONSTROMETER.get());
                output.accept(GBlocks.COMBUSTION_TABLE.get());
                output.accept(GBlocks.WARPED_ANCHOR.get());
                output.accept(GBlocks.SILVER_TILES.get());
                output.accept(GBlocks.SILVER_TILES_STAIRS.get());
                output.accept(GBlocks.SILVER_TILES_SLAB.get());
                output.accept(GBlocks.SILVER_PANEL.get());
                output.accept(GBlocks.SILVER_PANEL_STAIRS.get());
                output.accept(GBlocks.SILVER_PANEL_SLAB.get());
                output.accept(GBlocks.SILVER_LATTICE.get());
                output.accept(GBlocks.SILVER_ORE.get());
                output.accept(GBlocks.DEEPSLATE_SILVER_ORE.get());
                output.accept(GBlocks.SILVER_BLOCK.get());
                output.accept(GBlocks.RAW_SILVER_BLOCK.get());
                output.accept(GBlocks.ALLURITE_BLOCK.get());
                output.accept(GBlocks.LUMIERE_BLOCK.get());
                output.accept(GBlocks.CHARGED_LUMIERE_BLOCK.get());
                output.accept(GBlocks.ALLURITE_CLUSTER.get());
                output.accept(GBlocks.LUMIERE_CLUSTER.get());
                output.accept(GBlocks.GLINTED_ALLURITE_CLUSTER.get());
                output.accept(GBlocks.GLINTED_LUMIERE_CLUSTER.get());
                output.accept(GBlocks.GLINTED_AMETHYST_CLUSTER.get());
                output.accept(GBlocks.AMETHYST_STAIRS.get());
                output.accept(GBlocks.AMETHYST_SLAB.get());
                output.accept(GBlocks.ALLURITE_STAIRS.get());
                output.accept(GBlocks.ALLURITE_SLAB.get());
                output.accept(GBlocks.LUMIERE_STAIRS.get());
                output.accept(GBlocks.LUMIERE_SLAB.get());
                output.accept(GBlocks.SMOOTH_AMETHYST.get());
                output.accept(GBlocks.SMOOTH_AMETHYST_STAIRS.get());
                output.accept(GBlocks.SMOOTH_AMETHYST_SLAB.get());
                output.accept(GBlocks.SMOOTH_ALLURITE.get());
                output.accept(GBlocks.SMOOTH_ALLURITE_STAIRS.get());
                output.accept(GBlocks.SMOOTH_ALLURITE_SLAB.get());
                output.accept(GBlocks.SMOOTH_LUMIERE.get());
                output.accept(GBlocks.SMOOTH_LUMIERE_STAIRS.get());
                output.accept(GBlocks.SMOOTH_LUMIERE_SLAB.get());
                output.accept(GBlocks.AMETHYST_BRICKS.get());
                output.accept(GBlocks.AMETHYST_BRICK_STAIRS.get());
                output.accept(GBlocks.AMETHYST_BRICK_SLAB.get());
                output.accept(GBlocks.ALLURITE_BRICKS.get());
                output.accept(GBlocks.ALLURITE_BRICK_STAIRS.get());
                output.accept(GBlocks.ALLURITE_BRICK_SLAB.get());
                output.accept(GBlocks.LUMIERE_BRICKS.get());
                output.accept(GBlocks.LUMIERE_BRICK_STAIRS.get());
                output.accept(GBlocks.LUMIERE_BRICK_SLAB.get());
                output.accept(GBlocks.CHISELED_AMETHYST.get());
                output.accept(GBlocks.CHISELED_ALLURITE.get());
                output.accept(GBlocks.CHISELED_LUMIERE.get());
                output.accept(GBlocks.AMETHYST_LAMP.get());
                output.accept(GBlocks.ALLURITE_LAMP.get());
                output.accept(GBlocks.LUMIERE_LAMP.get());
                output.accept(GBlocks.LICHEN_MOSS.get());
                output.accept(GBlocks.LICHEN_ROOTS.get());
                output.accept(GBlocks.BOWL_LICHEN.get());
                output.accept(GBlocks.LICHEN_SHELF.get());
                output.accept(GBlocks.GLOW_INK_CLUMPS.get());
            })
            .build()
    );

}
