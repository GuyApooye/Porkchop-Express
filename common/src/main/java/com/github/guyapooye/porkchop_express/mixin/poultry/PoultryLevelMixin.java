package com.github.guyapooye.porkchop_express.mixin.poultry;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryManager;
import com.github.guyapooye.porkchop_express.ext.poultry.LevelPoultryExtension;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Level.class)
public class PoultryLevelMixin implements LevelPoultryExtension {
    
    @Unique
    private final PoultryManager porkchop_express$poultryManager = new PoultryManager();
    
    @Override
    public PoultryManager porkchop_express$getPoultryManager() {
        return this.porkchop_express$poultryManager;
    }
    
}
