package net.orcinus.galosphere.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.orcinus.galosphere.api.BottlePickable;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.init.GEntityTypes;

public class SpectreBottleItem extends Item {

    public SpectreBottleItem(Properties properties) {
        super(properties);
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
            SpectreEntity fay = GEntityTypes.SPECTRE.get().create(world);
            fay.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
            world.playSound(null, blockPos, SoundEvents.BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
            Entity entity = fay.getType().spawn(serverWorld, stack, null, blockPos, MobSpawnType.SPAWN_EGG, true, false);
            if (entity instanceof SpectreEntity fay1) {
                BottlePickable.loadDefaultDataFromBottleTag(fay1, compoundTag);
                fay1.setFromBottle(true);
            }
            world.gameEvent(useOnContext.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }
}