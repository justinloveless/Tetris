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
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class Tetris extends JPanel {
	/**
	 * 
	 */

	private Game game;
	private Grid storageGrid;
	private Shape storedPiece;
	private EventController ec;
	private JTextField txtTetris;
	private int score=0, level=0, highScore;
	private JTextField txtScore;
	private JTextField txtHiScore;
	private JTextField txtLevel;
	private JTextArea txtInstruction;
	private JPanel storedPanel;
	private ObjectOutputStream saveOutputFile;
	private ObjectInputStream saveInputFile;
	private Preferences prefs;
	/**
	 * Sets up the parts for the Tetris game, display and user control
	 * @throws Exception 
	 */
	public Tetris() throws Exception{
		prefs = new Preferences();
		prefs.init();
//		readPrefs();
		storageGrid = new Grid(4,3);
		storedPiece = null;
		game = new Game(this);
		ec = new EventController(game);
		JFrame f = new JFrame("Tetris Game");
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(550, 550);
		f.setVisible(true);
		f.addKeyListener(ec);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		
		/*create right panel*/
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.GRAY);
		rightPanel.setBounds(310, 50, 132, 405);
		add(rightPanel);
		rightPanel.setLayout(null);
		
		/*create score panel*/
		JPanel scorePanel = new JPanel();
		scorePanel.setBackground(Color.LIGHT_GRAY);
		scorePanel.setBounds(10, 60, 112, 110);
		rightPanel.add(scorePanel);
		scorePanel.setLayout(null);
		
		/*create score label*/
		JLabel lblScore = new JLabel("SCORE:");
		lblScore.setBounds(10, 10, 92, 14);
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 13));
		scorePanel.add(lblScore);
		
		/*set up score text field*/
		txtScore = new JTextField();
		txtScore.setEditable(false);
		txtScore.setBackground(SystemColor.activeCaptionBorder);
		txtScore.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtScore.setHorizontalAlignment(SwingConstants.CENTER);
		txtScore.setText(score+"");
		txtScore.setBounds(20, 30, 70, 20);
		scorePanel.add(txtScore);
		txtScore.setColumns(10);
		
		/*create hi-score label*/
		JLabel lblHiScore = new JLabel("HI-SCORE:");
		lblHiScore.setBounds(10, 60, 92, 14);
		lblHiScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblHiScore.setFont(new Font("Tahoma", Font.BOLD, 13));
		scorePanel.add(lblHiScore);
		
		/*set up hi-score text field*/
		txtHiScore = new JTextField();
		txtHiScore.setEditable(false);
		txtHiScore.setBackground(SystemColor.activeCaptionBorder);
		txtHiScore.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtHiScore.setHorizontalAlignment(SwingConstants.CENTER);
		txtHiScore.setText(highScore+"");
		txtHiScore.setBounds(20, 80, 70, 20);
		scorePanel.add(txtHiScore);
		txtHiScore.setColumns(10);
		
		/*set up Title text field*/
		txtTetris = new JTextField();
		txtTetris.setBackground(Color.GRAY);
		txtTetris.setHorizontalAlignment(SwingConstants.CENTER);
		txtTetris.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 18));
		txtTetris.setText(" TETRIS");
		txtTetris.setBounds(10, 11, 112, 37);
		txtTetris.setEditable(false);
		rightPanel.add(txtTetris);
		txtTetris.setColumns(10);
		
		/*create level label*/
		JLabel lblLevel = new JLabel("LEVEL:");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLevel.setBounds(46, 174, 46, 14);
		rightPanel.add(lblLevel);
		
		/*set up level text field*/
		txtLevel = new JTextField();
		txtLevel.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtLevel.setHorizontalAlignment(SwingConstants.CENTER);
		txtLevel.setBackground(Color.LIGHT_GRAY);
		txtLevel.setText(level+"");
		txtLevel.setBounds(29, 200, 74, 20);
		txtLevel.setEditable(false);
		txtLevel.setColumns(10);
		rightPanel.add(txtLevel);
		
		/*create instruction panel*/
		JPanel instructionPanel = new JPanel();
		instructionPanel.setBackground(Color.LIGHT_GRAY);
		instructionPanel.setBounds(10, 235, 112, 165);
		instructionPanel.setLayout(null);
		rightPanel.add(instructionPanel);
		
		/*create instruction text field*/
		txtInstruction = new JTextArea();
		txtInstruction.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtInstruction.setBackground(Color.LIGHT_GRAY);
		txtInstruction.setText(prefs.toString()+"");
		txtInstruction.setBounds(5, 5, 108, 161);
		txtInstruction.setEditable(false);
		txtInstruction.setColumns(10);
		txtInstruction.setLineWrap(true);
		txtInstruction.setWrapStyleWord(true);
		instructionPanel.add(txtInstruction);
		
		/*create left panel*/
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.GRAY);
		leftPanel.setBounds(10,50,80,405);
		leftPanel.setLayout(null);
		add(leftPanel);
		
		/*create stored piece label*/
		JLabel lblStored = new JLabel("Stored:");
		lblStored.setHorizontalAlignment(SwingConstants.CENTER);
		lblStored.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStored.setBounds(10,5,60,15);
		leftPanel.add(lblStored);

		/*create stored piece panel*/
		storedPanel = new JPanel();
		storedPanel.setBackground(Color.LIGHT_GRAY);
		storedPanel.setBounds(10, 20, 60, 120); 	
		storedPanel.setLayout(null);
		leftPanel.add(storedPanel);
		

		
		
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
			System.out.println("Score=" + score + " hiScore=" + highScore);
			if (score > highScore){
				prefs.highScore = score;
			}
			prefs.savePrefs();
		}
		else if(game.getGameIsPaused()){
			g.setFont(new Font("HonMincho", Font.BOLD, 30));
			g.setColor(Color.BLACK);
			g.drawString("PAUSED", 140, 200);
			System.out.println("HighScore = " + highScore + " score = " + score);
			if (score > prefs.highScore){
				prefs.highScore = score;
				System.out.println("HS(before save) = " + prefs.highScore);
				prefs.savePrefs();
			}
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
	public JPanel getStoredPanel(){
		return storedPanel;
	}
	public void setStoredPiece(Shape p){
		storedPiece = p;
	}
//	private void readPrefs(){
//		File save = new File("tetrisSave.txt");
//		try {
//			ObjectInputStream ois = new ObjectInputStream(
//					new FileInputStream(save));
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	private void savePrefs(){
//		ObjectOutputStream oos;
//		try {
//			File f = new File("tetrisSave.txt");
//			if (!f.exists()){
//				System.out.println("File didn't exist!");
//				f.createNewFile();
//				oos = new ObjectOutputStream(
//						new FileOutputStream(f, false));
////				prefs = new Preferences();
//				oos.writeObject(prefs);
//				oos.flush();
//				oos.close();
//				readPrefs();
//				return;
//			} else if (prefs != null){
//				System.out.println("about to save");
//				oos = new ObjectOutputStream(
//						new FileOutputStream(f, false));
////				prefs.savePrefs(oos);
//				oos.writeObject(prefs);
//				oos.flush();
//				oos.close();
//				System.out.println("HiScore->" + prefs.highScore);
//				readPrefs();
//				return;
//			} else {
//				System.out.println("prefs was null");
//				oos = new ObjectOutputStream(
//						new FileOutputStream(f, false));
//				prefs = new Preferences();
//				oos.writeObject(prefs);
//				oos.flush();
//				oos.close();
//				readPrefs();
//				return;
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("Didn't save");
//	}
	
	public Preferences getPrefs(){
		return prefs;
	}
	
	public void draw(){
		if (txtLevel != null && txtScore != null){
			txtLevel.setText(level + "");
			txtScore.setText(score + "");
			if (score > prefs.highScore){
				prefs.highScore = score;
			}
			txtHiScore.setText(prefs.highScore + "");
		}
	}
	public void updateStorage(){
		Graphics g = this.getGraphics();
		
	}
}
