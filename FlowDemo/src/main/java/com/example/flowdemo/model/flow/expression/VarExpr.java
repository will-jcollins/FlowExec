package com.example.flowdemo.model.flow.expression;

/**
 * Model representation of a flow-chart variable expression
 */
public class VarExpr extends Expr {
    private String identifier; // Variable identifier

    public VarExpr() {
        identifier = "";
    }

    public VarExpr(String val) {
        this.identifier = val;
    }

    /**
     * Returns the identifier of the variable in the expression
     * @return string identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the variable identifier for the expression
     * @param identifier new value of the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns a flow-language variable expression
     * @return flow-language variable expression string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + identifier;
    }
}
