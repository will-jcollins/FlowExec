package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CallExprTest {

    @Test
    void getIdentifier() {
        // Test that the identifier is returned correctly
        CallExpr callExpr = new CallExpr();
        callExpr.setIdentifier("test");
        assertEquals("test", callExpr.getIdentifier());
    }

    @Test
    void setNumberOfParameters() {
        // Test that the number of parameters is set correctly
        CallExpr callExpr = new CallExpr();
        callExpr.setNumberOfParameters(10);
        assertEquals(10, callExpr.getExprs().size());

        callExpr.setNumberOfParameters(4);
        assertEquals(4, callExpr.getExprs().size());
    }

    @Test
    void getExprs() {
        // Test that the expression is returned correctly
        CallExpr callExpr = new CallExpr();
        callExpr.setNumberOfParameters(2);
        IntLit intLit1 = new IntLit();
        IntLit intLit2 = new IntLit();
        callExpr.setExpr(intLit1, 0);
        callExpr.setExpr(intLit2, 1);
        assertEquals(intLit1, callExpr.getExprs().get(0));
        assertEquals(intLit2, callExpr.getExprs().get(1));
    }
}