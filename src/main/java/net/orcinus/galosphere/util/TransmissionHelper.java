package net.orcinus.galosphere.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.LinkedList;
import java.util.List;

public class TransmissionHelper {
    private static final Direction.Plane HORIZONTAL = Direction.Plane.HORIZONTAL;
    private List<BlockPos> currentPos;
    private List<BlockPos> lastPos;

    public void setStartingPosition(BlockPos pos) {
        for (Direction direction : HORIZONTAL) {
            this.currentPos.add(pos.relative(direction));
        }
    }

}
