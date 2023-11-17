package net.orcinus.galosphere.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.orcinus.galosphere.util.PreservedShulkerBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntity.class)
public class ShulkerBoxBlockEntityMixin implements PreservedShulkerBox {
    @Unique
    private boolean preserved;

    @Inject(at = @At("TAIL"), method = "load")
    private void G$load(CompoundTag tag, CallbackInfo ci) {
        this.setPreserved(tag.getBoolean("Preserved"));
    }

    @Inject(at = @At("TAIL"), method = "saveAdditional")
    private void G$saveAdditional(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("Preserved", this.isPreserved());
    }

    @Override
    public void setPreserved(boolean preserved) {
        this.preserved = preserved;
    }

    @Override
    public boolean isPreserved() {
        return this.preserved;
    }
}
