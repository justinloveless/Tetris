//package Tetris_Files;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * An L-Shape piece in the Tetris Game.
 * 
 * This piece is made up of 4 squares in the following configuration:
 * 
 * Sq <br>
 * Sq <br>
 * Sq Sq <br>
 * 
 * The game piece "floats above" the Grid. The (row, col) coordinates are the
 * location of the middle Square on the side within the Grid
 * 
 * 
 */
public class LShape {
	
	private int shapeType;
	
	public int getShapeType(){
		return shapeType;
	}
	
	public void setShapeType(int type){
		this.shapeType = type;
	}
	
	private boolean ableToMove; // can this piece move

	private Square[] square; // the squares that make up this piece

	// Made up of PIECE_COUNT squares
	private Grid grid; // the board this piece is on

	// number of squares in one Tetris game piece
	private static final int PIECE_COUNT = 4;

	/**
	 * Creates an L-Shape piece. See class description for actual location of r
	 * and c
	 * 
	 * @param r
	 *            row location for this piece
	 * @param c
	 *            column location for this piece
	 * @param g
	 *            the grid for this game piece
	 * 
	 */
	public LShape(int r, int c, Grid g, int n) {
		grid = g;
		square = new Square[PIECE_COUNT];
		ableToMove = true;
		setShapeType(n);
		// Create the squares
		if(n == 0){// L shape
			square[0] = new Square(g, r - 1, c, Color.orange, true);
			square[1] = new Square(g, r, c, Color.orange, true);
			square[2] = new Square(g, r + 1, c, Color.orange, true);
			square[3] = new Square(g, r + 1, c + 1, Color.orange, true);
		}
		else if(n == 1){// O shape
			square[0] = new Square(g, r, c - 1, Color.yellow, true);
			square[1] = new Square(g, r, c, Color.yellow, true);
			square[2] = new Square(g, r + 1, c, Color.yellow, true);
			square[3] = new Square(g, r + 1, c - 1, Color.yellow, true);
		}
		else if(n == 2){// S shape
			square[0] = new Square(g, r , c + 1, Color.red, true);
			square[1] = new Square(g, r, c, Color.red, true);
			square[2] = new Square(g, r + 1, c, Color.red, true);
			square[3] = new Square(g, r + 1, c - 1, Color.red, true);
		}
		else if(n == 3){// Z shape
			square[0] = new Square(g, r , c - 1, Color.green, true);
			square[1] = new Square(g, r, c, Color.green, true);
			square[2] = new Square(g, r + 1, c, Color.green, true);
			square[3] = new Square(g, r + 1, c + 1, Color.green, true);
		}
		else if(n == 4){// J shape
			square[0] = new Square(g, r - 1, c, Color.pink, true);
			square[1] = new Square(g, r, c, Color.pink, true);
			square[2] = new Square(g, r + 1, c, Color.pink, true);
			square[3] = new Square(g, r + 1, c - 1, Color.pink, true);
		}
		else if(n == 5){// T shape 
			square[0] = new Square(g, r, c - 1, Color.gray, true);
			square[1] = new Square(g, r, c, Color.gray, true);
			square[2] = new Square(g, r, c + 1, Color.gray, true);
			square[3] = new Square(g, r + 1, c, Color.gray, true);
		}
		else if(n == 6){// I Shape
			square[0] = new Square(g, r - 1, c, Color.magenta, true);
			square[1] = new Square(g, r, c, Color.magenta, true);
			square[2] = new Square(g, r + 1, c, Color.magenta, true);
			square[3] = new Square(g, r + 2, c, Color.magenta, true);
		}
	}

	/**
	 * Draws the piece on the given Graphics context
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < PIECE_COUNT; i++) {
			square[i].draw(g);
		}
	}

	/**
	 * Moves the piece if possible Freeze the piece if it cannot move down
	 * anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			for (int i = 0; i < PIECE_COUNT; i++)
				square[i].move(direction);
		}
		// if we couldn't move, see if because we're at the bottom
		else if (direction == Direction.DOWN) {
			ableToMove = false;
		}
	}

	/**
	 * Returns the (row,col) grid coordinates occupied by this Piece
	 * 
	 * @return an Array of (row,col) Points
	 */
	public Point[] getLocations() {
		Point[] points = new Point[PIECE_COUNT];
		for (int i = 0; i < PIECE_COUNT; i++) {
			points[i] = new Point(square[i].getRow(), square[i].getCol());
		}
		return points;
	}

