//package net.orcinus.galosphere.config;
//
//import com.terraformersmc.modmenu.api.ConfigScreenFactory;
//import com.terraformersmc.modmenu.api.ModMenuApi;
//import eu.midnightdust.core.MidnightLibClient;
//import eu.midnightdust.core.config.MidnightLibConfig;
//import eu.midnightdust.lib.config.MidnightConfig;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Environment(EnvType.CLIENT)
//public class GalosphereModMenu implements ModMenuApi {
//
//    @Override
//    public ConfigScreenFactory<?> getModConfigScreenFactory() {
//        return parent -> MidnightLibConfig.getScreen(parent, "galosphere");
//    }
//
//    @Override
//    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
//        HashMap<String, ConfigScreenFactory<?>> map = new HashMap<>();
//        MidnightConfig.configClass.forEach((s, aClass) -> {
//            if (!MidnightLibClient.hiddenMods.contains(s)) {
//                map.put(s, screen -> MidnightConfig.getScreen(screen, s));
//            }
//        });
//        return map;
//    }
//
//}
