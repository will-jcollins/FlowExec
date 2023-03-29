package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.Expr;

import java.util.Arrays;
import java.util.List;

/**
 * Model representation of a flow-chart output statement
 */
public class OutputNode extends FlowNode implements ExprContainer {

    private Expr val; // Value to output

    @Override
    public List<Expr> getExprs() {
        return Arrays.asList(val);
    }

    @Override
    public void setExpr(Expr expr, int branch) {
        val = expr;
    }

    /**
     * Returns a flow-language output statement
     * @return flow-language output statement string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + "Output(" + (val == null ? "" : val.toCode()) + ");";
    }
}
