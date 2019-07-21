import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Card {
	public static final Image BACK_IMAGE;
	private Image hiddenImage;
	private static int padding, inset;
	private static Color borderColor, paddingBorderColor;
	private int x,y; // top,left
	private static int size;
	private boolean matched;
	private boolean shouldReveal;
	
	static {
		BACK_IMAGE = new ImageIcon("resource/"+ "backside.png").getImage();
		size = 100;
		inset = 10;
		padding = 5;
		borderColor = Color.gray;
		paddingBorderColor = Color.black;
	}
	
	public static int getSize(){return size;}
	public Image getImage(){
		return hiddenImage;
	}
	
	public Card(Image image, int x, int y){
		hiddenImage = image;
		this.x = x;
		this.y = y;
	}
	
	public void show(){shouldReveal = true;}
	public void hide(){shouldReveal = false;}
	public void setMatched(){matched = true;}
	public void setUnMatched(){matched = false;}
	public boolean isMatched(){return matched;}
	public boolean isSelected(int mx, int my){
		return ((mx >= x && mx <= x + size)&&(my >= y && my <= y + size));
	}
	public boolean equals(Object other){
		Card o = (Card)other;
		return this.hiddenImage == o.hiddenImage;
	}
	

	public void draw(Graphics g){
		if(matched)return;
			if(shouldReveal){
				g.drawImage(hiddenImage,x+inset,y+inset,size-2*inset,size-2*inset,null);
			}
			else{
				g.drawImage(BACK_IMAGE,x+inset,y+inset,size-2*inset,size-2*inset,null);
			}
			g.setColor(borderColor);
			g.drawRect(x, y, size,size);
			g.setColor(paddingBorderColor);
			g.drawRect(x+padding, y+padding, size-2*padding, size-2*padding);
		}
}