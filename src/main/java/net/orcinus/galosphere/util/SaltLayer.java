package net.orcinus.galosphere.util;

import net.minecraft.util.ByIdMap;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum SaltLayer {
    NONE(null, 0, 0),
    WEAK("weak", 1, 200),
    AVERAGE("average", 2, 400),
    STRONG("strong", 3, 600);

    private static final IntFunction<SaltLayer> BY_ID = ByIdMap.continuous(SaltLayer::getId, SaltLayer.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    private final String name;
    private final int id;
    private final int durability;

    SaltLayer(String name, int id, int durability) {
        this.name = name;
        this.id = id;
        this.durability = durability;
    }

    public static SaltLayer byId(int i) {
        return BY_ID.apply(i);
    }

    @Nullable
    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int getDurability() {
        return this.durability;
    }
}
