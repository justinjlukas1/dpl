import java.io.EOFException;
import java.util.List;
import java.util.ArrayList;


public class Lexer {
    FileInput file;
    Lexeme currentLexeme;
    private boolean endOfFile;
    private int currentLine;

    public Lexer(String var1) {
        this.file = new FileInput(var1);
        this.endOfFile = false;
        this.currentLine = 1;
        this.advance();
    }

    public Lexeme getCurrentLexeme() {
        return this.currentLexeme;
    }

    public void setCurrentLexeme(Lexeme var1) {
        this.currentLexeme = var1;
    }

    public void scanner() {
        this.currentLexeme.display();

        while(this.currentLexeme.type != kind.ENDofINPUT) {
            this.advance();
            this.currentLexeme.display();
        }

    }

    public void advance() {
            if (this.endOfFile) {
                this.currentLexeme = new Lexeme(kind.ENDofINPUT, this.currentLine);
            } else {
                try {
                    this.currentLexeme = this.lex();
                } catch (EOFException var2) {
                    this.currentLexeme = new Lexeme(kind.ENDofINPUT, this.currentLine);
                }
            }
        }

    public List<Lexeme> getLexes() throws EOFException {
        List<Lexeme> lexemes = new ArrayList<>();

        this.currentLexeme = this.lex();

        while(this.currentLexeme.type != kind.ENDofINPUT) {
            lexemes.add(this.currentLexeme);

            this.currentLexeme = this.lex();
        }

        return lexemes;
    }


//lexing
    public Lexeme lex() throws EOFException {
        this.file.skipWhitespace();
        Character var1 = this.file.readNextRawCharacter();
        if(this.endOfFile){ return new Lexeme(kind.ENDofINPUT, this.currentLine);}

        switch(var1) {
            case '\n':
                this.currentLine = this.currentLine + 1;
                return new Lexeme(kind.NEWLINE, this.currentLine - 1);
            case '(':
                return new Lexeme(kind.O_PAREN, this.currentLine);
            case ')':
                return new Lexeme(kind.C_PAREN, this.currentLine);
            case '[':
                return new Lexeme(kind.O_BRACKET, this.currentLine);
            case ']':
                return new Lexeme(kind.C_BRACKET, this.currentLine);
            case '{':
                return new Lexeme(kind.O_BRACE, this.currentLine);
            case '}':
                return new Lexeme(kind.C_BRACE, this.currentLine);
            case '$':
                return lexComment();
        //don't need to include a value
            case '!':
                this.file.skipWhitespace();
                var1 = this.file.readNextRawCharacter();
                if (var1 == '=') {
                    return new Lexeme(kind.NOT_EQUALS_EX, this.currentLine);
                }
                else {
                    this.file.pushbackCharacter(var1);
                    return new Lexeme(kind.NOT_EX, this.currentLine);
                }
            case '<':
                this.file.skipWhitespace();
                var1 = this.file.readNextRawCharacter();
                if (var1 == '=') {
                    return new Lexeme(kind.LTE, this.currentLine);
                }
                else {
                    this.file.pushbackCharacter(var1);
                    return new Lexeme(kind.LESS_THAN, this.currentLine);
                }
            case '>':
                this.file.skipWhitespace();
                var1 = this.file.readNextRawCharacter();
                if (var1 == '=') {
                    return new Lexeme(kind.GTE, this.currentLine);
                }
                else {
                    this.file.pushbackCharacter(var1);
                    return new Lexeme(kind.GREATER_THAN, this.currentLine);
                }
            case '+':
                this.file.skipWhitespace();
                var1 = this.file.readNextRawCharacter();
                if (var1 == '=') {
                    return new Lexeme(kind.PLUS_EQUALS, this.currentLine);
                }
                else if (var1 == '+') {
                    return new Lexeme(kind.INC, this.currentLine);
                }
                else {
                    this.file.pushbackCharacter(var1);
                    return new Lexeme(kind.PLUS, this.currentLine);
                }
            case '-':   //subtract vs. negative number
                var1 = this.file.readNextRawCharacter();
                if(Character.isDigit(var1)){
                    this.file.pushbackCharacter(var1);
                    this.file.pushbackCharacter('-');
                    return this.lexNumber();
                }
                this.file.skipWhitespace();
                if (var1 == '=') {
                    return new Lexeme(kind.MINUS_EQUALS, this.currentLine);
                }
                else if (var1 == '-') {
                    return new Lexeme(kind.DEC, this.currentLine);
                }
                else {
                    this.file.pushbackCharacter(var1);
                    return new Lexeme(kind.MINUS, this.currentLine);
                }
            case '%':
                return new Lexeme(kind.MODULO, this.currentLine);
            case '*':
                var1 = this.file.readNextCharacter();
                if (var1 == '=') {
                    return new Lexeme(kind.MULTIPLY_EQUALS, this.currentLine);
                }
                else {
                    this.file.pushbackCharacter(var1);
                    return new Lexeme(kind.MULTIPLY, this.currentLine);
                }
            case '/':
                var1 = this.file.readNextCharacter();
                if (var1 == '=') {
                    return new Lexeme(kind.DIVIDE_EQUALS, this.currentLine);
                }
                else {
                    this.file.pushbackCharacter(var1);
                    return new Lexeme(kind.DIVIDE, this.currentLine);
                }
            case '.':
                var1 = this.file.readNextRawCharacter();
                if(Character.isDigit(var1)){
                    this.file.pushbackCharacter(var1);
                    return this.lexNumber();
                }
                else {
                    this.file.pushbackCharacter(var1);
                    return new Lexeme(kind.DOT, this.currentLine);
                }
            case '^':
                var1 = this.file.readNextCharacter();
                if (var1 == '=') {
                    return new Lexeme(kind.EXPONENT_EQUALS, this.currentLine);
                }
                else {
                    this.file.pushbackCharacter(var1);
                    return new Lexeme(kind.EXPONENT, this.currentLine);
                }
            case '=':
                return new Lexeme(kind.EQUALS, this.currentLine);
            case '#':
                return new Lexeme(kind.INDEX, this.currentLine);
            case '\"':
                return lexString();
            default:
                if (!Character.isDigit(var1) && var1 != '-') {  //non number
                    if (Character.isLetter(var1)) {
                        this.file.pushbackCharacter(var1);
                        return this.lexVarOrKeyword();
                    } else {    //neither a number, letter, or keyword
                        return var1 == '"' ? this.lexString() : new Lexeme(kind.UNKNOWN, this.currentLine);
                    }
                } else {    //number
                    this.file.pushbackCharacter(var1);
                    return this.lexNumber();
                }
        }
    }

