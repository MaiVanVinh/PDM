//package general;
//
//
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//import javax.swing.border.EmptyBorder;
//
//import com.formdev.flatlaf.FlatDarkLaf;
//import javax.swing.JLabel;
//import java.awt.Font;
//import java.awt.EventQueue;
//import javax.swing.JButton;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//
//
//public class test extends JFrame {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private JPanel contentPane;
//	private JButton Register;
//    private Register_Window register;
//
//
//	
//	public static void main(String[] args) {
//		
//		try {
//			 UIManager.setLookAndFeel(new FlatDarkLaf());
//		} catch (UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					test frame = new test();
//					frame.setVisible(true);
//					frame.setLocationRelativeTo(null);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//
//	public test() {
//		
//		
//		 try {
//			UIManager.setLookAndFeel(new FlatDarkLaf());
//		} catch (UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 
//		
//        
//        
//		register = new Register_Window(this);
//	    register.setVisible(false);
//	    
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 530, 353);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//		
//		Register = new JButton("Register");
//		Register.setBounds(98, 115, 115, 70);
//		Register.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			
//				createNewAccount();
//			}
//
//
//		});
//		contentPane.add(Register);
//		
//		JButton Signup = new JButton("Sign Up\r\n");
//		Signup.setBounds(303, 115, 115, 70);
//		contentPane.add(Signup);
//		
//		JLabel lblNewLabel = new JLabel("WELCOME TO ONLINE EXAM SYSTEM");
//		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
//		lblNewLabel.setBounds(98, 21, 320, 63);
//		contentPane.add(lblNewLabel);
//
//		
//
//	}
//	
//	
//	private void createNewAccount() {
//		setVisible(false);
//		register.setVisible(true);
//		register.setLocationRelativeTo(null);
//
//	}
//	
//	
//}
