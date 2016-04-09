//package Tetris_Files;
/**

 * Handles events for the Tetris Game.  User events (key strokes) as well as periodic timer
 * events.
 * 
 * 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.Timer;

//
public class EventController extends KeyAdapter implements ActionListener {

	private Game game; // current game: grid and current piece
	private Timer timer;

	private double PIECE_MOVE_TIME = 0.8;  // wait 0.8 s every time
														// the piece moves down
														// increase to slow it
	private boolean stateOfTime = true;													// down
	
	public void setStateOfTime(boolean state){
		this.stateOfTime = state;
	}
	
	public boolean getStateOfTime(){
		return stateOfTime;
	}
	private boolean gameOver;
	
	private boolean music;
	
	private Clip clip;
	/**
	 * Creates an EventController to handle key and timer events.
	 * 
	 * @param game
	 * the game this is controlling
	 * @throws Exception 
	 */
	public EventController(Game game) throws Exception {
		this.game = game;
		gameOver = false;
		double delay = 1000 * PIECE_MOVE_TIME; // in milliseconds
		timer = new Timer((int) delay, this);
		timer.setCoalesce(true); // if multiple events pending, bunch them to
		// 1 event
		timer.start();
		
		this.music = true;
		
		
		try{//Play tetris theme in background
	    	File open = new File("Tetris.wav");;
	        AudioInputStream ais = 
	        		AudioSystem.getAudioInputStream(open);
	        final Clip clip = AudioSystem.getClip();
	        clip.open( ais );
	        this.clip = clip;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		startClip();
		
	}
	
	//Start Tetris theme
	private void startClip() {
		clip.start();
		clip.loop(-1);
	}
	//Stop Tetris theme
	private void stopClip() {
		clip.stop();
	}
	/**
	 * Responds to special keys being pressed.
	 * 
	 * Currently just responds to the space key and the q(uit) key
	 */
	public void keyPressed(KeyEvent e) {
		// if 'Q', quit the game
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			timer.stop();
			((JFrame) e.getSource()).dispose();
			System.exit(0);
		}
		
		if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
			game.removeAll();
			timer.start();
			gameOver = false;
			game.isNotOver();
		}
			
		if(e.getKeyCode() == KeyEvent.VK_M){
			if(this.music == true){
				stopClip();
				this.music = false;
			}
			else{
				startClip();
				this.music = true;
			}
		}
	
		if (!gameOver  && this.getStateOfTime()) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				handleMove(Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				handleMove(Direction.DOWN);
				break;
            case KeyEvent.VK_LEFT:
                handleMove(Direction.LEFT);
				break;
            case KeyEvent.VK_RIGHT:
                handleMove(Direction.RIGHT);
				break;
			case KeyEvent.VK_SPACE:
				game.rotatePiece();
				break;
			case KeyEvent.VK_S:
				game.savePiece();
				break;
			case KeyEvent.VK_P:
				timer.stop();
				setStateOfTime(false);
				game.gamePaused(true);
				break;
			}
		}
		else if (!gameOver  && !this.getStateOfTime()){
				switch (e.getKeyCode()) {
				case KeyEvent.VK_P:
					timer.start();
					setStateOfTime(true);
					game.gamePaused(false);
					break;
				}
		}
		
	}

	/** Updates the game periodically based on a timer event */
	public void actionPerformed(ActionEvent e) {
		handleMove(Direction.DOWN);
	}

	/**
	 * Update the game by moving in the given direction
	 */
	private void handleMove(Direction direction) {
		game.movePiece(direction);
		gameOver = game.isGameOver();
		if (gameOver)
			timer.stop();
	}
	
	public void resetTimerEc(){
		
		PIECE_MOVE_TIME = 0.8;
		double delay = 1000 * PIECE_MOVE_TIME; // in milliseconds
		timer = new Timer((int) delay, this);
		timer.setCoalesce(true); 
		System.out.println("PIECE_MOVE_TIME == " + PIECE_MOVE_TIME);
	}

	public void updateTimerEc() {
		
		timer.stop();
		
		if(PIECE_MOVE_TIME > 0.3){
			PIECE_MOVE_TIME -= 0.2;
		}
		else if(PIECE_MOVE_TIME > 0.15 ){
			PIECE_MOVE_TIME -= 0.05;
		}
		else if(PIECE_MOVE_TIME > 0.01 && PIECE_MOVE_TIME < 0.15){
			PIECE_MOVE_TIME -= 0.02;
		}
		else{
			System.out.println("You Win, Congrats");
		}
		
		System.out.println("PIECE_MOVE_TIME == " + PIECE_MOVE_TIME);
		double delay = 1000 * PIECE_MOVE_TIME; // in milliseconds
		timer = new Timer((int) delay, this);
		timer.setCoalesce(true); // if multiple events pending, bunch them to
		timer.start();
		
	}
	

}
