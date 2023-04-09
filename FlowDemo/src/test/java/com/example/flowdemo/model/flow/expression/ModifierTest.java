package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModifierTest {

    @Test
    void fromString() {
        // Test that the correct modifier is returned
        assertEquals(Modifier.Not, Modifier.fromString(Modifier.Not.toString()));
    }
}