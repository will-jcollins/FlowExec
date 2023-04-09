package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpExprTest {

    @Test
    void getOp() {
        // Test that the correct operator is returned
        OpExpr opExpr = new OpExpr();
        opExpr.setOp(Operator.Add);
        assertEquals(Operator.Add, opExpr.getOp());
    }

    @Test
    void getById() {
        // Test override behavior for getById
        OpExpr opExpr1 = new OpExpr();
        IntLit intLit1 = new IntLit();
        IntLit intLit2 = new IntLit();
        opExpr1.setExpr(intLit1, 0);
        opExpr1.setExpr(intLit2, 1);
        assertEquals(intLit1, opExpr1.getById(intLit1.getId()));

        // Test behaviour carries into nested expressions and null expressions do not cause exceptions
        OpExpr opExpr2 = new OpExpr();
        IntLit intLit3 = new IntLit();
        opExpr2.setExpr(intLit3, 0);
        opExpr1.setExpr(opExpr2, 1);
        assertEquals(intLit3, opExpr1.getById(intLit3.getId()));
    }

    @Test
    void getExprs() {
        // Test that the expressions are returned correctly
        OpExpr opExpr = new OpExpr();
        IntLit intLit1 = new IntLit();
        IntLit intLit2 = new IntLit();
        opExpr.setExpr(intLit1, 0);
        opExpr.setExpr(intLit2, 1);
        assertEquals(intLit1, opExpr.getExprs().get(0));
        assertEquals(intLit2, opExpr.getExprs().get(1));
    }
}