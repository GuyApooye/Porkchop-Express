package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.content.hold.HoldBlockPacket;
import com.github.guyapooye.porkchop_express.content.hold.StopHoldBlockPacket;
import com.github.guyapooye.porkchop_express.content.poultry.explosive.FuzeSyncPacket;
import foundry.veil.api.network.VeilPacketManager;

public final class PEPackets {
    
    private static final VeilPacketManager PACKET_MANAGER = VeilPacketManager.create(PorkchopExpress.MOD_ID, "1");
    
    public static void bootstrap() {
        
        PACKET_MANAGER.registerClientbound(FuzeSyncPacket.TYPE, FuzeSyncPacket.CODEC, FuzeSyncPacket.HANDLER);
        
        PACKET_MANAGER.registerClientbound(HoldBlockPacket.TYPE, HoldBlockPacket.CODEC, HoldBlockPacket.HANDLER);
        PACKET_MANAGER.registerClientbound(StopHoldBlockPacket.TYPE, StopHoldBlockPacket.CODEC, StopHoldBlockPacket.HANDLER);
        
    }
    
}
