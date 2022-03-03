package net.orcinus.cavesandtrenches.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.cavesandtrenches.blocks.blockentities.AuraListenerBlockEntity;
import net.orcinus.cavesandtrenches.init.CTBlockEntities;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTItems;
import net.orcinus.cavesandtrenches.init.CTParticleTypes;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class AuraListenerBlock extends BaseEntityBlock {
    public static final BooleanProperty LISTENING = BooleanProperty.create("listening");

    public AuraListenerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LISTENING, false));
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos blockPos, Random random) {
        EntityType<?> dummy = EntityType.ZOMBIE;
        int range = getRange() / 2;
        int height = 3;
        if (state.getValue(LISTENING)) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    for (int y = -height; y <= height; y++) {
                        BlockPos position = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                        if (x * x + z * z <= range * range) {
                            if (world.getBrightness(LightLayer.BLOCK, position) == 0 && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, world, position, dummy)) {
                                double posX = position.getX() + 0.5D;
                                double posY = position.getY();
                                double posZ = position.getZ() + 0.5D;
                                world.addParticle(CTParticleTypes.AURA_LISTENER.get(), posX, posY + 0.01D, posZ, 0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (!state.getValue(LISTENING) && stack.is(CTBlocks.ALLURITE_BLOCK.get().asItem())) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            this.activate(state, pos, world);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(LISTENING)) {
            world.setBlock(pos, state.setValue(LISTENING, false), 2);
            world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    public void activate(BlockState state, BlockPos pos, Level world) {
        world.setBlock(pos, state.setValue(LISTENING, true), 2);
        world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.scheduleTick(pos, this, 200);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return !world.isClientSide ? createTickerHelper(type, CTBlockEntities.AURA_LISTENER.get(), AuraListenerBlockEntity::listenTick) : null;
    }

    public static int getRange() {
        return 16;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LISTENING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AuraListenerBlockEntity(pos, state);
    }
}
