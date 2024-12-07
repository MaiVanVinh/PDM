package studentAccount;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import general.MainMenu;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Student_UI{

	public  static JFrame Studentframe;
	public  static String STUDENT_ID;
	private static JPanel contentPane;
	private JTextField codeField;
	private JTextField passField;
	private JCheckBox showPass;
	private JButton summit;
	private JLayeredPane layeredPane;

	private static StudentClass studentClassList;
	private static ShowStudentQuiz showQuiz;
	private JButton logOut;

	public Student_UI(MainMenu mainmenu) {
		
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		 
		Studentframe = new JFrame();
		Studentframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Studentframe.setBounds(100, 100, 750, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		Studentframe.setContentPane(contentPane);
		contentPane.setLayout(null);
        

		layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.DARK_GRAY);
		layeredPane.setBounds(187, 6, 288, 93);
		contentPane.add(layeredPane);
		
		codeField = new JTextField();
		codeField.setBounds(117, 11, 128, 20);
		layeredPane.add(codeField);
		codeField.setColumns(10);
		
		passField = new JTextField();
		passField.setBounds(117, 42, 128, 20);
		layeredPane.add(passField);
		passField.setColumns(10);
		
		showPass = new JCheckBox("");
		showPass.setBounds(251, 42, 21, 23);
		layeredPane.add(showPass);
		
		summit = new JButton("Summit");
		summit.setFocusable(false);
		summit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				joinClassCode(codeField.getText(),passField.getText());
				codeField.setText("");
				passField.setText("");
			}
		});
		summit.setBounds(94, 71, 89, 20);
		layeredPane.add(summit);
		
		JLabel classCode = new JLabel("Course");
		classCode.setFont(new Font("Tahoma", Font.PLAIN, 15));
		classCode.setBounds(28, 12, 79, 14);
		layeredPane.add(classCode);
		
		JLabel pass = new JLabel("Password");
		pass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pass.setBounds(28, 43, 68, 14);
		layeredPane.add(pass);
		
		JButton studentClass = new JButton("Your Course");
		studentClass.setFocusable(false);
		studentClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadStudentClass();
			}
		});
		studentClass.setBounds(589, 55, 97, 44);
		contentPane.add(studentClass);
		
		layeredPane.setVisible(false);
		
		JButton joinClass = new JButton("Join Course");
		joinClass.setFocusable(false);
		joinClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.setVisible(true);
			}
		});
		joinClass.setBounds(589, 0, 97, 44);
		contentPane.add(joinClass);
		
		studentClassList = new StudentClass();
		studentClassList.setLocation(2, 152);
		contentPane.add(studentClassList);
		
		logOut = new JButton("Log out");
		logOut.setBounds(10, 6, 89, 23);
		logOut.setFocusable(false);
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Studentframe.dispose();
				Studentframe = null;
				mainmenu.setVisible(true);
				mainmenu.reset(1);
			}
		});
		contentPane.add(logOut);
		
		Studentframe.setLocationRelativeTo(null);
	}
	
	
	private void loadStudentClass() {
		studentClassList.setVisible(true);
	}
	
	public static void switchToQuizPane(String name,String classCode) {
		studentClassList.setVisible(false);
		System.out.println("Student_UI: "+classCode+"_"+name);
		showQuiz = new ShowStudentQuiz(classCode,name);
		showQuiz.setLocation(2, 152);
		contentPane.add(showQuiz);
		showQuiz.setVisible(true);
		
	}
	
	public static void switchBack() {
		showQuiz.setVisible(false);
		studentClassList.setVisible(true);
	}
	
	private void joinClassCode(String classCode, String pass) {
		CheckClassCode checkClass = new CheckClassCode();
		int classID = 0;
		String className = "";
		boolean check = false;
		
		try {
			check = checkClass.checkDatabase(classCode, pass);
			classID = checkClass.getClassID();
			className = checkClass.getName();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(check)
			addStudentToClass(classCode,classID,className);
		else
			JOptionPane.showMessageDialog(null, "The course does not exist", "Warning!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	private void addStudentToClass(String classCode,int classID,String className) {
		CheckClassCode joinClass = new CheckClassCode();
	    boolean check = false;


		try {
			if(joinClass.checkAvailability(STUDENT_ID, classID))
				JOptionPane.showMessageDialog(null, "You are in this course already !", "Announcement!", JOptionPane.INFORMATION_MESSAGE);
			else
			  check = joinClass.addStudentToClass(STUDENT_ID, classID,className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if(check) {
			JOptionPane.showMessageDialog(null, "Successfully !", "Announcement!", JOptionPane.INFORMATION_MESSAGE);
			studentClassList.addClass(classCode,className);
		}else
			JOptionPane.showMessageDialog(null, "Try again !", "Warning!", JOptionPane.WARNING_MESSAGE);
			
		
	}
	
	public void nothing() {
		
	}
}
