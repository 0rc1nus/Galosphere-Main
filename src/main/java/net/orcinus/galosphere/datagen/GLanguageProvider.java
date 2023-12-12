package net.orcinus.galosphere.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.items.SilverSmithingTemplateItem;

import java.util.function.Predicate;

public class GLanguageProvider extends LanguageProvider {

    public GLanguageProvider(PackOutput output) {
        super(output, Galosphere.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        GBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            this.add(block, reformat(ForgeRegistries.BLOCKS.getKey(block).getPath()));
        });
        GItems.ITEMS.getEntries().stream().map(RegistryObject::get).filter(Predicate.not(BlockItem.class::isInstance).or(ItemNameBlockItem.class::isInstance)).filter(Predicate.not(SilverSmithingTemplateItem.class::isInstance).and(Predicate.not(GItems.PRESERVED_TEMPLATE.get()::equals))).forEach(item -> {
            if (item != GItems.PRESERVED_TEMPLATE.get() || item != GItems.SILVER_UPGRADE_SMITHING_TEMPLATE.get()) {
                this.add(item, reformat(ForgeRegistries.ITEMS.getKey(item).getPath()));
            }
        });
        GEntityTypes.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get).forEach(entityType -> {
            this.add(entityType, reformat(ForgeRegistries.ENTITY_TYPES.getKey(entityType).getPath()));
        });
        GMobEffects.MOB_EFFECTS.getEntries().stream().map(RegistryObject::get).forEach(mobEffect -> {
            this.add(mobEffect, reformat(ForgeRegistries.MOB_EFFECTS.getKey(mobEffect).getPath()));
        });
        this.add("item.galosphere.preserved", "Preserved");
        this.add("item.galosphere.silver_bomb.duration", "Duration");
        this.add("item.galosphere.silver_bomb.explosion", "Explosion");
        this.add("item.galosphere.silver_bomb.bouncy", "Bouncy");
        this.add("subtitles.block.monstrometer.activate", "Monstrometer activates");
        this.add("subtitles.block.monstrometer.charge", "Monstrometer is charged");
        this.add("subtitles.block.monstrometer.deactivate", "Monstrometer deactivates");
        this.add("subtitles.block.lumiere.compost", "Composter filled with Lumiere Shard");
        this.add("subtitles.entity.specterpillar.death", "Specterpillar dies");
        this.add("subtitles.entity.specterpillar.hurt", "Specterpillar hurts");
        this.add("subtitles.entity.spectre.ambient", "Spectre chirps");
        this.add("subtitles.entity.spectre.death", "Spectre dies");
        this.add("subtitles.entity.spectre.hurt", "Spectre hurts");
        this.add("subtitles.entity.spectre.lock_to_spyglass", "Spyglass locks to Spectre");
        this.add("subtitles.entity.spectre.receive_item", "Spectre receives Item");
        this.add("subtitles.entity.berserker.roar", "Berserker roars");
        this.add("subtitles.entity.berserker.idle", "Berserker grunts");
        this.add("subtitles.entity.berserker.smash", "Berserker smashes");
        this.add("subtitles.entity.berserker.step", "Berserker steps");
        this.add("subtitles.entity.berserker.hurt", "Berserker hurts");
        this.add("subtitles.entity.berserker.death", "Berserker dies");
        this.add("subtitles.entity.berserker.summoning", "Berserker summons");
        this.add("subtitles.entity.berserker.punch", "Berserker hits");
        this.add("subtitles.entity.preserved.death", "Preserved dies");
        this.add("subtitles.entity.preserved.idle", "Preserved groans");
        this.add("subtitles.entity.preserved.hurt", "Preserved hurts");
        this.add("subtitles.entity.preserved.emerge", "Preserved emerges");
        this.add("subtitles.entity.pink_salt_pillar.emerge", "Pink salt pillar pierces");
        GBiomes.getIds().forEach(resourceLocation -> {
            this.add("biome.galosphere." + resourceLocation.getPath(), reformat(resourceLocation.getPath()));
        });
        this.add("galosphere.midnightconfig.title", "Galosphere Config");
        this.add("galosphere.midnightconfig.slowBuddingAmethystDestroySpeed", "Slowed Budding Amethyst Destroy Speed");
        this.add("galosphere.midnightconfig.pillagerDropSilverIngot", "Pillager Drop Silver Ingots");
        this.add("galosphere.midnightconfig.spectreFlareAncientCityLoot", "Spectre Flares spawn in ancient city chest loot");
        this.add("advancements.galosphere.crystal_lamps.description", "Have all Crystal Lamps in your inventory");
        this.add("advancements.galosphere.crystal_lamps.title", "Balanced, As All Things Should Be");
        this.add("advancements.galosphere.light_spread.description", "Deploy a Glow Flare");
        this.add("advancements.galosphere.light_spread.title", "Spread the Light!");
        this.add("advancements.galosphere.lumiere_compost.description", "Create Glowstone Dust by Composting with a Lumiere Shard");
        this.add("advancements.galosphere.lumiere_compost.title", "Fragility of Light");
        this.add("advancements.galosphere.silver_bomb.description", "Construct a Silver Bomb");
        this.add("advancements.galosphere.silver_bomb.title", "It's About Drive, It's About Power");
        this.add("advancements.galosphere.silver_ingot.description", "Obtain a Silver Ingot");
        this.add("advancements.galosphere.silver_ingot.title", "Multi-Disciplined");
        this.add("advancements.galosphere.sterling_armor.description", "Don a full suit of Sterling Armor");
        this.add("advancements.galosphere.sterling_armor.title", "Looking Good, Partner!");
        this.add("advancements.galosphere.use_spectre_spyglass.description", "Spectate a Spectre");
        this.add("advancements.galosphere.use_spectre_spyglass.title", "Watchdog");
        this.add("advancements.galosphere.use_spectre_flare.description", "Use a spectre flare");
        this.add("advancements.galosphere.use_spectre_flare.title", "I spy with my little eye");
        this.add("advancements.galosphere.warped_teleport.description", "Teleport to a Warped Anchor");
        this.add("advancements.galosphere.warped_teleport.title", "What is this place?");
        this.add("attribute.name.generic.illager_resistance", "Illager Resistance");
        this.add("item.minecraft.potion.effect.astral", "Potion of Astral");
        this.add("item.minecraft.splash_potion.effect.astral", "Splash Potion of Astral");
        this.add("item.minecraft.lingering_potion.effect.astral", "Lingering Potion of Astral");
        this.add("item.minecraft.lingering_potion.tipped_arrow.astral", "Arrow of Astral");
        this.add("itemGroup.galosphere.galosphere", "Galosphere");
        this.add("upgrade.galosphere.silver_upgrade", "Silver Upgrade");
        this.add("item.galosphere.smithing_template.silver_upgrade.applies_to", "Leather Equipment");
        this.add("item.galosphere.smithing_template.silver_upgrade.ingredients", "Silver Ingot");
        this.add("item.galosphere.smithing_template.silver_upgrade.base_slot_description", "Add Leather Armor");
        this.add("item.galosphere.smithing_template.silver_upgrade.additions_slot_description", "Add Silver Ingot");
        this.add("upgrade.galosphere.preserved_upgrade", "Preserved Upgrade");
        this.add("item.galosphere.smithing_template.preserved_upgrade.applies_to", "All items");
        this.add("item.galosphere.smithing_template.preserved_upgrade.ingredients", "Pink Salt Shard");
        this.add("item.galosphere.smithing_template.preserved_upgrade.base_slot_description", "Add an item");
        this.add("item.galosphere.smithing_template.preserved_upgrade.additions_slot_description", "Add Pink Salt Shard");
        this.add("container.galosphere.combustion_table", "Combustion Table");
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
