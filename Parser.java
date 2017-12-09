public class Parser {
    private Lexer l;
    private Lexeme root;

    public Parser(String fileName){
        this.l = new Lexer(fileName);
        //l.scanner();
    }

    public Lexeme execute(){
        this.root = this.statements();
        return this.root;
    }

    private boolean check(kind var1) {
        return this.l.getCurrentLexeme().check(var1);
    }

    private Lexeme match(kind var1) {
        if (!this.check(var1)) {
            Fatal.FATAL("Expected " + var1, this.l.getCurrentLexeme().getLine());
        }
        System.out.println(var1);
        Lexeme var2 = this.l.getCurrentLexeme();
        this.l.advance();
        return var2;
    }

    private Lexeme statements() {
        Lexeme var1;
        for(var1 = this.statement(); var1 == null && this.l.statementPending(); var1 = this.statement()) {
            ;
        }

        if (this.l.statementPending()) {
            var1.setRight(this.statements());
        }

        return var1;
    }

    private Lexeme statement() {
        Lexeme var1 = null;
        if (this.check(kind.NEWLINE)) {
            this.match(kind.NEWLINE);
        } else if (this.check(kind.COMMENT)) {
            this.match(kind.COMMENT);
        } else {
            var1 = new Lexeme(kind.STATEMENT, this.l.getCurrentLexeme().getLine());
            if (this.l.expressionPending()) {
                var1.setLeft(this.expression());
            } //else if (this.l.variableDefPending()) {
//                var1.setLeft(this.variableDef());
//            } else if (this.l.functionDefPending()) {
//                var1.setLeft(this.functionDef());
//            } else if (this.l.arrayDefPending()) {
//                var1.setLeft(this.arrayDef());
//            } else if (this.l.classDefPending()) {
//                var1.setLeft(this.classDef());
//            } else if (this.l.objectDefPending()) {
//                var1.setLeft(this.objectDef());
//            } else if (this.l.conditionalPending()) {
//                var1.setLeft(this.conditional());
//            } else if (this.check("INCLUDE")) {
//                var1.setLeft(this.include());
//            }

        }

        return var1;
    }

    private Lexeme expression() {
        Lexeme var1 = new Lexeme(kind.EXPRESSION, this.l.getCurrentLexeme().getLine());
        if(this.l.unaryPending()){
            var1.setLeft(this.unary());
        } else if (this.l.binaryPending()) {
            var1.setLeft(this.binary());
        }

        return var1;
    }

    private Lexeme unary() {
        Lexeme var1 = new Lexeme(kind.UNARY, this.l.getCurrentLexeme().getLine());
        if (this.check(kind.INTEGER)) {
            var1.setLeft(this.match(kind.INTEGER));
        } else if (this.check(kind.REAL)) {
            var1.setLeft(this.match(kind.REAL));
        } else if (this.check(kind.STRING)) {
            var1.setLeft(this.match(kind.STRING));
        }
//        } else if (this.l.anonymousPending()) {
//            var1.setLeft(this.anonymousExpression());
//        } else {
//            var1.setLeft(this.variableExpression());
//        }

        return var1;
    }

    private Lexeme binary() {
        Lexeme var1 = new Lexeme(kind.BINARY, this.l.getCurrentLexeme().getLine());
        Lexeme glue = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        var1.setLeft(glue);
        if(this.check(kind.PLUS)){
            var1.getLeft().setLeft(this.match(kind.PLUS));
        } else if(this.check(kind.INC)){
            var1.getLeft().setLeft(this.match(kind.INC));
        } else if(this.check(kind.PLUS_EQUALS)){
            var1.getLeft().setLeft(this.match(kind.PLUS_EQUALS));
        } else if(this.check(kind.MINUS)){
            var1.getLeft().setLeft(this.match(kind.MINUS));
        } else if(this.check(kind.DEC)){
            var1.getLeft().setLeft(this.match(kind.DEC));
        } else if(this.check(kind.MINUS_EQUALS)){
            var1.getLeft().setLeft(this.match(kind.MINUS_EQUALS));
        } else if(this.check(kind.MULTIPLY)){
            var1.getLeft().setLeft(this.match(kind.MULTIPLY));
        } else if(this.check(kind.MULTIPLY_EQUALS)){
            var1.getLeft().setLeft(this.match(kind.MULTIPLY_EQUALS));
        } else if(this.check(kind.DIVIDE)){
            var1.getLeft().setLeft(this.match(kind.DIVIDE));
        } else if(this.check(kind.DIVIDE_EQUALS)){
            var1.getLeft().setLeft(this.match(kind.DIVIDE_EQUALS));
        } else if(this.check(kind.EQUALS)){
            var1.getLeft().setLeft(this.match(kind.EQUALS));
        } else if(this.check(kind.LESS_THAN)){
            var1.getLeft().setLeft(this.match(kind.LESS_THAN));
        } else if(this.check(kind.GREATER_THAN)){
            var1.getLeft().setLeft(this.match(kind.GREATER_THAN));
        } else if(this.check(kind.LTE)){
            var1.getLeft().setLeft(this.match(kind.LTE));
        } else if(this.check(kind.GTE)){
            var1.getLeft().setLeft(this.match(kind.GTE));
        } else if(this.check(kind.INDEX)){
            var1.getLeft().setLeft(this.match(kind.INDEX));
        } else if(this.check(kind.AND)){
            var1.getLeft().setLeft(this.match(kind.AND));
        } else if(this.check(kind.OR)){
            var1.getLeft().setLeft(this.match(kind.OR));
        } else if(this.check(kind.NOT)){
            var1.getLeft().setLeft(this.match(kind.NOT));
        } else if(this.check(kind.NOT_EQUALS_EX)){
            var1.getLeft().setLeft(this.match(kind.NOT_EQUALS_EX));
        } else if(this.check(kind.NOT_EX)){
            var1.getLeft().setLeft(this.match(kind.NOT_EX));
        } else if(this.check(kind.EXPONENT)){
            var1.getLeft().setLeft(this.match(kind.EXPONENT));
        } else if(this.check(kind.EXPONENT_EQUALS)){
            var1.getLeft().setLeft(this.match(kind.EXPONENT_EQUALS));
        } else if(this.check(kind.MODULO)){
            var1.getLeft().setLeft(this.match(kind.MODULO));
        }
        if(this.l.expressionPending()) {
            var1.getLeft().setRight(this.expression());
            var1.setRight(this.optBinaryItems());
        }
        else {
            Fatal.FATAL("Expected Expression", this.l.getCurrentLexeme().getLine());
            //Fatal.FATAL("Expected Expression" + var1, this.l.getCurrentLexeme().getLine());
        }
        return var1;
    }
    private Lexeme optBinaryItems(){
        Lexeme var1 = new Lexeme(kind.NULL, this.l.getCurrentLexeme().getLine());
        if(this.l.expressionPending()){
            System.out.println("inside optBinary Items");
            var1.setLeft(this.expression());

            var1.setRight(this.optBinaryItems());
        }
        return var1;
    }

}
