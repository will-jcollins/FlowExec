package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.Signature;
import com.example.flowdemo.model.flow.expression.Expr;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Model representation of a flow-chart call statement
 */
public class CallNode extends FlowNode implements ExprContainer {

    private String identifier;
    private List<Expr> parameters;

    public CallNode() {
        identifier = "";
        parameters = new LinkedList<>();
    }

    /**
     * Returns the identifier for the function to be called
     * @return string identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier for the function to be called
     * @param identifier new value of the function identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Updates the number of expected parameters to be taken when calling a function
     * @param n new number of expected parameters
     */
    public void setNumberOfParameters(int n) {
        while (parameters.size() < n) {
            parameters.add(null);
        }

        while (parameters.size() > n) {
            parameters.remove(parameters.size() - 1);
        }
    }

    /**
     * Returns a flow-language call statement
     * @return flow language call statement string
     */
    @Override
    public String toCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toCode() + " " + identifier + "(");

        // Build function arguments
        String prefix = "";
        for (Expr expr : parameters) {
            sb.append(prefix);
            prefix = ",";
            sb.append(expr.toCode());
        }

        sb.append(");");

        return sb.toString();
    }

    @Override
    public List<Expr> getExprs() {
        return parameters;
    }

    @Override
    public void setExpr(Expr expr, int branch) {
        parameters.set(branch, expr);
    }
}
