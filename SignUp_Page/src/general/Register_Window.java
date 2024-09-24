package general;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
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

public class Register_Window extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JComboBox<String> comboBox;
	private JLabel label;
	private JLabel userLabel;
	private JLabel passLabel;
	private JCheckBox showPass;
	private JButton summit;
	
    private boolean checkLength;
    private boolean checkComboBox;
    
    
	public Register_Window(MainMenu mainmenu) {
		
		 try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		
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
		
		label = new JLabel("You are a ");
		label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		label.setBounds(199, 28, 172, 22);
		contentPane.add(label);
		
		userLabel = new JLabel("Username");
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userLabel.setBounds(43, 125, 90, 17);
		contentPane.add(userLabel);
		
		passLabel = new JLabel("Password");
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
		
		
		summit = new JButton("Register");
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
	
	
	private void summitAction() {
		
		checkLength = checkLength(usernameField.getText().length(), passwordField.getPassword().length);
		checkComboBox = checkcomboBox((String) comboBox.getSelectedItem());
		boolean checkUsername = false;
		
		try {
			if(!checkExistedAccount(usernameField.getText(),(String) comboBox.getSelectedItem())) {JOptionPane.showMessageDialog(null, "This username is already existed", "Warning!", JOptionPane.WARNING_MESSAGE); checkUsername = false;}
			else {checkUsername = true;}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
			if(checkLength && checkComboBox && checkUsername) {
				try {
					passInfo_toDatabase(usernameField.getText(),passwordField.getPassword(),(String) comboBox.getSelectedItem());
					usernameField.setText("");
					passwordField.setText("");
					comboBox.setSelectedIndex(0);
					showPass.setSelected(false);
			    } catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
	}
	

	
	private void passInfo_toDatabase(String name, char[] pass, String selection) throws SQLException {
		

		String password = new String(pass);
	
		try {
			
			     Class.forName("com.mysql.cj.jdbc.Driver");
			     Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
			     
			     StringBuilder query = new StringBuilder();
			     query.append("INSERT INTO user_"+selection.toLowerCase()+" (username, pass_word) VALUES (?,?)");
			     
			     PreparedStatement ps = connection.prepareStatement(query.toString());
			
			     ps.setString(1, name);
			     ps.setString(2, password);
			     
			     ps.executeUpdate(); 
			     JOptionPane.showMessageDialog(null, "Successfully", "Warning!", JOptionPane.WARNING_MESSAGE);
			     ps.close();
			     connection.close();
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}


	
	private boolean checkExistedAccount(String nameToCheck, String selection) throws ClassNotFoundException {
		    Class.forName("com.mysql.cj.jdbc.Driver"); 
	        String sql_student = "SELECT username FROM test.user_student WHERE username = ? ;";
	        String sql_teacher = "SELECT username FROM test.user_teacher WHERE username = ? ;";
	        String sql;
	        
	        if(selection.equals("Student"))
	        	sql = sql_student;
	        else
	        	sql = sql_teacher;
	      
	        
            boolean checkExist = true;
	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	             ps.setString(1, nameToCheck);
	            
	             ResultSet rs = ps.executeQuery();

	             if (rs.next()) {
	                 System.out.println("The student '" + nameToCheck + "' exists in the database.");
	                 checkExist = false;
	             } else {
	                 System.out.println("The student '" + nameToCheck + "' does not exist in the database.");
	             }
        
			     ps.close();
			     conn.close();
			     
			     return checkExist;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		

		
		
		return true;
		
	}

	private boolean checkLength(int username, int password) {
		
        if(username >= 20 || username == 0) {
        	JOptionPane.showMessageDialog(null, "Your username lenght must be below 20, greater than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
        	return false;
        }else if(password >= 20 || password == 0) {
        	JOptionPane.showMessageDialog(null, "Your password lenght must be below 20, greater than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
        	return false;
        }
        
        return true;
	}
	
	private boolean checkcomboBox(String comboBox) {
		if(comboBox.equals("")) {
			JOptionPane.showMessageDialog(null, "Your selection is empty", "Warning!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

}
