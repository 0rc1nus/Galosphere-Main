package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.ai.SparkleAi;
import net.orcinus.galosphere.entities.ai.SpectreAi;
import net.orcinus.galosphere.entities.ai.sensors.NearestLichenMossSensor;
import net.orcinus.galosphere.entities.ai.sensors.NearestPollinatedClusterSensor;

import java.util.Map;
import java.util.function.Supplier;

public class GSensorTypes {
    public static final Map<ResourceLocation, SensorType<?>> SENSOR_TYPES = Maps.newLinkedHashMap();

    public static final SensorType<TemptingSensor> SPARKLE_TEMPTATIONS = register("sparkle_temptations", () -> new TemptingSensor(SparkleAi.getTemptations()));
    public static final SensorType<TemptingSensor> SPECTRE_TEMPTATIONS = register("spectre_temptations", () -> new TemptingSensor(SpectreAi.getTemptations()));
    public static final SensorType<NearestPollinatedClusterSensor> NEAREST_POLLINATED_CLUSTER = register("nearest_pollinated_cluster", NearestPollinatedClusterSensor::new);
    public static final SensorType<NearestLichenMossSensor> NEAREST_LICHEN_MOSS = register("nearest_lichen_moss", NearestLichenMossSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(String string, Supplier<U> supplier) {
        SensorType<U> sensorType = new SensorType<>(supplier);
        SENSOR_TYPES.put(Galosphere.id(string), sensorType);
        return sensorType;
    }

    public static void init() {
        SENSOR_TYPES.forEach((resourceLocation, sensorType) -> Registry.register(BuiltInRegistries.SENSOR_TYPE, resourceLocation, sensorType));
    }

}