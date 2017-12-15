public class PrettyPrinter {

    private Lexeme parseTree;

    public PrettyPrinter(Lexeme parseTree) {
        this.parseTree = parseTree;
    }

    public void displayParseTree(Lexeme root, String leftOrRight) {

        System.out.println(leftOrRight + ": " + root.getType() + " : " + root.getValue());

        if (root.getLeft() != null)
            displayParseTree(root.getLeft(), "L");

        if (root.getRight() != null)
            displayParseTree(root.getRight(), "R");
    }

    public void displayStatements(Lexeme l) {

        if (l != null) {
            displayStatement(l);
            if (l.getRight() != null)
                displayStatements(l.getRight());
        }
    }

    private void displayStatement(Lexeme l) {
        if (l.getLeft().check(kind.EXPRESSION))
            displayExpression(l.getLeft());
        else if (l.getLeft().check(kind.DEFINE))
            displayDefine(l.getLeft());
        else if (l.getLeft().check(kind.RESULT))
            displayResult(l.getLeft());
        else if (l.getLeft().check(kind.SET))
            displayAssignment(l.getLeft());
        else if (l.getLeft().check(kind.IF))
            displayIfStatement(l.getLeft());
        else if (l.getLeft().check(kind.LOOP))
            displayLoop(l.getLeft());
        else if (l.getLeft().check(kind.DISPLAYLN))
            displayPrintln(l.getLeft());
        else if (l.getLeft().check(kind.DISPLAY))
            displayPrint(l.getLeft());

        System.out.println();
    }

    private void displayExpression(Lexeme l) {
            if (l.getLeft().check(kind.UNARY)) {
                displayUnary(l.getLeft());
            } else {    //binary
                displayBinary(l.getLeft());
            }
    }
    private void displayResult(Lexeme l) {
        System.out.print("result ");
        if(l.getRight() != null) {
            displayExpression(l.getRight());
        }
    }
    private void displayBinary(Lexeme l) {
        System.out.print(l.getLeft().getLeft().getValue() + " ");
        displayExpression(l.getLeft().getRight());
        if(l.getRight() != null) {
            displayOptBinaryItems(l.getRight());
        }
    }
    private void displayOptBinaryItems(Lexeme l) {
        System.out.print(" ");
        if(l.getLeft() != null) {
            displayExpression(l.getLeft());
            displayOptBinaryItems(l.getRight());
        }
    }
    private void displayUnary(Lexeme l) {
        if (digitPending(l.getLeft()))    //numbers
            System.out.print(l.getLeft().getValue() + " ");
        else if (l.getLeft().check(kind.STRING))
            System.out.print("\"" + l.getLeft().getValue() + "\" ");
        else if (l.getLeft().check(kind.OBJECT))
            displayObject(l.getLeft());
        else if (l.getLeft().check(kind.FUNCTIONCALL)) {
            displayFunctionCall(l.getLeft());

        } else if (l.getLeft().check(kind.LAMBDA))
            displayLambda(l.getLeft());
        else if (l.getLeft().check(kind.LIST))
            displayList(l.getLeft());
        else if(l.getLeft().check(kind.O_PAREN)) {
            System.out.print("(");
            displayExpression(l.getRight());
            System.out.print(") ");
        }
        else
            System.out.println("Fatal, unknown kind: " + l.getLeft().getType());

    }
    private void displayDefine(Lexeme l) {
        boolean flag = false;
        System.out.print("define ");
        if(l.getLeft().getRight().getLeft() != null) {
            System.out.print("function ");
            flag = true;
        }
        displayObject(l.getLeft().getRight().getRight());
        if(l.getRight().getLeft() != null) {
            System.out.print(" as ");
            if (flag) {
                displayProcedure(l.getRight().getRight());
            } else {
                displayExpression(l.getRight().getRight());
            }
        }
    }
    private void displayAssignment(Lexeme l) {
        boolean flag = false;
        System.out.print("set ");
        if(l.getLeft().getRight().getLeft() != null) {
            System.out.print("function ");
            flag = true;
        }
        displayObject(l.getLeft().getRight().getRight());
            System.out.print(" to ");
            if (flag) {
                displayProcedure(l.getRight().getRight());
            } else {
                displayExpression(l.getRight().getRight());
            }
    }
    private void displayIfStatement(Lexeme l) {
        System.out.print("if ");
        displayUnary(l.getLeft().getRight());
        if(l.getRight().getLeft().check(kind.BODY))
            displayBody(l.getRight().getLeft());
        else
            displayStatement(l.getRight().getLeft());
        if(l.getRight().getRight() != null) {
            System.out.print("else ");
            if(l.getRight().getRight().getRight().check(kind.BODY)) {
                displayBody(l.getRight().getRight().getRight());
            } else if(l.getRight().getRight().getRight().check(kind.STATEMENT)) {
                displayStatement(l.getRight().getRight().getRight());
            } else {
                displayIfStatement(l.getRight().getRight().getRight());
            }
        }
    }
    private void displayLoop(Lexeme l) {
        System.out.print("loop(");
        if(l.getLeft().getRight().getRight()!= null) { //should have two unaries
            displayUnary(l.getLeft().getRight().getRight().getLeft());
            System.out.print(" ");
            displayUnary(l.getLeft().getRight().getRight().getRight());
        }
        displayUnary(l.getRight().getLeft().getLeft());
        if(l.getRight().getLeft().getRight() != null) {
            displayExpression(l.getRight().getLeft().getRight());
        }
        System.out.print(") \n");
        if(l.getRight().getRight().check(kind.BODY))
            displayBody(l.getRight().getRight());
        else
            displayStatement(l.getRight().getRight());
    }
    private boolean digitPending(Lexeme l){
        return
                l.check(kind.INTEGER)
                || l.check(kind.NEG_INTEGER)
                || l.check(kind.NEG_REAL)
                || l.check(kind.REAL);
    }
    private void displayObject(Lexeme l) {
        //root is object
        System.out.print(l.getLeft().getValue());
        if(l.getRight() != null) {
            System.out.print(l.getRight().getLeft().getValue());  //could be changed to just print out '.'
            displayObject(l.getRight().getRight());
        }
        System.out.print(" ");
    }
    private void displayFunctionCall(Lexeme l) {
        displayObject(l.getLeft());
        displayList(l.getRight());
    }
    private void displayList(Lexeme l) {
        System.out.print("["); // '['
        displayListItem(l.getRight());
        System.out.print("] ");
    }
    private void displayListItem(Lexeme l) {
        if(l.getLeft() != null) {
            if (l.getLeft().check(kind.UNARY)) {
                displayUnary(l.getLeft());
                displayListItem(l.getRight());
            }
        }
    }
    private void displayLambda(Lexeme l) {
        System.out.print("lambda ");
        displayProcedure(l.getRight());
    }
    private void displayProcedure(Lexeme l) {
        displayList(l.getLeft());
        displayBody(l.getRight());
    }
    private void displayBody(Lexeme l) {
        System.out.println("{");
        displayStatements(l.getRight());
        System.out.print("} ");
    }
    private void displayPrintln(Lexeme l) {
        System.out.print("displayln ");
        if(l.getRight() != null)
            displayExpression(l.getRight());
    }
    private void displayPrint(Lexeme l) {
        System.out.print("display ");
        if(l.getRight() != null)
            displayExpression(l.getRight());
    }
}

