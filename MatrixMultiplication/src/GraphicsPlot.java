import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.util.Arrays;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.swing.*;

public class GraphicsPlot extends JFrame implements ActionListener {
	 int 
	    x=0
	   ,y=0
	   ,prev_x = 0,
		prev_y = 0,
		x2=0,
		y2=0,
		prev_x2 = 0,
		prev_y2 = 0, 
		size =0, 
		edgeAvoid=50; 

	 int shift_differnce = 0;
	 
	static int width=0; // set to 800 in case of errors
	static int height=0; // set to 800 in case of errors
	Timer clock = new Timer(100,this);
	static StrassensMethod s = new StrassensMethod();
	static StrassensMethod s2 = new StrassensMethod();
	static StrassensMethod s3 = new StrassensMethod();
	static StrassensMethod s4 = new StrassensMethod();

	
	int timeShift1 =200;
	int baseShift1 = 2;
	int timeShift2 =200;
	int baseShift2 = 2;
	
	int trueBase1 = 0;
	int trueBase2 = 0;
	
	Color firstNodeColor = Color.CYAN;
	Color firstStringColor = Color.RED;
	Color secondNodeColor = Color.RED;
	Color secondStringColor = Color.YELLOW;
	
	boolean terminator1 = false;
	boolean terminator2 = false;
	
	static int firstTestSize = 512;
	static int secondTestSize = 256;
	
	
	CpuStats cpu =  new CpuStats();
	
	//String message = message0;
	String message = "";



	public GraphicsPlot() {
		setTitle("Matrix Multiplier");
		/* in case if multi monitor, comment the following*/
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth()-30;
		height = (int) screenSize.getHeight()-50;
		size=(int) height;
		
		/* end of commenting */
		
		
		
		setSize(width, height);
		setResizable(false);
		this.getContentPane().setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		x=0;
		y=0;
		prev_x = 0;
		prev_y = 0;
		
		x2=0;
		y2=0;
		prev_x2 = 0;
		prev_y2 = 0;
		clock.start();
		this.getContentPane().setBackground(Color.BLACK);
	}
	
	public void paint(Graphics g) {
		//g.drawLine(x*10, y-700, 23, 54);
		//this.getContentPane().setBackground(Color.BLACK);
		drawPowersOf2(g);
		drawNodes(g);
	}
	
	public  void drawNodes(Graphics g) {

		
		Font font = g.getFont().deriveFont( 38.0f ); // for string size
		g.setFont( font );
			
		g.setColor(Color.black);
		if(s.finished==2)
			message = "excection finished";
		g.drawString(message, width-400, 100);
		
		
		try {
			String cpuLoad = String.valueOf(Math.round(100*cpu.getUsage()));
			//String cpuLoad = String.valueOf(10*getProcessCpuLoad());
			cpuLoad = "CPU Usage:" + cpuLoad + " %";
			message= cpuLoad;
		}catch(Exception e) {e.printStackTrace();}
		
	
		g.setColor(Color.white);
		
		g.drawString(message,width-400,100);

		if(x!=0) 
		{
		g.setColor(firstNodeColor);
		g.fillRect(x, height-y, 25, 25);
		g.setColor(firstStringColor);
		font = g.getFont().deriveFont(20.0f ); // for string size
		g.setFont( font );
		g.drawString(/*" t: "+x/200.0 +*/" b: "+trueBase1,x+1,height-y+1);
		g.drawLine(x, height-y, prev_x, height-prev_y);
		
		g.setColor(secondNodeColor);
		g.fillRect(x2, height-y2, 25, 25);
		g.setColor(secondStringColor);

		g.drawString(" b: "+trueBase2,x2+1,height-y2+1);
		g.drawLine(x2, height-y2, prev_x2, height-prev_y2);
		}
		
		System.out.println(x+ " 1:"+ y);
		System.out.println(x2+ " 2:"+ y2);

	}
	
	public static void main(String [] args) {
		s.matrix_size = firstTestSize;
		s.start();
		s2.c = 1;
		s2.matrix_size = secondTestSize;
		s2.start();

		new GraphicsPlot();
	}
	private void drawPowersOf2(Graphics g) {
		int t = 8;
		int b = 8;
		for(int i=0; i<10 ; i++) {
			g.setColor(Color.GRAY);
			t*=2;
			// vertical time lines
			g.drawLine(t, height, t, 0);
			// horizantal base lines
			b*=4;
			g.drawLine(0, height-b, width, height-b);
			
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		
		
		double arr []= s.getXY();
		if(/*!s.isAlive()*/false) {
			if(!terminator1) {
				prev_x = 0;
				prev_y = 0;
			firstNodeColor= Color.YELLOW;
			firstStringColor = Color.cyan;
			s = new StrassensMethod();
			s.matrix_size=512;
			s.c=0;
			timeShift1 = 100;
			s.start();
			}
			terminator1 = true;
		
		}
			prev_x= x;
			prev_y =y;
			if(arr[0]==-1) {
				cpu = new CpuStats();
			}
			x = (int)(timeShift1*arr[0]);
			y = (int)(baseShift1*arr[1]+shift_differnce);
			trueBase1 = (int)arr[1];
			if(x>width) {
				x = width-edgeAvoid;
			}
			
				
		//System.out.println(Arrays.toString(s.getXY()));
		//revalidate();
		repaint();

		if(/*!s2.isAlive()*/ false) {
			if(!terminator2) {
			prev_x2 = 0;
			prev_y2 = 0;
			firstNodeColor= Color.gray;
			firstStringColor = Color.GREEN;
			s2 = new StrassensMethod();
			s2.c=1;
			s2.matrix_size=513;
			timeShift2 = 100;
			s2.start();
			}
			terminator2 = true; // request termination
		}
		double arr2 []= s2.getXY();
		prev_x2= x2;
		prev_y2 =y2;
		if(arr2[0]==-1) {
			cpu = new CpuStats();
		}
		x2 = (int)(timeShift2*arr2[0]);
		y2 = (int)(baseShift2*arr2[1]+shift_differnce);
		trueBase2 = (int)arr2[1];
		if(x2>width) {
			x2 = width-edgeAvoid;
		}
		
	//System.out.println(Arrays.toString(s2.getXY()));
		repaint();
		
		cpu = new CpuStats();
	}
	
	
	public static double getProcessCpuLoad() throws Exception {

	    MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
	    ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
	    AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

	    if (list.isEmpty())     return Double.NaN;

	    Attribute att = (Attribute)list.get(0);
	    Double value  = (Double)att.getValue();

	    // usually takes a couple of seconds before we get real values
	    if (value == -1.0)      return Double.NaN;
	    // returns a percentage value with 1 decimal point precision
	    return ((int)(value * 1000) / 10.0);
	}
	

}
