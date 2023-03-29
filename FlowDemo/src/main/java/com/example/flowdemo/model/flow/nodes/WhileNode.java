package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.Expr;

import java.util.Arrays;
import java.util.List;

/**
 * Model representation of a flow-chart for loop
 */
public class WhileNode extends FlowNode implements ExprContainer, FlowContainer {

    private Expr condition; // Condition to evaluate
    private Flow loopBody = new Flow(); // List of statements to loop when condition is true

    public WhileNode() {
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
        return Arrays.asList(loopBody);
    }

    /**
     * Returns a flow-language while loop
     * @return Flow-language while loop string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + "while (" + (condition == null ? "" : condition.toCode()) + ") {\n" + loopBody.toBodyCode().indent(5) + "}";
    }
}
