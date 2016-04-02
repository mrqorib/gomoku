import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class GomokuFrame extends JFrame{
	private static final long serialVersionUID = -2890315465816072510L;

	public static void main(String[] args) {
		GomokuPanel panel = new GomokuPanel();
	    
		//Membuat dan mengatur frame
		JFrame frame = new JFrame();
		frame.setSize(950,700);
		frame.add(panel);
		frame.setTitle("Gomoku");
		URL url = GomokuFrame.class.getResource("/home.png");
		Image background = Toolkit.getDefaultToolkit().createImage(url);
		
		//Membuat home screen
		final JDialog dialog = new JDialog(frame, "Home Screen", true);
		Home home = new Home();
		dialog.add(home);
		dialog.setIconImage(background);
	    dialog.setSize(frame.getWidth(), frame.getHeight());
	    dialog.setLocationRelativeTo(frame);
		home.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
	    home.addActionListener(new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        dialog.dispose();
	      }
	    });
	    
	    dialog.setUndecorated(true);
	    dialog.setVisible(true);
	    
	    frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

/**
 * Class tambahan yang beruba panel dari Home Screen nya
 * @author M. Reza Qorib
 *
 */
class Home extends JButton{
	private static final long serialVersionUID = -2384513813030148385L;
	private Image background;
		
		public Home(){
			URL url = Home.class.getResource("/home.png");
			background = Toolkit.getDefaultToolkit().createImage(url);
			setBorderPainted(false);
			Graphics g = getGraphics();
			paint(g);
		}
		
		public void paintComponent(Graphics g){
			g.drawImage(background, 0, 0, null);
		}
		
	}
