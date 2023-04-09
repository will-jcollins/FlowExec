package com.example.flowdemo.model.transpiler;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.expression.Modifier;
import com.example.flowdemo.model.flow.expression.Operator;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarBaseVisitor;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarParser;

public class PythonConverter extends FlowGrammarBaseVisitor<String> {

    private static final int INDENT_SPACES = 5;

    @Override
    public String visitProg(FlowGrammarParser.ProgContext ctx) {
        StringBuilder output = new StringBuilder();

        // Build function declarations
        for (FlowGrammarParser.DeclContext decl : ctx.funclist) {
            output.append(visit(decl) + "\n\n");
        }

        return output.toString();
    }

    @Override
    public String visitDecl(FlowGrammarParser.DeclContext ctx) {
        StringBuilder output = new StringBuilder();

        if (ctx.signature(0).Idfr().getText().equals("main")) {
            // Generate main function
            output.append("if __name__ == \"__main__\":\n");
        } else {
            // Otherwise generate normal function
            output.append("def " + ctx.signature(0).Idfr().getText() + "(");

            // Build parameters
            String prefix = "";
            for (FlowGrammarParser.SignatureContext signature : ctx.params) {
                output.append(prefix + signature.Idfr().getText());
                prefix = ", ";
            }

            output.append("):\n");
        }

        // Generate function body
        output.append(visit(ctx.block()));

        return output.toString();
    }

    @Override
    public String visitBlock(FlowGrammarParser.BlockContext ctx) {
        StringBuilder output = new StringBuilder();

        if (ctx.stmtlist.size() > 0) {
            for (FlowGrammarParser.StmtContext stmt : ctx.stmtlist) {
                output.append((visit(stmt) + "\n").indent(INDENT_SPACES));
            }
        } else {
            output.append("pass\n".indent(INDENT_SPACES));
        }

        return output.toString();
    }

    @Override
    public String visitDeclAssignStmt(FlowGrammarParser.DeclAssignStmtContext ctx) {
        return ctx.signature().Idfr().getText() + " = " + visit(ctx.expr());
    }

    @Override
    public String visitArrayAssignStmt(FlowGrammarParser.ArrayAssignStmtContext ctx) {
        return ctx.Idfr().getText() + "[" + visit(ctx.expr(0)) + "] = " + visit(ctx.expr(1));
    }

    @Override
    public String visitAssignStmt(FlowGrammarParser.AssignStmtContext ctx) {
        return ctx.Idfr() + " = " + visit(ctx.expr());
    }

    @Override
    public String visitOutputStmt(FlowGrammarParser.OutputStmtContext ctx) {
        return "print(" + visit(ctx.expr()) + ")";
    }

    @Override
    public String visitCallStmt(FlowGrammarParser.CallStmtContext ctx) {
        StringBuilder output = new StringBuilder();

        // Build function name
        output.append(ctx.Idfr());
        output.append("(");

        // Generate parameters
        String prefix = "";
        for (FlowGrammarParser.ExprContext expr : ctx.params) {
            output.append(prefix + visit(expr));
            prefix = ", ";
        }

        output.append(")");

        return output.toString();
    }

    @Override
    public String visitForStmt(FlowGrammarParser.ForStmtContext ctx) {
        return "for " + ctx.signature().Idfr().getText() + " in range(" + visit(ctx.expr(0)) + ", " + visit(ctx.expr(1)) + "):\n" + visit(ctx.block());
    }

    @Override
    public String visitWhileStmt(FlowGrammarParser.WhileStmtContext ctx) {
        return "while " + visit(ctx.expr()) + ":\n" + visit(ctx.block());
    }

    @Override
    public String visitIfStmt(FlowGrammarParser.IfStmtContext ctx) {
        return "if " + visit(ctx.expr()) + ":\n" + visit(ctx.block(0)) + "else:\n" + visit(ctx.block(1));
    }

    @Override
    public String visitReturnStmt(FlowGrammarParser.ReturnStmtContext ctx) {
        return "return " + visit(ctx.expr());
    }

    @Override
    public String visitIntExpr(FlowGrammarParser.IntExprContext ctx) {
        return ctx.IntLit().getText();
    }

    @Override
    public String visitCharExpr(FlowGrammarParser.CharExprContext ctx) {
        return ctx.CharLit().getText();
    }

    @Override
    public String visitBoolExpr(FlowGrammarParser.BoolExprContext ctx) {
        return ctx.BoolLit().getText().equals("TRUE") ? "True" : "False";
    }

    @Override
    public String visitVarExpr(FlowGrammarParser.VarExprContext ctx) {
        return ctx.Idfr().getText();
    }

    @Override
    public String visitCallExpr(FlowGrammarParser.CallExprContext ctx) {
        StringBuilder output = new StringBuilder();

        // Build function name
        output.append(ctx.Idfr());
        output.append("(");

        // Generate parameters
        String prefix = "";
        for (FlowGrammarParser.ExprContext expr : ctx.params) {
            output.append(prefix + visit(expr));
            prefix = ", ";
        }

        output.append(")");

        return output.toString();
    }

    @Override
    public String visitModifierExpr(FlowGrammarParser.ModifierExprContext ctx) {
        Modifier modifier = Modifier.fromString(ctx.mod().getText());

        if (modifier == Modifier.Size) {
            return "len(" + visit(ctx.expr()) + ")";
        } else {
            return visit(ctx.mod()) + visit(ctx.expr());
        }
    }

    @Override
    public String visitOpExpr(FlowGrammarParser.OpExprContext ctx) {
        Operator operator = Operator.fromString(ctx.op().getText());

        if (operator == Operator.Index) {
            return visit(ctx.expr(0)) + "[" + visit(ctx.expr(1)) + "]";
        } else {
            return visit(ctx.expr(0)) + " " + visit(ctx.op()) + " " + visit(ctx.expr(1));
        }
    }

    @Override
    public String visitArrayExpr(FlowGrammarParser.ArrayExprContext ctx) {
        StringBuilder output = new StringBuilder("[");

        String prefix = "";
        for (FlowGrammarParser.ExprContext exprCtx : ctx.expr()) {
            output.append(prefix + visit(exprCtx));
            prefix = ", ";
        }

        output.append("]");
        return output.toString();
    }

    @Override
    public String visitOp(FlowGrammarParser.OpContext ctx) {
        Operator operator = Operator.fromString(ctx.getText());
        switch (operator) {
            case Add:
                return "+";
            case Sub:
                return "-";
            case Div:
                return "//";
            case Mult:
                return "*";
            case Mod:
                return "%";
            case Less:
                return "<";
            case LessEq:
                return "<=";
            case Greater:
                return ">";
            case GreaterEq:
                return ">=";
            case Eq:
                return "==";
            case And:
                return "and";
            case Or:
                return "or";
            case Xor:
                return "^";
            default:
                throw new IllegalArgumentException("Logic error in PythonConverter: op context");
        }
    }

    @Override
    public String visitMod(FlowGrammarParser.ModContext ctx) {
        Modifier modifier = Modifier.fromString(ctx.getText());
        switch (modifier) {
            case Not:
                return "not ";
            default:
                throw new IllegalArgumentException("Logic error in PythonConverter: mod context");
        }
    }

    @Override
    public String visitType(FlowGrammarParser.TypeContext ctx) {
        throw new IllegalArgumentException("Logic error in PythonConverter: type context");
    }

    @Override
    public String visitSignature(FlowGrammarParser.SignatureContext ctx) {
        throw new IllegalArgumentException("Logic error in PythonConverter: signature context");
    }
}
