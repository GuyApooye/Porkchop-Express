package com.github.guyapooye.porkchop_express.registry.data;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class PEItemTagsProvider extends IntrinsicHolderTagsProvider<Item> {
    
    private static final ListMultimap<TagKey<Item>, Supplier<? extends Item>> ITEM_TAGS = Multimaps.newListMultimap(new Object2ObjectArrayMap<>(), ObjectArrayList::new);
    
    public PEItemTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, Registries.ITEM, lookupProvider, (item) -> BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow());
    }
    
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (TagKey<Item> tag : ITEM_TAGS.keySet()) {
            IntrinsicTagAppender<Item> tagAppender = this.tag(tag);
            for (Supplier<? extends Item> block : ITEM_TAGS.get(tag)) {
                tagAppender.add(block.get());
            }
        }
    }
    
    public static void addItemTag(TagKey<Item> tag, Supplier<? extends Item> item) {
        ITEM_TAGS.put(tag, item);
    }
}
