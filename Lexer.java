//BenScriptLexer.java
//https://github.com/benjaminRomano/BenScript/blob/master/code/src/org/bromano/benscript/lexer/BenScriptLexer.java

import java.util.List;
import java.util.ArrayList;

public class Lexer {
    private String text;
    private int linePos;
    private int end;

    public Lexer(String source) {
        this.text = source;
        this.linePos = 0;
        this.end = source.length();
    }

    public List<Lexeme> getLexes() {
        List<Lexeme> lexemes = new ArrayList<>();

        Lexeme lexeme = this.lex();

        while(lexeme != null) {
            lexemes.add(lexeme);

            lexeme = this.lex();
        }

        return lexemes;
    }

    public Lexeme lex() {

        while (true) {

            if (this.linePos >= this.end) {
                return null;
            }

            char ch = this.text.charAt(this.linePos);

            switch (ch) {
                case '\n':
                case '\t':
                case ' ':
                    this.linePos++;
                    continue;
                case '+':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.Plus, "+");
                case '-':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.Minus, "-");
                case '*':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.Multiply, "*");
                case '/':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.Divide, "/");
                case '<':
                    this.linePos++;

                    if (checkNextChar('=')) {
                        this.linePos++;
                        return new Lexeme(Lexeme.kind.LessThanEqual, "<=");
                    }

                    return new Lexeme(Lexeme.kind.LessThan, "<");
                case '>':
                    this.linePos++;

                    if (checkNextChar('=')) {
                        this.linePos++;
                        return new Lexeme(Lexeme.kind.GreaterThanEqual, ">=");
                    }

                    return new Lexeme(Lexeme.kind.GreaterThan, ">");
                case '=':
                    this.linePos++;

                    if (checkNextChar('=')) {
                        this.linePos++;
                        return new Lexeme(Lexeme.kind.EqualEqual, "==");
                    }

                    return new Lexeme(Lexeme.kind.Equal, "=");
                case '!':
                    this.linePos++;

                    if (checkNextChar('=')) {
                        this.linePos++;
                        return new Lexeme(Lexeme.kind.ExclamationEqual, "!=");
                    }
                    // Something Bad happened if this is called...
                    return null;
                case ',':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.Comma, ",");
                case '.':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.Dot, ".");
                case ':':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.Colon, ":");
                case ';':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.SemiColon,";");
                case '(':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.OpenParen, "(");
                case ')':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.CloseParen, ")");
                case '[':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.OpenBracket, "[");
                case ']':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.CloseBracket, "]");
                case '{':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.OpenCurly, "{");
                case '}':
                    this.linePos++;
                    return new Lexeme(Lexeme.kind.CloseCurly, "}");
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    String s = readInteger();
                    return new Lexeme(Lexeme.kind.Integerkind, s);
                default:
                    String a = readString();
                    return createKeywordOrStringLexeme(a);
            }

        }
    }

    private boolean checkNextChar(char match) {

        return this.linePos < this.end && this.text.charAt(this.linePos) == match;
    }

    private String readInteger() {

        StringBuilder i = new StringBuilder();

        while (this.linePos < this.end) {
            char c = this.text.charAt(this.linePos);
            this.linePos++;

            if (c >= '0' && c <= '9') {
                i.append(c);
            }
            else {
                break;
            }
        }

        return i.toString();
    }

    private String readString() {

        StringBuilder i = new StringBuilder();

        while (this.linePos < this.end) {
            char c = this.text.charAt(this.linePos);
            this.linePos++;

            // Needed to remove leading " on string
            if (c == '\"') {
                c = this.text.charAt(this.linePos);
                this.linePos++;
            }

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                i.append(c);
            }
            else {
                break;
            }
        }

        return i.toString();
    }

    private Lexeme createKeywordOrStringLexeme(String value) {
        switch (value) {
            case "if":
                return new Lexeme(Lexeme.kind.IfKeyword, "if");
            case "else":
                return new Lexeme(Lexeme.kind.ElseKeyword, "else");
            case "for":
                return new Lexeme(Lexeme.kind.ForKeyword, "for");
            case "func":
                return new Lexeme(Lexeme.kind.FuncKeyword, "func");
            case "return":
                return new Lexeme(Lexeme.kind.ReturnKeyword, "return");
            case "var":
                String varName = readString();
                return new Lexeme(Lexeme.kind.VarKeyword, varName);
            default:
                return new Lexeme(Lexeme.kind.Stringkind, value);
        }
    }
}

