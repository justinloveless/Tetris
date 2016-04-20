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

	private Tetris display; // the visual for the Tetris game

	private Shape piece; // the current piece that is dropping
	
	private int score;
	
	private int level;

	private int linesCleared;
	
	private boolean isOver; // has the game finished?
	
	private boolean gameIsPaused;
	
	private int savedPieceNum;
	
	private int previousPieceNum;
	
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
		this.display = display;
		this.display.setLevel(1);
		
		score = 0;
		level = 1;
		linesCleared = 0;
		gameIsPaused = false;
		savedState = false;
		alreadySwitched = false;
		
		getNewPiece();
		isOver = false;
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
		grid.draw(g);
		if (piece != null) {
			piece.draw(g);
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
		display.update();
	}
	
	public void isNotOver(){
		isOver = false;
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
//		int lines = grid.getLinesCleared();
//		int curScore = this.display.getScore();
//		int curLevel = this.display.getLevel();
		int newScore = 0;
		switch (lines){
		case 0:
//			this.display.setScore(curScore);
			newScore = curScore + 1;
			return;
		case 1:
//			this.display.setScore(40*curLevel + curScore);
			newScore = 40*(curLevel + 1) + curScore;
			break;
		case 2:
//			this.display.setScore(100*curLevel + curScore);
			newScore = 100*(curLevel + 1) + curScore;
			break;
		case 3:
//			this.display.setScore(300*curLevel + curScore);
			newScore = 300*(curLevel + 1) + curScore;
			break;
		case 4:
//			this.display.setScore(1200*curLevel + curScore);
			newScore = 1200*(curLevel + 1) + curScore;
			break;
		default:
			System.out.println("Invalid number of lines cleared: " + lines);
			return;
		}
		this.display.setScore(newScore);
		System.out.println("Score = " + newScore);
		
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
    		if(!savedState){
    			piece = null;
    			previousPieceNum = savedPieceNum;
    			getNewPiece();
    			savedState = true;
    		}
    		else if (!alreadySwitched || newPieceState){
    			
    			piece = null;
    			getSavedPiece();
    			int temp = previousPieceNum;
    			previousPieceNum = savedPieceNum;
    			savedPieceNum = temp;
    		}
 
    	}
        
        public void getSavedPiece(){
        	
        	alreadySwitched = true;
        	newPieceState = false;
        	
        	switch (previousPieceNum){
        	case 0: // L Shape
        		piece = new LShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 1: // O Shape
        		piece = new OShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 2: // S Shape
        		piece = new SShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 3: // Z Shape
        		piece = new ZShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 4: // J Shape
        		piece = new JShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 5: // T Shape
        		piece = new TShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 6: // I Shape
        		piece = new IShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	}
        }
        
        /** get new piece **/
        public void getNewPiece(){
        	
        	newPieceState = true;
        	
        	Random rand = new Random();
        	savedPieceNum = rand.nextInt(7);
        	switch (savedPieceNum){
        	case 0: // L Shape
        		piece = new LShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 1: // O Shape
        		piece = new OShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 2: // S Shape
        		piece = new SShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 3: // Z Shape
        		piece = new ZShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 4: // J Shape
        		piece = new JShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 5: // T Shape
        		piece = new TShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	case 6: // I Shape
        		piece = new IShape(1, Grid.WIDTH / 2 - 1, grid);
        		break;
        	}
        	
        }


}
