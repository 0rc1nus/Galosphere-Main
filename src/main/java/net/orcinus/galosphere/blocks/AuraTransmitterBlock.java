package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.api.ISoulWince;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GParticleTypes;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class AuraTransmitterBlock extends Block {
    public static final EnumProperty<TransmissionType> TYPE = EnumProperty.create("transmission", TransmissionType.class);

    public AuraTransmitterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, TransmissionType.NONE));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        ItemStack stack = player.getItemInHand(hand);
        if (state.getValue(TYPE) == TransmissionType.NONE) {
            if (this.isValidStack(stack)) {
                if (stack.is(Items.AMETHYST_BLOCK)) {
                    this.setTransmissionState(state, world, pos, TransmissionType.AMETHYST);
                    this.scheduleTick(world, pos, 500);
                } else if (stack.is(GBlocks.ALLURITE_BLOCK.get().asItem())) {
                    this.setTransmissionState(state, world, pos, TransmissionType.ALLURITE);
                    this.scheduleTick(world, pos, 200);
                } else if (stack.is(GBlocks.LUMIERE_BLOCK.get().asItem())) {
                    this.setTransmissionState(state, world, pos, TransmissionType.LUMIERE);
                    this.scheduleTick(world, pos, 200);
                }
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, p_60508_);
    }

    public boolean isValidStack(ItemStack stack) {
        return stack.is(Items.AMETHYST_BLOCK) || stack.is(GBlocks.ALLURITE_BLOCK.get().asItem()) || stack.is(GBlocks.LUMIERE_BLOCK.get().asItem());
    }

    private void scheduleTick(Level world, BlockPos pos, int p_186463_) {
        world.scheduleTick(pos, this, p_186463_);
    }

    private void setTransmissionState(BlockState state, Level world, BlockPos pos, TransmissionType type) {
        world.setBlock(pos, state.setValue(TYPE, type), 2);
        world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
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
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(TYPE) == TransmissionType.LUMIERE) {
            List<LivingEntity> entities = getNearbyEntities(world, pos, 16.0D);
            for (LivingEntity entity : entities) {
                if (entity.isInvertedHealAndHarm()) {
                    entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1));
                }
            }
        }
        if (state.getValue(TYPE) == TransmissionType.AMETHYST) {
            List<LivingEntity> entities = getNearbyEntities(world, pos, 8.0D);
            for (LivingEntity entity : entities) {
                if (entity instanceof ISoulWince winced) {
                    if (entity.isInvertedHealAndHarm()) {
                        if (!winced.isWinced()) {
                            winced.setWinced(true);
                        }
                    }
                }
            }
        }
        if (state.getValue(TYPE) != TransmissionType.NONE) {
            resetToDefault(world, pos, state);
        }
    }

    @NotNull
    public static List<LivingEntity> getNearbyEntities(ServerLevel world, BlockPos pos, double range) {
        return world.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(range));
    }

    private void resetToDefault(ServerLevel world, BlockPos pos, BlockState state) {
        world.setBlock(pos, state.setValue(TYPE, TransmissionType.NONE), 2);
        world.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
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
