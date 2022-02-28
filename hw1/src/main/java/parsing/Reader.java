package parsing;

import parsing.statements.RawStmt;

import java.io.InputStream;
import java.util.Scanner;

public class Reader {
    Scanner scanner;

    public Reader(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public RawStmt read() {
        return new RawStmt(scanner.nextLine());
    }
}
