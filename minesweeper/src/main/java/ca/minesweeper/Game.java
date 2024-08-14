package ca.minesweeper;

import java.util.Scanner;
import java.util.InputMismatchException;


public class Game {
    private Grid grid;
    public Grid getGrid() {
        return grid;
    }
    
    private Scanner scanner;
    private int gridSize;
    private int mineCount;

    public Game(Scanner scanner) {
        this.scanner = scanner;
    }

    public void startGame() {
        setupGrid();
        playGame();
    }

    private void setupGrid() {
        do {
            System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
            gridSize = getValidGridSize();
            mineCount = getValidMineCount(gridSize);
        } while (mineCount < 0);


        System.out.println("Here is your minefield : ");
        grid = new Grid(gridSize, mineCount);
        grid.generateMines();
    }

    private void playGame() {
        while (!grid.isGameOver()) {
            System.out.println(grid);
            String userInput = getUserInput();
            if (!grid.revealCell(userInput)) {
                System.out.println("Oh no, you detonated a mine! Game over.");
                System.out.println(grid);
                return;
            }
            if (grid.isGameWon()) {
                System.out.println("Congratulations, you have won the game!");
                return;
            }
        }
    }

    private int getValidGridSize() {
        int size;
        while (true) {
            try {
                size = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                if (size < 1 || size > 26) {
                    System.out.print("Grid size must be between 1 and 26. Please Enter again :");
                    continue;
                }
                return size;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number :");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private int getValidMineCount(int size) {
        int count;
        while (true) {
            System.out.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
            try {
                count = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                if (count < 0 || count > (int) (0.35 * size * size)) {
                    System.out.printf("Number of mines must be between 0 and %d.%n", (int) (0.35 * size * size));
                    continue;
                }
                return count;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private String getUserInput() {
        String input;
        while (true) {
            System.out.print("Select a square to reveal (e.g. A1): ");
            input = scanner.nextLine().toUpperCase();
            if (input.matches("[A-Z]\\d+")) {
                return input;
            }
            System.out.println("Invalid input. Please enter a valid square (e.g. A1).");
        }
    }
}
