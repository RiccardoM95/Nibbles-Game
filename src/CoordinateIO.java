import java.io.*;
import java.util.*;

/**
 * Input/output for coordinates.
 * 
 * @author Hendrik Speleers
 * @author NMCGJ, AY 2018-2019
 */
public class CoordinateIO {
   
   // Each line in the text file must start with two integer values 
   // (x-coordinate and y-coordinate), separated by a space character. 
   // Some comments can be added as long as they are separated from 
   // the second integer by a space character.

   /**
    * Reads the coordinates from a text file and puts them in a list.
    * 
    * @param filename
    *           the name of the file
    */
   public static ArrayList<Coordinate> read(String filename) {
      return read(new File(filename));
   }

   /**
    * Reads the coordinates from a text file and puts them in a list.
    * 
    * @param file
    *           the file
    * @return a list of coordinates
    */
   public static ArrayList<Coordinate> read(File file) {
      try {
         if (file.canRead()) {
            return read(new FileReader(file));
         }
      } catch (IOException exc) {
         //
      }
      return new ArrayList<Coordinate>();
   }

   /**
    * Reads the coordinates from an input stream and puts them in a list.
    * 
    * @param in
    *           the input stream reader
    * @return a list of coordinates
    */
   public static ArrayList<Coordinate> read(Reader in) {
      ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
      try {
         coords.clear();
         BufferedReader reader = new BufferedReader(in);
         String line;
         while ((line = reader.readLine()) != null) {
            String[] coord = line.split("\\s+");
            if (coord.length >= 2) {
               try {
                  int x = Integer.parseInt(coord[0]);
                  int y = Integer.parseInt(coord[1]);
                  coords.add(new Coordinate(x, y));
               } catch (NumberFormatException exc) {
                  //
               }
            }
         }
         reader.close();
      } catch (IOException exc) {
         //
      }
      return coords;
   }

   /**
    * Writes a list of coordinates to a text file.
    * 
    * @param filename
    *           the name of the file
    * @param list
    *           the list of coordinates
    */
   public static void write(String filename, ArrayList<Coordinate> list) {
      write(new File(filename), list);
   }

   /**
    * Writes a list of coordinates to a text file.
    * 
    * @param file
    *           the file
    * @param list
    *           the list of coordinates
    */
   public static void write(File file, ArrayList<Coordinate> list) {
      try {
         file.createNewFile();
         if (file.canWrite()) {
            write(new FileWriter(file), list);
         }
      } catch (IOException exc) {
         //
      }
   }

   /**
    * Writes a list of coordinates to an output stream.
    * 
    * @param out
    *           the output stream writer
    * @param list
    *           the list of coordinates
    */
   public static void write(Writer out, ArrayList<Coordinate> list) {
      PrintWriter writer = new PrintWriter(new BufferedWriter(out));
      for (Coordinate coord : list) {
         writer.println(coord.getX() + " " + coord.getY());
      }
      writer.close();
   }

}
