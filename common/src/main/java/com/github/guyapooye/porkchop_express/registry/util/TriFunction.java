package com.github.guyapooye.porkchop_express.registry.util;

@FunctionalInterface
public interface TriFunction<A, B, C, T> {
    
    T apply(A a, B b, C c);
    
}
