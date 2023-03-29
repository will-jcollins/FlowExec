package com.example.flowdemo.model.transpiler;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.expression.Modifier;
import com.example.flowdemo.model.flow.expression.Operator;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarBaseVisitor;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarParser;

import java.util.HashMap;

public class FlowGrammarAnalyser extends FlowGrammarBaseVisitor<DataType> {
    private static IdVisitor idVisitor = new IdVisitor();
    private FlowGrammarParser.DeclContext currentDecl;
    private boolean hasReturnStmt;
    private HashMap<String, FlowGrammarParser.DeclContext> functionMap = new HashMap<>();
    private HashMap<String, FlowGrammarParser.SignatureContext> variableMap = new HashMap<>();

    private static int toId(String value) {
        return Integer.parseInt(value.replaceAll("\\|", ""));
    }

    @Override
    public DataType visitProg(FlowGrammarParser.ProgContext ctx) {
        // Add function declarations to function table
        for (FlowGrammarParser.DeclContext decl : ctx.funclist) {
            FlowGrammarParser.SignatureContext signature = decl.signature(0);
            String identifier = signature.Idfr().getText();

            // Check all function identifiers are unique
            if (!functionMap.containsKey(identifier)) {
                functionMap.put(identifier, decl);
            } else {
                // Duplicate functions detected: raise exception
                throw new FlowException(identifier, -1, ErrorType.Function, "Duplicate function identifiers: " + identifier);
            }
        }

        // Analyse functions
        for (FlowGrammarParser.DeclContext decl : functionMap.values()) {
            visit(decl);
        }

        return DataType.VoidType; // Value is ignored
    }

