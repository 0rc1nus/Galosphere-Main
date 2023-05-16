package net.orcinus.galosphere.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.orcinus.galosphere.entities.Sparkle;

public class PickaxeDispenseItemBehavior extends OptionalDispenseItemBehavior {

    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        Level level = source.getLevel();
        if (!level.isClientSide()) {
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            this.setSuccess(extractItemFromEntity((ServerLevel)level, blockpos, stack));
            if (this.isSuccess() && stack.hurt(1, level.getRandom(), null)) {
                stack.setCount(0);
            }
        }

        return stack;
    }

    private static boolean extractItemFromEntity(ServerLevel world, BlockPos blockPos, ItemStack stack) {
        for (Sparkle livingentity : world.getEntitiesOfClass(Sparkle.class, new AABB(blockPos), EntitySelector.NO_SPECTATORS)) {
            if (livingentity != null) {
                if (livingentity.getCrystaltype() != Sparkle.CrystalType.NONE) {
                    livingentity.extractShard(stack);
                    world.gameEvent(null, GameEvent.SHEAR, blockPos);
                    return true;
                }
            }
        }

        return false;
    }

}
