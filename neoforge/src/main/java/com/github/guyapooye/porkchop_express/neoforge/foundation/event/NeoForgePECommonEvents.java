package com.github.guyapooye.porkchop_express.neoforge.foundation.event;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.registry.PEDataGenerators;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = PorkchopExpress.MOD_ID)
public final class NeoForgePECommonEvents {
    
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        boolean runClient = event.includeClient();
        boolean runServer = event.includeServer();
        PackOutput output = generator.getPackOutput();
        PEDataGenerators.addProviders(
                (run, dataProvider) -> generator.addProvider(run, dataProvider.apply(output)),
                lookupProvider,
                runClient,
                runServer
        );
    }
    
}
