package com.example.flowdemo.model.flow.expression;

import com.example.flowdemo.model.flow.nodes.ExprContainer;
import com.example.flowdemo.view.editor.expr.UIExpr;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArrayLit extends Expr implements ExprContainer {

    private List<Expr> elements = new LinkedList<>();

    @Override
    public List<Expr> getExprs() {
        return elements;
    }



    @Override
    public void setExpr(Expr expr, int branch) {
        if (branch > elements.size() - 1) {
             // Make array larger if branch is out of bounds
            elements.add(expr);
        } else {
            elements.set(branch, expr);
        }

        // Make list have no trailing null values
        for (int i = elements.size() - 1; i >= 0; i--) {
            if (elements.get(i) == null) {
                elements.remove(i);
            } else {
                break;
            }
        }
    }

    @Override
    public Expr getById(int id) {
        Expr out = null;
        if (getId() == id) {
            out = this;
        } else {
            for (Expr expr : elements) {
                out = (expr.getById(id) != null) ? expr.getById(id) : out;
            }
        }

        return out;
    }

    @Override
    public String toCode() {
        StringBuilder sb = new StringBuilder(super.toCode() + " [");

        String prefix ="";
        for (Expr expr : elements) {
            sb.append(prefix + expr.toCode());
            prefix = ", ";
        }

        sb.append("]");
        return sb.toString();
    }
}
