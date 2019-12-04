import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * A JPanel for 2D graphics organized on a board.
 * 
 * @author Hendrik Speleers
 * @author NMCGJ, AY 2018-2019
 */
public class BoardPanel extends JPanel {

   private static final long serialVersionUID = 1L;
   protected static final int DIR_VERTICAL = 0;
   protected static final int DIR_HORIZONTAL = 1;
   protected static final int DIR_UP = 0;
   protected static final int DIR_LEFT = 1;
   protected static final int DIR_RIGHT = 2;
   protected static final int DIR_DOWN = 3;
   protected static final int DIR_NONE = -1;

   protected BoardComponent[][] board;
   protected int rows;
   protected int cols;

   /**
    * Constructs a board panel.
    * 
    * @param rows
    *           the number of rows of the board
    * @param cols
    *           the number of columns of the board
    */
   public BoardPanel(int rows, int cols) {
      this.rows = rows;
      this.cols = cols;
      this.board = new BoardComponent[rows][cols];
   }

   /**
    * Returns the number of rows of the board.
    * 
    * @return number of rows
    */
   public int getRows() {
      return rows;
   }

   /**
    * Returns the number of columns of the board.
    * 
    * @return number of columns
    */
   public int getColumns() {
      return cols;
   }
   
   /**
    * Checks whether the position (i, j) is inside or not.
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @return true if index is inside, false otherwise
    */
   public boolean isInside(int i, int j) {
      return (i >= 0 && i < rows && j >= 0 && j < cols);
   }

   /**
    * Clears the complete board.
    */
   public void clear() {
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
            board[i][j] = null;
         }
      }
   }

   /**
    * Clears the component at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    */
   public void clear(int i, int j) {
      board[i][j] = null;
   }

   /**
    * Draws a filled rectangle at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param color
    *           the color
    */
   public void drawRectangle(int i, int j, Color color) {
      board[i][j] = new BoardRectangle(color);
   }

   /**
    * Draws a filled rectangle at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param color
    *           the color
    * @param density
    *           the density percentage [0..100]
    */
   public void drawRectangle(int i, int j, Color color, int density) {
      board[i][j] = new BoardRectangle(color, density);
   }

   /**
    * Draws a filled rectangle with rounded corners at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param color
    *           the color
    */
   public void drawRoundedRectangle(int i, int j, Color color) {
      board[i][j] = new BoardRoundedRectangle(color);
   }

   /**
    * Draws a filled rectangle with rounded corners at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param color
    *           the color
    * @param density
    *           the density percentage [0..100]
    */
   public void drawRoundedRectangle(int i, int j, Color color, int density) {
      board[i][j] = new BoardRoundedRectangle(color, density);
   }

   /**
    * Draws a filled oval at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param color
    *           the color
    */
   public void drawOval(int i, int j, Color color) {
      board[i][j] = new BoardOval(color);
   }

   /**
    * Draws a filled oval at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param color
    *           the color
    * @param density
    *           the density percentage [0..100]
    */
   public void drawOval(int i, int j, Color color, int density) {
      board[i][j] = new BoardOval(color, density);
   }

   /**
    * Draws an axis-aligned line at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param color
    *           the color
    * @param dir
    *           the direction parameter
    *           0 = vertical
    *           1 = horizontal
    * @param density
    *           the density percentage [0..100]
    */
   public void drawLine(int i, int j, Color color, int dir, int density) {
      int[] dirs = {dir, 3-dir};
      board[i][j] = new BoardCross(color, dirs, density);
   }

   /**
    * Draws an axis-aligned cross at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param color
    *           the color
    * @param dirs[]
    *           the direction parameters for each leg of the cross
    *          -1 = none
    *           0 = up
    *           1 = left 
    *           2 = right 
    *           3 = down
    * @param density
    *           the density percentage [0..100]
    */
   public void drawCross(int i, int j, Color color, int[] dirs, int density) {
      board[i][j] = new BoardCross(color, dirs, density);
   }

   /**
    * Draws a (scaled) image at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param image
    *           the image to be drawn
    */
   public void drawImage(int i, int j, Image image) {
      board[i][j] = new BoardImage(image);
   }

   /**
    * Draws a (scaled) image at board position (i, j).
    * 
    * @param i
    *           the row index
    * @param j
    *           the column index
    * @param image
    *           the image to be drawn
    * @param density
    *           the density percentage [0..100]
    */
   public void drawImage(int i, int j, Image image, int density) {
      board[i][j] = new BoardImage(image, density);
   }

   /**
    * Creates an image from the graphics on the panel.
    * 
    * @return the image
    */
   public BufferedImage createImage() {
      BufferedImage image = new BufferedImage(getWidth(), getHeight(),
            BufferedImage.TYPE_INT_RGB);
      Graphics2D graphics = image.createGraphics();
      print(graphics);
      return image;
   }

   /**
    * Draws all the components on the panel.
    * 
    * @param g
    *           the graphics context
    */
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      int width = getWidth() / cols;
      int height = getHeight() / rows;
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
            if (board[i][j] != null) {
               board[i][j].draw(g, j * width, i * height, width, height);
            }
         }
      }
   }

}

