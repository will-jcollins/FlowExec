package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.IntLit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutputNodeTest {

    @Test
    void getExprs() {
        // Test that the expression is returned correctly
        OutputNode outputNode = new OutputNode();
        IntLit intLit = new IntLit(0);
        outputNode.setExpr(intLit, 0);
        assertEquals(intLit, outputNode.getExprs().get(0));
    }
}