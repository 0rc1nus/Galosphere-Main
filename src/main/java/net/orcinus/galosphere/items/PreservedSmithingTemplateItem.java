package net.orcinus.galosphere.items;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;
import net.orcinus.galosphere.Galosphere;

import java.util.List;

public class PreservedSmithingTemplateItem extends SmithingTemplateItem {
    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final Component PRESERVED_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade", Galosphere.id("preserved_upgrade"))).withStyle(TITLE_FORMAT);
    private static final Component PRESERVED_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", Galosphere.id( "smithing_template.preserved_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMAT);
    private static final Component PRESERVED_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId("item", Galosphere.id( "smithing_template.preserved_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMAT);
    private static final Component PRESERVED_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", Galosphere.id( "smithing_template.preserved_upgrade.base_slot_description")));
    private static final Component PRESERVED_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", Galosphere.id( "smithing_template.preserved_upgrade.additions_slot_description")));
    private static final ResourceLocation EMPTY_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
    private static final ResourceLocation EMPTY_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
    private static final ResourceLocation EMPTY_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
    private static final ResourceLocation EMPTY_SLOT_HOE = new ResourceLocation("item/empty_slot_hoe");
    private static final ResourceLocation EMPTY_SLOT_AXE = new ResourceLocation("item/empty_slot_axe");
    private static final ResourceLocation EMPTY_SLOT_SWORD = new ResourceLocation("item/empty_slot_sword");
    private static final ResourceLocation EMPTY_SLOT_SHOVEL = new ResourceLocation("item/empty_slot_shovel");
    private static final ResourceLocation EMPTY_SLOT_PICKAXE = new ResourceLocation("item/empty_slot_pickaxe");
    private static final ResourceLocation EMPTY_SLOT_BLOCK = Galosphere.id("item/empty_slot_block");
    private static final ResourceLocation EMPTY_SLOT_SHARD = Galosphere.id("item/empty_slot_shard");

    public PreservedSmithingTemplateItem() {
        super(PRESERVED_UPGRADE_APPLIES_TO, PRESERVED_UPGRADE_INGREDIENTS, PRESERVED_UPGRADE, PRESERVED_UPGRADE_BASE_SLOT_DESCRIPTION, PRESERVED_UPGRADE_ADDITIONS_SLOT_DESCRIPTION, createUpgradeIconList(), createNetheriteUpgradeMaterialList());
    }

    private static List<ResourceLocation> createUpgradeIconList() {
        return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_SWORD, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_PICKAXE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_AXE, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_SHOVEL, EMPTY_SLOT_BLOCK);
    }

    private static List<ResourceLocation> createNetheriteUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_SHARD);
    }
}
