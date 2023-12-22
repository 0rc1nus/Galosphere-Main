package net.orcinus.galosphere.blocks;

import com.sun.jna.platform.win32.WinDef;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.orcinus.galosphere.blocks.blockentities.PinkSaltChamberBlockEntity;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import org.jetbrains.annotations.Nullable;

public class PinkSaltChamberBlock extends BaseEntityBlock {
    public static final EnumProperty<ChamberPhase> PHASE = EnumProperty.create("chamber_phase", ChamberPhase.class);

    public PinkSaltChamberBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PHASE, ChamberPhase.INACTIVE));
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        boolean flag2 = false;
        for (Direction dir : Direction.values()) {
            BlockState relativeState = levelAccessor.getBlockState(blockPos.relative(dir));
            boolean flag = relativeState.is(GBlocks.PINK_SALT_CLUSTER) && relativeState.getValue(PinkSaltClusterBlock.FACING) == dir;
            if (flag) {
                flag2 = true;
                break;
            }
        }
        boolean flag = levelAccessor.getBlockEntity(blockPos) instanceof PinkSaltChamberBlockEntity pinkSaltChamberBlockEntity && pinkSaltChamberBlockEntity.getCooldown() < 6000;
        if (flag) {
            return blockState.setValue(PHASE, ChamberPhase.COOLDOWN);
        } else {
            return blockState.setValue(PHASE, flag2 ? ChamberPhase.CHARGED : ChamberPhase.INACTIVE);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PHASE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PinkSaltChamberBlockEntity(blockPos, blockState);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState blockState, @Nullable LivingEntity entity, ItemStack itemStack) {
        super.setPlacedBy(world, pos, blockState, entity, itemStack);
        if (entity instanceof Player player && player.getAbilities().instabuild && world.getBlockEntity(pos) instanceof PinkSaltChamberBlockEntity pinkSaltChamberBlockEntity) {
            pinkSaltChamberBlockEntity.setCooldown(pinkSaltChamberBlockEntity.maxCooldown);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : PinkSaltChamberBlock.createTickerHelper(blockEntityType, GBlockEntityTypes.PINK_SALT_CHAMBER, PinkSaltChamberBlockEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    public enum ChamberPhase implements StringRepresentable {
        INACTIVE("inactive"),
        CHARGED("charged"),
        COOLDOWN("cooldown");

        private final String name;

        ChamberPhase(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}
