package net.orcinus.galosphere.api;

public interface GoldenBreath {

    void setGoldenAirSupply(float goldenAirSupply);

    float getGoldenAirSupply();

    default float getMaxGoldenAirSupply() {
        return 300;
    }

}
