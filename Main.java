import java.util.List;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Create a Scanner object for keyboard input.
//        Scanner keyboard = new Scanner(System.in);
//
//        // Get the filename.
//        System.out.print("Enter the filename: ");
//        String filename = keyboard.nextLine();
        File file = new File("testCode.jjl");
        try {
            Scanner inputFile = new Scanner(file);
            while (inputFile.hasNext())
            {
                // Read the next name.
                String familyName = inputFile.nextLine();

                // Display the last name read.
                System.out.println(familyName);

            }

            // Close the file.
            inputFile.close();
        }
        catch (FileNotFoundException ex)
        {
            // insert code to run when exception occurs
        }

        // Read lines from the file until no more are left.



//        String input = "(+1 3)";
//        Lexer l = new Lexer(input);
//        List<Lexeme> lexList = l.getLexes();
//
//        for (Lexeme lex: lexList) {
//            System.out.println(lex.toString());
//        }
    }
}