package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.Signature;
import com.example.flowdemo.model.flow.expression.Expr;

import java.util.Arrays;
import java.util.List;

/**
 * Model representation of a flow-chart for loop
 */
public class ForNode extends FlowNode implements ExprContainer, FlowContainer {
    private String identifier;
    private Expr startVal;
    private Expr endVal;
    private Flow loopBody = new Flow();

    public ForNode() {
        identifier = "";
    }

    /**
     * Returns the identifier of the counter variable used in the for loop
     * @return string identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier of the counter variable used in the for loop
     * @param identifier new identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public List<Expr> getExprs() {
        return Arrays.asList(startVal, endVal);
    }

    @Override
    public void setExpr(Expr expr, int branch) {
        if (branch == 0) {
            startVal = expr;
        } else {
            endVal = expr;
        }
    }

    @Override
    public List<Flow> getFlows() {
        return Arrays.asList(loopBody);
    }

    /**
     * Returns a flow-language for loop
     * @return Flow-language for loop string
     */
    @Override
    public String toCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toCode() + " " + "For (Int " + identifier + " = " + (startVal == null ? "" : startVal.toCode()) + ", " + (endVal == null ? "" : endVal.toCode()) + ") {\n");
        sb.append(loopBody.toBodyCode().indent(5));
        sb.append("}");
        return sb.toString();
    }
}
