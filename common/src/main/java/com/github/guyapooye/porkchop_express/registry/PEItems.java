package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.content.poultry.detonator.RemoteDetonatorItem;
import com.github.guyapooye.porkchop_express.content.poultry.detonator.RemoteDetonatorRenderer;
import com.github.guyapooye.porkchop_express.registry.util.ItemBuilder;
import foundry.veil.platform.registry.RegistrationProvider;
import foundry.veil.platform.registry.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Function;

public final class PEItems {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, PorkchopExpress.MOD_ID);
    public static final Registry<Item> REGISTRY = ITEMS.asVanillaRegistry();
    
    public static void bootstrap() {
    }
    
    public static final RegistryObject<Item> WRETCHED_DISC = item("wretched_disc", Item::new)
            .properties(p -> p.jukeboxPlayable(PESounds.WRETCHED_DISC_KEY).rarity(
                    Rarity.EPIC).stacksTo(1))
            .lang("Wretched Music Disc")
            .build();

    public static final RegistryObject<RemoteDetonatorItem> REMOTE_DETONATOR = item("remote_detonator", RemoteDetonatorItem::new)
            .properties(p -> p.stacksTo(1))
            .renderer(() -> RemoteDetonatorRenderer::get)
            .lang("Remote Detonator")
            .build();
    
    private static <I extends Item> ItemBuilder<I> item(String name, Function<Item.Properties, I> factory) {
        return new ItemBuilder<>(name, factory);
    }
    
    private PEItems() {
    }
    
}
