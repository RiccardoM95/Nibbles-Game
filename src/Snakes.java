import java.awt.*;
import java.util.*;

import com.sun.glass.events.KeyEvent;

public class Snakes extends BoardObjects {

	protected final Color headColor = Color.BLACK;
	protected int direction;
	protected int length;

	
	/**
	 * Class Constructor
	 * @param color
	 * @param length
	 */
	protected Snakes(Color color, int length) {
		setColor(color);
		setLength(length);
	}
	
	/**
	 * Sets the snake position
	 */
	protected void setPosition(ArrayList<Coordinate> position) {
		this.position = position;
	}
	
	/**
	 * Getter for the snake position
	 */
	public ArrayList<Coordinate> getPosition() {
		return position;
	}
	
	/**
	 * Sets the snake color
	 */
	protected void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Getter for the snake color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Sets the snake length
	 * @param length
	 */
	protected void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Getter for the snake length
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Getter for the head of the snake
	 */
	protected Coordinate getHead() {
		if (position != null) {
			int size = position.size() - 1;
			return position.get(size);
		} else
			return null;
	}

	/**
	 * Getter for the tail of the snake 
	 */
	protected Coordinate getTail() {
		if (position != null) {
			return position.get(0);
		} else
			return null;
	}

	/**
	 * Changes the position of the snake
	 * @param x
	 * @param y
	 * @param isGrowning
	 */
	protected void moveSnake(int x, int y, boolean isGrowning) {
		if (!isGrowning) {
			position.remove(0);
		}
		position.add(new Coordinate(x, y));
	}

	/**
	 * Changes the position of the snake
	 * @param x
	 * @param y
	 */
	protected void moveSnake(int x, int y) {
		moveSnake(x, y, false);
	}

	/**
	 * Changes the direction of the snake, accordingly to the previous direction
	 * @param direction
	 */
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
	
	/**
	 * Checks if the given slot is occupied by a snake element
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean isSnake(int x, int y) {
		if (position != null) {
			for(int i=0; i<position.size(); i++) {
				if(position.get(i).getX()==x && position.get(i).getY()==y)
					return true;
			}
		}
		
		return false;
	}

}
