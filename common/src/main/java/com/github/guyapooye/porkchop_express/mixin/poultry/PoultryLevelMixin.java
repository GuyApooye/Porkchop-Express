package com.github.guyapooye.porkchop_express.mixin.poultry;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryBlockEntity;
import com.github.guyapooye.porkchop_express.content.poultry.PoultryManager;
import com.github.guyapooye.porkchop_express.ext.poultry.LevelPoultryExtension;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class PoultryLevelMixin implements LevelPoultryExtension {
    
    @Unique
    private final PoultryManager porkchop_express$poultryManager = new PoultryManager();
    
    @Override
    public PoultryManager porkchop_express$getPoultryManager() {
        return this.porkchop_express$poultryManager;
    }
    
}
