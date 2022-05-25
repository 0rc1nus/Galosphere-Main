package net.orcinus.galosphere.blocks;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.blocks.blockentities.AuraRingerBlockEntity;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GParticleTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class AuraRingerBlock extends BaseEntityBlock {
    public static final BooleanProperty RINGING = BooleanProperty.create("ringing");

    public AuraRingerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(RINGING, false));
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random pRandom) {
        EntityType<?> dummy = EntityType.ZOMBIE;
        for (BlockPos blockPos : this.getRadius(pos)) {
            if (state.getValue(RINGING)) {
                if (world.isEmptyBlock(blockPos) && world.getBrightness(LightLayer.BLOCK, blockPos) == 0 && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, world, blockPos, dummy)) {
                    double posX = blockPos.getX() + 0.5D;
                    double posY = blockPos.getY();
                    double posZ = blockPos.getZ() + 0.5D;
                    world.addParticle(GParticleTypes.AURA_LISTENER.get(), posX, posY + 0.1D, posZ, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    public List<BlockPos> getRadius(BlockPos blockPos) {
        int radius = 16;
        int height = 3;
        List<BlockPos> position = Lists.newArrayList();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -height; y <= height; y++) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    if (x * x + z * z <= radius * radius) {
                        position.add(pos);
                    }
                }
            }
        }
        if (position.isEmpty()) return null;

        return position;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> pBlockEntityType) {
        return !world.isClientSide() ? createTickerHelper(pBlockEntityType, GBlockEntityTypes.AURA_RINGER.get(), AuraRingerBlockEntity::ringingTick) : super.getTicker(world, state, pBlockEntityType);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (!state.getValue(RINGING) && stack.getItem() == GBlocks.ALLURITE_BLOCK.get().asItem()) {
            this.activate(state, world, pos);
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        else {
            return super.use(state, world, pos, pPlayer, pHand, pHit);
        }
    }

    public void activate(BlockState state, Level world, BlockPos pos) {
        world.scheduleTick(pos, this, 400);
        world.setBlock(pos, state.setValue(RINGING, true), 2);
        for (int i = 0; i < 20; i++) {
            world.addParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0, 0.0, 0.0);
        }
        world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.5F);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random pRandom) {
        if (state.getValue(RINGING)) {
            world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.setBlock(pos, state.setValue(RINGING, false), 3);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AuraRingerBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(RINGING);
    }
}
