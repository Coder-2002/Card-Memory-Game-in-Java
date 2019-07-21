import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JPanel{
	public static final int MARGIN = 50;
	private CardManager manager;
	private JLabel label;
	private Font font;
	public Panel(String title, int x, int y, int width, int height){
		JFrame frame = new JFrame(title);
		frame.setLocation(x,y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(width,height));
		frame.getContentPane().add(this);
		frame.pack();
		label = new JLabel("Score" +  ": " + Coordinator.getScore());
		label.setBounds(750,20,80,30);
		this.add(label);
		frame.setVisible(true);
		
	}
	
	public void setCardManager(CardManager manager){
		this.manager = manager;
	}
	public void paintComponent(Graphics g){
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g.setColor(Color.white);
		g.fillRect(0,0, getWidth(), getHeight());
		
		if(Coordinator.isGameOver()){
			if(Coordinator.getScore()>0)label.setText("Game Over. Great Job! Your score is " + Coordinator.getScore() + ".");
			else if(Coordinator.getScore()== 0)label.setText("Game Over. Getting better. Your score is " + Coordinator.getScore() + ".");
			else label.setText("Game Over. You have a very bad memory! Your score is " + Coordinator.getScore() + ".");
		}
		else {label.setText("Score" +  ": " + Coordinator.getScore());
	
			
		}
		if(manager != null) manager.draw(g);
	}

}