/**
 * An interface for components to be drawn.
 */
interface BoardComponent {

   /**
    * Draws the component.
    * 
    * @param g
    *           the graphics context
    * @param x
    *           the x-coordinate of the rectangle
    * @param y
    *           the y-coordinate of the rectangle
    * @param width
    *           the width of the rectangle
    * @param height
    *           the height of the rectangle
    */
   public void draw(Graphics g, int x, int y, int width, int height);

}

/**
 * A BoardComponent to draw rectangles.
 */
class BoardRectangle implements BoardComponent {
   public Color color;
   public int density;

   /**
    * Constructs a rectangle drawer.
    * 
    * @param color
    *           the color of the rectangle
    */
   BoardRectangle(Color color) {
      this(color, 100);
   }

   /**
    * Constructs a rectangle drawer.
    * 
    * @param color
    *           the color of the rectangle
    * @param density
    *           the density percentage [0..100]
    */
   BoardRectangle(Color color, int density) {
      this.color = color;
      this.density = (density > 0) ? density : 0;
   }

   /**
    * Draws the rectangle.
    * 
    * @param g
    *           the graphics context
    * @param x
    *           the x-coordinate of the rectangle
    * @param y
    *           the y-coordinate of the rectangle
    * @param width
    *           the width of the rectangle
    * @param height
    *           the height of the rectangle
    */
   public void draw(Graphics g, int x, int y, int width, int height) {
      g.setColor(color);
      if (density == 100) {
         g.fillRect(x, y, width, height);
      } else {
         g.fillRect(x + width * (100 - density) / 200,
                    y + height * (100 - density) / 200,
                    width * density / 100, height * density / 100);
      }
   }
}

/**
 * A BoardComponent to draw rounded rectangles.
 */
class BoardRoundedRectangle implements BoardComponent {
   public Color color;
   public int density;

   /**
    * Constructs a rounded rectangle drawer.
    * 
    * @param color
    *           the color of the rectangle
    */
   BoardRoundedRectangle(Color color) {
      this(color, 100);
   }

   /**
    * Constructs a rounded rectangle drawer.
    * 
    * @param color
    *           the color of the rectangle
    * @param density
    *           the density percentage [0..100]
    */
   BoardRoundedRectangle(Color color, int density) {
      this.color = color;
      this.density = (density > 0) ? density : 0;
   }

   /**
    * Draws the rounded rectangle.
    * 
    * @param g
    *           the graphics context
    * @param x
    *           the x-coordinate of the rectangle
    * @param y
    *           the y-coordinate of the rectangle
    * @param width
    *           the width of the rectangle
    * @param height
    *           the height of the rectangle
    */
   public void draw(Graphics g, int x, int y, int width, int height) {
      g.setColor(color);
      if (density == 100) {
         g.fillRoundRect(x, y, width, height, width / 2, height / 2);
      } else {
         g.fillRoundRect(x + width * (100 - density) / 200,
                         y + height * (100 - density) / 200,
                         width * density / 100, height * density / 100,
                         width * density / 200, height * density / 200);
      }
   }
}

/**
 * A BoardComponent to draw ovals.
 */
class BoardOval implements BoardComponent {
   public Color color;
   public int density;

   /**
    * Constructs a oval drawer.
    * 
    * @param color
    *           the color of the oval
    */
   BoardOval(Color color) {
      this(color, 100);
   }

