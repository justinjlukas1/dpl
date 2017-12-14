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
            } else if (this.l.definitionPending()) {
                var1.setLeft(this.definition());
            } else if (this.check(kind.RESULT)) {
                var1.setLeft(this.result());
            } else if (this.l.assignmentPending()) {
                var1.setLeft(this.assignment());
            } else if (this.l.ifStatementPending()) {
                var1.setLeft(this.ifStatement());
            } else if (this.l.loopPending()) {
                var1.setLeft(this.loop());
//            } else if (this.l.conditionalPending()) {
//                var1.setLeft(this.conditional());
//            } else if (this.check("INCLUDE")) {
//                var1.setLeft(this.include());
            }

        }

        return var1;
    }

    private Lexeme loop() {
        Lexeme var1 = new Lexeme(kind.LOOPSTATEMENT, this.l.getCurrentLexeme().getLine());
        Lexeme var2 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var3 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var4 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var5 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var6 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        var1.setLeft(var2);
        var1.setRight(var3);
        var1.getLeft().setRight(var4);
        var1.getRight().setLeft(var5);
        var1.getLeft().setLeft(this.match(kind.LOOP));
        var1.getLeft().getRight().setLeft(this.match(kind.O_PAREN));
        if(this.l.objectPending()) {
            var1.getLeft().getRight().setRight(var6);
            var1.getLeft().getRight().getRight().setLeft(this.object());
            var1.getLeft().getRight().getRight().setRight(this.unary());
        }
        var1.getRight().getLeft().setLeft(this.unary());
        if(this.l.expressionPending()) {
            var1.getRight().getLeft().setRight(this.expression());
        }
        this.match(kind.C_PAREN);
        if(this.l.bodyPending()) {
            var1.getRight().setRight(this.body());
        }
        else {
            var1.getRight().setRight(this.statement());
        }
        return var1;
    }

    private Lexeme ifStatement() {
        Lexeme var1 = new Lexeme(kind.IFSTATEMENT, this.l.getCurrentLexeme().getLine());
        Lexeme var2 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var3 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var4 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        var1.setLeft(var2);
        var1.setRight(var3);
        var1.getLeft().setLeft(this.match(kind.IF));
        var1.getLeft().setRight(this.unary());
        if(this.l.bodyPending()){
            var1.getRight().setLeft(body());
        }
        else {
            var1.getRight().setLeft(statement());
        }
        if(this.l.elsePending()) {
            var1.getRight().setRight(var4);
            var1.getRight().getRight().setLeft(this.match(kind.ELSE));
            if(this.l.ifStatementPending()) {
                var1.getRight().getRight().setRight(ifStatement());
            }
            else {
                if(this.l.bodyPending()){
                    var1.getRight().getRight().setRight(body());
                }
                else {
                    var1.getRight().getRight().setRight(statement());
                }
            }
        }

        return var1;
    }
    private Lexeme assignment(){
        Lexeme var1 = new Lexeme(kind.DEFINITION, this.l.getCurrentLexeme().getLine());
        Lexeme var2 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var3 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var4 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        var1.setLeft(var2);
        var1.setRight(var3);

        var1.getLeft().setLeft(this.match(kind.SET));
        var1.getLeft().setRight(var4);
        if(this.check(kind.FUNCTION)) {
            var1.getLeft().getRight().setLeft(this.match(kind.FUNCTION));
            var1.getLeft().getRight().setRight(this.object());
            var1.getRight().setLeft(this.match(kind.TO));
            var1.getRight().setRight(objProc());
        }
        else {
            var1.getLeft().getRight().setRight(this.object());
            var1.getRight().setLeft(this.match(kind.TO));
            var1.getRight().setRight(objExpr());
        }
        return var1;
    }

    private Lexeme result() {
        Lexeme var1 = new Lexeme(kind.RETURN, this.l.getCurrentLexeme().getLine());
        var1.setLeft(this.match(kind.RESULT));
        if(this.l.expressionPending()) {
            var1.setRight(this.expression());
        }

        return var1;
    }

    private Lexeme definition(){
        Lexeme var1 = new Lexeme(kind.DEFINITION, this.l.getCurrentLexeme().getLine());
        Lexeme var2 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var3 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        Lexeme var4 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        boolean procedure = false;
        var1.setLeft(var2);
        var1.getLeft().setRight(var3);
        var1.setRight(var4);
        var1.getLeft().setLeft(this.match(kind.DEFINE));
        if(this.check(kind.FUNCTION)) {
            procedure = true;
            var1.getLeft().getRight().setLeft(this.match(kind.FUNCTION));
            var1.getLeft().getRight().setRight(this.object());
            var1.getRight().setLeft(this.match(kind.AS));
            var1.getRight().setRight(this.objProc());
        }
        else {
            var1.getLeft().getRight().setRight(this.object());
            if (this.check(kind.AS)) {
                var1.getRight().setLeft(this.match(kind.AS));
            }
            var1.getRight().setRight(objExpr());
        }
        return var1;
    }

    private Lexeme objProc(){
        Lexeme var1 = new Lexeme(kind.OBJPROC, this.l.getCurrentLexeme().getLine());
        var1.setLeft(this.procedure());
        return var1;
    }

    private Lexeme objExpr(){
        Lexeme var1 = new Lexeme(kind.OBJEXPR, this.l.getCurrentLexeme().getLine());
        var1.setLeft(this.expression());

        return var1;
    }

    private Lexeme procedure() {
        Lexeme var1 = new Lexeme(kind.PROCEDURE, this.l.getCurrentLexeme().getLine());
        if(this.l.listPending()){
            var1.setLeft(this.listInit());

        }
        if(this.l.bodyPending()) {
            var1.setRight(this.body());
        }
        return var1;
    }

    private Lexeme body() {
        Lexeme var1 = new Lexeme(kind.BODY, this.l.getCurrentLexeme().getLine());
        var1.setLeft(this.match(kind.O_BRACE));
        var1.setRight(this.statements());
        this.match(kind.C_BRACE);

        return var1;
    }

    private Lexeme object() {
        Lexeme var1 = new Lexeme(kind.OBJECT, this.l.getCurrentLexeme().getLine());
        Lexeme var2 = new Lexeme(kind.GLUE, this.l.getCurrentLexeme().getLine());
        var1.setLeft(this.match(kind.VARIABLE));
        if(this.l.dotPending()) {
            var1.setRight(var2);
            var1.getRight().setLeft(this.match(kind.DOT));
            var1.getRight().setRight(this.object());
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
        } else if (this.check(kind.NEG_INTEGER)) {
            var1.setLeft(this.match(kind.NEG_INTEGER));
        } else if (this.check(kind.NEG_REAL)) {
            var1.setLeft(this.match(kind.NEG_REAL));
        } else if (this.check(kind.O_BRACKET))  {
            var1.setLeft(this.listInit());
        } else if (this.l.objectPending()){
            var1.setLeft(this.object());
        } else if (this.check(kind.O_PAREN)){
            var1.setLeft(this.match(kind.O_PAREN));
            var1.setRight(this.expression());
            this.match(kind.C_PAREN);
        }
            //var1.setLeft(this.anonymousExpression());
//        } else {
//            var1.setLeft(this.variableExpression());
//        }

        return var1;
    }

    private Lexeme listInit() {
        Lexeme var1 = new Lexeme(kind.LIST, this.l.getCurrentLexeme().getLine());
        var1.setLeft((this.match(kind.O_BRACKET)));
        var1.setRight(this.listItem());
        this.match(kind.C_BRACKET);

        return var1;
    }

    private Lexeme listItem()   {
        Lexeme var1 = new Lexeme(kind.LISTITEM, this.l.getCurrentLexeme().getLine());
        if(this.l.unaryPending()) {
            var1.setLeft(this.unary());
            //if(this.l.unaryPending()) {
                var1.setRight(this.listItem());
            //}
        }
        //set null?
        return var1;
    }

    private Lexeme binary() {   //I think binary works and is done...maybe not glue
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
            var1.setLeft(this.expression());

            var1.setRight(this.optBinaryItems());
        }
        return var1;
    }

}
