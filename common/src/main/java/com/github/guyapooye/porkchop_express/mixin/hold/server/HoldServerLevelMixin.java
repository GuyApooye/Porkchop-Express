package com.github.guyapooye.porkchop_express.mixin.hold.server;

import com.github.guyapooye.porkchop_express.content.hold.ServerHoldingManager;
import com.github.guyapooye.porkchop_express.ext.hold.ServerLevelHoldExtension;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class HoldServerLevelMixin implements ServerLevelHoldExtension {
    
    @Unique
    private ServerHoldingManager porkchop_express$holdingManager = null;
    
    @Override
    public ServerHoldingManager porkchop_express$getHoldingManager() {
        if (this.porkchop_express$holdingManager == null) {
            this.porkchop_express$holdingManager = new ServerHoldingManager();
        }
        return this.porkchop_express$holdingManager;
    }
    
    @Inject(method = "tick", at = @At("TAIL"))
    private void tickHoldingManager(BooleanSupplier pHasTimeLeft, CallbackInfo ci) {
        this.porkchop_express$holdingManager.tick();
    }
}
