public class lexeme extends types {

    public static void lexeme(String token) {
        String type = token;
        String word;
        int value;
        double real;

// need to add real?
        if(Character.isDigit(token.charAt(0))) {
            type = "INTEGER";
            value = Integer.parseInt(token);
            System.out.println(type + " " + value);
        }
        if(Character.isLetter(token.charAt(0))) {
            type = "VARIABLE";
            word = token;
            System.out.println(type + " " + word);
        }
        if(token.charAt(0) == '"') {
            type = "STRING";
            word = token;
            System.out.println(type + " " + word);
        System.out.println(type);
        }
    }
}
