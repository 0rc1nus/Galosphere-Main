package net.orcinus.galosphere.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.orcinus.galosphere.entities.FayEntity;
import net.orcinus.galosphere.init.GEntityTypes;

public class PixieBottleItem extends Item {

    public PixieBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int p_41415_) {
        entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        BlockPlaceContext itemPlacementContext = new BlockPlaceContext(useOnContext);
        Level world = useOnContext.getLevel();
        Player playerEntity = useOnContext.getPlayer();
        BlockPos blockPos = itemPlacementContext.getClickedPos();
        ItemStack stack = useOnContext.getItemInHand();
        CompoundTag compoundTag = stack.getOrCreateTag();
        if (world instanceof ServerLevel serverWorld) {
            if (!playerEntity.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (!playerEntity.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                playerEntity.drop(new ItemStack(Items.GLASS_BOTTLE), false);
            }
            FayEntity pixie = GEntityTypes.FAY.get().create(world);
            pixie.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
            world.playSound(null, blockPos, SoundEvents.BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (compoundTag.contains("NoAI")) {
                pixie.setNoAi(compoundTag.getBoolean("NoAI"));
            }
            if (compoundTag.contains("Silent")) {
                pixie.setSilent(compoundTag.getBoolean("Silent"));
            }
            if (compoundTag.contains("NoGravity")) {
                pixie.setNoGravity(compoundTag.getBoolean("NoGravity"));
            }
            if (compoundTag.contains("Glowing")) {
                pixie.setGlowingTag(compoundTag.getBoolean("Glowing"));
            }
            if (compoundTag.contains("Invulnerable")) {
                pixie.setInvulnerable(compoundTag.getBoolean("Invulnerable"));
            }
            if (compoundTag.contains("Health", 99)) {
                pixie.setHealth(compoundTag.getFloat("Health"));
            }
            serverWorld.addFreshEntity(pixie);
            world.gameEvent(useOnContext.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }
}
