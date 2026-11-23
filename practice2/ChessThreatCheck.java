import java.util.Scanner;


public class ChessThreatCheck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // читаем две клетки, например: b2 e5
        String first = sc.next();
        String second = sc.next();

        // переводим клетки в номера столбцов и строк
        int col1 = first.charAt(0) - 'a' + 1;
        int row1 = first.charAt(1) - '0';
        int col2 = second.charAt(0) - 'a' + 1;
        int row2 = second.charAt(1) - '0';

        // считаем расстояние по столбцам и по строкам
        int dCol = Math.abs(col1 - col2);
        int dRow = Math.abs(row1 - row2);

        // одна горизонталь: строки совпадают
        // одна вертикаль: столбцы совпадают
        // одна диагональ: расстояния по столбцам и строкам равны
        if (row1 == row2 || col1 == col2 || dCol == dRow) {
            System.out.println("да");
        } else {
            System.out.println("нет");
        }

        sc.close();
    }
}
