package com.example.flowdemo.model.flow.expression;

import com.example.flowdemo.model.flow.DataType;

/**
 * Model representation of a flow-chart int expression
 */
public class IntLit extends Expr {
    int val;

    public IntLit() {
        this.val = 0;
    }

    public IntLit(int val) {
        this.val = val;
    }

    /**
     * Returns the int value of the expression
     * @return int value
     */
    public int getVal() {
        return val;
    }

    /**
     * Sets the int value of the expression
     * @param val new value of the expression
     */
    public void setVal(int val) {
        this.val = val;
    }

    /**
     * Returns a flow-language int
     * @return flow language int expression string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + val;
    }
}
