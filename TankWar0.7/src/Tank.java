import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	int x,y;

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
	}
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			if(x>=5){
			x -= 5;
			}
			break;
		case KeyEvent.VK_RIGHT:
			x += 5;
			break;
		case KeyEvent.VK_UP:
			y -= 5;
			break;
		case KeyEvent.VK_DOWN:
			y += 5;
			break;
		}
	}
	
}
