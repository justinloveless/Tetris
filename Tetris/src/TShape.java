//package Tetris_Files;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * An L-Shape piece in the Tetris Game.
 * 
 * This piece is made up of 4 squares in the following configuration:
 * 
 * Sq Sq Sq <br>
 *    Sq <br>
 * 
 * The game piece "floats above" the Grid. The (row, col) coordinates are the
 * location of the middle Square on the side within the Grid
 * 
 * 
 */
public class TShape extends Shape{
	
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
	 * Creates an T-Shape piece. See class description for actual location of r
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
	public TShape(int r, int c, Grid g) {
		super(r,c,g);
		grid = g;
		square = new Square[PIECE_COUNT];
		ableToMove = true;
		// Create the squares
			square[0] = new Square(g, r, c - 1, Color.gray, true);
			square[1] = new Square(g, r, c, Color.gray, true);
			square[2] = new Square(g, r, c + 1, Color.gray, true);
			square[3] = new Square(g, r + 1, c, Color.gray, true);
		
	}

	/**
	 * Draws the piece on the given Graphics context
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < PIECE_COUNT; i++) {
			square[i].draw(g);
		}
	}
	//draw method used for putting in storage
	public void drawAside(Graphics g, int left, int top){
		for (int i = 0; i < PIECE_COUNT; i++) {
			square[i].draw(g, left, top);
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

		/*
		 * S0 (S1) S2
		 *     S3
		 */
		if( square[1].getRow() == square[0].getRow()
				&& square[1].getCol() - 1 == square[0].getCol()){
				
				square[0].setRow(-1);
				square[0].setCol(1);
				square[2].setRow(1);
				square[2].setCol(-1);
				square[3].setRow(-1);
				square[3].setCol(-1);
		}
		/*     S2
		 * S3 (S1)
		 *     S2
		 */
		else if( (square[1].getRow() - 1 == square[0].getRow() )
				&& (square[1].getCol() == square[0].getCol())){
			if (square[1].getCol() > (grid.WIDTH - 2)){
				
			}else {
				square[0].setRow(1);
				square[0].setCol(1);
				square[2].setRow(-1);
				square[2].setCol(-1);
				square[3].setRow(-1);
				square[3].setCol(1);
			}
		}
		/*     S3
		 * S2 (S1) S0
		 * 
		 */
		else if( (square[1].getRow()  == square[0].getRow() )
				&& (square[1].getCol() + 1 == square[0].getCol())){
			
			square[0].setRow(1);
			square[0].setCol(-1);
			square[2].setRow(-1);
			square[2].setCol(1);
			square[3].setRow(1);
			square[3].setCol(1);
		}
		/*  S2
		 * (S1) S3
		 *  S0
		 */
		else if( (square[1].getRow() + 1 == square[0].getRow() )
				&& (square[1].getCol() == square[0].getCol())){
			if (square[1].getCol() < 1){
				
			}else {
				square[0].setRow(-1);
				square[0].setCol(-1);
				square[2].setRow(1);
				square[2].setCol(1);
				square[3].setRow(1);
				square[3].setCol(-1);
			}
		}
		
		
	}
	

	public String toString(){
		return "T-Shape";
	}

	public int getPieceNum(){
		return 5;
	}
}
