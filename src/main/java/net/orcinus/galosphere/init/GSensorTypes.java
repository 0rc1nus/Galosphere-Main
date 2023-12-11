package net.orcinus.galosphere.init;

import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.ai.SparkleAi;
import net.orcinus.galosphere.entities.ai.SpectreAi;
import net.orcinus.galosphere.entities.ai.sensors.BerserkerEntitySensor;
import net.orcinus.galosphere.entities.ai.sensors.NearestLichenMossSensor;
import net.orcinus.galosphere.entities.ai.sensors.NearestPollinatedClusterSensor;
import net.orcinus.galosphere.entities.ai.sensors.PreservedEntitySensor;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GSensorTypes {

    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, Galosphere.MODID);

    public static final RegistryObject<SensorType<TemptingSensor>> SPARKLE_TEMPTATIONS = SENSOR_TYPES.register("sparkle_temptations", () -> new SensorType<>(() -> new TemptingSensor(SparkleAi.getTemptations())));
    public static final RegistryObject<SensorType<TemptingSensor>> SPECTRE_TEMPTATIONS = SENSOR_TYPES.register("spectre_temptations", () -> new SensorType<>(() -> new TemptingSensor(SpectreAi.getTemptations())));
    public static final RegistryObject<SensorType<NearestPollinatedClusterSensor>> NEAREST_POLLINATED_CLUSTER = SENSOR_TYPES.register("nearest_pollinated_cluster", () -> new SensorType<>(NearestPollinatedClusterSensor::new));
    public static final RegistryObject<SensorType<NearestLichenMossSensor>> NEAREST_LICHEN_MOSS = SENSOR_TYPES.register("nearest_lichen_moss", () -> new SensorType<>(NearestLichenMossSensor::new));
    public static final RegistryObject<SensorType<BerserkerEntitySensor>> BLIGHTED_ENTITY_SENSOR = SENSOR_TYPES.register("blighted_entity_sensor", () -> new SensorType<>(BerserkerEntitySensor::new));
    public static final RegistryObject<SensorType<PreservedEntitySensor>> PRESERVED_ENTITY_SENSOR = SENSOR_TYPES.register("preserved_entity_sensor", () -> new SensorType<>(PreservedEntitySensor::new));

}