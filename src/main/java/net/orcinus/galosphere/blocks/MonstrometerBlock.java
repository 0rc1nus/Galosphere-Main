package net.orcinus.galosphere.blocks;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.blocks.blockentities.MonstrometerBlockEntity;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class MonstrometerBlock extends BaseEntityBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");

    public MonstrometerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false).setValue(CHARGED, false));
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (!level.isClientSide) {
            boolean flag = blockState.getValue(CHARGED) && !blockState.getValue(ACTIVE);
            if (flag == level.hasNeighborSignal(blockPos)) {
                if (!flag) {
                    return;
                }
                MonstrometerBlock.activate(blockState, level, blockPos);
            }
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        if (blockState.getValue(ACTIVE) && level.getBlockEntity(blockPos) instanceof MonstrometerBlockEntity monstrometerBlockEntity) {
            return monstrometerBlockEntity.getRedstoneSignal();
        }
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE).add(CHARGED);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter getter, BlockPos pos) {
        float value = super.getDestroyProgress(state, player, getter, pos);
        return isActive(state) ? value / 25 : value;
    }

    public static boolean isActive(BlockState state) {
        return state.getValue(MonstrometerBlock.ACTIVE);
    }

    public static boolean isCharged(BlockState state) {
        return state.getValue(MonstrometerBlock.CHARGED);
    }

    public static boolean isUnsafe(Level world, BlockPos pos) {
        return world.getBrightness(LightLayer.BLOCK, pos) == 0;
    }

    public static List<BlockPos> getIndicatedBlocks(BlockPos origin, Predicate<BlockPos> predicate) {
        int radius = 16;
        int height = 6;
        List<BlockPos> list = Lists.newArrayList();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -height; y <= height; y++) {
                    if (x * x + z * z <= radius * radius) {
                        list.add(origin.offset(x, y, z));
                    }
                }
            }
        }
        return list.stream().filter(predicate).toList();
    }

    public static int getParticleViewRange() {
        return 12;
    }

    public static void activate(BlockState state, Level world, BlockPos pos) {
        world.scheduleTick(pos, state.getBlock(), 30 * 20);
        world.setBlock(pos, state.setValue(ACTIVE, true), 2);
        world.playSound(null, pos, GSoundEvents.MONSTROMETER_ACTIVATE, SoundSource.BLOCKS, 1, 1);
        world.updateNeighborsAt(pos, state.getBlock());
    }

    public static void deactivate(BlockState state, Level world, BlockPos pos) {
        world.setBlock(pos, state.setValue(ACTIVE, false).setValue(CHARGED, false), 3);
        world.playSound(null, pos, GSoundEvents.MONSTROMETER_DEACTIVATE, SoundSource.BLOCKS, 1, 1);
    }

    public static void setCharged(BlockState state, Level world, BlockPos pos) {
        world.setBlock(pos, state.cycle(CHARGED), 2);
        world.playSound(null, pos, GSoundEvents.MONSTROMETER_CHARGE, SoundSource.BLOCKS, 1, 1);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(GBlocks.LUMIERE_BLOCK.asItem())) {
            if (!isCharged(state)) {
                setCharged(state, world, pos);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                return InteractionResult.SUCCESS;
            }
        } else {
            if (isCharged(state) && !isActive(state)) {
                activate(state, world, pos);
                world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (isActive(state)) deactivate(state, world, pos);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MonstrometerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> te) {
        return !world.isClientSide ? createTickerHelper(te, GBlockEntityTypes.MONSTROMETER, MonstrometerBlockEntity::tick) : null;
    }
}
