package net.orcinus.galosphere.events;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.IBanner;
import net.orcinus.galosphere.api.ISoulWince;
import net.orcinus.galosphere.blocks.LumiereComposterBlock;
import net.orcinus.galosphere.blocks.MimicLightBlock;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;
import net.orcinus.galosphere.config.GConfig;
import net.orcinus.galosphere.entities.SparkleEntity;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.items.SterlingArmorItem;
import net.orcinus.galosphere.util.BannerRendererUtil;

import java.util.List;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(GEntityTypes.SPARKLE.get(), SparkleEntity.createAttributes().build());
    }

    @SubscribeEvent
    public void onBreakSpeedChanged(PlayerEvent.BreakSpeed event) {
        BlockState state = event.getState();
        if (GConfig.speedReductionOnBuddingAmethyst.get() && state.getBlock() == Blocks.BUDDING_AMETHYST) {
            event.setNewSpeed(2.0F);
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        Entity attacker = event.getSource().getEntity();
        if (livingEntity instanceof Horse horse) {
            if (!((IBanner)horse).getBanner().isEmpty() && horse.getArmor().is(GItems.STERLING_HORSE_ARMOR.get())) {
                ItemStack copy = ((IBanner) horse).getBanner();
                horse.spawnAtLocation(copy);
                ((IBanner) horse).setBanner(ItemStack.EMPTY);
            }
        }
        if (attacker instanceof LivingEntity && livingEntity instanceof ISoulWince) {
            for (int t = 0; t < 32; t++) {
                /*
                 * Why the fuck is this not working???
                 */
                ((ServerLevel) livingEntity.level).sendParticles(ParticleTypes.SOUL, livingEntity.getX(), livingEntity.getY() + 0.5D, livingEntity.getZ(), 0, livingEntity.getRandomX(0.5F), 0.6F, livingEntity.getRandomZ(0.5F), 0.1F);
                livingEntity.level.addParticle(ParticleTypes.SOUL, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 0.0D, 0.0D, 0.0D);
            }
            ((LivingEntity)attacker).heal(livingEntity.getMaxHealth() / 10);
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
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        Entity target = event.getTarget();
        BannerRendererUtil util = new BannerRendererUtil();
        if (target instanceof Horse horse) {
            if (horse.getArmor().is(GItems.STERLING_HORSE_ARMOR.get())) {
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
        List<BlockPos> list = Lists.newArrayList();
        if (entity instanceof IBanner bannerEntity) {
            if (!bannerEntity.getBanner().isEmpty()) {
                if (entity instanceof Horse horse) {
                    if (!((IBanner)horse).getBanner().isEmpty() && !horse.getArmor().is(GItems.STERLING_HORSE_ARMOR.get())) {
                        ItemStack copy = ((IBanner) horse).getBanner();
                        horse.spawnAtLocation(copy);
                        ((IBanner) horse).setBanner(ItemStack.EMPTY);
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
        if (entity instanceof GlowSquid glowSquid) {
            Level world = glowSquid.level;
            if (!world.isClientSide()) {
                int radius = 5;
                int height = 3;
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        for (int y = -height; y <= height; y++) {
                            BlockPos glowSquidPos = glowSquid.blockPosition();
                            BlockPos pos = new BlockPos(glowSquidPos.getX() + x, glowSquidPos.getY() + y, glowSquidPos.getZ() + z);
                            if (world.getBlockState(pos).is(GBlocks.MIMIC_LIGHT.get())) {
//                                list.add(pos);
                                if (glowSquid.isAlive()) {
                                    if (world.getBlockState(pos).hasProperty(MimicLightBlock.LEVEL)) {
                                        world.setBlock(pos, GBlocks.MIMIC_LIGHT.get().defaultBlockState().setValue(MimicLightBlock.LEVEL, 15 + (Math.min(15, Math.max(0, Mth.floor(Mth.sqrt((float) glowSquid.blockPosition().distSqr(pos))))) - 1) * -1), 3);
                                    }
                                }
                            }
                        }
                    }
                }
//                if (!list.isEmpty()) {
//                    BlockPos possibles = list.get(glowSquid.getRandom().nextInt(list.size()));
//                    if (glowSquid.isAlive()) {
//                        if (world.getBlockState(possibles).hasProperty(MimicLightBlock.LEVEL)) {
//                            world.setBlock(possibles, GBlocks.MIMIC_LIGHT.get().defaultBlockState().setValue(MimicLightBlock.LEVEL, 15 + (Math.min(15, Math.max(0, Mth.floor(Mth.sqrt((float) glowSquid.blockPosition().distSqr(possibles))))) - 1) * -1), 3);
//                        }
//                    }
//                }
            }
        }
        if (entity instanceof ISoulWince) {
            if (((ISoulWince)entity).isWinced() && entity.level.isClientSide()) {
                entity.level.addParticle(ParticleTypes.SOUL, entity.getX(), entity.getY(), entity.getZ(), 0.0D, 0.0D, 0.0D);
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
            if (player.getItemInHand(hand).getItem() == GBlocks.LUMIERE_BLOCK.get().asItem()) {
                if (state.getValue(ComposterBlock.LEVEL) > 0 && state.getValue(ComposterBlock.LEVEL) < 8) {
                    event.setCanceled(true);
                    world.setBlock(pos, GBlocks.LUMIERE_COMPOSTER.get().defaultBlockState().setValue(LumiereComposterBlock.LEVEL, state.getValue(ComposterBlock.LEVEL)), 2);
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
                        if (state.getBlock() == GBlocks.WARPED_ANCHOR.get()) {
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
                if (state.getBlock() == GBlocks.WARPED_ANCHOR.get()) {
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
