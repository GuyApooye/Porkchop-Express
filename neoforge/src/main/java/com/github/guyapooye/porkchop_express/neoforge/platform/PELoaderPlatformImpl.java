package com.github.guyapooye.porkchop_express.neoforge.platform;

import com.github.guyapooye.porkchop_express.foundation.Environment;
import com.github.guyapooye.porkchop_express.platform.PELoaderPlatform;
import net.neoforged.fml.loading.FMLLoader;

public class PELoaderPlatformImpl implements PELoaderPlatform {
    
    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
    
    @Override
    public Environment getEnvironment() {
        if (FMLLoader.getDist().isClient()) {
            return Environment.CLIENT;
        } else {
            return Environment.SERVER;
        }
    }
}
