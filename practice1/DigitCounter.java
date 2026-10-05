public class DigitCounter {

    public static int countDigits(String s) {
        return countDigits(s, 0);
    }

    private static int countDigits(String s, int index) {
        if (index == s.length()) {
            return 0;
        }

        int result = countDigits(s, index + 1);

        if (s.charAt(index) >= '0' && s.charAt(index) <= '9') {
            result++;
        }

        return result;
    }
}
