package main.java.parsing;

import java.io.InputStream;
import java.util.Scanner;
import main.java.parsing.statements.RawStmt;

public class Reader {
    Scanner scanner;

    public Reader(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public RawStmt read() {
        return new RawStmt(scanner.nextLine());
    }
}
