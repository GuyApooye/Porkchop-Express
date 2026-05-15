package com.github.guyapooye.porkchop_express.registry.util.access;

import com.google.common.hash.HashingOutputStream;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AccessTransformerWriter {
    
    protected final String modId;
    @Nullable
    protected final String name;
    
    public AccessTransformerWriter(String modId, @Nullable String name) {
        this.modId = modId;
        this.name = name;
    }
    
    public abstract AccessTransformerType getType();
    
    public abstract Path getFile(Path outputFile);
    
    public abstract void writeClassTransformer(HashingOutputStream outputStream, ClassTransformer classTransformer) throws IOException;
//    public abstract void writeMethodTransformer(HashingOutputStream outputStream, MethodTransformer methodTransformer);
//    public abstract void writeFieldTransformer(HashingOutputStream outputStream, FieldTransformer fieldTransformer);
}
