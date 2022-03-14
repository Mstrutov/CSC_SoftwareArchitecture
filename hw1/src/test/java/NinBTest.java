import Utils.TestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NinBTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeAll
    static void init() {
        TestUtils testUtils = new TestUtils(10, 0);
        String userInput = IntStream
                .range(0, 1000)
                .mapToObj(i -> testUtils.randomString())
                .collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator() + "exit";
        System.setIn(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));
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