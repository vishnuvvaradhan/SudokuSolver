/*
  Author: Vishnu Varadhan

  Date: 4/3/2024

  Name: Cell.java

  Purpose: Implements a cell of a sudoku board and its properties
*/

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

public class Cell {
    private int row;
    private int col;
    private int value;
    private boolean isLocked;

    /**
     * Constructs a Cell object with specified row, column, and value.
     * By default, the cell is not locked.
     * 
     * @param row the row position of the cell
     * @param col the column position of the cell
     * @param value the value assigned to the cell
     */
    public Cell(int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
        isLocked = false;
    }

    /**
     * Constructs a Cell object with specified row, column, value, and lock status.
     * 
     * @param row the row position of the cell
     * @param col the column position of the cell
     * @param value the value assigned to the cell
     * @param isLocked the lock status of the cell (true if the cell is locked, false otherwise)
     */
    public Cell(int row, int col, int value, boolean isLocked){
        this(row, col, value);
        this.isLocked = isLocked;
    }
    
    /**
     * Returns the row position of the cell.
     * 
     * @return the row position
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Returns the column position of the cell.
     * 
     * @return the column position
     */
    public int getCol(){
        return this.col;
    }

    /**
     * Returns the value of the cell.
     * 
     * @return the value of the cell
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Sets the value of the cell.
     * 
     * @param newval the new value to be set
     */
    public void setValue(int newval){
        this.value = newval;
    }

    /**
     * Checks if the cell is locked.
     * 
     * @return true if the cell is locked, false otherwise
     */
    public boolean isLocked(){
        return isLocked;
    }

    /**
     * Sets the lock status of the cell.
     * 
     * @param lock the lock status to set (true to lock the cell, false to unlock)
     */
    public void setLocked(boolean lock){
        this.isLocked = lock;
    }



    /**
     * Draws a cell representation
     * 
     * 
     */
    public void draw(Graphics g, int x, int y, int scale){
        char toDraw = (char) ((int) '0' + getValue());
        g.setColor(isLocked()? Color.BLUE : Color.RED);
        g.drawChars(new char[] {toDraw}, 0, 1, x, y);
    }

    /**
     * Returns a string representation of the cell.
     * 
     * @return a string that describes the cell's value
     */
    public String toString(){
        return "Cell Value: " + this.value;
    }


}
