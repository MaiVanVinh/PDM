package general;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import connectionSQL.MyConnection;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField phoneInput;
    
    
	public Register_Window(MainMenu mainmenu) {
		
		 try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 671, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Teacher", "Student"}));
		comboBox.setBounds(235, 61, 188, 22);
		contentPane.add(comboBox);
		
		label = new JLabel("You are a ");
		label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		label.setBounds(251, 28, 172, 22);
		contentPane.add(label);
		
		userLabel = new JLabel("Username");
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userLabel.setBounds(10, 125, 90, 17);
		contentPane.add(userLabel);
		
		passLabel = new JLabel("Password");
		passLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passLabel.setBounds(10, 184, 90, 17);
		contentPane.add(passLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(110, 127, 188, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		

		
		passwordField = new JPasswordField();
		passwordField.setBounds(110, 186, 188, 20);
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
		showPass.setBounds(110, 214, 120, 23);
		contentPane.add(showPass);
		
		
		summit = new JButton("Register");
		summit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				summitAction();
			}});
		summit.setBounds(209, 398, 89, 33);
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
		back.setBounds(361, 398, 89, 33);
		contentPane.add(back);
		
		JLabel firstName = new JLabel("First Name");
		firstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		firstName.setBounds(350, 125, 100, 22);
		contentPane.add(firstName);
		
		firstNameInput = new JTextField();
		firstNameInput.setBounds(460, 127, 172, 20);
		contentPane.add(firstNameInput);
		firstNameInput.setColumns(10);
		
		JLabel lastName = new JLabel("Last Name");
		lastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lastName.setBounds(350, 184, 100, 22);
		contentPane.add(lastName);
		
		lastNameInput = new JTextField();
		lastNameInput.setColumns(10);
		lastNameInput.setBounds(460, 186, 172, 20);
		contentPane.add(lastNameInput);
		
		JLabel phoneNum = new JLabel("Phone Number");
		phoneNum.setFont(new Font("Tahoma", Font.PLAIN, 20));
		phoneNum.setBounds(268, 244, 142, 33);
		contentPane.add(phoneNum);
		
		phoneInput = new JTextField();
		phoneInput.setColumns(10);
		phoneInput.setBounds(251, 277, 172, 20);
		contentPane.add(phoneInput);
		setVisible(true);

	}
	
	
	private void summitAction() {
		
		if(phoneInput.getText().equals("") || lastNameInput.getText().equals("") || firstNameInput.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "You have to fill up everything !!", "Warning!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String lastName = lastNameInput.getText();
		String firstName = firstNameInput.getText();
		String phoneNum = phoneInput.getText();
		
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
					createUserAccount(usernameField.getText(),passwordField.getPassword(),(String) comboBox.getSelectedItem(),lastName,firstName,phoneNum);
					usernameField.setText("");
					passwordField.setText("");
					lastNameInput.setText("");
					firstNameInput.setText("");
					phoneInput.setText("");
					comboBox.setSelectedIndex(0);
					showPass.setSelected(false);
			    } catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
	}
	

	
	private void createUserAccount(String name, char[] pass, String role,String firstName,String lastName,String phoneNum) throws SQLException {

		String password = new String(pass);
		int ID = 0;
	
		try {
			
			     Class.forName("com.mysql.cj.jdbc.Driver");
			     Connection connection = MyConnection.getConnection();
			     
			     StringBuilder query = new StringBuilder();
			     query.append("INSERT INTO accounts (user_Name, pass_word,role) VALUES (?,?,?)");
			     
			     PreparedStatement ps = connection.prepareStatement(query.toString(),Statement.RETURN_GENERATED_KEYS);
			     ps.setString(1, name);
			     ps.setString(2, password);
			     ps.setString(3, role);
			     ps.executeUpdate(); 
			     
			     ResultSet pK = ps.getGeneratedKeys();
			     if(pK.next()) 
			         ID = pK.getInt(1);
			     pushTeacherOrStudentInfomation(ID,role,firstName,lastName,phoneNum);
			     
			     JOptionPane.showMessageDialog(null, "Successfully", "Warning!", JOptionPane.WARNING_MESSAGE);
			     ps.close();
			     connection.close();
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

	private void pushTeacherOrStudentInfomation(int userID,String role,String firstName,String lastName,String phoneNum) throws SQLException {

		
		try {
			
			     Class.forName("com.mysql.cj.jdbc.Driver");
			     Connection connection = MyConnection.getConnection();
			     
			     StringBuilder query = new StringBuilder();
			     query.append("INSERT INTO "+role+" (user_id,firstName,lastName,phoneNumber) VALUES (?,?,?,?)");
			     
			     PreparedStatement ps = connection.prepareStatement(query.toString());
			
			     ps.setInt(1, userID);
			     ps.setString(2, firstName);
			     ps.setString(3, lastName);
			     ps.setString(4, phoneNum);
			     ps.executeUpdate(); 
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	private boolean checkExistedAccount(String nameToCheck, String selection) throws ClassNotFoundException {
		    Class.forName("com.mysql.cj.jdbc.Driver"); 
	        String sql = "SELECT user_Name FROM test.accounts WHERE user_Name = ? ;";

            boolean checkExist = true;
	        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	             ps.setString(1, nameToCheck);           
	             ResultSet rs = ps.executeQuery();
	             if (rs.next()) 
	                 checkExist = false;

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
