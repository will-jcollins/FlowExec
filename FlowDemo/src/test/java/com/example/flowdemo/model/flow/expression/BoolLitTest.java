package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoolLitTest {

    @Test
    void getVal() {
        // Test that the value is returned correctly
        BoolLit boolLit = new BoolLit();
        boolLit.setVal(false);
        assertFalse(boolLit.getVal());
    }
}