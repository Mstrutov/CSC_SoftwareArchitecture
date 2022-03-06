package parsing;

import Utils.TestUtils;
import org.junit.jupiter.api.RepeatedTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReaderTest {

    private final TestUtils utils = new TestUtils(10, 0);

    @RepeatedTest(1000)
    public void readerTest() {
        String str = utils.randomString();
        InputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Reader reader = new Reader(byteArrayInputStream);
        String newStr = reader.read().getString();
        assertEquals(newStr, str);
    }

}
