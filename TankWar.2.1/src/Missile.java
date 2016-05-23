import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missile {
	public static final int XSPEED=10;   //子弹移动的速度
	public static final int YSPEED=10;
	public static final int WIDTH=10;	 //子弹的宽度
	public static final int HEIGHT=10;
	
	private TankClient tc;
	
	private int x,y;
	private boolean live=true;
	private boolean good;
	public boolean isLive() {
		return live;
	}
	Tank.Direction dir;           
	public Missile(int x, int y,boolean good, Tank.Direction dir,TankClient tc) {
		this.x = x; 
		this.y = y;
		this.good=good;
		this.dir = dir;
		this.tc = tc;
	}
	/**
	 * 画子弹
	 * @param g
	 */
	public void draw(Graphics g){
		if(!this.isLive()){
			tc.missiles.remove(this);
			return;
		}
		Color c =g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}
	/**
	 * 子弹的移动
	 */
	private void move() {
		// TODO Auto-generated method stub
		 switch(dir){
		 case L:
			 x-=XSPEED;
			 break;
		 case LU:
			 x-=XSPEED;
			 y-=YSPEED;
			 break;
		 case LD:
			 x-=XSPEED;
			 y+=YSPEED;
			 break;
		 case R:
			 x+=XSPEED;
			 break;
		 case RU:
			 x+=XSPEED;
			 y-=YSPEED;
			 break;
		 case RD:
			 x+=XSPEED;
			 y+=YSPEED;
			 break;
		 case U:
			 y-=YSPEED;
			 break;
		 case D:
			 y+=YSPEED;
			 break;

		 }
		 if (x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGTH){
			 this.live=false;
		 }
	}
	
	public  Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	  
	  /**
	   * 是否打中坦克
	   * @param t
	   * @return
	   */
	public boolean hitTank(Tank t){
		if(this.live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()){
			t.setLive(false);
			this.live =false;
			Explode e=new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	public boolean hitWall(Wall w){
		if(this.getRect().intersects(w.getRect())){
			this.live=false;
			return true;
		}
		return false;
	}
}
