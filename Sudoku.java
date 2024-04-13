/*
  Author: Vishnu Varadhan

  Date: 4/3/2024

  Name: Sudoku.java

  Purpose: Implements the core functionality of creating and solving a Sudoku puzzle, with support for graphical display.
*/

import java.util.Random;
import java.util.random.*;

public class Sudoku {
    protected Board sudokuBoard;
    private Random rand = new Random();
    private LandscapeDisplay ld;

    /**
     * Default constructor that initializes a Sudoku puzzle with a random number of
     * locked cells.
     */
    public Sudoku() {
        this.sudokuBoard = new Board(1 + rand.nextInt(30));
        ld = new LandscapeDisplay(sudokuBoard);
    }

    /**
     * Constructor that initializes a Sudoku puzzle with a specified number of
     * locked cells.
     * 
     * @param specifiedVals Number of cells to be pre-filled (locked).
     */
    public Sudoku(int specifiedVals) {
        this.sudokuBoard = new Board(specifiedVals);
        ld = new LandscapeDisplay(sudokuBoard);
    }

    /**
     * Constructor that initializes a Sudoku puzzle based on values from a file.
     * 
     * @param filename Path to the file containing the initial board setup.
     */
    public Sudoku(String filename) {
        this.sudokuBoard = new Board(filename);
        ld = new LandscapeDisplay(sudokuBoard);
    }

    /**
     * Finds the next valid value for a cell, starting from the current cell value +
     * 1.
     * 
     * @param cell The cell for which to find the next valid value.
     * @return The next valid value or 0 if no valid values are found.
     */
    public int findNextValue(Cell cell) {
        int currVal = cell.getValue() + 1;
        while (currVal <= 9) {
            if (this.sudokuBoard.validValue(cell.getRow(), cell.getCol(), currVal)) {
                return currVal;
            }
            currVal++;
        }
        return 0;
    }

    /**
     * Finds the next cell on the board that is empty.
     * 
     * @return The next empty cell or null if all cells are filled.
     */
    public Cell findNextCell() {
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                if (sudokuBoard.get(i, j).getValue() == 0) {
                    int newVal = findNextValue(sudokuBoard.get(i, j));
                    if (newVal != 0) {
                        sudokuBoard.get(i, j).setValue(newVal);
                        return sudokuBoard.get(i, j);
                    }
                    return null;
                }
            }
        }
        return null;

    }

    /**
     * Attempts to solve the Sudoku puzzle.
     * 
     * @return True if the puzzle is solved successfully, false otherwise.
     */
    public boolean solve() {
        Stack<Cell> solutions = new LinkedList<Cell>();
        int unspecifiedCells = (Board.SIZE * Board.SIZE) - this.sudokuBoard.numLocked();
        int delay = 0;

        while (solutions.size() < unspecifiedCells) {

            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (ld != null) {
                ld.repaint();
            }

            Cell next = findNextCell();
            while (next == null && !solutions.isEmpty()) {

                if (delay > 0) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (ld != null) {
                    ld.repaint();
                }
                Cell tempCell = solutions.pop();
                tempCell.setValue(findNextValue(tempCell));
                if (tempCell.getValue() != 0) {
                    next = tempCell;
                }
            }

            if (next == null) {
                return false;
            } else {
                solutions.push(next);
            }
        }

        sudokuBoard.finished = true;
        return true;

    }

    /**
     * Main method to run the Sudoku game and display the initial and solved board.
     */
    public static void main(String[] args) {
        System.out.println("Test 1: Empty Board");
        Sudoku sudokuEmpty = new Sudoku("board2.txt");
        System.out.println("Initial Board:");
        System.out.println(sudokuEmpty.sudokuBoard);
        if (sudokuEmpty.solve()) {
            System.out.println("Solved Board:");
            System.out.println(sudokuEmpty.sudokuBoard);
        } else {
            System.out.println("No solution could be found.");
        }

        System.out.println(sudokuEmpty.sudokuBoard.validSolution());
    }

}
