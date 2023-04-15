package net.orcinus.galosphere.mixin.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.orcinus.galosphere.blocks.ChandelierBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBlockRenderTypes.class)
public class ItemBlockRenderTypesMixin {

    @Inject(at = @At("HEAD"), method = "getChunkRenderType", cancellable = true)
    private static void G$getChunkRenderType(BlockState blockState, CallbackInfoReturnable<RenderType> cir) {
        if (blockState.getBlock() instanceof ChandelierBlock) {
            cir.setReturnValue(blockState.getValue(ChandelierBlock.HALF) == DoubleBlockHalf.UPPER ? RenderType.translucent() : RenderType.cutout());
        }
    }

}
