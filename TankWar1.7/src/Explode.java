import java.awt.Color;
import java.awt.Graphics;


public class Explode {
	private int  x,y;
	private boolean live=true;
	TankClient tc;
	int[] diameter={4,8,12,20,40,32,25,12,3};
	int step=0;
	
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g){
		if(!live)return;
		Color c=g.getColor();
		g.setColor(Color.YELLOW);
		if(step==diameter.length){
			live=false;
			step=0;
			return;
		}
		g.drawOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step++;
	}
	
 
}
