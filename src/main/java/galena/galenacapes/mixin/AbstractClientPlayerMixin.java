package galena.galenacapes.mixin;

import galena.galenacapes.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Objects;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    /*
        @author: Xaidee

        For the most recent version of this file please refer to https://github.com/Xaidee/Galena-Capes/blob/1.18/Common/src/main/java/galena/galenacapes/mixin/AbstractClientPlayerMixin.java
     */

    @Shadow @Nullable
    protected abstract PlayerInfo getPlayerInfo();

    @Inject(method= "getCloakTextureLocation()Lnet/minecraft/resources/ResourceLocation;", at=@At("RETURN"), cancellable = true)

    public void getCloakTextureLocation(CallbackInfoReturnable<ResourceLocation> cir) {
        assert Minecraft.getInstance().player != null;
        if(!(Objects.requireNonNull(this.getPlayerInfo()).getProfile().getId().equals(Minecraft.getInstance().player.getUUID()))) {
            cir.setReturnValue(null);
        } else {
            String username = this.getPlayerInfo().getProfile().getName();

            for (int i = 0; Constants.Dev.size() > i; i++) {
                if (Constants.Dev.get(i).equals(username)) cir.setReturnValue(new ResourceLocation(Constants.MOD_ID, "textures/capes/dev.png"));
            }
            for (int i = 0; Constants.OPatreons.size() > i; i++) {
                if (Constants.OPatreons.get(i).equals(username)) cir.setReturnValue(new ResourceLocation(Constants.MOD_ID, "textures/capes/oreganized.png"));
            }
            for (int i = 0; Constants.OFPatreons.size() > i; i++) {
                if (Constants.OFPatreons.get(i).equals(username)) cir.setReturnValue(new ResourceLocation(Constants.MOD_ID, "textures/capes/overweightfarming.png"));
            }
            for (int i = 0; Constants.GPatreonsBlue.size() > i; i++) {
                if (Constants.GPatreonsBlue.get(i).equals(username)) cir.setReturnValue(new ResourceLocation(Constants.MOD_ID, "textures/capes/galosphere_blue.png"));
            }
            for (int i = 0; Constants.GPatreonsYellow.size() > i; i++) {
                if (Constants.GPatreonsYellow.get(i).equals(username)) cir.setReturnValue(new ResourceLocation(Constants.MOD_ID, "textures/capes/galosphere_yellow.png"));
            }
            for (int i = 0; Constants.CPatreons.size() > i; i++) {
                if (Constants.CPatreons.get(i).equals(username)) cir.setReturnValue(new ResourceLocation(Constants.MOD_ID, "textures/capes/coopperative.png"));
            }
        }
    }
}
