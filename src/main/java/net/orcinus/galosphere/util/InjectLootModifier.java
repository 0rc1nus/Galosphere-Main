package net.orcinus.galosphere.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class InjectLootModifier extends LootModifier {
    public static final Codec<InjectLootModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).and(ItemStack.CODEC.fieldOf("item").forGetter(injectLootModifier -> injectLootModifier.itemStack)).apply(inst, InjectLootModifier::new));
    private final ItemStack itemStack;

    protected InjectLootModifier(LootItemCondition[] conditionsIn, ItemStack itemStack) {
        super(conditionsIn);
        this.itemStack = itemStack;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ItemStack stack = this.itemStack.copy();
        if (stack.getCount() < stack.getMaxStackSize()) {
            generatedLoot.add(stack);
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

}
