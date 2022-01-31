package net.orcinus.cavesandtrenches.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.api.IBanner;
import net.orcinus.cavesandtrenches.blocks.AuraListenerBlock;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTItems;

import static net.orcinus.cavesandtrenches.blocks.AuraListenerBlock.TYPE;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    @SubscribeEvent
    public void onRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getPlayer();
        if (player.isShiftKeyDown() && !((IBanner)player).getBanner().isEmpty() && stack.isEmpty()) {
            ItemStack copy = ((IBanner) player).getBanner();
            player.setItemInHand(InteractionHand.MAIN_HAND, copy);
            ((IBanner) player).setBanner(ItemStack.EMPTY);
        }
    }

    @SubscribeEvent
    public void onRightClickEvent(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        if (((IBanner)player).getBanner().isEmpty() && player.getItemBySlot(EquipmentSlot.HEAD).is(CTItems.STERLING_HELMET.get())) {
            if (stack.getItem() instanceof BannerItem) {
                ItemStack copy = stack.copy();
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                copy.setCount(1);
                ((IBanner) player).setBanner(copy);
                player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
                player.swing(hand);
            }
        }
    }

    @SubscribeEvent
    public void onDispense(TagsUpdatedEvent event) {
        DispenserBlock.registerBehavior(CTBlocks.ALLURITE_BLOCK.get().asItem(), new OptionalDispenseItemBehavior() {
            public ItemStack execute(BlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = source.getPos().relative(direction);
                Level world = source.getLevel();
                BlockState state = world.getBlockState(blockpos);
                this.setSuccess(true);
                if (state.is(CTBlocks.AURA_LISTENER.get())) {
                    if (state.getValue(TYPE) == AuraListenerBlock.AuraSignalType.INACTIVE) {
                        world.playSound(null, blockpos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        world.setBlock(blockpos, state.setValue(TYPE, AuraListenerBlock.AuraSignalType.ACTIVE), 2);
                        stack.shrink(1);
                    } else {
                        this.setSuccess(false);
                    }

                    return stack;
                } else {
                    return super.execute(source, stack);
                }
            }
        });

        DispenserBlock.registerBehavior(CTItems.LUMIERE_SHARD.get(), new OptionalDispenseItemBehavior() {
            public ItemStack execute(BlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = source.getPos().relative(direction);
                Level world = source.getLevel();
                BlockState state = world.getBlockState(blockpos);
                this.setSuccess(true);
                if (state.is(Blocks.COMPOSTER)) {
                    if (state.getValue(ComposterBlock.LEVEL) > 0) {
                        world.playSound(null, blockpos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        world.setBlock(blockpos, CTBlocks.LUMIERE_COMPOSTER.get().defaultBlockState().setValue(ComposterBlock.LEVEL, state.getValue(ComposterBlock.LEVEL)), 2);
                        stack.shrink(1);
                    } else {
                        this.setSuccess(false);
                    }

                    return stack;
                } else {
                    return super.execute(source, stack);
                }
            }
        });
    }

}
