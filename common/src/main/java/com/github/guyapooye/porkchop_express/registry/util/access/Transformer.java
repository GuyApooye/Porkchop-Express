package com.github.guyapooye.porkchop_express.registry.util.access;

public abstract class Transformer {
    
    public final AccessType accessType;
    
    protected Transformer(AccessType accessType) {
        this.accessType = accessType;
    }
    
    abstract String name();
}
