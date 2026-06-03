package com.github.guyapooye.porkchop_express.content.hold;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import foundry.veil.api.network.VeilPacketManager;
import foundry.veil.api.network.handler.ClientPacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public class StopHoldBlockPacket implements CustomPacketPayload {
    
    public static final Type<StopHoldBlockPacket> TYPE = new Type<>(PorkchopExpress.id("stop_hold"));
    public static final StreamCodec<FriendlyByteBuf, StopHoldBlockPacket> CODEC = StreamCodec.ofMember(
            (packet, buf) -> {},
            buf -> new StopHoldBlockPacket()
    );
    public static final VeilPacketManager.PacketHandler<ClientPacketContext, StopHoldBlockPacket> HANDLER = (packet, ctx) -> {
        ClientHoldingManager.INSTANCE.heldSubLevel.clear();
        ClientHoldingManager.INSTANCE.heldBlockPos = null;
    };
    
    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
