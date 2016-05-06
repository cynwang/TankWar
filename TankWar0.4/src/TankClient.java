import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.RepaintManager;

/**
 * 
 * @author wangxiaoxin
 *
 */
public class TankClient extends Frame {
	
	int x=50,y=50;
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		y+=5;
	}
    Image offScreenImage=null;
    /**
     * 使用双缓冲消除闪烁现象
     */
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if(offScreenImage==null){
			offScreenImage=this.createImage(800,600);
		}
		Graphics gOffScreenImage =offScreenImage.getGraphics();  //得到背景图片的画笔
		Color c =gOffScreenImage.getColor();
		gOffScreenImage.setColor(Color.GREEN);
		gOffScreenImage.fillRect(0, 0, 800, 600);
		gOffScreenImage.setColor(c);
		paint(gOffScreenImage);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	/**
	 * 启动窗口
	 */
	public void lanchFrame() {
		this.setLocation(300, 100);
		this.setSize(800, 600);
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
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankClient tc = new TankClient();
		tc.lanchFrame();
	}
	/**
	 * 内部类   启动一个线程重画
	 * @author Administrator
	 *
	 */
	public class PaintThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
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
}
