package net.orcinus.galosphere.asm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;

public class GalosphereASM implements Runnable {

    private String getIntermediaryClass(String path) {
        return FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", path);
    }

    @Override
    public void run() {
        ClassTinkerers.enumBuilder(getIntermediaryClass("net.minecraft.class_1886")).addEnumSubclass("GALOSPHERE_SALTBOUND_TABLET", "net.orcinus.galosphere.asm.SaltboundTabletEnchantmentTarget").build();
    }
}
