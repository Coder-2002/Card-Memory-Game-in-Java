public class GameLogic extends Thread {
	CardManager cardMgr;
	public GameLogic(CardManager c) {
		cardMgr = c;
	}
	public void run() {
		cardMgr.applyGameLogic();
	}   

}