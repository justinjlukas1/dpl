import java.io.*;

public class scanner extends lexer{

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);

        String i;
        String token;
        lexer(file);
    }
}
