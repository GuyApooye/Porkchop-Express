package com.github.guyapooye.porkchop_express.content.hold;

import com.github.guyapooye.porkchop_express.ext.hold.ServerLevelHoldExtension;
import dev.ryanhcode.sable.api.sublevel.ClientSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelObserver;
import dev.ryanhcode.sable.platform.SableEventPlatform;
import dev.ryanhcode.sable.sublevel.ClientSubLevel;
import net.minecraft.core.BlockPos;

import java.lang.ref.WeakReference;

public class ClientHoldingManager implements SubLevelObserver {
    
    public static final ClientHoldingManager INSTANCE = new ClientHoldingManager();
    
    public WeakReference<ClientSubLevel> heldSubLevel = new WeakReference<>(null);
    public BlockPos heldBlockPos = null;
    
    public static void bootstrap() {
        SableEventPlatform.INSTANCE.onSubLevelContainerReady((level, container) -> {
            if (container instanceof ClientSubLevelContainer) {
                container.addObserver(ClientHoldingManager.INSTANCE);
            }
        });
    }
    
}
