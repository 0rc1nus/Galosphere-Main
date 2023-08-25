package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.blocks.blockentities.SilverBalanceBlockEntity;
import org.jetbrains.annotations.Nullable;

public class SilverBalanceBlock extends BaseEntityBlock {

    public SilverBalanceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState.is(blockState2.getBlock())) {
            return;
        }
        if (level.getBlockEntity(blockPos) instanceof SilverBalanceBlockEntity silverBalanceBlockEntity && !silverBalanceBlockEntity.isEmpty()) {
            ItemStack stack = silverBalanceBlockEntity.getItem(0);
            float f = 0.0F;
            float g = 0.0F;
            ItemEntity itemEntity = new ItemEntity(level, (double)blockPos.getX() + 0.5 + (double)f, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5 + (double)g, stack);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
            silverBalanceBlockEntity.clearContent();
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        ItemStack stack = player.getItemInHand(interactionHand);
        if (blockEntity instanceof SilverBalanceBlockEntity silverBalanceBlockEntity) {
            if (!silverBalanceBlockEntity.isEmpty()) {
                if (!level.isClientSide) {
                    silverBalanceBlockEntity.removeItem(player);
                }
                silverBalanceBlockEntity.clearContent();
                return InteractionResult.SUCCESS;
            }
            if (!level.isClientSide && !stack.isEmpty() && stack.getItem() instanceof BlockItem) {
                silverBalanceBlockEntity.addItem(player, player.getAbilities().instabuild ? stack.copy() : stack);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.playSound(null, blockPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 1);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        return blockEntity instanceof SilverBalanceBlockEntity silverBalanceBlockEntity ? this.calculateOutputSignal(silverBalanceBlockEntity) : 0;
    }

    private int calculateOutputSignal(SilverBalanceBlockEntity silverBalanceBlockEntity) {
        ItemStack stack = silverBalanceBlockEntity.getItem(0);
        Item item = stack.getItem();
        if (stack.isEmpty() || !(item instanceof BlockItem)) {
            return 0;
        }
        Block block = Block.byItem(item);
        float destroyTime = block.defaultDestroyTime();
        return destroyTime < 0 ? 0 : (int) Math.min(15, Math.max(1, destroyTime) * 2);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SilverBalanceBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
