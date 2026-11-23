import java.util.Scanner;


ublic class ChessCheck1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // читаем позиции фигур, например: a1 a5 c3
        String rook = sc.next();
        String bishop = sc.next();
        String knight = sc.next();

        // переводим буквы в номера столбцов (a = 1, b = 2, ...)
        // а цифры в номера строк
        int rookCol = rook.charAt(0) - 'a' + 1;
        int rookRow = rook.charAt(1) - '0';
        int bishopCol = bishop.charAt(0) - 'a' + 1;
        int bishopRow = bishop.charAt(1) - '0';
        int knightCol = knight.charAt(0) - 'a' + 1;
        int knightRow = knight.charAt(1) - '0';

        // 1) угрожает ли ладья слону
        boolean threat = false;
        if (rookCol == bishopCol || rookRow == bishopRow) {
            threat = true;

            // проверяем, не стоит ли конь между ладьёй и слоном
            if (rookCol == bishopCol && knightCol == rookCol) {
                int minRow = Math.min(rookRow, bishopRow);
                int maxRow = Math.max(rookRow, bishopRow);
                if (knightRow > minRow && knightRow < maxRow) {
                    threat = false;
                }
            }
            if (rookRow == bishopRow && knightRow == rookRow) {
                int minCol = Math.min(rookCol, bishopCol);
                int maxCol = Math.max(rookCol, bishopCol);
                if (knightCol > minCol && knightCol < maxCol) {
                    threat = false;
                }
            }
        }

        if (threat) {
            System.out.println("Ладья угрожает слону: да");
        } else {
            System.out.println("Ладья угрожает слону: нет");
        }

        // 2) одного ли цвета клетки ладьи и коня
        // если сумма столбца и строки чётная, клетка чёрная, иначе белая
        boolean rookBlack = (rookCol + rookRow) % 2 == 0;
        boolean knightBlack = (knightCol + knightRow) % 2 == 0;

        if (rookBlack == knightBlack) {
            System.out.println("Клетки ладьи и коня одного цвета: да");
        } else {
            System.out.println("Клетки ладьи и коня одного цвета: нет");
        }

        // 3) число ходов ладьи на пустой доске
        // 7 клеток по горизонтали + 7 по вертикали
        int moves = 7 + 7;
        System.out.println("Число ходов ладьи: " + moves);

        sc.close();
    }
}
