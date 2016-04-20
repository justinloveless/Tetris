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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class Tetris extends JPanel {
	/**
	 * 
	 */

	private Game game;
	private EventController ec;
	private JTextField txtTetris;
	private int score=0, level=0;
	private JTextField textField;
	private JTextField textField_1;
	/**
	 * Sets up the parts for the Tetris game, display and user control
	 * @throws Exception 
	 */
	public Tetris() throws Exception{
		game = new Game(this);
		JFrame f = new JFrame("Tetris Game");
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(550, 550);
		f.setVisible(true);
		ec = new EventController(game);
		f.addKeyListener(ec);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(367, 11, 132, 441);
		add(panel);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel_2.setBounds(10, 79, 112, 84);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SCORE:");
		lblNewLabel.setBounds(10, 11, 92, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_2.add(lblNewLabel);
		
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(SystemColor.activeCaptionBorder);
		textField.setFont(new Font("Tahoma", Font.BOLD, 16));
		textField.setText(score+"");
		textField.setBounds(20, 44, 70, 20);
		panel_2.add(textField);
		textField.setColumns(10);
		
		txtTetris = new JTextField();
		txtTetris.setBackground(Color.GRAY);
		txtTetris.setHorizontalAlignment(SwingConstants.CENTER);
		txtTetris.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 18));
		txtTetris.setText(" TETRIS");
		txtTetris.setBounds(10, 11, 112, 37);
		txtTetris.setEditable(false);
		panel.add(txtTetris);
		txtTetris.setColumns(10);
		
		JLabel lblLevel = new JLabel("LEVEL:");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLevel.setBounds(46, 174, 46, 14);
		panel.add(lblLevel);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		textField_1.setBackground(Color.LIGHT_GRAY);
		textField_1.setText(level+"");
		textField_1.setBounds(29, 200, 74, 20);
		textField_1.setEditable(false);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
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
			ec.updateTimerEc();
		
	}
	public void setLevel(int lvl){
		level = lvl;
	}
	public void setScore (int sc){
		score = sc;
	}
	public int getLevel(){
		return level;
	}
	public int getScore(){
		return score;
	}
	public void draw(){
		if (textField_1 != null && textField != null){
			textField_1.setText(level + "");
			textField.setText(score + "");
		}
	}
}
