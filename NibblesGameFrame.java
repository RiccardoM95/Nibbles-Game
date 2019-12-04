import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class NibblesGameFrame extends KeyAnimationBoardFrame {

	private static final long serialVersionUID = 1L;

	private static int rows = 50;
	private static int cols = 50;
	private static int size = 15;

	private final int winCondition = 10;

	private BoardPanel board;
	private NibblesGameStructure gameTable;
	
	private static ArrayList<Coordinate> extraWalls;

	public NibblesGameFrame(String title, int rows, int columns) {
		super(title, rows, columns);
	}

	public NibblesGameFrame(String title, int rows, int columns, int size) {
		super(title, rows, columns, size);
	}

	protected void processKey(KeyEvent e) {
		if (gameTable != null) {
			if (e.getKeyCode() == KeyEvent.VK_P && !isAnimPaused)
				pauseAnimation();
			else if (e.getKeyCode() == KeyEvent.VK_S)
				stopAnimation();
			else if (e.getKeyCode() == KeyEvent.VK_P && isAnimPaused)
				playAnimation();
			else
				gameTable.changeSnakeDirection(e.getKeyCode());
		}
	}

	protected void animateInit() {
		showMessageDialog(
				"1) Directional arrows to move\n" + "2) [P] to Pause or Resume the game\n" + "3) [S] to Stop the game",
				"COMMANDS");
		showMessageDialog(winCondition + " Points!", "GOAL");

		GameSounds.playMusic("MainTheme.wav");
		gameSettings();

		board = getBoardPanel();
		board.setBackground(new Color(153, 255, 204));
		
		gameTable = new NibblesGameStructure(rows, cols, extraWalls);
		if(!gameTable.checkSpace()) {
			showMessageDialog("Sorry, no space for snake with this walls position","ERROR");
			animateFinal();
		}
	}

	private void gameSettings() {
		String msgGame = "Choose Speed:\n" + "1) Slow\n" + "2) Normal\n" + "3) Fast\n";
		int speed = showInputDialogInt(msgGame, 1);
		switch (speed) {
		case 1:
			setAnimationDelay(110);
			break;
		case 2:
			setAnimationDelay(75);
			break;
		case 3:
			setAnimationDelay(30);
			break;
		default:
			showMessageDialog("Invalid Input", "ERROR");
			gameSettings();
			break;
		}
	}

	protected void animateNext() {
		if (gameTable.getPoints() == winCondition) {
			GameSounds.playSound("WinSound.wav");
			win();
		}
		gameTable.nextStep();
		if (gameTable.gameOver) {
			GameSounds.stopMusic();
			GameSounds.playSound("LoseSound.wav");
			stopAnimation();
			showMessageDialog("SCORE = " + gameTable.getPoints() + " Points!", "GAME OVER");
		} else
			printNextPhase();

		board.repaint();
	}

	protected void animateFinal() {
		clearBoard();
		GameSounds.stopMusic();
	}

	private void printNextPhase() {
		board.clear();
		printWalls();
		printFood();
		printSnake();
	}

	private void win() {
		GameSounds.stopMusic();
		stopAnimation();
		showMessageDialog("SCORE = " + winCondition + " points!", "YOU HAVE WON !!!");
	}

	public void loadGraphicsFile(File file) {
		if (!isAnimPaused) {
			showMessageDialog("The game is loaded from " + file.getName(), "TODO");
			ArrayList<Coordinate> wallPosition = CoordinateIO.read(file);

			Coordinate dimensions = wallPosition.get(0);
			cols = dimensions.getX();
			rows = dimensions.getY();
			wallPosition.remove(0);

			if (checkWallFile(wallPosition)) {
				extraWalls = wallPosition;
				setGraphicsDimension(cols*size,rows*size);
				addBoard(rows,cols);
			}
			else {
				showMessageDialog("Sorry, walls position is not acceptable","ERROR");
				animateFinal();
			}
		}
	}

	private boolean checkWallFile(ArrayList<Coordinate> wallPosition) {
		for (int i = 0; i < wallPosition.size(); i++) {
			if (wallPosition.get(i).getX() >= cols || wallPosition.get(i).getY() >= rows)
				return false;
		}
		
		return true;
	}

	// ---------- PRINT METHODS ----------

	private void printSnake() {
		ArrayList<Coordinate> snakePosition = gameTable.getSnake();
		int size = snakePosition.size() - 1;

		for (int i = 0; i < size; i++) {

			board.drawRectangle(snakePosition.get(i).getY(), snakePosition.get(i).getX(), gameTable.snake.color);
		}
		board.drawRectangle(snakePosition.get(size).getY(), snakePosition.get(size).getX(), gameTable.snake.headColor);
	}

	private void printFood() {
		Coordinate foodPosition = gameTable.getFood();
		board.drawRectangle(foodPosition.getY(), foodPosition.getX(), gameTable.food.getColor());
	}

	private void printWalls() {
		ArrayList<Coordinate> wallsPosition = gameTable.getWalls();

		for (int i = 0; i < wallsPosition.size(); i++) {
			board.drawRectangle(wallsPosition.get(i).getY(), wallsPosition.get(i).getX(), gameTable.walls.color);
		}
	}

	// -----------------------------------

	public static void main(String[] args) {
		NibblesGameFrame frame = new NibblesGameFrame("Nibbles", rows, cols, size);
		frame.start();
	}

}
