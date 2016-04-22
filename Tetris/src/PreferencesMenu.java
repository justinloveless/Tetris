import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//import Preferences.keyListener;

public class PreferencesMenu {

	private JFrame frame;
	private Preferences preferences;
	private HashMap<JButton, Integer> prefMap;
	Tetris tetris;
	
	PreferencesMenu(Tetris t, Preferences p){
		this.preferences = p;
		this.tetris = t;
		
	}
	
	public void openPrefsMenu(){
		frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(null);
		frame.add(panel);
		
		/*	LABELS	*/
		ArrayList<JLabel> labelList = new ArrayList<JLabel>();
		JLabel lblQuit = new JLabel("Quit:");
		labelList.add(lblQuit);
		JLabel lblMute = new JLabel("Mute:");
		labelList.add(lblMute);
		JLabel lblPause = new JLabel("Pause:");
		labelList.add(lblPause);
		JLabel lblNewGame = new JLabel("New Game:");
		labelList.add(lblNewGame);
		JLabel lblSoftDrop = new JLabel("Soft Drop:");
		labelList.add(lblSoftDrop);
		JLabel lblHardDrop = new JLabel("Hard Drop:");
		labelList.add(lblHardDrop);
		JLabel lblMoveLeft = new JLabel("Move Left:");
		labelList.add(lblMoveLeft);
		JLabel lblMoveRight = new JLabel("Move Right:");
		labelList.add(lblMoveRight);
		JLabel lblRotate = new JLabel("Rotate:");
		labelList.add(lblRotate);
		JLabel lblSwap = new JLabel("Swap:");
		labelList.add(lblSwap);
		int top = 20;
		for (JLabel label:labelList){
			label.setBounds(10, top, 90, 14);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setFont(new Font("Tahoma", Font.BOLD, 13));
			panel.add(label);
			top += 20;
		}
		
		/*	BUTTONS	*/
		prefMap = new HashMap<JButton, Integer>();
		ArrayList<JButton> buttonList = new ArrayList<JButton>();
		JButton btnQuit = new JButton(KeyEvent.getKeyText(preferences.quit));
		prefMap.put(btnQuit, preferences.quit);
		buttonList.add(btnQuit);
		JButton btnMute = new JButton(KeyEvent.getKeyText(preferences.mute));
		prefMap.put(btnMute, preferences.mute);
		buttonList.add(btnMute);
		JButton btnPause = new JButton(KeyEvent.getKeyText(preferences.pause));
		prefMap.put(btnPause, preferences.pause);
		buttonList.add(btnPause);
		JButton btnNewGame = new JButton(KeyEvent.getKeyText(preferences.newGame));
		prefMap.put(btnNewGame, preferences.newGame);
		buttonList.add(btnNewGame);
		JButton btnSoftDrop = new JButton(KeyEvent.getKeyText(preferences.dropSoft));
		prefMap.put(btnSoftDrop, preferences.dropSoft);
		buttonList.add(btnSoftDrop);
		JButton btnHardDrop = new JButton(KeyEvent.getKeyText(preferences.dropHard));
		prefMap.put(btnHardDrop, preferences.dropHard);
		buttonList.add(btnHardDrop);
		JButton btnMoveLeft = new JButton(KeyEvent.getKeyText(preferences.moveLeft));
		prefMap.put(btnMoveLeft, preferences.moveLeft);
		buttonList.add(btnMoveLeft);
		JButton btnMoveRight = new JButton(KeyEvent.getKeyText(preferences.moveRight));
		prefMap.put(btnMoveRight, preferences.moveRight);
		buttonList.add(btnMoveRight);
		JButton btnRotate = new JButton(KeyEvent.getKeyText(preferences.rotate));
		prefMap.put(btnRotate, preferences.rotate);
		buttonList.add(btnRotate);
		JButton btnSwap = new JButton(KeyEvent.getKeyText(preferences.savePiece));
		prefMap.put(btnSwap, preferences.savePiece);
		buttonList.add(btnSwap);

		//button ActionListener
		ActionListener chgPrefsListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final JButton src = (JButton) e.getSource();
				System.out.println("source = " + src.getActionCommand());
				
				
				frame.addKeyListener(new KeyListener(){

					@Override
					public void keyTyped(KeyEvent e) {
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						Integer current = prefMap.get(src);
						Integer newVal = e.getKeyCode();
						if (!exists(newVal))
						{
							setPrefs(current, newVal);
							src.setText(KeyEvent.getKeyText(newVal));							
						} else {
							swapPrefs(current, newVal);
							src.setText(KeyEvent.getKeyText(newVal));
							//TODO "otherBtn".setText(KeyEvent.getKeyText(current));
						}
						preferences.updateText();
						System.out.println(KeyEvent.getKeyText(newVal) + " key pressed");
						frame.removeKeyListener(this);
					}

					@Override
					public void keyReleased(KeyEvent e) {
						
					}
					
				});
				
			}
		};
		
		top = 20;
		for (JButton button:buttonList){
			button.setFont(new Font("Tahoma", Font.PLAIN, 12));
			button.setBackground(Color.LIGHT_GRAY);
			button.setForeground(Color.BLACK);
			button.setBounds(105, top-2, 70, 20);
			button.setEnabled(true);
			button.setFocusable(false);
			button.addActionListener(chgPrefsListener);
			panel.add(button);
			top+=20;
		}
		
		//OK button
		JButton btnOK = new JButton("OK");
		btnOK.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnOK.setBackground(Color.LIGHT_GRAY);
		btnOK.setForeground(Color.BLACK);
		btnOK.setBounds(200, top+10, 70, 20);
		btnOK.setEnabled(true);
		btnOK.setFocusable(false);
		btnOK.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				preferences.savePrefs();
				System.out.println("Save complete");
				frame.setVisible(false);
				frame.dispose();
				tetris.draw();
			}
		});
		panel.add(btnOK);
		
	}
	
	public boolean exists (int newVal){
		if (	   newVal != preferences.mute
				&& newVal != preferences.quit
				&& newVal != preferences.pause
				&& newVal != preferences.newGame
				&& newVal != preferences.dropSoft
				&& newVal != preferences.dropHard
				&& newVal != preferences.moveLeft
				&& newVal != preferences.moveRight
				&& newVal != preferences.rotate
				&& newVal != preferences.savePiece)
			return false;
		return true;
	}
	
	public void setPrefs(int current, int newVal){
		if (current == preferences.mute){
			preferences.mute = newVal;
		} else if (current == preferences.quit){
			preferences.quit = newVal;							
		} else if (current == preferences.pause){
			preferences.pause = newVal;	
		} else if (current == preferences.newGame){
			preferences.newGame = newVal;		
		} else if (current == preferences.dropSoft){
			preferences.dropSoft = newVal;		
		} else if (current == preferences.dropHard){
			preferences.dropHard = newVal;		
		} else if (current == preferences.moveLeft){
			preferences.moveLeft = newVal;		
		} else if (current == preferences.moveRight){
			preferences.moveRight = newVal;		
		} else if (current == preferences.rotate){
			preferences.rotate = newVal;		
		} else if (current == preferences.savePiece){
			preferences.savePiece = newVal;	
		}
	}
	
	public void swapPrefs(int current, int newVal){
		int temp = current;
		setPrefs(current, newVal);
		setPrefs(newVal, temp);
	}
	
}
