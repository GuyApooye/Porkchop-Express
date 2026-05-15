package com.github.guyapooye.porkchop_express.foundation;

public enum Environment {
    
    CLIENT,
    
    SERVER;
    
    public boolean isClient() {
        return this == CLIENT;
    }
    
    public boolean isServer() {
        return this == SERVER;
    }
}
