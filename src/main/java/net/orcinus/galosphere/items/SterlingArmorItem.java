package net.orcinus.galosphere.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.init.GAttributes;
import net.orcinus.galosphere.init.GItems;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public class SterlingArmorItem extends ArmorItem {
    private static final SterlingArmorMaterial material = new SterlingArmorMaterial();
    private static final String HELMET_TEXTURE = Galosphere.MODID + ":textures/entity/sterling_helmet.png";
    private static final String LEGS_TEXTURE = Galosphere.MODID + ":textures/entity/sterling_armor_2.png";
    private static final String TEXTURE = Galosphere.MODID + ":textures/entity/sterling_armor.png";

    public SterlingArmorItem(EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String name;
        switch (slot) {
            case HEAD -> name = HELMET_TEXTURE;
            case LEGS -> name = LEGS_TEXTURE;
            default -> name = TEXTURE;
        }
        return name;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = UUID.fromString("fb112e48-f201-4a6f-ae86-0f11df4f8e79");
        UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
        UUID defaultUUid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        double amount = this.getExplosionResistance(slot);
        builder.put(Attributes.ARMOR, new AttributeModifier(defaultUUid, "Armor modifier", this.getMaterial().getDefenseForSlot(slot), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(defaultUUid, "Armor toughness", this.getMaterial().getToughness(), AttributeModifier.Operation.ADDITION));
        builder.put(GAttributes.EXPLOSION_RESISTANCE.get(), new AttributeModifier(uuid, "Armor explosion resistance", amount, AttributeModifier.Operation.ADDITION));
        return slot == this.slot ? builder.build() : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Nullable
            @Override
            public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                return armorSlot == EquipmentSlot.HEAD ? new SterlingArmorModel<>(SterlingArmorModel.createBodyLayer().bakeRoot()) : IItemRenderProperties.super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
            }
        });
    }

    public float getExplosionResistance(EquipmentSlot slot) {
        float[] array = new float[]{3, 5, 4, 2};
        return array[slot.getIndex()];
    }

    private static class SterlingArmorMaterial implements ArmorMaterial {
        private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return HEALTH_PER_SLOT[slot.getIndex()] * 12;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            int[] slots = new int[]{1, 3, 4, 1};
            return slots[slot.getIndex()];
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
            return Ingredient.of(GItems.SILVER_INGOT.get());
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
