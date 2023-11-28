package net.orcinus.galosphere.api;

import net.orcinus.galosphere.util.SaltLayer;

public interface SaltBound {

    int getSaltLayers();

    void setSaltLayers(SaltLayer saltLayers);

    int getSaltDegradation();

    void setSaltDegradation(int saltDegradation);

}
