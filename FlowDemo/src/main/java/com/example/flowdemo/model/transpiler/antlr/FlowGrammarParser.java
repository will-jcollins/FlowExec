// Generated from java-escape by ANTLR 4.11.1
package com.example.flowdemo.model.transpiler.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class FlowGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, Comment=2, Assign=3, While=4, For=5, If=6, Else=7, Output=8, Return=9, 
		IntArrayType=10, BoolArrayType=11, CharArrayType=12, IntType=13, CharType=14, 
		BoolType=15, VoidType=16, LParen=17, RParen=18, Comma=19, LBrace=20, RBrace=21, 
		Semicolon=22, LCrotchet=23, RCrotchet=24, Plus=25, Sub=26, Div=27, Mult=28, 
		Mod=29, Less=30, LessEq=31, Grtr=32, GrtrEq=33, Eq=34, And=35, Or=36, 
		Xor=37, Not=38, Index=39, Size=40, Idfr=41, ComponentId=42, IntLit=43, 
		IndexNum=44, CharLit=45, BoolLit=46;
	public static final int
		RULE_prog = 0, RULE_decl = 1, RULE_block = 2, RULE_stmt = 3, RULE_expr = 4, 
		RULE_op = 5, RULE_mod = 6, RULE_type = 7, RULE_signature = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "decl", "block", "stmt", "expr", "op", "mod", "type", "signature"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'='", "'While'", "'For'", "'If'", "'Else'", "'Output'", 
			"'Return'", "'IntArray'", "'BoolArray'", "'CharArray'", "'Int'", "'Char'", 
			"'Bool'", "'Void'", "'('", "')'", "','", "'{'", "'}'", "';'", "'['", 
			"']'", "'+'", "'-'", "'/'", "'*'", "'%'", "'<'", "'<='", "'>'", "'>='", 
			"'EQUALS'", "'AND'", "'OR'", "'XOR'", "'NOT'", "'INDEX'", "'SIZE'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "Comment", "Assign", "While", "For", "If", "Else", "Output", 
			"Return", "IntArrayType", "BoolArrayType", "CharArrayType", "IntType", 
			"CharType", "BoolType", "VoidType", "LParen", "RParen", "Comma", "LBrace", 
			"RBrace", "Semicolon", "LCrotchet", "RCrotchet", "Plus", "Sub", "Div", 
			"Mult", "Mod", "Less", "LessEq", "Grtr", "GrtrEq", "Eq", "And", "Or", 
			"Xor", "Not", "Index", "Size", "Idfr", "ComponentId", "IntLit", "IndexNum", 
			"CharLit", "BoolLit"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FlowGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public DeclContext decl;
		public List<DeclContext> funclist = new ArrayList<DeclContext>();
		public TerminalNode EOF() { return getToken(FlowGrammarParser.EOF, 0); }
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 130048L) != 0) {
				{
				{
				setState(18);
				((ProgContext)_localctx).decl = decl();
				((ProgContext)_localctx).funclist.add(((ProgContext)_localctx).decl);
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(24);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclContext extends ParserRuleContext {
		public SignatureContext signature;
		public List<SignatureContext> params = new ArrayList<SignatureContext>();
		public List<SignatureContext> signature() {
			return getRuleContexts(SignatureContext.class);
		}
		public SignatureContext signature(int i) {
			return getRuleContext(SignatureContext.class,i);
		}
		public TerminalNode LParen() { return getToken(FlowGrammarParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(FlowGrammarParser.RParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> Comma() { return getTokens(FlowGrammarParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(FlowGrammarParser.Comma, i);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			signature();
			setState(27);
			match(LParen);
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 130048L) != 0) {
				{
				setState(28);
				((DeclContext)_localctx).signature = signature();
				((DeclContext)_localctx).params.add(((DeclContext)_localctx).signature);
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Comma) {
					{
					{
					setState(29);
					match(Comma);
					setState(30);
					((DeclContext)_localctx).signature = signature();
					((DeclContext)_localctx).params.add(((DeclContext)_localctx).signature);
					}
					}
					setState(35);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(38);
			match(RParen);
			setState(39);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public StmtContext stmt;
		public List<StmtContext> stmtlist = new ArrayList<StmtContext>();
		public TerminalNode LBrace() { return getToken(FlowGrammarParser.LBrace, 0); }
		public TerminalNode RBrace() { return getToken(FlowGrammarParser.RBrace, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(LBrace);
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ComponentId) {
				{
				{
				setState(42);
				((BlockContext)_localctx).stmt = stmt();
				((BlockContext)_localctx).stmtlist.add(((BlockContext)_localctx).stmt);
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
			match(RBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StmtContext extends ParserRuleContext {
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	 
		public StmtContext() { }
		public void copyFrom(StmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfStmtContext extends StmtContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode If() { return getToken(FlowGrammarParser.If, 0); }
		public TerminalNode LParen() { return getToken(FlowGrammarParser.LParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RParen() { return getToken(FlowGrammarParser.RParen, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode Else() { return getToken(FlowGrammarParser.Else, 0); }
		public IfStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitIfStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhileStmtContext extends StmtContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode While() { return getToken(FlowGrammarParser.While, 0); }
		public TerminalNode LParen() { return getToken(FlowGrammarParser.LParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RParen() { return getToken(FlowGrammarParser.RParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitWhileStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignStmtContext extends StmtContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode Idfr() { return getToken(FlowGrammarParser.Idfr, 0); }
		public TerminalNode Assign() { return getToken(FlowGrammarParser.Assign, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Semicolon() { return getToken(FlowGrammarParser.Semicolon, 0); }
		public AssignStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitAssignStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallStmtContext extends StmtContext {
		public ExprContext expr;
		public List<ExprContext> params = new ArrayList<ExprContext>();
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode Idfr() { return getToken(FlowGrammarParser.Idfr, 0); }
		public TerminalNode LParen() { return getToken(FlowGrammarParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(FlowGrammarParser.RParen, 0); }
		public TerminalNode Semicolon() { return getToken(FlowGrammarParser.Semicolon, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(FlowGrammarParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(FlowGrammarParser.Comma, i);
		}
		public CallStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterCallStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitCallStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitCallStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclAssignStmtContext extends StmtContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public TerminalNode Assign() { return getToken(FlowGrammarParser.Assign, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Semicolon() { return getToken(FlowGrammarParser.Semicolon, 0); }
		public DeclAssignStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterDeclAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitDeclAssignStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitDeclAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayAssignStmtContext extends StmtContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode Idfr() { return getToken(FlowGrammarParser.Idfr, 0); }
		public TerminalNode LCrotchet() { return getToken(FlowGrammarParser.LCrotchet, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RCrotchet() { return getToken(FlowGrammarParser.RCrotchet, 0); }
		public TerminalNode Assign() { return getToken(FlowGrammarParser.Assign, 0); }
		public TerminalNode Semicolon() { return getToken(FlowGrammarParser.Semicolon, 0); }
		public ArrayAssignStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterArrayAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitArrayAssignStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitArrayAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OutputStmtContext extends StmtContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode Output() { return getToken(FlowGrammarParser.Output, 0); }
		public TerminalNode LParen() { return getToken(FlowGrammarParser.LParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RParen() { return getToken(FlowGrammarParser.RParen, 0); }
		public TerminalNode Semicolon() { return getToken(FlowGrammarParser.Semicolon, 0); }
		public OutputStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterOutputStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitOutputStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitOutputStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForStmtContext extends StmtContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode For() { return getToken(FlowGrammarParser.For, 0); }
		public TerminalNode LParen() { return getToken(FlowGrammarParser.LParen, 0); }
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public TerminalNode Assign() { return getToken(FlowGrammarParser.Assign, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode Comma() { return getToken(FlowGrammarParser.Comma, 0); }
		public TerminalNode RParen() { return getToken(FlowGrammarParser.RParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterForStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitForStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitForStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStmtContext extends StmtContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode Return() { return getToken(FlowGrammarParser.Return, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterReturnStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitReturnStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_stmt);
		int _la;
		try {
			setState(123);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new DeclAssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				match(ComponentId);
				setState(51);
				signature();
				setState(52);
				match(Assign);
				setState(53);
				expr();
				setState(54);
				match(Semicolon);
				}
				break;
			case 2:
				_localctx = new ArrayAssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				match(ComponentId);
				setState(57);
				match(Idfr);
				setState(58);
				match(LCrotchet);
				setState(59);
				expr();
				setState(60);
				match(RCrotchet);
				setState(61);
				match(Assign);
				setState(62);
				expr();
				setState(63);
				match(Semicolon);
				}
				break;
			case 3:
				_localctx = new AssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(65);
				match(ComponentId);
				setState(66);
				match(Idfr);
				setState(67);
				match(Assign);
				setState(68);
				expr();
				setState(69);
				match(Semicolon);
				}
				break;
			case 4:
				_localctx = new OutputStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(71);
				match(ComponentId);
				setState(72);
				match(Output);
				setState(73);
				match(LParen);
				setState(74);
				expr();
				setState(75);
				match(RParen);
				setState(76);
				match(Semicolon);
				}
				break;
			case 5:
				_localctx = new CallStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(78);
				match(ComponentId);
				setState(79);
				match(Idfr);
				setState(80);
				match(LParen);
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ComponentId) {
					{
					setState(81);
					((CallStmtContext)_localctx).expr = expr();
					((CallStmtContext)_localctx).params.add(((CallStmtContext)_localctx).expr);
					setState(86);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==Comma) {
						{
						{
						setState(82);
						match(Comma);
						setState(83);
						((CallStmtContext)_localctx).expr = expr();
						((CallStmtContext)_localctx).params.add(((CallStmtContext)_localctx).expr);
						}
						}
						setState(88);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(91);
				match(RParen);
				setState(92);
				match(Semicolon);
				}
				break;
			case 6:
				_localctx = new ForStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(93);
				match(ComponentId);
				setState(94);
				match(For);
				setState(95);
				match(LParen);
				setState(96);
				signature();
				setState(97);
				match(Assign);
				setState(98);
				expr();
				setState(99);
				match(Comma);
				setState(100);
				expr();
				setState(101);
				match(RParen);
				setState(102);
				block();
				}
				break;
			case 7:
				_localctx = new WhileStmtContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(104);
				match(ComponentId);
				setState(105);
				match(While);
				setState(106);
				match(LParen);
				setState(107);
				expr();
				setState(108);
				match(RParen);
				setState(109);
				block();
				}
				break;
			case 8:
				_localctx = new IfStmtContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(111);
				match(ComponentId);
				setState(112);
				match(If);
				setState(113);
				match(LParen);
				setState(114);
				expr();
				setState(115);
				match(RParen);
				setState(116);
				block();
				setState(117);
				match(Else);
				setState(118);
				block();
				}
				break;
			case 9:
				_localctx = new ReturnStmtContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(120);
				match(ComponentId);
				setState(121);
				match(Return);
				setState(122);
				expr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolExprContext extends ExprContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode BoolLit() { return getToken(FlowGrammarParser.BoolLit, 0); }
		public BoolExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitBoolExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitBoolExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayExprContext extends ExprContext {
		public ExprContext expr;
		public List<ExprContext> elems = new ArrayList<ExprContext>();
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode LCrotchet() { return getToken(FlowGrammarParser.LCrotchet, 0); }
		public TerminalNode RCrotchet() { return getToken(FlowGrammarParser.RCrotchet, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(FlowGrammarParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(FlowGrammarParser.Comma, i);
		}
		public ArrayExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterArrayExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitArrayExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitArrayExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VarExprContext extends ExprContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode Idfr() { return getToken(FlowGrammarParser.Idfr, 0); }
		public VarExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterVarExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitVarExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitVarExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallExprContext extends ExprContext {
		public ExprContext expr;
		public List<ExprContext> params = new ArrayList<ExprContext>();
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode Idfr() { return getToken(FlowGrammarParser.Idfr, 0); }
		public TerminalNode LParen() { return getToken(FlowGrammarParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(FlowGrammarParser.RParen, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(FlowGrammarParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(FlowGrammarParser.Comma, i);
		}
		public CallExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterCallExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitCallExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitCallExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntExprContext extends ExprContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode IntLit() { return getToken(FlowGrammarParser.IntLit, 0); }
		public IntExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterIntExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitIntExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitIntExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OpExprContext extends ExprContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public OpContext op() {
			return getRuleContext(OpContext.class,0);
		}
		public OpExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterOpExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitOpExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitOpExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ModifierExprContext extends ExprContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public ModContext mod() {
			return getRuleContext(ModContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ModifierExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterModifierExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitModifierExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitModifierExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CharExprContext extends ExprContext {
		public TerminalNode ComponentId() { return getToken(FlowGrammarParser.ComponentId, 0); }
		public TerminalNode CharLit() { return getToken(FlowGrammarParser.CharLit, 0); }
		public CharExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterCharExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitCharExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitCharExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expr);
		int _la;
		try {
			setState(169);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new IntExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(125);
				match(ComponentId);
				setState(126);
				match(IntLit);
				}
				break;
			case 2:
				_localctx = new CharExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(127);
				match(ComponentId);
				setState(128);
				match(CharLit);
				}
				break;
			case 3:
				_localctx = new BoolExprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				match(ComponentId);
				setState(130);
				match(BoolLit);
				}
				break;
			case 4:
				_localctx = new VarExprContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(131);
				match(ComponentId);
				setState(132);
				match(Idfr);
				}
				break;
			case 5:
				_localctx = new CallExprContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(133);
				match(ComponentId);
				setState(134);
				match(Idfr);
				setState(135);
				match(LParen);
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ComponentId) {
					{
					setState(136);
					((CallExprContext)_localctx).expr = expr();
					((CallExprContext)_localctx).params.add(((CallExprContext)_localctx).expr);
					setState(141);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==Comma) {
						{
						{
						setState(137);
						match(Comma);
						setState(138);
						((CallExprContext)_localctx).expr = expr();
						((CallExprContext)_localctx).params.add(((CallExprContext)_localctx).expr);
						}
						}
						setState(143);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(146);
				match(RParen);
				}
				break;
			case 6:
				_localctx = new ModifierExprContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(147);
				match(ComponentId);
				setState(148);
				mod();
				setState(149);
				expr();
				}
				break;
			case 7:
				_localctx = new OpExprContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(151);
				match(ComponentId);
				setState(152);
				expr();
				setState(153);
				op();
				setState(154);
				expr();
				}
				break;
			case 8:
				_localctx = new ArrayExprContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(156);
				match(ComponentId);
				setState(157);
				match(LCrotchet);
				setState(166);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ComponentId) {
					{
					setState(158);
					((ArrayExprContext)_localctx).expr = expr();
					((ArrayExprContext)_localctx).elems.add(((ArrayExprContext)_localctx).expr);
					setState(163);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==Comma) {
						{
						{
						setState(159);
						match(Comma);
						setState(160);
						((ArrayExprContext)_localctx).expr = expr();
						((ArrayExprContext)_localctx).elems.add(((ArrayExprContext)_localctx).expr);
						}
						}
						setState(165);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(168);
				match(RCrotchet);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OpContext extends ParserRuleContext {
		public TerminalNode Plus() { return getToken(FlowGrammarParser.Plus, 0); }
		public TerminalNode Sub() { return getToken(FlowGrammarParser.Sub, 0); }
		public TerminalNode Mult() { return getToken(FlowGrammarParser.Mult, 0); }
		public TerminalNode Div() { return getToken(FlowGrammarParser.Div, 0); }
		public TerminalNode Mod() { return getToken(FlowGrammarParser.Mod, 0); }
		public TerminalNode Eq() { return getToken(FlowGrammarParser.Eq, 0); }
		public TerminalNode Less() { return getToken(FlowGrammarParser.Less, 0); }
		public TerminalNode LessEq() { return getToken(FlowGrammarParser.LessEq, 0); }
		public TerminalNode Grtr() { return getToken(FlowGrammarParser.Grtr, 0); }
		public TerminalNode GrtrEq() { return getToken(FlowGrammarParser.GrtrEq, 0); }
		public TerminalNode And() { return getToken(FlowGrammarParser.And, 0); }
		public TerminalNode Or() { return getToken(FlowGrammarParser.Or, 0); }
		public TerminalNode Xor() { return getToken(FlowGrammarParser.Xor, 0); }
		public TerminalNode Index() { return getToken(FlowGrammarParser.Index, 0); }
		public OpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpContext op() throws RecognitionException {
		OpContext _localctx = new OpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			_la = _input.LA(1);
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 824600166400L) != 0) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ModContext extends ParserRuleContext {
		public TerminalNode Not() { return getToken(FlowGrammarParser.Not, 0); }
		public TerminalNode Size() { return getToken(FlowGrammarParser.Size, 0); }
		public ModContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterMod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitMod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitMod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModContext mod() throws RecognitionException {
		ModContext _localctx = new ModContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_mod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			_la = _input.LA(1);
			if ( !(_la==Not || _la==Size) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode IntArrayType() { return getToken(FlowGrammarParser.IntArrayType, 0); }
		public TerminalNode CharArrayType() { return getToken(FlowGrammarParser.CharArrayType, 0); }
		public TerminalNode BoolArrayType() { return getToken(FlowGrammarParser.BoolArrayType, 0); }
		public TerminalNode IntType() { return getToken(FlowGrammarParser.IntType, 0); }
		public TerminalNode CharType() { return getToken(FlowGrammarParser.CharType, 0); }
		public TerminalNode BoolType() { return getToken(FlowGrammarParser.BoolType, 0); }
		public TerminalNode VoidType() { return getToken(FlowGrammarParser.VoidType, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			_la = _input.LA(1);
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 130048L) != 0) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SignatureContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Idfr() { return getToken(FlowGrammarParser.Idfr, 0); }
		public SignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_signature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).enterSignature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlowGrammarListener ) ((FlowGrammarListener)listener).exitSignature(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlowGrammarVisitor ) return ((FlowGrammarVisitor<? extends T>)visitor).visitSignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignatureContext signature() throws RecognitionException {
		SignatureContext _localctx = new SignatureContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_signature);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			type();
			setState(178);
			match(Idfr);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001.\u00b5\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0005\u0000\u0014\b\u0000\n\u0000\f\u0000\u0017"+
		"\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0005\u0001 \b\u0001\n\u0001\f\u0001#\t\u0001\u0003"+
		"\u0001%\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001"+
		"\u0002\u0005\u0002,\b\u0002\n\u0002\f\u0002/\t\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003U\b"+
		"\u0003\n\u0003\f\u0003X\t\u0003\u0003\u0003Z\b\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0003\u0003|\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u008c"+
		"\b\u0004\n\u0004\f\u0004\u008f\t\u0004\u0003\u0004\u0091\b\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u00a2\b\u0004\n\u0004\f\u0004"+
		"\u00a5\t\u0004\u0003\u0004\u00a7\b\u0004\u0001\u0004\u0003\u0004\u00aa"+
		"\b\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0000\u0000\t\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0000\u0003\u0002\u0000\u0019%\'\'\u0002\u0000"+
		"&&((\u0001\u0000\n\u0010\u00c4\u0000\u0015\u0001\u0000\u0000\u0000\u0002"+
		"\u001a\u0001\u0000\u0000\u0000\u0004)\u0001\u0000\u0000\u0000\u0006{\u0001"+
		"\u0000\u0000\u0000\b\u00a9\u0001\u0000\u0000\u0000\n\u00ab\u0001\u0000"+
		"\u0000\u0000\f\u00ad\u0001\u0000\u0000\u0000\u000e\u00af\u0001\u0000\u0000"+
		"\u0000\u0010\u00b1\u0001\u0000\u0000\u0000\u0012\u0014\u0003\u0002\u0001"+
		"\u0000\u0013\u0012\u0001\u0000\u0000\u0000\u0014\u0017\u0001\u0000\u0000"+
		"\u0000\u0015\u0013\u0001\u0000\u0000\u0000\u0015\u0016\u0001\u0000\u0000"+
		"\u0000\u0016\u0018\u0001\u0000\u0000\u0000\u0017\u0015\u0001\u0000\u0000"+
		"\u0000\u0018\u0019\u0005\u0000\u0000\u0001\u0019\u0001\u0001\u0000\u0000"+
		"\u0000\u001a\u001b\u0003\u0010\b\u0000\u001b$\u0005\u0011\u0000\u0000"+
		"\u001c!\u0003\u0010\b\u0000\u001d\u001e\u0005\u0013\u0000\u0000\u001e"+
		" \u0003\u0010\b\u0000\u001f\u001d\u0001\u0000\u0000\u0000 #\u0001\u0000"+
		"\u0000\u0000!\u001f\u0001\u0000\u0000\u0000!\"\u0001\u0000\u0000\u0000"+
		"\"%\u0001\u0000\u0000\u0000#!\u0001\u0000\u0000\u0000$\u001c\u0001\u0000"+
		"\u0000\u0000$%\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&\'\u0005"+
		"\u0012\u0000\u0000\'(\u0003\u0004\u0002\u0000(\u0003\u0001\u0000\u0000"+
		"\u0000)-\u0005\u0014\u0000\u0000*,\u0003\u0006\u0003\u0000+*\u0001\u0000"+
		"\u0000\u0000,/\u0001\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000-.\u0001"+
		"\u0000\u0000\u0000.0\u0001\u0000\u0000\u0000/-\u0001\u0000\u0000\u0000"+
		"01\u0005\u0015\u0000\u00001\u0005\u0001\u0000\u0000\u000023\u0005*\u0000"+
		"\u000034\u0003\u0010\b\u000045\u0005\u0003\u0000\u000056\u0003\b\u0004"+
		"\u000067\u0005\u0016\u0000\u00007|\u0001\u0000\u0000\u000089\u0005*\u0000"+
		"\u00009:\u0005)\u0000\u0000:;\u0005\u0017\u0000\u0000;<\u0003\b\u0004"+
		"\u0000<=\u0005\u0018\u0000\u0000=>\u0005\u0003\u0000\u0000>?\u0003\b\u0004"+
		"\u0000?@\u0005\u0016\u0000\u0000@|\u0001\u0000\u0000\u0000AB\u0005*\u0000"+
		"\u0000BC\u0005)\u0000\u0000CD\u0005\u0003\u0000\u0000DE\u0003\b\u0004"+
		"\u0000EF\u0005\u0016\u0000\u0000F|\u0001\u0000\u0000\u0000GH\u0005*\u0000"+
		"\u0000HI\u0005\b\u0000\u0000IJ\u0005\u0011\u0000\u0000JK\u0003\b\u0004"+
		"\u0000KL\u0005\u0012\u0000\u0000LM\u0005\u0016\u0000\u0000M|\u0001\u0000"+
		"\u0000\u0000NO\u0005*\u0000\u0000OP\u0005)\u0000\u0000PY\u0005\u0011\u0000"+
		"\u0000QV\u0003\b\u0004\u0000RS\u0005\u0013\u0000\u0000SU\u0003\b\u0004"+
		"\u0000TR\u0001\u0000\u0000\u0000UX\u0001\u0000\u0000\u0000VT\u0001\u0000"+
		"\u0000\u0000VW\u0001\u0000\u0000\u0000WZ\u0001\u0000\u0000\u0000XV\u0001"+
		"\u0000\u0000\u0000YQ\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000"+
		"Z[\u0001\u0000\u0000\u0000[\\\u0005\u0012\u0000\u0000\\|\u0005\u0016\u0000"+
		"\u0000]^\u0005*\u0000\u0000^_\u0005\u0005\u0000\u0000_`\u0005\u0011\u0000"+
		"\u0000`a\u0003\u0010\b\u0000ab\u0005\u0003\u0000\u0000bc\u0003\b\u0004"+
		"\u0000cd\u0005\u0013\u0000\u0000de\u0003\b\u0004\u0000ef\u0005\u0012\u0000"+
		"\u0000fg\u0003\u0004\u0002\u0000g|\u0001\u0000\u0000\u0000hi\u0005*\u0000"+
		"\u0000ij\u0005\u0004\u0000\u0000jk\u0005\u0011\u0000\u0000kl\u0003\b\u0004"+
		"\u0000lm\u0005\u0012\u0000\u0000mn\u0003\u0004\u0002\u0000n|\u0001\u0000"+
		"\u0000\u0000op\u0005*\u0000\u0000pq\u0005\u0006\u0000\u0000qr\u0005\u0011"+
		"\u0000\u0000rs\u0003\b\u0004\u0000st\u0005\u0012\u0000\u0000tu\u0003\u0004"+
		"\u0002\u0000uv\u0005\u0007\u0000\u0000vw\u0003\u0004\u0002\u0000w|\u0001"+
		"\u0000\u0000\u0000xy\u0005*\u0000\u0000yz\u0005\t\u0000\u0000z|\u0003"+
		"\b\u0004\u0000{2\u0001\u0000\u0000\u0000{8\u0001\u0000\u0000\u0000{A\u0001"+
		"\u0000\u0000\u0000{G\u0001\u0000\u0000\u0000{N\u0001\u0000\u0000\u0000"+
		"{]\u0001\u0000\u0000\u0000{h\u0001\u0000\u0000\u0000{o\u0001\u0000\u0000"+
		"\u0000{x\u0001\u0000\u0000\u0000|\u0007\u0001\u0000\u0000\u0000}~\u0005"+
		"*\u0000\u0000~\u00aa\u0005+\u0000\u0000\u007f\u0080\u0005*\u0000\u0000"+
		"\u0080\u00aa\u0005-\u0000\u0000\u0081\u0082\u0005*\u0000\u0000\u0082\u00aa"+
		"\u0005.\u0000\u0000\u0083\u0084\u0005*\u0000\u0000\u0084\u00aa\u0005)"+
		"\u0000\u0000\u0085\u0086\u0005*\u0000\u0000\u0086\u0087\u0005)\u0000\u0000"+
		"\u0087\u0090\u0005\u0011\u0000\u0000\u0088\u008d\u0003\b\u0004\u0000\u0089"+
		"\u008a\u0005\u0013\u0000\u0000\u008a\u008c\u0003\b\u0004\u0000\u008b\u0089"+
		"\u0001\u0000\u0000\u0000\u008c\u008f\u0001\u0000\u0000\u0000\u008d\u008b"+
		"\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e\u0091"+
		"\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u0090\u0088"+
		"\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000\u0091\u0092"+
		"\u0001\u0000\u0000\u0000\u0092\u00aa\u0005\u0012\u0000\u0000\u0093\u0094"+
		"\u0005*\u0000\u0000\u0094\u0095\u0003\f\u0006\u0000\u0095\u0096\u0003"+
		"\b\u0004\u0000\u0096\u00aa\u0001\u0000\u0000\u0000\u0097\u0098\u0005*"+
		"\u0000\u0000\u0098\u0099\u0003\b\u0004\u0000\u0099\u009a\u0003\n\u0005"+
		"\u0000\u009a\u009b\u0003\b\u0004\u0000\u009b\u00aa\u0001\u0000\u0000\u0000"+
		"\u009c\u009d\u0005*\u0000\u0000\u009d\u00a6\u0005\u0017\u0000\u0000\u009e"+
		"\u00a3\u0003\b\u0004\u0000\u009f\u00a0\u0005\u0013\u0000\u0000\u00a0\u00a2"+
		"\u0003\b\u0004\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a2\u00a5\u0001"+
		"\u0000\u0000\u0000\u00a3\u00a1\u0001\u0000\u0000\u0000\u00a3\u00a4\u0001"+
		"\u0000\u0000\u0000\u00a4\u00a7\u0001\u0000\u0000\u0000\u00a5\u00a3\u0001"+
		"\u0000\u0000\u0000\u00a6\u009e\u0001\u0000\u0000\u0000\u00a6\u00a7\u0001"+
		"\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8\u00aa\u0005"+
		"\u0018\u0000\u0000\u00a9}\u0001\u0000\u0000\u0000\u00a9\u007f\u0001\u0000"+
		"\u0000\u0000\u00a9\u0081\u0001\u0000\u0000\u0000\u00a9\u0083\u0001\u0000"+
		"\u0000\u0000\u00a9\u0085\u0001\u0000\u0000\u0000\u00a9\u0093\u0001\u0000"+
		"\u0000\u0000\u00a9\u0097\u0001\u0000\u0000\u0000\u00a9\u009c\u0001\u0000"+
		"\u0000\u0000\u00aa\t\u0001\u0000\u0000\u0000\u00ab\u00ac\u0007\u0000\u0000"+
		"\u0000\u00ac\u000b\u0001\u0000\u0000\u0000\u00ad\u00ae\u0007\u0001\u0000"+
		"\u0000\u00ae\r\u0001\u0000\u0000\u0000\u00af\u00b0\u0007\u0002\u0000\u0000"+
		"\u00b0\u000f\u0001\u0000\u0000\u0000\u00b1\u00b2\u0003\u000e\u0007\u0000"+
		"\u00b2\u00b3\u0005)\u0000\u0000\u00b3\u0011\u0001\u0000\u0000\u0000\f"+
		"\u0015!$-VY{\u008d\u0090\u00a3\u00a6\u00a9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}