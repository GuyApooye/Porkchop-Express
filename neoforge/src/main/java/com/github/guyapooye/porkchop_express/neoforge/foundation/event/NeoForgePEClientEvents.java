package com.github.guyapooye.porkchop_express.neoforge.foundation.event;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.foundation.client.BlockEntityRenderers;
import com.github.guyapooye.porkchop_express.foundation.client.BlockRenderTypes;
import com.github.guyapooye.porkchop_express.neoforge.registry.PEClientExtensions;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = PorkchopExpress.MOD_ID, value = Dist.CLIENT)
public class NeoForgePEClientEvents {
    
    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        PEClientExtensions.register(event);
    }
    
    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        PEClientExtensions.replaceWithCustomRenderers(event.getModels());
    }
    
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockRenderTypes.registerRenderTypes(ItemBlockRenderTypes::setRenderLayer);
    }
    
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        BlockEntityRenderers.register(event::registerBlockEntityRenderer);
    }
    
}
