import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	private int x,y;	
	TankClient tc=null;
	private boolean  bL=false,bD=false,bU=false,bR=false;
	enum Direction{L,LU,LD,R,RU,RD,U,D,STOP};
	private Direction dir=Direction.STOP;
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x, int y, TankClient tc) {
		this(x,y);
		this.tc = tc;
	}

	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		move();


		
	}
	 void move(){
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
		 case STOP:
			 break;
		 }
	}
	 
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			TankClient.m=fire();
			break;
		case KeyEvent.VK_LEFT:
			bL=true;
			break;
		case KeyEvent.VK_RIGHT:
			bR=true;
			break;
		case KeyEvent.VK_UP:
			bU=true;
			break;
		case KeyEvent.VK_DOWN:
			bD=true;
			break;
		}
		locateDirection();
	}

	private Missile fire() {
		// TODO Auto-generated method stub
		Missile m=new Missile(x, y, dir);
		return m;
	}
	void locateDirection(){
		if(bL&&!bR&&!bU&&!bD) dir=Direction.L;
		else if(bL&&!bR&&bU&&!bD) dir=Direction.LU;
		else if(bL&&!bR&&!bU&&bD) dir=Direction.LD;
		else if(!bL&&bR&&!bU&&!bD) dir=Direction.R;
		else if(!bL&&bR&&bU&&!bD) dir=Direction.RU;
		else if(!bL&&bR&&!bU&&bD) dir=Direction.RD;
		else if(!bL&&!bR&&bU&&!bD) dir=Direction.U;
		else if(!bL&&!bR&&!bU&&bD) dir=Direction.D;
		else if(!bL&&!bR&&!bU&&!bD) dir=Direction.STOP;
	}
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL=false;
			break;
		case KeyEvent.VK_RIGHT:
			bR=false;
			break;
		case KeyEvent.VK_UP:
			bU=false;
			break;
		case KeyEvent.VK_DOWN:
			bD=false;
			break;
		}
		locateDirection();
	}
}
