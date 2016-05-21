import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;


public class Tank {
	private int x,y;	
	private boolean good;             //区分是自己的坦克还是敌人的坦克
	private boolean live=true;             //表示敌人的坦克是否被打中
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isLive() {
		return live;
	}

	private boolean  bL=false,bD=false,bU=false,bR=false;
	enum Direction{L,LU,LD,R,RU,RD,U,D,STOP};  //坦克的方向枚举
	private Direction dir=Direction.STOP;   //坦克方向
	private Direction ptDir=Direction.R;    //炮筒方向
	public static final int XSPEED=5;       //坦克每次移动的速度
	public static final int YSPEED=5;       
	public static final int WIDTH=30;		//坦克的宽度
	public static final int HEIGHT=30;
	public static Random r =new Random();
	
	public Tank(int x, int y,boolean good) {
		this.x = x;
		this.y = y;
		this.good=good;
	}
	
	TankClient tc=null;        
	public Tank(int x, int y,boolean good, Direction dir,TankClient tc) {
		this(x,y,good);
		this.dir=dir;
		this.tc = tc;
	}
/**
 * 画坦克
 * @param g
 */
	public void draw(Graphics g){
		if(!this.isLive()){
			tc.tanks.remove(this);
			return;
		}
		Color c = g.getColor();
		if(good) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT);  //画坦克
		g.setColor(c);
		
		//
		move();
		
		//画炮筒
		 switch(ptDir){
		 case L:
			 g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT/2);
			 break;
		 case LU:
			 g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
			 break;
		 case LD:
			 g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT);
			 break;
		 case R:
			 g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT/2);
			 break;
		 case RU:
			 g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y);
			 break;
		 case RD:
			 g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT);
			 break;
		 case U:
			 g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y);
			 break;
		 case D:
			 g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT);
			 break;
		 }
	}
/**
 * 移动坦克
 * 改变炮筒方向	
 */
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
		 if(dir!=Direction.STOP){
			 this.ptDir=this.dir;
		 }
		 if(x<0) x=0;
		 if(y<20)y=20;
		 if(x+Tank.WIDTH>TankClient.GAME_WIDTH) x=TankClient.WIDTH-Tank.WIDTH;
		 if(y+Tank.HEIGHT>TankClient.GAME_HEIGTH) y=TankClient.GAME_HEIGTH-Tank.HEIGHT;
		 if(!good){
			 Direction[] dirs=Direction.values();
			 int rn=r.nextInt(dirs.length);
			 dir=dirs[rn];
		 }
	}
	/**
	 * 按下事件监听 
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
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
		//确定坦克的方向
		locateDirection();
	}
/**
 * 放射子弹
 * @return
 */
	private Missile fire() {
		// TODO Auto-generated method stub
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		Missile m=new Missile(x, y,ptDir,tc);
		tc.missiles.add(m);
		return m;
	}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			fire();       
			break;
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
	/**
	 * 确定坦克的方向
	 */
	
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
	
	public  Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
}
