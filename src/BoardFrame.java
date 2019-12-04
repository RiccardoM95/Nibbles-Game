import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.imageio.*;
import java.io.*;

/**
 * A JFrame for 2D graphics organized on a board.
 * 
 * @author Hendrik Speleers
 * @author NMCGJ, AY 2018-2019
 */
public class BoardFrame extends JFrame {

   private static final long serialVersionUID = 1L;

   /**
    * Constructs a board frame with a given title and the dimensions of the board.
    * 
    * @param title
    *           the frame title
    * @param rows
    *           the number of rows of the board
    * @param cols
    *           the number of columns of the board
    */
   public BoardFrame(String title, int rows, int cols) {
      setTitle(title);
      setFrameOptions();
      addMenuBar();
      addBoard(rows, cols);
   }

   /**
    * Constructs a board frame with a given title and the dimensions of the board.
    * 
    * @param title
    *           the frame title
    * @param rows
    *           the number of rows of the board
    * @param cols
    *           the number of columns of the board
    * @param size
    *           the preferred size of each block on the board
    */
   public BoardFrame(String title, int rows, int cols, int size) {
      setTitle(title);
      setFrameOptions();
      addMenuBar();
      addBoard(rows, cols, size);
   }

   /**
    * Visualizes the frame.
    */
   public void start() {
      setVisible(true);
   }

   /**
    * Closes the frame.
    */
   public void close() {
      setVisible(false);
      dispose();
   }

   /**
    * Sets the general options for the frame.
    */
   protected void setFrameOptions() {
      Toolkit toolkit = Toolkit.getDefaultToolkit();
      Dimension dimension = toolkit.getScreenSize();

      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setBounds(dimension.width / 6, dimension.height / 6,
            dimension.width * 2 / 3, dimension.height * 2 / 3);
   }

   protected JMenuBar menuBar;
   protected JMenu menuFile;
   protected JMenuItem menuFileNew, menuFileOpen, menuFileSave, menuFileExit;

   protected File file;
   protected JFileChooser fileChooser;
   protected FileNameExtensionFilter pngFilter, jpgFilter, imageFilter;

