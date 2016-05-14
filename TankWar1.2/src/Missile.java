import java.awt.Color;
import java.awt.Graphics;

public class Missile {
	public static final int XSPEED=10;   //�ӵ��ƶ����ٶ�
	public static final int YSPEED=10;
	public static final int WIDTH=10;	 //�ӵ��Ŀ���
	public static final int HEIGHT=10;
	private int x,y;
	Tank.Direction dir;           
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x; 
		this.y = y;
		this.dir = dir;
	}
	/**
	 * ���ӵ�
	 * @param g
	 */
	public void draw(Graphics g){
		Color c =g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}
	/**
	 * ̹�˵��ƶ�����
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
	}
}