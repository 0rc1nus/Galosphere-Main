package net.orcinus.galosphere.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.blockentities.AuraListenerBlockEntity;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Galosphere.MODID);

    public static final RegistryObject<BlockEntityType<AuraListenerBlockEntity>> AURA_LISTENER = BLOCK_ENTITIES.register("aura_listener", () -> BlockEntityType.Builder.of(AuraListenerBlockEntity::new, CTBlocks.AURA_LISTENER.get()).build(null));

}
