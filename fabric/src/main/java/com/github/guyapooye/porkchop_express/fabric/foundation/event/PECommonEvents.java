package com.github.guyapooye.porkchop_express.fabric.foundation.event;

import com.github.guyapooye.porkchop_express.registry.PEDataGenerators;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;

import java.util.concurrent.CompletableFuture;

public final class PECommonEvents implements DataGeneratorEntrypoint {
    
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        CompletableFuture<HolderLookup.Provider> lookupProvider = generator.getRegistries();
        FabricDataGenerator.Pack pack = generator.createPack();
        
        PEDataGenerators.addProviders(
                (run, dataProvider) -> pack.addProvider((FabricDataGenerator.Pack.Factory<DataProvider>) dataProvider::apply),
                lookupProvider,
                true,
                true
        );
    }
}
