grammar FlowGrammar;

// ---- Context-Free Grammar ----

// Function declaration
prog            : (funclist+=decl)* EOF;
decl            : signature LParen (params+=signature (Comma params+=signature)*)? RParen block;
block           : LBrace (stmtlist+=stmt)* RBrace;

// Statements
stmt        : ComponentId signature Assign expr Semicolon                                       #DeclAssignStmt
            | ComponentId Idfr LCrotchet expr RCrotchet Assign expr Semicolon                   #ArrayAssignStmt
            | ComponentId Idfr Assign expr Semicolon                                            #AssignStmt
            | ComponentId Output LParen expr RParen Semicolon                                   #OutputStmt
            | ComponentId Idfr LParen (params+=expr (Comma params+=expr)*)? RParen Semicolon    #CallStmt
            | ComponentId For LParen signature Assign expr Comma expr RParen block              #ForStmt
            | ComponentId While LParen expr RParen block                                        #WhileStmt
            | ComponentId If LParen expr RParen block Else block                                #IfStmt
            | ComponentId Return expr                                                           #ReturnStmt;

// Expressions
expr        : ComponentId IntLit                                                        #IntExpr
            | ComponentId CharLit                                                       #CharExpr
            | ComponentId BoolLit                                                       #BoolExpr
            | ComponentId Idfr                                                          #VarExpr
            | ComponentId Idfr LParen (params+=expr (Comma params+=expr)*)? RParen      #CallExpr
            | ComponentId mod expr                                                      #ModifierExpr
            | ComponentId expr op expr                                                  #OpExpr
            | ComponentId LCrotchet (elems+=expr (Comma elems+=expr)*)? RCrotchet       #ArrayExpr;

op          : Plus | Sub | Mult | Div | Mod | Eq | Less | LessEq | Grtr | GrtrEq | And | Or | Xor | Index;
mod         : Not | Size;

// Types
type        : IntArrayType | CharArrayType | BoolArrayType | IntType | CharType | BoolType | VoidType;
signature   : type Idfr;

// ---- Lexer Grammar ----
WS      : [ \n\r\t]+ -> skip; // Skip whitespace
Comment : '//' ~[\r\n]* -> skip;
Assign  : '=';

// Control flow
While   : 'While';
For     : 'For';
If      : 'If';
Else    : 'Else';
Output  : 'Output';
Return  : 'Return';

// Type tokens
IntArrayType   : 'IntArray';
BoolArrayType   : 'BoolArray';
CharArrayType   : 'CharArray';
IntType     : 'Int';
CharType    : 'Char';
BoolType    : 'Bool';
VoidType    : 'Void';

// Delimiter tokens
LParen      : '(';
RParen      : ')';
Comma       : ',';

LBrace      : '{';
RBrace      : '}';
Semicolon   : ';';

LCrotchet   : '[';
RCrotchet   : ']';

// Operator Tokens

// Int -> Int Operators
Plus        : '+';
Sub         : '-';
Div         : '/';
Mult        : '*';
Mod         : '%';

// Int -> Bool Operators
Less        : '<';
LessEq      : '<=';
Grtr        : '>';
GrtrEq      : '>=';

// * -> Bool Operators
Eq          : 'EQUALS';

// Bool -> Bool Operators
And         : 'AND';
Or          : 'OR';
Xor         : 'XOR';
Not         : 'NOT';

// Array -> Literal Operator
Index       : 'INDEX';
Size        : 'SIZE';

// Identifiers and Literals
Idfr        : [a-z][a-zA-Z0-9]*;
ComponentId : '|' [0-9]+ '|';
IntLit      : '0' | '-'?[1-9][0-9]*;
IndexNum    : [0-9]+;
CharLit     : ('\'' | '"')[a-zA-Z0-9]('\'' | '"');
BoolLit     : 'TRUE' | 'FALSE';