package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.IntLit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfNodeTest {

    @Test
    void getExprs() {
        // Test that the expression is returned correctly
        IfNode ifNode = new IfNode();
        IntLit intLit = new IntLit(0);
        ifNode.setExpr(intLit, 0);
        assertEquals(intLit, ifNode.getExprs().get(0));
    }
}