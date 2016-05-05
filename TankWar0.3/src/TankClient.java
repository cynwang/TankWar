import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.RepaintManager;

/**
 * 
 * @author wangxiaoxin
 *
 */
public class TankClient extends Frame {
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(50, 50, 30, 30);
		g.setColor(c);
	
	}

	/**
	 * Æô¶¯´°¿Ú
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
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankClient tc = new TankClient();
		tc.lanchFrame();
	}
	
}
