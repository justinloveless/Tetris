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
import java.awt.Robot;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
	private int score=0, level=0, highScore;
	private JTextField txtTetris;
	private JTextField txtScore;
	private JTextField txtHiScore;
	private JTextField txtLevel;
	private JTextArea txtInstruction;
	private JPanel storedPanel;
	private JButton muteBtn;
	private JButton settingsBtn;
	private ObjectOutputStream saveOutputFile;
	private ObjectInputStream saveInputFile;
	private Preferences prefs;
	private PreferencesMenu prefsMenu;
	/**
	 * Sets up the parts for the Tetris game, display and user control
	 * @throws Exception 
	 */
	public Tetris() throws Exception{
		prefs = new Preferences();
		prefs.init();
		prefsMenu = new PreferencesMenu(this, prefs);
//		readPrefs();
		storageGrid = new Grid(4,3);
		storedPiece = null;
		game = new Game(this);
		ec = new EventController(game);
		//do not start timer yet
		ec.getTimer().stop();
		
		JFrame f = new JFrame("Tetris Game");
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500, 550);
		f.setVisible(true);
		f.addKeyListener(ec);
//		f.setVisible(false);
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
		
		/*set up Title text pane using rainbow colors*/
		StyledDocument doc = new DefaultStyledDocument();
		JTextPane TETRIS = new JTextPane(doc);
		TETRIS.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 23));
		TETRIS.setAlignmentY(CENTER_ALIGNMENT);
		TETRIS.setAlignmentX(CENTER_ALIGNMENT);
		TETRIS.setText("TETRIS");
		TETRIS.setBounds(10, 11, 112, 37);
		TETRIS.setEditable(false);
		TETRIS.setBackground(Color.GRAY);
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, Color.RED);
		doc.setCharacterAttributes(0, 1, set, true);
		StyleConstants.setForeground(set, Color.ORANGE);
		doc.setCharacterAttributes(1, 1, set, true);
		StyleConstants.setForeground(set, Color.YELLOW);
		doc.setCharacterAttributes(2, 1, set, true);
		StyleConstants.setForeground(set, Color.GREEN);
		doc.setCharacterAttributes(3, 1, set, true);
		StyleConstants.setForeground(set, Color.BLUE);
		doc.setCharacterAttributes(4, 1, set, true);
		StyleConstants.setForeground(set, Color.CYAN);
		doc.setCharacterAttributes(5, 1, set, true);
		rightPanel.add(TETRIS);
		
		
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
		
		/*create instruction text area*/
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
		
		/*create border for use in store and next piece text fields*/
		Border border = BorderFactory.createLineBorder(Color.GRAY,0);
		/*create stored piece text field*/
		JTextField txtStored= new JTextField();
		txtStored.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtStored.setHorizontalAlignment(SwingConstants.LEFT);
		txtStored.setBackground(Color.DARK_GRAY);
		txtStored.setForeground(Color.LIGHT_GRAY);
		txtStored.setBorder(border);
		txtStored.setText("Stored:");
		txtStored.setBounds(20, 60, 60, 15);
		txtStored.setEditable(false);
		txtStored.setColumns(10);
		add(txtStored);
		
		/*create next piece text field*/
		JTextField txtNext= new JTextField();
		txtNext.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtNext.setHorizontalAlignment(SwingConstants.LEFT);
		txtNext.setBackground(Color.DARK_GRAY);
		txtNext.setForeground(Color.LIGHT_GRAY);
		txtNext.setBorder(border);
		txtNext.setText("Next:");
		txtNext.setBounds(20, 220, 60, 15);
		txtNext.setEditable(false);
		txtNext.setColumns(10);
		add(txtNext);
		
		/*create mute button*/
		muteBtn = new JButton("Mute");
		muteBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		muteBtn.setBackground(Color.LIGHT_GRAY);
		muteBtn.setForeground(Color.BLACK);
		muteBtn.setBounds(10, 340, 80, 20);
		muteBtn.setEnabled(true);
		muteBtn.setText("Mute");
		muteBtn.addActionListener(new ActionListener(){
			Robot robot = new Robot();
			public void actionPerformed(ActionEvent e){
				if (ec.isMusicPlaying()){
					robot.keyPress(prefs.mute);
				} else {
					robot.keyPress(prefs.mute);
				}
				
			}
		});
		muteBtn.setFocusable(false);
		add(muteBtn);
		
		/*create settings button*/
		settingsBtn = new JButton("Settings");
		settingsBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		settingsBtn.setBackground(Color.LIGHT_GRAY);
		settingsBtn.setForeground(Color.BLACK);
		settingsBtn.setBounds(10, 380, 80, 20);
		settingsBtn.setEnabled(true);
		settingsBtn.addActionListener(new ActionListener(){
			Robot robot = new Robot();
			public void actionPerformed(ActionEvent e){
				if (ec.getStateOfTime()){
					robot.keyPress(prefs.pause);
				} 
				prefsMenu.openPrefsMenu();
			}
		});
		settingsBtn.setFocusable(false);
		add(settingsBtn);
		

//		f.setVisible(true);
		//start timer 
		ec.getTimer().start();
		f.requestFocusInWindow();
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
			if (score > prefs.highScore){
				prefs.highScore = score;
				prefs.savePrefs();
			}
		}
		else if(game.isPaused()){
			g.setFont(new Font("HonMincho", Font.BOLD, 30));
			g.setColor(Color.BLACK);
			g.drawString("PAUSED", 140, 200);
			System.out.println("HighScore = " + prefs.highScore + " score = " + score);
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
	
	public Preferences getPrefs(){
		return prefs;
	}
	
	public PreferencesMenu getPrefsMenu(){
		return prefsMenu;
	}
	
	public JButton getMuteBtn(){
		return muteBtn;
	}
	
	public void draw(){
		if (txtLevel != null && txtScore != null){
			txtLevel.setText(level + "");
			txtScore.setText(score + "");
			if (score > prefs.highScore){
				prefs.highScore = score;
			}
			txtHiScore.setText(prefs.highScore + "");
			if (!txtInstruction.equals(prefs.toString())){
				txtInstruction.setText(prefs.toString());
			}
		}
	}
	public void updateStorage(){
		Graphics g = this.getGraphics();
		
	}
}
