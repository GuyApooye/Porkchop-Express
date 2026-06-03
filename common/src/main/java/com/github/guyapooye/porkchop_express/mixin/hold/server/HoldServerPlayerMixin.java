package com.github.guyapooye.porkchop_express.mixin.hold.server;

import com.github.guyapooye.porkchop_express.ext.hold.ServerLevelHoldExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class HoldServerPlayerMixin {
    
    @Shadow
    public abstract ServerLevel serverLevel();
    
    @Inject(method = "disconnect", at = @At("HEAD"))
    private void removeFromHoldingManager(CallbackInfo ci) {
        ((ServerLevelHoldExtension) this.serverLevel()).porkchop_express$getHoldingManager()
                .removeHoldingPoint((ServerPlayer) (Object) this);
    }
    
}
