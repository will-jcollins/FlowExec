package com.example.flowdemo.model.transpiler;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.expression.Modifier;
import com.example.flowdemo.model.flow.expression.Operator;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarBaseVisitor;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarParser;

public class JavaConverter extends FlowGrammarBaseVisitor<String> {

    private static final int INDENT_SPACES = 5;
    private FlowGrammarAnalyser typeAnalyser = new FlowGrammarAnalyser();

    @Override
    public String visitProg(FlowGrammarParser.ProgContext ctx) {
        // Populate type analyser's function table
        typeAnalyser.visit(ctx);

        StringBuilder output = new StringBuilder();

        // Wrap in static Java class structure
        output.append("import java.util.*;\n\npublic class Flowgram {\n");

        // Build function declarations
        for (FlowGrammarParser.DeclContext decl : ctx.funclist) {
            output.append((visit(decl) + "\n\n").indent(INDENT_SPACES));
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
                output.append(prefix + visit(signature));
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
    public String visitArrayAssignStmt(FlowGrammarParser.ArrayAssignStmtContext ctx) {
        return ctx.Idfr().getText() + "[" + visit(ctx.expr(0)) + "] = " + visit(ctx.expr(1)) + ";";
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
            output.append(prefix + visit(expr));
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
        return ctx.CharLit().getText();
    }

    @Override
    public String visitBoolExpr(FlowGrammarParser.BoolExprContext ctx) {
        return ctx.BoolLit().getText().equals("TRUE") ? "true" : "false";
    }

    @Override
    public String visitArrayExpr(FlowGrammarParser.ArrayExprContext ctx) {
        StringBuilder output = new StringBuilder("new ");
        DataType type = typeAnalyser.visit(ctx);

        switch (type) {
            case IntArrayType -> output.append("int[]");
            case BoolArrayType -> output.append("boolean[]");
            case CharArrayType -> output.append("char[]");
            default -> throw new IllegalArgumentException("Logic error in JavaConverter: arrayExpr context");
        }

        output.append("{");
        String prefix = "";

        for (FlowGrammarParser.ExprContext exprCtx : ctx.expr()) {
            output.append(prefix + visit(exprCtx));
            prefix = ", ";
        }

        output.append("}");

        return output.toString();
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
            return visit(ctx.expr()) + visit(ctx.mod());
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
            return "(" + visit(ctx.expr(0)) + " " + visit(ctx.op()) + " " + visit(ctx.expr(1)) + ")";
        }
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
                return "/";
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
                return "&&";
            case Or:
                return "||";
            case Xor:
                return "^";
            default:
                throw new IllegalArgumentException("Logic error in JavaConverter: op context");
        }
    }

    @Override
    public String visitMod(FlowGrammarParser.ModContext ctx) {
        Modifier modifier = Modifier.fromString(ctx.getText());
        switch (modifier) {
            case Not:
                return "!";
            case Size:
                return ".length";
            default:
                throw new IllegalArgumentException("Logic error in JavaConverter: mod context");
        }
    }

    @Override
    public String visitType(FlowGrammarParser.TypeContext ctx) {
        DataType type = DataType.fromString(ctx.getText());
        switch (type) {
            case IntType:
                return "int";
            case CharType:
                return "char";
            case BoolType:
                return "boolean";
            case IntArrayType:
                return "int[]";
            case BoolArrayType:
                return "boolean[]";
            case CharArrayType:
                return "char[]";
            case VoidType:
                return "void";
            default:
                throw new IllegalArgumentException("Logic error in JavaConverter: type context");
        }
    }

    @Override
    public String visitSignature(FlowGrammarParser.SignatureContext ctx) {
        return visit(ctx.type()) + " " + ctx.Idfr().getText();
    }
}
