package com.github.guyapooye.porkchop_express.registry.util.access;

import com.github.guyapooye.porkchop_express.platform.PERegistrationPlatform;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class AccessTransformerProvider implements DataProvider {
    
    protected final PackOutput output;
    private final AccessTransformerWriter writer;
    
    private final List<Transformer> transformers = new ObjectArrayList<>();
    
    public AccessTransformerProvider(PackOutput output, String modId, @Nullable String name) {
        this.output = output;
        this.writer = PERegistrationPlatform.INSTANCE.createAccessTransformerWriter(modId, name);
    }
    
    protected abstract void addEntries();
    
    protected void addClass(Class<?> clazz, AccessType accessType) {
        this.transformers.add(new ClassTransformer(accessType, clazz));
    }
    
    protected void addClass(String clazz, AccessType accessType){
        try {
            this.addClass(Class.forName(clazz), accessType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    @NotNull
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        
        this.addEntries();
        Path at = this.writer.getFile(this.output.getOutputFolder());
        
        return CompletableFuture.runAsync(() -> {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            
            // noinspection deprecation, UnstableApiUsage
            HashingOutputStream outputStream = new HashingOutputStream(Hashing.sha1(), arrayOutputStream);
            try {
                for (Transformer transformer : this.transformers) {
                    
                    switch (transformer) {
                        case ClassTransformer classTransformer -> {
                            this.writer.writeClassTransformer(outputStream, classTransformer);
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + transformer);
                    }
                    
                }
                
                cachedOutput.writeIfNeeded(at, arrayOutputStream.toByteArray(), outputStream.hash());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    @Override
    @NotNull
    public String getName() {
        return "Access " + this.writer.getType().getName();
    }
    
}
