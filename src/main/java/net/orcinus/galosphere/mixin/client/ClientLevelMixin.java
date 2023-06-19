package net.orcinus.galosphere.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.orcinus.galosphere.init.GBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @ModifyVariable(at = @At("HEAD"), method = "playSound", ordinal = 0, argsOnly = true)
    private float G$playSound(float value, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl, long l) {
        boolean flag = false;
        Level $this = (ClientLevel) (Object) this;
        BlockPos blockPos = BlockPos.containing(d, e, f);
        for (Direction direction : Direction.values()) {
            if ($this.getBlockState(blockPos).getRenderShape() == RenderShape.INVISIBLE || $this.getBlockState(blockPos).is(GBlockTags.OBFUSCATES_SOUND_WAVES) || !$this.getBlockState(blockPos.relative(direction)).is(GBlockTags.OBFUSCATES_SOUND_WAVES)) {
                continue;
            }
            flag = true;
        }
        return flag ? value * 0.1F : value;
    }

}