    @Override
    public DataType visitDecl(FlowGrammarParser.DeclContext ctx) {
        // Boolean flag that tracks whether a value is returned from the function
        hasReturnStmt = false;

        // Create new variable table to track function's local variables
        variableMap = new HashMap<>();
        currentDecl = ctx;

        // Include function parameters in table
        for (FlowGrammarParser.SignatureContext signature : ctx.params) {
            String identifier = signature.Idfr().getText();
            DataType type = DataType.fromString(signature.type().getText());

            if (type == DataType.VoidType) {
                // Invalid parameter type detected: raise exception
                throw new FlowException(currentDecl.signature(0).Idfr().getText(), -1, ErrorType.Function, "Parameter: " + identifier + " has an invalid type: " + type);
            }

            // Check all parameter identifiers are unique
            if (!variableMap.containsKey(identifier)) {
                variableMap.put(identifier, signature);
            } else {
                // Duplicate parameters detected: raise exception
                throw new FlowException(currentDecl.signature(0).Idfr().getText(), -1, ErrorType.Function, "Duplicate parameter identifiers: " + identifier);
            }
        }

        // Analyse function body
        visit(ctx.block());

        DataType type = DataType.fromString(ctx.signature(0).type().getText());

        // Check that function has a return value
        if (!hasReturnStmt && type != DataType.VoidType) {
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), -1, ErrorType.Function, "Missing return value");
        } else if (hasReturnStmt && type == DataType.VoidType) {
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), -1, ErrorType.Function, "Unexpected return statement");
        }

        return DataType.VoidType; // Value is ignored
    }

    @Override
    public DataType visitBlock(FlowGrammarParser.BlockContext ctx) {

        // Visit all statements in list
        for (FlowGrammarParser.StmtContext stmt : ctx.stmtlist) {
            visit(stmt);
        }

        return DataType.VoidType; // Value is ignored
    }

    @Override
    public DataType visitDeclAssignStmt(FlowGrammarParser.DeclAssignStmtContext ctx) {
        FlowGrammarParser.SignatureContext signature = ctx.signature();
        String identifier = signature.Idfr().getText();
        DataType type = DataType.fromString(signature.type().getText());

        // Check identifier is unique
        if (!variableMap.containsKey(identifier)) {
            variableMap.put(identifier, signature);
        } else {
            // Duplicate variable identifier detected: raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Node, "Duplicate variable identifier: " + identifier);
        }

        // Check declaration type matches expression type
        if (type != visit(ctx.expr())) {
            // Type error: raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr()), ErrorType.Expr, "Expression type (" + visit(ctx.expr()) + ") does not match declaration type (" + type + ")");
        }

        return DataType.VoidType; // Dummy return value
    }

    @Override
    public DataType visitArrayAssignStmt(FlowGrammarParser.ArrayAssignStmtContext ctx) {
        String identifier = ctx.Idfr().getText();

        // Check variable has been declared
        if (!variableMap.containsKey(ctx.Idfr().getText())) {
            // Variable has not been declared: raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Node, identifier + " has not been declared");
        }

        FlowGrammarParser.SignatureContext signature = variableMap.get(identifier);
        DataType type = DataType.fromString(signature.type().getText());

        if (type != DataType.IntArrayType && type != DataType.BoolArrayType && type != DataType.CharArrayType) {
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Node, "Variable \'" + signature.Idfr().getText() + "\' is not of type: Array");
        }



        // Check declaration type matches expression type
        if (type != visit(ctx.expr())) {
            // Type error : raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr()), ErrorType.Expr, "Expression type (" + visit(ctx.expr()) + ") does not match declaration type (" + type + ")");
        }

        return DataType.VoidType; // Value is ignored
    }

    @Override
    public DataType visitAssignStmt(FlowGrammarParser.AssignStmtContext ctx) {
        String identifier = ctx.Idfr().getText();

        // Check variable has been declared
        if (!variableMap.containsKey(ctx.Idfr().getText())) {
            // Variable has not been declared: raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Node, identifier + " has not been declared");
        }

        FlowGrammarParser.SignatureContext signature = variableMap.get(identifier);
        DataType type = DataType.fromString(signature.type().getText());

        // Check declaration type matches expression type
        if (type != visit(ctx.expr())) {
            // Type error : raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr()), ErrorType.Expr, "Expression type (" + visit(ctx.expr()) + ") does not match declaration type (" + type + ")");
        }

        return DataType.VoidType; // Value is ignored
    }

    @Override
    public DataType visitOutputStmt(FlowGrammarParser.OutputStmtContext ctx) {
        return DataType.VoidType;
    }

    @Override
    public DataType visitCallStmt(FlowGrammarParser.CallStmtContext ctx) {
        String identifier = ctx.Idfr().getText();

        // Check identifier exists in function table
        if (!functionMap.containsKey(identifier)) {
            // Function has not been declared : raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), + toId(ctx.ComponentId().getText()), ErrorType.Node, "Function: " + identifier + " has not been defined");
        }

        FlowGrammarParser.DeclContext decl = functionMap.get(identifier);

        // Check number of arguments matches function declaration
        if (decl.params.size() != ctx.expr().size()) {
            // Number of arguments different to function declaration
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Node, "Number of parameters (" + ctx.expr().size() + ") does not match function declaration (" + decl.params.size() + ")");
        }

        // Check expression types match declaration types
        for (int i = 0; i < decl.params.size(); i++) {
            FlowGrammarParser.SignatureContext declArg = decl.params.get(i);
            DataType expectedType = DataType.fromString(declArg.type().getText());

            FlowGrammarParser.ExprContext argExpr = ctx.expr(i);
            DataType actualType = visit(argExpr);

            if (expectedType != actualType) {
                // Argument expression type does not match declaration
                throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(i)), ErrorType.Expr, "Expression type: " + actualType + " does not match declaration type: " + expectedType);
            }
        }

        return DataType.VoidType; // Value is ignored
    }

    @Override
    public DataType visitForStmt(FlowGrammarParser.ForStmtContext ctx) {
        FlowGrammarParser.SignatureContext signature = ctx.signature();
        String identifier = signature.Idfr().getText();
        DataType type = DataType.fromString(signature.type().getText());

        // Check identifier is unique and include it in context
        if (!variableMap.containsKey(identifier)) {
            variableMap.put(identifier, signature);
        } else {
            // Duplicate variable identifier detected: raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Node, "Duplicate for loop variable identifier: " + identifier);
        }

        // Check initial value is an integer
        DataType startType = visit(ctx.expr(0));
        if (startType != DataType.IntType) {
            // Condition expression has incorrect type : raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(0)), ErrorType.Expr , "For loop start value is not of type int, instead: " + startType.toString());
        }

        // Check initial value is an integer
        DataType endType = visit(ctx.expr(1));
        if (endType != DataType.IntType) {
            // Condition expression has incorrect type : raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(1)), ErrorType.Expr , "For loop end value is not of type int, instead: " + endType.toString());
        }

        // Analyse for loop body
        visit(ctx.block());

        variableMap.remove(identifier); // Remove for loop iterator identifier from context
        return DataType.VoidType; // Value is ignored
    }

    @Override
    public DataType visitWhileStmt(FlowGrammarParser.WhileStmtContext ctx) {
        // Check condition expression has boolean type
        if (visit(ctx.expr()) != DataType.BoolType) {
            // Condition expression has incorrect type : raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Node , "While condition expression is not of boolean type");
        }

        // Analyse while loop body
        visit(ctx.block());

        return DataType.VoidType; // Value is ignored
    }

    @Override
    public DataType visitIfStmt(FlowGrammarParser.IfStmtContext ctx) {
        // Check condition expression has boolean type
        if (visit(ctx.expr()) != DataType.BoolType) {
            // Condition expression has incorrect type : raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Node, "If / else condition expression is not of boolean type");
        }

        // Analyse if & else block
        visit(ctx.block(0));
        visit(ctx.block(1));

        return super.visitIfStmt(ctx);
    }

    @Override
    public DataType visitReturnStmt(FlowGrammarParser.ReturnStmtContext ctx) {
        hasReturnStmt = true;

        DataType returnType = visit(ctx.expr());
        DataType functionType = DataType.fromString(currentDecl.signature.type().getText());

        if (returnType != functionType) {
            // Check return type matches function type
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Node, "Return type (" + returnType + ") does not match function definition type (" + functionType + ")");
        }

        return returnType;
    }

    @Override
    public DataType visitIntExpr(FlowGrammarParser.IntExprContext ctx) {
        return DataType.IntType;
    }

    @Override
    public DataType visitCharExpr(FlowGrammarParser.CharExprContext ctx) {
        return DataType.CharType;
    }

    @Override
    public DataType visitBoolExpr(FlowGrammarParser.BoolExprContext ctx) {
        return DataType.BoolType;
    }

    @Override
    public DataType visitVarExpr(FlowGrammarParser.VarExprContext ctx) {
        String identifier = ctx.Idfr().getText();

        // Check identifier exists in variable table
        if (!variableMap.containsKey(identifier)) {
            // Identifier does not exist in variable table
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Expr, "Variable: " + identifier + " has not been declared");
        }

        FlowGrammarParser.SignatureContext signature = variableMap.get(identifier);
        return DataType.fromString(signature.type().getText());
    }

    @Override
    public DataType visitCallExpr(FlowGrammarParser.CallExprContext ctx) {
        String identifier = ctx.Idfr().getText();

        // Check identifier exists in function table
        if (!functionMap.containsKey(identifier)) {
            // Function has not been declared : raise exception
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Expr, "Functions: " + identifier + " has not been declared.");
        }

        FlowGrammarParser.DeclContext decl = functionMap.get(identifier);

        // Check number of arguments matches function declaration
        if (decl.params.size() != ctx.expr().size()) {
            // Number of arguments different to function declaration
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Expr, "Number of parameters (" + ctx.expr().size() + ") does not match function declaration (" + decl.params.size() + ")");
        }

        // Check expression types match declaration types
        for (int i = 0; i < decl.params.size(); i++) {
            FlowGrammarParser.SignatureContext declArg = decl.params.get(i);
            DataType expectedType = DataType.fromString(declArg.type().getText());

            FlowGrammarParser.ExprContext argExpr = ctx.expr(i);
            DataType actualType = visit(argExpr);

            if (expectedType != actualType) {
                // Argument expression type does not match declaration
                throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(i)), ErrorType.Expr, "Expression type: " + actualType + " does not match declaration type: " + expectedType);
            }
        }

        DataType type = DataType.fromString(decl.signature(0).type().getText());

        return type;
    }

    @Override
    public DataType visitArrayExpr(FlowGrammarParser.ArrayExprContext ctx) {
        if (ctx.expr().size() < 1) {
            throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Expr, "Array must have at least one element.");
        }

        DataType elementType = visit(ctx.expr(0));

        for (int i = 1; i < ctx.expr().size(); i++) {
            if (visit(ctx.expr(i)) != elementType) {
                throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Expr, "Array elements' type must be uniform.");
            }
        }

        DataType arrayType;

        switch (elementType) {
            case IntType:
                arrayType = DataType.IntArrayType;
                break;
            case BoolType:
                arrayType = DataType.BoolArrayType;
                break;
            case CharType:
                arrayType = DataType.CharArrayType;
                break;
            default:
                throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Expr, "Array elements' type must be Int, Bool or Char. Instead is: " + elementType.toString());
        }

        return arrayType;
    }

    @Override
    public DataType visitModifierExpr(FlowGrammarParser.ModifierExprContext ctx) {
        Modifier modifier = Modifier.fromString(ctx.mod().getText());
        DataType inputType = visit(ctx.expr());
        DataType type;

        switch (modifier) {
            case Not:

                // Check value being modified is of bool type
                if (inputType != DataType.BoolType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Expr, "NOT expr takes type: bool and was given type: " + inputType.toString());
                }

                type = DataType.BoolType;
                break;
            case Size:
                // Check input value is an array
                if (inputType != DataType.IntArrayType && inputType != DataType.BoolArrayType && inputType != DataType.CharArrayType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), toId(ctx.ComponentId().getText()), ErrorType.Expr, "SIZE expr takes type: Array and was given type: " + inputType.toString());
                }

                type = DataType.IntType;
            default:
                throw new IllegalStateException("Parse error: check modifier implementation in analyser");
        }

        return type;
    }

    @Override
    public DataType visitOpExpr(FlowGrammarParser.OpExprContext ctx) {
        Operator operator = Operator.fromString(ctx.op().getText());
        DataType type;

        // Determine type of left and right expression
        DataType leftType = visit(ctx.expr(0));
        DataType rightType = visit(ctx.expr(1));

        switch (operator) {
            case Eq:
                type = DataType.BoolType;
                break;
            case Or:
            case And:
            case Xor:
                // Check left and right expressions are of boolean type
                if (leftType != DataType.BoolType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(0)), ErrorType.Expr, "Operator expr takes type: bool and was given type: " + leftType.toString());
                }

                if (rightType != DataType.BoolType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(1)), ErrorType.Expr, "Operator expr takes type: bool and was given type: " + rightType.toString());
                }

                type = DataType.BoolType;
                break;
            case Add:
            case Sub:
            case Mult:
            case Div:
            case Mod:
                // Check left and right expressions are of int type
                if (leftType != DataType.IntType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(0)), ErrorType.Expr, "Operator expr takes type: int and was given type: " + leftType.toString());
                }

                if (rightType != DataType.IntType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(1)), ErrorType.Expr, "Operator expr takes type: int and was given type: " + rightType.toString());
                }

                type = DataType.IntType;

                break;
            case Less:
            case LessEq:
            case Greater:
            case GreaterEq:
                // Check left and right expressions are of int type
                if (leftType != DataType.IntType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(0)), ErrorType.Expr, "Operator expr takes type: int and was given type: " + leftType.toString());
                }

                if (rightType != DataType.IntType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(1)), ErrorType.Expr, "Operator expr takes type: int and was given type: " + rightType.toString());
                }

                type = DataType.BoolType;
                break;
            case Index:
                // Check left expression is an array
                if (leftType != DataType.IntArrayType || leftType != DataType.BoolArrayType || leftType != DataType.CharArrayType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(0)), ErrorType.Expr, "Operator expr takes type: Array and was given type: " + leftType.toString());
                }
                if (rightType != DataType.IntType) {
                    throw new FlowException(currentDecl.signature(0).Idfr().getText(), idVisitor.visit(ctx.expr(0)), ErrorType.Expr, "Operator expr takes type: Int and was given type: " + rightType.toString());
                }

                switch (leftType) {
                    case IntArrayType:
                        type = DataType.IntType;
                        break;
                    case BoolArrayType:
                        type = DataType.BoolType;
                        break;
                    case CharArrayType:
                        type = DataType.CharType;
                        break;
                    default:
                        throw new IllegalStateException("Impossible state reached: check operator implementation in analyser");
                }
                break;
            default:
                throw new IllegalStateException("Parse error: check operator implementation in analyser");
        }

        return type;
    }

    @Override
    public DataType visitOp(FlowGrammarParser.OpContext ctx) {
        throw new IllegalStateException("Parse error: check operator implementation in analyser");
    }

    @Override
    public DataType visitMod(FlowGrammarParser.ModContext ctx) {
        throw new IllegalStateException("Parse error: check modifier implementation in analyser");
    }

    @Override
    public DataType visitType(FlowGrammarParser.TypeContext ctx) {
        throw new IllegalStateException("Parse error: check type implementation in analyser");
    }

    @Override
    public DataType visitSignature(FlowGrammarParser.SignatureContext ctx) {
        throw new IllegalStateException("Parse error: check signature implementation in analyser");
    }
}
