/*
  Author: Vishnu Varadhan

  Date: 4/3/2024

  Name: SudokuExtension.java

  Purpose: Extends the Sudoku solving functionality to support custom board sizes and provide a visual display.
*/

import java.util.Random;

public class SudokuExtension {
    protected BoardExtension sudokuBoard;
    private Random rand = new Random();
    private LandscapeDisplayExtension ld;
    private final int gridScale = 30;

    /**
     * Constructor for creating a Sudoku puzzle of a specified size.
     */
    public SudokuExtension(int size) {
        this.sudokuBoard = new BoardExtension(size);
        ld = new LandscapeDisplayExtension(sudokuBoard, gridScale); // Include gridScale
    }

    /**
     * Constructor for creating a Sudoku puzzle with a specified number of initially
     * set values.
     */
    public SudokuExtension(int size, int specifiedVals) {
        this.sudokuBoard = new BoardExtension(size, specifiedVals);
        ld = new LandscapeDisplayExtension(sudokuBoard, gridScale); // Include gridScale
    }

    /**
     * Constructor for creating a Sudoku puzzle from a file, allowing for puzzles to
     * be loaded from external sources.
     */
    public SudokuExtension(String filename, int size) {
        this.sudokuBoard = new BoardExtension(size);
        this.sudokuBoard.read(filename);
        ld = new LandscapeDisplayExtension(sudokuBoard, gridScale); // Include gridScale
    }

    /**
     * Finds the next possible valid value for a given cell.
     */
    public int findNextValue(Cell cell) {
        int currVal = cell.getValue() + 1;
        while (currVal <= sudokuBoard.getSize()) {
            if (sudokuBoard.validValue(cell.getRow(), cell.getCol(), currVal)) {
                return currVal;
            }
            currVal++;
        }
        return 0;
    }

    /**
     * Identifies the next cell in the Sudoku grid that needs to be filled.
     */
    public Cell findNextCell() {
        for (int i = 0; i < sudokuBoard.getSize(); i++) {
            for (int j = 0; j < sudokuBoard.getSize(); j++) {
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
     * Attempts to solve the Sudoku puzzle using a backtracking algorithm.
     */
    public boolean solve() {
        Stack<Cell> solutions = new LinkedList<>();
        int unspecifiedCells = (sudokuBoard.getSize() * sudokuBoard.getSize()) - sudokuBoard.numLocked();
        System.out.println(unspecifiedCells);
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
     * Main method to execute the Sudoku solver with command line parameters
     * specifying the board size and initial values.
     */
    public static void main(String[] args) {
        int perfSquare = Integer.parseInt(args[0]);
        int start_vals = Integer.parseInt(args[1]);
        SudokuExtension sudokuEmpty = new SudokuExtension(perfSquare, start_vals);
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
