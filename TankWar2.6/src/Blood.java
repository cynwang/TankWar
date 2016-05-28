import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Blood {
	private int x,y,w,h;
	private boolean live=true;
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isLive() {
		return live;
	}
	int step=0;
	private int[][] pos={{300,400},{320,380},{340,400},{360,420},{340,440},{320,460},{300,440},{280,420}};
	public Blood(){
		x=pos[0][0];
		y=pos[0][1];
		w=h=15;
	}
	public void draw(Graphics g){
		if(!live) return ;
		Color c=g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		move();
	}
	private void move() {
		// TODO Auto-generated method stub
		step++;
		if(step==pos.length){
			step=0;
		}else{
			x=pos[step][0];
			y=pos[step][1];
			
		}
	}
	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}
}
