package com.example.flowdemo.model.flow.expression;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.nodes.ExprContainer;

import java.util.Arrays;
import java.util.List;

public class OpExpr extends Expr implements ExprContainer {

    private Expr left;
    private Expr right;
    private Operator op;

    public OpExpr() {
        op = Operator.Add;
    }

    public OpExpr(Expr left, Expr right, Operator op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    /**
     * Returns the operator enumeration used in the expression
     * @return operator value
     */
    public Operator getOp() {
        return op;
    }

    /**
     * Sets the operator enumeration used in the expression
     * @param op new operator enumeration
     */
    public void setOp(Operator op) {
        this.op = op;
    }

    /**
     * Returns expression with id (if it exists) that exists within nested expression structure
     * @param id id of target expression
     * @return expr object that matches the id passed
     */
    @Override
    public Expr getById(int id) {
        Expr out = null;
        if (getId() == id) {
            out = this;
        } else {
            out = (left.getById(id) != null) ? left.getById(id) : out;
            out = (right.getById(id) != null) ? right.getById(id) : out;
        }

        return out;
    }

    /**
     * Returns a flow-language operator expression
     * @return flow language modifier operator string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + (left == null ? "" : left.toCode()) + " " + op.toString() + " " + (right == null ? "" : right.toCode());
    }

    @Override
    public List<Expr> getExprs() {
        return Arrays.asList(left, right);
    }

    @Override
    public void setExpr(Expr expr, int branch) {
        if (branch < 1) {
            left = expr;
        } else {
            right = expr;
        }
    }
}
