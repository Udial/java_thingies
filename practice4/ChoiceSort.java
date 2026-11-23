import java.util.Scanner;

public class ChoiceSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // читаем массив
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // счётчики
        int comparisons = 0;
        int swaps = 0;

        // сортировка выбором
        for (int i = 0; i < n - 1; i++) {
            // ищем минимум в части массива от i до конца
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++; // сравниваем два элемента
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // меняем местами, только если минимум не на своём месте
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                swaps++;
            }
        }

        // выводим отсортированный массив
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        // выводим метрики
        System.out.println("Сравнений: " + comparisons);
        System.out.println("Обменов: " + swaps);

        sc.close();
    }
}
