import java.awt.Color;
import java.util.ArrayList;

public class Walls extends BoardObjects {
	
	/**
	 * Class Constructor
	 */
	protected Walls() {
		setColor(new Color(210,210,210));
	}
	
	/**
	 * Sets walls position
	 */
	protected void setPosition(ArrayList<Coordinate> position) {
		if(this.position != null)
			this.position.addAll(position);
		else
			this.position = position;
	}
	
	/**
	 * Getter for the walls position
	 */
	public ArrayList<Coordinate> getPosition(){
		return position;
	}
	
	/**
	 * Sets the walls color
	 */
	protected void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Getter for the walls color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Checks if the given slot is occupied by a wall element
	 * @param x
	 * @param y
	 */
	protected boolean isWall(int x, int y) {
		if (position != null) {
			for(int i=0; i<position.size(); i++) {
				if(position.get(i).getX()==x && position.get(i).getY()==y)
					return true;
			}
		}
		
		return false;
	}

}
