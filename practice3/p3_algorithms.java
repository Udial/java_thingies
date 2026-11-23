// Алгоритмы, сложность которых мы проверяем
public class p3_algorithms{

    // Задание 1. Доступ к элементу по индексу - O(1)
    public static int getElement(int[] array, int index) {
        return array[index];
    }

    // Задание 2. Бинарный поиск - O(log N)
    // массив должен быть отсортирован
    // возвращает индекс target или -1, если его нет
    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            // середина текущего диапазона
            int middle = (left + right) / 2;

            if (array[middle] == target) {
                return middle;
            } else if (array[middle] < target) {
                // ищем в правой половине
                left = middle + 1;
            } else {
                // ищем в левой половине
                right = middle - 1;
            }
        }

        // не нашли
        return -1;
    }

    // Задание 3. Линейный поиск - O(N)
    // идём по массиву слева направо, пока не найдём target
    public static int linearSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Задание 4. Подсчёт элементов больше target - O(N)
    // всегда проходим весь массив
    public static int countGreater(int[] array, int target) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > target) {
                count++;
            }
        }
        return count;
    }

    // Задание 5 (самостоятельное). Проверка, отсортирован ли массив - O(N)
    // идём по массиву и сравниваем соседние элементы
    // как только нашли пару "больше -> меньше", сразу выходим
    // лучший случай: нарушение в самом начале (1 сравнение)
    // худший случай: массив отсортирован (N - 1 сравнение)
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }
}
