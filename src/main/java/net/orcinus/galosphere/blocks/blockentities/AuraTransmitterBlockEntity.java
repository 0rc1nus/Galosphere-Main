package net.orcinus.galosphere.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.orcinus.galosphere.api.ISoulWince;
import net.orcinus.galosphere.init.GBlockEntities;

import java.util.List;

public class AuraTransmitterBlockEntity extends BlockEntity {

    public AuraTransmitterBlockEntity(BlockPos pos, BlockState state) {
        super(GBlockEntities.AURA_TRANSMITTER.get(), pos, state);
    }

    public static void lumiereTick(Level world, BlockPos pos, BlockState state, AuraTransmitterBlockEntity type) {
        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(8.0D));
        for (LivingEntity entity : entities) {
            if (entity.isInvertedHealAndHarm()) {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20));
            }
        }
    }

    public static void amethystTick(Level world, BlockPos pos, BlockState state, AuraTransmitterBlockEntity type) {
        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(8.0D));
        for (LivingEntity entity : entities) {
            if (entity.isInvertedHealAndHarm()) {
                if (entity instanceof ISoulWince soulWince) {
                    soulWince.setWinced(!soulWince.isWinced());
                }
            }
        }
    }

}
