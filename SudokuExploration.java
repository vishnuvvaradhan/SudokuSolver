/*
  Author: Vishnu Varadhan

  Date: 4/3/2024

  Name: SudokuExploration.java

  Purpose: Conducts an experimental analysis to assess the impact of varying initial values on the Sudoku solving process.
*/

public class SudokuExploration {
    private static final long TIMEOUT_MS = 5000;

    /**
     * Main method to conduct the experiment of solving Sudoku puzzles with
     * different initial values.
     */
    public static void main(String[] args) {
        int[] initialValuesCounts = { 10, 20, 30, 40 };
        int trialsPerCase = 5;

        for (int initialValuesCount : initialValuesCounts) {
            int solvedCount = 0;
            long totalSolveTime = 0;

            for (int trial = 0; trial < trialsPerCase; trial++) {
                Sudoku sudoku = new Sudoku(initialValuesCount);
                long startTime = System.currentTimeMillis();
                boolean solved = false;

                solved = sudoku.solve();
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;

                if (duration < TIMEOUT_MS) {
                    if (solved) {
                        solvedCount++;
                    }
                } else {
                    System.out.println("Timeout reached for trial " + (trial + 1) + " with " + initialValuesCount
                            + " initial values.");
                }

                totalSolveTime += Math.min(duration, TIMEOUT_MS);
            }

            System.out.println("Initial Values: " + initialValuesCount + ", Solved: " + solvedCount +
                    "/" + trialsPerCase + ", Avg Time: " + (totalSolveTime / trialsPerCase) + "ms");
        }
    }
}