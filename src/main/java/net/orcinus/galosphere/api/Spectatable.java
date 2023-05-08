package net.orcinus.galosphere.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public interface Spectatable {

    UUID getManipulatorUUID();

    void setManipulatorUUID(UUID uuid);

    void spectateTick(UUID uuid);

    default boolean cancelKeybindings() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    default boolean matchesClientPlayerUUID() {
        return Minecraft.getInstance().player != null && Minecraft.getInstance().player.getUUID().equals(this.getManipulatorUUID());
    }

    default void copyPlayerRotation(LivingEntity livingEntity, Player player) {
        livingEntity.setYRot(player.getYRot());
        livingEntity.yRotO = livingEntity.getYRot();
        livingEntity.setXRot(player.getXRot() * 0.5F);
        livingEntity.setYRot(livingEntity.getYRot() % 360.0f);
        livingEntity.setXRot(livingEntity.getXRot() % 360.0f);
        livingEntity.yBodyRot = livingEntity.getYRot();
        livingEntity.yHeadRot = livingEntity.getYRot();
    }

}