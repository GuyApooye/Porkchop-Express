package com.github.guyapooye.porkchop_express.content.hold;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.sublevel.ClientSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import foundry.veil.api.network.VeilPacketManager;
import foundry.veil.api.network.handler.ClientPacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public record HoldBlockPacket(BlockPos blockPos) implements CustomPacketPayload {
    
    public static final Type<HoldBlockPacket> TYPE = new Type<>(PorkchopExpress.id("hold_block"));
    public static final StreamCodec<FriendlyByteBuf, HoldBlockPacket> CODEC = StreamCodec.ofMember(
            (packet, buf) -> buf.writeBlockPos(packet.blockPos),
            buf -> new HoldBlockPacket(buf.readBlockPos())
    );
    public static final VeilPacketManager.PacketHandler<ClientPacketContext, HoldBlockPacket> HANDLER = (packet, ctx) -> {
        Level level = ctx.level();
        if (level != null) {
            SubLevel subLevel = Sable.HELPER.getContaining(level, packet.blockPos);
            if (subLevel != null) {
                ClientHoldingManager.INSTANCE.heldSubLevel = new WeakReference<>((ClientSubLevel) subLevel);
                ClientHoldingManager.INSTANCE.heldBlockPos = packet.blockPos;
            }
        }
    };
    
    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