    private Lexeme lexVarOrKeyword() throws EOFException {
        String var2 = new String();
        Character var1 = this.file.readNextRawCharacter();

        while(Character.isLetterOrDigit(var1) && !this.endOfFile) {
            var2 = var2.concat(Character.toString(var1));

            try {
                var1 = this.file.readNextRawCharacter();
            } catch (EOFException var4) {
                this.endOfFile = true;
            }
        }
        this.file.pushbackCharacter(var1);
        if (var2.equals("define")) {
            return new Lexeme(kind.DEFINE, this.currentLine);
        } else if (var2.equals("NULL")) {
            return new Lexeme(kind.NULL, this.currentLine);
        } else if (var2.equals("define")) {
            return new Lexeme(kind.DEFINE, this.currentLine);
        } else if (var2.equals("as")) {
            return new Lexeme(kind.AS, this.currentLine);
        } else if (var2.equals("set")) {
            return new Lexeme(kind.SET, this.currentLine);
        } else if (var2.equals("to")) {
            return new Lexeme(kind.TO, this.currentLine);
        } else if (var2.equals("if")) {
            return new Lexeme(kind.IF, this.currentLine);
        } else if (var2.equals("else")) {
            return new Lexeme(kind.ELSE, this.currentLine);
        } else if (var2.equals("loop")) {
            return new Lexeme(kind.LOOP, this.currentLine);
        } else if (var2.equals("lambda")) {
            return new Lexeme(kind.LAMBDA, this.currentLine);
        } else if (var2.equals("and")) {
            return new Lexeme(kind.AND, this.currentLine);
        } else if (var2.equals("or")) {
            return new Lexeme(kind.OR, this.currentLine);
        } else if (var2.equals("include")) {    //include?
            return new Lexeme(kind.INCLUDE, this.currentLine);
        } else if (var2.equals("not")) {
            return new Lexeme(kind.NOT, this.currentLine);
        } else if (var2.equals("result")){
            return new Lexeme(kind.RESULT, this.currentLine);
        } else if (var2.equals("function")) {
            return new Lexeme(kind.FUNCTION, this.currentLine);
        } else {
            return new Lexeme(kind.VARIABLE, var2, this.currentLine);

        }
    }

