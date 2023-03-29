package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.Expr;

import java.util.Arrays;
import java.util.List;

/**
 * Model representation of a flow-chart if-else statement
 */
public class IfNode extends FlowNode implements ExprContainer, FlowContainer {

    private Expr condition; // Boolean condition to evaluate
    private Flow ifBody = new Flow(); // list of statements to execute when condition is true
    private Flow elseBody = new Flow(); // list of statements to execute when condition is false

    public IfNode() {
    }

    @Override
    public List<Expr> getExprs() {
        return Arrays.asList(condition);
    }

    @Override
    public void setExpr(Expr expr, int branch) {
        condition = expr;
    }

    @Override
    public List<Flow> getFlows() {
        return Arrays.asList(ifBody, elseBody);
    }

    /**
     * Returns a flow-language if-else statement
     * @return Flow-language if-else statement string
     */
    @Override
    public String toCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toCode() + " " + "if (" + (condition == null ? "" : condition.toCode()) + ") {\n");
        sb.append(ifBody.toBodyCode().indent(5) + "} else {\n");
        sb.append(elseBody.toBodyCode().indent(5) + "}");

        return sb.toString();
    }
}
