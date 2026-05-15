package com.github.guyapooye.porkchop_express.fabric;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.foundation.client.BlockRenderTypes;
import com.github.guyapooye.porkchop_express.foundation.client.BlockEntityRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

public final class FabricPorkchopExpress extends PorkchopExpress implements ModInitializer {
    
    @Override
    public void onInitialize() {
        this.init();
    }
    
    public static class Client extends PorkchopExpress.Client implements ClientModInitializer {
        
        @Override
        public void onInitializeClient() {
            this.clientInit();
            BlockRenderTypes.registerRenderTypes(BlockRenderLayerMap.INSTANCE::putBlock);
            BlockEntityRenderers.register(net.minecraft.client.renderer.blockentity.BlockEntityRenderers::register);
        }
        
    }
}
