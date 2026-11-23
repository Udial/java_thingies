import java.util.Scanner;


public class LastElementLessThanX {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // читаем размер массива и количество запросов
        int n = sc.nextInt();
        int q = sc.nextInt();

        // читаем массив
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // обрабатываем каждый запрос
        for (int j = 0; j < q; j++) {
            int x = sc.nextInt();

            // границы поиска
            int left = 0;
            int right = n - 1;

            // если ничего не найдём, ответ будет -1
            int answer = -1;

            // бинарный поиск
            while (left <= right) {
                int middle = (left + right) / 2;

                if (arr[middle] <= x) {
                    // элемент подходит, запоминаем его
                    answer = arr[middle];
                    // и ищем правее, вдруг там есть ещё подходящий
                    left = middle + 1;
                } else {
                    // элемент больше x, ищем левее
                    right = middle - 1;
                }
            }

            System.out.println(answer);
        }

        sc.close();
    }
}
