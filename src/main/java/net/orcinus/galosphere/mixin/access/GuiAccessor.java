package net.orcinus.galosphere.mixin.access;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Gui.class)
public interface GuiAccessor {
    @Accessor
    int getDisplayHealth();

    @Accessor
    long getHealthBlinkTime();
}