   /**
    * Adds the menu bar to the frame.
    */
   protected void addMenuBar() {
      ActionListener menuFileListener = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JMenuItem actionMenu = (JMenuItem) e.getSource();
            if (actionMenu == menuFileNew) {
               newFile();
            } else if (actionMenu == menuFileOpen) {
               openFile();
            } else if (actionMenu == menuFileSave) {
               saveFile();
            } else if (actionMenu == menuFileExit) {
               close();
            }
         }
      };

      fileChooser = new JFileChooser();
      imageFilter = new FileNameExtensionFilter("All Image Files",
            ImageIO.getReaderFileSuffixes());
      jpgFilter = new FileNameExtensionFilter("JPEG Files (*.jpg, *.jpeg)",
            "jpg", "jpeg");
      pngFilter = new FileNameExtensionFilter("PNG Files (*.png)", "png");
      // fileChooser.addChoosableFileFilter(imageFilter);
      // fileChooser.addChoosableFileFilter(jpgFilter);
      // fileChooser.addChoosableFileFilter(pngFilter);
      fileChooser.setAcceptAllFileFilterUsed(true);
      file = new File("");

      menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      menuFile = new JMenu("File");
      menuFile.setMnemonic(KeyEvent.VK_F);
      menuBar.add(menuFile);

      menuFileNew = new JMenuItem("New");
      menuFileNew.setMnemonic(KeyEvent.VK_N);
      menuFileNew.addActionListener(menuFileListener);
      menuFile.add(menuFileNew);

      menuFileOpen = new JMenuItem("Open");
      menuFileOpen.setMnemonic(KeyEvent.VK_O);
      menuFileOpen.addActionListener(menuFileListener);
      menuFile.add(menuFileOpen);

      menuFileSave = new JMenuItem("Save");
      menuFileSave.setMnemonic(KeyEvent.VK_S);
      menuFileSave.addActionListener(menuFileListener);
      menuFile.add(menuFileSave);

      menuFile.addSeparator();

      menuFileExit = new JMenuItem("Exit");
      menuFileExit.setMnemonic(KeyEvent.VK_X);
      menuFileExit.addActionListener(menuFileListener);
      menuFile.add(menuFileExit);
   }

   /**
    * Indicates whether the menu bar is visible or not.
    * 
    * @param visible
    *           the new status
    */
   public void setMenuVisible(boolean visible) {
      menuBar.setVisible(visible);
   }

   protected BoardPanel boardPanel; // this class is a client of BoardPanel

   /**
    * Adds the board to the frame.
    * 
    * @param rows
    *           the number of rows of the board
    * @param cols
    *           the number of columns of the board
    */
   protected void addBoard(int rows, int cols) {
      boardPanel = new BoardPanel(rows, cols);
      getContentPane().add(boardPanel, BorderLayout.CENTER);
   }
   
   /**
    * Adds the board to the frame.
    * 
    * @param rows
    *           the number of rows of the board
    * @param cols
    *           the number of columns of the board
    * @param size
    *           the preferred size of each block on the board
    */
   protected void addBoard(int rows, int cols, int size) {
      addBoard(rows, cols);
      setGraphicsDimension(cols * size, rows * size);
   }


   /**
    * Gives the board panel of the frame.
    * 
    * @return the panel
    */
   public BoardPanel getBoardPanel() {
      return boardPanel;
   }

   /**
    * Clears the board (and possibly the related file pointer).
    */
   protected void newFile() {
      file = new File("");
      clearBoard();
   }

   /**
    * Opens a file and the image.
    */
   protected void openFile() {
      fileChooser.setDialogTitle("Open File");
      fileChooser.setSelectedFile(file);
      int rVal = fileChooser.showOpenDialog(this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
         file = fileChooser.getSelectedFile();
         loadGraphicsFile(file);
      }
   }

   /**
    * Saves graphics to a file.
    */
   protected void saveFile() {
      fileChooser.setDialogTitle("Save File");
      fileChooser.setSelectedFile(file);
      int rVal = fileChooser.showSaveDialog(this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
         file = fileChooser.getSelectedFile();
         saveGraphicsFile(file);
      }
   }

   /**
    * Clears the board panel.
    */
   public void clearBoard() {
      boardPanel.clear();
      boardPanel.repaint();
   }

   /**
    * Updates the board panel.
    */
   public void updateBoard() {
      boardPanel.repaint();
   }

   /**
    * Loads a given image to the board panel (after dividing it in blocks).
    * 
    * @param image
    *           the image
    */
   public void loadGraphics(BufferedImage image) {
      if (image != null) {
         boardPanel.clear();
         int rows = boardPanel.rows;
         int cols = boardPanel.cols;
         int bwidth = image.getWidth() / cols;
         int bheight = image.getHeight() / rows;
         for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
               boardPanel.drawImage(i, j, 
                     image.getSubimage(j * bwidth, i * bheight, bwidth, bheight));
            }
         }
         boardPanel.repaint();
      }
   }

   /**
    * Loads image from a file to the board panel.
    * 
    * @param file
    *           the file
    */
   public void loadGraphicsFile(File file) {
      loadGraphics(readImage(file));
   }

   /**
    * Reads an image from a file.
    * 
    * @param file
    *           the file
    * @return the image contained in the file
    */
   protected BufferedImage readImage(File file) {
      try {
         if (file.canRead()) {
            return ImageIO.read(file);
         }
      } catch (IOException exc) {
         showErrorDialog("Failed to load image from file.");
      }
      return null;
   }
   
   /**
    * Saves image from the board panel to a file.
    * 
    * @param file
    *           the file
    */
   public void saveGraphicsFile(File file) {
      writeImage(file, boardPanel.createImage());
   }

   /**
    * Writes an image to a file.
    * 
    * @param file
    *           the file
    * @param image
    *           the image
    */
   protected void writeImage(File file, BufferedImage image) {
      try {
         file.createNewFile();
         if (file.canWrite()) {
            String type = getWriterImageFormat(file);
            if (type != null) {
               ImageIO.write(image, type, file);
            } else {
               showErrorDialog("Unknown image file extension.");
            }
         }
      } catch (IOException exc) {
         showErrorDialog("Failed to save image to file.");
      }
   }

   /**
    * Returns the image format name of a given file, or empty if unknown.
    * 
    * @param file
    *           the file
    * @return the image format
    */
   protected String getWriterImageFormat(File file) {
      String writerFormats[] = ImageIO.getWriterFormatNames();
      String filename = file.getName();
      String format = null;
      for (String f : writerFormats) {
         if (filename.endsWith(f)) {
            format = f;
         }
      }
      return format;
   }

   /**
    * Sets the preferred dimension of the board panel.
    * 
    * @param width
    *           the width of the panel
    * @param height
    *           the height of the panel
    */
   protected void setGraphicsDimension(int width, int height) {
      boardPanel.setPreferredSize(new Dimension(width, height));
      pack();
   }

   /**
    * Shows a dialog with a given message.
    * 
    * @param message
    *           the message
    * @param title
    *           the dialog title
    */
   protected void showMessageDialog(String message, String title) {
      JOptionPane.showMessageDialog(this, message, title,
            JOptionPane.PLAIN_MESSAGE);
   }

   /**
    * Shows a dialog with a given error message.
    * 
    * @param message
    *           the error message
    */
   protected void showErrorDialog(String message) {
      JOptionPane.showMessageDialog(this, message, "Error",
            JOptionPane.ERROR_MESSAGE);
   }

   /**
    * Shows a dialog requesting a user input.
    * 
    * @param message
    *           the message
    * @param init
    *           initial value
    * @return the user input (the null string if canceled)
    */
   protected String showInputDialog(String message, String init) {
      return JOptionPane.showInputDialog(this, message, init);
   }

   /**
    * Shows a dialog requesting an integer number as user input.
    * 
    * @param message
    *           the message
    * @param init
    *           initial value
    * @return the user input (the value 0 if canceled or invalid number)
    */
   protected int showInputDialogInt(String message, int init) {
      try {
         String input = JOptionPane.showInputDialog(this, message, init);
         return Integer.parseInt(input);
      } catch (NumberFormatException e) {
         return 0;
      }
   }

   /**
    * Shows a dialog requesting a floating point number as user input.
    * 
    * @param message
    *           the message
    * @param init
    *           initial value
    * @return the user input (the value 0.0 if canceled or invalid number)
    */
   protected double showInputDialogDouble(String message, double init) {
      try {
         String input = JOptionPane.showInputDialog(this, message, init);
         return Double.parseDouble(input);
      } catch (NumberFormatException e) {
         return 0.0;
      }
   }

   /**
    * A small test of the BoardFrame class.
    */
   public static void main(String[] args) {
      int rows = 10;
      int cols = 20;
      int size = 40;
      BoardFrame frame = new BoardFrame("My Board Frame", rows, cols, size);
      frame.start();

      BoardPanel board = frame.getBoardPanel();
      board.setBackground(Color.WHITE);
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
            if ((i + j) % 2 == 0) {
               board.drawRectangle(i, j, Color.BLACK);
            }
         }
      }
   }

}
