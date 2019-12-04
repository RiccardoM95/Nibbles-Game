/**
 * A class for storing x/y-coordinates.
 * 
 * @author Hendrik Speleers
 * @author NMCGJ, AY 2018-2019
 */
public class Coordinate {

   private int x;
   private int y;

   /**
    * Constructs an object with the coordinates (0, 0).
    */
   public Coordinate() {
      this(0, 0);
   }

   /**
    * Constructs an object with the given coordinates (x, y).
    * 
    * @param x
    *           the x-coordinate
    * @param y
    *           the y-coordinate
    */
   public Coordinate(int x, int y) {
      setX(x);
      setY(y);
   }

   /**
    * Gives the x-coordinate.
    * 
    * @return the value of the x-coordinate
    */
   public int getX() {
      return x;
   }

   /**
    * Sets the x-coordinate.
    * 
    * @param x
    *           the new value of the x-coordinate
    */
   public void setX(int x) {
      this.x = x;
   }

   /**
    * Gives the y-coordinate.
    * 
    * @return the value of the x-coordinate
    */
   public int getY() {
      return y;
   }

   /**
    * Sets the y-coordinate.
    * 
    * @param y
    *           the new value of the y-coordinate
    */
   public void setY(int y) {
      this.y = y;
   }

}
