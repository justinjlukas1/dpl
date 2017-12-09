//https://github.com/benjaminRomano/BenScript/blob/master/code/src/org/bromano/benscript/lexer/Lexer.java
//Lexer.java


public class Lexeme {
    public kind type;
    protected Object value;
    protected int line;

    protected Lexeme right;
    protected Lexeme left;

    public Lexeme(kind var1, int var2) {
        this.type = var1;
        this.value = null;
        this.line = var2;
    }

    public Lexeme(kind var1, Object var2, int var3) {
        this.type = var1;
        this.value = var2;
        this.line = var3;
    }

    public kind getType() {
        return this.type;
    }

    public void setType(kind var1) {
        this.type = var1;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(String var1) {
        this.value = var1;
    }

    public Lexeme getLeft() {
        return this.left;
    }

    public void setLeft(Lexeme var1) {
        this.left = var1;
    }

    public Lexeme getRight() {
        return this.right;
    }

    public void setRight(Lexeme var1) {
        this.right = var1;
    }

    public void display() {
        System.out.print(this.type);
        if (this.value != null) {
            System.out.print(": " + this.value + " " + this.line + " ");
        }
        System.out.println();
    }

    public boolean check(String var1) {
        return this.type.equals(var1);
    }

    public int getLine() {
        return this.line;
    }

}