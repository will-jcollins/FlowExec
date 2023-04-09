package com.example.flowdemo.model.flow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignatureTest {

    @Test
    void getIdentifier() {
        // Test that the identifier is returned correctly
        Signature signature = new Signature("test", DataType.IntType);
        assertEquals("test", signature.getIdentifier());
    }

    @Test
    void setIdentifier() {
        // Test that the identifier is set correctly
        Signature signature = new Signature("test", DataType.IntType);
        signature.setIdentifier("test2");
        assertEquals("test2", signature.getIdentifier());
    }

    @Test
    void getType() {
        // Test that the type is returned correctly
        Signature signature = new Signature("test", DataType.IntType);
        assertEquals(DataType.IntType, signature.getType());
    }

    @Test
    void setType() {
        // Test that the type is set correctly
        Signature signature = new Signature("test", DataType.IntType);
        signature.setType(DataType.BoolType);
    }
}