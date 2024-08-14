package ca.minesweeper;
import java.util.Random;

public class Grid {
    private Cell[][] cells;
    private int size;
    private int mineCount;
    private boolean gameOver;
    private boolean gameWon;

    public Grid(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        this.cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }
    }
    

    public void generateMines() {
        Random rand = new Random();
        for (int i = 0; i < mineCount; ) {
            int row = rand.nextInt(size);
            int col = rand.nextInt(size);
            if (!cells[row][col].isMine()) {
                cells[row][col].setMine();
                incrementAdjacentCounts(row, col);
                i++;
            }
        }
    }

    private void incrementAdjacentCounts(int mineRow, int mineCol) {
        for (int row = mineRow - 1; row <= mineRow + 1; row++) {
            for (int col = mineCol - 1; col <= mineCol + 1; col++) {
                if (isInBounds(row, col) && !(row == mineRow && col == mineCol)) {
                    cells[row][col].incrementAdjacentMines();
                }
            }
        }
    }

    public boolean revealCell(String userInput) {
        int row = userInput.charAt(0) - 'A';
        int col = Integer.parseInt(userInput.substring(1)) - 1;
        
        if (!isInBounds(row, col)) {
            System.out.println("Invalid cell selection. Please try again.");
            return true;
        }
        if (cells[row][col].isRevealed()) {
            System.out.println("This cell has already been revealed. Please choose another cell.");
            return true;
        }
        if (cells[row][col].isMine()) {
            gameOver = true;
            return false;
        }
        
        reveal(row, col);
        return true;
    }

    public Cell[][] getCells() {
		return cells;
	}


	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}


	private void reveal(int row, int col) {
        if (!isInBounds(row, col) || cells[row][col].isRevealed()) {
            return;
        }
        cells[row][col].reveal();
        if (cells[row][col].getAdjacentMines() == 0) {
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    if (isInBounds(r, c) && !(r == row && c == col)) {
                        reveal(r, c);
                    }
                }
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!cells[i][j].isMine() && !cells[i][j].isRevealed()) {
                    return false;
                }
            }
        }
        gameWon = true;
        return gameWon;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int i = 1; i <= size; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n");
        for (int i = 0; i < size; i++) {
            sb.append((char) ('A' + i)).append(" ");
            for (int j = 0; j < size; j++) {
                if (cells[i][j].isRevealed()) {
                    sb.append(cells[i][j].isMine() ? "*" : cells[i][j].getAdjacentMines()).append(" ");
                } else {
                    sb.append("_ ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public int getMineCount() {
		return mineCount;
	}


	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

}