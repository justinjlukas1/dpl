//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class lexerHelper implements Lexer {
//
//    private String text;
//    private int lineNum;
//    private int lineStart;
//    private int pos;
//    private int end;
//    private Map<String, kind> keywordMap;
//
//    public lexerHelper() {
//        this.keywordMap = this.generateKeywordMap();
//    }
//
//    public lexerHelper(String text) {
//        this.setText(text);
//        this.keywordMap = this.generateKeywordMap();
//    }
//
//    @Override
//    public void setText(String text) {
//        this.text = text;
//        this.pos = 0;
//        this.lineNum = 1;
//        this.lineStart = 0;
//        this.end = this.text.length();
//    }
//
//    @Override
//    public Lexeme lex() throws LexerException {
//
//        while (true) {
//            String value;
//            linePosition linePos;
//
//            if (this.pos >= this.end) {
//                return null;
//            }
//
//            char ch = text.charAt(this.pos);
//
//            switch (ch) {
//                case '\n':
//                    this.pos++;
//                    this.lineNum++;
//                    this.lineStart = this.pos;
//                    continue;
//                case '\t':
//                case '\r':
//                case '\f':
//                case ' ':
//                    this.pos++;
//                    continue;
//                case '(':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.O_PAREN, linePos);
//                case ')':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.C_PAREN, linePos);
//                case '[':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.O_BRACE, linePos);
//                case ']':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.C_BRACE, linePos);
//                case '{':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.O_BRACKET, linePos);
//                case '}':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.C_BRACKET, linePos);
//                case '0':
//                case '1':
//                case '2':
//                case '3':
//                case '4':
//                case '5':
//                case '6':
//                case '7':
//                case '8':
//                case '9':
//                    linePos = getLinePosition();
//                    value = this.scanInteger(); //also need to check something like this.scanReal();
//                    return new Lexeme(kind.INTEGER, value, linePos);
//                    //really should be a function that keeps reading until not integer, then check to see if '.' character and then check to see if integers follow
//
//
//
//
//
//
//
//
//
//
//
//                case '!':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.ExclamationEquals, "!=", linePos);
//                    }
//
//                    return new Lexeme(kind.Exclamation, "!", linePos);
//                case '"':
//                case '\'':
//                    linePos = getLinePosition();
//                    value = this.scanString();
//
//                    return new Lexeme(kind.StringLiteral, value, linePos);
//                case '%':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.PercentEquals, "%=", linePos);
//                    }
//
//                    return new Lexeme(kind.Percent, "%", linePos);
//                case '&':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (!isAMatch(this.pos, "&")) {
//                        throw new LexerException(error("Ampersand Expected"));
//                    }
//
//                    this.pos++;
//                    return new Lexeme(kind.AmpersandAmpersand, "&&", linePos);
//                case '*':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.AsteriskEquals, "*=", linePos);
//                    }
//
//                    return new Lexeme(kind.Asterisk, "*", linePos);
//                case '+':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.PlusEquals, "+=", linePos);
//                    }
//
//                    return new Lexeme(kind.Plus, "+", linePos);
//                case ',':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.Comma, ",", linePos);
//                case '-':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.MinusEquals, "-=", linePos);
//                    }
//
//                    return new Lexeme(kind.Minus, "-", linePos);
//                case '.':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.Dot, ".", linePos);
//                case '/':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.SlashEquals, "/=", linePos);
//                    } else if (isAMatch(this.pos, "/")) {
//                        //Line comments
//                        this.pos++;
//
//                        while (this.pos < this.end) {
//                            if (isAMatch(this.pos, "\n")) {
//                                this.pos++;
//                                break;
//                            }
//                            this.pos++;
//                        }
//
//                        this.lineNum++;
//                        this.lineStart = this.pos;
//                        continue;
//                    } else if (isAMatch(this.pos, "*")) {
//                        //Block comments
//                        this.pos++;
//
//                        while (pos < end) {
//                            if (isAMatch(pos, "*/")) {
//                                this.pos += 2;
//                                break;
//                            }
//
//                            if (isAMatch(this.pos, "\n")) {
//                                this.pos++;
//                                this.lineNum++;
//                                this.lineStart = this.pos;
//                                continue;
//                            }
//
//                            this.pos++;
//                        }
//
//                        continue;
//                    }
//
//                    return new Lexeme(kind.Slash, "/", linePos);
//                case ':':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.Colon, ":", linePos);
//                case ';':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.Semicolon, ";", linePos);
//                case '<':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.LessThanEquals, "<=", linePos);
//                    }
//
//                    return new Lexeme(kind.LessThan, "<", linePos);
//                case '=':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.EqualsEquals, "==", linePos);
//                    } else if (this.isAMatch(this.pos, ">")) {
//                        this.pos++;
//                        return new Lexeme(kind.EqualsGreaterThan, "=>", linePos);
//                    }
//
//                    return new Lexeme(kind.Equals, "=", linePos);
//                case '>':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.GreaterThanEquals, ">=", linePos);
//                    }
//
//                    return new Lexeme(kind.GreaterThan, ">", linePos);
//                case '?':
//                    linePos = getLinePosition();
//                    this.pos++;
//                    return new Lexeme(kind.QuestionMark, "?", linePos);
//                case '^':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (isAMatch(this.pos, "=")) {
//                        this.pos++;
//                        return new Lexeme(kind.CaretEquals, "^=", linePos);
//                    }
//
//                    return new Lexeme(kind.Caret, "^", linePos);
//                case '_':
//                    linePos = getLinePosition();
//                    value = this.scanIdentifier();
//                    return new Lexeme(kind.Identifier, value, linePos);
//                case '|':
//                    linePos = getLinePosition();
//                    this.pos++;
//
//                    if (!isAMatch(this.pos, "|")) {
//                        throw new LexerException(error("Expected a |"));
//                    }
//
//                    this.pos++;
//                    return new Lexeme(kind.BarBar, "||", linePos);
//                default:
//                    linePos = getLinePosition();
//
//                    if (this.isAlphabetOrUnderscore(ch)) {
//
//                        String identifierOrKeyword = this.scanIdentifier();
//
//                        if (identifierOrKeyword.trim().toLowerCase().equals("true")
//                                || identifierOrKeyword.trim().toLowerCase().equals("false")) {
//                            return new Lexeme(kind.BooleanLiteral, identifierOrKeyword.trim().toLowerCase());
//                        }
//
//                        if (this.keywordMap.containsKey(identifierOrKeyword.trim().toLowerCase())) {
//                            return new Lexeme(keywordMap.get(identifierOrKeyword.trim().toLowerCase()), linePos);
//                        }
//
//                        return new Lexeme(kind.Identifier, identifierOrKeyword, linePos);
//                    }
//
//                    throw new LexerException(error("Char " + ch + " cannot be lexed"));
//
//            }
//        }
//    }
//
//    @Override
//    public List<Lexeme> getLexStream() throws LexerException {
//        List<Lexeme> lexemes = new ArrayList<>();
//
//        Lexeme lexeme = this.lex();
//
//        while (lexeme != null) {
//            lexemes.add(lexeme);
//
//            lexeme = this.lex();
//        }
//
//        return lexemes;
//    }
//
//    private boolean isAlphabetOrUnderscore(char ch) {
//        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_';
//    }
//
//    private boolean isDigit(char ch) {
//        return ch >= '0' && ch <= '9';
//    }
//
//    private String scanInteger() {
//        StringBuilder sb = new StringBuilder();
//
//        while (this.pos < this.end) {
//            char ch = this.text.charAt(this.pos);
//
//            if (sb.length() == 0 && ch == '0') {
//                this.pos++;
//                return "0";
//            } else if (this.isDigit(ch)) {
//                sb.append(ch);
//                this.pos++;
//            } else {
//                break;
//            }
//        }
//
//        return sb.toString();
//    }
//
//    private String scanIdentifier() throws LexerException {
//        StringBuilder sb = new StringBuilder();
//
//        boolean isFirstCharacter = true;
//
//        while (this.pos < this.end) {
//            char ch = text.charAt(this.pos);
//
//            if (this.isAlphabetOrUnderscore(ch)) {
//                isFirstCharacter = false;
//                sb.append(ch);
//                this.pos++;
//            } else if (isDigit(ch)) {
//                if (isFirstCharacter) {
//                    throw new LexerException("Identifier cannot start with a digit");
//                }
//                sb.append(ch);
//                this.pos++;
//            } else {
//                break;
//            }
//        }
//
//        return sb.toString();
//    }
//
//    private String scanString() throws LexerException {
//
//        if (this.pos >= this.end) {
//            return "";
//        }
//
//        char quote = this.text.charAt(this.pos);
//        this.pos++;
//
//        if (quote != '"' && quote != '\'') {
//            throw new LexerException("String must start with a quote");
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        boolean hasClosingQuote = false;
//
//        while (this.pos < this.end) {
//            char ch = this.text.charAt(this.pos);
//
//            if (isAMatch(this.pos, "\\")) {
//                sb.append(scanEscapeSequence());
//            } else if (isAMatch(this.pos, "\n")) {
//                throw new LexerException("Unexpected new-line character in string");
//            } else if (quote == ch) {
//                this.pos++;
//                hasClosingQuote = true;
//                break;
//            } else {
//                sb.append(ch);
//                this.pos++;
//            }
//        }
//
//        if (!hasClosingQuote) {
//            throw new LexerException("String-literal is missing closing quote");
//        }
//
//        return sb.toString();
//    }
//
//    private String scanEscapeSequence() throws LexerException {
//
//        StringBuilder sb = new StringBuilder();
//
//        if (!isAMatch(this.pos, "\\")) {
//            throw new LexerException("Expected backslash in escape sequence");
//        }
//
//        String escapeSequence = "";
//
//        escapeSequence += this.text.charAt(this.pos);
//        this.pos++;
//
//        if (!isAMatch(this.pos, new char[]{'\'', '"', '\\', 't', 'n'})) {
//            throw new LexerException(error("Unexpected char " + this.text.charAt(this.pos) + " found in escape sequence"));
//        }
//
//
//        escapeSequence += this.text.charAt(this.pos);
//        this.pos++;
//
//        switch (escapeSequence) {
//            case "\\":
//                sb.append('\\');
//                break;
//            case "\\\"":
//                sb.append('\"');
//                break;
//            case "\\\\":
//                sb.append('\\');
//                break;
//            case "\\t":
//                sb.append('\t');
//                break;
//            case "\\n":
//                sb.append('\n');
//                break;
//        }
//
//        return sb.toString();
//    }
//
//    private boolean isAMatch(int start, String sequence) {
//        int end = start + sequence.length();
//        return start < this.end && end <= this.end
//                && text.substring(start, end).equals(sequence);
//    }
//
//    private boolean isAMatch(int start, char[] chars) {
//        if (start >= this.end) {
//            return false;
//        }
//
//        char currChar = text.charAt(this.pos);
//
//        for (char ch : chars) {
//            if (currChar == ch) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    private linePosition getLinePosition() {
//        return new linePosition(this.lineNum, this.pos - this.lineStart);
//    }
//
//    private String error(String s) {
//        return getLinePosition().toString() + " " + s;
//    }
//
//    private Map<String, kind> generateKeywordMap() {
//        Map<String, kind> keywordMap = new HashMap<>();
//        keywordMap.put("null", kind.NullKeyword);
//        keywordMap.put("if", kind.IfKeyword);
//        keywordMap.put("var", kind.VarKeyword);
//        keywordMap.put("func", kind.FuncKeyword);
//        keywordMap.put("lfunc", kind.LFuncKeyword);
//        keywordMap.put("break", kind.BreakKeyword);
//        keywordMap.put("return", kind.ReturnKeyword);
//        keywordMap.put("continue", kind.ContinueKeyword);
//        keywordMap.put("else", kind.ElseKeyword);
//        keywordMap.put("for", kind.ForKeyword);
//        keywordMap.put("while", kind.WhileKeyword);
//
//        return keywordMap;
//    }
//}
//

