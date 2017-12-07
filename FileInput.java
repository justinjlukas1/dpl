import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.Scanner;

public class FileInput {
    private FileInputStream file;
    private Scanner scanner;
    private PushbackReader buffer;
    private Reader reader;
    private boolean endOfFile;

    public FileInput(String var1) {
        try {
            this.file = new FileInputStream(var1);
        } catch (IOException var3) {
            System.err.println(var3.getMessage());
            System.exit(1);
        }

        this.createScanner();
        this.createBuffer();
    }

    public boolean endOfFile() {
        return this.scanner.next() == null;
    }

    public String readNextString() {
        return this.scanner.next();
    }

    public Character readNextCharacter() throws EOFException {
        int var1 = 0;
        this.skipWhitespace();

        try {
            var1 = this.buffer.read();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        if (var1 == -1) {
            this.endOfFile = true;
        }

        return new Character((char)var1);
    }

    public Character readNextRawCharacter() throws EOFException {
        int var1 = 0;

        try {
            var1 = this.buffer.read();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        if (var1 == -1) {
            throw new EOFException();
        } else {
            return new Character((char)var1);
        }
    }

    public void pushbackCharacter(char var1) {
        try {
            this.buffer.unread(var1);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public void close() {
        this.scanner.close();

        try {
            this.reader.close();
        } catch (IOException var4) {
            System.err.println(var4.getMessage());
        }

        try {
            this.buffer.close();
        } catch (IOException var3) {
            System.err.println(var3.getMessage());
        }

        try {
            this.file.close();
        } catch (IOException var2) {
            System.err.println(var2.getMessage());
        }

    }

    public void skipWhitespace() throws EOFException {
        Character var1;
        for(var1 = this.readNextRawCharacter(); this.isWhitespace(var1) && !this.endOfFile; var1 = this.readNextRawCharacter()) {
            ;
        }

        this.pushbackCharacter(var1);
    }

    private boolean isWhitespace(Character var1) {
        return var1 == ' ' || var1 == '\t' || var1 == '\r';
    }

    private void createScanner() {
        this.scanner = new Scanner(this.file);
    }

    private void createBuffer() {
        this.reader = new InputStreamReader(this.file);
        this.buffer = new PushbackReader(this.reader, 10);
    }
}

