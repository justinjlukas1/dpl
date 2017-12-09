public class Parser {
    private Lexer l;
    private Lexeme root;

    public Parser(String fileName){
        this.l = new Lexer(fileName);
        l.scanner();
    }

    public Lexeme execute(){
        this.root = this.statements();
        return this.root;
    }

    private boolean check(String var1) {
        return this.l.getCurrentLexeme().check(var1);
    }

    private Lexeme match(String var1) {
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
//
//    private Lexeme statement() {
//        Lexeme var1 = null;
//        if (this.check("NEWLINE")) {
//            this.match("NEWLINE");
//        } else if (this.check("COMMENT")) {
//            this.match("COMMENT");
//        } else {
//            var1 = new Lexeme("STATEMENT", this.l.getCurrentLexeme().getLine());
//            if (this.l.expressionPending()) {
//                var1.setLeft(this.expression());
//            } else if (this.l.variableDefPending()) {
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
//        }
//
//        return var1;
//    }

}
