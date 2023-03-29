package com.example.flowdemo.model.flow.expression;

import com.example.flowdemo.model.flow.DataType;

import java.util.Objects;

/**
 * Model representation of a flow-chart expression with a unique identifier
 */
public abstract class Expr {

    private static int idCounter = 0; // Static ID counter, assigns unique ID to instantiated objects

    private final int id; // Unique identifier

    public Expr() {
        this.id = idCounter++;
    }

    /**
     * Get expression's unique identifier
     * @return unique id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns expression with id (if it exists) that exists within nested expression structure
     * @param id id of target expression
     * @return expr object that matches the id passed
     */
    public Expr getById(int id) {
        return (id == this.id) ? this : null;
    }

    /**
     * Returns an expression's flow language equivalent
     * @return flow language string
     */
    public String toCode() {
        // Include expression id so analyser can relate errors to model
        return "|" + id + "|";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expr newExpr = (Expr) o;
        return id == newExpr.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
