package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntLitTest {

    @Test
    void getVal() {
        // Test that the value is returned correctly
        IntLit intLit = new IntLit();
        intLit.setVal(10);
        assertEquals(10, intLit.getVal());
    }
}