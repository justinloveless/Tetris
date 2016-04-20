import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Preferences implements Serializable{
	private int highScore;
	private int quit;
	private int pause;
	private int mute;
	private int newGame;
	private int rotate;
	private int savePiece;
	private int moveLeft;
	private int moveRight;
	private int dropSoft;
	private int dropHard;
	
	//defaults
	Preferences(){
		highScore = 0;
		quit = KeyEvent.VK_Q;
		pause=(KeyEvent.VK_P);
		mute=(KeyEvent.VK_M);
		newGame=(KeyEvent.VK_ENTER);
		rotate=(KeyEvent.VK_SPACE);
		savePiece=(KeyEvent.VK_S);
		moveLeft=(KeyEvent.VK_LEFT);
		moveRight=(KeyEvent.VK_RIGHT);
		dropSoft=(KeyEvent.VK_DOWN);
		dropHard=(KeyEvent.VK_UP);
	}
	//read from file
	Preferences(ObjectInputStream ois){
		try {
			Preferences pref = (Preferences) ois.readObject();
			highScore = pref.getHS();
			quit = pref.getQuitKE();
			pause = pref.getpauseKE();
			mute = pref.getMuteKE();
			newGame = pref.getNewGameKE();
			rotate = pref.getRotateKE();
			savePiece = pref.getSavePieceKE();
			moveLeft = pref.getMoveLeftKE();
			moveRight = pref.getMoveRightKE();
			dropSoft = pref.getDropSoftKE();
			dropHard = pref.getDropHardKE();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//save to file
	public boolean savePrefs(ObjectOutputStream oos){
		try {
			oos.writeObject(this);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int getHS(){
		return highScore;
	}
	public void setHS(int score){
		highScore = score;
	}
	public int getpauseKE(){
		return pause;
	}
	public void setPauseKE(int e){
		pause = e;
	}
	public int getQuitKE(){
		return quit;
	}
	public void setQuitKE(int e){
		quit = e;
	}
	public int getMuteKE(){
		return mute;
	}
	public void setMuteKE(int e){
		mute = e;
	}
	public int getNewGameKE(){
		return newGame;
	}
	public void setNewGameKE(int e){
		newGame = e;
	}
	public int getRotateKE(){
		return rotate;
	}
	public void setRotateKE(int e){
		rotate = e;
	}
	public int getSavePieceKE(){
		return savePiece;
	}
	public void setSavePieceKE(int e){
		savePiece = e;
	}
	public int getMoveLeftKE(){
		return moveLeft;
	}
	public void setMoveLeftKE(int e){
		moveLeft = e;
	}
	public int getMoveRightKE(){
		return moveRight;
	}
	public void setMoveRightKE(int e){
		moveRight = e;
	}
	public int getDropSoftKE(){
		return dropSoft;
	}
	public void setDropSoftKE(int e){
		dropSoft = e;
	}
	public int getDropHardKE(){
		return dropHard;
	}
	public void setDropHardKE(int e){
		dropHard = e;
	}
}
