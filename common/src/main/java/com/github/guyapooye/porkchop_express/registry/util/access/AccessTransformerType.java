package com.github.guyapooye.porkchop_express.registry.util.access;

public enum AccessTransformerType {
    TRANSFORMER("Transformer"),
    WIDENER("Widener");
    
    private final String name;
    
    AccessTransformerType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
