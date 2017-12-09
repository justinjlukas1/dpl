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
            System.out.println("test newline");
            this.match(kind.NEWLINE);
        } else if (this.check(kind.COMMENT)) {
            System.out.println("test comment");
            this.match(kind.COMMENT);
        } else {
            System.out.println("test not a new line or comment");
            var1 = new Lexeme(kind.STATEMENT, this.l.getCurrentLexeme().getLine());
            if (this.l.expressionPending()) {
                System.out.println("expression pending");
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
        var1.setLeft(this.unary());
        if (this.l.binaryPending()) {
            var1.setRight(this.binary());
        }

        return var1;
    }

    private Lexeme unary() {
        Lexeme var1 = new Lexeme(kind.UNARY, this.l.getCurrentLexeme().getLine());
        if (this.check(kind.INTEGER)) {
            var1.setLeft(this.match(kind.INTEGER));
        }
//        } else if (this.check("REAL")) {
//            var1.setLeft(this.match("REAL"));
//        } else if (this.check("STRING")) {
//            var1.setLeft(this.match("STRING"));
//        } else if (this.l.anonymousPending()) {
//            var1.setLeft(this.anonymousExpression());
//        } else {
//            var1.setLeft(this.variableExpression());
//        }

        return var1;
    }

    private Lexeme binary() {
        Lexeme var1 = new Lexeme(kind.BINARY, this.l.getCurrentLexeme().getLine());
        if(this.check(kind.PLUS)){
            var1.setLeft(this.match(kind.PLUS));
        }
        return var1;
    }


}
