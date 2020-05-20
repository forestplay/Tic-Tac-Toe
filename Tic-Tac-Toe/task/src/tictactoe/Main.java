package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String table = "_________";
        printTable(table);

        while (gameState(table).matches("Game not finished")) {
            table = takeATurn(scanner, table, "X");
            printTable(table);
            if (gameState(table).matches("Game not finished")) {
                table = takeATurn(scanner, table, "O");
                printTable(table);
            }
        }
        System.out.println(gameState(table));
    }

    public static void printTable(String table) {
        System.out.println("---------");
        for (int x = 0; x < 3; x++) {
            System.out.print("| ");
            for (int y = 0; y < 3; y++) {
                System.out.print(table.charAt(x * 3 + y) + " ");
            }
            System.out.println(" |");
        }
        System.out.println("---------");
    }

    public static String gameState(String table) {
        if (Math.abs(countOccurences(table, 'X', 0) - countOccurences(table, 'O', 0)) > 1) {
            return "Impossible";
        }

        boolean xWins = false;
        boolean oWins = false;
        for (int x = 0; x < 3; x++) {
            String row = Character.toString(table.charAt(x * 3))
                    + Character.toString(table.charAt(x * 3 + 1))
                    + Character.toString(table.charAt(x * 3 + 2));
            if (row.equals("XXX")) xWins = true;
            if (row.equals("OOO")) oWins = true;
        }

        for (int y = 0; y < 3; y++) {
            String column = Character.toString(table.charAt(y))
                    + Character.toString(table.charAt(3 + y))
                    + Character.toString(table.charAt(6 + y));
            if (column.equals("XXX")) xWins = true;
            if (column.equals("OOO")) oWins = true;
        }

        String diagonalRight = Character.toString(table.charAt(0))
                + Character.toString(table.charAt(4))
                + Character.toString(table.charAt(8));
        if (diagonalRight.equals("XXX")) xWins = true;
        if (diagonalRight.equals("OOO")) oWins = true;

        String diagonalLeft = Character.toString(table.charAt(2))
                + Character.toString(table.charAt(4))
                + Character.toString(table.charAt(6));
        if (diagonalLeft.equals("XXX")) xWins = true;
        if (diagonalLeft.equals("OOO")) oWins = true;

        if (xWins && oWins) return "Impossible";
        if (xWins == oWins && countOccurences(table, '_', 0) > 0) return "Game not finished";
        if (xWins) return "X wins";
        if (oWins) return "O wins";
        return "Draw";
    }

    private static boolean cellOpen(String table, int x, int y) {
        char cell = table.charAt(x * 3 + y);
        return cell != 'X' && cell != 'O';
    }

    private static int countOccurences(String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }
        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + countOccurences(
                someString, searchedChar, index + 1);
    }

    private static String takeATurn(Scanner scanner, String table, String player) {
        while (true) {
            System.out.print("Enter the coordinates: ");
            String input = scanner.nextLine();
            if (!input.matches("[0-9] [0-9]")) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (!input.matches("[1-3] [1-3]")) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            int j = input.charAt(0) - 48;
            int i = input.charAt(2) - 48;

            int x = (i == 1) ? 2 : (i == 2) ? 1 : 0;
            int y = (j == 1) ? 0 : (j == 2) ? 1 : 2;

            if (cellOpen(table, x, y)) {
                table = table.substring(0, x * 3 + y) + player + table.substring(x * 3 + y + 1);
                break;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }

        }
        return table;
    }

}
