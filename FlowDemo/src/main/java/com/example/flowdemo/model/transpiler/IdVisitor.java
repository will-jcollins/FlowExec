package com.example.flowdemo.model.transpiler;

import com.example.flowdemo.model.transpiler.antlr.FlowGrammarBaseVisitor;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarParser;

public class IdVisitor extends FlowGrammarBaseVisitor<Integer> {

    private static int toId(String value) {
        return Integer.parseInt(value.replaceAll("\\|", ""));
    }

    @Override
    public Integer visitIntExpr(FlowGrammarParser.IntExprContext ctx) {
        return toId(ctx.ComponentId().getText());
    }

    @Override
    public Integer visitCharExpr(FlowGrammarParser.CharExprContext ctx) {
        return toId(ctx.ComponentId().getText());
    }

    @Override
    public Integer visitBoolExpr(FlowGrammarParser.BoolExprContext ctx) {
        return toId(ctx.ComponentId().getText());
    }

    @Override
    public Integer visitVarExpr(FlowGrammarParser.VarExprContext ctx) {
        return toId(ctx.ComponentId().getText());
    }

    @Override
    public Integer visitCallExpr(FlowGrammarParser.CallExprContext ctx) {
        return toId(ctx.ComponentId().getText());
    }

    @Override
    public Integer visitModifierExpr(FlowGrammarParser.ModifierExprContext ctx) {
        return toId(ctx.ComponentId().getText());
    }

    @Override
    public Integer visitOpExpr(FlowGrammarParser.OpExprContext ctx) {
        return toId(ctx.ComponentId().getText());
    }


}
