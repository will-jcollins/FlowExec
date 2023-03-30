// Generated from java-escape by ANTLR 4.11.1
package com.example.flowdemo.model.transpiler.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FlowGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FlowGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FlowGrammarParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(FlowGrammarParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowGrammarParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(FlowGrammarParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowGrammarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(FlowGrammarParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DeclAssignStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclAssignStmt(FlowGrammarParser.DeclAssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayAssignStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAssignStmt(FlowGrammarParser.ArrayAssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(FlowGrammarParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OutputStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutputStmt(FlowGrammarParser.OutputStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStmt(FlowGrammarParser.CallStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(FlowGrammarParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(FlowGrammarParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(FlowGrammarParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(FlowGrammarParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntExpr(FlowGrammarParser.IntExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CharExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharExpr(FlowGrammarParser.CharExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(FlowGrammarParser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarExpr(FlowGrammarParser.VarExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(FlowGrammarParser.CallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ModifierExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifierExpr(FlowGrammarParser.ModifierExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OpExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpExpr(FlowGrammarParser.OpExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(FlowGrammarParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowGrammarParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp(FlowGrammarParser.OpContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowGrammarParser#mod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMod(FlowGrammarParser.ModContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowGrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(FlowGrammarParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowGrammarParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignature(FlowGrammarParser.SignatureContext ctx);
}