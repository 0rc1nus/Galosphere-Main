package net.orcinus.galosphere.api;

import net.orcinus.galosphere.util.SaltLayers;

public interface SaltBound {

    int getSaltLayers();

    void setSaltLayers(SaltLayers saltLayers);

    int getSaltDegradation();

    void setSaltDegradation(int saltDegradation);

}
