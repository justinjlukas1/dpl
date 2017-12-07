//https://github.com/benjaminRomano/BenScript/blob/master/code/src/org/bromano/benscript/lexer/Lexer.java
//Lexer.java


public class Lexeme {
    public kind type;

    //change to type Object?
    public String value;
    public Integer intValue;
    public Double doubleValue;
    //public linePosition linePos;
    public int line;

    //add left and right?


//    public Lexeme(kind type) {
//        this.type = type;
//        this.value = null;
//        this.linePos = null;
//    }
//
//    public Lexeme(kind type, linePosition linePos) {
//        this.type = type;
//        this.value = null;
//        this.linePos = linePos;
//    }
//
//    public Lexeme(kind type, String value) {
//        this.type = type;
//        this.value = value;
//        this.linePos = null;
//    }
//
//    public Lexeme(kind type, String value, linePosition linePos) {
//        this.type = type;
//        this.value = value;
//        this.linePos = linePos;
//    }

    public Lexeme(kind var1, int var2) {
        this.type = var1;
        this.value = null;
        this.line = var2;
    }

    public Lexeme(kind var1, String var2, int var3) {
        this.type = var1;
        this.value = var2;
        this.line = var3;
    }

    public Lexeme(kind var1, int var2, int var3) {
        this.type = var1;
        this.intValue = var2;
        this.line = var3;
    }

    public Lexeme(kind var1, double var2, int var3) {
        this.type = var1;
        this.doubleValue = var2;
        this.line = var3;
    }

    public kind getType() {
        return this.type;
    }

    public void setType(kind var1) {
        this.type = var1;
    }

    //may need to change to type Object?
    public String getValue() {
        return this.value;
    }

    public void setValue(String var1) {
        this.value = var1;
    }

    public void display() {
        System.out.print(this.type);
        if (this.value != null) {
            System.out.print(": " + this.value + " ");
        }
        if (this.intValue != null) {
            System.out.print(": " + this.value + " ");
        }
        if (this.doubleValue != null) {
            System.out.print(": " + this.value + " ");
        }
        System.out.println();
    }

    public boolean check(String var1) {
        return this.type.equals(var1);
    }

//    public linePosition getLine() {
//        return this.linePos;
//    }

    public int getLine() {
        return this.line;
    }

//    @Override
//    public String toString() {
//        if (linePos != null) {
//            return this.linePos.toString() + " " + this.type.name() + " " + (this.value != null ? this.value : "");
//        }
//        return this.type.name() + " " + this.value;
//    }
}
//    @Override
//    public boolean equals(Object obj) {
//        if(obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//
//        Lexeme lexeme = (Lexeme) obj;
//        return (lexeme.type == this.type
//                &&  (lexeme.value == null && this.value == null
//                || (lexeme.value != null && this.value != null && lexeme.value.equals(this.value))));
//    }


//
//    public enum kind {
//    //Literals
//        O_BRACE,
//        C_BRACE,
//
//        O_PAREN,
//        C_PAREN,
//
//        O_BRACKET,
//        C_BRACKET,
//
//
//        RESULT, //STILL NEED TO DO
//        NULL,   //still need to do
//        INTEGER,
//        REAL,
//        STRING,
//        EX_POINT,
//        MINUSUNARY,
//
//        DEFINE,
//        AS,
//
//        SET,
//        TO,
//
//        IF,
//        ELSE,
//
//        LOOP,
//
//        VARIABLE,
//        DOT,
//
//        LAMBDA,
//
//        PLUS,
//        MINUS,
//        MULTIPLY,
//        DIVIDE,
//        EQUALS,
//        LESS_THAN,
//        GREATER_THAN,
//        LTE,
//        GTE,
//        INDEX,
//        AND,
//        OR,
//        NOT,
//        EXPONENT,
//        MODULO,
//
//        //Literals
//        IntegerLiteral,
//        StringLiteral,
//        BooleanLiteral,
//        Identifier,
//        //Keywords
//        IfKeyword,
//        VarKeyword,
//        FuncKeyword,
//        LFuncKeyword,
//        BreakKeyword,
//        ReturnKeyword,
//        ContinueKeyword,
//        NullKeyword,
//        ElseKeyword,
//        ForKeyword,
//        WhileKeyword,
//        //Punctuation
//        Colon,
//        EqualsGreaterThan,
//        OpenBrace,
//        CloseBrace,
//        OpenBracket,
//        CloseBracket,
//        OpenParen,
//        CloseParen,
//        LessThan,
//        LessThanEquals,
//        GreaterThan,
//        GreaterThanEquals,
//        EqualsEquals,
//        ExclamationEquals,
//        Exclamation,
//        Semicolon,
//        QuestionMark,
//        AmpersandAmpersand,
//        BarBar,
//        Plus,
//        Minus,
//        Asterisk,
//        Slash,
//        Percent,
//        Caret,
//        Equals,
//        PlusEquals,
//        MinusEquals,
//        AsteriskEquals,
//        SlashEquals,
//        PercentEquals,
//        CaretEquals,
//        Comma,
//        Dot,
//    }
//
//}


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
