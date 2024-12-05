package general;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import connectionSQL.MyConnection;
import studentAccount.Student_UI;
import updateRes.LoadCreatedClass;
import teacherAccount.Teacher_UI;

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
    
    private Student_UI student_UI;
    
    
	public SignIn_Window(MainMenu mainmenu) {
		
		 try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		 
		getCreatedClass = new LoadCreatedClass();
		teacher_UI = new Teacher_UI(this);
		
		
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
	    Teacher_UI.frame.setVisible(true);
	    Teacher_UI.frame.setLocationRelativeTo(null);
	    
   }

   private void summitAction() {
		try {
			String selection = (String) comboBox.getSelectedItem();
			  if(checkLogin( passwordField.getPassword(), (String) comboBox.getSelectedItem())) {
				  usernameField.setText("");
				  passwordField.setText("");
				  comboBox.setSelectedIndex(0);
				  showPass.setSelected(false);
				  if(selection.equals("Student")) 
					  initializeStudentAccount();
				  else 
				      loadClassToTeacherAccount();
				  
				  JOptionPane.showMessageDialog(null, "Login successfully !!", "Warning!", JOptionPane.WARNING_MESSAGE);		
			  }else
				  System.out.println("Invalid account");
			 
		} catch (HeadlessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
   }
   

   private void initializeStudentAccount() {
		  student_UI = new Student_UI();
		  student_UI.nothing();
		  Student_UI.Studentframe.setVisible(true);
		  setVisible(false);
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
	        String sql_student = "SELECT user_ID, pass_word FROM test.accounts WHERE role = 'Student' and user_Name = ?;";
	        String sql_teacher = "SELECT user_ID, pass_word FROM test.accounts WHERE role = 'Teacher' and user_Name = ?;";
	        String sql = null;

	        id  = null;
	        
	        
	        if(selection.equals("Student"))
	        	sql = sql_student;
	        else if(selection.equals("Teacher"))
	        	sql = sql_teacher;
	        else 
	        	JOptionPane.showMessageDialog(null, "Your selection is empty", "Warning!", JOptionPane.WARNING_MESSAGE);
	        
	        
            boolean checkAuth = false;
	        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	             ps.setString(1, nameToCheck);
	             ResultSet rs = ps.executeQuery();

	             if (rs.next()) { 	    
	            	String storedPassword = rs.getString("pass_word");

                    if(storedPassword.equals(password)) { 
                    	 checkAuth = true;
                    	 id = rs.getString("user_ID");
                    	 getTeacherOrStudentID(id,selection); 
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
	
	private void getTeacherOrStudentID(String userID,String role) throws ClassNotFoundException {
		    Class.forName("com.mysql.cj.jdbc.Driver"); 
	        String sql_student = "SELECT id_student FROM test.Student WHERE user_id = ?;";
	        String sql_teacher = "SELECT id_teacher FROM test.Teacher WHERE user_id = ?;";
	        String sql = null;

	        if(role.equals("Student"))
	        	sql = sql_student;
	        else if(role.equals("Teacher"))
	        	sql = sql_teacher;
	        else 
	        	JOptionPane.showMessageDialog(null, "Your selection is empty", "Warning!", JOptionPane.WARNING_MESSAGE);

	        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	             ps.setString(1, userID);
	             ResultSet rs = ps.executeQuery();

	             if (rs.next()) { 	    
		     	     if(role.equals("Student"))
		     	        Student_UI.STUDENT_ID= rs.getString("id_student");
		    	     else
		    	        Teacher_UI.teacherID = rs.getString("id_teacher");
                 }


	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
   

	}
	
	
}
