package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {

    @Test
    void fromString() {
        // Test that the correct operator is returned
        assertEquals(Operator.Add, Operator.fromString(Operator.Add.toString()));
    }
}