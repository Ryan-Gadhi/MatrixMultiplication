import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class WelcomeScreen {

	private JFrame frame;
	private JTextField textField;
//	static StrassensMethod s = new StrassensMethod();
//	static StrassensMethod s2 = new StrassensMethod();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeScreen window = new WelcomeScreen();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WelcomeScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Start Simulation");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				s.start();
//				s2.c = 1;
//				s2.start();

//				new GraphicsPlot();
				GraphicsPlot p = new GraphicsPlot();
			}
		});
		btnNewButton.setBounds(146, 49, 148, 50);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblMatrixMulitiplication = new JLabel("Matrix Mulitiplication");
		lblMatrixMulitiplication.setBounds(136, 19, 171, 16);
		frame.getContentPane().add(lblMatrixMulitiplication);
		
		JLabel lblMatrixSize = new JLabel("Matrix Size");
		lblMatrixSize.setBounds(30, 124, 106, 16);
		frame.getContentPane().add(lblMatrixSize);
		
		textField = new JTextField();
		textField.setBounds(115, 119, 79, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
