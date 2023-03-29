package com.example.flowdemo.model.transpiler;

import com.example.flowdemo.model.transpiler.antlr.FlowGrammarBaseVisitor;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarLexer;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class JavaConverter extends FlowGrammarBaseVisitor<String> {

    private static final int INDENT_SPACES = 5;

    @Override
    public String visitProg(FlowGrammarParser.ProgContext ctx) {
        StringBuilder output = new StringBuilder();

        // Wrap in static Java class structure
        output.append("public class Flowgram {\n");

        // Build function declarations
        for (FlowGrammarParser.DeclContext decl : ctx.funclist) {
            output.append((visit(decl) + "\n").indent(INDENT_SPACES));
        }

        output.append("}");

        return output.toString();
    }

    @Override
    public String visitDecl(FlowGrammarParser.DeclContext ctx) {
        StringBuilder output = new StringBuilder();

        if (ctx.signature(0).Idfr().getText().equals("main")) {
            // Generate java main signature if function signature is void main
            output.append("public static void main(String[] args)");
        } else {
            // Otherwise generate normal function signature
            output.append("public static ");
            output.append(visit(ctx.signature(0)));
            output.append("(");

            // Build parameter declarations
            String prefix = "";
            for (FlowGrammarParser.SignatureContext signature : ctx.params) {
                output.append(visit(signature) + prefix);
                prefix = ", ";
            }

            output.append(") ");
        }

        // Generate function body
        output.append(visit(ctx.block()));

        return output.toString();
    }

    @Override
    public String visitBlock(FlowGrammarParser.BlockContext ctx) {
        StringBuilder output = new StringBuilder();
        output.append("{\n");

        // Build list of statements
        for (FlowGrammarParser.StmtContext stmt : ctx.stmtlist) {
            output.append((visit(stmt) + "\n").indent(INDENT_SPACES));
        }

        output.append("}");

        return output.toString();
    }

    @Override
    public String visitDeclAssignStmt(FlowGrammarParser.DeclAssignStmtContext ctx) {
        return visit(ctx.signature()) + " = " + visit(ctx.expr()) + ";";
    }

    @Override
    public String visitAssignStmt(FlowGrammarParser.AssignStmtContext ctx) {
        return ctx.Idfr().getText() + " = " + visit(ctx.expr()) + ";";
    }

    @Override
    public String visitOutputStmt(FlowGrammarParser.OutputStmtContext ctx) {
        return "System.out.println(" + visit(ctx.expr()) + ");";
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
            output.append(prefix);
            output.append(visit(expr));
            prefix = ", ";
        }

        output.append(");");

        return output.toString();
    }

    @Override
    public String visitForStmt(FlowGrammarParser.ForStmtContext ctx) {
        return "for (" + visit(ctx.signature()) + " = " + visit(ctx.expr(0)) + "; " + ctx.signature().Idfr().getText() + " < " + visit(ctx.expr(1)) + "; i++) " + visit(ctx.block());
    }

    @Override
    public String visitWhileStmt(FlowGrammarParser.WhileStmtContext ctx) {
        return "while (" + visit(ctx.expr()) + ") " + visit(ctx.block());
    }

    @Override
    public String visitIfStmt(FlowGrammarParser.IfStmtContext ctx) {
        return "if (" + visit(ctx.expr()) + ") " + visit(ctx.block(0)) + " else " + visit(ctx.block(1));
    }

    @Override
    public String visitReturnStmt(FlowGrammarParser.ReturnStmtContext ctx) {
        return "return " + visit(ctx.expr()) + ";";
    }

    @Override
    public String visitIntExpr(FlowGrammarParser.IntExprContext ctx) {
        return ctx.IntLit().getText();
    }

    @Override
    public String visitCharExpr(FlowGrammarParser.CharExprContext ctx) {
        return "'" + ctx.CharLit().getText() + "'";
    }

    @Override
    public String visitBoolExpr(FlowGrammarParser.BoolExprContext ctx) {
        return ctx.BoolLit().getText().equals("TRUE") ? "true" : "false";
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
            output.append(prefix);
            output.append(visit(expr));
            prefix = ", ";
        }

        output.append(")");

        return output.toString();
    }

    @Override
    public String visitModifierExpr(FlowGrammarParser.ModifierExprContext ctx) {
        return visit(ctx.mod()) + visit(ctx.expr());
    }

    @Override
    public String visitOpExpr(FlowGrammarParser.OpExprContext ctx) {
        return visit(ctx.expr(0)) + " " + visit(ctx.op()) + visit(ctx.expr(1));
    }

    @Override
    public String visitOp(FlowGrammarParser.OpContext ctx) {
        switch (ctx.getText()) {
            case "+":
                return "+";
            case "-":
                return "-";
            case "/":
                return "/";
            case "*":
                return "*";
            case "%":
                return "%";
            case "<":
                return "<";
            case "<=":
                return "<=";
            case ">":
                return ">";
            case ">=":
                return ">=";
            case "EQUALS":
                return "==";
            case "AND":
                return "&&";
            case "OR":
                return "||";
            case "XOR":
                return "^";
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String visitMod(FlowGrammarParser.ModContext ctx) {
        switch (ctx.getText()) {
            case "NOT":
                return "!";
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String visitType(FlowGrammarParser.TypeContext ctx) {
        switch (ctx.getText()) {
            case "int":
                return "int";
            case "char":
                return "char";
            case "bool":
                return "boolean";
            case "void":
                return "void";
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String visitSignature(FlowGrammarParser.SignatureContext ctx) {
        return visit(ctx.type()) + " " + ctx.Idfr().getText();
    }

    public static void main(String[] args) throws IOException {
        CharStream input = CharStreams.fromStream(System.in);
        FlowGrammarLexer lexer = new FlowGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FlowGrammarParser parser = new FlowGrammarParser(tokens);
        ParseTree tree = parser.prog();

        JavaConverter converter = new JavaConverter();
        System.out.println(converter.visit(tree));
    }
}
