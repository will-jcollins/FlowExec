package com.example.flowdemo.model.flow.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModifierExprTest {

    @Test
    void getModifier() {
        // Test that the modifier is returned correctly
        ModifierExpr modifierExpr = new ModifierExpr();
        modifierExpr.setModifier(Modifier.Not);
        assertEquals(Modifier.Not, modifierExpr.getModifier());
    }

    @Test
    void getById() {
        ModifierExpr modifierExpr = new ModifierExpr();

        // Test null expression in modifier expression does not cause exception
        assertNull(modifierExpr.getById(modifierExpr.getId() + 1));

        // Test override behaviour for getById
        IntLit intLit = new IntLit();
        modifierExpr.setExpr(intLit, 0);
        assertEquals(intLit, modifierExpr.getById(intLit.getId()));
    }

    @Test
    void getExprs() {
        // Test that the expression is returned correctly
        ModifierExpr modifierExpr = new ModifierExpr();
        IntLit intLit = new IntLit();
        modifierExpr.setExpr(intLit, 0);
        assertEquals(intLit, modifierExpr.getExprs().get(0));
    }
}