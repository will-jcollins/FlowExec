package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.Expr;

import java.util.Arrays;
import java.util.List;

/**
 * Model representation of a flow-chart return statement
 */
public class ReturnNode extends FlowNode implements ExprContainer {
    private Expr expr; // Value to return

    /**
     * Returns a flow-language return statement
     * @return flow-language return statement string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + "return " + (expr == null ? "" : expr.toCode());
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
