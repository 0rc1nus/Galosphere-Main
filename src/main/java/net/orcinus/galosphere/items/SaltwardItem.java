package net.orcinus.galosphere.items;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.entities.PinkSaltPillar;
import net.orcinus.galosphere.init.GEntityTypes;

public class SaltwardItem extends Item {

    public SaltwardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        HitResult result = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        if (result.getType() == HitResult.Type.BLOCK) {
            PinkSaltPillar pinkSaltPillar = GEntityTypes.PINK_SALT_PILLAR.create(level);
            pinkSaltPillar.setPos(result.getLocation().x, result.getLocation().y, result.getLocation().z);
            level.addFreshEntity(pinkSaltPillar);
            HitResult endVec = player.pick(20.0F, 0.0F, false);
            Vec3 diff = endVec.getLocation().subtract(player.position());
            Vec3 adjusted = diff.normalize();
            player.setDeltaMovement(-adjusted.x * 2.0F, -adjusted.y * 1.2F, -adjusted.z * 2.0F);
            player.resetFallDistance();
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide);
        }
        return super.use(level, player, interactionHand);
    }
}
