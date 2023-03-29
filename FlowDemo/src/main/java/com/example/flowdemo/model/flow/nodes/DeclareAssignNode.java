package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.Signature;
import com.example.flowdemo.model.flow.expression.Expr;

import java.util.Arrays;
import java.util.List;

/**
 * Model representation of a flow-chart declare & assign statement
 */
public class DeclareAssignNode extends FlowNode implements ExprContainer {

    private Signature signature; // Typed identifier of variable being created
    private Expr val; // Initial value of variable

    public DeclareAssignNode() {
        signature = new Signature();
    }

    /**
     * Returns the typed identifier of the variable being created
     * @return string identifier
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     * Sets the typed identifier for the variable being assigned
     * @param signature new value of the typed identifier
     */
    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    @Override
    public List<Expr> getExprs() {
        return Arrays.asList(val);
    }

    @Override
    public void setExpr(Expr expr, int branch) {
        val = expr;
    }

    /**
     * Returns a flow-language declare & assign statement
     * @return flow-language declare & assign statement string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " +  signature.toCode() + " = " + (val == null ? "" : val.toCode()) + ";";
    }
}
