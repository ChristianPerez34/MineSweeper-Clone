import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanelProject extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;
	
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	
	//Arrays for Minesweeper Game
	public boolean[][] minesArray = new boolean [TOTAL_COLUMNS][TOTAL_ROWS];
	public boolean[][] minesNeighbor = new boolean [TOTAL_COLUMNS][TOTAL_ROWS];
	
	
	
	
	Random randMine = new Random();
	int numMines = randMine.nextInt(10);
	
	public int flagCounter = 0;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	
	public MyPanelProject() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The whole grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
	initializeBoard();
	}
	
	public void initializeBoard() {
		//Set up the main game.
		
				flagCounter = 0; //set amount of flags to 0
				
			for(int x = 0; x < TOTAL_COLUMNS; x++){     //Creates 9x9 mine field
				for(int y = 0; y < TOTAL_ROWS; y++){
					setColorArray(x, y, Color.WHITE);
					}
				}
				//Randomly generates bombs in the mine field
				
			for(int x = 0; x < TOTAL_COLUMNS; x++){
				for(int y = 0; y < TOTAL_ROWS; y++){
					minesArray[x][y] = false;
				}
			}
			for(int x = 0; x < numMines;){
				int i = randMine.nextInt(TOTAL_COLUMNS);
				int j = randMine.nextInt(TOTAL_ROWS);
				
					if(minesArray[i][j] == false) {
						minesArray[i][j] = true;
						x++;
					}
				}
			} 
					
	public boolean isMine(int x, int y){
		return minesArray[x][y];
	}
	
	public void minePressed() {
		for(int x = 0; x < TOTAL_COLUMNS; x++){
			for(int y = 0; y < TOTAL_ROWS; y++){
				if(isMine(x,y)){
					setColorArray(x, y, Color.BLACK);
				}else {
					setColorArray(x, y, Color.GRAY);
				}
			}
		}
	repaint();
	int option = JOptionPane.showConfirmDialog(null, "Try Again", "You Lose", JOptionPane.YES_NO_OPTION);
	
		if(option == JOptionPane.YES_OPTION){
			initializeBoard();
		}else{
			System.exit(0);
		}
	}

	/*public void minesNeighbors(int x, int y) {
		
		if (){
		getX();
		getY();
	}*/
	
	public void checkNeighbors(int x, int y){
		for (int i = x-1; i< x+1; i++) {
			for (int j = y-1; j< y+1; j++) {
				if(i< TOTAL_COLUMNS && i>-1 && j < TOTAL_ROWS && j > -1){
					
				}
			}
		}
	}
	
	public void paintComponent(Graphics g) {
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

		
		g.setColor(Color.BLACK);
		
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				
				if ((x == 0) || (y != TOTAL_ROWS)) {
					Color c = getColorArray(x,y);
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				}
			}
		}
	}
	
	public int getGridX(int x, int y) {
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
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
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
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	
		
	//Setters	
	public void setColorArray(int x, int y, Color WHITE) {
		this.colorArray[x][y] = WHITE;
	}
	public void setFlagCounter(int flagCounter) {
		this.flagCounter = flagCounter;
	}
	
	//Getters
	public Color getColorArray(int x, int y) {
		return colorArray[x][y];
	}
	public int getFlagCounter() {
		return flagCounter;
	}
}