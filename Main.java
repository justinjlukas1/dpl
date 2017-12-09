import java.io.*;

public class Main {
    public static void main(String[] args) throws EOFException {
        if (!args[0].endsWith(".jjl")) {
            Fatal.FATAL("Incorrect file type", -1);
        }

        Parser var1 = new Parser(args[0]);
        Lexeme var2 = var1.execute();

        //PrettyPrinter var3 = new PrettyPrinter();
        //var3.displayParseTree(var2);


        Parser l = new Parser("test.jjl");
    }
}