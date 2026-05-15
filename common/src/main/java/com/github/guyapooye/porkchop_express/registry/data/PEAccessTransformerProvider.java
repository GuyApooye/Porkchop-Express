package com.github.guyapooye.porkchop_express.registry.data;

import com.github.guyapooye.porkchop_express.registry.util.access.AccessTransformerProvider;
import com.github.guyapooye.porkchop_express.registry.util.access.AccessType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class PEAccessTransformerProvider extends AccessTransformerProvider {
    
    public PEAccessTransformerProvider(PackOutput output, String modId, @Nullable String name) {
        super(output, modId, name);
    }
    
    @Override
    protected void addEntries() {
    }
}
