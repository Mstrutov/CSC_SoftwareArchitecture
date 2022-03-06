import Utils.TestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NinBTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeAll
    static void init() {
        TestUtils testUtils = new TestUtils(10, 0);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(testUtils.randomString());
        }
        list.add("exit");
        System.setIn(new ByteArrayInputStream(list.stream().collect(Collectors.joining(System.lineSeparator())).getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    static void end() {
        System.setIn(System.in);
        System.setOut(System.out);
        System.setOut(System.err);
    }

    @Test
    public void ninBTest() {
        assertDoesNotThrow(() -> NinB.main(null));
    }

}