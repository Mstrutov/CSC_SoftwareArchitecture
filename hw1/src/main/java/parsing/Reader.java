package parsing;

import parsing.statements.RawStmt;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Part of the REPL process. Reads a string from given {@code InputStream}.
 */
public class Reader {
    private final Scanner scanner;

    /**
     * Constructor of {@code Reader}.
     * @param inputStream Input of the program
     */
    public Reader(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    /**
     * Main method of the {@code Reader} class. Reads a string.
     * @return Input converted into raw string
     */
    public RawStmt read() {
        return new RawStmt(scanner.nextLine());
    }
}
