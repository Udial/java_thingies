import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;

public class practice3{

    // размеры входа из задания
    static final int[] SIZES = {10, 100, 1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 64_000, 128_000};

    // сколько раз повторяем каждый опыт
    static final int REPEATS = 5;

    // сколько раз прогреваем JVM перед замерами для каждого N
    static final int WARMUP = 5;

    // одна операция занимает несколько наносекунд, а System.nanoTime
    // так точно не меряет, поэтому за один замер выполняем операцию много раз
    // и делим время на количество операций
    static final int OPS_FAST = 1_000_000; // для O(1) и O(log N)
    static final int OPS_LINEAR = 1_000;    // для O(N)

    // фиксированное зерно, чтобы результаты можно было повторить
    static Random random = new Random(42);

    // сюда складываем результаты, чтобы JIT не выкинул "ненужные" вычисления
    static long blackhole = 0;

    // один опыт: подготавливает данные для размера n,
    // замеряет время и возвращает среднее время одной операции в наносекундах
    interface Experiment {
        double run(int n);
    }

    public static void main(String[] args) throws IOException {
        // папка для таблиц с результатами
        new File("results").mkdirs();

        // Задание 1. O(1)
        measure("task1_access", "Задание 1. Доступ по индексу", Main::accessByIndex);

        // Задание 2. O(log N)
        measure("task2_first", "Задание 2. Бинарный поиск: первый элемент", n -> binarySearchExp(n, 0));
        measure("task2_random", "Задание 2. Бинарный поиск: случайный элемент", n -> binarySearchExp(n, 1));
        measure("task2_absent", "Задание 2. Бинарный поиск: отсутствующий элемент", n -> binarySearchExp(n, 2));

        // Задание 3. O(N)
        measure("task3_first", "Задание 3. Линейный поиск: первый элемент", n -> linearSearchExp(n, 0));
        measure("task3_random", "Задание 3. Линейный поиск: случайный элемент", n -> linearSearchExp(n, 1));
        measure("task3_absent", "Задание 3. Линейный поиск: отсутствующий элемент", n -> linearSearchExp(n, 2));

        // Задание 4. O(N)
        measure("task4_all", "Задание 4. Подсчёт: все элементы подходят", n -> countExp(n, 0));
        measure("task4_random", "Задание 4. Подсчёт: случайное число совпадений", n -> countExp(n, 1));
        measure("task4_none", "Задание 4. Подсчёт: ни один не подходит", n -> countExp(n, 2));

        // Задание 5. O(N)
        measure("task5_best", "Задание 5. Проверка сортировки: лучший случай", n -> isSortedExp(n, 0));
        measure("task5_average", "Задание 5. Проверка сортировки: средний случай", n -> isSortedExp(n, 1));
        measure("task5_worst", "Задание 5. Проверка сортировки: худший случай", n -> isSortedExp(n, 2));

        // печатаем, чтобы вычисления точно не были выброшены
        System.out.println("blackhole = " + blackhole);
    }

    // ===== Шаблон измерений =====
    // для каждого N: прогрев, потом REPEATS замеров, потом среднее
    static void measure(String fileName, String title, Experiment experiment) throws IOException {
        System.out.println();
        System.out.println(title);
        System.out.println("N\tзамер1\tзамер2\tзамер3\tзамер4\tзамер5\tсреднее (нс/операция)");

        PrintWriter out = new PrintWriter(new FileWriter("results/" + fileName + ".csv"));
        out.println("N;run1;run2;run3;run4;run5;avg");

        for (int n : SIZES) {
            // прогрев: результаты не записываем
            for (int i = 0; i < WARMUP; i++) {
                experiment.run(n);
            }

            // настоящие замеры
            double[] times = new double[REPEATS];
            double sum = 0;
            for (int i = 0; i < REPEATS; i++) {
                times[i] = experiment.run(n);
                sum += times[i];
            }
            double avg = sum / REPEATS;

            // вывод в консоль и в файл
            StringBuilder line = new StringBuilder();
            line.append(n);
            for (double t : times) {
                line.append(String.format(Locale.US, "\t%.3f", t));
            }
            line.append(String.format(Locale.US, "\t%.3f", avg));
            System.out.println(line);

            out.print(n);
            for (double t : times) {
                out.print(String.format(Locale.US, ";%.4f", t));
            }
            out.println(String.format(Locale.US, ";%.4f", avg));
        }

        out.close();
    }

