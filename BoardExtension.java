/*
  Author: Vishnu Varadhan
  Date: 4/3/2024
  Name: BoardExtension.java
  Purpose: Extends the functionality of the Board class to support dynamic board sizes based on perfect squares.
*/

import java.io.*;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardExtension {
    private Cell[][] board;
    private Random rand = new Random();
    protected boolean finished;
    private int size;
    private int subGridSize;

    /**
     * Constructs a board of given size, ensuring it's a perfect square.
     */
    public BoardExtension(int size) {
        if (!isPerfectSquare(size)) {
            throw new IllegalArgumentException("Size must be a perfect square.");
        }
        this.size = size;
        this.subGridSize = (int) Math.sqrt(size);
        this.board = new Cell[this.size][this.size];
        initializeBoard();
    }

    /**
     * Constructs a board from a file with a given size.
     */
    public BoardExtension(String filename, int size) {
        this(size);
        read(filename);
    }

    /**
     * Constructs a board of given size with a specified number of locked cells.
     */
    public BoardExtension(int size, int numLocked) {
        this(size);
        int locked = 0;
        while (locked < numLocked) {
            int row = rand.nextInt(this.size);
            int col = rand.nextInt(this.size);
            int value = 1 + rand.nextInt(9);

            if (this.board[row][col].getValue() == 0 && validValue(row, col, value)) {
                this.board[row][col].setValue(value);
                this.board[row][col].setLocked(true);
                locked++;
            }
        }
    }

    /**
     * Checks if a number is a perfect square.
     */
    private boolean isPerfectSquare(int num) {
        int sqrt = (int) Math.sqrt(num);
        return sqrt * sqrt == num;
    }

    /**
     * Initializes the board with empty cells.
     */
    private void initializeBoard() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                board[i][j] = new Cell(i, j, 0);
            }
        }
    }

    /**
     * Retrieves a cell object from a specified location on the board.
     * 
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the Cell object at the specified location
     */
    public Cell get(int row, int col) {
        return this.board[row][col];
    }

    /**
     * Returns the size of the board (both the number of rows and columns).
     * 
     * @return the size of the board
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the number of rows in the board.
     * 
     * @return the number of rows
     */
    public int getRows() {
        return this.size;
    }

    /**
     * Returns the number of columns in the board.
     * 
     * @return the number of columns
     */
    public int getCols() {
        return this.size;
    }

    /**
     * Sets the value of a cell at a specified location on the board.
     * 
     * @param row   the row index of the cell
     * @param col   the column index of the cell
     * @param value the value to set the cell to
     */
    public void set(int row, int col, int value) {
        this.board[row][col].setValue(value);
    }

    /**
     * Determines if a cell at a specified location is locked.
     * 
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return true if the cell is locked, false otherwise
     */
    public boolean isLocked(int row, int col) {
        return this.board[row][col].isLocked();
    }

    /**
     * Counts and returns the number of locked cells on the board.
     * 
     * @return the number of locked cells
     */
    public int numLocked() {
        int lockedCells = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (isLocked(i, j)) {
                    lockedCells++;
                }
            }
        }
        return lockedCells;
    }

    /**
     * Retrieves the value of a cell at a specified location.
     * 
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the value of the cell
     */
    public int value(int row, int col) {
        return this.board[row][col].getValue();
    }

    /**
     * Sets the lock status of a cell at a specified location.
     * 
     * @param row    the row index of the cell
     * @param col    the column index of the cell
     * @param locked the lock status to set (true or false)
     */
    public void set(int row, int col, boolean locked) {
        this.board[row][col].setLocked(locked);
    }

    /**
     * Checks if a given value can be placed at a specified location without
     * violating Sudoku rules.
     * 
     * @param row   the row index to check
     * @param col   the column index to check
     * @param value the value to check
     * @return true if the value can be placed, false otherwise
     */
    public boolean validValue(int row, int col, int value) {
        for (int i = 0; i < this.size; i++) {
            if (i != col && value(row, i) == value)
                return false;
            if (i != row && value(i, col) == value)
                return false;
        }

        int startRow = (row / subGridSize) * subGridSize;
        int startCol = (col / subGridSize) * subGridSize;
        for (int i = startRow; i < startRow + subGridSize; i++) {
            for (int j = startCol; j < startCol + subGridSize; j++) {
                if ((i != row || j != col) && value(i, j) == value)
                    return false;
            }
        }
        return true;
    }

    /**
     * Validates if the current board configuration is a valid Sudoku solution.
     * 
     * @return true if the solution is valid, false otherwise
     */
    public boolean validSolution() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (!validValue(i, j, value(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reads board data from a file and initializes the board accordingly.
     * Each value read is set on the board and locked if not zero.
     *
     * @param filename the path to the file containing the board configuration
     * @return true if the file was read successfully, false otherwise
     */
    public boolean read(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.err.println("File does not exist: " + filename);
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < this.size) {
                String[] values = line.trim().split("\\s+");
                for (int col = 0; col < this.size && col < values.length; col++) {
                    int value = Integer.parseInt(values[col]);
                    this.board[row][col].setValue(value);
                    if (value != 0) {
                        this.board[row][col].setLocked(true);
                    }
                }
                row++;
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error reading board configuration: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates a string representation of the board, suitable for printing to
     * console.
     * This includes sub-grid delimiters for visual separation.
     *
     * @return a string representing the current state of the board
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int subGridSize = (int) Math.sqrt(this.size);

        for (int i = 0; i < this.size; i++) {
            if (i % subGridSize == 0 && i != 0) {

                builder.append("\n");
            }

            for (int j = 0; j < this.size; j++) {

                if (j % subGridSize == 0) {
                    builder.append("| ");
                }

                if (j % subGridSize == 0 && j != 0) {

                    builder.append("| ");
                }
                builder.append(this.board[i][j].getValue()).append(" ");
            }
            builder.append("|\n");
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Draws the board on a given Graphics context, adjusting the drawing scale.
     * This method will visually display the board on a GUI, drawing each cell value
     * and displaying a message upon completion if the board is finished.
     *
     * @param g     the Graphics context to draw on
     * @param scale the scale factor for drawing (size of each cell)
     */
    public void draw(Graphics g, int scale) {
        int width = getCols() * scale;
        int height = getRows() * scale;

        if (scale < 30) {
            g.setFont(new Font("SansSerif", Font.BOLD, scale / 2));
        } else {
            g.setFont(new Font("SansSerif", Font.BOLD, scale / 3));
        }
        FontMetrics fm = g.getFontMetrics();

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                Cell cell = get(i, j);
                int cellValue = cell.getValue();
                String textValue = (cellValue > 0) ? Integer.toString(cellValue) : "";
                int textWidth = fm.stringWidth(textValue);

                int textX = j * scale + (scale - textWidth) / 2;
                int textY = i * scale + (scale - fm.getHeight()) / 2 + fm.getAscent();

                if (cellValue > 0) {
                    g.drawString(textValue, textX, textY);
                }
            }
        }

        if (finished) {
            String message = validSolution() ? "Hurray!" : "No solution!";
            Color textColor = validSolution() ? new Color(0, 127, 0) : new Color(127, 0, 0);

            g.setColor(textColor);
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(message);
            int textX = (width - textWidth) / 2;
            int textY = height + fm.getAscent();

            g.drawString(message, textX, textY);
        }
    }
}
