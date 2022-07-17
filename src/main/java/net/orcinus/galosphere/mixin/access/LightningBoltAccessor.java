package net.orcinus.galosphere.mixin.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LightningBolt.class)
public interface LightningBoltAccessor {
    @Invoker
    BlockPos callGetStrikePosition();
}
