import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	private int x,y;	
	
	private boolean  bL=false,bD=false,bU=false,bR=false;
	enum Direction{L,LU,LD,R,RU,RD,U,D,STOP};  //̹�˵ķ���ö��
	private Direction dir=Direction.STOP;   //̹�˷���
	private Direction ptDir=Direction.R;    //��Ͳ����
	public static final int XSPEED=5;       //̹��ÿ���ƶ����ٶ�
	public static final int YSPEED=5;       
	public static final int WIDTH=30;		//̹�˵Ŀ��
	public static final int HEIGHT=30;
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	TankClient tc=null;
	public Tank(int x, int y, TankClient tc) {
		this(x,y);
		this.tc = tc;
	}
/**
 * ��̹��
 * @param g
 */
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);  //��̹��
		g.setColor(c);
		move();
		//����Ͳ
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
 * �ƶ�̹��
 * �ı���Ͳ����	
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
	}
	/**
	 * �����¼����� 
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			tc.m=fire();        //��TankClient���е�m���Ը�ֵ��Ҳ���Խ�TAnkClient���е�m����Ϊstatic���ͣ�ֱ��ͨ����������m
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
/**
 * �����ӵ�
 * @return
 */
	private Missile fire() {
		// TODO Auto-generated method stub
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		Missile m=new Missile(x, y,ptDir);
		return m;
	}
	/**
	 * ȷ��̹�˵ķ���
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
