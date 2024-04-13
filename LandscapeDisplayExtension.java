/*
	Originally written by Bruce A. Maxwell and updated by Vishnu Varadhan to fit dynamic board sizes.
    updated it for the sake of my extension.

	Creates a window using the JFrame class.

	Creates a drawable area in the window using the JPanel class.

	The JPanel calls the Landscape's draw method to fill in content, so the
	Landscape class needs a draw method.
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

/**
 * Displays a BoardExtension graphically using Swing. This class manages a
 * graphical representation of a board.
 */
public class LandscapeDisplayExtension {
    private JFrame win;
    private BoardExtension scape;
    private LandscapePanel canvas;
    private int gridScale;

    /**
     * Initializes a display window for a BoardExtension.
     * 
     * @param scape the BoardExtension to display
     * @param scale controls the relative size of the display
     */
    public LandscapeDisplayExtension(BoardExtension scape, int scale) {

        this.win = new JFrame("Sudoku");
        this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.scape = scape;
        this.gridScale = scale;

        int width = scape.getSize() * gridScale;
        int height = scape.getSize() * gridScale + gridScale;

        this.canvas = new LandscapePanel(width, height);

        this.win.add(this.canvas, BorderLayout.CENTER);
        this.win.pack();
        this.win.setVisible(true);
    }

    /**
     * Saves an image of the display contents to a file. The supplied
     * filename should have an extension supported by javax.imageio, e.g.
     * "png" or "jpg".
     *
     * @param filename the name of the file to save
     */
    public void saveImage(String filename) {
        String ext = filename.substring(filename.lastIndexOf('.') + 1);

        BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics g = image.createGraphics();
        canvas.paint(g);
        g.dispose();

        try {
            ImageIO.write(image, ext, new File(filename));
        } catch (IOException ioe) {
            System.out.println("Error saving file: " + ioe.getMessage());
        }
    }

    private class LandscapePanel extends JPanel {
        public LandscapePanel(int width, int height) {
            super();
            this.setPreferredSize(new Dimension(width, height));
            this.setBackground(Color.white);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            scape.draw(g, gridScale);
        }
    }

    public void repaint() {
        this.canvas.repaint();
    }
}