import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import resources.SoundPlayer;

public class CardManager implements MouseListener{
	private Card[][] cards;
	private int row, col;
	private int numOfImages;
	private boolean oneSelected,twoSelected;
	private Card firstSelected,secondSelected;
	private int numOfLiveCards;
	private long timeTwoCardsSelected;
	private SoundPlayer buzz,tick,chime;
	private String[] imageNames = {"apple.png","bank.png","basketball.png"
			, "bubble_blue.png", "bubble_green.png","bubble_red.png","building.png", "cat.png",
			"cheese.png","denture.png","dog.png","hockey_stick.png", "keys.png", "phone.png","pizza.png", "santa.png",
			"soccer_ball.png", "sock.png", "toilet_bowl.png", "toilet_paper.png",  "xmas_tree.png"};

	private Image[] images;
	//CardManager manager = new CardManager(38,7);	
	public CardManager(int numOfCards, int col){
		Image image;
		int i,j;
		this.col = col;
		if(numOfCards%2!=0)numOfCards++;
		if(numOfCards > imageNames.length*2)numOfCards = numOfImages;
		row = (numOfCards/col)+((numOfCards%col!=0)?1:0);
		numOfLiveCards = numOfCards;
		images = new Image[numOfCards];
		ArrayList<String> temp = new ArrayList<String>();
		for(String name : imageNames){
			temp.add(name);
		}
		String[] newImages = new String[temp.size()];
		for(i = 0; i < newImages.length; i++){
			newImages[i] = temp.remove((int)(Math.random()*temp.size()));
		}
		imageNames = newImages;
		ArrayList<Integer> a = new ArrayList<Integer>();
		for(int k = 0; k < numOfCards; k++){
			a.add(new Integer(k));
		}
		for(i = 0; i < numOfCards/2; i++){
//			image = new ImageIcon("jrJava/memoryCardGame2/" + imageNames[i]).getImage();
			image = new ImageIcon("resource/" + imageNames[i]).getImage();
			int randIndex1 = a.remove((int)(Math.random()*a.size()));
			int randIndex2 = a.remove((int)(Math.random()*a.size()));
			images[randIndex1] = image;
			images[randIndex2] = image;
		}
		cards = new Card[row][];
		for(i = 0; i < cards.length-1; i++){
			cards[i] = new Card[col];
		}
		cards[cards.length-1] = new Card[numOfCards-(row-1)*col];
		int x;
		int y;
		for(i = 0; i < cards.length; i++){
			for(j = 0; j < cards[i].length; j++){
				image = images[i*col + j];
				x = 50 + j*Card.getSize();
				y = 50 + i * Card.getSize();
				cards[i][j] = new Card(image,x,y);
			}
		}
	}

	public int getRowNumber(){return row;}
	public int getCol(){return col;}
	public void draw(Graphics g){
		int i,j;
		for(i = 0; i< cards.length; i++){
			for(j = 0; j < cards[i].length; j++){
				cards[i][j].draw(g);
			}
		}
	}

	public void showAll(){
		int i,j;
		for(i = 0; i< cards.length; i++){
			for(j = 0; j < cards[i].length; j++){
				cards[i][j].show();
			}
		}
	}
	
	public void showBoth(Card first, Card second){
		first.show();
		second.show();
	}

	public void hideAll(){
		int i,j;
		for(i = 0; i< cards.length; i++){
			for(j = 0; j < cards[i].length; j++){
				cards[i][j].hide();
			}
		}
	}


	public void mouseClicked(MouseEvent e) {}
	
	public void applyGameLogic(){
		if(!twoSelected || System.currentTimeMillis()<timeTwoCardsSelected + 1500)return;
		
		if(firstSelected.equals(secondSelected)){
			Coordinator.increment();
			numOfLiveCards-=2;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			firstSelected.setMatched();
			secondSelected.setMatched();
			chime.play();
			if(numOfLiveCards == 0)Coordinator.makeGameOver();
		}
		else{
			Coordinator.decrement();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			firstSelected.hide();
			secondSelected.hide();
			buzz.play();
		}
	
		firstSelected = null;
		secondSelected = null;
		oneSelected = false;
		twoSelected = false;
	}


	public void mousePressed(MouseEvent e) {
		if(twoSelected) return;
		int mx = e.getX(), my = e.getY(),i,j;
		String resourcePath = "resource/";
		buzz = new SoundPlayer(resourcePath + "buzz.wav");
		chime = new SoundPlayer(resourcePath + "chime.wav");
		tick = new SoundPlayer(resourcePath + "tick.wav");
		for(i = 0; i < cards.length; i++){
			for(j = 0; j < cards[i].length; j++){
				if(cards[i][j].isSelected(mx, my) && cards[i][j]!= firstSelected && !cards[i][j].isMatched()){
					cards[i][j].show();
					if(!oneSelected){
						tick.play();
						oneSelected = true;
						firstSelected = cards[i][j];
					}
					else{
						tick.play();
						oneSelected = false;
						twoSelected = true;
						secondSelected = cards[i][j];
						timeTwoCardsSelected = System.currentTimeMillis();
						//applyGameLogic();
						//new GameLogic(this).start();
					}
					return;
				}
			}
		}
	}


	public void mouseReleased(MouseEvent e) {}


	public void mouseEntered(MouseEvent e) {}


	public void mouseExited(MouseEvent e) {}
}