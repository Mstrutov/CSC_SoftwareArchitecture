import parsing.QuoteParser;
import parsing.Reader;
import parsing.statements.QuotedStmt;
import parsing.statements.RawStmt;

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
