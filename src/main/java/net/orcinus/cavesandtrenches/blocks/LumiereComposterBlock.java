package net.orcinus.cavesandtrenches.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class LumiereComposterBlock extends ComposterBlock {

    public LumiereComposterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int i = state.getValue(LEVEL);
        ItemStack itemstack = player.getItemInHand(hand);
        if (i < 8 && COMPOSTABLES.containsKey(itemstack.getItem())) {
            if (i < 7 && !world.isClientSide) {
                BlockState blockstate = addItem(state, world, pos, itemstack);
                world.levelEvent(1500, pos, state != blockstate ? 1 : 0);
                player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else if (i == 8) {
            extractGlowstoneDust(state, world, pos);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public static BlockState extractGlowstoneDust(BlockState state, Level world, BlockPos pos) {
        if (!world.isClientSide) {
            double d0 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
            double d1 = (double)(world.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
            double d2 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
            ItemEntity itementity = new ItemEntity(world, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(Items.GLOWSTONE_DUST));
            itementity.setDefaultPickUpDelay();
            world.addFreshEntity(itementity);
        }

        BlockState blockstate = empty(state, world, pos);
        world.playSound(null, pos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        return blockstate;
    }

    public static BlockState empty(BlockState state, LevelAccessor world, BlockPos pos) {
        BlockState blockstate = Blocks.COMPOSTER.defaultBlockState().setValue(ComposterBlock.LEVEL, 0);
        world.setBlock(pos, blockstate, 3);
        return blockstate;
    }

    public static BlockState addItem(BlockState state, LevelAccessor world, BlockPos pos, ItemStack stack) {
        int i = state.getValue(LEVEL);
        float f = COMPOSTABLES.getFloat(stack.getItem());
        if ((i != 0 || !(f > 0.0F)) && !(world.getRandom().nextDouble() < (double)f)) {
            return state;
        } else {
            int j = i + 1;
            BlockState blockstate = state.setValue(LEVEL, j);
            world.setBlock(pos, blockstate, 3);
            if (j == 7) {
                world.scheduleTick(pos, state.getBlock(), 20);
            }

            return blockstate;
        }
    }

    @Override
    public WorldlyContainer getContainer(BlockState state, LevelAccessor world, BlockPos pos) {
        int i = state.getValue(LEVEL);
        if (i == 8) {
            return new LumiereComposterBlock.OutputContainer(state, world, pos, new ItemStack(Items.GLOWSTONE_DUST));
        } else {
            return (i < 7 ? new LumiereComposterBlock.InputContainer(state, world, pos) : new LumiereComposterBlock.EmptyContainer());
        }
    }

    static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
        public EmptyContainer() {
            super(0);
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            return new int[0];
        }

        @Override
        public boolean canPlaceItemThroughFace(int p_52008_, ItemStack stack, @Nullable Direction direction) {
            return false;
        }

        @Override
        public boolean canTakeItemThroughFace(int p_52014_, ItemStack stack, Direction direction) {
            return false;
        }
    }

    static class InputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public InputContainer(BlockState p_52022_, LevelAccessor p_52023_, BlockPos p_52024_) {
            super(1);
            this.state = p_52022_;
            this.level = p_52023_;
            this.pos = p_52024_;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public int[] getSlotsForFace(Direction p_52032_) {
            return p_52032_ == Direction.UP ? new int[]{0} : new int[0];
        }

        @Override
        public boolean canPlaceItemThroughFace(int p_52028_, ItemStack p_52029_, @Nullable Direction p_52030_) {
            return !this.changed && p_52030_ == Direction.UP && ComposterBlock.COMPOSTABLES.containsKey(p_52029_.getItem());
        }

        @Override
        public boolean canTakeItemThroughFace(int p_52034_, ItemStack p_52035_, Direction p_52036_) {
            return false;
        }

        @Override
        public void setChanged() {
            ItemStack itemstack = this.getItem(0);
            if (!itemstack.isEmpty()) {
                this.changed = true;
                BlockState blockstate = LumiereComposterBlock.addItem(this.state, this.level, this.pos, itemstack);
                this.level.levelEvent(1500, this.pos, blockstate != this.state ? 1 : 0);
                this.removeItemNoUpdate(0);
            }

        }
    }

    static class OutputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public OutputContainer(BlockState state, LevelAccessor world, BlockPos pos, ItemStack stack) {
            super(stack);
            this.state = state;
            this.level = world;
            this.pos = pos;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            return direction == Direction.DOWN ? new int[]{0} : new int[0];
        }

        @Override
        public boolean canPlaceItemThroughFace(int p_52049_, ItemStack p_52050_, @Nullable Direction p_52051_) {
            return false;
        }

        @Override
        public boolean canTakeItemThroughFace(int p_52055_, ItemStack p_52056_, Direction direection) {
            return !this.changed && direection == Direction.DOWN && p_52056_.is(Items.GLOWSTONE_DUST);
        }

        @Override
        public void setChanged() {
            LumiereComposterBlock.empty(this.state, this.level, this.pos);
            this.changed = true;
        }
    }
}
