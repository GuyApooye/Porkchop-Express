package com.github.guyapooye.porkchop_express.neoforge.registry.util.access;

import com.github.guyapooye.porkchop_express.registry.util.access.AccessTransformerType;
import com.github.guyapooye.porkchop_express.registry.util.access.AccessTransformerWriter;
import com.github.guyapooye.porkchop_express.registry.util.access.AccessType;
import com.github.guyapooye.porkchop_express.registry.util.access.ClassTransformer;
import com.google.common.hash.HashingOutputStream;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Path;

import static com.github.guyapooye.porkchop_express.registry.util.access.AccessType.ACCESSIBLE;
import static com.github.guyapooye.porkchop_express.registry.util.access.AccessType.MUTABLE;

public class AccessWidenerWriter extends AccessTransformerWriter {
    
    private static final String SPACING = " ";
    private static final String MUTABLE = "-f";
    private static final String NEW_LINE = "\n";
    
    public AccessWidenerWriter(String modId, String name) {
        super(modId, name);
    }
    
    @Override
    public AccessTransformerType getType() {
        return AccessTransformerType.WIDENER;
    }
    
    @Override
    public Path getFile(Path outputFile) {
        String path = "";
        if (this.name != null) {
            path = path + "_" + this.name;
        }
        return outputFile.resolve("META-INF").resolve("accesstransformer" + path + ".cfg");
    }
    
    @Override
    public void writeClassTransformer(HashingOutputStream outputStream, ClassTransformer classTransformer) throws IOException {
        StringBuilder builder = new StringBuilder();
        switch (classTransformer.accessType) {
            case ACCESSIBLE -> {
                builder.append("public");
            }
            case MUTABLE -> {
                builder.append(getClassAccessString(classTransformer.clazz));
                builder.append(MUTABLE);
            }
            case ACCESSIBLE_MUTABLE -> {
                builder.append("public");
                builder.append(MUTABLE);
            }
        }
        builder.append(SPACING);
        builder.append(classTransformer.clazz.getName());
        outputStream.write(builder.toString().getBytes());
    }
    
    private static String getClassAccessString(Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            return "public";
        } else if (Modifier.isPrivate(modifiers)) {
            return "private";
        } else if (Modifier.isProtected(modifiers)) {
            return "protected";
        } else {
            return "default";
        }
        
    }
    
}
