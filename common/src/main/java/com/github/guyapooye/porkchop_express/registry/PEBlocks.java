package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.content.poultry.explosive.ExplosivePoultryBlock;
import com.github.guyapooye.porkchop_express.content.poultry.red.RedPoultryBlock;
import com.github.guyapooye.porkchop_express.content.poultry.splitting.SplittingPoultryBlock;
import com.github.guyapooye.porkchop_express.content.swine.WretchedSwineBlock;
import com.github.guyapooye.porkchop_express.content.swine.WretchedSwineBlockItem;
import com.github.guyapooye.porkchop_express.registry.util.BlockBuilder;
import foundry.veil.platform.registry.RegistryObject;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.Function;

public final class PEBlocks {
    
    public static final RegistryObject<WretchedSwineBlock> SWINE = block("wretched_swine", WretchedSwineBlock::new)
            .properties(p -> p
                    .noOcclusion()
                    .strength(1.0f, 1.0f)
                    .sound(PESoundTypes.SWINE.get()))
            .renderType(() -> RenderType::cutout)
            .lootTable((block) ->
                               WretchedSwineBlock.addLoot(LootTable.lootTable(), block.get()))
            .item("hurt_swine", (block, properties) ->
                    new WretchedSwineBlockItem(block, properties, WretchedSwineBlock.Mood.HURT))
            .lang("Hurt Swine").endItem()
            .item("angry_swine", (block, properties) ->
                    new WretchedSwineBlockItem(block, properties, WretchedSwineBlock.Mood.ANGRY))
            .lang("Angry Swine").endItem()
            .item("burnt_swine", (block, properties) ->
                    new WretchedSwineBlockItem(block, properties, WretchedSwineBlock.Mood.BURNT))
            .lang("Burnt Swine").endItem()
            .item("wretched_swine", (block, properties) ->
                    new WretchedSwineBlockItem(block, properties, WretchedSwineBlock.Mood.HAPPY))
            .lang("Wretched Swine").endItem()
            .build();
    
    public static final RegistryObject<RedPoultryBlock> RED_POULTRY = block("red_poultry", RedPoultryBlock::new)
            .properties(p -> p.noOcclusion()
                    .strength(1.0f, 30.0f))
            .renderType(() -> RenderType::cutout)
            .dropSelf()
            .simpleItem()
            .lang("Furious Poultry")
            .build();
    
    public static final RegistryObject<ExplosivePoultryBlock> EXPLOSIVE_POULTRY = block("explosive_poultry", ExplosivePoultryBlock::new)
            .properties(p -> p.noOcclusion()
                    .strength(1.0f, 30.0f))
            .renderType(() -> RenderType::cutout)
            .dropSelf()
            .simpleItem()
            .lang("Explosive Poultry")
            .build();
    
    public static final RegistryObject<SplittingPoultryBlock> SPLITTING_POULTRY = block("splitting_poultry", SplittingPoultryBlock::new)
            .properties(p -> p.noOcclusion()
                    .strength(1.0f, 30.0f))
            .renderType(() -> RenderType::cutout)
            .dropSelf()
            .simpleItem()
            .lang("Splitting Poultry")
            .build();
    
    private static <B extends Block> BlockBuilder<B> block(String name, Function<BlockBehaviour.Properties, B> factory) {
        return new BlockBuilder<>(name, factory);
    }
    
    public static void bootstrap() {
    
    }
    
    private PEBlocks() {
    }
}
