import java.awt.*;
import java.util.*;

import com.sun.glass.events.KeyEvent;

public class Snakes extends BoardObjects {

	protected final Color headColor = Color.BLACK;
	protected int direction;
	protected int lenght;

	protected Snakes(Color color, int lenght) {
		setColor(color);
		setLenght(lenght);
	}
	
	protected void setPosition(ArrayList<Coordinate> position) {
		this.position = position;
	}
	
	public ArrayList<Coordinate> getPosition() {
		return position;
	}
	
	protected void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	protected void setLenght(int lenght) {
		this.lenght = lenght;
	}
	
	public int getLenght() {
		return lenght;
	}

	protected boolean isSnake(int x, int y) {
		if (position != null) {
			for(int i=0; i<position.size(); i++) {
				if(position.get(i).getX()==x && position.get(i).getY()==y)
					return true;
			}
		}
		
		return false;
	}

	protected Coordinate getHead() {
		if (position != null) {
			int size = position.size() - 1;
			return position.get(size);
		} else
			return null;
	}

	protected Coordinate getTail() {
		if (position != null) {
			return position.get(0);
		} else
			return null;
	}

	protected void moveSnake(int x, int y, boolean isGrowning) {
		if (!isGrowning) {
			position.remove(0);
		}
		position.add(new Coordinate(x, y));
	}

	protected void moveSnake(int x, int y) {
		moveSnake(x, y, false);
	}

	// next slot after the head
	protected void changeDirection(int direction) {
		final int up = KeyEvent.VK_UP;
		final int down = KeyEvent.VK_DOWN;
		final int left = KeyEvent.VK_LEFT;
		final int right = KeyEvent.VK_RIGHT;

		switch (direction) {
		case up:
			if (this.direction != down)
				this.direction = up;
			break;
		case down:
			if (this.direction != up)
				this.direction = down;
			break;
		case left:
			if (this.direction != right)
				this.direction = left;
			break;
		case right:
			if (this.direction != left)
				this.direction = right;
			break;
		}
	}

}
