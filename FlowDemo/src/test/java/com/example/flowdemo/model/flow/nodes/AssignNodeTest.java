package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.IntLit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignNodeTest {

    @Test
    void getIdentifier() {
        // Test that the identifier is returned correctly
        AssignNode assign = new AssignNode();
        assign.setIdentifier("test");
        assertEquals("test", assign.getIdentifier());
    }

    @Test
    void getExprs() {
        // Test that the exprs are returned correctly
        AssignNode assign = new AssignNode();
        IntLit intLit1 = new IntLit(0);
        assign.setExpr(intLit1, 0);
        assertEquals(intLit1, assign.getExprs().get(0));
    }
}