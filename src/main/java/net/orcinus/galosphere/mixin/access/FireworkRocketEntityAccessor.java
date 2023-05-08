package net.orcinus.galosphere.mixin.access;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FireworkRocketEntity.class)
public interface FireworkRocketEntityAccessor {

    @Accessor
    static EntityDataAccessor<ItemStack> getDATA_ID_FIREWORKS_ITEM() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    static EntityDataAccessor<Boolean> getDATA_SHOT_AT_ANGLE() {
        throw new UnsupportedOperationException();
    }

    @Accessor("life")
    void setLife(int life);

    @Accessor("lifetime")
    void setLifeTime(int lifeTime);

    @Accessor
    int getLife();

    @Accessor
    int getLifetime();
}
