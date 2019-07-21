import java.util.Scanner;

import resources.Timer;

public class Coordinator {
	private static boolean gameOver;
	private static int score;
	private static Panel panel;
	public static int getScore(){return score;}
	public static boolean isGameOver(){return gameOver;}
	public static void increment(){
		score += 100;
		panel.repaint();
	}
	public static void decrement(){
		score -= 50;
		if(score <= -1000){
			makeGameOver();
			return;
		}
	    panel.repaint();
	}
	public static void makeGameOver(){
		gameOver = true;
		panel.repaint();
	}
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Number of cards?");
		int numOfCards = scanner.nextInt();
		System.out.println("Number of columns?");
		int numOfCols = scanner.nextInt();
		CardManager manager = new CardManager(numOfCards,numOfCols);
		int numOfRows = manager.getRowNumber();		
		panel = new Panel("Memory Card Game",200,50,numOfCols*Card.getSize() + 2*Panel.MARGIN,numOfRows*Card.getSize()+2*Panel.MARGIN);
		panel.setCardManager(manager);
		panel.addMouseListener(manager);
		panel.repaint();
		while(!gameOver){
			manager.applyGameLogic();
			panel.repaint();
			Timer timer = new Timer();
			timer.pause(100);
			/*try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}
}