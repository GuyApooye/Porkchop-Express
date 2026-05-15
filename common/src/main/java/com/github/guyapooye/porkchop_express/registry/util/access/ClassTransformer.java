package com.github.guyapooye.porkchop_express.registry.util.access;

public class ClassTransformer extends Transformer {

    public final Class<?> clazz;
    
    protected ClassTransformer(AccessType accessType, Class<?> clazz) {
        super(accessType);
        this.clazz = clazz;
    }
    
    @Override
    String name() {
        return "class";
    }
}
