package net.orcinus.galosphere.mixin.access;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Gui.class)
public interface GuiAccessor {
    @Accessor
    int getDisplayHealth();

    @Accessor
    long getHealthBlinkTime();

    @Invoker
    Player callGetCameraPlayer();
}
