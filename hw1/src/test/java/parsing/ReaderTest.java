package parsing;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import parsing.statements.RawStmt;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    private static final String CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int RANDOM_STRING_LENGTH = 10;

    private static String randomString() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            int number = getRandomNumber();
            char ch = (char) ('a' + number);
            randomString.append(ch);
        }
        return randomString.toString();
    }

    private static int getRandomNumber() {
        int randomInt;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

    @RepeatedTest(10)
    public void readerTest() {
        String str = randomString();
        InputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        Reader reader = new Reader(byteArrayInputStream);
        assertEquals(reader.read().getString(), str);
    }

}
