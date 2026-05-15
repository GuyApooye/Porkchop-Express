package com.github.guyapooye.porkchop_express.fabric.platform;

import com.github.guyapooye.porkchop_express.foundation.Environment;
import com.github.guyapooye.porkchop_express.platform.PELoaderPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class PELoaderPlatformImpl implements PELoaderPlatform {
    
    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
    
    @Override
    public Environment getEnvironment() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            return Environment.CLIENT;
        } else {
            return Environment.SERVER;
        }
    }
}
