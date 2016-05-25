import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {
	private int x, y;
	private int xOld, yOld; // ��¼̹���ƶ�֮ǰ��λ��
	private boolean good; // �������Լ���̹�˻��ǵ��˵�̹��
	private int life=100;;
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isGood() {
		return good;
	}

	private boolean live = true; // ��ʾ���˵�̹���Ƿ񱻴���

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	private boolean bL = false, bD = false, bU = false, bR = false;

	enum Direction {
		L, LU, LD, R, RU, RD, U, D, STOP
	}; // ̹�˵ķ���ö��

	private Direction dir = Direction.STOP; // ̹�˷���
	private Direction ptDir = Direction.R; // ��Ͳ����
	public static final int XSPEED = 5; // ̹��ÿ���ƶ����ٶ�
	public static final int YSPEED = 5;
	public static final int WIDTH = 30; // ̹�˵Ŀ��
	public static final int HEIGHT = 30;
	public static Random r = new Random();
	private int step = r.nextInt(12) + 3;

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.xOld = x;
		this.yOld = y;
		this.good = good;
	}

	TankClient tc = null;

	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}

	/**
	 * ��̹��
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		if (!this.isLive()) {
			tc.tanks.remove(this);
			return;
		}
		Color c = g.getColor();
		if (good)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT); // ��̹��
		g.setColor(c);

		//
		move();

		// ����Ͳ
		switch (ptDir) {
		case L:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y
					+ Tank.HEIGHT / 2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y
					+ Tank.HEIGHT);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH,
					y + Tank.HEIGHT / 2);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH,
					y);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH,
					y + Tank.HEIGHT);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH
					/ 2, y);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH
					/ 2, y + Tank.HEIGHT);
			break;
		}
	}

	/**
	 * �ƶ�̹�� �ı���Ͳ����
	 */
	void move() {
		this.xOld = x;
		this.yOld = y;
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		if (dir != Direction.STOP) {
			this.ptDir = this.dir;
		}
		if (x < 0)
			x = 0;
		if (y < 20)
			y = 20;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH)
			x = TankClient.WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGTH)
			y = TankClient.GAME_HEIGTH - Tank.HEIGHT;
		if (!good) {
			Direction[] dirs = Direction.values();
			int rn = r.nextInt(dirs.length);
			if (step == 0) {
				step = r.nextInt(12) + 3;
				dir = dirs[rn];
			}
			step--;
			if (r.nextInt(40) > 38)
				this.fire();
		}

	}

	/**
	 * �����¼�����
	 * 
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		// ȷ��̹�˵ķ���
		locateDirection();
	}

	/**
	 * �����ӵ�
	 * 
	 * @return
	 */
	private Missile fire() {
		// TODO Auto-generated method stub
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, good, ptDir, tc);
		tc.missiles.add(m);
		return m;
	}
	private Missile fire(Direction dir) {
		// TODO Auto-generated method stub
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, good, dir, tc);
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
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
	}

	/**
	 * ȷ��̹�˵ķ���
	 */

	void locateDirection() {
		if (bL && !bR && !bU && !bD)
			dir = Direction.L;
		else if (bL && !bR && bU && !bD)
			dir = Direction.LU;
		else if (bL && !bR && !bU && bD)
			dir = Direction.LD;
		else if (!bL && bR && !bU && !bD)
			dir = Direction.R;
		else if (!bL && bR && bU && !bD)
			dir = Direction.RU;
		else if (!bL && bR && !bU && bD)
			dir = Direction.RD;
		else if (!bL && !bR && bU && !bD)
			dir = Direction.U;
		else if (!bL && !bR && !bU && bD)
			dir = Direction.D;
		else if (!bL && !bR && !bU && !bD)
			dir = Direction.STOP;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public void stay() {
		this.x = xOld;
		this.y = yOld;
	}

	/**
	 * ̹����ǽ����ײ
	 * 
	 * @param w
	 * @return
	 */
	public boolean ColidesWithWall(Wall w) {
		if (this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	/**
	 * ̹����̹������ײ
	 * 
	 * @param tanks
	 * @return
	 */
	public boolean colidesWithTanks(java.util.List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);
			if(this!=t){
				if(this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	private void superFire(){
		Direction[] dirs =Direction.values();
		for(int i=0;i<dirs.length;i++){
			this.fire(dirs[i]);
		}
	}
}
