package general;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import loadRes.LoadCreatedClass;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class SignIn_Window extends JFrame {


	private static final long serialVersionUID = 1L;
    public static boolean checkUsername = false;
    
    private String id;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JComboBox<String> comboBox;
	private JCheckBox showPass;
	
	
    private Teacher_UI teacher_UI;
    private LoadCreatedClass getCreatedClass;
    
    
    
	public SignIn_Window(MainMenu mainmenu) {
		
		 try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		getCreatedClass = new LoadCreatedClass();
		teacher_UI = new Teacher_UI(this);
		teacher_UI.frame.setVisible(false);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 530, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Teacher", "Student"}));
		comboBox.setBounds(175, 61, 188, 22);
		contentPane.add(comboBox);
		
		JLabel label = new JLabel("You are a ");
		label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		label.setBounds(199, 28, 172, 22);
		contentPane.add(label);
		
		JLabel userLabel = new JLabel("Username");
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userLabel.setBounds(43, 125, 90, 17);
		contentPane.add(userLabel);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passLabel.setBounds(43, 186, 90, 17);
		contentPane.add(passLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(175, 127, 188, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		

		
		passwordField = new JPasswordField();
		passwordField.setBounds(175, 186, 188, 20);
		contentPane.add(passwordField);
		
		
		showPass = new JCheckBox("Show password");
		showPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(showPass.isSelected()) 
					passwordField.setEchoChar((char)0);
				else
					passwordField.setEchoChar(('*'));
			}  
		});
		showPass.setBounds(175, 213, 120, 23);
		contentPane.add(showPass);
		
		
		JButton summit = new JButton("Sign In");
		summit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   summitAction();
			}});
		summit.setBounds(175, 270, 89, 33);
		contentPane.add(summit);

		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usernameField.setText("");
				passwordField.setText("");
				comboBox.setSelectedIndex(0);
				showPass.setSelected(false);
				setVisible(false);
				mainmenu.setVisible(true);
			}
		});
		back.setBounds(274, 270, 89, 33);
		contentPane.add(back);
		setVisible(true);

	}
	

    private void loadClassToTeacherAccount() throws ClassNotFoundException {
    	
    	if(!id.equals(null) ) {
    		LoadCreatedClass.ID = id;
    		getCreatedClass.getCreatedClass();
    		Teacher_UI.teacherOwnClass = getCreatedClass.getInfoClass();
    		Teacher_UI.componentClass  = getCreatedClass.getNum();
			openTeacherUI();
    	}


	}
	
	
   private void openTeacherUI() {
	    setVisible(false);
	    teacher_UI.initializeClass();
	    teacher_UI.frame.setVisible(true);
	    teacher_UI.frame.setLocationRelativeTo(null);
	    
   }

   private void summitAction() {
		try {
			  if(checkLogin( passwordField.getPassword(), (String) comboBox.getSelectedItem())) {
				  usernameField.setText("");
				  passwordField.setText("");
				  comboBox.setSelectedIndex(0);
				  showPass.setSelected(false);
				  loadClassToTeacherAccount();
				  JOptionPane.showMessageDialog(null, "Login successfully !!", "Warning!", JOptionPane.WARNING_MESSAGE);		
			  }
		} catch (HeadlessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
   }
   
	

   private boolean checkLogin(char[] password, String selection) throws HeadlessException, ClassNotFoundException {
	   if(checkExistedAccount(usernameField.getText(),password,selection)) {
		   checkUsername = true;
		   return true;
	   }
	return false;

   }
   

	private boolean checkExistedAccount(String nameToCheck,char[] pass, String selection) throws ClassNotFoundException {
		    Class.forName("com.mysql.cj.jdbc.Driver"); 
		    String password = new String(pass);
	        String sql_student = "SELECT id_student, pass_word FROM test.user_student WHERE username = ?;";
	        String sql_teacher = "SELECT id_teacher, pass_word FROM test.user_teacher WHERE username = ? ;";
	        String sql = null;
	        id  = null;
	        
	        
	        if(selection.equals("Student"))
	        	sql = sql_student;
	        else if(selection.equals("Teacher"))
	        	sql = sql_teacher;
	        else 
	        	JOptionPane.showMessageDialog(null, "Your selection is empty", "Warning!", JOptionPane.WARNING_MESSAGE);
	        
	        
            boolean checkAuth = false;
	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	             ps.setString(1, nameToCheck);
	            
	             ResultSet rs = ps.executeQuery();

	             if (rs.next()) { 	    
	            	 String storedPassword = rs.getString("pass_word");
	            	 
	     	        if(selection.equals("Student"))
	     	        	 id = rs.getString("id_student");
	    	        else
	    	        	 id = rs.getString("id_teacher");
	            	 
                    if(storedPassword.equals(password)) { 
                    	 checkAuth = true;
                    	 Teacher_UI.teacherID = id;
                    	 //System.out.println(id);
                    }
                 }
	             
	             if(!checkAuth) 
	            	 JOptionPane.showMessageDialog(null, "Wrong username or password", "Warning!", JOptionPane.WARNING_MESSAGE);
	             
			     ps.close();
			     conn.close();
			     
			     
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	  return checkAuth;
	}
	
	
}
