package net.orcinus.cavesandtrenches.events;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.api.IBanner;
import net.orcinus.cavesandtrenches.blocks.AuraListenerBlock;
import net.orcinus.cavesandtrenches.blocks.LumiereComposterBlock;
import net.orcinus.cavesandtrenches.blocks.WarpedAnchorBlock;
import net.orcinus.cavesandtrenches.entities.SparkleEntity;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTEntityTypes;
import net.orcinus.cavesandtrenches.init.CTItems;
import net.orcinus.cavesandtrenches.items.SterlingArmorItem;
import net.orcinus.cavesandtrenches.util.BannerRendererUtil;

import java.util.List;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(CTEntityTypes.SPARKLE.get(), SparkleEntity.createAttributes().build());
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        Entity attacker = event.getSource().getEntity();
        int range = AuraListenerBlock.getRange();
        if (livingEntity instanceof Horse horse) {
            if (!((IBanner)horse).getBanner().isEmpty() && horse.getArmor().is(CTItems.STERLING_HORSE_ARMOR.get())) {
                ItemStack copy = ((IBanner) horse).getBanner();
                horse.spawnAtLocation(copy);
                ((IBanner) horse).setBanner(ItemStack.EMPTY);
            }
        }
        if (livingEntity instanceof Monster monster) {
            BlockPos blockPos = monster.blockPosition();
            Level world = monster.level;
            int healRange = range / 2;
            for (int x = -healRange; x <= healRange; x++) {
                for (int z = -healRange; z <= healRange; z++) {
                    for (int y = -healRange; y <= healRange; y++) {
                        BlockPos rangePos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                        BlockState state = world.getBlockState(rangePos);
                        if (state.is(CTBlocks.AURA_LISTENER.get())) {
                            float monsterHealth = monster.getMaxHealth() / 8.0F;
                            if (attacker instanceof Player player && state.getValue(AuraListenerBlock.LISTENING)) {
                                float value = Math.max(1.0F, monsterHealth);
                                player.heal(value);
                                world.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.4F, 1.4F);
                                ((ServerLevel) world).sendParticles(ParticleTypes.HEART, monster.getX(), monster.getY(), monster.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.15D);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        boolean flag = event.getSource().isExplosion();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (flag) {
                float reductionAmount = 0.0F;
                Item item = entity.getItemBySlot(slot).getItem();
                if (entity instanceof Horse horse) {
                    Item horseItem = horse.getArmor().getItem();
                    if (horseItem == CTItems.STERLING_HORSE_ARMOR.get()) {
                        float damageReduction = 3.0F;
                        reductionAmount = event.getAmount() - damageReduction;
                    }
                }
                if (item instanceof SterlingArmorItem sterlingArmorItem) {
                    float damageReduction = sterlingArmorItem.getExplosionResistance(slot);
                    reductionAmount = event.getAmount() - damageReduction;
                }
                event.setAmount(reductionAmount);
            }
        }
    }

    @SubscribeEvent
    public void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        Entity target = event.getTarget();
        BannerRendererUtil util = new BannerRendererUtil();
        if (target instanceof Horse horse) {
            if (horse.getArmor().is(CTItems.STERLING_HORSE_ARMOR.get())) {
                if (((IBanner) horse).getBanner().isEmpty()) {
                    if (util.isTapestryStack(stack) || stack.getItem() instanceof BannerItem) {
                        ItemStack copy = stack.copy();
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                        copy.setCount(1);
                        ((IBanner) horse).setBanner(copy);
                        player.playSound(SoundEvents.HORSE_ARMOR, 1.0F, 1.0F);
                        player.swing(hand);
                    }
                } else {
                    if (player.isShiftKeyDown() && stack.isEmpty()) {
                        ItemStack copy = ((IBanner) horse).getBanner();
                        player.setItemInHand(hand, copy);
                        ((IBanner) horse).setBanner(ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof IBanner bannerEntity) {
            if (!bannerEntity.getBanner().isEmpty()) {
                if (entity instanceof Horse horse) {
                    if (!((IBanner)horse).getBanner().isEmpty() && !horse.getArmor().is(CTItems.STERLING_HORSE_ARMOR.get())) {
                        ItemStack copy = ((IBanner) horse).getBanner();
                        horse.spawnAtLocation(copy);
                        ((IBanner) horse).setBanner(ItemStack.EMPTY);
                    }
                } else {
                    if (!entity.getItemBySlot(EquipmentSlot.HEAD).is(CTItems.STERLING_HELMET.get())) {
                        ItemStack copy = bannerEntity.getBanner();
                        entity.spawnAtLocation(copy);
                        bannerEntity.setBanner(ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onInteractEvent(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Level world = event.getWorld();
        BlockState state = world.getBlockState(pos);
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        if (state.getBlock() == Blocks.COMPOSTER) {
            if (player.getItemInHand(hand).getItem() == CTBlocks.LUMIERE_BLOCK.get().asItem()) {
                if (state.getValue(ComposterBlock.LEVEL) > 0 && state.getValue(ComposterBlock.LEVEL) < 8) {
                    event.setCanceled(true);
                    world.setBlock(pos, CTBlocks.LUMIERE_COMPOSTER.get().defaultBlockState().setValue(LumiereComposterBlock.LEVEL, state.getValue(ComposterBlock.LEVEL)), 2);
                    world.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    player.swing(hand);
                }
            }
        }
    }

    @SubscribeEvent
    public void onTeleportEvent(EntityTeleportEvent.EnderPearl event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            BlockPos pos = new BlockPos(event.getTarget());
            List<BlockPos> possibles = Lists.newArrayList();
            Level world = entity.level;
            int radius = 8;
            int height = 6;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = -height; y <= height; y++) {
                        BlockPos checkPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                        BlockState state = world.getBlockState(checkPos);
                        if (state.getBlock() == CTBlocks.WARPED_ANCHOR.get()) {
                            if (state.getValue(WarpedAnchorBlock.WARPED_CHARGE) > 0) {
                                possibles.add(checkPos);
                            }
                        }
                    }
                }
            }
            if (!possibles.isEmpty()) {
                BlockPos lockPosition = possibles.get(((Player) entity).getRandom().nextInt(possibles.size()));
                BlockState state = world.getBlockState(lockPosition);
                if (state.getBlock() == CTBlocks.WARPED_ANCHOR.get()) {
                    world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                    event.setTargetX(lockPosition.getX() + 0.5D);
                    event.setTargetY(lockPosition.getY() + 0.5D);
                    event.setTargetZ(lockPosition.getZ() + 0.5D);
                    world.playSound(null, lockPosition, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    world.setBlock(lockPosition, state.setValue(WarpedAnchorBlock.WARPED_CHARGE, state.getValue(WarpedAnchorBlock.WARPED_CHARGE) - 1), 2);
                }
            }
        }
    }

}
