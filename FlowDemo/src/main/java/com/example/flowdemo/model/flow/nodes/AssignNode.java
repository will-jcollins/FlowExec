package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.Expr;

import java.util.Arrays;
import java.util.List;

/**
 * Model representation of a flow-chart assign statement
 */
public class AssignNode extends FlowNode implements ExprContainer {

    private String identifier; // identifier of the variable to be assigned
    private Expr val; // Expression value to assign to variable

    public AssignNode() {
        identifier = "";
    }

    /**
     * Returns the identifier of the variable being assigned
     * @return string identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the variable identifier for the variable being assigned
     * @param identifier new value of the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns a flow-language assignment statement
     * @return flow-language assignment statement string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + identifier + " = " + (val == null ? "" : val.toCode()) + ";";
    }

    @Override
    public List<Expr> getExprs() {
        return Arrays.asList(val);
    }

    @Override
    public void setExpr(Expr expr, int branch) {
        val = expr;
    }
}
