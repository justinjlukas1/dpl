import java.io.*;

public class Main {
    public static void main(String[] args) throws EOFException {
            //don't have input yet
//        if (!args[0].endsWith(".jjl")) {
//            Fatal.FATAL("Incorrect file type", -1);
//        }

        //Parser var1 = new Parser(args[0]);
        Parser var1 = new Parser("testCode.jjl");
        Lexeme var2 = var1.execute();

        //PrettyPrinter var3 = new PrettyPrinter();
        //var3.displayParseTree(var2);


        //Parser l = new Parser("test.jjl");
    }
}