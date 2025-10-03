package net.orcinus.galosphere.events;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.crafting.LumiereReformingManager;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.entities.Preserved;
import net.orcinus.galosphere.entities.Sparkle;
import net.orcinus.galosphere.entities.SpectatorVision;
import net.orcinus.galosphere.entities.Specterpillar;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.network.BarometerPacket;
import net.orcinus.galosphere.network.PlayCooldownSoundPacket;
import net.orcinus.galosphere.network.SendParticlesPacket;
import net.orcinus.galosphere.network.SendPerspectivePacket;
import net.orcinus.galosphere.network.handler.ClientEventsHandler;
import net.orcinus.galosphere.network.handler.ServerEventsHandler;


@EventBusSubscriber(modid = Galosphere.MODID)
public class RegisterEvents {

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(GEntityTypes.SPARKLE.get(), Sparkle.createAttributes().build());
        event.put(GEntityTypes.SPECTRE.get(), Spectre.createAttributes().build());
        event.put(GEntityTypes.SPECTERPILLAR.get(), Specterpillar.createAttributes().build());
        event.put(GEntityTypes.SPECTATOR_VISION.get(), SpectatorVision.createAttributes().build());
        event.put(GEntityTypes.BERSERKER.get(), Berserker.createAttributes().build());
        event.put(GEntityTypes.PRESERVED.get(), Preserved.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(GEntityTypes.SPARKLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Sparkle::checkSparkleSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(GEntityTypes.SPECTRE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void registerPayloadHandler(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        
        registrar.playToClient(
                BarometerPacket.TYPE,
                BarometerPacket.CODEC,
                ServerEventsHandler::sendBarometerInfo
        );
        registrar.playToClient(
                PlayCooldownSoundPacket.TYPE,
                PlayCooldownSoundPacket.CODEC,
                ServerEventsHandler::playCooldownSound
        );
        registrar.playToClient(
                SendParticlesPacket.TYPE,
                SendParticlesPacket.CODEC,
                ServerEventsHandler::handleSendParticles
        );
        registrar.playToClient(
                SendPerspectivePacket.TYPE,
                SendPerspectivePacket.CODEC,
                ServerEventsHandler::sendPerspective
        );
    }

}