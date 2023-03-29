package com.example.flowdemo.model.flow.expression;

import com.example.flowdemo.model.flow.DataType;

/**
 * Model representation of a flow-chart boolean expression
 */
public class BoolLit extends Expr {

    boolean val;

    public BoolLit() {
        this.val = true;
    }

    public BoolLit(boolean val) {
        this.val = val;
    }

    /**
     * Returns the boolean value of the expression
     * @return boolean value
     */
    public boolean getVal() {
        return val;
    }

    /**
     * Sets the boolean value of the expression
     * @param val new value of the expression
     */
    public void setVal(boolean val) {
        this.val = val;
    }

    /**
     * Returns a flow-language boolean
     * @return flow language boolean expression string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + (val ? "TRUE" : "FALSE");
    }
}
