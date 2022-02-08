package net.orcinus.cavesandtrenches.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTParticleTypes;

import java.util.Random;

public class AuraListenerBlock extends Block {
    public static final EnumProperty<AuraSignalType> TYPE = EnumProperty.create("aura_type", AuraSignalType.class);

    public AuraListenerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, AuraSignalType.INACTIVE));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == CTBlocks.ALLURITE_BLOCK.get().asItem() && state.getValue(TYPE) == AuraSignalType.INACTIVE) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos);
            world.setBlock(pos, state.setValue(TYPE, AuraSignalType.ACTIVE), 2);
            world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else if (state.getValue(TYPE) == AuraSignalType.ACTIVE) {
            world.setBlock(pos, state.setValue(TYPE, AuraSignalType.COOLDOWN), 2);
            world.playSound(null, pos, SoundEvents.SCULK_CLICKING, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos blockPos, boolean p_60514_) {
        if (state.getValue(TYPE) == AuraSignalType.ACTIVE && world.hasNeighborSignal(pos)) {
            world.setBlock(pos, state.setValue(TYPE, AuraSignalType.COOLDOWN), 2);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(TYPE) == AuraSignalType.COOLDOWN) {
            world.setBlock(pos, state.setValue(TYPE, AuraSignalType.INACTIVE), 2);
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState blockState, boolean p_60570_) {
        if (state.getValue(TYPE) == AuraSignalType.COOLDOWN) {
            world.scheduleTick(pos, state.getBlock(), 200);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int radius = 8;
        int height = 8;
        if (state.getValue(TYPE) == AuraSignalType.COOLDOWN) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = -height; y <= height; y++) {
                        if ((x * x) + (z * z) <= (radius * radius)) {
                            mutableBlockPos.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            if (!world.isEmptyBlock(mutableBlockPos.below()) && world.isEmptyBlock(mutableBlockPos)) {
                                EntityType<?> entitytype = EntityType.ZOMBIE;
                                if (world.getBrightness(LightLayer.BLOCK, mutableBlockPos) == 0 && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, world, mutableBlockPos, entitytype)) {
                                    double posX = mutableBlockPos.getX() + 0.5D;
                                    double posY = mutableBlockPos.getY() + 1.0D;
                                    double posZ = mutableBlockPos.getZ() + 0.5D;
                                    if (random.nextInt(5) == 0) {
                                        world.addParticle(ParticleTypes.SOUL, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
                                        world.addParticle(CTParticleTypes.AURA_LISTENER.get(), posX, 0.05 + mutableBlockPos.getY(), posZ, 0.0D, 0.0D, 0.0D);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    public enum AuraSignalType implements StringRepresentable {
        ACTIVE("active"),
        INACTIVE("inactive"),
        COOLDOWN("cooldown");

        private final String name;

        AuraSignalType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

    }
}
