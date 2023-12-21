package net.orcinus.galosphere.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.galosphere.entities.PinkSaltPillar;
import net.orcinus.galosphere.init.GSoundEvents;

public class SaltboundTabletItem extends Item {

    public SaltboundTabletItem(Properties properties) {
        super(properties);
    }

    public int getUseDuration(ItemStack stack) {
        return 18;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.playSound(GSoundEvents.SALTBOUND_TABLET_PREPARE_ATTACK.get(), 1, 1);
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        return performAttack(stack, level, user);
    }

    public ItemStack performAttack(ItemStack stack, Level level, LivingEntity user) {
        if (user instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();
            player.swing(hand);
            player.playSound(GSoundEvents.SALTBOUND_TABLET_CAST_ATTACK.get(), 1, 1);
            Vec3 lookAngle = player.getLookAngle();
            Vec3 adjustedAngle = lookAngle.add(player.getX(), player.getY(), player.getZ());
            double d = Math.min(adjustedAngle.y(), player.getY());
            double e = Math.max(adjustedAngle.y(), player.getY()) + 1.0;
            float f = (float) Mth.atan2(adjustedAngle.z() - player.getZ(), adjustedAngle.x() - player.getX());
            int cooldown = player.getAbilities().instabuild ? 10 : 60;
            if (lookAngle.get(Direction.Axis.Y) <= -0.8) {
                for (int round = 2; round < 5; round++) {
                    for (float i = 0; i < Mth.PI * 2; i += Mth.PI / 4) {
                        createPillar(player, player.getX() + (Mth.sin(i)) * round, player.getZ() + (Mth.cos(i)) * round, d, e, f, (int) i * 2, stack);
                        player.getCooldowns().addCooldown(this, cooldown);
                    }
                }
            } else {
                for (int index = 0; index < 16; ++index) {
                    double h = 1.25 * (double) (index + 1);
                    createPillar(player, adjustedAngle.x() + (double) Mth.cos(f) * h + ((player.getRandom().nextFloat() - 0.5F) * 0.4F), adjustedAngle.z() + (double)Mth.sin(f) * h + ((player.getRandom().nextFloat() - 0.5F) * 0.4F), d, e, f, index, stack);
                    player.getCooldowns().addCooldown(this, cooldown);
                }
            }
            stack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(hand));
        }

        return stack;
    }

    private void createPillar(Player player, double d, double e, double f, double g, float h, int index, ItemStack stack) {
        BlockPos pos = BlockPos.containing(d, g, e);
        Level level = player.level();
        boolean bl = false;
        double j = 0;
        do {
            VoxelShape voxelShape;
            BlockPos pos2 = pos.below();
            BlockState state = level.getBlockState(pos2);
            if (!state.isFaceSturdy(level, pos2, Direction.UP)) continue;
            if (!level.isEmptyBlock(pos) && !(voxelShape = level.getBlockState(pos).getCollisionShape(level, pos)).isEmpty()) {
                j = voxelShape.max(Direction.Axis.Y);
            }
            bl = true;
            break;
        } while ((pos = pos.below()).getY() >= Mth.floor(f) - 7);
        if (bl) {
            float damage = 12;
            int warmupDelayTicks = index;
            level.addFreshEntity(new PinkSaltPillar(level, d, (double)pos.getY() + j, e, h, warmupDelayTicks, damage / (index + 1), player, stack));
        }
    }
}