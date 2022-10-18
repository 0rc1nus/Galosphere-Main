package net.orcinus.galosphere.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public record DistanceComparator(BlockPos blockPos) implements Comparator<BlockPos> {

    @Override
    public int compare(BlockPos o1, BlockPos o2) {
        double distance1 = this.getDistance(o1);
        double distance2 = this.getDistance(o2);
        return Double.compare(distance1, distance2);
    }

    private double getDistance(BlockPos pos) {
        return pos.distToCenterSqr(Vec3.atCenterOf(this.blockPos));
    }

}