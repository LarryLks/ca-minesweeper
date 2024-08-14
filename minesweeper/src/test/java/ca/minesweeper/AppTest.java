package ca.minesweeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private Game game;
    private Grid grid;

    @BeforeEach
    void setUp() {
        game = new Game(new Scanner(System.in));
    }

    @Test
    void testGridInitialization() {
        grid = new Grid(4, 4);
        assertEquals(4, grid.getSize());
        assertEquals(4, grid.getMineCount());
    }

    @Test
    void testGenerateMines() {
        grid = new Grid(4, 4);
        grid.generateMines();

        int mineCount = 0;
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.getCells()[i][j].isMine()) {
                    mineCount++;
                }
            }
        }
        assertEquals(4, mineCount);
    }

    @Test
    void testRevealCellValid() {
        grid = new Grid(4, 4);
        grid.generateMines();

        // Find a cell that is not a mine
        int row = 0; // Assuming first cell for simplicity
        int col = 0; // Adjust accordingly
        if (grid.getCells()[row][col].isMine()) {
            row++;
        }

        String userInput = (char) ('A' + row) + Integer.toString(col + 1); // Convert to user input format
        assertTrue(grid.revealCell(userInput));
        assertTrue(grid.getCells()[row][col].isRevealed());
    }

    @Test
    void testRevealCellInvalid() {
        grid = new Grid(4, 4);
        grid.generateMines();

        String invalidInput = "Z99"; // Invalid input
        assertTrue(grid.revealCell(invalidInput)); // Should return true as it won't be game over
    }

    @Test
    void testRevealCellAlreadyRevealed() {
        grid = new Grid(4, 0);
        grid.generateMines();

        // Reveal a cell first
        String userInput = "A1"; // Adjust based on your grid
        assertTrue(grid.revealCell(userInput));

        // Try revealing it again
        assertTrue(grid.revealCell(userInput)); // Should prompt user about already revealed cell
    }

    @Test
    void testGameOverWithMine() {
        grid = new Grid(4, 4);
        grid.generateMines();

        // Assume there's a mine at A1, try to reveal it
        String userInput = "A1"; // Adjust based on your grid setup
        boolean result = grid.revealCell(userInput);
        assertFalse(result); // Should return true because it reveals a mine
        assertTrue(grid.isGameOver());
    }

    @Test
    void testGameWon() {
        grid = new Grid(4, 4);
        grid.generateMines();

        // Manually reveal all non-mine cells
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (!grid.getCells()[i][j].isMine()) {
                    grid.revealCell((char) ('A' + i) + Integer.toString(j + 1));
                }
            }
        }

        assertTrue(grid.isGameWon()); // Check if the game is won
    }

    @Test
    void testGetSize() {
        grid = new Grid(4, 4);
        assertEquals(4, grid.getSize());
    }

    @Test
    void testGetMineCount() {
        grid = new Grid(4, 4);
        assertEquals(4, grid.getMineCount());
    }
}
