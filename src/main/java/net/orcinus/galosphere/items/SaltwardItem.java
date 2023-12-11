package net.orcinus.galosphere.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.orcinus.galosphere.blocks.PinkSalt;
import net.orcinus.galosphere.entities.Berserker;
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
            HitResult endVec = player.pick(20.0F, 0.0F, false);
            Vec3 location = endVec.getLocation();
            int cooldown;
            if (!player.isShiftKeyDown() && player.distanceToSqr(result.getLocation()) <= 6.0D) {
                Vec3 position = player.position();
                level.addFreshEntity(new PinkSaltPillar(player.level(), position.x, (double)position.y, position.z, 0, 0, player));
                Vec3 diff = location.subtract(position);
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
            player.getCooldowns().addCooldown(this, cooldown);
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
