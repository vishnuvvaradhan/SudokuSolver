/*
  Author: Vishnu Varadhan

  Date: 4/3/2024

  Name: PerformanceTester.java

  Purpose: Measures the performance of the Sudoku solver on boards of various sizes and determines if a timeout occurs.
*/

import java.util.Random;

public class PerformanceTester {

    private static final long TIMEOUT_MS = 25000; // 25 seconds in milliseconds

    /**
     * Executes performance testing on Sudoku boards of various sizes to measure
     * solve time and detect timeouts.
     */
    public static void main(String[] args) {
        int[] boardSizes = new int[] { 4, 9, 16, 25 };
        String[] timesTaken = new String[boardSizes.length];

        for (int i = 0; i < boardSizes.length; i++) {
            int size = boardSizes[i];
            SudokuExtension solver = new SudokuExtension(size);
            long startTime = System.currentTimeMillis();
            boolean solved = solver.solve();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            if (duration > TIMEOUT_MS) {
                timesTaken[i] = "DNE";
                System.out.println(
                        "Board size " + size + " solution took longer than " + TIMEOUT_MS + "ms. Marked as DNE.");
            } else {
                timesTaken[i] = String.valueOf(duration);
                System.out.println("Board size " + size + " solved in " + duration + "ms.");
            }
        }

        System.out.println("Board Size | Time Taken (ms) or DNE");
        System.out.println("---------------------------------");
        for (int i = 0; i < boardSizes.length; i++) {
            System.out.println("    " + boardSizes[i] + "     |      " + timesTaken[i]);
        }
    }
}