    // ===== Задание 1. Доступ по индексу =====
    static double accessByIndex(int n) {
        // создаём и заполняем массив
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }

        // заранее готовим случайные индексы, чтобы генерация не попала в замер
        int[] indexes = new int[OPS_FAST];
        for (int i = 0; i < OPS_FAST; i++) {
            indexes[i] = random.nextInt(n);
        }

        long sum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < OPS_FAST; i++) {
            sum += Algorithms.getElement(array, indexes[i]);
        }
        long end = System.nanoTime();

        blackhole += sum;
        return (double) (end - start) / OPS_FAST;
    }

    // ===== Задание 2. Бинарный поиск =====
    // mode: 0 - первый элемент, 1 - случайный элемент, 2 - отсутствующий элемент
    static double binarySearchExp(int n, int mode) {
        // отсортированный массив из чётных чисел: 0, 2, 4, ...
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i * 2;
        }

        // готовим значения для поиска
        int[] targets = new int[OPS_FAST];
        for (int i = 0; i < OPS_FAST; i++) {
            if (mode == 0) {
                targets[i] = array[0];
            } else if (mode == 1) {
                targets[i] = array[random.nextInt(n)];
            } else {
                // нечётного числа в массиве точно нет
                targets[i] = random.nextInt(n) * 2 + 1;
            }
        }

        long sum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < OPS_FAST; i++) {
            sum += Algorithms.binarySearch(array, targets[i]);
        }
        long end = System.nanoTime();

        blackhole += sum;
        return (double) (end - start) / OPS_FAST;
    }

    // ===== Задание 3. Линейный поиск =====
    // mode: 0 - первый элемент, 1 - случайный элемент, 2 - отсутствующий элемент
    static double linearSearchExp(int n, int mode) {
        // массив из случайных чисел от 0 до 999_999
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(1_000_000);
        }

        int[] targets = new int[OPS_LINEAR];
        for (int i = 0; i < OPS_LINEAR; i++) {
            if (mode == 0) {
                targets[i] = array[0];
            } else if (mode == 1) {
                targets[i] = array[random.nextInt(n)];
            } else {
                // отрицательного числа в массиве нет
                targets[i] = -1;
            }
        }

        long sum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < OPS_LINEAR; i++) {
            sum += Algorithms.linearSearch(array, targets[i]);
        }
        long end = System.nanoTime();

        blackhole += sum;
        return (double) (end - start) / OPS_LINEAR;
    }

    // ===== Задание 4. Подсчёт элементов больше target =====
    // mode: 0 - все подходят, 1 - случайное количество, 2 - ни один не подходит
    static double countExp(int n, int mode) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(1_000_000);
        }

        int[] targets = new int[OPS_LINEAR];
        for (int i = 0; i < OPS_LINEAR; i++) {
            if (mode == 0) {
                targets[i] = -1;                         // все элементы больше -1
            } else if (mode == 1) {
                targets[i] = random.nextInt(1_000_000);  // часть больше, часть нет
            } else {
                targets[i] = 1_000_000;                  // ни один не больше
            }
        }

        long sum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < OPS_LINEAR; i++) {
            sum += Algorithms.countGreater(array, targets[i]);
        }
        long end = System.nanoTime();

        blackhole += sum;
        return (double) (end - start) / OPS_LINEAR;
    }

    // ===== Задание 5. Проверка, отсортирован ли массив =====
    // mode: 0 - лучший случай, 1 - средний случай, 2 - худший случай
    static double isSortedExp(int n, int mode) {
        // готовим 20 массивов (1000 массивов заняли бы слишком много памяти):
        // отсортированные, но с одним "сломанным" элементом в нужном месте
        int pool = 20;
        int[][] arrays = new int[pool][];
        for (int k = 0; k < pool; k++) {
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = i;
            }

            if (mode == 0) {
                // лучший: нарушение сразу во второй паре
                array[1] = -1;
            } else if (mode == 1) {
                // средний: нарушение в случайном месте
                int pos = 1 + random.nextInt(n - 1);
                array[pos] = -1;
            }
            // худший (mode == 2): массив полностью отсортирован

            arrays[k] = array;
        }

        long sum = 0;
        long start = System.nanoTime();
        for (int k = 0; k < OPS_LINEAR; k++) {
            // берём массивы по кругу
            if (Algorithms.isSorted(arrays[k % pool])) {
                sum++;
            }
        }
        long end = System.nanoTime();

        blackhole += sum;
        return (double) (end - start) / OPS_LINEAR;
    }
}
