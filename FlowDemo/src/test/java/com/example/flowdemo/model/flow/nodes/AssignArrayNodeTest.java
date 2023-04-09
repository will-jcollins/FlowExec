package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.IntLit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignArrayNodeTest {

    @Test
    void getExprs() {
        // Test that the exprs are returned correctly
        AssignArrayNode assignArr = new AssignArrayNode();
        IntLit intLit1 = new IntLit(0);
        IntLit intLit2 = new IntLit(1);
        assignArr.setExpr(intLit1, 0);
        assignArr.setExpr(intLit2, 1);
        assertEquals(intLit1, assignArr.getExprs().get(0));
        assertEquals(intLit2, assignArr.getExprs().get(1));
    }
}