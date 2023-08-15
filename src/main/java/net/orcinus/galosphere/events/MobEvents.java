package net.orcinus.galosphere.events;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.BannerAttachable;
import net.orcinus.galosphere.api.GoldenBreath;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;
import net.orcinus.galosphere.config.GalosphereConfig;
import net.orcinus.galosphere.entities.Sparkle;
import net.orcinus.galosphere.entities.SpectatorVision;
import net.orcinus.galosphere.entities.Specterpillar;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GCriteriaTriggers;
import net.orcinus.galosphere.init.GEntityTypeTags;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.items.SterlingArmorItem;
import net.orcinus.galosphere.util.BannerRendererUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(GEntityTypes.SPARKLE.get(), Sparkle.createAttributes().build());
        event.put(GEntityTypes.SPECTRE.get(), Spectre.createAttributes().build());
        event.put(GEntityTypes.SPECTERPILLAR.get(), Specterpillar.createAttributes().build());
        event.put(GEntityTypes.SPECTATOR_VISION.get(), SpectatorVision.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(GEntityTypes.SPARKLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Sparkle::checkSparkleSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(GEntityTypes.SPECTRE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    @SubscribeEvent
    public void onBreakSpeedChanged(PlayerEvent.BreakSpeed event) {
        BlockState state = event.getState();
        if (state.getBlock() == Blocks.BUDDING_AMETHYST && GalosphereConfig.SLOWED_BUDDING_AMETHYST_MINING_SPEED.get()) {
            event.setNewSpeed(2.0F);
        }
    }

    @SubscribeEvent
    public void onItemExpire(ItemExpireEvent event) {
        ItemEntity entity = event.getEntity();
        if (!entity.level().isClientSide) {
            BlockState blockState = entity.level().getBlockState(BlockPos.containing(entity.getEyePosition().x, entity.getEyePosition().y, entity.getEyePosition().z));
            if (blockState.is(GBlocks.CAPTIVATED_MEMBRANE_BLOCK.get())) {
                event.setExtraLife(6000);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Horse horse && horse instanceof BannerAttachable bannerAttachable) {
            if (!bannerAttachable.getBanner().isEmpty() && horse.getArmor().is(GItems.STERLING_HORSE_ARMOR.get())) {
                ItemStack copy = bannerAttachable.getBanner();
                horse.spawnAtLocation(copy);
                bannerAttachable.setBanner(ItemStack.EMPTY);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        float originalAmount = event.getAmount();
        boolean flag = source.getEntity() instanceof Mob mob && (mob.getMobType() == MobType.ILLAGER || mob.getType().is(GEntityTypeTags.STERLING_IMMUNE_ENTITY_TYPES));
        if (flag) {
            if (entity instanceof Horse horse && horse.getArmor().is(GItems.STERLING_HORSE_ARMOR.get())) {
                event.setAmount(originalAmount - 4.0F);
            }
            float illagerReduction = 0.0F;
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (entity.getItemBySlot(equipmentSlot).getItem() instanceof SterlingArmorItem sterlingArmorItem && equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                    illagerReduction+=sterlingArmorItem.getInsurgentResistance(equipmentSlot);
                }
            }
            if (illagerReduction > 0) {
                float value = 4 * (originalAmount / illagerReduction);
                event.setAmount(value);
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
                        if (!horse.level().isClientSide()) {
                            event.setCanceled(true);
                            ItemStack copy = stack.copy();
                            if (!player.getAbilities().instabuild) {
                                stack.shrink(1);
                            }
                            copy.setCount(1);
                            horse.level().playSound(null, horse, SoundEvents.HORSE_ARMOR, SoundSource.PLAYERS, 1.0F, 1.0F);
                            horse.gameEvent(GameEvent.ENTITY_INTERACT, player);
                            ((BannerAttachable) horse).setBanner(copy);
                            player.swing(hand);
                        }
                    }
                } else {
                    if (player.isShiftKeyDown() && stack.isEmpty()) {
                        if (!horse.level().isClientSide()) {
                            event.setCanceled(true);
                            ItemStack copy = ((BannerAttachable) horse).getBanner();
                            player.setItemInHand(hand, copy);
                            horse.level().playSound(null, horse, SoundEvents.HORSE_ARMOR, SoundSource.PLAYERS, 1.0F, 1.0F);
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
        if (SpectreBoundSpyglass.canUseSpectreBoundedSpyglass(useItem) && useItem.getTag() != null) {
            if (!entity.level().isClientSide) {
                Entity spectreBound = ((ServerLevel)entity.level()).getEntity(useItem.getTag().getUUID("SpectreBoundUUID"));
                Optional.ofNullable(spectreBound).filter(Spectre.class::isInstance).map(Spectre.class::cast).filter(Spectre::isAlive).ifPresent(spectre -> {
                    if (entity instanceof Player player && spectre.getManipulatorUUID() != player.getUUID()) {
                        boolean withinDistance = Math.sqrt(Math.pow((player.getX() - spectre.getX()), 2) + Math.pow((player.getZ() - spectre.getZ()), 2)) < 110;
                        if (withinDistance) {
                            spectre.setCamera(player);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public void onTeleportEvent(EntityTeleportEvent.EnderPearl event) {
        ThrownEnderpearl pearl = event.getPearlEntity();
        List<BlockPos> poses = Lists.newArrayList();
        ServerPlayer player = event.getPlayer();
        Level world = player.level();
        BlockPos pearlPos = pearl.blockPosition();
        int radius = 16;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {
                    BlockPos blockPos = BlockPos.containing(pearl.getX() + x, pearl.getY() + y, pearl.getZ() + z);
                    BlockState blockState = world.getBlockState(blockPos);
                    if (blockState.is(GBlocks.WARPED_ANCHOR.get()) && blockState.getValue(WarpedAnchorBlock.WARPED_CHARGE) > 0) {
                        poses.add(blockPos);
                    }
                }
            }
        }
        if (!poses.isEmpty()) {
            poses.sort(Comparator.comparingDouble(pearlPos::distSqr));
            for (BlockPos blockPos : poses) {
                event.setCanceled(true);
                GCriteriaTriggers.WARPED_TELEPORT.trigger(player);
                pearl.level().gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                pearl.level().playSound(null, blockPos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.teleportTo(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D);
                player.resetFallDistance();
                pearl.level().setBlock(blockPos, pearl.level().getBlockState(blockPos).setValue(WarpedAnchorBlock.WARPED_CHARGE, pearl.level().getBlockState(blockPos).getValue(WarpedAnchorBlock.WARPED_CHARGE) - 1), 2);
                pearl.discard();
                break;
            }
        }
    }

}