    private Lexeme lexString() throws EOFException {
        String var2 = new String();
        Character var1 = this.file.readNextRawCharacter();

        while(var1 != '\"' && !this.endOfFile) {
            var2 = var2.concat(Character.toString(var1));

            try {
                var1 = this.file.readNextRawCharacter();
            } catch (EOFException var4) {
                this.endOfFile = true;
            }
        }

        return new Lexeme(kind.STRING, var2, this.currentLine);
    }

    private Lexeme lexNumber() throws EOFException {
        String var2 = new String();
        Character var1 = this.file.readNextRawCharacter();
        if(var1 == '-'){
            var2 = var2.concat(Character.toString(var1));
            var1 = this.file.readNextRawCharacter();
            while ((Character.isDigit(var1) || var1 == '.') && !this.endOfFile) {
                var2 = var2.concat(Character.toString(var1));

                try {
                    var1 = this.file.readNextRawCharacter();
                } catch (EOFException var6) {
                    this.endOfFile = true;
                }
            }

            this.file.pushbackCharacter(var1);
            if (var2.contains(Character.toString('.'))) {
                try {
                    return new Lexeme(kind.NEG_REAL, Double.parseDouble(var2), this.currentLine);
                } catch (NumberFormatException var4) {
                    return new Lexeme(kind.UNKNOWN, this.currentLine);
                }
            } else {
                try {
                    Integer temp = Integer.parseInt(var2);
                    return new Lexeme(kind.NEG_INTEGER, temp, this.currentLine);
                } catch (NumberFormatException var5) {
                    return new Lexeme(kind.UNKNOWN, this.currentLine);
                }
            }
        }
        else {
            while ((Character.isDigit(var1) || var1 == '.') && !this.endOfFile) {
                var2 = var2.concat(Character.toString(var1));

                try {
                    var1 = this.file.readNextRawCharacter();
                } catch (EOFException var6) {
                    this.endOfFile = true;
                }
            }

            this.file.pushbackCharacter(var1);
            if (var2.contains(Character.toString('.'))) {
                try {
                    return new Lexeme(kind.REAL, Double.parseDouble(var2), this.currentLine);
                } catch (NumberFormatException var4) {
                    return new Lexeme(kind.UNKNOWN, this.currentLine);
                }
            } else {
                try {
                    Integer temp = Integer.parseInt(var2);
                    return new Lexeme(kind.INTEGER, temp, this.currentLine);
                } catch (NumberFormatException var5) {
                    return new Lexeme(kind.UNKNOWN, this.currentLine);
                }
            }
        }
    }

    private Lexeme lexComment() throws EOFException {
        String var2 = new String();
        Character var1 = this.file.readNextRawCharacter();
        if (var1 == '*') {  //block comment
            while (var1 != '$' && !this.endOfFile) {
                if(var1 == '\n')
                    this.currentLine = this.currentLine + 1;
                var2 = var2.concat(Character.toString(var1));

                try {
                    var1 = this.file.readNextRawCharacter();
                } catch (EOFException var4) {
                    this.endOfFile = true;
                }
            }

            return new Lexeme(kind.COMMENT, var2, this.currentLine);
        }
        else    {   //single line comment
            while (var1 != '\n' && !this.endOfFile) {
                var2 = var2.concat(Character.toString(var1));

                try {
                    var1 = this.file.readNextRawCharacter();
                } catch (EOFException var4) {
                    this.endOfFile = true;
                }
            }
            this.currentLine = this.currentLine + 1;
            return new Lexeme(kind.COMMENT, var2, this.currentLine-1);
        }
    }


//recognizing / parsing
    public boolean statementPending() {
        return
                this.expressionPending()
                || this.definitionPending()
                || this.returnPending()
                || this.assignmentPending()
                || this.ifStatementPending()
//                || this.loopPending()
//                //|| this.conditionalPending()
//                //|| this.currentLexeme.check("INCLUDE")
                || this.commentPending()
                || this.currentLexeme.check(kind.NEWLINE);
    }

    public boolean returnPending() {
        return this.currentLexeme.check(kind.RESULT);
    }

    public boolean ifStatementPending() {
        return this.currentLexeme.check(kind.IF);
    }
    public boolean definitionPending(){
        return this.currentLexeme.check(kind.DEFINE);
    }

