package net.orcinus.galosphere.blocks;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.blocks.blockentities.AuraTransmitterBlockEntity;
import net.orcinus.galosphere.init.GBlockEntities;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GParticleTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class AuraTransmitterBlock extends BaseEntityBlock {
    public static final EnumProperty<TransmissionType> TYPE = EnumProperty.create("transmission", TransmissionType.class);

    public AuraTransmitterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, TransmissionType.NONE));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.AMETHYST_BLOCK)) {
            setTypeAndUpdate(state, world, pos, TransmissionType.AMETHYST, 200);
            return InteractionResult.SUCCESS;
        }
        else if (stack.is(GBlocks.ALLURITE_BLOCK.get().asItem())) {
            setTypeAndUpdate(state, world, pos, TransmissionType.ALLURITE, 200);
            return InteractionResult.SUCCESS;
        }
        else if (stack.is(GBlocks.LUMIERE_BLOCK.get().asItem())) {
            setTypeAndUpdate(state, world, pos, TransmissionType.LUMIERE, 200);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    private void setTypeAndUpdate(BlockState state, Level world, BlockPos pos, TransmissionType type, int ticks) {
        world.setBlock(pos, state.setValue(TYPE, type), 2);
        world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.scheduleTick(pos, this, ticks);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AuraTransmitterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> te) {
        TransmissionType type = state.getValue(TYPE);
        if (type == TransmissionType.LUMIERE) {
            return !world.isClientSide() ? createTickerHelper(te, GBlockEntities.AURA_TRANSMITTER.get(), AuraTransmitterBlockEntity::lumiereTick) : null;
        } else if (type == TransmissionType.AMETHYST) {
            return !world.isClientSide() ? createTickerHelper(te, GBlockEntities.AURA_TRANSMITTER.get(), AuraTransmitterBlockEntity::amethystTick) : null;
        } else {
            return super.getTicker(world, state, te);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        EntityType<?> dummy = EntityType.ZOMBIE;
        for (BlockPos blockPos : this.getRadius(pos)) {
            if (state.getValue(TYPE) == TransmissionType.ALLURITE) {
                if (world.getBrightness(LightLayer.BLOCK, blockPos) == 0 && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, world, blockPos, dummy)) {
                    double posX = blockPos.getX() + 0.5D;
                    double posY = blockPos.getY();
                    double posZ = blockPos.getZ() + 0.5D;
                    world.addParticle(GParticleTypes.AURA_LISTENER.get(), posX, posY + 0.01D, posZ, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(TYPE) != TransmissionType.NONE) {
            world.setBlockAndUpdate(pos, state.setValue(TYPE, TransmissionType.NONE));
            world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.BLOCKS, 1.0F, 1.0F);
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
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    public enum TransmissionType implements StringRepresentable {
        NONE("none"),
        ALLURITE("allurite"),
        LUMIERE("lumiere"),
        AMETHYST("amethyst");

        private final String name;

        TransmissionType(String name) {
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
