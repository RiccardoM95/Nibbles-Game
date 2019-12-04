import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class NibblesGameStructure {

	protected Snakes snake;
	protected Walls walls;
	protected Food food;

	private int rows, cols;

	private final int growthFactor = 5;
	protected final int initialSafeSpace = 5;

	private int depth = 0;
	protected int points = 0;
	protected int lives = 1;
	
	private boolean changing;
	protected boolean bonusFood;

	protected boolean gameOver;

	
	/**
	 * Class Constructor
	 * @param rows
	 * @param cols
	 * @param extraWalls
	 */
	public NibblesGameStructure(int rows, int cols, ArrayList<Coordinate> extraWalls) {
		this.rows = rows; this.cols = cols;
		
		setBoardObjects(extraWalls);
	}
	
	/**
	 * Initializes the game objects
	 * @param extraWalls
	 */
	private void setBoardObjects(ArrayList<Coordinate> extraWalls) {
		walls = new Walls();
		setWalls();
		if (extraWalls != null)
			addNewWalls(extraWalls);

		snake = new Snakes(new Color(110, 205, 145), 5);
		setSnake();

		food = new Food();
		setFood();
		
	}

	/**
	 * Change the "direction" value of the class variable "snake"
	 * @param direction
	 */
	protected void changeSnakeDirection(int direction) {
		if (snake != null && !changing) {
			snake.changeDirection(direction);
			changing = true;
		}
	}

	/**
	 * Getter for the snake direction
	 */
	public int getSnakeDirection() {
		return snake.direction;
	}

	/**
	 * Randomly puts the snake on free slots
	 */
	private void setSnake() {
		ArrayList<Coordinate> snakePosition = new ArrayList<Coordinate>();

		Coordinate tail = randomSnakeTail();

		for (int i = 0; i < snake.length; i++) {
			snakePosition.add(new Coordinate(tail.getX(), tail.getY() + i));
		}
		snake.changeDirection(KeyEvent.VK_DOWN);

		snake.setPosition(snakePosition);
	}

	/**
	 * Randomly finds a free slot for the tail, considering if there is enough 
	 * space to build the rest of the body
	 */
	private Coordinate randomSnakeTail() {
		boolean done = false;
		int x = 0, y = 0;

		while (!done) {
			x = (int) (Math.random() * (cols - 5));
			y = (int) (Math.random() * (rows - 5));

			if (!walls.isWall(x, y))
				done = true;

			for (int i = 1; i < snake.length + initialSafeSpace && done == true; i++) {
				if (walls.isWall(x, y + i))
					done = false;
			}
		}

		return new Coordinate(x, y);
	}

	/**
	 * Getter for the snake position
	 */
	public ArrayList<Coordinate> getSnake() {
		return snake.position;
	}

	/**
	 * Puts the food randomly on the game board
	 */
	private void setFood() {
		if(Math.random()<0.1) 
			bonusFood = true;
		else
			bonusFood = false;
		
		if (food != null) {
			int x = 0, y = 0;

			while (walls.isWall(x, y) || snake.isSnake(x, y)) {
				x = (int) (Math.random() * (cols - 5));
				y = (int) (Math.random() * (rows - 5));
			}

			food.setPosition(x, y);
		}
	}

	/**
	 * Getter for the food
	 */
	public Coordinate getFood() {
		return food.getPosition();
	}

	/**
	 * Sets the "position" value of the class variable "walls"
	 */
	private void setWalls() {
		if (walls != null) {
			ArrayList<Coordinate> wallsPosition = new ArrayList<Coordinate>();

			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++) {
					if (y == 0 || y == rows - 1) {
						wallsPosition.add(new Coordinate(x, y));
					} else if (x == 0 || x == cols - 1) {
						wallsPosition.add(new Coordinate(x, y));
					}
				}
			}

			walls.setPosition(wallsPosition);
		}
	}

	/**
	 * Adds extra wall the default frame
	 * @param position
	 */
	private void addNewWalls(ArrayList<Coordinate> position) {
		walls.setPosition(position);
	}

	/**
	 * Getter for the walls position
	 */
	public ArrayList<Coordinate> getWalls() {
		return walls.position;
	}

	/**
	 * Checks if the given slot is free, or there is a board object
	 * @param x
	 * @param y
	 */
	private void checkNextSlot(int x, int y) {
		if (walls.isWall(x, y) || snake.isSnake(x, y)) {
			gameOver = true;
			lives --;
		} 
		else if (food.isFood(x, y)) {
			if(bonusFood) {
				lives++;
				GameSounds.playSound("SoundEffect2.wav");
			}
			else
				GameSounds.playSound("SoundEffect.wav");
			
			setFood();
			depth += growthFactor;
			points++;
		}
	}

	/**
	 * Produces the next phase
	 */
	protected void nextStep() {
		if (snake != null && walls != null) {
			int headX = snake.getHead().getX(), headY = snake.getHead().getY();
			int tailX = snake.getTail().getX(), tailY = snake.getTail().getY();

			switch (snake.direction) {
			case (KeyEvent.VK_UP):
				buildNextPhase(headX, headY - 1, tailX, tailY);
				break;
			case (KeyEvent.VK_DOWN):
				buildNextPhase(headX, headY + 1, tailX, tailY);
				break;
			case (KeyEvent.VK_LEFT):
				buildNextPhase(headX - 1, headY, tailX, tailY);
				break;
			case (KeyEvent.VK_RIGHT):
				buildNextPhase(headX + 1, headY, tailX, tailY);
				break;
			}

			changing = false;
		}
	}

	/**
	 * Builds the next phase of the game, changing the snake position accordingly to its direction
	 */
	private void buildNextPhase(int headX, int headY, int tailX, int tailY) {

		checkNextSlot(headX, headY);

		if (depth == 0) {
			snake.moveSnake(headX, headY);
		} else {
			snake.moveSnake(headX, headY, true);
			depth--;
		}

	}
	
	/**
	 * Rebuilt game objects when player lose a life
	 */
	protected void rebuild() {
		snake = new Snakes(new Color(110, 205, 145), 5);
		food = new Food();
		setSnake();
		setFood();
		
		depth += points*growthFactor;
			// restart with snake.length but then increases the dimension accordingly with the points gained
	}

}
