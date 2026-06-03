package com.github.guyapooye.porkchop_express.ext.hold;

import com.github.guyapooye.porkchop_express.content.hold.ServerHoldingManager;

public interface ServerLevelHoldExtension {
    
    ServerHoldingManager porkchop_express$getHoldingManager();
    
}
