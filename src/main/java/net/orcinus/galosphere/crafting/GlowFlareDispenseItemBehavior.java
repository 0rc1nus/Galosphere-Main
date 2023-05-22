package net.orcinus.galosphere.crafting;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.orcinus.galosphere.entities.GlowFlare;

public class GlowFlareDispenseItemBehavior extends OptionalDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
        Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
        GlowFlare glowFlare = new GlowFlare(blockSource.getLevel(), itemStack, blockSource.x(), blockSource.y(), blockSource.x(), true);
        DispenseItemBehavior.setEntityPokingOutOfBlock(blockSource, glowFlare, direction);
        glowFlare.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 0.5f, 1.0f);
        blockSource.getLevel().addFreshEntity(glowFlare);
        itemStack.shrink(1);
        return itemStack;
    }

    @Override
    protected void playSound(BlockSource blockSource) {
        blockSource.getLevel().levelEvent(1004, blockSource.getPos(), 0);
    }

}
