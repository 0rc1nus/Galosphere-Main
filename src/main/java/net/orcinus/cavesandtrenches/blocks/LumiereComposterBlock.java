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
        double d0 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
        double d1 = (double)(world.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
        double d2 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
        for (int i = 0; i <= Mth.nextInt(world.random, 1, 4); i++) {
            ItemEntity itementity = new ItemEntity(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Items.GLOWSTONE_DUST));
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

    public static BlockState addItem(BlockState p_51984_, LevelAccessor p_51985_, BlockPos p_51986_, ItemStack p_51987_) {
        int i = p_51984_.getValue(LEVEL);
        float f = COMPOSTABLES.getFloat(p_51987_.getItem());
        if ((i != 0 || !(f > 0.0F)) && !(p_51985_.getRandom().nextDouble() < (double)f)) {
            return p_51984_;
        } else {
            int j = i + 1;
            BlockState blockstate = p_51984_.setValue(LEVEL, j);
            p_51985_.setBlock(p_51986_, blockstate, 3);
            if (j == 7) {
                p_51985_.scheduleTick(p_51986_, p_51984_.getBlock(), 20);
            }

            return blockstate;
        }
    }

    @Override
    public WorldlyContainer getContainer(BlockState p_51956_, LevelAccessor p_51957_, BlockPos p_51958_) {
        int i = p_51956_.getValue(LEVEL);
        if (i == 8) {
            return new LumiereComposterBlock.OutputContainer(p_51956_, p_51957_, p_51958_, new ItemStack(Items.BONE_MEAL));
        } else {
            return (i < 7 ? new LumiereComposterBlock.InputContainer(p_51956_, p_51957_, p_51958_) : new LumiereComposterBlock.EmptyContainer());
        }
    }

    static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
        public EmptyContainer() {
            super(0);
        }

        public int[] getSlotsForFace(Direction p_52012_) {
            return new int[0];
        }

        public boolean canPlaceItemThroughFace(int p_52008_, ItemStack p_52009_, @Nullable Direction p_52010_) {
            return false;
        }

        public boolean canTakeItemThroughFace(int p_52014_, ItemStack p_52015_, Direction p_52016_) {
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

        public int getMaxStackSize() {
            return 1;
        }

        public int[] getSlotsForFace(Direction p_52032_) {
            return p_52032_ == Direction.UP ? new int[]{0} : new int[0];
        }

        public boolean canPlaceItemThroughFace(int p_52028_, ItemStack p_52029_, @Nullable Direction p_52030_) {
            return !this.changed && p_52030_ == Direction.UP && ComposterBlock.COMPOSTABLES.containsKey(p_52029_.getItem());
        }

        public boolean canTakeItemThroughFace(int p_52034_, ItemStack p_52035_, Direction p_52036_) {
            return false;
        }

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

        public OutputContainer(BlockState p_52042_, LevelAccessor p_52043_, BlockPos p_52044_, ItemStack p_52045_) {
            super(p_52045_);
            this.state = p_52042_;
            this.level = p_52043_;
            this.pos = p_52044_;
        }

        public int getMaxStackSize() {
            return 1;
        }

        public int[] getSlotsForFace(Direction p_52053_) {
            return p_52053_ == Direction.DOWN ? new int[]{0} : new int[0];
        }

        public boolean canPlaceItemThroughFace(int p_52049_, ItemStack p_52050_, @Nullable Direction p_52051_) {
            return false;
        }

        public boolean canTakeItemThroughFace(int p_52055_, ItemStack p_52056_, Direction p_52057_) {
            return !this.changed && p_52057_ == Direction.DOWN && p_52056_.is(Items.GLOWSTONE_DUST);
        }

        public void setChanged() {
            LumiereComposterBlock.empty(this.state, this.level, this.pos);
            this.changed = true;
        }
    }
}
