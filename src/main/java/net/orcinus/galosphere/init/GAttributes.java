package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.orcinus.galosphere.Galosphere;

import java.util.Map;

public class GAttributes {

    public static final Map<ResourceLocation, Attribute> ATTRIBUTES = Maps.newLinkedHashMap();

    public static final Attribute EXPLOSION_RESISTANCE = registerAttribute("explosion_resistance", (new RangedAttribute("attribute.name.generic.explosion_resistance", 0.0D, 0.0D, 10.0D)));

    public static <A extends Attribute> A registerAttribute(String name, A attribute) {
        ATTRIBUTES.put(new ResourceLocation(Galosphere.MODID, name), attribute);
        return attribute;
    }

    public static void init() {
        for (ResourceLocation id : ATTRIBUTES.keySet()) {
            Registry.register(Registry.ATTRIBUTE, id, ATTRIBUTES.get(id));
        }
    }

}
