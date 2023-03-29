package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.Expr;
import com.example.flowdemo.model.flow.expression.OpExpr;

import java.util.List;

/**
 * Interface for Flow model objects which store expressions
 */
public interface ExprContainer {

    /**
     * Returns a list of the expressions contained within an ExprContainer
     * @return list of expressions
     */
    List<Expr> getExprs();

    /**
     * Sets the expr at the index specified
     * @param expr new expression to store
     * @param branch index to store the expression at
     */
    void setExpr(Expr expr, int branch);

    /**
     * Searches an expression's structure for the expression with the id specified
     * @param expr expression to search
     * @param id id of the target expression
     * @return expression which matches the id passed (null if no match is found)
     */
    static Expr searchExpr(Expr expr, int id) {
        Expr out = null;

        if (expr != null) {
            if (expr.getId() == id) {
                // Set return value to current expr if one is found
                out = expr;
            } else if (expr instanceof ExprContainer container) {
                // Otherwise if expression contains more expressions, recursively search child expressions
                List<Expr> childExprs = container.getExprs();
                for (int i = 0; i < childExprs.size(); i++) {
                    out = (out == null) ? searchExpr(childExprs.get(i), id) : out;
                }
            }
        }

        return out;
    }

    /**
     * Searches an expression's structure for the expression with the id specified, returns the parent expression
     * @param expr expression to search
     * @param id id of child expression to find
     * @return parent expression of the expression with the id passed
     */
    static Expr searchExprParent(Expr expr, int id) {
        Expr out = null;

        if (expr != null) {
            if (expr instanceof ExprContainer container) {
                // If expression contains children (implements ExprContainer interface) search for child expression
                //      matching the id parameter
                List<Expr> childExprs = container.getExprs();
                for (int i = 0; i < childExprs.size(); i++) {
                    Expr childExpr = childExprs.get(i);
                    if (childExpr != null) {
                        if (childExpr.getId() == id) {
                            out = expr;
                        }
                    }
                }

                if (out == null) {
                    // If a child was not found recursively search children's children for id
                    for (int i = 0; i < childExprs.size(); i++) {
                        out = (out == null) ? searchExprParent(childExprs.get(i), id) : out;
                    }
                }
            }
        }

        return out;
    }

    /**
     * Sets the value of the expression with the id passed to expr in
     * @param target expression to search for value to set
     * @param in value to set expression to
     * @param id id of expression to overwrite
     */
    static void setChildExpr(Expr target, Expr in, int id) {
        // Find the parent of the expression to overwrite
        Expr parentExpr = searchExprParent(target, id);

        if (parentExpr != null) {
            if (parentExpr instanceof ExprContainer container) {
                // If parentExpr contains child expressions, search for one that matches id
                List<Expr> childExprs = container.getExprs();
                for (int i = 0; i < childExprs.size(); i++) {
                    Expr expr = childExprs.get(i);
                    if (expr != null) {
                        if (expr.getId() == id) {
                            // If id matches, overwrite to new value
                            container.setExpr(in, i);
                        } else {
                            // Otherwise recursively search expression for expression with matching id to overwrite
                            setChildExpr(expr, in, id);
                        }
                    }
                }
            }
        }
    }
}
