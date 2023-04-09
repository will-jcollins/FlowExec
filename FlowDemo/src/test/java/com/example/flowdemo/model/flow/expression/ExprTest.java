package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExprTest {

    @Test
    void getById() {
        // Test default behaviour for getById
        IntLit intLit = new IntLit();
        assertEquals(intLit, intLit.getById(intLit.getId()));
    }
}