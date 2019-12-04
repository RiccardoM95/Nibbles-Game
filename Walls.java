import java.awt.Color;
import java.util.ArrayList;

public class Walls extends BoardObjects {
	
	private final boolean[][] wallMatrix;
	
	protected Walls(int rows, int cols) {
		setColor(new Color(210,210,210));
		wallMatrix = new boolean[rows][cols];
	}
	
	protected void setPosition(ArrayList<Coordinate> position) {
		if(this.position != null)
			this.position.addAll(position);
		else
			this.position = position;
		
		buildWalls();
	}
	
	public ArrayList<Coordinate> getPosition(){
		return position;
	}
	
	protected void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	private void buildWalls() {
		for(int i=0; i<position.size(); i++) {
			wallMatrix[position.get(i).getY()][position.get(i).getX()] = true;
		}
	}
	
	protected boolean isWall(int x, int y) {
		return wallMatrix[y][x];
	}

}
