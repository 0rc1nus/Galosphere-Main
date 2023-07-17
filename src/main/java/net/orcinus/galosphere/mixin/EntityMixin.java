package net.orcinus.galosphere.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.blocks.ShadowFrameBlock;
import net.orcinus.galosphere.blocks.blockentities.ShadowFrameBlockEntity;
import net.orcinus.galosphere.entities.Spectre;
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
        boolean flag = (Entity) (Object) this instanceof Player player && this.isFirstPerspective() && SpectreBoundSpyglass.canUseSpectreBoundSpyglass(player.getUseItem());
        return flag ? value * 8 : value;
    }

    @Environment(EnvType.CLIENT)
    private boolean isFirstPerspective() {
        return Minecraft.getInstance().options.getCameraType().isFirstPerson() && Minecraft.getInstance().getCameraEntity() instanceof Spectre;
    }

    @ModifyVariable(at = @At("STORE"), method = "spawnSprintParticle")
    private BlockState G$spawnSprintParticle(BlockState value) {
        Entity $this = (Entity) (Object) this;
        BlockPos blockPos = $this.getOnPosLegacy();
        if ($this.level().getBlockEntity(blockPos) instanceof ShadowFrameBlockEntity shadowFrameBlockEntity && value.getValue(ShadowFrameBlock.FILLED)) {
            return shadowFrameBlockEntity.getCopiedState();
        }
        return value;
    }

}
