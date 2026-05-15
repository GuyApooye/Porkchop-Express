package com.github.guyapooye.porkchop_express.platform;

import com.github.guyapooye.porkchop_express.foundation.Environment;
import dev.ryanhcode.sable.platform.SablePlatformUtil;

public interface PELoaderPlatform {
    
    PELoaderPlatform INSTANCE = SablePlatformUtil.load(PELoaderPlatform.class);
    
    boolean isDevelopmentEnvironment();
    
    Environment getEnvironment();
}
