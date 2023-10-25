package net.orcinus.galosphere.client.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public class PinkSaltPillarAnimations {

    public static final AnimationDefinition PINK_SALT_PILLAR_EMERGE = AnimationDefinition.Builder.withLength(0.125f)
            .addAnimation("spike",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0.5f, 0f, 0.5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.scaleVec(0.8f, 0.4f, 0.8f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.125f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM))).build();

    public static final AnimationDefinition PINK_SALT_PILLAR_RETRACT = AnimationDefinition.Builder.withLength(0.16766666f)
            .addAnimation("spike",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.scaleVec(0.7f, 1f, 0.7f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.16766666f, KeyframeAnimations.scaleVec(0.7f, 0f, 0.6f),
                                    AnimationChannel.Interpolations.CATMULLROM))).build();

}