//
//
//
//
//
//
//
//
//
//import java.io.*;
//import java.nio.charset.*;
//import java.lang.*;
//import java.util.*;
//
//
//public class lexer extends lexeme {
//
//    public static void lexer(File filename) throws IOException {
//        File file = filename;
//        Character[] chArray = readFile(file);
//        List<Character> charList = Arrays.asList(chArray);  //Zero based
//        lex(charList);
//    }
//
//    //Receive character, parse and analyze
//    public static int lex(List<Character> charList) {
//        List<Character> input = charList;
//        int size = input.size();
//        int index = 0;
//        while(index < input.size()) {
//            switch(input.get(index)) {
//                case '{':
//                    lexeme("OBRACE");
//                    index++;
//                    break;
//                case '}':
//                    lexeme("CBRACE");   //Signals ENDofINPUT?
//                    if(input.size() == size) {
//                        System.out.println("ENDofINPUT");
//                    }
//                    index++;
//                    break;
//                case '(':
//                    lexeme("OPAREN");
//                    index++;
//                    break;
//                case ')':
//                    lexeme("CPAREN");
//                    index++;
//                    break;
//                case ',':
//                    lexeme("COMMA");
//                    index++;
//                    break;
//                case ';':
//                    lexeme("SEMICOLON");
//                    index++;
//                    break;
//                case '<':
//                    lexeme("LESSTHAN");
//                    index++;
//                    break;
//                case '>':
//                    lexeme("GREATERTHAN");
//                    index++;
//                    break;
//                case '=':
//                    lexeme("ASSIGN");
//                    index++;
//                    break;
//                case '+':
//                    lexeme("PLUS");     //What about ++ and +=
//                    index++;
//                    break;
//                case '-':
//                    lexeme("MINUS");
//                    index++;
//                    break;
//                case '*':
//                    lexeme("MULTIPLY");
//                    index++;
//                    break;
//                case '/':
//                    lexeme("DIVISION");
//                    index++;
//                    break;
//                case '%':
//                    lexeme("MODULUS");
//                    index++;
//                    break;
//                //add any other single cases here
//
//                default:
//                    //System.out.println("Default case");
//                    if(Character.isWhitespace(input.get(index))) {
//                        //System.out.println("Whitespace");
//                    }
//                    else if(Character.isDigit(input.get(index))) {
//                        //System.out.println("Digit: " + input.get(index));
//                        //send lexNumber the list and the current index
//                        index = lexNumber(input, index);
//                    }
//                    else if(Character.isLetter(input.get(index))) {
//                        index = lexVariable(input, index);
//                        //index++;
//                        //return lexVariable(input.get(index));
//                    }
//                    else if(input.get(index) == '"') {
//                        index = lexString(input, index);
//                    }
//                    else
//                        System.out.println("ENDofINPUT");
//                        index++;
//            }
//        }
//        return 0;
//    }
//
//    private static boolean isWhitespace(Character ch) {
//        if(Character.isWhitespace(ch)) {
//            return true;
//        }
//        else
//            return false;
//    }
//
//    /* TODO */
//    private static int lexNumber(List<Character> input, int index) {
//        String digit = "";
//        digit += input.get(index);
//        while(Character.isDigit(input.get(index+1))) {
//            digit += input.get(index+1);
//            index += 1;
//        }
////        System.out.println("Digit: " + digit);
//        lexeme(digit);
//        return index;
//    }
//
//    private static int lexVariable(List<Character> input, int index) {
//        String variable = "";
//        variable += input.get(index);
//        while(Character.isLetter(input.get(index+1))) {
//            variable += input.get(index+1);
//            index += 1;
//        }
////        System.out.println("Variable : " + variable);
//        lexeme(variable);
//        return index;
//    }
//
//    private static int lexString(List<Character> input, int index) {
//        String quotes = "";
//        quotes += input.get(index);
//        index += 1;
//        while(input.get(index) != '"') {
//            quotes += input.get(index);
//            index += 1;
//        }
//        quotes += '"';
////        System.out.println("Quotes: " + quotes);
//        lexeme(quotes);
//        return index;
//    }
//
//    private static boolean skipWhitespace(Character ch) {
//        if(Character.isWhitespace(ch)) {
//            return true;
//        }
//        else if(ch == '#') {
//            return true;
//        }
//        return false;
//    }
//
//    private static Character[] readFile(File file) throws IOException {
//        int r;
//        Charset encoding = Charset.defaultCharset();
//        List<Character> input = new ArrayList<Character>();
//        System.out.println("Test file : " + file);
//        System.out.println("File encoding : " + encoding);
//        try {
//            InputStream stream = new FileInputStream(file);
//            Reader reader = new InputStreamReader(stream, encoding);
//            Reader buffer = new BufferedReader(reader);
//            while((r = buffer.read()) != -1) {
//                char ch = (char) r;
//                input.add(ch);
//            }
//            stream.close();
//            reader.close();
//            buffer.close();
//        }
//        catch (FileNotFoundException e) {
//            System.err.println(e);
//        }
//        return input.toArray(new Character[input.size()]);
//    }
//}
