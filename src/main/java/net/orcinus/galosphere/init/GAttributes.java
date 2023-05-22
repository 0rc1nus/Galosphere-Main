package net.orcinus.galosphere.init;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Galosphere.MODID);

    public static final RegistryObject<Attribute> ILLAGER_RESISTANCE = ATTRIBUTES.register("illager_resistance", () -> (new RangedAttribute("attribute.name.generic.illager_resistance", 0.0D, 0.0D, 10.0D)));

}
