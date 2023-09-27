package net.orcinus.galosphere.mixin.access;

import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Projectile.class)
public interface ProjectileAccessor {
    @Accessor
    boolean isLeftOwner();

    @Accessor
    boolean isHasBeenShot();

    @Accessor
    void setLeftOwner(boolean leftOwner);

    @Accessor
    void setHasBeenShot(boolean hasBeenShot);

    @Invoker
    boolean callCheckLeftOwner();
}
