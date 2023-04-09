package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.IntLit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReturnNodeTest {

    @Test
    void getExprs() {
        // Test that the expression is returned correctly
        ReturnNode returnNode = new ReturnNode();
        IntLit intLit = new IntLit(0);
        returnNode.setExpr(intLit, 0);
        assertEquals(intLit, returnNode.getExprs().get(0));
    }
}