package net.orcinus.galosphere.crafting;

import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.entities.GlowFlare;

public class GlowFlareDispenseItemBehavior extends AbstractProjectileDispenseBehavior {

    @Override
    protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
        return new GlowFlare(level, position.x(), position.y(), position.z());
    }

}
