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
	private int points = 0;
	private boolean changing;

	protected boolean gameOver;

	public NibblesGameStructure(int rows, int cols, ArrayList<Coordinate> extraWalls) {
		this.rows = rows; this.cols = cols;
		
		setBoardObjects(extraWalls);
	}
	
	private void setBoardObjects(ArrayList<Coordinate> extraWalls) {
		walls = new Walls(rows, cols);
		setWalls();
		if (extraWalls != null)
			addNewWalls(extraWalls);

		snake = new Snakes(new Color(110, 205, 145), 5);
		setSnake();

		food = new Food();
		setFood();
	}

	protected void changeSnakeDirection(int direction) {
		if (snake != null && !changing) {
			snake.changeDirection(direction);
			changing = true;
		}
	}

	public int getSnakeDirection() {
		return snake.direction;
	}

	private void setSnake() {
		ArrayList<Coordinate> snakePosition = new ArrayList<Coordinate>();

		Coordinate tail = randomSnakeTail();

		for (int i = 0; i < snake.lenght; i++) {
			snakePosition.add(new Coordinate(tail.getX(), tail.getY() + i));
		}
		snake.changeDirection(KeyEvent.VK_DOWN);

		snake.setPosition(snakePosition);
	}

	private Coordinate randomSnakeTail() {
		boolean done = false;
		int x = 0, y = 0;

		while (!done) {
			x = (int) (Math.random() * (cols - 5));
			y = (int) (Math.random() * (rows - 5));

			if (!walls.isWall(x, y))
				done = true;

			for (int i = 1; i < snake.lenght + initialSafeSpace && done == true; i++) {
				if (walls.isWall(x, y + i))
					done = false;
			}
		}

		return new Coordinate(x, y);
	}

	protected boolean checkSpace() {
		int freeSlotsNeeded = snake.lenght + initialSafeSpace;
		int free = 0;

		for (int x = 1; x < cols - 1; x++) {
			for (int y = 1; y < rows - 1; y++) {
				if (!walls.isWall(x, y) && !food.isFood(x, y))
					free++;
				else
					free = 0;

				if (free == freeSlotsNeeded)
					return true;
			}
		}

		return false;
	}

	public ArrayList<Coordinate> getSnake() {
		return snake.position;
	}

	private void setFood() {
		if (food != null) {
			int x = 0, y = 0;

			while (walls.isWall(x, y) || snake.isSnake(x, y)) {
				x = (int) (Math.random() * (cols - 5));
				y = (int) (Math.random() * (rows - 5));
			}

			food.setPosition(x, y);
		}
	}

	public Coordinate getFood() {
		return food.getPosition();
	}

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

	private void addNewWalls(ArrayList<Coordinate> position) {
		walls.setPosition(position);
	}

	public ArrayList<Coordinate> getWalls() {
		return walls.position;
	}

	private void checkNextSlot(int x, int y) {
		if (walls.isWall(x, y) || snake.isSnake(x, y)) {
			gameOver = true;
		} else if (food.isFood(x, y)) {
			GameSounds.playSound("SoundEffect.wav");
			setFood();
			depth += growthFactor;
			points++;
		}
	}

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

	private void buildNextPhase(int headX, int headY, int tailX, int tailY) {

		checkNextSlot(headX, headY);

		if (depth == 0) {
			snake.moveSnake(headX, headY);
		} else {
			snake.moveSnake(headX, headY, true);
			depth--;
		}

	}

	public int getPoints() {
		return points;
	}

}
