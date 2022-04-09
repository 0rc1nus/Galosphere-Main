package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.CTParticleTypes;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Random;

public class AuraListenerBlock extends Block {
    public static final BooleanProperty LISTENING = BooleanProperty.create("listening");
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    public AuraListenerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LISTENING, false).setValue(LEVEL, 0));
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos blockPos, Random random) {
        EntityType<?> dummy = EntityType.ZOMBIE;
        for (BlockPos radiusPos : this.getRadius(blockPos)) {
            if (state.getValue(LISTENING)) {
                if (world.getBrightness(LightLayer.BLOCK, radiusPos) == 0 && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, world, radiusPos, dummy)) {
                    double posX = radiusPos.getX() + 0.5D;
                    double posY = radiusPos.getY();
                    double posZ = radiusPos.getZ() + 0.5D;
                    world.addParticle(CTParticleTypes.AURA_LISTENER.get(), posX, posY + 0.01D, posZ, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    public List<BlockPos> getRadius(BlockPos blockPos) {
        int radius = 8;
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

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (!state.getValue(LISTENING)) {
            if (stack.is(GBlocks.ALLURITE_BLOCK.get().asItem())) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                this.activate(state, pos, world);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(GBlocks.LUMIERE_BLOCK.get().asItem())) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                world.setBlock(pos, state.setValue(LEVEL, 15), 2);
                world.scheduleTick(pos, this, 400);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(Items.REDSTONE_BLOCK)) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
                world.scheduleTick(pos, this, 200);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(LISTENING)) {
            if (state.getValue(LEVEL) > 0) {
                world.setBlock(pos, state.setValue(LEVEL, state.getValue(LEVEL) - 1), 2);
            } else {
                world.setBlock(pos, state.setValue(LISTENING, false), 2);
            }
            world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    public void activate(BlockState state, BlockPos pos, Level world) {
        world.setBlock(pos, state.setValue(LISTENING, true), 2);
        world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.scheduleTick(pos, this, 200);
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
        builder.add(LISTENING, LEVEL);
    }
    
}
