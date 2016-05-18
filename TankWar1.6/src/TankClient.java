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
 * 
 * @author wangxiaoxin
 *
 */
public class TankClient extends Frame {
	public static final int GAME_WIDTH = 800; // 游戏窗口的大小
	public static final int GAME_HEIGTH = 600;
	int x = 50, y = 50;
	Tank myTank = new Tank(50, 50, true, this);
	Tank enemyTank = new Tank(50, 50, false, this);

	List<Missile> missiles = new ArrayList<Missile>();

	/**
	 * TankClient的paint方法 主要是画坦克，子弹
	 */
	@Override
	public void paint(Graphics g) {
		myTank.draw(g);

		g.drawString("Missiles count: " + missiles.size(), 40, 40);
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTank(enemyTank);
			if (!m.isLive())
				missiles.remove(m);
			else
				m.draw(g);

		}
		if(enemyTank.isLive()) enemyTank.draw(g);
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
					Thread.sleep(100);

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
