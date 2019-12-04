import java.awt.event.*;

/**
 * A JFrame for 2D animated graphics organized on a board and keyboard actions.
 * 
 * @author Hendrik Speleers
 * @author NMCGJ, AY 2018-2019
 */
abstract public class KeyAnimationBoardFrame extends AnimationBoardFrame {

   private static final long serialVersionUID = 1L;

   /**
    * Constructs a key animation board frame with a given title.
    * 
    * @param title
    *           the frame title
    * @param rows
    *           the number of rows of the board
    * @param columns
    *           the number of columns of the board
    */
   public KeyAnimationBoardFrame(String title, int rows, int columns) {
      super(title, rows, columns);
      addKeyComponents();
   }

   /**
    * Constructs a key animation board frame with a given title.
    * 
    * @param title
    *           the frame title
    * @param rows
    *           the number of rows of the board
    * @param columns
    *           the number of columns of the board
    * @param size
    *           the preferred size of each block on the board
    */
   public KeyAnimationBoardFrame(String title, int rows, int columns, int size) {
      super(title, rows, columns, size);
      addKeyComponents();
   }

   /**
    * Adds the keyboard components to the frame.
    */
   protected void addKeyComponents() {
      KeyAdapter keyAdapter = new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            processKey(e);
         }
      };

      boardPanel.addKeyListener(keyAdapter);
      boardPanel.setFocusable(true);
   }

   /**
    * Processes the given key event.
    * 
    * @param e
    *           the key event
    */
   abstract protected void processKey(KeyEvent e);

}