    public boolean assignmentPending(){
        return this.currentLexeme.check(kind.SET);
    }

    public boolean expressionPending() {
        return
                this.unaryPending()
                || this.binaryPending();
    }

    public boolean commentPending(){
        return this.currentLexeme.check(kind.COMMENT);
    }

    public boolean dotPending(){
        return this.currentLexeme.check(kind.DOT);
    }

    public boolean listPending() {
        return this.currentLexeme.check(kind.O_BRACKET);
    }

    public boolean bodyPending() {
        System.out.println(this.getCurrentLexeme().getType());
        return this.currentLexeme.check(kind.O_BRACE);
    }

    public boolean unaryPending() {
        return
                this.currentLexeme.check(kind.INTEGER)
                || this.currentLexeme.check(kind.REAL)
                || this.currentLexeme.check(kind.STRING)
                || this.currentLexeme.check(kind.NEG_REAL)
                || this.currentLexeme.check(kind.NEG_INTEGER)
                || this.objectPending()
                || this.currentLexeme.check(kind.O_BRACKET)
                || this.currentLexeme.check(kind.O_PAREN);
//                        || this.currentLexeme.check("VARIABLE");
//                || this.anonymousPending()
        //               || this.currentLexeme.check(); //functionCall
//                | lambda
//                | list
//                | object
//                | O_PAREN expression C_PAREN
//                | EX_POINT unary  //! x1
//                | MINUS unary
//                | arrayInit
    }

    public boolean binaryPending() {
        return this.operatorPending();
    }

    public boolean objectPending() {
        return
                this.currentLexeme.check(kind.VARIABLE)
                || this.currentLexeme.check(kind.OBJECT);
    }

    public boolean operatorPending() {
        return
                this.currentLexeme.check(kind.PLUS)
                || this.currentLexeme.check(kind.INC)
                || this.currentLexeme.check(kind.PLUS_EQUALS)
                || this.currentLexeme.check(kind.MINUS)
                || this.currentLexeme.check(kind.DEC)
                || this.currentLexeme.check(kind.MINUS_EQUALS)
                || this.currentLexeme.check(kind.MULTIPLY)
                || this.currentLexeme.check(kind.MULTIPLY_EQUALS)
                || this.currentLexeme.check(kind.DIVIDE)
                || this.currentLexeme.check(kind.DIVIDE_EQUALS)
                || this.currentLexeme.check(kind.EQUALS)
                || this.currentLexeme.check(kind.LESS_THAN)
                || this.currentLexeme.check(kind.GREATER_THAN)
                || this.currentLexeme.check(kind.LTE)
                || this.currentLexeme.check(kind.GTE)
                || this.currentLexeme.check(kind.INDEX)
                || this.currentLexeme.check(kind.AND)
                || this.currentLexeme.check(kind.OR)
                || this.currentLexeme.check(kind.NOT)
                || this.currentLexeme.check(kind.NOT_EQUALS_EX)
                || this.currentLexeme.check(kind.NOT_EX)
                || this.currentLexeme.check(kind.EXPONENT)
                || this.currentLexeme.check(kind.EXPONENT_EQUALS)
                || this.currentLexeme.check(kind.MODULO);
    }

//
//
//    public boolean ifStatementPending() {
//        return this.currentLexeme.check("IF");
//    }
//
//    public boolean loopPending() {
//        return this.currentLexeme.check("LOOP");
//    }
//
//    public boolean resultPending() {
//        return this.currentLexeme.check("RESULT");
//    }
//
//    public boolean commentPending() {
//        return this.currentLexeme.check("DOLLAR_SIGN");
//    }
//
//
//    public boolean classDefPending() {
//        return this.currentLexeme.check("CLASS");
//    }
//
//    public boolean objectDefPending() {
//        return this.currentLexeme.check("OBJECT");
//    }
//
//    public boolean initializerExpressionPending() {
//        return this.currentLexeme.check("OPEN_PAREN");
//    }
//
//    public boolean anonymousPending() {
//        return this.currentLexeme.check("ANONYMOUS") || this.currentLexeme.check("OPEN_PAREN");
//    }
//
//    public boolean objectExpressionPending() {
//        return this.currentLexeme.check("DOT");
//    }
//
//    public boolean variableExpressionPending() {
//        return this.currentLexeme.check("VARIABLE");
//    }
//
//    public boolean optParameterListPending() {
//        return this.currentLexeme.check("VARIABLE");
//    }
}
