package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.IntLit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForNodeTest {

    @Test
    void getIdentifier() {
        // Test that the identifier is returned correctly
        ForNode forNode = new ForNode();
        forNode.setIdentifier("test");
        assertEquals("test", forNode.getIdentifier());
    }

    @Test
    void getExprs() {
        // Test that the exprs are returned correctly
        ForNode forNode = new ForNode();
        IntLit intLit1 = new IntLit(0);
        IntLit intLit2 = new IntLit(1);
        forNode.setExpr(intLit1, 0);
        forNode.setExpr(intLit2, 1);
        assertEquals(intLit1, forNode.getExprs().get(0));
        assertEquals(intLit2, forNode.getExprs().get(1));

    }
}