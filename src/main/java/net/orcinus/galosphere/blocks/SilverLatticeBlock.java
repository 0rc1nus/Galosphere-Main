package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.init.GBlocks;

public class SilverLatticeBlock extends IronBarsBlock {

    public SilverLatticeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (stack.is(Items.GLOW_BERRIES) && blockState.is(GBlocks.SILVER_LATTICE.get())) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            level.setBlock(blockPos, GBlocks.GLOW_BERRIES_SILVER_LATTICE.get().withPropertiesOf(blockState), 2);
            level.playSound(null, blockPos, SoundEvents.CAVE_VINES_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}