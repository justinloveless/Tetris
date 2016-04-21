//package Tetris_Files;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
/**
 * Manages the game Tetris. Keeps track of the current piece and the grid.
 * Updates the display whenever the state of the game has changed.
 * 
 * 
 */
public class Game {

	private Grid grid; // the grid that makes up the Tetris board
	
	private Grid storageGrid; // the grid that displays the saved piece
	
	private Grid nextPieceGrid; // the grid that displays the next piece

	private Tetris display; // the visual for the Tetris game

	private Shape piece; // the current piece that is dropping
	
	private Shape storedPiece; // the piece that is stored
	
	private Shape nextPiece; // the next piece
	
	private int score;
	
	private int level;

	private int linesCleared;
	
	private boolean isOver; // has the game finished?
	
	private boolean gameIsPaused;
	
	private int savedPieceNum;
	
	private int previousPieceNum;
	
	private int nextPieceNum;
	
	private boolean savedState;
	
	private boolean alreadySwitched;
	
	private boolean newPieceState = false;
	/**
	 * Creates a Tetris game
	 * 
	 * @param Tetris
	 *            the display
	 */
	public Game(Tetris display) {
		grid = new Grid();
		storageGrid = new Grid(4,3);
		nextPieceGrid = new Grid(4,3);
		this.display = display;
		this.display.setLevel(1);
		
		score = 0;
		level = 1;
		linesCleared = 0;
		gameIsPaused = false;
		savedState = false;
		alreadySwitched = false;
		Random rand = new Random();
		nextPieceNum = rand.nextInt(7);
    	nextPiece = getPiece(nextPieceNum, 1, 1, nextPieceGrid);
		getNewPiece();
		isOver = false;
	}
	
	public boolean save(){
		return this.display.getPrefs().savePrefs();
	}
	
	public void gamePaused(boolean state){
		if(state){
			gameIsPaused = true;
			display.update();
		}
		else{
			gameIsPaused = false;
		}
	}
	
	public boolean getGameIsPaused(){
		return gameIsPaused;
	}
	
	public int getLevel(){
		return this.display.getLevel();
	}
	
	public int getScore(){
		return this.display.getScore();
	}
	/**
	 * Draws the current state of the game
	 * 
	 * @param g
	 *            the Graphics context on which to draw
	 */
	public void draw(Graphics g) {
		int left = 20, top = 80, topOffset = 160;
		grid.draw(g);
		if (piece != null) {
			piece.draw(g);
		}
		storageGrid.draw(g, left, top, 4,3);
		if (storedPiece != null){
			storedPiece.drawAside(g, left, top); // TODO does this need to use the modified draw function?
		}
		nextPieceGrid.draw(g,  left, top + topOffset, 4, 3);
		if (nextPiece != null){
			nextPiece.drawAside(g, left, top + topOffset);
		}
		this.display.draw();
	}

	/**
	 * Moves the piece in the given direction
	 * 
	 * @param the
	 *            direction to move
	 */
	

	public void movePiece(Direction direction) {
		if (direction == Direction.UP){
			while(piece != null){
				piece.move(Direction.DOWN);
				updatePiece();
		        grid.checkRows();
		        display.update();
		        //add 2 points per dropped cell
		        this.display.setScore(this.display.getScore() + 2);
			}
		}
		else if (piece != null) {
			piece.move(direction);
		}
		updatePiece();
        grid.checkRows();
        display.update();
		
                
	}
	
	public void removeAll(){
		grid.removeAll();
//		storageGrid.removeAll();
		storedPiece = null;
		nextPiece = null;
		display.update();
	}
	
	public void isNotOver(){
		isOver = false;
		this.display.setLevel(1);
		this.display.setScore(0);
	}

	/**
	 * Returns true if the game is over
	 */
	public boolean isGameOver() {
		// game is over if the piece occupies the same space as some non-empty
		// part of the grid. Usually happens when a new piece is made
		if (piece == null) {
			return false;
		}
		
		// check if game is already over
		if (isOver) {
			return true;
		}

		// check every part of the piece
		Point[] p = piece.getLocations();
		for (int i = 0; i < p.length; i++) {
			if (grid.isSet((int) p[i].getX(), (int) p[i].getY())) {
				isOver = true;
				return true;
			}
		}
		return false;
	}

