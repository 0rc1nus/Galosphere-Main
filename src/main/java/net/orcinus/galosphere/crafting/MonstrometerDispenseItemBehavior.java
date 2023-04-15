package net.orcinus.galosphere.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.orcinus.galosphere.blocks.MonstrometerBlock;

public class MonstrometerDispenseItemBehavior extends OptionalDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        BlockPos pos = source.getPos().relative(direction);
        Level world = source.getLevel();
        BlockState state = world.getBlockState(pos);

        setSuccess(false);

        if (state.getBlock() instanceof MonstrometerBlock && !MonstrometerBlock.isCharged(state)) {
            MonstrometerBlock.setCharged(state, world, pos);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
            setSuccess(true);
            stack.shrink(1);
        }

        return stack;
    }

}
