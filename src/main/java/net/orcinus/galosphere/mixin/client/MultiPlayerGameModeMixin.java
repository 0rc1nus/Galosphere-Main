package net.orcinus.galosphere.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.orcinus.galosphere.api.FayBoundedSpyglass;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Shadow @Final private Minecraft minecraft;

    @Inject(at = @At("HEAD"), method = "hasExperience", cancellable = true)
    private void GE$hasExperience(CallbackInfoReturnable<Boolean> cir) {
        Optional.ofNullable(this.minecraft.player).filter(FayBoundedSpyglass.class::isInstance).map(FayBoundedSpyglass.class::cast).filter(FayBoundedSpyglass::isUsingFayBoundedSpyglass).ifPresent(localPlayer -> cir.setReturnValue(false));
    }

}
