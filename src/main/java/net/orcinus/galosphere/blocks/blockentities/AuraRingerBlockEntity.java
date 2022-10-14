package net.orcinus.galosphere.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
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

    public AuraRingerBlockEntity(BlockPos worldPosition, BlockState state) {
        super(GBlockEntityTypes.AURA_RINGER, worldPosition, state);
    }

    public static void ringingTick(Level world, BlockPos pos, BlockState state, AuraRingerBlockEntity type) {
        List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(16.0D));
        if (state.getBlock() instanceof AuraRingerBlock) {
            if (state.getValue(AuraRingerBlock.RINGING)) {
                for (LivingEntity livingEntity : list) {
                    MobEffectInstance illusive = new MobEffectInstance(GMobEffects.ILLUSIVE, 200, 0, false, false);
                    if (livingEntity instanceof Villager villager) {
                        Vec3 vec3 = Vec3.atBottomCenterOf(pos).add(0.0D, 0.6F, 0.0D);
                        villager.getNavigation().moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.5D);
                        if (villager.blockPosition().closerThan(pos, 3)) {
                            villager.getLookControl().setLookAt(vec3.x(), vec3.y(), vec3.z());
                        }
                        villager.addEffect(illusive);
                    }
                    if (livingEntity instanceof Player player) {
                        if (player.getAbilities().instabuild) return;
                        if (!player.hasEffect(GMobEffects.ILLUSIVE) || (player.hasEffect(GMobEffects.ILLUSIVE) && player.getEffect(GMobEffects.ILLUSIVE).getDuration() < 100)) {
                            player.addEffect(illusive);
                        }
                    }
                }
            }
        }
    }

}
