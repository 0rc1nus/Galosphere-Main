package net.orcinus.galosphere.items;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.entities.GlowFlare;
import net.orcinus.galosphere.init.GCriteriaTriggers;

public class GlowFlareItem extends Item {

    public GlowFlareItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if (!level.isClientSide) {
            if (useOnContext.getPlayer() instanceof ServerPlayer serverPlayer) {
                GCriteriaTriggers.LIGHT_SPREAD.trigger(serverPlayer);
            }
            ItemStack itemStack = useOnContext.getItemInHand();
            Vec3 vec3 = useOnContext.getClickLocation();
            Direction direction = useOnContext.getClickedFace();
            GlowFlare glowFlare = new GlowFlare(level, useOnContext.getPlayer(), vec3.x + (double)direction.getStepX() * 0.15, vec3.y + (double)direction.getStepY() * 0.15, vec3.z + (double)direction.getStepZ() * 0.15, itemStack);
            level.addFreshEntity(glowFlare);
            itemStack.shrink(1);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

}
