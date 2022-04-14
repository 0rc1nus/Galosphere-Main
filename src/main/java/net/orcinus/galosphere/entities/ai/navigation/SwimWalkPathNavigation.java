package net.orcinus.galosphere.entities.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;

public class SwimWalkPathNavigation extends WaterBoundPathNavigation {

    public SwimWalkPathNavigation(Mob mob, Level world) {
        super(mob, world);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new AmphibiousNodeEvaluator(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return !this.level.getBlockState(pos.below()).isAir();
    }

}
