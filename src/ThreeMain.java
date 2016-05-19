import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ThreeMain {

	public static JFrame frame;
	public static int WIDTH = 768;
	public static int HEIGHT = 768;
	
	
	public static void main(String[] args)
	{
		 
		 int w = (800);		 
		 int h = (800);		 
		 Central central = new Central(w,h);
	     central.setMinimumSize(new Dimension(w, h));
	     central.setMaximumSize(new Dimension(w, h));
		 central.setPreferredSize(new Dimension(w, h));
		
		 frame = new JFrame("Three");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setLayout(new BorderLayout());
		 frame.add(central, "Center");
		 frame.pack();
		 frame.setResizable(false);
		 frame.setLocationRelativeTo(null);
		 frame.setVisible(true);
		 central.start();
	}

}
