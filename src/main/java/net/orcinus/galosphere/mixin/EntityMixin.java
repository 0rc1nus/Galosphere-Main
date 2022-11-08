package net.orcinus.galosphere.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.init.GItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public class EntityMixin {

    @ModifyVariable(at = @At("HEAD"), method = "turn", ordinal = 0, argsOnly = true)
    private double I$XRotate(double value) {
        return this.spectrePerspectiveValue(value);
    }

    @ModifyVariable(at = @At("HEAD"), method = "turn", ordinal = 1, argsOnly = true)
    private double I$YRotate(double value) {
        return this.spectrePerspectiveValue(value);
    }

    @Unique
    private double spectrePerspectiveValue(double value) {
        boolean flag = (Entity) (Object) this instanceof Player player && this.isFirstPerspective() && SpectreBoundSpyglass.canUseSpectreBoundedSpyglass(player.getUseItem(), player);
        return flag ? value * 8 : value;
    }

    @OnlyIn(Dist.CLIENT)
    private boolean isFirstPerspective() {
        return Minecraft.getInstance().options.getCameraType().isFirstPerson();
    }

}
