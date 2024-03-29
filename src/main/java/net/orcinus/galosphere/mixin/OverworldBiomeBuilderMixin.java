package net.orcinus.galosphere.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.orcinus.galosphere.init.GBiomes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public class OverworldBiomeBuilderMixin {

    @Shadow @Final private Climate.Parameter FULL_RANGE;

    @Shadow @Final private Climate.Parameter[] erosions;

    @Shadow @Final private Climate.Parameter[] humidities;

    @Inject(at = @At("RETURN"), method = "addUndergroundBiomes")
    public void G$addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo ci) {
        consumer.accept(Pair.of(Climate.parameters(this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.7F, 1.0F), Climate.Parameter.span(this.erosions[0], this.erosions[1]), Climate.Parameter.span(0.2F, 0.9F), this.FULL_RANGE, 0.0F), GBiomes.CRYSTAL_CANYONS));
        consumer.accept(Pair.of(Climate.parameters(this.FULL_RANGE, Climate.Parameter.span(-1.0F, -0.7F), Climate.Parameter.span(-0.7F, -0.5F), Climate.Parameter.span(-0.9F, -0.7F), Climate.Parameter.span(0.2F, 0.9F), this.FULL_RANGE, 0.0F), GBiomes.LICHEN_CAVES));
        consumer.accept(Pair.of(Climate.parameters(this.FULL_RANGE, this.humidities[4], Climate.Parameter.span(0.5F, 0.7F), Climate.Parameter.span(-0.8F, -0.7F), Climate.Parameter.span(0.2F, 1.1F), this.FULL_RANGE, 0.0F), GBiomes.PINK_SALT_CAVES));
    }

}
