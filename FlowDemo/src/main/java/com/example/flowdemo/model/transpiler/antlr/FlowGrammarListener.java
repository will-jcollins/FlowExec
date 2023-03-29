// Generated from java-escape by ANTLR 4.11.1
package com.example.flowdemo.model.transpiler.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FlowGrammarParser}.
 */
public interface FlowGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FlowGrammarParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(FlowGrammarParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowGrammarParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(FlowGrammarParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowGrammarParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(FlowGrammarParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowGrammarParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(FlowGrammarParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowGrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(FlowGrammarParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowGrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(FlowGrammarParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DeclAssignStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterDeclAssignStmt(FlowGrammarParser.DeclAssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DeclAssignStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitDeclAssignStmt(FlowGrammarParser.DeclAssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(FlowGrammarParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(FlowGrammarParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OutputStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterOutputStmt(FlowGrammarParser.OutputStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OutputStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitOutputStmt(FlowGrammarParser.OutputStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterCallStmt(FlowGrammarParser.CallStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitCallStmt(FlowGrammarParser.CallStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(FlowGrammarParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(FlowGrammarParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(FlowGrammarParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(FlowGrammarParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(FlowGrammarParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(FlowGrammarParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReturnStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(FlowGrammarParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReturnStmt}
	 * labeled alternative in {@link FlowGrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(FlowGrammarParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIntExpr(FlowGrammarParser.IntExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIntExpr(FlowGrammarParser.IntExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CharExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCharExpr(FlowGrammarParser.CharExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CharExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCharExpr(FlowGrammarParser.CharExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(FlowGrammarParser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(FlowGrammarParser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVarExpr(FlowGrammarParser.VarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVarExpr(FlowGrammarParser.VarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCallExpr(FlowGrammarParser.CallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCallExpr(FlowGrammarParser.CallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ModifierExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterModifierExpr(FlowGrammarParser.ModifierExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ModifierExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitModifierExpr(FlowGrammarParser.ModifierExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OpExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOpExpr(FlowGrammarParser.OpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OpExpr}
	 * labeled alternative in {@link FlowGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOpExpr(FlowGrammarParser.OpExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowGrammarParser#op}.
	 * @param ctx the parse tree
	 */
	void enterOp(FlowGrammarParser.OpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowGrammarParser#op}.
	 * @param ctx the parse tree
	 */
	void exitOp(FlowGrammarParser.OpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowGrammarParser#mod}.
	 * @param ctx the parse tree
	 */
	void enterMod(FlowGrammarParser.ModContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowGrammarParser#mod}.
	 * @param ctx the parse tree
	 */
	void exitMod(FlowGrammarParser.ModContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(FlowGrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(FlowGrammarParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowGrammarParser#signature}.
	 * @param ctx the parse tree
	 */
	void enterSignature(FlowGrammarParser.SignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowGrammarParser#signature}.
	 * @param ctx the parse tree
	 */
	void exitSignature(FlowGrammarParser.SignatureContext ctx);
}