	/**
	 * Return the color of this piece
	 */
	public Color getColor() {
		// all squares of this piece have the same color
		return square[0].getColor();
	}

	/**
	 * Returns if this piece can move in the given direction
	 * 
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove)
			return false;

		// Each square must be able to move in that direction
		boolean answer = true;
		for (int i = 0; i < PIECE_COUNT; i++) {
			answer = answer && square[i].canMove(direction);
		}

		return answer;
	}

	/**
	* Rotate the Piece
	*/
	public void rotate()
	{
		if(this.getShapeType() == 0){
			if( square[1].getRow() + 1 == square[3].getRow()
				&& square[1].getCol() + 1 == square[3].getCol()){
				
				square[0].setRow(1);
				square[0].setCol(-1);
				square[2].setCol(-1);
				square[3].setRow(-1);
			}
			else if( (square[1].getRow() + 1 == square[2].getRow() )
					&& (square[1].getCol() - 1 == square[2].getCol())){
				
				square[0].setRow(-1);
				square[2].setCol(1);
				square[3].setRow(-1);
				square[3].setCol(-1);
			}
			else if( (square[1].getRow() - 1 == square[0].getRow() )
					&& (square[1].getCol() - 1 == square[0].getCol())){
				
				square[0].setRow(1);
				square[2].setRow(-1);
				square[2].setCol(1);
				square[3].setCol(1);
			}
			else if( (square[1].getRow() - 1 == square[3].getRow() )
					&& (square[1].getCol() + 1 == square[3].getCol())){
				
				square[0].setRow(-1);
				square[0].setCol(1);
				square[2].setRow(1);
				square[2].setCol(-1);
				square[3].setRow(2);
			}
		}
		
		else if(this.getShapeType() == 1){	
		}
		
		else if(this.getShapeType() == 2){
			if( square[1].getRow() + 1 == square[2].getRow()
					&& square[1].getCol() == square[2].getCol()){
					
					square[0].setCol(-2);
					square[2].setRow(-2);
					square[2].setCol(-1);
					square[3].setCol(1);
				}
				else if( (square[1].getRow() - 1 == square[2].getRow() )
						&& (square[1].getCol() - 1 == square[2].getCol())){
					
					square[0].setRow(-1);
					square[0].setCol(1);
					square[2].setRow(1);
					square[3].setRow(-2);
					square[3].setCol(1);
				}
				else if( (square[1].getRow()  == square[2].getRow() )
						&& (square[1].getCol() - 1 == square[2].getCol())){
					
					square[0].setRow(1);
					square[0].setCol(1);
					square[2].setRow(-1);
					square[2].setCol(1);
					square[3].setRow(2);
				}
				else if( (square[1].getRow() - 1 == square[2].getRow() )
						&& (square[1].getCol() == square[2].getCol())){
					
					square[2].setRow(2);
					square[3].setCol(-2);
				}
		}
		else if(this.getShapeType() == 3){
/*			if( square[1].getRow() + 1 == square[2].getRow()
					&& square[1].getCol() == square[2].getCol()){
					
					square[0].setCol(-2);
					square[2].setRow(-2);
					square[2].setCol(-1);
					square[3].setCol(1);
				}
				else if( (square[1].getRow() - 1 == square[2].getRow() )
						&& (square[1].getCol() - 1 == square[2].getCol())){
					
					square[0].setRow(-1);
					square[0].setCol(1);
					square[2].setRow(1);
					square[3].setRow(-2);
					square[3].setCol(1);
				}
				else if( (square[1].getRow()  == square[2].getRow() )
						&& (square[1].getCol() - 1 == square[2].getCol())){
					
					square[0].setRow(1);
					square[0].setCol(1);
					square[2].setRow(-1);
					square[2].setCol(1);
					square[3].setRow(2);
				}
				else if( (square[1].getRow() - 1 == square[2].getRow() )
						&& (square[1].getCol() == square[2].getCol())){
					
					square[2].setRow(2);
					square[3].setCol(-2);
				}
*/		}
		else if(this.getShapeType() == 4){
			
		}
		else if(this.getShapeType() == 5){
			
		}
		else if(this.getShapeType() == 6){
			
		}
		
	}
}
