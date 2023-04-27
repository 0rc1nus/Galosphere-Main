package net.orcinus.galosphere.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
import net.orcinus.galosphere.init.GSoundEvents;

import java.util.Optional;

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
        if (world instanceof ServerLevel serverWorld) {
            if (!playerEntity.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (!playerEntity.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                playerEntity.drop(new ItemStack(Items.GLASS_BOTTLE), false);
            }
            if (stack.getTag() != null && !stack.getTag().isEmpty()) {
                Optional<Entity> entity1 = EntityType.create(stack.getTag(), world);
                entity1.ifPresent(entity -> {
                    entity.setPos(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);
                    serverWorld.addWithUUID(entity);
                    if (entity instanceof BottlePickable bottlePickable) {
                        bottlePickable.setFromBottle(true);
                    }
                });
            } else {
                SpectreEntity spectre = GEntityTypes.SPECTRE.create(world);
                spectre.setPos(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);
                spectre.setFromBottle(true);
                Entity entity = spectre.getType().spawn(serverWorld, stack, null, blockPos, MobSpawnType.SPAWN_EGG, true, false);
                if (entity instanceof BottlePickable bottlePickable) {
                    bottlePickable.setFromBottle(true);
                }
                world.playSound(null, blockPos, GSoundEvents.SPECTRE_BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
                world.gameEvent(useOnContext.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }
}
