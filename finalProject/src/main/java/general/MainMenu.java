package general;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MainMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    public static boolean loginSuccess = false;
    private JPanel contentPane;
    private JButton Register;
    private Register_Window register;
    private SignIn_Window signin;
    private Image backgroundImage;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainMenu frame = new MainMenu();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainMenu() {
    	setType(Type.UTILITY);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setFont(new Font("Dubai Medium", Font.PLAIN, 14));
    	setTitle("EduExam");
    	setForeground(new Color(0, 0, 0));
    	setBackground(new Color(255, 255, 255));
        register = new Register_Window(this);
        register.setVisible(false);

        signin = new SignIn_Window(this);
        signin.setVisible(false);

        URL imageURL = MainMenu.class.getResource("/bg.jpg");
        if(imageURL != null) {
            backgroundImage = new ImageIcon(imageURL).getImage();
        } else {
            System.out.println("imageURL is null");
        }


        setBounds(100, 100, 880, 550);
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setBorder(UIManager.getBorder("ComboBox.border"));
        contentPane.setBackground(new Color(255, 255, 255));
        setContentPane(contentPane);

        Register = new JButton("REGISTER");
        Register.setBounds(373, 308, 115, 63);
        Register.setForeground(new Color(0, 0, 0));
        Register.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
        Register.setBackground(new Color(255, 153, 255));
        Register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNewAccount();
            }
        });

        JButton Signin = new JButton("SIGN IN");
        Signin.setBounds(373, 206, 115, 70);
        Signin.setForeground(new Color(0, 0, 0));
        Signin.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
        Signin.setBackground(new Color(255, 153, 255));
        Signin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signInAccount();
            }
        });

        JLabel lblNewLabel = new JLabel("WELCOME TO ONLINE EXAM SYSTEM");
        lblNewLabel.setBounds(254, 95, 349, 63);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
        contentPane.setLayout(null);
        contentPane.add(Register);
        contentPane.add(Signin);
        contentPane.add(lblNewLabel);
    }

	public void reset(int i) {
		if(i == 0) {
		   signin = new SignIn_Window(this);
		   signin.setVisible(false);
		}else {
		   register = new Register_Window(this);
		   register.setVisible(false);
		}
			
	}
	
    private void createNewAccount() {
        setVisible(false);
        register.setVisible(true);
        register.setLocationRelativeTo(null);
    }

    private void signInAccount() {
        setVisible(false);
        signin.setVisible(true);
        signin.setLocationRelativeTo(null);
    }
}