package net.orcinus.galosphere.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.orcinus.galosphere.Galosphere;

public class GCriterion extends SimpleCriterionTrigger<GCriterion.TriggerInstance> {
    private final ResourceLocation ID;

    public GCriterion(String name) {
        ID = new ResourceLocation(Galosphere.MODID, name);
    }

    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate composite, DeserializationContext deserializationContext) {
        return new GCriterion.TriggerInstance(ID, composite);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, conditions -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation resourceLocation, ContextAwarePredicate composite) {
            super(resourceLocation, composite);
        }

    }
}
