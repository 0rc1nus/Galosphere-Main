package net.orcinus.galosphere.blocks;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.init.GBlocks;

public class SilverLatticeVineBlock extends SilverLatticeBlock implements BonemealableBlock {
    public static final BooleanProperty BERRIES = BlockStateProperties.BERRIES;
    public static final BooleanProperty SPREADABLE = BooleanProperty.create("spreadable");

    public SilverLatticeVineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BERRIES, false).setValue(SPREADABLE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BERRIES, SPREADABLE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return !blockState.getValue(BERRIES) || blockState.getValue(SPREADABLE);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!blockState.getValue(SPREADABLE)) {
            return;
        }
        if (randomSource.nextBoolean()) {
            if (blockState.getValue(BERRIES)) {
                for (Direction direction : Direction.values()) {
                    BlockPos relativePos = blockPos.relative(direction);
                    BlockState relativeState = serverLevel.getBlockState(relativePos);
                    if (!relativeState.is(GBlocks.SILVER_LATTICE)) {
                        continue;
                    }
                    serverLevel.setBlockAndUpdate(relativePos, GBlocks.GLOW_BERRIES_SILVER_LATTICE.withPropertiesOf(relativeState));
                    break;
                }
            } else {
                BlockState state = blockState.setValue(BERRIES, true);
                serverLevel.setBlockAndUpdate(blockPos, state);
                serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(state));
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(GBlocks.SILVER_LATTICE);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (stack.is(ConventionalItemTags.SHEARS) && blockState.getValue(SPREADABLE)) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, stack);
            }
            level.playSound(player, blockPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlockAndUpdate(blockPos, blockState.setValue(SPREADABLE, false));
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(interactionHand));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (stack.isEmpty()) {
            BlockState lattice = GBlocks.SILVER_LATTICE.defaultBlockState().setValue(NORTH, blockState.getValue(NORTH)).setValue(EAST, blockState.getValue(EAST)).setValue(SOUTH, blockState.getValue(SOUTH)).setValue(WEST, blockState.getValue(WEST)).setValue(WATERLOGGED, blockState.getValue(WATERLOGGED));
            if (!player.getInventory().add(new ItemStack(Items.GLOW_BERRIES))) {
                player.drop(new ItemStack(Items.GLOW_BERRIES), false);
            }
            BlockState newState = blockState.getValue(BERRIES) ? blockState.setValue(BERRIES, false) : lattice;
            level.setBlockAndUpdate(blockPos, newState);
            level.playSound(null, blockPos, SoundEvents.CAVE_VINES_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return !blockState.getValue(BERRIES);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        serverLevel.setBlock(blockPos, blockState.setValue(BERRIES, true), 2);
    }
}
