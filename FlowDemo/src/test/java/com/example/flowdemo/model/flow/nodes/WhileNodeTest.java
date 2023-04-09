package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.IntLit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileNodeTest {

    @Test
    void getExprs() {
        // Test that the expression is returned correctly
        WhileNode whileNode = new WhileNode();
        IntLit intLit = new IntLit(0);
        whileNode.setExpr(intLit, 0);
        assertEquals(intLit, whileNode.getExprs().get(0));
    }
}