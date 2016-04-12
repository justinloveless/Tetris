//package Tetris_Files;
/**

 * Create and control the game Tetris.
 * 
 *
 *
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JPanel {
	/**
	 * 
	 */

	private Game game;
	private EventController ec;
	private int level;
	/**
	 * Sets up the parts for the Tetris game, display and user control
	 * @throws Exception 
	 */
	public Tetris() throws Exception{
		level = 1;
		game = new Game(this);
		JFrame f = new JFrame("Tetris Game");
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 550);
		f.setVisible(true);
		ec = new EventController(game);
		f.addKeyListener(ec);
		setBackground(Color.DARK_GRAY);
		
	}
	
	public void setLevel(int restart){
		level = restart;
	}
	/**
	 * Updates the display
	 */
	public void update() {
		repaint();
	}

	/**
	 * Paint the current state of the game
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.draw(g);
		
		g.setFont(new Font("HonMincho", Font.BOLD, 30));
		g.setColor(Color.ORANGE);
		g.drawString("LV: " + level, 10,Grid.TOP + 22);
		
		if (game.isGameOver()) {
			g.setFont(new Font("HonMincho", Font.BOLD, 30));
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", 107, 200);
			ec.resetTimerEc();

		}
		else if(game.getGameIsPaused()){
			g.setFont(new Font("HonMincho", Font.BOLD, 30));
			g.setColor(Color.BLACK);
			g.drawString("PAUSED", 140, 200);
		}
	}

	public static void main(String[] args) throws Exception {
		
		new Tetris();
	}
	
	public void updateTimer() {
			level++;
			repaint();
			ec.updateTimerEc();
		
	}
}
