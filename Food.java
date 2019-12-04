import java.awt.Color;

public class Food {
	
	private Coordinate position;
	private Color color;
	
	protected Food() {
		color = Color.RED;
	}
	
	public Color getColor() {
		return color;
	}
	
	protected void setPosition(int x, int y) {
		position = new Coordinate(x,y);
	}
	
	public Coordinate getPosition() {
		return position;
	}
	
	protected boolean isFood(int x, int y) {
		if(position.getX()==x && position.getY()==y) {
			return true;
		}
		else
			return false;
	}

}
