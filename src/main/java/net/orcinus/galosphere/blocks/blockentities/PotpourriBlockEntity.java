package net.orcinus.galosphere.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GMobEffects;

import java.util.List;

public class PotpourriBlockEntity extends BlockEntity {
    public int tickCount;

    public PotpourriBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(GBlockEntityTypes.POTPOURRI, blockPos, blockState);
    }

    public static void tick(Level world, BlockPos pos, BlockState blockState, PotpourriBlockEntity potpourriBlockEntity) {
        potpourriBlockEntity.tickCount++;
        List<Player> list = world.getEntitiesOfClass(Player.class, new AABB(pos).inflate(5.0D));
        if (list.isEmpty()) {
            return;
        }
        if (potpourriBlockEntity.tickCount % 65 == 0) {
            list.forEach(monster -> monster.addEffect(new MobEffectInstance(GMobEffects.HARMONY, 260, 0, false, true)));
        }
    }

}
