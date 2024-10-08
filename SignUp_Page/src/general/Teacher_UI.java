package general;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class Teacher_UI{


    private static final String CHARACTERS_CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    public  static String teacherID;
    private boolean resultSetError = false;
    private String uniqueClassCode;
    private int iJSrollPane = 0;
    
    
    public static ArrayList<String> teacherOwnClass;
    public static int componentClass;
    
    
    public  JFrame frame;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
    private JScrollPane scrollPane;
    private JLayeredPane createClass_panel;
    private JLabel name_CreateClass;
    private JLabel pass_CreatClass;
    private JButton summitClass; 
    private JCheckBox showPass; 
    private JLabel teacher_class;
    private GridBagConstraints gbc;
    private JPanel panel;

	public Teacher_UI(SignIn_Window signin) {
		
		 try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
		
		panel = new JPanel(new GridBagLayout());
		scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10, 127, 664, 248);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add(scrollPane);
		
		
		
		createClass_panel = new JLayeredPane();
		createClass_panel.setBackground(Color.DARK_GRAY);
		createClass_panel.setBounds(147, 32, 353, 90);
		createClass_panel.setOpaque(true);
		createClass_panel.setVisible(false);
		contentPane.add(createClass_panel);
		
		
		teacher_class = new JLabel("Your class");
		teacher_class.setBounds(10, 86, 157, 30);
		contentPane.add(teacher_class);
		
		JButton createClass = new JButton("Create class");
		createClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewClassButton();
			}
		});
		createClass.setBounds(517, 32, 157, 54);
		contentPane.add(createClass);
		
		
		textField = new JTextField();
		textField.setBounds(112, 11, 126, 25);
		textField.setVisible(false);
		createClass_panel.add(textField);
		textField.setColumns(10);
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(112, 54, 126, 25);
		passwordField.setVisible(false);
		createClass_panel.add(passwordField);
		
		
		name_CreateClass = new JLabel("Class Name");
		name_CreateClass.setBounds(10, 16, 92, 20);
		name_CreateClass.setVisible(false);
		createClass_panel.add(name_CreateClass);
		
		
		pass_CreatClass = new JLabel("Pass (Optional)");
		pass_CreatClass.setBounds(10, 59, 92, 20);
		pass_CreatClass.setVisible(false);
		createClass_panel.add(pass_CreatClass);
		
		
		summitClass = new JButton("Summit");
		summitClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				summitAction();
			}});
		summitClass.setBounds(254, 11, 89, 25);
		createClass_panel.setVisible(false);
		createClass_panel.add(summitClass);
		
		
		showPass = new JCheckBox("Show pass");
		showPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(showPass.isSelected()) 
					passwordField.setEchoChar((char)0);
				else
					passwordField.setEchoChar(('*'));
			}
		});
		
		showPass.setBounds(254, 55, 89, 24);
		showPass.setVisible(false);
		createClass_panel.add(showPass);
		

	}
	
	
	private void summitAction() {
		try {
			
			if(textField.getText().equals(""))
				JOptionPane.showMessageDialog(null, "Your class name is empty", "Warning!", JOptionPane.WARNING_MESSAGE);
			else {
			   if(generateUniqueCode())
			        pushInfotoSQL();
			        addClass();
			        JOptionPane.showMessageDialog(null, "Successfully", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	private void addClass() {

		    char[] pass = passwordField.getPassword();
		    String password  = new String(pass);
        	
            JLabel classCode_label = new JLabel("Code: "+uniqueClassCode);
            JButton button = new JButton(textField.getText());
            JLabel label2 = new JLabel();
        	
        	label2.setText(" Password: "+password);
        	
            gbc.gridx = 0; // Column 1
            gbc.gridy = iJSrollPane++;// Incrementing row number
            panel.add(button, gbc);

            // Position the label in the first column
            gbc.gridx = 1; // Column 2
            panel.add(classCode_label, gbc);
            
            gbc.gridx = 2; // Column 2
            panel.add(label2, gbc);

            panel.revalidate();
            panel.repaint();
        
		
	}
	
	public void initializeClass() {
		
//		gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
        int j = 0;
        for (int i = 0; i < (componentClass / 3); i++) {
        	
        	String classCode = teacherOwnClass.get(j++);
        	String className = teacherOwnClass.get(j++);
        	String classPass = teacherOwnClass.get(j++);
        	
            JLabel classCode_label = new JLabel("Code: "+classCode);
            JButton button = new JButton(className);
            JLabel label2 = new JLabel();
        	
        	if(classPass == null) 
        		label2.setText(" Password: ");
        	else 
        		label2.setText(" Password: "+classPass);

            gbc.gridx = 0; // Column 1
            gbc.gridy = iJSrollPane++;// Incrementing row number
            panel.add(button, gbc);

            // Position the label in the first column
            gbc.gridx = 1; // Column 2
            panel.add(classCode_label, gbc);
            
            gbc.gridx = 2; // Column 2
            panel.add(label2, gbc);


        }
        
        panel.revalidate();
        panel.repaint();
        
        
        
	}
	
	
	private void createNewClassButton() {
		createClass_panel.setVisible(!createClass_panel.isVisible());
		textField.setVisible(true);
		showPass.setVisible(true);
		createClass_panel.setVisible(true);
		name_CreateClass.setVisible(true);
		textField.setVisible(true);
		passwordField.setVisible(true);
		pass_CreatClass.setVisible(true);
	}
	
	
	
	
	private void pushInfotoSQL() throws ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		char[] pass = passwordField.getPassword();
		String nameClass = new String(textField.getText());
		String password  = new String(pass);
		String sql;
		
		if(!password.equals(null)) {
		    sql = "INSERT INTO class(class_code,class_name,pass_word,teacher_id) VALUES (?,?,?,?)";
		}else {
		    sql = "INSERT INTO class(class_code,class_name,teacher_id) VALUES (?,?,?)";
		}
		

		
		
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
	             PreparedStatement ps = conn.prepareStatement(sql)) {
    		     
        	           if(password.equals(null)) {
		                    ps.setString(1, uniqueClassCode);
		                    ps.setString(2, nameClass);
		                    ps.setString(3, teacherID);
       	               }else {
		                    ps.setString(1, uniqueClassCode);
		                    ps.setString(2, nameClass);
		                    ps.setString(3, password);
		                    ps.setString(4, teacherID);
       	               }
        	     ps.executeUpdate(); 
			     ps.close();
			     conn.close(); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
		createClass_panel.setVisible(false);
		textField.setVisible(false);
		showPass.setVisible(false);
		createClass_panel.setVisible(false);
		name_CreateClass.setVisible(false);
		textField.setVisible(false);
		passwordField.setVisible(false);
		pass_CreatClass.setVisible(false);
		
	}
	
	private boolean checkUniqueCode(String codetoCheck) throws ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String sql = "SELECT class_code FROM test.class WHERE class_code = ?;";

		
		
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
	             PreparedStatement ps = conn.prepareStatement(sql)) {
        	     ps.setString(1, codetoCheck);
	             ResultSet rs = ps.executeQuery();

	             if (rs.next()) { 	    
	            	 String classCode = rs.getString("class_code");
                     if(!classCode.equals(codetoCheck)) 
                    	 return false; // if codetoCheck is unique
                 }else {
                	 resultSetError = true;
                 }

			     ps.close();
			     conn.close(); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
		
		return true; // if codetoCheck is duplicated
	}
	
	private boolean generateUniqueCode() throws ClassNotFoundException {
        StringBuilder code = new StringBuilder(8);
        do {
        	
        	code.setLength(0);
            for(int i = 0; i < 8; i++) {
                code.append(CHARACTERS_CODE.charAt(RANDOM.nextInt(CHARACTERS_CODE.length())));
            }
            
        }while(checkUniqueCode(code.toString()) && !resultSetError);


        uniqueClassCode = code.toString();
        System.out.println(uniqueClassCode);
        return true;
    }
	
	
	
	
}
