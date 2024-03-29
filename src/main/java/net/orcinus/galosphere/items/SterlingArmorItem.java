package net.orcinus.galosphere.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.orcinus.galosphere.init.GAttributes;
import net.orcinus.galosphere.init.GItems;

import java.util.UUID;

public class SterlingArmorItem extends ArmorItem {
    private static final SterlingArmorMaterial material = new SterlingArmorMaterial();

    public SterlingArmorItem(Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = UUID.fromString("fb112e48-f201-4a6f-ae86-0f11df4f8e79");
        UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
        UUID defaultUUid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(defaultUUid, "Armor modifier", this.getMaterial().getDefenseForType(this.type), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(defaultUUid, "Armor toughness", this.getMaterial().getToughness(), AttributeModifier.Operation.ADDITION));
        builder.put(GAttributes.ILLAGER_RESISTANCE, new AttributeModifier(uuid, "Armor illager resistance", this.getInsurgentResistance(slot), AttributeModifier.Operation.ADDITION));
        return slot == this.type.getSlot() ? builder.build() : super.getDefaultAttributeModifiers(slot);
    }

    public float getInsurgentResistance(EquipmentSlot slot) {
        float[] array = new float[]{3, 5, 6, 2};
        return array[slot.getIndex()];
    }

    private static class SterlingArmorMaterial implements ArmorMaterial {
        private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

        @Override
        public int getDurabilityForType(Type type) {
            return HEALTH_PER_SLOT[type.getSlot().getIndex()] * 12;
        }

        @Override
        public int getDefenseForType(Type type) {
            int[] slots = new int[]{1, 3, 4, 1};
            return slots[type.getSlot().getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return 9;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_CHAIN;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(GItems.SILVER_INGOT);
        }

        @Override
        public String getName() {
            return "sterling";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }

    }
}