	/** Updates the piece */
	private void updatePiece() {
		if (piece == null) {
//			Random rand = new Random();
//			piece = new LShape(1, Grid.WIDTH / 2 - 1, grid, rand.nextInt(7));
			getNewPiece();
			//increase score
			int lines = grid.getLinesCleared();
			int curScore = this.display.getScore();
			int curLevel = this.display.getLevel();
			if (lines != linesCleared){
				//if multiple lines were cleared at once, score them all
				while (lines > linesCleared){ 
					linesCleared ++;
					incScore(linesCleared, curScore, curLevel);
				}
				linesCleared = lines;
			}
			int check = grid.checkRowsCleared();
			if(check == 1){
				display.updateTimer();
				//increase level
				incLevel();
			}
			this.display.update();
		}

		// set Grid positions corresponding to frozen piece
		// and then release the piece
		else if (!piece.canMove(Direction.DOWN)) {
			Point[] p = piece.getLocations();
			Color c = piece.getColor();
			for (int i = 0; i < p.length; i++) {
				grid.set((int) p[i].getX(), (int) p[i].getY(), c);
			}
			piece = null;
		}

	}
	/*increase score using original Nintendo Tetris Scoring System*/
	public void incScore(int lines, int curScore, int curLevel){
		int newScore = 0;
		switch (lines){
		case 0:
			newScore = curScore + 1;
			return;
		case 1:
			newScore = 40*(curLevel + 1) + curScore;
			break;
		case 2:
			newScore = 100*(curLevel + 1) + curScore;
			break;
		case 3:
			newScore = 300*(curLevel + 1) + curScore;
			break;
		case 4:
			newScore = 1200*(curLevel + 1) + curScore;
			break;
		default:
			System.out.println("Invalid number of lines cleared: " + lines);
			return;
		}
		this.display.setScore(newScore);
		
	}
	
	/*increase level*/
	public void incLevel(){
		this.display.setLevel(this.display.getLevel()+1);
	}

			/** Rotate the piece*/
        public void rotatePiece()
        {
            if (piece != null) {
                piece.rotate();
            }
            updatePiece();
            grid.checkRows();
            display.update();
        }
        
        
    	public void savePiece(){
    		if(!savedState){ // first time saving piece
    			previousPieceNum = piece.getPieceNum();
    			piece = null;
    			storedPiece = getPiece(previousPieceNum, 1, 1, storageGrid);
    			getNewPiece();
    			savedState = true;
            	alreadySwitched = true;
            	newPieceState = false;
    		}
    		else if (!alreadySwitched || newPieceState){//only allow one switch per new piece
    			previousPieceNum = piece.getPieceNum();
    			savedPieceNum = storedPiece.getPieceNum();
    			storedPiece = getPiece(previousPieceNum, 1, 1, storageGrid);
    			piece = getPiece(savedPieceNum, 1, Grid.WIDTH/2 -1, grid);
            	alreadySwitched = true;
            	newPieceState = false;
    		}
 
    	}
        
        
        /** get new piece **/
        public void getNewPiece(){
        	newPieceState = true;
        	Random rand = new Random();
        	//swap nextPiece into current piece
        	piece = getPiece(nextPiece.getPieceNum(), 1, Grid.WIDTH/2 - 1, grid);
        	//get a new nextPiece
        	nextPiece = getPiece(rand.nextInt(7), 1, 1, nextPieceGrid);
        }
        
        public Shape getPiece(int pieceNum, int row, int col, Grid g){
        	Shape p = null;
        	switch (pieceNum){
        	case 0: // L Shape
        		p = new LShape(row, col, g);
        		break;
        	case 1: // O Shape
        		p = new OShape(row, col, g);
        		break;
        	case 2: // S Shape
        		p = new SShape(row, col, g);
        		break;
        	case 3: // Z Shape
        		p = new ZShape(row, col, g);
        		break;
        	case 4: // J Shape
        		p = new JShape(row, col, g);
        		break;
        	case 5: // T Shape
        		p = new TShape(row, col, g);
        		break;
        	case 6: // I Shape
        		p = new IShape(row, col, g);
        		break;
        	}
        	return p;
        }
        
        public Tetris getDisp(){
        	return this.display;
        }

}
