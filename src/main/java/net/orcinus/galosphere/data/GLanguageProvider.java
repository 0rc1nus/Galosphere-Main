package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.items.SilverSmithingTemplateItem;

import java.util.function.Predicate;

public class GLanguageProvider extends FabricLanguageProvider {

    public GLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        GBlocks.BLOCKS.values().forEach(block -> {
            translationBuilder.add(block, reformat(BuiltInRegistries.BLOCK.getKey(block).getPath()));
        });
        GItems.ITEMS.values().stream().filter(Predicate.not(BlockItem.class::isInstance).or(ItemNameBlockItem.class::isInstance)).filter(Predicate.not(SilverSmithingTemplateItem.class::isInstance).and(Predicate.not(GItems.PRESERVING_TEMPLATE::equals))).forEach(item -> {
            if (item != GItems.PRESERVING_TEMPLATE || item != GItems.SILVER_UPGRADE_SMITHING_TEMPLATE) {
                translationBuilder.add(item, reformat(BuiltInRegistries.ITEM.getKey(item).getPath()));
            }
        });
        translationBuilder.add("item.galosphere.silver_bomb.duration", "Duration");
        translationBuilder.add("item.galosphere.silver_bomb.explosion", "Explosion");
        translationBuilder.add("item.galosphere.silver_bomb.bouncy", "Bouncy");
        translationBuilder.add(GEntityTypes.SPARKLE, "Sparkle");
        translationBuilder.add(GEntityTypes.SPECTRE, "Spectre");
        translationBuilder.add(GEntityTypes.SIVLER_BOMB, "Silver Bomb");
        translationBuilder.add(GEntityTypes.SPECTATOR_VISION, "Spectator Vision");
        translationBuilder.add(GEntityTypes.SPECTERPILLAR, "Specterpillar");
        translationBuilder.add(GEntityTypes.GLOW_FLARE, "Glow Flare");
        translationBuilder.add(GEntityTypes.SPECTRE_FLARE, "Spectre Flare");
        translationBuilder.add(GMobEffects.ASTRAL, "Astral");
        GBiomes.getIds().forEach(resourceLocation -> {
            translationBuilder.add("biome.galosphere." + resourceLocation.getPath(), reformat(resourceLocation.getPath()));
        });
        translationBuilder.add("galosphere.midnightconfig.title", "Galosphere Config");
        translationBuilder.add("galosphere.midnightconfig.slowBuddingAmethystDestroySpeed", "Slowed Budding Amethyst Destroy Speed");
        translationBuilder.add("galosphere.midnightconfig.pillagerDropSilverIngot", "Pillager Drop Silver Ingots");
        translationBuilder.add("galosphere.midnightconfig.spectreFlareAncientCityLoot", "Spectre Flares spawn in ancient city chest loot");
        translationBuilder.add("advancements.galosphere.crystal_lamps.description", "Have all Crystal Lamps in your inventory");
        translationBuilder.add("advancements.galosphere.crystal_lamps.title", "Balanced, As All Things Should Be");
        translationBuilder.add("advancements.galosphere.light_spread.description", "Deploy a Glow Flare");
        translationBuilder.add("advancements.galosphere.light_spread.title", "Spread the Light!");
        translationBuilder.add("advancements.galosphere.lumiere_compost.description", "Create Glowstone Dust by Composting with a Lumiere Shard");
        translationBuilder.add("advancements.galosphere.lumiere_compost.title", "Fragility of Light");
        translationBuilder.add("advancements.galosphere.silver_bomb.description", "Construct a Silver Bomb");
        translationBuilder.add("advancements.galosphere.silver_bomb.title", "It's About Drive, It's About Power");
        translationBuilder.add("advancements.galosphere.silver_ingot.description", "Obtain a Silver Ingot");
        translationBuilder.add("advancements.galosphere.silver_ingot.title", "Multi-Disciplined");
        translationBuilder.add("advancements.galosphere.sterling_armor.description", "Don a full suit of Sterling Armor");
        translationBuilder.add("advancements.galosphere.sterling_armor.title", "Looking Good, Partner!");
        translationBuilder.add("advancements.galosphere.use_spectre_spyglass.description", "Spectate a Spectre");
        translationBuilder.add("advancements.galosphere.use_spectre_spyglass.title", "Watchdog");
        translationBuilder.add("advancements.galosphere.use_spectre_flare.description", "Use a spectre flare");
        translationBuilder.add("advancements.galosphere.use_spectre_flare.title", "I spy with my little eye");
        translationBuilder.add("advancements.galosphere.warped_teleport.description", "Teleport to a Warped Anchor");
        translationBuilder.add("advancements.galosphere.warped_teleport.title", "What is this place?");
        translationBuilder.add("attribute.name.generic.illager_resistance", "Illager Resistance");
        translationBuilder.add("item.minecraft.potion.effect.astral", "Potion of Astral");
        translationBuilder.add("item.minecraft.splash_potion.effect.astral", "Splash Potion of Astral");
        translationBuilder.add("item.minecraft.lingering_potion.effect.astral", "Lingering Potion of Astral");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.astral", "Arrow of Astral");
        translationBuilder.add("itemGroup.galosphere.galosphere", "Galosphere");
        translationBuilder.add("upgrade.galosphere.silver_upgrade", "Silver Upgrade");
        translationBuilder.add("item.galosphere.smithing_template.silver_upgrade.applies_to", "Leather Equipment");
        translationBuilder.add("item.galosphere.smithing_template.silver_upgrade.ingredients", "Silver Ingot");
        translationBuilder.add("container.galosphere.combustion_table", "Combustion Table");
    }

    private String reformat(String id) {
        char[] buffer = id.toCharArray();
        for (int i = 0; i < buffer.length; i++) {
            boolean flag1 = i > 0 && String.valueOf(buffer[i - 1]).equals("_");
            if (i == 0 || flag1) {
                buffer[i] = Character.toUpperCase(buffer[i]);
                if (flag1) {
                    buffer[i - 1] = ' ';
                }
            }
        }
        return new String(buffer);
    }
}
