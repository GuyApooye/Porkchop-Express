package com.github.guyapooye.porkchop_express.registry.util;

import com.github.guyapooye.porkchop_express.registry.PECreativeModeTab;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class LanguageProvider implements DataProvider {
    
    private final Map<String, String> translations = new TreeMap<>();
    
    private final PackOutput output;
    
    private final String locale;
    private final String modId;
    
    public LanguageProvider(PackOutput output, String locale, String modId) {
        this.output = output;
        this.locale = locale;
        this.modId = modId;
    }

    protected abstract void addTranslations();
    
    protected void add(String descriptionId, String translation) {
        this.translations.put(descriptionId, translation);
    }
    
    protected void addBlock(Block block, String translation) {
        this.add(block.getDescriptionId(), translation);
    }
    
    protected void addItem(Item item, String translation) {
        this.add(item.getDescriptionId(), translation);
    }
    
    protected void addBlock(Supplier<? extends Block> block, String translation) {
        this.addBlock(block.get(), translation);
    }
    
    protected void addItem(Supplier<? extends Item> item, String translation) {
        this.addItem(item.get(), translation);
    }
    
    @Override
    @NotNull
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        this.addTranslations();
        
        Path path = this.output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang")
                .json(ResourceLocation.fromNamespaceAndPath(this.modId, this.locale));
        
        JsonObject json = this.generateJson();
        
        return DataProvider.saveStable(cachedOutput, json, path);
    }
    
    @Override
    @NotNull
    public String getName() {
        return this.modId + " Language Provider";
    }
    
    private JsonObject generateJson() {
        JsonObject json = new JsonObject();
        this.translations.forEach(json::addProperty);
        return json;
    }
    
}
