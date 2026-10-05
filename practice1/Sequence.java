public class Sequence {

    public static int s(int n) {
        if (n == 0) {
            return 2;
        }

        if (n == 1) {
            return 3;
        }

        return s(n - 1) + 2 * s(n - 2);
    }
}