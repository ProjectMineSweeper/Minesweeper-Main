import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 55;
	private static final int GRID_Y = 55;
	private static final int INNER_CELL_SIZE = 40;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 10;   //Last row has only one cell
	public int counterOfBombs = 0;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public boolean minesPanel[][] = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];
	public int[][] sorroundingMines = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public int mines = 10;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
		
		// This generates the mines inside the grid
		
				for (int i = 0; i < mines; i++ ){
					
					for (int j = 0; j < 1; j++){
						
						Random rand = new Random();
						
						int x = rand.nextInt(TOTAL_COLUMNS);
						int y = rand.nextInt(TOTAL_ROWS);
						
						if(minesPanel[x][y] == false){
							minesPanel[x][y] = true;
						}
						
						else{
							
							i--;
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

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS - 1; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)));
		}


		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS - 1; y++) {
				if ((x == 0) || (y != TOTAL_ROWS - 1)) {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				}
			}
		}
		
		//Displays number of bombs in perimeter
		for(x=0; x < 9; x++){
			for(y=0; y < 9; y++){
				if(sorroundingMines[x][y] !=0) {
					g.setColor(Color.BLUE);
					g.drawString("" + sorroundingMines[x][y], (x*40)+75, (y*40)+85);
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
			return -1;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
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
			return -1;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
		//Determines whether the cell is a mine or not
		public boolean isMine (int x, int y){
			
			if (minesPanel[x][y] == true){
				
				return true;	
			}
			return false;
		}
		//Uncovers squares in perimeter
		public void uncoverPerimeter(int xCoordinate, int yCoordinate){
			this.colorArray[xCoordinate][yCoordinate] = Color.GRAY; 
			this.repaint();	
			for(x = -1; x < 2; x++){
				for( y = -1; y < 2; y++){
					if((xCoordinate + x > 0 && xCoordinate + x <= 8) && (yCoordinate + y > 0 && yCoordinate + y <= 8))
					{
						this.colorArray[xCoordinate + x][yCoordinate + y] = Color.GRAY; 
						this.repaint();	
						//Muestra y chequea que los cuadrados al rededor tengan bombas o no
						this.minesInPerimeter(xCoordinate + x, yCoordinate + y);

					}

				}
			}
			
		}
		
		//Checks how many bombs are in the perimeter of that square
		public int minesInPerimeter(int xCoordinate, int yCoordinate){
			//Resetting counter
			counterOfBombs = 0;
			//Pressed squares that have 8 bombs in the perimeter
			if((xCoordinate != 0 && xCoordinate!= 8) && (yCoordinate !=0 && yCoordinate !=8)){
				for(int x = -1; x < 2; x++){
					for(int y =-1; y < 2; y++){
						if(this.isMine(xCoordinate + x, yCoordinate + y)){
							counterOfBombs++;
						}
					}
				}
				sorroundingMines[xCoordinate][yCoordinate] = counterOfBombs;
			}
			//Pressed top left corner square
			if(xCoordinate == 0 && yCoordinate ==0){
				for(int x = 1; x >= 0; x--){
					for(y = 0; y < 2; y++){
						if(this.isMine(xCoordinate + x, yCoordinate + y)){
							counterOfBombs++;
						}
					}
				}
				sorroundingMines[xCoordinate][yCoordinate] = counterOfBombs;
			}
			//Pressed bottom left corner square
			if(xCoordinate == 0 && yCoordinate == 8){
				for(int x = 0; x < 2; x++){
					for(int y = 0; y >= -1; y-- ){
						if(this.isMine(xCoordinate + x, yCoordinate + y)){
							counterOfBombs++;
						}
					}
				}
				sorroundingMines[xCoordinate][yCoordinate] = counterOfBombs;
			}
			//Pressed top right corner square
			if(xCoordinate == 8 && yCoordinate == 0){
				for(int x= -1; x < 1; x++){
					for(int y =0; y < 2; y++){
						if(this.isMine(xCoordinate + x, yCoordinate + y)){
							counterOfBombs++;		
						}
					}
				}
				sorroundingMines[xCoordinate][yCoordinate] = counterOfBombs;
			}
			//Pressed bottom right corner square
			if(xCoordinate == 8 && yCoordinate == 8){
				for(int x = -1; x < 1; x++){
					for(int y = -1; y < 1; y++){
						if(this.isMine(xCoordinate + x, yCoordinate + y)){
							counterOfBombs++;		
						}
					}
				}
				sorroundingMines[xCoordinate][yCoordinate] = counterOfBombs;
			}
			//Pressed squares in left wall that are not the corners
			if(xCoordinate == 0 && (yCoordinate !=0 && yCoordinate != 8)){
				for(int x = 0; x < 2; x++){
					for(int y = -1; y < 2; y++){
						if(this.isMine(xCoordinate + x, yCoordinate + y)){
							counterOfBombs++;		
						}						
					}
				}
				sorroundingMines[xCoordinate][yCoordinate] = counterOfBombs;
			}
			//Pressed squares in bottom wall that are not the corners
			if(yCoordinate == 8 && (xCoordinate != 0 && xCoordinate !=8)){
				for(int x =-1; x < 2; x++){
					for(int y = -1; y < 1; y++){
						if(this.isMine(xCoordinate + x, yCoordinate + y)){
							counterOfBombs++;		
						}						
					}
				}
				sorroundingMines[xCoordinate][yCoordinate] = counterOfBombs;

			}
			//Pressed squares in right wall that are not the corners
			if(xCoordinate == 8 && (yCoordinate !=0 && yCoordinate != 8)){
				for(int x = -1; x < 1; x++){
					for(int y = -1; y < 2; y++){
						if(this.isMine(xCoordinate + x, yCoordinate + y)){
							counterOfBombs++;		
						}						
					}
				}
				sorroundingMines[xCoordinate][yCoordinate] = counterOfBombs;
			}
			//Pressed squares in top wal that are not the corners
			if(yCoordinate == 0 && (xCoordinate !=0 && xCoordinate!=8)){
				for(int x = -1; x < 2; x++){
					for(int y = 0; y < 2; y++){
						if(this.isMine(xCoordinate + x, yCoordinate + y)){
							counterOfBombs++;		
						}						
					}
				}
				sorroundingMines[xCoordinate][yCoordinate] = counterOfBombs;
			}
			return counterOfBombs;
		}

public boolean Winner (int x, int y){
			
			if (minesPanel[x][y] == false){
				
			}
				
				if (Winner(x, y)){
					
					final JOptionPane pane = new JOptionPane ("You Have Won The Game");
					final JDialog win = pane.createDialog("Congrats");
					win.setVisible(true);
				}
				return true;

	
			
			}
}
			
		
		
	
