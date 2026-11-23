import java.util.Scanner;


public class RightNeighbourTrade {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // читаем массив
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // находим максимальный элемент
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // номер раунда, когда максимум попал в конец
        // если он уже стоит в конце, раундов не нужно (0)
        int maxRound = -1;
        if (arr[n - 1] == max) {
            maxRound = 0;
        }

        int round = 0;
        boolean swapped = true;

        // делаем раунды, пока в раунде были обмены
        while (swapped) {
            swapped = false;

            // один раунд: идём слева направо и меняем с правым соседом
            for (int i = 0; i < n - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }

            // если обменов не было, массив уже отсортирован
            if (!swapped) {
                break;
            }

            round++;

            // выводим состояние массива после раунда
            System.out.print("Раунд " + round + ": ");
            for (int i = 0; i < n; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();

            // запоминаем раунд, когда максимум впервые оказался в конце
            if (maxRound == -1 && arr[n - 1] == max) {
                maxRound = round;
            }
        }

        System.out.println("Максимум " + max + " оказался в конце после раунда: " + maxRound);

        sc.close();
    }
}