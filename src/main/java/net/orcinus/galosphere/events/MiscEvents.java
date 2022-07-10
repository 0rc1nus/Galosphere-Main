package net.orcinus.galosphere.events;

//@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class MiscEvents {
//
//    @SubscribeEvent
//    public void onResourceLoad(AddReloadListenerEvent event) {
//        event.addListener(new LumiereReformingManager());
//    }
//
//    @SubscribeEvent
//    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
//        ItemStack stack = event.getItemStack();
//        Player player = event.getPlayer();
//        InteractionHand hand = event.getHand();
//        BlockPos pos = event.getPos();
//        Level world = event.getWorld();
//        BlockState state = world.getBlockState(pos);
//        if (player.isShiftKeyDown() && !((IBanner) player).getBanner().isEmpty() && stack.isEmpty()) {
//            ItemStack copy = ((IBanner) player).getBanner();
//            player.setItemInHand(hand, copy);
//            player.gameEvent(GameEvent.EQUIP, player);
//            ((IBanner) player).setBanner(ItemStack.EMPTY);
//        }
//        if (state.getBlock() == Blocks.COMPOSTER) {
//            if (stack.getItem() == GItems.LUMIERE_SHARD) {
//                if (state.getValue(ComposterBlock.LEVEL) > 0 && state.getValue(ComposterBlock.LEVEL) < 8) {
//                    event.setCanceled(true);
//                    if (!player.getAbilities().instabuild) {
//                        stack.shrink(1);
//                    }
//                    world.setBlock(pos, GBlocks.LUMIERE_COMPOSTER.defaultBlockState().setValue(LumiereComposterBlock.LEVEL, state.getValue(ComposterBlock.LEVEL)), 2);
//                    world.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
//                    world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
//                    player.swing(hand);
//                }
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
//        ItemStack stack = event.getItemStack();
//        Player player = event.getPlayer();
//        InteractionHand hand = event.getHand();
//        BannerRendererUtil util = new BannerRendererUtil();
//        if (((IBanner) player).getBanner().isEmpty() && player.getItemBySlot(EquipmentSlot.HEAD).is(GItems.STERLING_HELMET)) {
//            if (util.isTapestryStack(stack) || stack.getItem() instanceof BannerItem) {
//                player.gameEvent(GameEvent.EQUIP, player);
//                ItemStack copy = stack.copy();
//                if (!player.getAbilities().instabuild) {
//                    stack.shrink(1);
//                }
//                copy.setCount(1);
//                ((IBanner) player).setBanner(copy);
//                player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
//                player.swing(hand);
//            }
//        }
//    }
//
//}
