import java.awt.Color;
import java.util.ArrayList;

public abstract class BoardObjects {	
	
	protected Color color;
	
	protected ArrayList<Coordinate> position;
	
	protected abstract void setPosition(ArrayList<Coordinate> position);
	
	public abstract ArrayList<Coordinate> getPosition();
	
	protected abstract void setColor(Color color);
	
	public abstract Color getColor();

}
