import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MineSweeperGrid extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;   //Last row has only one cell
	private int numBombs = 15;
	private int maxFlags = numBombs;
	private int flagCounter = 0;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public boolean[][] bombArray = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];

	
	public MineSweeperGrid() {
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		
		initializeBoard(); //Runs tile and bomb setup method
	}
	
	public void paintComponent(Graphics g) {	//Method in charge of painting tiles and numbers
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS )));
		}

		//Paint cell colors and paint necessary numbers
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				Color c = getColorArray(x, y);
				g.setColor(c);
				g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				
				//If the tile has been pressed, count surrounding bombs
				if (getColorArray(x, y) == Color.GRAY) {
					int bombCount = adjacentBombs(x, y);
					//Call the method to paint the number
					paintNumber(g, bombCount, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 10, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 22);
				}
			}
		}	
	}
	
	public int getGridX(int x, int y) {		//Return column
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	
	public int getGridY(int x, int y) {		//Return row
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS -1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	
	public int getTotalColumns() {
		return TOTAL_COLUMNS;
	}
	public int getTotalRows() {
		return TOTAL_ROWS;
	}
	public void initializeBoard() {	//This method paints the tiles and sets the bomb locations, used to reset board
		flagCounter = 0; //Set the flag count to 0
		
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //Delete any existing bombs
			for (int y = 0; y < TOTAL_ROWS; y++) {
				bombArray[x][y] = false;
			}
		}
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //Paint all tiles to their covered color
			for (int y = 0; y < TOTAL_ROWS; y++) {
				setColorArray(x, y, Color.WHITE);
			}
		}
		for (int x = 0; x < numBombs;) {		//Assign bombs to random spaces
			int i = new Random().nextInt(TOTAL_COLUMNS);
			int j = new Random().nextInt(TOTAL_ROWS);
			if(bombArray[i][j] == false) { 		//Assigns a bomb if the space doesn't have one already
				bombArray[i][j] = true;
				x++;
			}
		}
	}
	public void setColorArray(int x, int y, Color c) {
		this.colorArray[x][y] = c;
		
	}
	public Color getColorArray(int x, int y){
		return colorArray[x][y];
	}
	public boolean isMine(int x, int y){
		return bombArray[x][y];
	}
	public void minePressed(){
		for(int i = 0; i < TOTAL_COLUMNS; i++){
			for(int j = 0; j < TOTAL_ROWS; j++){
				if(isMine(i,j)){
					setColorArray(i,j, Color.BLACK);
					
				}
				else{
					setColorArray(i,j,Color.GRAY);
				}
			}
		}
		repaint();
		int option = JOptionPane.showConfirmDialog(null, "Try Again?", "You lose", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			initializeBoard();
		}
		else{
			System.exit(0);
		}
	}

	public void addFlag(){
			flagCounter++;
	}
	public void removeFlag(){
		flagCounter--;
	}
	public int getFlagCounter() {
		return flagCounter;
	}
	public int getMaxFlags() {
		return maxFlags;
	}
public int adjacentBombs(int x, int y) {		//Method for counting number of bombs around a tile.
		
		int bombCounter = 0;
		
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				//Check if coordinates are inside the grid
				if(i < getTotalColumns() && i >= 0 && j < getTotalRows() && j >= 0 ) {
					if(isMine(i,j)) {
						bombCounter++;
					}
				}
			}
		}
		return bombCounter;
	}
public void adjacentTiles(int x, int y) { 	//Recursive method to uncover a chain of tiles that have no surrounding bombs
	//Check if there aren't any surrounding bombs
	if(adjacentBombs(x, y) == 0) {
		for(int i=x-1; i<=x+1; i++) {
			for (int j=y-1; j<=y+1; j++) {
				//Check if coordinates are inside the grid
				if(i < getTotalColumns() && i >= 0 && j < getTotalRows() && j >= 0) {
					//Check that tile is uncovered
					if(getColorArray(i,j) == Color.WHITE){
						setColorArray(i, j, Color.GRAY);
						adjacentTiles(i, j);
					}
				}
			}
		}
	}
}
	public void paintNumber(Graphics g, int bombCount, int x, int y) {		//Method to paint each number with a different color and display it
		switch(bombCount){
		case 0:
			break;
		case 1:
			g.setColor(Color.BLUE);
			break;
		case 2:
			g.setColor(Color.GREEN);
			break;
		case 3:
			g.setColor(Color.RED);
			break;
		case 4:
			g.setColor(Color.YELLOW);
			break;
		case 5:
			g.setColor(Color.ORANGE);
			break;
		case 6:
			g.setColor(Color.MAGENTA);
			break;
		case 7:
			g.setColor(Color.PINK);
			break;
		case 8:
			g.setColor(Color.WHITE);
			break;
			
		}
		g.setFont(new Font("default", Font.BOLD, 18));
		g.drawString(bombCount + "" + "", x, y);
	}
	public void isWinner(){
		for(int i = 0; i < getTotalColumns(); i++){
			for(int j = 0; j < getTotalRows(); j++){
				if((getColorArray(i,j) == Color.WHITE || getColorArray(i,j) == Color.RED) && isMine(i,j) == false){
					return;
				}
			}
		}
		repaint();
		int option = JOptionPane.showConfirmDialog(null, "Try Again?", "You Win", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			initializeBoard();
		}
		else{
			System.exit(0);
		}
	}
}