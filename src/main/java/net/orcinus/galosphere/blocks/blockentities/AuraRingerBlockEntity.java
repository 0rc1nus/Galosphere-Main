package net.orcinus.galosphere.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.blocks.AuraRingerBlock;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GMobEffects;

import java.util.List;

public class AuraRingerBlockEntity extends BlockEntity {

    public AuraRingerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(GBlockEntityTypes.AURA_RINGER.get(), pWorldPosition, pBlockState);
    }

    public static void ringingTick(Level world, BlockPos pos, BlockState state, AuraRingerBlockEntity type) {
        List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(16.0D));
        if (state.getBlock() instanceof AuraRingerBlock block) {
            if (block.isRinging(state)) {
                for (LivingEntity livingEntity : list) {
                    if (livingEntity instanceof Mob mob) {
                        if (mob.isInvertedHealAndHarm() && mob.getTarget() == null) {
                            Vec3 vec3 = Vec3.atBottomCenterOf(pos).add(0.0D, 0.6F, 0.0D);
                            mob.getNavigation().moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.5D);
                            if (mob.blockPosition().closerThan(pos, 3)) {
                                mob.getLookControl().setLookAt(vec3.x(), vec3.y(), vec3.z());
                            }
                            mob.addEffect(new MobEffectInstance(GMobEffects.ILLUSIVE.get(), 200));
                        }
                    }
                    if (livingEntity instanceof Player player) {
                        if (player.getAbilities().instabuild) return;
                        player.addEffect(new MobEffectInstance(GMobEffects.ILLUSIVE.get(), 200));
                    }
                }
            }
        }
    }

}
