/*
  Author: Vishnu Varadhan

  Date: 4/3/2024

  Name: BoardTests.java

  Purpose: Implements tests for the Board class functionality including initialization, 
           value setting, locking mechanism, and validation of values.
*/

public class BoardTests {

    public static void main(String[] args) {
        testEmptyBoardValues();
        testLockedCellsInitialization();
        testValueSetting();
        testLockingMechanism();
        testValidValue();
    }

    /**
     * Tests that a new board is initialized with all cells set to zero.
     */
    private static void testEmptyBoardValues() {
        System.out.println("Testing empty board values...");
        Board board = new Board();
        boolean allZeros = true;
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                if (board.value(i, j) != 0) {
                    allZeros = false;
                    break;
                }
            }
        }
        System.out.println("Empty Board Values: " + (allZeros ? "PASS" : "FAIL"));
    }

    /**
     * Tests the initialization of locked cells on the board.
     */
    private static void testLockedCellsInitialization() {
        System.out.println("Testing locked cells initialization...");
        int numLockedCells = 10;
        Board board = new Board(numLockedCells);
        int lockedCount = board.numLocked();
        System.out.println("Locked Cells Initialization: " + (lockedCount == numLockedCells ? "PASS" : "FAIL"));
    }

    /**
     * Tests setting a value to a specific cell on the board.
     */
    private static void testValueSetting() {
        System.out.println("Testing value setting...");
        Board board = new Board();
        board.set(0, 0, 5);
        boolean valueSetCorrectly = board.value(0, 0) == 5;
        System.out.println("Value Setting: " + (valueSetCorrectly ? "PASS" : "FAIL"));
    }

    /**
     * Tests the mechanism for locking a cell on the board.
     */
    private static void testLockingMechanism() {
        System.out.println("Testing cell locking mechanism...");
        Board board = new Board();
        board.set(0, 0, true);
        boolean lockedCorrectly = board.isLocked(0, 0);
        System.out.println("Locking Mechanism: " + (lockedCorrectly ? "PASS" : "FAIL"));
    }

    /**
     * Tests the validation of placing a specific value in a specific cell.
     */
    private static void testValidValue() {
        System.out.println("Testing valid value checking...");
        Board board = new Board();
        boolean validValueResult = board.validValue(0, 0, 5);
        System.out.println("Valid Value Checking: " + (validValueResult ? "PASS" : "FAIL"));
    }
}