//
//
//
//    private String text;
//    private int lineNum;
//    private int lineStart;
//    private int pos;
//    private int end;
//    private Map<String, kind> keywordMap;
//
//    public ScriptLexer() {
//        this.keywordMap = this.generateKeywordMap();
//    }
//
//    public ScriptLexer(String text) {
//        this.setText(text);
//        this.keywordMap = this.generateKeywordMap();
//    }
//
//
//    public Lexer(String source) {
//        this.text = source;
//        this.linePos = 0;
//        this.end = source.length();
//    }
//
//    public List<Lexeme> getLexes() {
//        List<Lexeme> lexemes = new ArrayList<>();
//
//        Lexeme lexeme = this.lex();
//
//        while(lexeme != null) {
//            lexemes.add(lexeme);
//
//            lexeme = this.lex();
//        }
//
//        return lexemes;
//    }
//
//    public Lexeme lex() {
//
//        while (true) {
//
//            if (this.linePos >= this.end) {
//                return null;
//            }
//
//            char ch = this.text.charAt(this.linePos);
//
//            switch (ch) {
//                case '\n':
//                case '\t':
//                case ' ':
//                    this.linePos++;
//                    continue;
//                case '[':
//                    this.linePos++;
//                    return new Lexeme(kind.O_BRACE);
//                case ']':
//                    this.linePos++;
//                    return new Lexeme(kind.C_BRACE);
//                case '(':
//                    this.linePos++;
//                    return new Lexeme(kind.O_PAREN);
//                case ')':
//                    this.linePos++;
//                    return new Lexeme(kind.C_PAREN);
//                case '{':
//                    this.linePos++;
//                    return new Lexeme(kind.O_BRACKET);
//                case '}':
//                    this.linePos++;
//                    return new Lexeme(kind.C_BRACKET);
//
//
//
//
////                case '+':
////                    this.linePos++;
////                    return new Lexeme(kind.Plus, "+");
////                case '-':
////                    this.linePos++;
////                    return new Lexeme(kind.Minus, "-");
////                case '*':
////                    this.linePos++;
////                    return new Lexeme(kind.Multiply, "*");
////                case '/':
////                    this.linePos++;
////                    return new Lexeme(kind.Divide, "/");
////                case '<':
////                    this.linePos++;
////
////                    if (checkNextChar('=')) {
////                        this.linePos++;
////                        return new Lexeme(kind.LessThanEqual, "<=");
////                    }
////
////                    return new Lexeme(kind.LessThan, "<");
////                case '>':
////                    this.linePos++;
////
////                    if (checkNextChar('=')) {
////                        this.linePos++;
////                        return new Lexeme(kind.GreaterThanEqual, ">=");
////                    }
////
////                    return new Lexeme(kind.GreaterThan, ">");
////                case '=':
////                    this.linePos++;
////
////                    if (checkNextChar('=')) {
////                        this.linePos++;
////                        return new Lexeme(kind.EqualEqual, "==");
////                    }
////
////                    return new Lexeme(kind.Equal, "=");
////                case '!':
////                    this.linePos++;
////
////                    if (checkNextChar('=')) {
////                        this.linePos++;
////                        return new Lexeme(kind.ExclamationEqual, "!=");
////                    }
////                    // Something Bad happened if this is called...
////                    return null;
////                case ',':
////                    this.linePos++;
////                    return new Lexeme(kind.Comma, ",");
////                case '.':
////                    this.linePos++;
////                    return new Lexeme(kind.Dot, ".");
////                case ':':
////                    this.linePos++;
////                    return new Lexeme(kind.Colon, ":");
////                case ';':
////                    this.linePos++;
////                    return new Lexeme(kind.SemiColon,";");
////                case '(':
////                    this.linePos++;
////                    return new Lexeme(kind.OpenParen, "(");
////                case ')':
////                    this.linePos++;
////                    return new Lexeme(kind.CloseParen, ")");
////                case '[':
////                    this.linePos++;
////                    return new Lexeme(kind.OpenBracket, "[");
////                case ']':
////                    this.linePos++;
////                    return new Lexeme(kind.CloseBracket, "]");
////                case '{':
////                    this.linePos++;
////                    return new Lexeme(kind.OpenCurly, "{");
////                case '}':
////                    this.linePos++;
////                    return new Lexeme(kind.CloseCurly, "}");
////                case '0':
////                case '1':
////                case '2':
////                case '3':
////                case '4':
////                case '5':
////                case '6':
////                case '7':
////                case '8':
////                case '9':
////                    String s = readInteger();
////                    return new Lexeme(kind.Integerkind, s);
////                default:
////                    String a = readString();
////                    return createKeywordOrStringLexeme(a);
//            }
//
//        }
//    }
//
//    private boolean checkNextChar(char match) {
//
//        return this.linePos < this.end && this.text.charAt(this.linePos) == match;
//    }
//
//    private String readInteger() {
//
//        StringBuilder i = new StringBuilder();
//
//        while (this.linePos < this.end) {
//            char c = this.text.charAt(this.linePos);
//            this.linePos++;
//
//            if (c >= '0' && c <= '9') {
//                i.append(c);
//            }
//            else {
//                break;
//            }
//        }
//
//        return i.toString();
//    }
//
//    private String readString() {
//
//        StringBuilder i = new StringBuilder();
//
//        while (this.linePos < this.end) {
//            char c = this.text.charAt(this.linePos);
//            this.linePos++;
//
//            // Needed to remove leading " on string
//            if (c == '\"') {
//                c = this.text.charAt(this.linePos);
//                this.linePos++;
//            }
//
//            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
//                i.append(c);
//            }
//            else {
//                break;
//            }
//        }
//
//        return i.toString();
//    }
//
//    private Lexeme createKeywordOrStringLexeme(String value) {
//        switch (value) {
//            case "if":
//                return new Lexeme(kind.IfKeyword, "if");
//            case "else":
//                return new Lexeme(kind.ElseKeyword, "else");
//            case "for":
//                return new Lexeme(kind.ForKeyword, "for");
//            case "func":
//                return new Lexeme(kind.FuncKeyword, "func");
//            case "return":
//                return new Lexeme(kind.ReturnKeyword, "return");
//            case "var":
//                String varName = readString();
//                return new Lexeme(kind.VarKeyword, varName);
//            default:
//                return new Lexeme(kind.Stringkind, value);
//        }
//    }
//}
//
////
////
////
////
////
////
////
////
////
////import java.io.*;
////import java.nio.charset.*;
////import java.lang.*;
////import java.util.*;
////
////
////public class lexer extends lexeme {
////
////    public static void lexer(File filename) throws IOException {
////        File file = filename;
////        Character[] chArray = readFile(file);
////        List<Character> charList = Arrays.asList(chArray);  //Zero based
////        lex(charList);
////    }
////
////    //Receive character, parse and analyze
////    public static int lex(List<Character> charList) {
////        List<Character> input = charList;
////        int size = input.size();
////        int index = 0;
////        while(index < input.size()) {
////            switch(input.get(index)) {
////                case '{':
////                    lexeme("OBRACE");
////                    index++;
////                    break;
////                case '}':
////                    lexeme("CBRACE");   //Signals ENDofINPUT?
////                    if(input.size() == size) {
////                        System.out.println("ENDofINPUT");
////                    }
////                    index++;
////                    break;
////                case '(':
////                    lexeme("OPAREN");
////                    index++;
////                    break;
////                case ')':
////                    lexeme("CPAREN");
////                    index++;
////                    break;
////                case ',':
////                    lexeme("COMMA");
////                    index++;
////                    break;
////                case ';':
////                    lexeme("SEMICOLON");
////                    index++;
////                    break;
////                case '<':
////                    lexeme("LESSTHAN");
////                    index++;
////                    break;
////                case '>':
////                    lexeme("GREATERTHAN");
////                    index++;
////                    break;
////                case '=':
////                    lexeme("ASSIGN");
////                    index++;
////                    break;
////                case '+':
////                    lexeme("PLUS");     //What about ++ and +=
////                    index++;
////                    break;
////                case '-':
////                    lexeme("MINUS");
////                    index++;
////                    break;
////                case '*':
////                    lexeme("MULTIPLY");
////                    index++;
////                    break;
////                case '/':
////                    lexeme("DIVISION");
////                    index++;
////                    break;
////                case '%':
////                    lexeme("MODULUS");
////                    index++;
////                    break;
////                //add any other single cases here
////
////                default:
////                    //System.out.println("Default case");
////                    if(Character.isWhitespace(input.get(index))) {
////                        //System.out.println("Whitespace");
////                    }
////                    else if(Character.isDigit(input.get(index))) {
////                        //System.out.println("Digit: " + input.get(index));
////                        //send lexNumber the list and the current index
////                        index = lexNumber(input, index);
////                    }
////                    else if(Character.isLetter(input.get(index))) {
////                        index = lexVariable(input, index);
////                        //index++;
////                        //return lexVariable(input.get(index));
////                    }
////                    else if(input.get(index) == '"') {
////                        index = lexString(input, index);
////                    }
////                    else
////                        System.out.println("ENDofINPUT");
////                        index++;
////            }
////        }
////        return 0;
////    }
////
////    private static boolean isWhitespace(Character ch) {
////        if(Character.isWhitespace(ch)) {
////            return true;
////        }
////        else
////            return false;
////    }
////
////    /* TODO */
////    private static int lexNumber(List<Character> input, int index) {
////        String digit = "";
////        digit += input.get(index);
////        while(Character.isDigit(input.get(index+1))) {
////            digit += input.get(index+1);
////            index += 1;
////        }
//////        System.out.println("Digit: " + digit);
////        lexeme(digit);
////        return index;
////    }
////
////    private static int lexVariable(List<Character> input, int index) {
////        String variable = "";
////        variable += input.get(index);
////        while(Character.isLetter(input.get(index+1))) {
////            variable += input.get(index+1);
////            index += 1;
////        }
//////        System.out.println("Variable : " + variable);
////        lexeme(variable);
////        return index;
////    }
////
////    private static int lexString(List<Character> input, int index) {
////        String quotes = "";
////        quotes += input.get(index);
////        index += 1;
////        while(input.get(index) != '"') {
////            quotes += input.get(index);
////            index += 1;
////        }
////        quotes += '"';
//////        System.out.println("Quotes: " + quotes);
////        lexeme(quotes);
////        return index;
////    }
////
////    private static boolean skipWhitespace(Character ch) {
////        if(Character.isWhitespace(ch)) {
////            return true;
////        }
////        else if(ch == '#') {
////            return true;
////        }
////        return false;
////    }
////
////    private static Character[] readFile(File file) throws IOException {
////        int r;
////        Charset encoding = Charset.defaultCharset();
////        List<Character> input = new ArrayList<Character>();
////        System.out.println("Test file : " + file);
////        System.out.println("File encoding : " + encoding);
////        try {
////            InputStream stream = new FileInputStream(file);
////            Reader reader = new InputStreamReader(stream, encoding);
////            Reader buffer = new BufferedReader(reader);
////            while((r = buffer.read()) != -1) {
////                char ch = (char) r;
////                input.add(ch);
////            }
////            stream.close();
////            reader.close();
////            buffer.close();
////        }
////        catch (FileNotFoundException e) {
////            System.err.println(e);
////        }
////        return input.toArray(new Character[input.size()]);
////    }
//}