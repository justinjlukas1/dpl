public enum kind {
    //Literals
    O_BRACE,
    C_BRACE,
    O_PAREN,
    C_PAREN,
    O_BRACKET,
    C_BRACKET,
    RESULT,
    NULL,
    INTEGER,
    REAL,
    STRING,
    NEG_INTEGER,
    NEG_REAL,
    DEFINE,
    AS,
    SET,
    TO,
    IF,
    ELSE,
    LOOP,
    VARIABLE,
    DOT,
    LAMBDA,
    ARRAYINIT,
    LISTITEM,
    LIST,

//operators
    PLUS,
    INC,
    PLUS_EQUALS,
    MINUS,
    DEC,
    MINUS_EQUALS,
    MULTIPLY,
    MULTIPLY_EQUALS,
    DIVIDE,
    DIVIDE_EQUALS,
    EQUALS,
    LESS_THAN,
    GREATER_THAN,
    LTE,
    GTE,
    INDEX,
    AND,
    OR,
    NOT,
    NOT_EQUALS_EX,
    NOT_EX,
    EXPONENT,
    EXPONENT_EQUALS,
    MODULO,


    NEWLINE,
    ENDofINPUT,
    COMMENT,
    INCLUDE,
    UNKNOWN,

//parsing Kinds
    STATEMENT,
    EXPRESSION,
    UNARY,
    BINARY,
    GLUE,
    DEFINITION,
    OBJECT,
    OBJEXPR,
    OBJPROC,
    PROCEDURE,
    BODY,
    FUNCTION,
    RETURN,
    IFSTATEMENT,
    LOOPSTATEMENT,


    //Literals
    IntegerLiteral,
    StringLiteral,
    BooleanLiteral,
    Identifier,
    //Keywords
    IfKeyword,
    VarKeyword,
    FuncKeyword,
    LFuncKeyword,
    BreakKeyword,
    ReturnKeyword,
    ContinueKeyword,
    NullKeyword,
    ElseKeyword,
    ForKeyword,
    WhileKeyword,
    //Punctuation
    Colon,
    EqualsGreaterThan,
    OpenBrace,
    CloseBrace,
    OpenBracket,
    CloseBracket,
    OpenParen,
    CloseParen,
    LessThan,
    LessThanEquals,
    GreaterThan,
    GreaterThanEquals,
    EqualsEquals,
    ExclamationEquals,
    Exclamation,
    Semicolon,
    QuestionMark,
    AmpersandAmpersand,
    BarBar,
    Plus,
    Minus,
    Asterisk,
    Slash,
    Percent,
    Caret,
    Equals,
    PlusEquals,
    MinusEquals,
    AsteriskEquals,
    SlashEquals,
    PercentEquals,
    CaretEquals,
    Comma,
    Dot,
}