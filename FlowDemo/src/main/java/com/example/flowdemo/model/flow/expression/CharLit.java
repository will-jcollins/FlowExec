package com.example.flowdemo.model.flow.expression;

import com.example.flowdemo.model.flow.DataType;

public class CharLit extends Expr {

    /**
     * Model representation of a flow-chart char expression
     */
    private char val;

    public CharLit() {
        this.val = '\u0000'; // Null char
    }

    /**
     * Returns the char value of the expression
     * @return boolean value
     */
    public char getVal() {
        return val;
    }

    /**
     * Sets the char value of the expression
     * @param val new value of the expression
     */
    public void setVal(char val) {
        this.val = val;
    }

    /**
     * Returns a flow-language char
     * @return flow language char expression string
     */
    @Override
    public String toCode() {
        return super.toCode() + " '" + val + "'";
    }
}
