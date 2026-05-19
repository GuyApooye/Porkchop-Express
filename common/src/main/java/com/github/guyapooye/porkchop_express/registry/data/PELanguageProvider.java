package com.github.guyapooye.porkchop_express.registry.data;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.registry.PECreativeModeTab;
import com.github.guyapooye.porkchop_express.registry.util.LanguageProvider;
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

public class PELanguageProvider extends LanguageProvider {
    
    private static final Map<Supplier<? extends Block>, String> BLOCK_LANGS = new Object2ObjectArrayMap<>();
    private static final Map<Supplier<? extends Item>, String> ITEM_LANGS = new Object2ObjectArrayMap<>();
    
    public PELanguageProvider(PackOutput output, String locale) {
        super(output, locale, PorkchopExpress.MOD_ID);
    }
    
    @Override
    protected void addTranslations() {
        BLOCK_LANGS.forEach(this::addBlock);
        
        ITEM_LANGS.forEach(this::addItem);
        
        this.add(PECreativeModeTab.ITEM_GROUP, "The Porkchop Express");
        this.add("item.porkchop_express.wretched_disc.desc", "Bad Piggies theme - Ilmari Hakkola");
    }
    
    public static void addBlockTranslation(Supplier<? extends Block> block, String name) {
        BLOCK_LANGS.put(block, name);
    }
    
    public static void addItemTranslation(Supplier<? extends Item> item, String name) {
        ITEM_LANGS.put(item, name);
    }
    
    
}
