package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.IntLit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CallNodeTest {

    @Test
    void getIdentifier() {
        // Test that the identifier is returned correctly
        CallNode call = new CallNode();
        call.setIdentifier("test");
        assertEquals("test", call.getIdentifier());
    }

    @Test
    void setNumberOfParameters() {
        // Test that the number of parameters is set correctly
        CallNode call = new CallNode();

        call.setNumberOfParameters(2);
        assertEquals(2, call.getExprs().size());

        // Test that the number of parameters shrinks
        call.setNumberOfParameters(1);
        assertEquals(1, call.getExprs().size());
    }

    @Test
    void getExprs() {
        // Test that the exprs are returned correctly
        CallNode call = new CallNode();
        call.setNumberOfParameters(1);
        IntLit intLit = new IntLit(0);
        call.setExpr(intLit, 0);
        assertEquals(intLit, call.getExprs().get(0));
    }
}