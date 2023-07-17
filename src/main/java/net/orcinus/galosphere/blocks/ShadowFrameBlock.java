package net.orcinus.galosphere.blocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.galosphere.blocks.blockentities.ShadowFrameBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShadowFrameBlock extends BaseEntityBlock {
    public static final BooleanProperty FILLED = BooleanProperty.create("filled");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public ShadowFrameBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FILLED, false).setValue(WATERLOGGED, false).setValue(LEVEL, 0).setValue(POWERED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Level world = blockPlaceContext.getLevel();
        BlockPos pos = blockPlaceContext.getClickedPos();
        return this.defaultBlockState().setValue(POWERED, world.hasNeighborSignal(pos)).setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (level.isClientSide) {
            return;
        }
        level.setBlock(blockPos, blockState.setValue(POWERED, level.hasNeighborSignal(blockPos)), 2);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public float getDestroyProgress(BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos) {
        if (blockState.getValue(FILLED) && blockGetter.getBlockEntity(blockPos) instanceof ShadowFrameBlockEntity shadowFrameBlockEntity) {
            return shadowFrameBlockEntity.getCopiedState().getDestroyProgress(player, blockGetter, blockPos);
        }
        return super.getDestroyProgress(blockState, player, blockGetter, blockPos);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean skipRendering(BlockState blockState, BlockState blockState2, Direction direction) {
        if (blockState2.is(this) && !blockState2.getValue(FILLED)) {
            return true;
        }
        return super.skipRendering(blockState, blockState2, direction);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(FILLED) && blockGetter.getBlockEntity(blockPos) instanceof ShadowFrameBlockEntity shadowFrameBlockEntity) {
            BlockState copiedState = shadowFrameBlockEntity.getCopiedState();
            return copiedState.getBlock().getCloneItemStack(blockGetter, blockPos, copiedState);
        }
        return super.getCloneItemStack(blockGetter, blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(interactionHand);
        BlockPlaceContext blockPlaceContext = new BlockPlaceContext(player, interactionHand, stack, blockHitResult);
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() == this) {
            return InteractionResult.FAIL;
        }
        if (level.getBlockEntity(blockPos) instanceof ShadowFrameBlockEntity shadowFrameBlockEntity) {
            if (stack.getItem() instanceof HoneycombItem && !shadowFrameBlockEntity.isWaxed()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, stack);
                }
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockState));
                level.levelEvent(player, 3003, blockPos, 0);
                shadowFrameBlockEntity.setWaxed(true);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            if (shadowFrameBlockEntity.isWaxed()) {
                return InteractionResult.FAIL;
            }
            if (!blockState.getValue(FILLED)) {
                if (stack.getItem() instanceof BlockItem blockItem) {
                    Block block = blockItem.getBlock();
                    BlockState stateForPlacement = block.getStateForPlacement(blockPlaceContext);
                    if (this.canBeFramed(level, blockPlaceContext, stateForPlacement)) {
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                        level.playSound(null, blockPos, stateForPlacement.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                        shadowFrameBlockEntity.interact(stateForPlacement, level, blockPos, blockState);
                        return InteractionResult.SUCCESS;
                    }
                }
            } else if (stack.isEmpty()) {
                if (!level.isClientSide) {
                    level.setBlock(blockPos, blockState.setValue(FILLED, false).setValue(LEVEL, 0), 2);
                    level.playSound(null, blockPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                    Block.popResource(level, blockPos, new ItemStack(shadowFrameBlockEntity.getCopiedState().getBlock().asItem()));
                }
                shadowFrameBlockEntity.setCopiedState(Blocks.AIR.defaultBlockState());
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    private boolean canBeFramed(Level level, BlockPlaceContext blockPlaceContext, BlockState stateForPlacement) {
        BlockPos clickedPos = blockPlaceContext.getClickedPos();
        return stateForPlacement != null && !(stateForPlacement.getBlock() instanceof EntityBlock) && Block.isShapeFullBlock(stateForPlacement.getShape(level, clickedPos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FILLED, WATERLOGGED, LEVEL, POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ShadowFrameBlockEntity(blockPos, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (blockState.getValue(FILLED) && blockGetter.getBlockEntity(blockPos) instanceof ShadowFrameBlockEntity shadowFrameBlockEntity) {
            return shadowFrameBlockEntity.getCopiedState().getShape(blockGetter, blockPos, collisionContext);
        }
        return super.getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return !blockState.getValue(POWERED) ? Shapes.empty() : blockState.getShape(blockGetter, blockPos);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        List<ItemStack> drops = super.getDrops(blockState, builder);
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof ShadowFrameBlockEntity shadowFrameBlockEntity) {
            drops.addAll(shadowFrameBlockEntity.getCopiedState().getDrops(builder));
        }
        return drops;
    }

}
