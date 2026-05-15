package com.github.guyapooye.porkchop_express.fabric.registry.util.access;

import com.github.guyapooye.porkchop_express.registry.util.access.AccessTransformerType;
import com.github.guyapooye.porkchop_express.registry.util.access.AccessTransformerWriter;
import com.github.guyapooye.porkchop_express.registry.util.access.AccessType;
import com.github.guyapooye.porkchop_express.registry.util.access.ClassTransformer;
import com.google.common.hash.HashingOutputStream;

import java.io.IOException;
import java.nio.file.Path;

import static com.github.guyapooye.porkchop_express.registry.util.access.AccessType.ACCESSIBLE;
import static com.github.guyapooye.porkchop_express.registry.util.access.AccessType.MUTABLE;

public class AccessWidenerWriter extends AccessTransformerWriter {
    
    private static final String SPACING = "  ";
    
    public AccessWidenerWriter(String modId, String name) {
        super(modId, name);
    }
    
    @Override
    public AccessTransformerType getType() {
        return AccessTransformerType.WIDENER;
    }
    
    @Override
    public Path getFile(Path outputFile) {
        String path = this.modId;
        if (this.name != null) {
            path = path + "_" + this.name;
        }
        return outputFile.resolve(path + ".accesswidener");
    }
    
    @Override
    public void writeClassTransformer(HashingOutputStream outputStream, ClassTransformer classTransformer) throws IOException {
        switch (classTransformer.accessType) {
            case ACCESSIBLE, MUTABLE -> {
                this.writeClassTransformer(outputStream, classTransformer.clazz, classTransformer.accessType);
            }
            case ACCESSIBLE_MUTABLE -> {
                this.writeClassTransformer(outputStream, classTransformer.clazz, ACCESSIBLE);
                this.writeClassTransformer(outputStream, classTransformer.clazz, MUTABLE);
            }
        }
    }
    
    private void writeClassTransformer(HashingOutputStream outputStream, Class<?> clazz, AccessType type) throws IOException {
        StringBuilder builder = new StringBuilder();
        switch (type) {
            case ACCESSIBLE -> {
                builder.append("accessible");
            }
            case MUTABLE -> {
                builder.append("extendable");
            }
        }
        builder.append(SPACING);
        builder.append("class");
        builder.append(SPACING);
        builder.append(clazz.descriptorString());
        outputStream.write(builder.toString().getBytes());
    }
    
}
