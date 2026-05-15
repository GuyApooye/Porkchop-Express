package com.github.guyapooye.porkchop_express.neoforge;


import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.foundation.client.BlockRenderTypes;
import com.github.guyapooye.porkchop_express.foundation.client.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(PorkchopExpress.MOD_ID)
public final class NeoForgePorkchopExpress extends PorkchopExpress {
    
    public NeoForgePorkchopExpress(IEventBus eventBus) {
        this.init();
    }
    
    @Mod(value = PorkchopExpress.MOD_ID, dist = Dist.CLIENT)
    public static class Client extends PorkchopExpress.Client {
        
        public Client(IEventBus eventBus) {
            this.clientInit();
        }
    
    }
}
