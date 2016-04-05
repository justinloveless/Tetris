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
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game game;

	/**
	 * Sets up the parts for the Tetris game, display and user control
	 */
	public Tetris() {
		game = new Game(this);
		JFrame f = new JFrame("Tetris Game");
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.setSize(400, 550);
		f.setVisible(true);
		EventController ec = new EventController(game);
		f.addKeyListener(ec);
		setBackground(Color.DARK_GRAY);
		
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
		if (game.isGameOver()) {
			g.setFont(new Font("Palatino", Font.BOLD, 40));
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", 80, 300);
		}
	}

	public static void main(String[] args) throws Exception {
		
    	File open = new File("Tetris.wav");;
        AudioInputStream ais = AudioSystem.getAudioInputStream(open);
        final Clip clip = AudioSystem.getClip();
        clip.open( ais );
        clip.start();
        clip.loop(-1);

		new Tetris();
		
	}
}
