package com.github.guyapooye.porkchop_express.mixin.poultry;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryBlockEntity;
import com.github.guyapooye.porkchop_express.ext.poultry.LevelPoultryExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelChunk.class)
public class PoultryLevelChunkMixin{
    
    @Shadow
    @Final
    Level level;
    
    @Inject(method = "removeBlockEntity", at = @At("TAIL"))
    private void porkchop_express$removeBlockEntity(BlockPos pos, CallbackInfo ci) {
        ((LevelPoultryExtension) this.level).porkchop_express$getPoultryManager().birds.remove(pos);
    }
    
    @Inject(method = "addAndRegisterBlockEntity", at = @At("TAIL"))
    private void loadPoultry(BlockEntity blockEntity, CallbackInfo ci) {
        if (blockEntity instanceof PoultryBlockEntity poultry) {
            poultry.onCreate();
        }
    }
    
}
