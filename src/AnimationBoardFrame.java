import java.awt.event.*;
import javax.swing.*;

/**
 * A JFrame for 2D animated graphics organized on a board.
 * 
 * @author Hendrik Speleers
 * @author NMCGJ, AY 2018-2019
 */
abstract public class AnimationBoardFrame extends BoardFrame {

   private static final long serialVersionUID = 1L;

   /**
    * Constructs an animation board frame with a given title.
    * 
    * @param title
    *           the frame title
    * @param rows
    *           the number of rows of the board
    * @param cols
    *           the number of columns of the board
    */
   public AnimationBoardFrame(String title, int rows, int cols) {
      super(title, rows, cols);
      addMenuAnimation();
   }

   /**
    * Constructs an animation board frame with a given title.
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
   public AnimationBoardFrame(String title, int rows, int cols, int size) {
      super(title, rows, cols, size);
      addMenuAnimation();
   }

   protected JMenu menuAnim;
   protected JMenuItem menuAnimPlay, menuAnimPause, menuAnimStop;

   /**
    * Adds the animation menu to the menu bar.
    */
   protected void addMenuAnimation() {
      ActionListener menuAnimListener = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JMenuItem actionMenu = (JMenuItem) e.getSource();
            if (actionMenu == menuAnimPlay) {
               playAnimation();
            } else if (actionMenu == menuAnimPause) {
               pauseAnimation();
            } else if (actionMenu == menuAnimStop) {
               stopAnimation();
            }
         }
      };

      menuAnim = new JMenu("Animation");
      menuAnim.setMnemonic(KeyEvent.VK_A);
      menuBar.add(menuAnim);

      menuAnimPlay = new JMenuItem("Play");
      menuAnimPlay.setMnemonic(KeyEvent.VK_P);
      menuAnimPlay.addActionListener(menuAnimListener);
      menuAnim.add(menuAnimPlay);

      menuAnimPause = new JMenuItem("Pause");
      menuAnimPause.setMnemonic(KeyEvent.VK_U);
      menuAnimPause.addActionListener(menuAnimListener);
      menuAnim.add(menuAnimPause);

      menuAnimStop = new JMenuItem("Stop");
      menuAnimStop.setMnemonic(KeyEvent.VK_T);
      menuAnimStop.addActionListener(menuAnimListener);
      menuAnim.add(menuAnimStop);
   }

   protected boolean isAnimEnabled;
   protected boolean isAnimPaused;
   protected long delay;

   /**
    * Plays the animation when not currently active or resumes the animation
    * when paused. It has no effect if the animation is already running.
    */
   public void playAnimation() {
      if (!isAnimEnabled) {
         isAnimEnabled = true;
         isAnimPaused = false;
         
         Thread thread = new Thread() {
            public void run() {
               animateInit();
               while (isAnimEnabled) {
                  if (!isAnimPaused) {
                     animateNext();
                  }
                  try {
                     Thread.sleep(delay);
                  } catch (InterruptedException e) {
                     //
                  }
               }
               animateFinal();
            }
         };
         thread.setDaemon(true);
         thread.start();
         
      } else if (isAnimPaused) {
         isAnimPaused = false;
      }
   }

   /**
    * Pauses the animation.
    */
   public void pauseAnimation() {
      isAnimPaused = true;
   }

   /**
    * Stops the animation.
    */
   public void stopAnimation() {
      isAnimEnabled = false;
   }

   /**
    * Indicates whether the animation is enabled or not.
    * 
    * @return the animation status
    */
   protected boolean isAnimationEnabled() {
      return isAnimEnabled;
   }

   /**
    * Indicates whether the animation is paused or not.
    * 
    * @return the animation status
    */
   protected boolean isAnimationPaused() {
      return isAnimPaused;
   }
   
   /**
    * Sets the delay time (in milliseconds) between each animation step.
    * 
    * @param millis
    *           number of milliseconds
    */
   protected void setAnimationDelay(long millis) {
      delay = millis;
   }
   
   /**
    * Gets the delay time (in milliseconds) between each animation step.
    * 
    * @return number of milliseconds
    */
   protected long getAnimationDelay() {
      return delay;
   }
   
   /**
    * Initializes a new animation (is called before the start of the animation).
    */
   abstract protected void animateInit();
   
   /**
    * Executes the next step in the animation.
    */
   abstract protected void animateNext();
   
   /**
    * Finalizes the animation (is called after the end of the animation).
    */
   abstract protected void animateFinal();

}
