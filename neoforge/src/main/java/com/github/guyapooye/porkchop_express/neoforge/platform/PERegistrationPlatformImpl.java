package com.github.guyapooye.porkchop_express.neoforge.platform;

import com.github.guyapooye.porkchop_express.foundation.client.item.CustomItemRenderer;
import com.github.guyapooye.porkchop_express.neoforge.registry.PEClientExtensions;
import com.github.guyapooye.porkchop_express.neoforge.registry.util.ItemExtensions;
import com.github.guyapooye.porkchop_express.neoforge.registry.util.access.AccessWidenerWriter;
import com.github.guyapooye.porkchop_express.platform.PERegistrationPlatform;
import com.github.guyapooye.porkchop_express.registry.util.access.AccessTransformerWriter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class PERegistrationPlatformImpl implements PERegistrationPlatform {
    
    @Override
    public <T extends Item> void registerCustomItemRenderer(Supplier<T> item, ResourceLocation itemId, Supplier<CustomItemRenderer> itemRenderer) {
        PEClientExtensions.addItemExtensions(item, ItemExtensions.simpleRenderer(itemRenderer));
        PEClientExtensions.setWithCustomRenderer(itemId);
    }
    
    @Override
    public AccessTransformerWriter createAccessTransformerWriter(String modId, String name) {
        return new AccessWidenerWriter(modId, name);
    }
    
}
