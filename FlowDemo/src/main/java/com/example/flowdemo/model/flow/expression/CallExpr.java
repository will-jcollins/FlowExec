package com.example.flowdemo.model.flow.expression;

import com.example.flowdemo.model.flow.nodes.ExprContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Model representation of a flow-chart function invocation expression
 */
public class CallExpr extends Expr implements ExprContainer {

    private String identifier; // Name of the function to be called
    private List<Expr> parameters; // List of expressions to be used as parameters in the function invocation

    public CallExpr() {
        identifier = "";
        parameters = new ArrayList<>();
    }

    /**
     * Returns the identifier for the function to be called in the expression
     * @return string identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier for the function to be called in the expression
     * @param identifier new value of the function identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Updates the number of expected parameters to be taken when calling a function in this expression
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
     * Returns a flow-language call expression
     * @return flow language call expression string
     */
    @Override
    public String toCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toCode() + " ");
        sb.append(identifier + "(");

        // Build function arguments
        String prefix = "";
        for (Expr expr : parameters) {
            sb.append(prefix);
            prefix = ",";
            sb.append((expr == null ? "" : expr.toCode()));
        }

        sb.append(")");
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
