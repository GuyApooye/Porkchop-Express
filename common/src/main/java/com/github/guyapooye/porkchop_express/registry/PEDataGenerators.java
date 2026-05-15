package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.registry.data.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class PEDataGenerators {
    
    public static void addProviders(
            BiConsumer<Boolean, Function<PackOutput, DataProvider>> addProvider,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            boolean runClient,
            boolean runServer
    ) {
        addProvider.accept(runServer, output ->
                new LootTableProvider(output, Set.of(),
                                      List.of(new LootTableProvider.SubProviderEntry(
                                              PELootTableProvider::new, LootContextParamSets.BLOCK)),
                                      lookupProvider));
        
        addProvider.accept(runClient, output ->
                new PELanguageProvider(output, "en_us"));
        
        addProvider.accept(runServer, output ->
                new PEBlockTagsProvider(output, lookupProvider));
        
        addProvider.accept(runServer, output ->
                new PEItemTagsProvider(output, lookupProvider));
    }
    
}
