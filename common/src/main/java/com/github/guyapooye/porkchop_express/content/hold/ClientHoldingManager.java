package com.github.guyapooye.porkchop_express.content.hold;

import com.github.guyapooye.porkchop_express.ext.hold.ServerLevelHoldExtension;
import dev.ryanhcode.sable.api.sublevel.ClientSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelObserver;
import dev.ryanhcode.sable.platform.SableEventPlatform;
import dev.ryanhcode.sable.sublevel.ClientSubLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

public class ClientHoldingManager implements SubLevelObserver {
    
    public static final ClientHoldingManager INSTANCE = new ClientHoldingManager();
    
    public WeakReference<ClientSubLevel> heldSubLevel = new WeakReference<>(null);
    public BlockPos heldBlockPos = null;
    public boolean isHolding = false;
    
    public static void bootstrap() {
        SableEventPlatform.INSTANCE.onSubLevelContainerReady((level, container) -> {
            if (container instanceof ClientSubLevelContainer) {
                container.addObserver(ClientHoldingManager.INSTANCE);
            }
        });
    }
    
    public static boolean isHolding(AbstractClientPlayer player) {
        return INSTANCE.isHolding;
    }
    
    public static boolean isHolding(AbstractClientPlayer player, ClientSubLevel subLevel) {
        return INSTANCE.heldSubLevel.get() == subLevel;
    }
    
    public static boolean isHolding() {
        return isHolding(Minecraft.getInstance().player);
    }
    
    public static boolean isHolding(ClientSubLevel subLevel) {
        return isHolding(Minecraft.getInstance().player, subLevel);
    }
    
    public static AbstractClientPlayer getHolding(@Nullable ClientSubLevel subLevel) {
        if (subLevel == null) {
            return null;
        }
        return Minecraft.getInstance().player;
    }

//    public record HoldingPoint(WeakReference<ClientSubLevel>)
}
