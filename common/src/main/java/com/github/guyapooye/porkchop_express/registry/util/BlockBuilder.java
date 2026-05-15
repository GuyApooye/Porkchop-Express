package com.github.guyapooye.porkchop_express.registry.util;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.foundation.client.BlockRenderTypes;
import com.github.guyapooye.porkchop_express.platform.PELoaderPlatform;
import com.github.guyapooye.porkchop_express.registry.PECreativeModeTab;
import com.github.guyapooye.porkchop_express.registry.data.PEBlockTagsProvider;
import com.github.guyapooye.porkchop_express.registry.data.PEItemTagsProvider;
import com.github.guyapooye.porkchop_express.registry.data.PELanguageProvider;
import com.github.guyapooye.porkchop_express.registry.data.PELootTableProvider;
import foundry.veil.platform.registry.RegistrationProvider;
import foundry.veil.platform.registry.RegistryObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockBuilder<B extends Block> {
    
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, PorkchopExpress.MOD_ID);
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, PorkchopExpress.MOD_ID);
    
    private final String name;
    private final Function<BlockBehaviour.Properties, B> factory;
    
    private final List<ItemBuilder<?>> items = new ObjectArrayList<>();
    private final BlockBehaviour.Properties blockProperties = BlockBehaviour.Properties.of();
    
    private Function<BlockBehaviour.Properties, BlockBehaviour.Properties> properties = Function.identity();
    private Iterable<TagKey<Block>> tags = Set.of();
    
    @Nullable
    private Function<Supplier<B>, LootTable.Builder> lootTable = null;
    @Nullable
    private String lang = null;
    @Nullable
    private Supplier<Supplier<RenderType>> renderType = null;
    
    public BlockBuilder(String name, Function<BlockBehaviour.Properties, B> factory) {
        this.name = name;
        this.factory = factory;
    }
    
    public BlockBuilder<B> properties(Function<BlockBehaviour.Properties, BlockBehaviour.Properties> properties) {
        this.properties = this.properties.andThen(properties);
        return this;
    }
    
    public BlockBuilder<B> renderType(Supplier<Supplier<RenderType>> renderType) {
        assert this.renderType == null : "Attempted to set render type twice!";
        this.renderType = renderType;
        return this;
    }
    
    public BlockBuilder<B> dropSelf() {
        return this.lootTable(PELootTableProvider::dropSelf);
    }
    
    public BlockBuilder<B> lootTable(Function<Supplier<B>, LootTable.Builder> lootTable) {
        assert this.lootTable == null : "Attempted to set loot table twice!";
        this.lootTable = lootTable;
        return this;
    }
    
    public BlockBuilder<B> lootTable(Supplier<LootTable.Builder> lootTable) {
        return this.lootTable(block -> lootTable.get());
    }
    
    public BlockBuilder<B> simpleItem() {
        this.items.add(new ItemBuilder<>(BlockItem::new));
        return this;
    }
    
    public <I extends Item> ItemBuilder<I> item(String name, BiFunction<B, Item.Properties, I> factory) {
        ItemBuilder<I> item = new ItemBuilder<>(name, factory);
        this.items.add(item);
        return item;
    }
    
    public ItemBuilder<BlockItem> item(String name) {
        return this.item(name, BlockItem::new);
    }
    
    public BlockBuilder<B> lang(String lang) {
        assert this.lang == null : "Attempted to set lang twice!";
        this.lang = lang;
        return this;
    }
    
    public BlockBuilder<B> tags(Iterable<TagKey<Block>> tags) {
        assert this.tags == null : "Attempted to set tags twice!";
        this.tags = tags;
        return this;
    }
    
    public RegistryObject<B> build() {
        RegistryObject<B> block = BLOCKS.register(
                this.name,
                () -> this.factory.apply(this.properties.apply(this.blockProperties))
        );
        if (this.lootTable != null) {
            PELootTableProvider.addLootTable(block.getId(), () -> this.lootTable.apply(block));
        }
        if (this.lang != null) {
            PELanguageProvider.addBlockTranslation(block, this.lang);
        }
        for (TagKey<Block> tag : this.tags) {
            PEBlockTagsProvider.addBlockTag(tag, block);
        }
        if (this.renderType != null && PELoaderPlatform.INSTANCE.getEnvironment().isClient()) {
            BlockRenderTypes.addBlockRenderType(block, this.renderType.get());
        }
        this.items.forEach(item -> item.build(block));
        return block;
    }
    
    public class ItemBuilder<I extends Item> {
        
        private final String name;
        private final BiFunction<B, Item.Properties, I> factory;
        
        private final Item.Properties itemProperties = new Item.Properties();
        
        private Function<Item.Properties, Item.Properties> properties = Function.identity();
        private Iterable<TagKey<Item>> tags = Set.of();
        private boolean addToCreativeTab = true;
        
        @Nullable
        private String lang = null;
        
        public ItemBuilder(String name, BiFunction<B, Item.Properties, I> factory) {
            this.name = name;
            this.factory = factory;
        }
        
        public ItemBuilder(BiFunction<B, Item.Properties, I> factory) {
            this(BlockBuilder.this.name, factory);
        }
        
        public ItemBuilder<I> properties(Function<Item.Properties, Item.Properties> properties) {
            this.properties = this.properties.andThen(properties);
            return this;
        }
        
        public ItemBuilder<I> lang(String lang) {
            assert this.lang == null : "Attempted to set lang twice!";
            this.lang = lang;
            return this;
        }
        
        public ItemBuilder<I> tags(Iterable<TagKey<Item>> tags) {
            assert this.tags == null : "Attempted to set tags twice!";
            this.tags = tags;
            return this;
        }
        
        public ItemBuilder<I> addToCreativeTab(boolean shouldAdd) {
            this.addToCreativeTab = shouldAdd;
            return this;
        }
        
        public BlockBuilder<B> endItem() {
            return BlockBuilder.this;
        }
        
        private void build(Supplier<B> block) {
            RegistryObject<I> item = ITEMS.register(this.name, () -> this.factory.apply(block.get(), this.properties.apply(this.itemProperties)));
            if (BlockBuilder.this.items.size() > 1) {
                if (this.lang != null) {
                    PELanguageProvider.addItemTranslation(item, this.lang);
                }
            }
            if (this.addToCreativeTab) {
                PECreativeModeTab.addTabItem(item);
            }
            for (TagKey<Item> tag : this.tags) {
                PEItemTagsProvider.addItemTag(tag, item);
            }
            
        }
    }
    
}
