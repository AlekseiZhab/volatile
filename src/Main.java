import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger lengthThree = new AtomicInteger(0);
    public static AtomicInteger lengthFour = new AtomicInteger(0);
    public static AtomicInteger lengthFive = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    checkTextLengthAndIncrementAtomicValue(text);
                }
            }
        }).start();
        new Thread(() -> {
            for (String text : texts) {
                if (isSameCharString(text)) {
                    checkTextLengthAndIncrementAtomicValue(text);
                }
            }
        }).start();
        new Thread(() -> {
            for (String text : texts) {
                if (isAscending(text)) {
                    checkTextLengthAndIncrementAtomicValue(text);
                }
            }
        }).start();
        printFinalResult();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String str) {
        StringBuilder reverseStringBuilder = new StringBuilder();
        reverseStringBuilder.append(str).reverse();
        return str.contentEquals(reverseStringBuilder);
    }

    public static boolean isSameCharString(String str) {
        char ch = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != ch) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscending(String str) {
        char ch = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (ch > str.charAt(i)) {
                return false;
            } else {
                ch = str.charAt(i);
            }
        }
        return true;
    }

    public static void printFinalResult() {
        System.out.println("Красивых слов с длиной 3: " + lengthThree + " шт.");
        System.out.println("Красивых слов с длиной 4: " + lengthFour + " шт.");
        System.out.println("Красивых слов с длиной 5: " + lengthFive + " шт.");
    }

    private static void checkTextLengthAndIncrementAtomicValue(String text) {
        switch (text.length()) {
            case 3 -> lengthThree.getAndIncrement();
            case 4 -> lengthFour.getAndIncrement();
            case 5 -> lengthFive.getAndIncrement();
        }
    }
}