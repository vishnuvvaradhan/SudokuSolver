
/*
  Author: Vishnu Varadhan

  Date: 4/3/2024

  Name: Reflection2.java

  Purpose: Extends Sudoku functionality to experiment with creating minimal Sudoku boards that have exactly one solution.
*/

import java.util.Random;

public class Reflection2 extends Sudoku {

    public final Random rand = new Random();

    /**
     * Constructor that initializes the Reflection2 with a solved and permuted
     * board.
     */
    public Reflection2() {
        super(0);
        solve();
        sudokuBoard.randomPermute();
    }

    /**
     * Locks all cells on the board.
     */
    public void lockAllCells() {
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                sudokuBoard.set(i, j, true);
            }
        }
    }

    /**
     * Attempts to create a minimal Sudoku board by randomly removing values and
     * ensuring only one solution exists.
     */
    public void makeMinimalBoard() {
        lockAllCells();
        while (true) {

            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            Cell temp = sudokuBoard.get(row, col);
            temp.setValue(0);
            temp.setLocked(false);

            if (numSolutions() != 1) {
                sudokuBoard.set(row, col, temp.getValue());
                break;
            }
        }
    }

    /**
     * Calculates the number of solutions for the current board state.
     * 
     * @return the number of solutions.
     */
    public int numSolutions() {

        Stack<Cell> solutions = new LinkedList<Cell>();
        int unspecifiedCells = (Board.SIZE * Board.SIZE) - this.sudokuBoard.numLocked();
        int counter = 0;
        int delay = 0;

        while (true) {
            if (solutions.size() == unspecifiedCells) {
                counter++;
            }
            Cell next = findNextCell();
            while (next == null && !solutions.isEmpty()) {
                Cell tempCell = solutions.pop();
                tempCell.setValue(findNextValue(tempCell));
                if (tempCell.getValue() != 0) {
                    next = tempCell;
                }
            }
            if (next == null) {
                // System.out.println(counter);
                return counter;
            } else {
                solutions.push(next);
            }
        }
    }

    /**
     * Main method to run the Reflection2 class functionality.
     */
    public static void main(String[] args) {
        Reflection2 sudoku = new Reflection2();
        sudoku.numSolutions();

        System.out.println("Solved Board:");
        System.out.println(sudoku.sudokuBoard);
        sudoku.makeMinimalBoard();
        System.out.println("Board After Making Minimal:");
        System.out.println(sudoku.sudokuBoard);
    }

}
