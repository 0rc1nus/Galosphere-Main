package net.orcinus.galosphere.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.BannerAttachable;
import net.orcinus.galosphere.api.FayBoundedSpyglass;
import net.orcinus.galosphere.api.GoldenBreath;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.entities.SparkleEntity;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GCriteriaTriggers;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.items.SterlingArmorItem;
import net.orcinus.galosphere.util.BannerRendererUtil;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(GEntityTypes.SPARKLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SparkleEntity::checkSparkleSpawnRules);
        event.put(GEntityTypes.SPARKLE.get(), SparkleEntity.createAttributes().build());
        event.put(GEntityTypes.SPECTRE.get(), SpectreEntity.createAttributes().build());
    }

    @SubscribeEvent
    public void onBreakSpeedChanged(PlayerEvent.BreakSpeed event) {
        BlockState state = event.getState();
        if (state.getBlock() == Blocks.BUDDING_AMETHYST) {
            event.setNewSpeed(2.0F);
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Horse horse) {
            if (!((BannerAttachable)horse).getBanner().isEmpty() && horse.getArmor().is(GItems.STERLING_HORSE_ARMOR.get())) {
                ItemStack copy = ((BannerAttachable) horse).getBanner();
                horse.spawnAtLocation(copy);
                ((BannerAttachable) horse).setBanner(ItemStack.EMPTY);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        boolean flag = event.getSource().isExplosion();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (flag) {
                float reductionAmount = 0.0F;
                Item item = entity.getItemBySlot(slot).getItem();
                if (entity instanceof Horse horse) {
                    Item horseItem = horse.getArmor().getItem();
                    if (horseItem == GItems.STERLING_HORSE_ARMOR.get()) {
                        float damageReduction = 3.0F;
                        reductionAmount = event.getAmount() - damageReduction;
                    }
                }
                if (item instanceof SterlingArmorItem sterlingArmorItem) {
                    float damageReduction = sterlingArmorItem.getExplosionResistance(slot);
                    reductionAmount = event.getAmount() - damageReduction;
                }
                if (item instanceof SterlingArmorItem || (entity instanceof Horse && ((Horse)entity).getArmor().getItem() == GItems.STERLING_HORSE_ARMOR.get())){
                    event.setAmount(reductionAmount);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        Entity target = event.getTarget();
        BannerRendererUtil util = new BannerRendererUtil();
        if (target instanceof Horse horse) {
            if (horse.getArmor().is(GItems.STERLING_HORSE_ARMOR.get())) {
                if (((BannerAttachable) horse).getBanner().isEmpty()) {
                    if (util.isTapestryStack(stack) || stack.getItem() instanceof BannerItem) {
                        if (!horse.level.isClientSide()) {
                            event.setCanceled(true);
                            ItemStack copy = stack.copy();
                            if (!player.getAbilities().instabuild) {
                                stack.shrink(1);
                            }
                            copy.setCount(1);
                            horse.level.playSound(null, horse, SoundEvents.HORSE_ARMOR, SoundSource.PLAYERS, 1.0F, 1.0F);
                            horse.gameEvent(GameEvent.ENTITY_INTERACT, player);
                            ((BannerAttachable) horse).setBanner(copy);
                            player.swing(hand);
                        }
                    }
                } else {
                    if (player.isShiftKeyDown() && stack.isEmpty()) {
                        if (!horse.level.isClientSide()) {
                            event.setCanceled(true);
                            ItemStack copy = ((BannerAttachable) horse).getBanner();
                            player.setItemInHand(hand, copy);
                            horse.level.playSound(null, horse, SoundEvents.HORSE_ARMOR, SoundSource.PLAYERS, 1.0F, 1.0F);
                            horse.gameEvent(GameEvent.ENTITY_INTERACT, player);
                            ((BannerAttachable) horse).setBanner(ItemStack.EMPTY);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack useItem = entity.getUseItem();
        if (entity instanceof BannerAttachable bannerEntity) {
            if (!bannerEntity.getBanner().isEmpty()) {
                if (entity instanceof Horse horse) {
                    if (!((BannerAttachable)horse).getBanner().isEmpty() && !horse.getArmor().is(GItems.STERLING_HORSE_ARMOR.get())) {
                        ItemStack copy = ((BannerAttachable) horse).getBanner();
                        horse.spawnAtLocation(copy);
                        ((BannerAttachable) horse).setBanner(ItemStack.EMPTY);
                    }
                } else {
                    if (!entity.getItemBySlot(EquipmentSlot.HEAD).is(GItems.STERLING_HELMET.get())) {
                        ItemStack copy = bannerEntity.getBanner();
                        entity.spawnAtLocation(copy);
                        bannerEntity.setBanner(ItemStack.EMPTY);
                    }
                }
            }
        }
        if (entity.isAlive() && entity instanceof GoldenBreath goldenBreath) {
            if (goldenBreath.getGoldenAirSupply() > 0) {
                goldenBreath.setGoldenAirSupply(goldenBreath.decreaseGoldenAirSupply(entity, (int) goldenBreath.getGoldenAirSupply()));
            }
        }
        if (FayBoundedSpyglass.canUseFayBoundedSpyglass(useItem, entity) && useItem.getTag() != null) {
            if (!entity.level.isClientSide) {
                Entity fayBound = ((ServerLevel)entity.level).getEntity(useItem.getTag().getUUID("FayBoundUUID"));
                Optional.ofNullable(fayBound).filter(SpectreEntity.class::isInstance).map(SpectreEntity.class::cast).filter(SpectreEntity::isAlive).ifPresent(fay -> {
                    if (entity instanceof Player player && fay.getManipulatorUUID() != player.getUUID()) {
                        boolean withinDistance = Math.sqrt(Math.pow((player.getX() - fay.getX()), 2) + Math.pow((player.getZ() - fay.getZ()), 2)) < 110;
                        if (withinDistance) {
                            fay.setCamera(player);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public void onTeleportEvent(EntityTeleportEvent.EnderPearl event) {
        ServerPlayer player = event.getPlayer();
        BlockPos pos = new BlockPos(event.getTarget());
        Level world = player.getLevel();
        int radius = 16;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {
                    BlockPos blockPos = new BlockPos(event.getTargetX() + x, event.getTargetY() + y, event.getTargetZ() + z);
                    BlockState blockState = world.getBlockState(blockPos);
                    if (pos.closerThan(blockPos, 12) && blockState.is(GBlocks.WARPED_ANCHOR.get()) && blockState.getValue(WarpedAnchorBlock.WARPED_CHARGE) > 0) {
                        GCriteriaTriggers.WARPED_TELEPORT.trigger(player);
                        world.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                        world.setBlockAndUpdate(blockPos, blockState.setValue(WarpedAnchorBlock.WARPED_CHARGE, blockState.getValue(WarpedAnchorBlock.WARPED_CHARGE) - 1));
                        world.playSound(null, blockPos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
                        event.setTargetX(blockPos.getX() + 0.5D);
                        event.setTargetY(blockPos.getY() + 0.5D);
                        event.setTargetZ(blockPos.getZ() + 0.5D);
                    }
                }
            }
        }
    }

}
