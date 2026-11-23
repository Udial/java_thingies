public class Gistogramma {
    public static void main(String[] args) {
        // измерения сигнала
        int[] values = {0, 2, 5};

        // проходим по всем значениям
        for (int i = 0; i < values.length; i++) {
            // отрицательные значения пропускаем
            if (values[i] < 0) {
                continue;
            }

            // собираем строку из плюсов нужной длины
            String line = "";
            for (int j = 0; j < values[i]; j++) {
                line = line + "+";
            }

            // выводим строку (для 0 будет пустая строка)
            System.out.println(line);
        }
    }
}
