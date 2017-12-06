import java.io.*;
import java.nio.charset.*;
import java.lang.*;
import java.util.*;

public class lexer extends lexeme {

    public static void lexer(File filename) throws IOException {
        File file = filename;
        Character[] chArray = readFile(file);
        List<Character> charList = Arrays.asList(chArray);  //Zero based
        lex(charList);
    }

    //Receive character, parse and analyze
    public static int lex(List<Character> charList) {
        List<Character> input = charList;
        int size = input.size();
        int index = 0;
        while(index < input.size()) {
            switch(input.get(index)) {
                case '{':
                    lexeme("OBRACE");
                    index++;
                    break;
                case '}':
                    lexeme("CBRACE");   //Signals ENDofINPUT?
                    if(input.size() == size) {
                        System.out.println("ENDofINPUT");
                    }
                    index++;
                    break;
                case '(':
                    lexeme("OPAREN");
                    index++;
                    break;
                case ')':
                    lexeme("CPAREN");
                    index++;
                    break;
                case ',':
                    lexeme("COMMA");
                    index++;
                    break;
                case ';':
                    lexeme("SEMICOLON");
                    index++;
                    break;
                case '<':
                    lexeme("LESSTHAN");
                    index++;
                    break;
                case '>':
                    lexeme("GREATERTHAN");
                    index++;
                    break;
                case '=':
                    lexeme("ASSIGN");
                    index++;
                    break;
                case '+':
                    lexeme("PLUS");     //What about ++ and +=
                    index++;
                    break;
                case '-':
                    lexeme("MINUS");
                    index++;
                    break;
                case '*':
                    lexeme("MULTIPLY");
                    index++;
                    break;
                case '/':
                    lexeme("DIVISION");
                    index++;
                    break;
                case '%':
                    lexeme("MODULUS");
                    index++;
                    break;
                //add any other single cases here

                default:
                    //System.out.println("Default case");
                    if(Character.isWhitespace(input.get(index))) {
                        //System.out.println("Whitespace");
                    }
                    else if(Character.isDigit(input.get(index))) {
                        //System.out.println("Digit: " + input.get(index));
                        //send lexNumber the list and the current index
                        index = lexNumber(input, index);
                    }
                    else if(Character.isLetter(input.get(index))) {
                        index = lexVariable(input, index);
                        //index++;
                        //return lexVariable(input.get(index));
                    }
                    else if(input.get(index) == '"') {
                        index = lexString(input, index);
                    }
                    else
                        System.out.println("ENDofINPUT");
                        index++;
            }
        }
        return 0;
    }

    private static boolean isWhitespace(Character ch) {
        if(Character.isWhitespace(ch)) {
            return true;
        }
        else
            return false;
    }

    /* TODO */
    private static int lexNumber(List<Character> input, int index) {
        String digit = "";
        digit += input.get(index);
        while(Character.isDigit(input.get(index+1))) {
            digit += input.get(index+1);
            index += 1;
        }
//        System.out.println("Digit: " + digit);
        lexeme(digit);
        return index;
    }

    private static int lexVariable(List<Character> input, int index) {
        String variable = "";
        variable += input.get(index);
        while(Character.isLetter(input.get(index+1))) {
            variable += input.get(index+1);
            index += 1;
        }
//        System.out.println("Variable : " + variable);
        lexeme(variable);
        return index;
    }

    private static int lexString(List<Character> input, int index) {
        String quotes = "";
        quotes += input.get(index);
        index += 1;
        while(input.get(index) != '"') {
            quotes += input.get(index);
            index += 1;
        }
        quotes += '"';
//        System.out.println("Quotes: " + quotes);
        lexeme(quotes);
        return index;
    }

    private static boolean skipWhitespace(Character ch) {
        if(Character.isWhitespace(ch)) {
            return true;
        }
        else if(ch == '#') {
            return true;
        }
        return false;
    }

    private static Character[] readFile(File file) throws IOException {
        int r;
        Charset encoding = Charset.defaultCharset();
        List<Character> input = new ArrayList<Character>();
        System.out.println("Test file : " + file);
        System.out.println("File encoding : " + encoding);
        try {
            InputStream stream = new FileInputStream(file);
            Reader reader = new InputStreamReader(stream, encoding);
            Reader buffer = new BufferedReader(reader);
            while((r = buffer.read()) != -1) {
                char ch = (char) r;
                input.add(ch);
            }
            stream.close();
            reader.close();
            buffer.close();
        }
        catch (FileNotFoundException e) {
            System.err.println(e);
        }
        return input.toArray(new Character[input.size()]);
    }
}