   /**
    * Constructs a oval drawer.
    * 
    * @param color
    *           the color of the oval
    * @param density
    *           the density percentage [0..100]
    */
   BoardOval(Color color, int density) {
      this.color = color;
      this.density = (density > 0) ? density : 0;
   }

   /**
    * Draws the oval.
    * 
    * @param g
    *           the graphics context
    * @param x
    *           the x-coordinate of the rectangle
    * @param y
    *           the y-coordinate of the rectangle
    * @param width
    *           the width of the rectangle
    * @param height
    *           the height of the rectangle
    */
   public void draw(Graphics g, int x, int y, int width, int height) {
      g.setColor(color);
      if (density == 100) {
         g.fillOval(x, y, width, height);
      } else {
         g.fillOval(x + width * (100 - density) / 200,
                    y + height * (100 - density) / 200,
                    width * density / 100, height * density / 100);
      }
   }
}

/**
 * A BoardComponent to draw a cross
 */
class BoardCross implements BoardComponent {
   public Color color;
   public int dirs[];
   public int density;

   /**
    * Constructs a corner drawer.
    * 
    * @param color
    *           the color of the rectangle
    * @param dirs
    *           all the cross directions
    * @param density
    *           the density percentage [0..100]
    */
   BoardCross(Color color, int[] dirs, int density) {
      this.color = color;
      this.dirs = dirs;
      this.density = (density > 0) ? density : 0;
   }

   /**
    * Draws the corner.
    * 
    * @param g
    *           the graphics context
    * @param x
    *           the x-coordinate of the rectangle
    * @param y
    *           the y-coordinate of the rectangle
    * @param width
    *           the width of the rectangle
    * @param height
    *           the height of the rectangle
    */
   public void draw(Graphics g, int x, int y, int width, int height) {
      g.setColor(color);
      if (dirs != null) {
         for (int i=0; i<dirs.length; i++) {
            switch (dirs[i]) {
            case BoardPanel.DIR_UP:
               g.fillRect(x + width * (100 - density) / 200,
                          y, width * density / 100, 
                          height * (100 + density) / 200);
               break;
            case BoardPanel.DIR_LEFT:
               g.fillRect(x, y + height * (100 - density) / 200,
                          width * (100 + density) / 200,
                          height * density / 100);
               break;
            case BoardPanel.DIR_RIGHT:
               g.fillRect(x + width * (100 - density) / 200, 
                          y + height * (100 - density) / 200,
                          width * (100 + density) / 200,
                          height * density / 100);
               break;
            case BoardPanel.DIR_DOWN:
               g.fillRect(x + width * (100 - density) / 200,
                          y + height * (100 - density) / 200, 
                          width * density / 100, 
                          height * (100 + density) / 200);
               break;
            default: // no direction
               g.fillRect(x + width * (100 - density) / 200,
                     y + height * (100 - density) / 200, 
                     width * density / 100, height * density / 100);
            }
         }
      }
   }
}

/**
 * A BoardComponent to draw images.
 */
class BoardImage implements BoardComponent {
   public Image image;
   public int density;

   /**
    * Constructs an image drawer.
    * 
    * @param image
    *           the image to be drawn
    */
   BoardImage(Image image) {
      this(image, 100);
   }

   /**
    * Constructs an image drawer.
    * 
    * @param image
    *           the image to be drawn
    * @param scale
    *           the scaling factor in range [0,1]
    */
   BoardImage(Image image, int density) {
      this.image = image;
      this.density = (density > 0) ? density : 0;
   }

   /**
    * Draws the image.
    * 
    * @param g
    *           the graphics context
    * @param x
    *           the x-coordinate of the rectangle
    * @param y
    *           the y-coordinate of the rectangle
    * @param width
    *           the width of the rectangle
    * @param height
    *           the height of the rectangle
    */
   public void draw(Graphics g, int x, int y, int width, int height) {
      if (density == 100) {
         g.drawImage(image, x, y, width, height, null);
      } else {
         g.drawImage(image, x + width * (100 - density) / 200,
                     y + height * (100 - density) / 200,
                     width * density / 100, height * density / 100,
                     null);
      }
   }
}
