package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayLitTest {

    @Test
    void getExprs() {
        // Test that the expression is returned correctly
        ArrayLit arrayLit = new ArrayLit();
        IntLit intLit = new IntLit();
        arrayLit.setExpr(intLit, 0);
        assertEquals(intLit, arrayLit.getExprs().get(0));

        // Test that exception is not thrown when an element is set to an out-of-bounds index
        arrayLit.setExpr(intLit, 100);
        assertEquals(2, arrayLit.getExprs().size());


        // Test that the array's size shrinks to the last non-null value
        arrayLit.setExpr(null, 0);
        assertEquals(2, arrayLit.getExprs().size());

        arrayLit.setExpr(null, 1);
        assertEquals(0, arrayLit.getExprs().size());
    }

    @Test
    void getById() {
        // Test that the expression is returned correctly
        ArrayLit arrayLit = new ArrayLit();
        IntLit intLit1 = new IntLit();
        arrayLit.setExpr(intLit1, 0);
        IntLit intLit2 = new IntLit();
        arrayLit.setExpr(intLit2, 1);
        IntLit intLit3 = new IntLit();
        arrayLit.setExpr(intLit3, 2);

        assertEquals(intLit1, arrayLit.getById(intLit1.getId()));
        assertEquals(intLit2, arrayLit.getById(intLit2.getId()));
        assertEquals(intLit3, arrayLit.getById(intLit3.getId()));
    }
}