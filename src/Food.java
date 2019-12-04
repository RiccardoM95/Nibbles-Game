import java.io.File;

public class Food {
	
	private Coordinate position;
	private File imageFile;
	private File imageFileBonus;
	
	/**
	 * Class Constructor
	 */
	protected Food() {
		imageFile = new File("strawberry.png");
		imageFileBonus = new File("cake.png");
	}
	
	/**
	 * Getter for the image file
	 * @return
	 */
	public File getImageFile(boolean bonus) {
		if(bonus)
			return imageFileBonus;
		else
			return imageFile;
	}
	
	/**
	 * Sets the position of the food package
	 * @param x
	 * @param y
	 */
	protected void setPosition(int x, int y) {
		position = new Coordinate(x,y);
	}
	
	/**
	 * Getter of the food position
	 * @return
	 */
	public Coordinate getPosition() {
		return position;
	}
	
	/**
	 * Checks if there is food in the given position
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean isFood(int x, int y) {
		if(position.getX()==x && position.getY()==y) {
			return true;
		}
		else
			return false;
	}

}
