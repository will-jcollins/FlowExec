package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.expression.Expr;

import java.util.ArrayList;
import java.util.List;

public class AssignArrayNode extends AssignNode {

    private Expr index;

    @Override
    public List<Expr> getExprs() {
        List<Expr> out = new ArrayList<>();
        out.addAll(super.getExprs());
        out.add(index);
        return out;
    }

    @Override
    public void setExpr(Expr expr, int branch) {
        if (branch > 0) {
            index = expr;
        } else {
            super.setExpr(expr, branch);
        }
    }

    @Override
    public String toCode() {
        return "|" + getId() + "| " + getIdentifier() + "[" + (index == null ? "" : index.toCode()) + "] = " + (getExprs().get(0) == null ? "" : getExprs().get(0).toCode()) + ";";
    }
}
