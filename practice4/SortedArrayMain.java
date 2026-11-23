import java.util.Scanner;

public class SortedArrayMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // читаем количество чисел и количество команд
        int n = sc.nextInt();
        int q = sc.nextInt();

        // складываем исходные возрасты, add сам их сортирует
        SortedArray ages = new SortedArray();
        for (int i = 0; i < n; i++) {
            ages.add(sc.nextInt());
        }

        // обрабатываем команды
        for (int i = 0; i < q; i++) {
            String command = sc.next();

            if (command.equals("ADD")) {
                ages.add(sc.nextInt());
            } else if (command.equals("CONTAINS")) {
                if (ages.contains(sc.nextInt())) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            } else if (command.equals("REMOVE")) {
                ages.remove(sc.nextInt());
            } else if (command.equals("COUNT_RANGE")) {
                int l = sc.nextInt();
                int r = sc.nextInt();
                System.out.println(ages.countRange(l, r));
            } else if (command.equals("PRINT")) {
                System.out.println(ages.print());
            }
        }

        sc.close();
    }
}