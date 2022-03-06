package Utils;

import java.util.Random;

public class TestUtils {
    private final int RANDOM_STRING_LENGTH;
    private final Random randomGenerator;

    public TestUtils(int stringLength, int seed) {
        this.RANDOM_STRING_LENGTH = stringLength;
        randomGenerator = new Random(seed);
    }

    public String randomString() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            int number = getRandomNumber();
            char ch = (char) ('a' + number);
            randomString.append(ch);
        }
        return randomString.toString();
    }

    private int getRandomNumber() {
        int randomInt;
        randomInt = randomGenerator.nextInt(25);
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
}
