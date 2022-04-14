package net.orcinus.galosphere.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.blockentities.AuraTransmitterBlockEntity;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Galosphere.MODID);

    public static final RegistryObject<BlockEntityType<AuraTransmitterBlockEntity>> AURA_TRANSMITTER = BLOCK_ENTITIES.register("aura_transmitter", () -> BlockEntityType.Builder.of(AuraTransmitterBlockEntity::new, GBlocks.AURA_TRANSMITTER.get()).build(null));

}



