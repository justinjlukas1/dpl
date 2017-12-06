//https://github.com/benjaminRomano/BenScript/blob/master/code/src/org/bromano/benscript/lexer/Lexer.java
//Lexer.java


public class Lexeme {
    public kind type;
    public String value;

    public Lexeme(kind type) {
        this.type = type;
    }

    public Lexeme(kind type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.type.name() + " " + this.value;
    }

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
        EX_POINT,
        MINUSUNARY,

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

        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        EQUALS,
        LESS_THAN,
        GREATER_THAN,
        LTE,
        GTE,
        INDEX,
        AND,
        OR,
        NOT,
        EXPONENT,
        MODULO,

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

}


//public class Lexeme {
//    public LexemeKind kind;
//    public String value;
//    public LinePosition linePos;
//
//
//    public Lexeme(LexemeKind kind) {
//        this.kind = kind;
//    }
//
//    public Lexeme(LexemeKind kind, String value) {
//        this.kind = kind;
//        this.value = value;
//    }
//
//    public Lexeme(LexemeKind kind, LinePosition linePos) {
//        this.kind = kind;
//
//        this.linePos = linePos;
//    }
//
//    public Lexeme(LexemeKind kind, String value, LinePosition linePos) {
//        this.kind = kind;
//        this.value = value;
//        this.linePos = linePos;
//    }
//
//    @Override
//    public String toString() {
//        if (linePos != null) {
//            return this.linePos.toString() + " " + this.kind.name() + " " + (this.value != null ? this.value : "");
//        }
//        return this.kind.name() + " " + this.value;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if(obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//
//        Lexeme lexeme = (Lexeme) obj;
//        return (lexeme.kind == this.kind
//                &&  (lexeme.value == null && this.value == null
//                || (lexeme.value != null && this.value != null && lexeme.value.equals(this.value))));
//    }
//}



//
//
//public class lexeme extends types {
//
//    public static void lexeme(String token) {
//        String type = token;
//        String word;
//        int value;
//        double real;
//
//// need to add real?
//        if(Character.isDigit(token.charAt(0))) {
//            type = "INTEGER";
//            value = Integer.parseInt(token);
//            System.out.println(type + " " + value);
//        }
//        if(Character.isLetter(token.charAt(0))) {
//            type = "VARIABLE";
//            word = token;
//            System.out.println(type + " " + word);
//        }
//        if(token.charAt(0) == '"') {
//            type = "STRING";
//            word = token;
//            System.out.println(type + " " + word);
//        System.out.println(type);
//        }
//    }
//}
