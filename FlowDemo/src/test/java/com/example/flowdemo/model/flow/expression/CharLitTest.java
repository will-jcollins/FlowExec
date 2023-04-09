package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharLitTest {

    @Test
    void getVal() {
        // Test that the value is returned correctly
        CharLit charLit = new CharLit();
        charLit.setVal('a');
        assertEquals('a', charLit.getVal());
    }
}