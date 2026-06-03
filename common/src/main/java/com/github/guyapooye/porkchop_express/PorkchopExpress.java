package com.github.guyapooye.porkchop_express;

import com.github.guyapooye.porkchop_express.content.hold.ClientHoldingManager;
import com.github.guyapooye.porkchop_express.content.hold.ServerHoldingManager;
import com.github.guyapooye.porkchop_express.foundation.client.BlockEntityRenderers;
import com.github.guyapooye.porkchop_express.foundation.client.BlockRenderTypes;
import com.github.guyapooye.porkchop_express.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PorkchopExpress {
    
    public static final String MOD_ID = "porkchop_express";
    public static final String MOD_NAME = "Porkchop Express";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    
    public void init() {
        PECreativeModeTab.bootstrap();
        PEBlocks.bootstrap();
        PEItems.bootstrap();
        PEBlockEntities.bootstrap();
        PESounds.bootstrap();
        PEPackets.bootstrap();
        PEDataComponents.bootstrap();
        
        ServerHoldingManager.bootstrap();
    }
    
    public static ResourceLocation id(String s) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, s);
    }
    
    public static String prefix(String s) {
        return MOD_ID + s;
    }
    
    public static String suffix(String s) {
        return s + MOD_ID;
    }
    
    public static class Client {
        
        public void clientInit() {
            PEPartialModels.bootstrap();
            
            ClientHoldingManager.bootstrap();
        }
        
    }
    
}
