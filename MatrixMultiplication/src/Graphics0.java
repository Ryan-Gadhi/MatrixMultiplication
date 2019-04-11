import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Graphics0 extends JFrame{
	static Graphics0 g;
	private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
 
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
 
        //Add the ubiquitous "Hello World" label.
        //JLabel label = new JLabel("Hello World");
        //frame.getContentPane().add(label);
        
//        for(int i=350;i<800;i+=50) {
//			Matrix t = Matrix.generateMatrix( i, 0, 4000);
//			Matrix t2 = Matrix.generateMatrix( i, 0, 4000);
//			long start = System.currentTimeMillis();
//			t.IterativeMultiply(t2);
//			long finish = System.currentTimeMillis();
//			System.out.println((finish - start)/1000.0);
//			
//		}
        
        
        //Display the window.
        frame.add(g);
        frame.setVisible(true);
    }
	
	public void paint(Graphics g) 
	{
		// backgournd
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
	

		g.setColor(Color.WHITE);
		g.drawString("Ryan is the beast", 50, 50);
		
		// borders
		g.setColor(Color.yellow);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(691,0,3,592);
		//the paddle
		g.setColor(Color.green);
		g.fillRect(2, 550, 100, 8);
		
		// the ball
		g.setColor(Color.yellow);
		g.fillOval(3, 2, 20, 20);
		//g.drawImage("img.png", 50, 50, 50, 50, 50, 50, 50, 50, true);
	}
	
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            		g = new Graphics0();
                createAndShowGUI();
            }
        });
    }
}