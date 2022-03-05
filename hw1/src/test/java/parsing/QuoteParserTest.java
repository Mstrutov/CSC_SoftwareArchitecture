package parsing;

import org.junit.jupiter.api.Test;
import parsing.statements.QuotedStmt;
import parsing.statements.RawStmt;
import parsing.statements.parsed.FullQuotedString;
import parsing.statements.parsed.QuoteProcessedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.WeakQuotedString;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuoteParserTest {
    private static final QuoteParser quoteParser = new QuoteParser();

    @Test
    public void test() {
        testString("abc'de", List.of(new RawString("abc'de")));
        testString("\"abc'de\"", List.of(new WeakQuotedString("abc'de")));
        testString("'abc'de'", List.of(new FullQuotedString("abc"), new RawString("de'")));
        testString("abc''de", List.of(new RawString("abc"), new FullQuotedString(""), new RawString("de")));
        testString("echo \"abc\" 'def' ghi \"jkl\" 'mno'",
                List.of(new RawString("echo "), new WeakQuotedString("abc"),
                        new RawString(" "), new FullQuotedString("def"),
                        new RawString(" ghi "), new WeakQuotedString("jkl"),
                        new RawString(" "), new FullQuotedString("mno")));
        testString("", Collections.emptyList());
    }

    public void testString(String str, List<QuoteProcessedString> actualList) {
        QuotedStmt stmt = quoteParser.parse(new RawStmt(str));
        Iterator<QuoteProcessedString> actualIterator = actualList.iterator();
        while (actualIterator.hasNext() && stmt.hasNext()) {
            QuoteProcessedString actualNext = actualIterator.next();
            QuoteProcessedString expectedNext = stmt.next();
            assertEquals(actualNext.getClass(), expectedNext.getClass());
            assertEquals(actualNext.getString(), expectedNext.getString());
        }
        if (actualIterator.hasNext() || stmt.hasNext()) {
            fail("Lists have different size");
        }
    }
}
