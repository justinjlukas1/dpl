import java.util.List;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;
//import org.apache.commons.io.FileUtils;


public class Main {
    public static void main(String[] args) throws EOFException {
        FileInput file;
        file = new FileInput("testCode.jjl");

//        function scanner(filename)
//                {
//                        var token;
//        var i = new lexer(fileName);
//
//        token = i.lex();
//        while (token.type != ENDofINPUT)
//        {
//            Lexeme.display(token);
//            token = i.lex();
//        }
//        }

//        System.out.println("test");
        //String input = " if \"test\" 0 + 8 - / >= <= -9998 766; var xyz; func while \\Hello this is a comment :) \\ ";
//        Lexer l = new Lexer("testCode.jjl");
//        l.scanner();

        Lexer y = new Lexer("testCode.jjl");
        y.scanner();
//        Lexer l = new Lexer(input);
//        List<Lexeme> lexList = l.getLexes();
//
//        for (Lexeme lex: lexList) {
//            System.out.println(lex.toString());
//        }
//


//        Lexer l = new Lexer("testCode.jjl");
//        List<Lexeme> lexList = l.getLexes();
//
//        for (Lexeme lex: lexList) {
//            System.out.println(lex.toString());
//        }
//        new Lexer("testFile.java");
    }
//
//        public static void scanner(fileName) {
//            var token;
//        var i = new Lexer(fileName);
//
//        token = i.lex();
//        while (token.type != ENDofINPUT)
//        {
//            Lexeme.display(token);
//            token = i.lex();
//        }
//    }
//
//
//
//
//        String content = readFile("test.txt", Charset.defaultCharset());
//
//        public static String readFileToString(File file)
//                       throws IOException
//
//        Lexer l = new Lexer(input);
//        List<Lexeme> lexList = l.getLexes();
//
//        for (Lexeme lex: lexList) {
//            System.out.println(lex.toString());
//
//
//            String str = FileUtils.readFileToString(file);



        // Create a Scanner object for keyboard input.
//        Scanner keyboard = new Scanner(System.in);
//
//        // Get the filename.
//        System.out.print("Enter the filename: ");
//        String filename = keyboard.nextLine();
//        File file = new File("testCode.jjl");
//        try {
//            Scanner inputFile = new Scanner(file);
//            while (inputFile.hasNext())
//            {
//                // Read the next name.
//                String familyName = inputFile.nextLine();
//
//                // Display the last name read.
//                System.out.println(familyName);
//
//            }
//
//            // Close the file.
//            inputFile.close();
//        }
//        catch (FileNotFoundException ex)
//        {
//            // insert code to run when exception occurs
//        }

        // Read lines from the file until no more are left.



//        String input = "(+1 3)";
//        Lexer l = new Lexer(input);
//        List<Lexeme> lexList = l.getLexes();
//
//        for (Lexeme lex: lexList) {
//            System.out.println(lex.toString());
//        }
    }
