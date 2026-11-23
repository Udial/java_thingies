// Класс, который хранит возрасты в отсортированном виде
public class SortedArray {
    private int[] data; // сам массив
    private int size;   // сколько элементов реально лежит в массиве

    public SortedArray() {
        data = new int[10];
        size = 0;
    }

    // ADD x: вставить x так, чтобы массив остался отсортированным
    public void add(int x) {
        // если место кончилось, создаём массив в 2 раза больше
        if (size == data.length) {
            int[] bigger = new int[data.length * 2];
            for (int i = 0; i < size; i++) {
                bigger[i] = data[i];
            }
            data = bigger;
        }

        // идём с конца и сдвигаем вправо все элементы больше x
        int i = size - 1;
        while (i >= 0 && data[i] > x) {
            data[i + 1] = data[i];
            i--;
        }

        // ставим x на освободившееся место
        data[i + 1] = x;
        size++;
    }

    // ищем индекс x бинарным поиском, если нет, возвращаем -1
    private int find(int x) {
        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int middle = (left + right) / 2;

            if (data[middle] == x) {
                return middle;
            } else if (data[middle] < x) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }

    // CONTAINS x: есть ли такой возраст
    public boolean contains(int x) {
        return find(x) != -1;
    }

    // REMOVE x: удалить один элемент x (если он есть)
    public void remove(int x) {
        int index = find(x);
        if (index == -1) {
            return; // такого нет, ничего не делаем
        }

        // сдвигаем все элементы после него на одну позицию влево
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
    }

    // COUNT_RANGE L R: сколько элементов от L до R включительно
    public int countRange(int l, int r) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (data[i] >= l && data[i] <= r) {
                count++;
            }
        }
        return count;
    }

    // PRINT: вернуть массив строкой через пробел
    public String print() {
        String result = "";
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result = result + " ";
            }
            result = result + data[i];
        }
        return result;
    }
}
