import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.RepaintManager;


/**
 * 游戏的窗体类
 * @author wangxiaoxin
 *
 */
public class TankClient extends Frame {
	public static final int GAME_WIDTH = 800; // 游戏窗口的大小
	public static final int GAME_HEIGTH = 600;
	public Tank myTank = new Tank(50, 300, true, Tank.Direction.STOP,this);
    public Wall w1=new Wall(0, 200, 300, 30, this);
    public Wall w2=new Wall(400, 200, 300,30, this);
	
    public Blood b=new Blood();
	public List<Explode> explodes = new ArrayList<Explode>();
	public List<Missile> missiles = new ArrayList<Missile>();
	public List<Tank> tanks = new ArrayList<Tank>();

	/**
	 * 该方法由jvm自己调用，而且一直在调用
	 * TankClient的paint方法 
	 */
	@Override
	public void paint(Graphics g) {
		// 画出子弹、爆炸的数目提示
		g.drawString("Missiles count: " + missiles.size(), 40, 40);
		g.drawString("Explodes count: " + explodes.size(), 40, 50);
		g.drawString("Tanks count: " + tanks.size(), 40, 60);
		g.drawString("myTank count: " + myTank.getLife(), 40, 70);
		if(tanks.size()<=0){
			for(int i=0;i<5;i++){
				tanks.add(new Tank(200,100+(i*30),false,Tank.Direction.L,this));
			}
		}
		// 画出我方的坦克
		myTank.draw(g);
		myTank.eat(b);
		//画出墙
		w1.draw(g);
		w2.draw(g);
		// 画出所有的子弹
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}

		// 画出所有的爆炸
		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		// 画出敌人坦克
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);
			t.draw(g);
			t.ColidesWithWall(w1);
			t.ColidesWithWall(w2);
			t.colidesWithTanks(tanks);
		}
		b.draw(g);
		
	}

	Image offScreenImage = null;

	/**
	 * 使用双缓冲消除闪烁现象
	 */
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGTH);
		}
		Graphics gOffScreenImage = offScreenImage.getGraphics(); // 得到背景图片的画笔
		Color c = gOffScreenImage.getColor();
		gOffScreenImage.setColor(Color.GREEN);
		gOffScreenImage.fillRect(0, 0, GAME_WIDTH, GAME_HEIGTH);
		gOffScreenImage.setColor(c);
		paint(gOffScreenImage);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	/**
	 * 启动窗口
	 */
	public void lanchFrame() {
		for(int i=0;i<10;i++){
			tanks.add(new Tank(100+(i*50),50,false,Tank.Direction.L,this));
		}
		this.setLocation(300, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGTH);
		this.setTitle("TankWar");
		this.setBackground(Color.GREEN);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});
		this.setResizable(false);
		this.setVisible(true);
		Thread thread = new Thread(new PaintThread());
		thread.start();
		this.addKeyListener(new KeyMonitor());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankClient tc = new TankClient();
		tc.lanchFrame();
	}

	/**
	 * 内部类 启动一个线程重画
	 * 
	 * @author Administrator
	 *
	 */
	public class PaintThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				//
				repaint();
				try {
					Thread.sleep(50);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 键盘监听类
	 * 
	 * @author Administrator
	 *
	 */
	public class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			myTank.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			myTank.keyReleased(e);
		}
	}
}
