import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Preferences implements Serializable{
	public int highScore;
	public int quit;
	public String txtQuit;
	public int pause;
	public String txtPause;
	public int mute;
	public String txtMute;
	public int newGame;
	public String txtNewGame;
	public int rotate;
	public String txtRotate;
	public int savePiece;
	public String txtSavePiece;
	public int moveLeft;
	public String txtMoveLeft;
	public int moveRight;
	public String txtMoveRight;
	public int dropSoft;
	public String txtDropSoft;
	public int dropHard;
	public String txtDropHard;
	
	//defaults
	Preferences(){
		highScore = 0;
		quit = KeyEvent.VK_Q;
		txtQuit = KeyEvent.getKeyText(quit) + " - Quit Game";
		pause=(KeyEvent.VK_P);
		txtPause = KeyEvent.getKeyText(pause) + " - Pause";
		mute=(KeyEvent.VK_M);
		txtMute = KeyEvent.getKeyText(mute) + " - Mute";		
		newGame=(KeyEvent.VK_ENTER);
		txtNewGame = KeyEvent.getKeyText(newGame) + " - New Game";	
		rotate=(KeyEvent.VK_SPACE);
		txtRotate = KeyEvent.getKeyText(rotate) + " - Rotate";	
		savePiece=(KeyEvent.VK_S);
		txtSavePiece = KeyEvent.getKeyText(savePiece) + " - Swap";
		moveLeft=(KeyEvent.VK_LEFT);
		txtMoveLeft = KeyEvent.getKeyText(moveLeft) + " - Move left";
		moveRight=(KeyEvent.VK_RIGHT);
		txtMoveRight= KeyEvent.getKeyText(moveRight) + " - Move right";
		dropSoft=(KeyEvent.VK_DOWN);
		txtDropSoft = KeyEvent.getKeyText(dropSoft) + " - Soft Drop";
		dropHard=(KeyEvent.VK_UP);
		txtDropHard = KeyEvent.getKeyText(dropHard) + "  - Hard Drop";
	}
	//read from file
	Preferences(ObjectInputStream ois){
		try {
			Preferences pref;
			if (ois.available() > 0){
				pref = (Preferences) ois.readObject();
			} else {
				pref = new Preferences();
			}
			highScore = pref.highScore;
			quit = pref.quit;
			txtQuit = pref.txtQuit;
			pause = pref.pause;
			txtPause = pref.txtPause;
			mute = pref.mute;
			txtMute = pref.txtMute;
			newGame = pref.newGame;
			txtNewGame = pref.txtNewGame;
			rotate = pref.rotate;
			txtRotate = pref.txtRotate;
			savePiece = pref.savePiece;
			txtSavePiece = pref.txtSavePiece;
			moveLeft = pref.moveLeft;
			txtMoveLeft = pref.txtMoveLeft;
			moveRight = pref.moveRight;
			txtMoveRight = pref.txtMoveRight;
			dropSoft = pref.dropSoft;
			txtDropSoft = pref.txtDropSoft;
			dropHard = pref.dropHard;
			txtDropHard = pref.txtDropHard;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//save to file
	public boolean savePrefs(){
		File save = new File("tetrisSave.txt");
		ObjectOutputStream oos;
//		System.out.println("Saving...");
		try {
			oos = new ObjectOutputStream(
					new FileOutputStream(save));
			oos.writeObject(this);
			oos.flush();
			oos.writeObject(this);
			oos.flush();
			oos.close();
//			System.out.println("Checking that file was written correctly");
			try {
				ObjectInputStream ois1 = new ObjectInputStream(
						new FileInputStream(save));
				Preferences temp = (Preferences) ois1.readObject();
				this.clone(temp);
//				System.out.println("File successfully created:\n" 
//						+ "highScore = " + this.highScore 
//						+ "\n" + this.toString());
			} catch (IOException | ClassNotFoundException e1) {
				System.out.println("File was not corretly written");
				System.exit(-1);
			}
			return true;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("File not found");
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("IOException found when saving");
			return false;
		}
	}
	
	public String toString(){
		return (txtQuit + "\n" + txtPause + "\n" + txtMute + "\n" + txtNewGame + "\n"
				+ txtRotate + "\n" + txtSavePiece + "\n" + txtMoveLeft + "\n"
				+ txtMoveRight + "\n" + txtDropSoft + "\n" + txtDropHard
		);
	}
	
	public void clone(Preferences p){
		highScore = p.highScore;
		quit = p.quit;
		txtQuit = p.txtQuit;
		pause = p.pause;
		txtPause = p.txtPause;
		mute = p.mute;
		txtMute = p.txtMute;
		newGame = p.newGame;
		txtNewGame = p.txtNewGame;
		rotate = p.rotate;
		txtRotate = p.txtRotate;
		savePiece = p.savePiece;
		txtSavePiece = p.txtSavePiece;
		moveLeft = p.moveLeft;
		txtMoveLeft = p.txtMoveLeft;
		moveRight = p.moveRight;
		txtMoveRight = p.txtMoveRight;
		dropSoft = p.dropSoft;
		txtDropSoft = p.txtDropSoft;
		dropHard = p.dropHard;
		txtDropHard = p.txtDropHard;
	}
	
	public void init(){
		//check if file exists
		File save = new File("tetrisSave.txt");
		//if so, try reading file
			//if input stream throws EOFException, file is empty:
			//so make default prefs and write them to file
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(save));
			ois.reset();
			Preferences temp = new Preferences();
			temp = (Preferences) ois.readObject();
//			System.out.println(temp.toString());
			this.clone(temp);
//			System.out.println("highScore = " + this.highScore 
//					+ "\n" + this.toString());
			ois.close();
		} catch (FileNotFoundException e) {
			//if file does not exist, create one
//			System.out.println("File did not exist. Creating one now...");
			try {
				save.createNewFile();
				//write default info to file
				ObjectOutputStream oos = new ObjectOutputStream (
						new FileOutputStream(save));
				oos.writeObject(new Preferences());
				oos.flush();
				oos.close();
			}catch (IOException ex) {
				e.printStackTrace();
				System.out.println("Unable to create file");
				return;
			}
//			System.out.println("Checking that file was written correctly");
				try {
					ObjectInputStream ois1 = new ObjectInputStream(
							new FileInputStream(save));
					Preferences temp = (Preferences) ois1.readObject();
					this.clone(temp);
//					System.out.println("File successfully created:\n" 
//							+ "highScore = " + this.highScore 
//							+ "\n" + this.toString());
				} catch (IOException | ClassNotFoundException e1) {
					System.out.println("File was not corretly written");
					System.exit(-1);
				}
			
			
		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println("outer IOException reached, writing new file");
			//this should catch EOFExceptions too, so try writing an object
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(save));
				Preferences temp = (Preferences) ois.readObject();
				this.clone(temp);
//				System.out.println("hiScore = " + temp.highScore + "\n" 
//						+ temp.toString());
				ois.close();
//				ObjectOutputStream oos = new ObjectOutputStream(
//						new FileOutputStream(save));
//				oos.writeObject(new Preferences());
//				oos.flush();
//				oos.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//this should never be called, because it's handled by an outer catch
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//I don't know what to do in this situation
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//I don't know what to do in this situation
			}
//			System.out.println("Checking that file was written correctly");
//			try {
//				ObjectInputStream ois1 = new ObjectInputStream(
//						new FileInputStream(save));
//				Preferences temp = (Preferences) ois1.readObject();
//				this.clone(temp);
//				System.out.println("File successfully created:\n" 
//						+ "highScore = " + this.highScore 
//						+ "\n" + this.toString());
//			} catch (IOException | ClassNotFoundException e1) {
//				System.out.println("File was not corretly written");
//				System.exit(-1);
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//not sure what to do about this situation
		} 
			
	}
	
}
