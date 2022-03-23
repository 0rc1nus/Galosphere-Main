package net.orcinus.galosphere.items;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.init.CTItems;
import org.jetbrains.annotations.Nullable;

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
        if (slot == EquipmentSlot.HEAD) {
            return HELMET_TEXTURE;
        } else if (slot == EquipmentSlot.LEGS) {
            return LEGS_TEXTURE;
        } else {
            return TEXTURE;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {

            @Nullable
            @Override
            public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                return armorSlot == EquipmentSlot.HEAD ? new SterlingArmorModel<>(SterlingArmorModel.createArmorLayer().bakeRoot()) : IItemRenderProperties.super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
            }
        });
    }

    public float getExplosionResistance(EquipmentSlot slot) {
        return material.getDefenseForSlot(slot);
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
            return Ingredient.of(CTItems.SILVER_INGOT.get());
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
