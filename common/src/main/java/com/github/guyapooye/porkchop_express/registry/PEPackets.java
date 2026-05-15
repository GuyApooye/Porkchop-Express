package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.content.poultry.explosive.FuzeSyncPacket;
import foundry.veil.api.network.VeilPacketManager;

public class PEPackets {
    
    private static final VeilPacketManager PACKET_MANAGER = VeilPacketManager.create(PorkchopExpress.MOD_ID, "1");
    
    public static void bootstrap() {
        
        PACKET_MANAGER.registerClientbound(FuzeSyncPacket.TYPE, FuzeSyncPacket.CODEC, FuzeSyncPacket.HANDLER);
        
    }
    
}
