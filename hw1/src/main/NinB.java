package main;

import main.parsing.QuoteParser;
import main.parsing.Reader;
import main.parsing.statements.QuotedStmt;
import main.parsing.statements.RawStmt;

public class NinB {
    public static void main(String[] args) {
        Reader reader = new Reader(System.in);
        QuoteParser quoteParser = new QuoteParser();
        while (true) {
            RawStmt rawStmt = reader.read();
            QuotedStmt quotedStmt = quoteParser.parse(rawStmt);
//            while (quotedStmt.hasNext()) {
//                System.out.println(quotedStmt.next().getString());
//            }
        }
    }
}
