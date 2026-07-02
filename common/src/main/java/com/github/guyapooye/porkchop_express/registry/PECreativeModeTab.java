package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import foundry.veil.platform.registry.RegistrationProvider;
import foundry.veil.platform.registry.RegistryObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Supplier;

public final class PECreativeModeTab {
    
    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, PorkchopExpress.MOD_ID);
    
    private static final List<Supplier<? extends ItemLike>> TAB_ITEMS = new ObjectArrayList<>();
    
    public static final String ITEM_GROUP = PorkchopExpress.suffix("itemGroup.");
    
    public static final RegistryObject<CreativeModeTab> PE_TAB =
            CREATIVE_MODE_TABS.register(PorkchopExpress.MOD_ID, () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable(ITEM_GROUP))
                    .icon(() -> new ItemStack(PEBlocks.SWINE.get()))
                    .displayItems((parameters, output) -> {
                        for (Supplier<? extends ItemLike> tabItem : TAB_ITEMS) {
                            output.accept(tabItem.get());
                        }
                    })
                    .build());
    
    public static void addTabItem(Supplier<? extends ItemLike> itemLike) {
        TAB_ITEMS.add(itemLike);
    }
    
    public static void bootstrap() {
    }
    
    private PECreativeModeTab() {
    }
}
