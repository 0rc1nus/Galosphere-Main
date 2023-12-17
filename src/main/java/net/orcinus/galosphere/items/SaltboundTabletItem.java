package net.orcinus.galosphere.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.galosphere.api.SaltboundFlying;
import net.orcinus.galosphere.entities.PinkSaltPillar;

public class SaltboundTabletItem extends Item {

    public SaltboundTabletItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        return Mth.color(0.737f, 1, 0.737f);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        HitResult result = player.pick(5.0F, 0.0F, false);
        if (result.getType() == HitResult.Type.BLOCK) {
            Vec3 location = result.getLocation();
            int cooldown;
            if ((!player.isShiftKeyDown() && player.distanceToSqr(result.getLocation()) <= 6D) || ((SaltboundFlying)player).isFlying()) {
                Vec3 position = player.position();
                ((SaltboundFlying)player).setFlying(true);
                level.addFreshEntity(new PinkSaltPillar(player.level(), position.x, position.y, position.z, 0, 0, player));
                Vec3 diff = player.pick(20, 0, false).getLocation().subtract(position);
                Vec3 adjusted = diff.normalize();
                player.setDeltaMovement(-adjusted.x * 2.5F, -adjusted.y * 1.2F, -adjusted.z * 2.5F);
                player.resetFallDistance();
                cooldown = 20;
            } else {
                double d = Math.min(location.y(), player.getY());
                double e = Math.max(location.y(), player.getY()) + 1.0;
                float f = (float)Mth.atan2(location.z() - player.getZ(), location.x() - player.getX());
                if (player.isShiftKeyDown()) {
                    float g;
                    int i;
                    for (i = 0; i < 5; ++i) {
                        g = f + (float)i * (float)Math.PI * 0.4f;
                        this.createPillar(player, player.getX() + (double)Mth.cos(g) * 1.5, player.getZ() + (double)Mth.sin(g) * 1.5, d, e, g, 0);
                    }
                    for (i = 0; i < 8; ++i) {
                        g = f + (float)i * (float)Math.PI * 2.0f / 8.0f + 1.2566371f;
                        this.createPillar(player, player.getX() + (double)Mth.cos(g) * 2.5, player.getZ() + (double)Mth.sin(g) * 2.5, d, e, g, 3);
                    }
                } else {
                    for (int i = 0; i < 16; ++i) {
                        double h = 1.25 * (double)(i + 1);
                        this.createPillar(player, player.getX() + (double)Mth.cos(f) * h, player.getZ() + (double)Mth.sin(f) * h, d, e, f, i);
                    }
                }
                cooldown = 50;
            }
            if (!player.getAbilities().instabuild) {
                player.getCooldowns().addCooldown(this, cooldown);
            }
            player.getItemInHand(interactionHand).hurtAndBreak(1, player, e -> e.broadcastBreakEvent(interactionHand));
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide);
        }
        return super.use(level, player, interactionHand);
    }

    private void createPillar(Player player, double d, double e, double f, double g, float h, int i) {
        BlockPos blockPos = BlockPos.containing(d, g, e);
        boolean bl = false;
        double j = 0.0;
        do {
            VoxelShape voxelShape;
            BlockPos blockPos2 = blockPos.below();
            BlockState blockState = player.level().getBlockState(blockPos2);
            if (!blockState.isFaceSturdy(player.level(), blockPos2, Direction.UP)) continue;
            if (!player.level().isEmptyBlock(blockPos) && !(voxelShape = player.level().getBlockState(blockPos).getCollisionShape(player.level(), blockPos)).isEmpty()) {
                j = voxelShape.max(Direction.Axis.Y);
            }
            bl = true;
            break;
        } while ((blockPos = blockPos.below()).getY() >= Mth.floor(f) - 1);
        if (bl) {
            player.level().addFreshEntity(new PinkSaltPillar(player.level(), d, (double)blockPos.getY() + j, e, h, i, player));
        }
    }
}
