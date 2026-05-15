package com.github.guyapooye.porkchop_express.fabric.platform;

import com.github.guyapooye.porkchop_express.fabric.registry.util.access.AccessWidenerWriter;
import com.github.guyapooye.porkchop_express.foundation.client.item.CustomItemRenderer;
import com.github.guyapooye.porkchop_express.platform.PERegistrationPlatform;
import com.github.guyapooye.porkchop_express.registry.util.access.AccessTransformerWriter;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class PERegistrationPlatformImpl implements PERegistrationPlatform {
    
    @Override
    public <T extends Item> void registerCustomItemRenderer(
            Supplier<T> item, ResourceLocation itemId, Supplier<CustomItemRenderer> itemRenderer
    ) {
        BuiltinItemRendererRegistry.INSTANCE.register(item.get(), itemRenderer.get()::renderByItem);
    }
    
    @Override
    public AccessTransformerWriter createAccessTransformerWriter(String modId, String name) {
        return new AccessWidenerWriter(modId, name);
    }
    
}
