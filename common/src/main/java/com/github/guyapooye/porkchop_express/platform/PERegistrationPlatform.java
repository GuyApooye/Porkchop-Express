package com.github.guyapooye.porkchop_express.platform;

import com.github.guyapooye.porkchop_express.foundation.client.item.CustomItemRenderer;
import com.github.guyapooye.porkchop_express.registry.util.access.AccessTransformerWriter;
import dev.ryanhcode.sable.platform.SablePlatformUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface PERegistrationPlatform {
    
    PERegistrationPlatform INSTANCE = SablePlatformUtil.load(PERegistrationPlatform.class);
    
    <T extends Item> void registerCustomItemRenderer(Supplier<T> item, ResourceLocation itemId, Supplier<CustomItemRenderer> itemRenderer);

    AccessTransformerWriter createAccessTransformerWriter(String modId, String name);
    
    default AccessTransformerWriter createAccessTransformerWriter(String modId) {
        return this.createAccessTransformerWriter(modId, null);
    }
    
}
