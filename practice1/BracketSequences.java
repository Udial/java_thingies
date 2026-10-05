import java.util.ArrayList;
import java.util.List;

public class BracketSequences {

    public static List<String> generate(int n) {
        List<String> result = new ArrayList<>();

        generateBrackets(
                "",
                0,
                0,
                n,
                result
        );

        return result;
    }

    private static void generateBrackets(
            String current,
            int opened,
            int closed,
            int n,
            List<String> result
    ) {
        if (current.length() == 2 * n) {
            result.add(current);
            return;
        }

        if (opened < n) {
            generateBrackets(
                    current + "(",
                    opened + 1,
                    closed,
                    n,
                    result
            );
        }

        if (closed < opened) {
            generateBrackets(
                    current + ")",
                    opened,
                    closed + 1,
                    n,
                    result
            );
        }
    }
}