/*
    private void displayConditional(Lexeme l) {

        if (l.getLeft().check(Lexeme.Type.IfKeyword))
            displayIfStatement(l.getLeft());
        else if (l.getLeft().check(Lexeme.Type.WhileKeyword));
        displayWhileStatement(l.getLeft());
    }

    private void displayIfStatement(Lexeme l) {

        System.out.print("if (");
        displayExpression(l.getLeft());
        System.out.print(") ");
        displayBlock(l.getRight().getLeft());

        if (l.getRight().getRight() != null)
            displayElseStatement(l.getRight().getRight());
    }

    private void displayElseStatement(Lexeme l) {

        System.out.print("else ");

        if (l.getLeft().check(Lexeme.Type.IfKeyword))
            displayIfStatement(l.getLeft());
        else
            displayBlock(l.getLeft());
    }

    private void displayWhileStatement(Lexeme l) {

        System.out.print("while (");
        displayExpression(l.getLeft());
        System.out.print(") ");
        displayBlock(l.getRight());
    }

    private void displayObject(Lexeme l) {

        System.out.print("obj ");

        displayVariable(l.getLeft());
        displayInitializerExpression(l.getRight());
    }

    private void displayArray(Lexeme l) {

        System.out.print("array ");

        displayVariable(l.getLeft());
        displayInitializerExpression(l.getRight());
    }

    private void displayFunction(Lexeme l) {

        System.out.print("func ");

        displayVariable(l.getLeft());
        displayParameterExpression(l.getLeft().getLeft());
        displayBlock(l.getRight());

    }

    private void displayObjectExpression(Lexeme l) {

        displayVariable(l.getLeft());

        while (l.getLeft() != null) {
            System.out.print(".");
            displayVariable(l.getLeft());
            l = l.getLeft();
        }

        if (l.getRight() != null) {
            if (l.getRight().check(Lexeme.Type.ObjectExpression))
                displayObjectExpression(l.getRight());
            else if (l.getRight().check(Lexeme.Type.InitializerExpression))
                displayInitializerExpression(l.getRight());
            else if (l.getRight().check(Lexeme.Type.Equal)) {
                System.out.print(" = ");
                displayUnary(l.getRight().getLeft());
            }

        }
    }

    private void displayInitializerExpression(Lexeme l) {

        System.out.print("(");

        if (l.getLeft() != null)
            displayExpression(l.getLeft());

        System.out.print(")");
    }

    private void displayAnonymousExpression(Lexeme l) {

        if (l.getRight().check(Lexeme.Type.AnonymousCall)) {
            System.out.print("afunc ");
            displayParameterExpression(l.getLeft());
            displayBlock(l.getRight());
        }
        else {
            System.out.print("(");
            System.out.print("afunc");
            displayParameterExpression(l.getLeft());
            displayBlock(l.getRight());
            displayInitializerExpression(l.getRight().getRight());
            System.out.print(")");
        }
    }

    private void displayParameterExpression(Lexeme l) {

        System.out.print("(");

        if (l.getLeft() != null)
            displayParameters(l.getLeft());

        System.out.print(")");
    }

    private void displayParameters(Lexeme v) {

        Lexeme l = v.getLeft();
        if (l != null) {
            if (v.getRight() == null)
                System.out.print(l.getValue());
            else {
                System.out.print(l.getValue() + ", ");
                displayParameters(v.getRight());
            }
        }
    }

    private void displayBlock(Lexeme l) {

        System.out.print("{");

        if (l.getLeft() != null)
            displayStatements(l.getLeft());

        System.out.print("}");
    }

    private void displayVariableExpression(Lexeme l) {

        displayVariable(l);

        if (l.getLeft() != null) {
            if (l.getLeft().check(Lexeme.Type.Equal)) {
                System.out.print(" = ");
                displayUnary(l.getLeft().getLeft());
            }
            else
                displayInitializerExpression(l.getLeft());
        }
    }

    private void displayVariable(Lexeme l) {

        System.out.print(l.getValue());
    }

    private void displayVariableDef(Lexeme l) {

        Lexeme v = l.getLeft();

        System.out.print("var ");
        displayVariable(v);
        displayInitializerExpression(l.getRight());
    }
}

*/
/*
public class PrettyPrinter {
    public PrettyPrinter() {
    }

    public void displayParseTree(Lexeme var1) {
        this.displayStatements(var1);
    }

    public void displayParser(Lexeme var1) {
        this.display(var1, "Root");
    }

    private void display(Lexeme var1, String var2) {
        System.out.println(var2 + ": " + var1.getType());
        if (var1.getLeft() != null) {
            this.display(var1.getLeft(), "L");
        }

        if (var1.getRight() != null) {
            this.display(var1.getRight(), "R");
        }

    }

    private void displayStatements(Lexeme var1) {
        if (var1 != null) {
            this.displayStatement(var1);
        }

        if (var1.getRight() != null) {
            this.displayStatements(var1.getRight());
        }

    }

    private void displayStatement(Lexeme var1) {
        Lexeme var2 = var1.getLeft();
        kind var3 = var2.getType();
        byte var4 = -1;
        switch(var3.hashCode()) {
            case -2131401768:
                if (var3.equals("FUNCTION")) {
                    var4 = 2;
                }
                break;
            case -1970038977:
                if (var3.equals("OBJECT")) {
                    var4 = 5;
                }
                break;
            case -1634410360:
                if (var3.equals("INCLUDE")) {
                    var4 = 7;
                }
                break;
            case -1310359912:
                if (var3.equals("EXPRESSION")) {
                    var4 = 0;
                }
                break;
            case -933875098:
                if (var3.equals("CONDITIONAL")) {
                    var4 = 6;
                }
                break;
            case 62552633:
                if (var3.equals("ARRAY")) {
                    var4 = 3;
                }
                break;
            case 64205144:
                if (var3.equals("CLASS")) {
                    var4 = 4;
                }
                break;
            case 118457566:
                if (var3.equals("VARIABLE_DEFINE")) {
                    var4 = 1;
                }
                break;
            case 1668381247:
                if (var3.equals("COMMENT")) {
                    var4 = 8;
                }
        }

        switch(var4) {
            case 0:
                this.displayExpression(var2);
                break;
            case 1:
                this.displayVariableDefine(var2);
                break;
            case 2:
                this.displayFunction(var2);
                break;
            case 3:
                this.displayArray(var2);
                break;
            case 4:
                this.displayClass(var2);
                break;
            case 5:
                this.displayObject(var2);
                break;
            case 6:
                this.displayConditional(var2);
                break;
            case 7:
                this.displayInclude(var2);
                break;
            case 8:
                this.displayComment(var2);
        }

        System.out.println();
    }

    private void displayExpression(Lexeme var1) {
        Lexeme var2 = var1.getLeft();
        if (var2 != null) {
            this.displayUnary(var2);
            if (var1.getRight() != null) {
                System.out.print(" ");
                this.displayExpression(var1.getRight());
            }
        }

    }

    private void displayVariableDefine(Lexeme var1) {
        Lexeme var2 = var1.getLeft();
        System.out.print("variable ");
        this.displayVariable(var2);
        this.displayInitializerExpression(var1.getRight());
    }

    private void displayFunction(Lexeme var1) {
        System.out.print("function ");
        this.displayVariable(var1.getLeft());
        this.displayParameterExpression(var1.getLeft().getLeft());
        this.displayBlock(var1.getRight());
    }

    private void displayArray(Lexeme var1) {
        System.out.print("array ");
        this.displayVariable(var1.getLeft());
        this.displayInitializerExpression(var1.getRight());
    }

    private void displayClass(Lexeme var1) {
        System.out.print("class ");
        this.displayVariable(var1.getLeft());
        this.displayBlock(var1.getRight());
    }

    private void displayObject(Lexeme var1) {
        System.out.print("object ");
        this.displayVariable(var1.getLeft());
        this.displayInitializerExpression(var1.getRight());
    }

    private void displayConditional(Lexeme var1) {
        String var2 = var1.getLeft().getType();
        byte var3 = -1;
        switch(var2.hashCode()) {
            case 2333:
                if (var2.equals("IF")) {
                    var3 = 0;
                }
                break;
            case 82563857:
                if (var2.equals("WHILE")) {
                    var3 = 1;
                }
        }

        switch(var3) {
            case 0:
                this.displayIf(var1.getLeft());
                break;
            case 1:
                this.displayWhile(var1.getLeft());
        }

    }

    private void displayInclude(Lexeme var1) {
        System.out.print("include ");
        System.out.print("\"" + var1.getLeft().getValue() + "\"");
    }

    private void displayComment(Lexeme var1) {
        System.out.print("[");
        System.out.print(var1.getValue());
        System.out.print("]");
    }

    private void displayUnary(Lexeme var1) {
        Lexeme var2 = var1.getLeft();
        String var3 = var2.getType();
        byte var4 = -1;
        switch(var3.hashCode()) {
            case -1838656495:
                if (var3.equals("STRING")) {
                    var4 = 2;
                }
                break;
            case -1618932450:
                if (var3.equals("INTEGER")) {
                    var4 = 0;
                }
                break;
            case -879521896:
                if (var3.equals("OBJECT_EXPRESSION")) {
                    var4 = 4;
                }
                break;
            case 2022338513:
                if (var3.equals("DOUBLE")) {
                    var4 = 1;
                }
                break;
            case 2144046538:
                if (var3.equals("ANONYMOUS_EXPRESSION")) {
                    var4 = 3;
                }
        }

        switch(var4) {
            case 0:
                System.out.print(var2.getValue());
                break;
            case 1:
                System.out.print(var2.getValue());
                break;
            case 2:
                System.out.print("\"" + var2.getValue() + "\"");
                break;
            case 3:
                this.displayAnonymous(var2);
                break;
            case 4:
                this.displayObjectExpression(var2);
                break;
            default:
                this.displayVariableExpression(var2);
        }

    }

    private void displayInitializerExpression(Lexeme var1) {
        Lexeme var2 = var1.getLeft();
        System.out.print("(");
        if (var2 != null) {
            this.displayExpression(var2);
        }

        System.out.print(")");
    }

    private void displayVariable(Lexeme var1) {
        System.out.print(var1.getValue());
    }

    private void displayParameterExpression(Lexeme var1) {
        System.out.print("(");
        if (var1.getLeft() != null) {
            this.displayOptParameterList(var1.getLeft());
        }

        System.out.print(") ");
    }

    private void displayIf(Lexeme var1) {
        System.out.print("if ");
        System.out.print("(");
        this.displayExpression(var1.getLeft());
        System.out.print(") ");
        this.displayBlock(var1.getRight().getLeft());
        if (var1.getRight().getRight() != null) {
            this.displayOptElse(var1.getRight().getRight());
        }

    }

    private void displayWhile(Lexeme var1) {
        System.out.print("while ");
        System.out.print("(");
        this.displayExpression(var1.getLeft());
        System.out.print(") ");
        this.displayBlock(var1.getRight());
    }

    private void displayAnonymous(Lexeme var1) {
        Lexeme var2 = var1.getLeft();
        if (var2.getRight().check("CALL")) {
            this.displayAnonymousCall(var2);
        } else {
            this.displayAnonymousDefine(var2);
        }

    }

    private void displayObjectExpression(Lexeme var1) {
        this.displayVariable(var1.getLeft());

        for(Lexeme var2 = var1.getLeft(); var2.getLeft() != null; var2 = var2.getLeft()) {
            System.out.print(".");
            this.displayVariable(var2.getLeft());
        }

        if (var1.getRight() != null) {
            if (var1.getRight().check("OBJECT_EXPRESSION")) {
                this.displayObjectExpression(var1.getRight());
            } else if (var1.getRight().check("ASSIGN")) {
                System.out.print(" = ");
                this.displayUnary(var1.getRight().getLeft());
            } else if (var1.getRight().check("INITIALIZER_EXPRESSION")) {
                this.displayInitializerExpression(var1.getRight());
            }
        }

    }

    private void displayVariableExpression(Lexeme var1) {
        this.displayVariable(var1);
        if (var1.getLeft() != null) {
            if (var1.getLeft().check("ASSIGN")) {
                System.out.print(" = ");
                this.displayUnary(var1.getLeft().getLeft());
            } else {
                this.displayInitializerExpression(var1.getLeft());
            }
        }

    }

    private void displayBlock(Lexeme var1) {
        System.out.println("{");
        if (var1.getLeft() != null) {
            this.displayStatements(var1.getLeft());
        }

        System.out.print("}");
    }

    private void displayOptParameterList(Lexeme var1) {
        Lexeme var2 = var1.getLeft();
        if (var2 != null) {
            if (var1.getRight() == null) {
                System.out.print(var2.getValue());
            } else {
                System.out.print(var2.getValue() + " ");
                this.displayOptParameterList(var1.getRight());
            }
        }

    }

    private void displayOptElse(Lexeme var1) {
        System.out.print("else ");
        if (var1.getLeft().check("IF")) {
            this.displayIf(var1.getLeft());
        } else {
            this.displayBlock(var1.getLeft());
        }

    }

    private void displayAnonymousDefine(Lexeme var1) {
        System.out.print("anonymous ");
        this.displayParameterExpression(var1.getLeft());
        this.displayBlock(var1.getRight());
    }

    private void displayAnonymousCall(Lexeme var1) {
        System.out.print("(");
        System.out.print("anonymous ");
        this.displayParameterExpression(var1.getLeft());
        this.displayBlock(var1.getRight().getLeft());
        this.displayInitializerExpression(var1.getRight().getRight());
        System.out.print(")");
    }

}

*/