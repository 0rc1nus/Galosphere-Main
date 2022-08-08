package net.orcinus.galosphere.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.blocks.LumiereComposterBlock;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ComposterBlock.class)
public class ComposterBlockMixin {

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    private void G$use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        InteractionHand offHand = InteractionHand.OFF_HAND;
        ItemStack offhandStack = player.getItemInHand(offHand);
        if (offhandStack.is(GItems.LUMIERE_SHARD) && blockState.getValue(ComposterBlock.LEVEL) > 0 && blockState.getValue(ComposterBlock.LEVEL) < 8) {
            if (!player.getAbilities().instabuild) {
                offhandStack.shrink(1);
            }
            level.setBlock(blockPos, GBlocks.LUMIERE_COMPOSTER.defaultBlockState().setValue(LumiereComposterBlock.LEVEL, blockState.getValue(ComposterBlock.LEVEL)), 2);
            level.playSound(null, blockPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
            player.swing(offHand);
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }

}
