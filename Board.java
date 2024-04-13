/*
  Author: Vishnu Varadhan

  Date: 4/3/2024

  Name: Board.java

  Purpose: Implements a sudoku board and the functionality of reading a txt file and creating
  a board from it.
*/

import java.io.*;
import java.util.Random;
import java.util.random.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board {

  private Cell[][] board;
  public static final int SIZE = 9;
  private Random rand = new Random();
  boolean finished;

  /**
   * Default constructor. Initializes a SIZE x SIZE board with empty cells (value
   * 0).
   */
  public Board() {
    this.board = new Cell[this.SIZE][this.SIZE];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        board[i][j] = new Cell(i, j, 0);
      }
    }
  }

  /**
   * Constructor that initializes the board and reads values from a given filename
   * to populate the board.
   * 
   * @param filename the path to the text file containing the board layout
   */
  public Board(String filename) {
    this();
    read(filename);
  }

  public Board(int numLocked) {
    this();
    int locked = 0;
    while (locked < numLocked) {
      int row = rand.nextInt(this.SIZE);
      int col = rand.nextInt(this.SIZE);
      int value = 1 + rand.nextInt(9);

      if (this.board[row][col].getValue() == 0 && validValue(row, col, value)) {
        this.board[row][col].setValue(value);
        this.board[row][col].setLocked(true);
        locked++;
      }
    }
  }

  /**
   * Retrieves the Cell object at a specified location.
   * 
   * @param row the row index
   * @param col the column index
   * @return the Cell object at the specified location
   */
  public Cell get(int row, int col) {
    return this.board[row][col];
  }

  /**
   * Updates the value of a specific cell on the board.
   * 
   * @param row   the row index
   * @param col   the column index
   * @param value the new value to set in the cell
   */
  public void set(int row, int col, int value) {
    this.board[row][col].setValue(value);
  }

  /**
   * Checks if a specific cell is locked.
   * 
   * @param row the row index
   * @param col the column index
   * @return true if the cell is locked, otherwise false
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
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        if (isLocked(i, j)) {
          lockedCells++;
        }
      }

    }
    return lockedCells;
  }

  /**
   * Returns the value of a cell at a specified location.
   * 
   * @param row the row index
   * @param col the column index
   * @return the value of the cell at the specified indexes
   */
  public int value(int row, int col) {
    return this.board[row][col].getValue();
  }

  /**
   * Sets the lock status of a specific cell on the board.
   * 
   * @param row    the row index
   * @param col    the column index
   * @param locked the lock status to set (true or false)
   */
  public void set(int row, int col, boolean locked) {
    this.board[row][col].setLocked(locked);
  }

  /**
   * Reads the board configuration from a file and populates the board
   * accordingly.
   * 
   * @param filename the path to the file containing the board configuration
   * @return true if the file was read successfully, false otherwise
   */
  public boolean read(String filename) {
    try {
      // assign to a variable of type FileReader a new FileReader object, passing
      // filename to the constructor
      FileReader fr = new FileReader(filename);
      // assign to a variable of type BufferedReader a new BufferedReader, passing the
      // FileReader variable to the constructor
      BufferedReader br = new BufferedReader(fr);

      // assign to a variable of type String line the result of calling the readLine
      // method of your BufferedReader object.
      String line = br.readLine();
      // start a while loop that loops while line isn't null
      int row = 0;
      while (line != null) {
        // print line
        // System.out.println( line );
        // assign to an array of Strings the result of splitting the line up by spaces
        // (line.split("[ ]+"))
        String[] arr = line.split("[ ]+");
        // let's see what this array holds:
        // System.out.println("the first item in arr: " + arr[0] + ", the second item in
        // arr: " + arr[1]);
        // print the size of the String array (you can use .length)
        // System.out.println( arr.length );
        // use the line to set various Cells of this Board accordingly
        for (int i = 0; i < arr.length; i++) {
          int value = Integer.parseInt(arr[i]);
          this.board[row][i].setValue(value);
          if (value != 0) {
            this.board[row][i].setLocked(true);
          }
        }
        // assign to line the result of calling the readLine method of your
        // BufferedReader object.
        row++;
        line = br.readLine();
      }
      // call the close method of the BufferedReader
      br.close();
      return true;
    } catch (FileNotFoundException ex) {
      System.out.println("Board.read():: unable to open file " + filename);
    } catch (IOException ex) {
      System.out.println("Board.read():: error reading file " + filename);
    }
    return false;
  }

  /**
   * Returns a string representation of the board, formatted for display.
   * 
   * @return a string representing the board state
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < this.board.length; i++) {
      if (i % 3 == 0)
        builder.append("\n");
      for (int j = 0; j < this.board[i].length; j++) {
        if (j % 3 == 0)
          builder.append("|");
        builder.append(this.board[i][j].getValue()).append(" ");
      }
      builder.append("|\n");
    }
    builder.append("\n");
    return builder.toString();
  }

  /**
   * Returns the number of columns in the board.
   * 
   * @return the size of columns in the board
   */
  public int getCols() {
    return this.SIZE;
  }

  /**
   * Returns the number of rows in the board.
   * 
   * @return the size of rows in the board
   */
  public int getRows() {
    return this.SIZE;
  }

  public boolean validValue(int row, int col, int value) {
    for (int i = 0; i < board.length; i++) {
      if (i != col && value(row, i) == value)
        return false;
      if (i != row && value(i, col) == value)
        return false;
    }

    int startRow = (row / 3) * 3;
    int startCol = (col / 3) * 3;
    for (int i = startRow; i < startRow + 3; i++) {
      for (int j = startCol; j < startCol + 3; j++) {
        if ((i != row || j != col) && value(i, j) == value)
          return false;
      }
    }
    return true;
  }

  public boolean validSolution() {
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board.length; j++) {
        if (!validValue(i, j, value(i, j))) {
          return false;
        }
      }
    }
    return true;
  }

  public void draw(Graphics g, int scale) {
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getCols(); j++) {
        get(i, j).draw(g, j * scale + 5, i * scale + 10, scale);
      }
    }
    if (finished) {
      if (validSolution()) {
        g.setColor(new Color(0, 127, 0));
        g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale * 3 + 5, scale * 10 + 10);
      } else {
        g.setColor(new Color(127, 0, 0));
        g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale * 3 + 5, scale * 10 + 10);
      }
    }
  }

  /**
   * Randomly permutes the symbols used in the solution.
   */
  public void randomPermute() {
    int[] permutation = new int[getRows()];
    Random rand = new Random();
    for (int i = 0; i < getRows(); i++) {
      int swapIndex = rand.nextInt(i + 1);
      permutation[i] = permutation[swapIndex];
      permutation[swapIndex] = i;
    }

    for (int r = 0; r < getRows(); r++) {
      for (int c = 0; c < getCols(); c++) {
        set(r, c, permutation[value(r, c) - 1] + 1);
      }
    }
  }

}
