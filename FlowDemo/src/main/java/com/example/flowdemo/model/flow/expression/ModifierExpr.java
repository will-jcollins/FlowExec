package com.example.flowdemo.model.flow.expression;

import com.example.flowdemo.model.flow.nodes.ExprContainer;

import java.util.Arrays;
import java.util.List;

/**
 * Model representation of a flow-chart operator expression that is applied to one argument
 */
public class ModifierExpr extends Expr implements ExprContainer {

    private Expr expr; // Expression to be modified
    private Modifier modifier;

    public ModifierExpr() {
        modifier = Modifier.Not;
    }

    public ModifierExpr(Expr expr, Modifier modifier) {
        this.expr = expr;
        this.modifier = modifier;
    }

    /**
     * Returns the modifier enumeration used in the expression
     * @return modifier value
     */
    public Modifier getModifier() {
        return modifier;
    }

    /**
     * Sets the modifier enumeration used in the expression
     * @param modifier new modifier enumeration
     */
    public void setModifier(Modifier modifier) {
        this.modifier = modifier;
    }

    /**
     * Returns expression with id (if it exists) that exists within nested expression structure
     * @param id id of target expression
     * @return expr object that matches the id passed
     */
    @Override
    public Expr getById(int id) {
        Expr out = null;
        if (getId() == id) {
            out = this;
        } else {
            out = (expr.getById(id) != null) ? expr.getById(id) : out;
        }

        return out;
    }

    /**
     * Returns a flow-language modifier expression
     * @return flow language modifier expression string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + modifier.toString() + (expr == null ? "" : expr.toCode());
    }

    @Override
    public List<Expr> getExprs() {
        return Arrays.asList(expr);
    }

    @Override
    public void setExpr(Expr expr, int branch) {
        this.expr = expr;
    }
}
