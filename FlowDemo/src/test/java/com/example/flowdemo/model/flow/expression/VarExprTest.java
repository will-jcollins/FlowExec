package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExprTest {

    @Test
    void getIdentifier() {
        // Test that the correct identifier is returned
        VarExpr varExpr = new VarExpr();
        varExpr.setIdentifier("test");
        assertEquals("test", varExpr.getIdentifier());
    }
}