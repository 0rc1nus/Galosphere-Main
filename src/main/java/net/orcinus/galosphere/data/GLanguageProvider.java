package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEnchantments;
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
        GItems.ITEMS.values().stream().filter(Predicate.not(BlockItem.class::isInstance).or(ItemNameBlockItem.class::isInstance)).filter(Predicate.not(SilverSmithingTemplateItem.class::isInstance).and(Predicate.not(GItems.PRESERVED_TEMPLATE::equals))).forEach(item -> {
            if (item != GItems.PRESERVED_TEMPLATE || item != GItems.SILVER_UPGRADE_SMITHING_TEMPLATE) {
                translationBuilder.add(item, reformat(BuiltInRegistries.ITEM.getKey(item).getPath()));
            }
        });
        GEntityTypes.ENTITY_TYPES.values().forEach(entityType -> {
            translationBuilder.add(entityType, reformat(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath()));
        });
        GMobEffects.MOB_EFFECTS.values().forEach(mobEffect -> {
            translationBuilder.add(mobEffect, reformat(BuiltInRegistries.MOB_EFFECT.getKey(mobEffect).getPath()));
        });
        GEnchantments.ENCHANTMENTS.values().forEach(enchantment -> {
            translationBuilder.add(enchantment, reformat(BuiltInRegistries.ENCHANTMENT.getKey(enchantment).getPath()));
        });
        translationBuilder.add("item.galosphere.preserved", "Preserved");
        translationBuilder.add("item.galosphere.silver_bomb.duration", "Duration");
        translationBuilder.add("item.galosphere.silver_bomb.explosion", "Explosion");
        translationBuilder.add("item.galosphere.silver_bomb.bouncy", "Bouncy");
        translationBuilder.add("subtitles.block.monstrometer.activate", "Monstrometer activates");
        translationBuilder.add("subtitles.block.monstrometer.charge", "Monstrometer is charged");
        translationBuilder.add("subtitles.block.monstrometer.deactivate", "Monstrometer deactivates");
        translationBuilder.add("block.pink_salt_chamber.summon", "Pink Salt Chamber summons");
        translationBuilder.add("block.pink_salt_chamber.deactivate", "Pink Salt Chamber deactivates");
        translationBuilder.add("subtitles.block.lumiere.compost", "Composter filled with Lumiere Shard");
        translationBuilder.add("subtitles.entity.specterpillar.death", "Specterpillar dies");
        translationBuilder.add("subtitles.entity.specterpillar.hurt", "Specterpillar hurts");
        translationBuilder.add("subtitles.item.saltbound_tablet.prepare_attack", "Saltbound Tablet charges up");
        translationBuilder.add("subtitles.item.saltbound_tablet.cast_attack", "Saltbound Tablet fires");
        translationBuilder.add("subtitles.item.saltbound_tablet.cooldown_over", "Saltbound Tablet recharged");
        translationBuilder.add("subtitles.entity.spectre.ambient", "Spectre chirps");
        translationBuilder.add("subtitles.entity.spectre.death", "Spectre dies");
        translationBuilder.add("subtitles.entity.spectre.hurt", "Spectre hurts");
        translationBuilder.add("subtitles.entity.spectre.lock_to_spyglass", "Spyglass locks to Spectre");
        translationBuilder.add("subtitles.entity.spectre.receive_item", "Spectre receives Item");
        translationBuilder.add("subtitles.entity.berserker.death", "Berserker dies");
        translationBuilder.add("subtitles.entity.berserker.hurt", "Berserker hurts");
        translationBuilder.add("subtitles.entity.berserker.idle", "Berserker grunts");
        translationBuilder.add("subtitles.entity.berserker.punch", "Berserker punch");
        translationBuilder.add("subtitles.entity.berserker.roar", "Berserker roars");
        translationBuilder.add("subtitles.entity.berserker.shake", "Berserker shakes");
        translationBuilder.add("subtitles.entity.berserker.smash", "Berserker smashes");
        translationBuilder.add("subtitles.entity.berserker.step", "Berserker steps");
        translationBuilder.add("subtitles.entity.berserker.summoning", "Berserker summons");
        translationBuilder.add("subtitles.entity.pink_salt_pillar.emerge", "Pink Salt Pillar pierces");
        translationBuilder.add("subtitles.entity.pink_salt_shard.land", "Pink Salt Shard lands");
        translationBuilder.add("subtitles.entity.preserved.death", "Preserved dies");
        translationBuilder.add("subtitles.entity.preserved.emerge", "Preserved emerges");
        translationBuilder.add("subtitles.entity.preserved.hurt", "Preserved hurts");
        translationBuilder.add("subtitles.entity.preserved.idle", "Preserved groans");
        GBiomes.getIds().stream().map(ResourceLocation::getPath).forEach(path -> {
            translationBuilder.add("biome.galosphere." + path, reformat(path));
        });
        translationBuilder.add("galosphere.midnightconfig.title", "Galosphere Config");
        translationBuilder.add("galosphere.midnightconfig.slowBuddingAmethystDestroySpeed", "Slowed Budding Amethyst Destroy Speed");
        translationBuilder.add("galosphere.midnightconfig.pillagerDropSilverIngot", "Pillager Drop Silver Ingots");
        translationBuilder.add("galosphere.midnightconfig.spectreFlareAncientCityLoot", "Spectre Flares spawn in Ancient City chest loot");
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
        translationBuilder.add("advancements.galosphere.use_spectre_flare.description", "Use a Spectre Flare");
        translationBuilder.add("advancements.galosphere.use_spectre_flare.title", "I spy with my little eye");
        translationBuilder.add("advancements.galosphere.warped_teleport.description", "Teleport to a Warped Anchor");
        translationBuilder.add("advancements.galosphere.warped_teleport.title", "What is this Place?");
        translationBuilder.add("attribute.name.generic.illager_resistance", "Illager Resistance");
        translationBuilder.add("item.minecraft.potion.effect.astral", "Potion of Astral");
        translationBuilder.add("item.minecraft.splash_potion.effect.astral", "Splash Potion of Astral");
        translationBuilder.add("item.minecraft.lingering_potion.effect.astral", "Lingering Potion of Astral");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.astral", "Arrow of Astral");
        translationBuilder.add("itemGroup.galosphere.galosphere", "Galosphere");
        translationBuilder.add("upgrade.galosphere.silver_upgrade", "Silver Upgrade");
        translationBuilder.add("item.galosphere.smithing_template.silver_upgrade.applies_to", "Leather Equipment");
        translationBuilder.add("item.galosphere.smithing_template.silver_upgrade.ingredients", "Silver Ingot");
        translationBuilder.add("item.galosphere.smithing_template.silver_upgrade.base_slot_description", "Add Leather Armor");
        translationBuilder.add("item.galosphere.smithing_template.silver_upgrade.additions_slot_description", "Add Silver Ingot");
        translationBuilder.add("upgrade.galosphere.preserved_upgrade", "Preserved Upgrade");
        translationBuilder.add("item.galosphere.smithing_template.preserved_upgrade.applies_to", "Everything");
        translationBuilder.add("item.galosphere.smithing_template.preserved_upgrade.ingredients", "Pink Salt Shard");
        translationBuilder.add("item.galosphere.smithing_template.preserved_upgrade.base_slot_description", "Add an item");
        translationBuilder.add("item.galosphere.smithing_template.preserved_upgrade.additions_slot_description", "Add Pink Salt Shard");
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
