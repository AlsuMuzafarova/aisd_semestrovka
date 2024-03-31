package aisd_semestrov.random_aisd;

import java.util.Random;

public class random {
    public static String generateRandomString(int n) {
        int leftLimit = 65; // numeral 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(n)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
    public static int generateRandomNumber(int min, int max){
        final int rnd = (int) (Math.random() * ++max) + min;
        return rnd;
    }
}
