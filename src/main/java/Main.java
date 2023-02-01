import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static AtomicInteger counter3 = new AtomicInteger(0);
    public static AtomicInteger counter4 = new AtomicInteger(0);
    public static AtomicInteger counter5 = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread paliandrome = new Thread(() -> {
            for (String nickname : texts) {
                if (isPalindrome(nickname) && !isSameChars(nickname)) incrementCounter(nickname.length());
            }
        });
        paliandrome.start();

        Thread sameChars = new Thread(() -> {
            for (String nickname : texts) {
                if (isSameChars(nickname)) incrementCounter(nickname.length());
            }
        });
        sameChars.start();

        Thread ascending = new Thread(() -> {
            for (String nickname : texts) {
                if (isAscending(nickname) && !isSameChars(nickname)) incrementCounter(nickname.length());
            }
        });
        ascending.start();

        paliandrome.join();
        sameChars.join();
        ascending.join();

        System.out.println("Красивых слов с длиной 3: " + counter3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter5 + " шт");

    }

    public static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isSameChars(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) != text.charAt(i + 1)) return false;
        }
        return true;
    }

    public static boolean isAscending(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) return false;
        }
        return true;
    }

    public static void incrementCounter(int length) {
        switch (length) {
            case 3:
                counter3.getAndIncrement();
                break;
            case 4:
                counter4.getAndIncrement();
                break;
            case 5:
                counter5.getAndIncrement();
                break;
        }
    }
}
