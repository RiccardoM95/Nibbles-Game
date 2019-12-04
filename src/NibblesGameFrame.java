import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class NibblesGameFrame extends KeyAnimationBoardFrame {

	private static final long serialVersionUID = 1L;

	private static int rows = 50;
	private static int cols = 50;
	private static int size = 15;

	private final int winCondition = 20;

	private BoardPanel board;
	private NibblesGameStructure gameTable;
	
	private static ArrayList<Coordinate> extraWalls;

	
	/**
	 * Class Constructor
	 * @param title
	 * @param rows
	 * @param columns
	 */
	public NibblesGameFrame(String title, int rows, int columns) {
		super(title, rows, columns);
	}

	/**
	 * Class Constructor
	 * @param title
	 * @param rows
	 * @param columns
	 * @param size
	 */
	public NibblesGameFrame(String title, int rows, int columns, int size) {
		super(title, rows, columns, size);
	}

	/**
	 * Gets keyboard inputs
	 */
	protected void processKey(KeyEvent e) {
		int input = e.getKeyCode();
		
		if (input == KeyEvent.VK_P && !isAnimPaused)
			pauseAnimation();
		else if (input == KeyEvent.VK_E)
			stopAnimation();
		else if (input == KeyEvent.VK_P && isAnimPaused)
			playAnimation();
		else if(input == KeyEvent.VK_S && gameTable != null) {
			pauseAnimation();
			showMessageDialog("Current score is: "+gameTable.points+" Points!\n"
							+ (winCondition - gameTable.points)+" Points left to win!\n"
							+ "Current lives: "+gameTable.lives,"STATUS");
			playAnimation();
		}
		else if(gameTable != null)
			gameTable.changeSnakeDirection(input);
	}

	/**
	 * Starts the game after giving some information, playing some music and setting all the game data
	 */
	protected void animateInit() {
		showMessageDialog("1) Directional arrows to move\n" 
						+ "2) [P] to Pause and Resume the game\n"
						+ "3) [E] to End the game\n" 
						+ "3) [S] to see the current status (score and lives left)", "COMMANDS");
		showMessageDialog("Cakes give you an extra life! (you start with just 1)"
						+ "\nThe goal is to reach "+ winCondition + " Points without losing all of your lives.\n"
						+ "You lose one by hitting walls or snake's body.", "RULES");

		GameSounds.playMusic("MainTheme.wav");
		gameSettings();

		board = getBoardPanel();
		board.setBackground(new Color(153, 255, 204));
		
		gameTable = new NibblesGameStructure(rows, cols, extraWalls);
	}

	/**
	 * Sets the speed of the Snake
	 */
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

	/**
	 * Produces the next phase, after the win and lose conditions are checked
	 */
	protected void animateNext() {
		if (gameTable.points == winCondition) {
			GameSounds.playSound("WinSound.wav");
			win();
		}
		
		gameTable.nextStep();
		
		if (gameTable.gameOver)
			manageGameOver();
		else
			printNextPhase();

		board.repaint();
	}
	
	/**
	 * Ends the game or rebuilds the phase, accordingly with lives left
	 */
	private void manageGameOver() {
		
		if (gameTable.lives>0) {
			gameTable.rebuild();
			pauseAnimation();
			showMessageDialog(gameTable.lives + " left!", "Life Lost");
			playAnimation();
			gameTable.gameOver = false;
		}
		else{
			GameSounds.stopMusic();
			GameSounds.playSound("GameOver.wav");
			stopAnimation();
			showMessageDialog("SCORE = " + gameTable.points + " Points!", "GAME OVER");
		}
		
	}

	/**
	 * Ends the animation
	 */
	protected void animateFinal() {
		clearBoard();
		GameSounds.stopMusic();
	}

	/**
	 * Collection of all the printing methods
	 */
	private void printNextPhase() {
		board.clear();
		printWalls();
		printFood();
		printSnake();
	}

	/**
	 * Stops the animation and displays a victory message
	 */
	private void win() {
		GameSounds.stopMusic();
		stopAnimation();
		showMessageDialog("SCORE = " + winCondition + " points!", "YOU HAVE WON !!!");
	}

	/**
	 * Loads external "levels"
	 */
	public void loadGraphicsFile(File file) {
		if (!isAnimEnabled && CoordinateIO.read(file).size() >= 2) {
			showMessageDialog("The game is loaded from " + file.getName(), "TODO");
			ArrayList<Coordinate> wallPosition = CoordinateIO.read(file);

			Coordinate dimensions = wallPosition.get(0);
			wallPosition.remove(0);

			if ( checkWallFile(dimensions, wallPosition) ) {	
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

	/**
	 * Checks if the file data are acceptable for our game
	 * @param wallPosition
	 * @return
	 */
	private boolean checkWallFile(Coordinate dimensions, ArrayList<Coordinate> wallPosition) {
		
		// I choose minimum value for the dimensions
		if(dimensions.getX() >= 10 && dimensions.getY() >= 10) {
			// I add a cast to be sure that the value is an integer
			cols = (int) dimensions.getX();
			rows = (int) dimensions.getY();
		}
		else
			return false;
		
		for (int i = 0; i < wallPosition.size(); i++) {
			if (wallPosition.get(i).getX() >= cols || wallPosition.get(i).getY() >= rows)
				return false;
		}
		
		return true;
	}

	// ---------- PRINT METHODS ----------

	/**
	 * Prints the snake
	 */
	private void printSnake() {
		if(gameTable != null) {
			ArrayList<Coordinate> snakePosition = gameTable.getSnake();
			int size = snakePosition.size() - 1;
			int x, y;

			for (int i = 0; i <= size; i++) {
				x = snakePosition.get(i).getX();
				y = snakePosition.get(i).getY();
				if (i<size)
					board.drawRectangle(y, x, gameTable.snake.color,90);
				else
					board.drawRectangle(y, x, gameTable.snake.headColor,90);
			}
		}
	}

	/**
	 * Prints the food package
	 */
	private void printFood() {
		if(gameTable != null) {
			Coordinate foodPosition = gameTable.getFood();
			File imageFile;
			
			if(gameTable.bonusFood)
				imageFile = gameTable.food.getImageFile(true);
			else
				imageFile = gameTable.food.getImageFile(false);
			
			board.drawImage(foodPosition.getY(), foodPosition.getX(), readImage(imageFile),130);
		}
	}

	/**
	 * Prints walls
	 */
	private void printWalls() {
		if(gameTable != null) {
			ArrayList<Coordinate> wallsPosition = gameTable.getWalls();
			int x, y;
			
			for (int i = 0; i < wallsPosition.size(); i++) {
				x = wallsPosition.get(i).getX();
				y = wallsPosition.get(i).getY();
				board.drawRectangle(y, x, gameTable.walls.color);
			}
			
		}
	}

	// -----------------------------------

	public static void main(String[] args) {
		NibblesGameFrame frame = new NibblesGameFrame("Nibbles", rows, cols, size);
		frame.start();
	}

}
