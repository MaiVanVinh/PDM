 package studentAccount;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import com.formdev.flatlaf.FlatLightLaf;

import accountFactory.StudentInterface;
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
import java.awt.Window.Type;

public class Student_UI implements StudentInterface{

	public  static JFrame Studentframe;
	public  static String STUDENT_ID;
	private static JPanel contentPane;
	private JTextField codeField;
	private JTextField passField;
	private JCheckBox showPass;
	private JButton submit;
	private JLayeredPane layeredPane;

	private static StudentClass studentClassList;
	private static ShowStudentQuiz showQuiz;

	public Student_UI(MainMenu mainmenu) {
		
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		 
		Studentframe = new JFrame();
		Studentframe.setTitle("EduExam");
		Studentframe.setType(Type.UTILITY);
		Studentframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Studentframe.setBounds(100, 100, 750, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		Studentframe.setContentPane(contentPane);
		contentPane.setLayout(null);
        

		layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.DARK_GRAY);
		layeredPane.setBounds(187, 6, 288, 120);
		contentPane.add(layeredPane);
		
		codeField = new JTextField();
		codeField.setBackground(new Color(255, 153, 255));
		codeField.setForeground(new Color(0, 0, 0));
		codeField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		codeField.setBounds(117, 11, 128, 20);
		layeredPane.add(codeField);
		codeField.setColumns(10);
		
		passField = new JTextField();
		passField.setForeground(new Color(0, 0, 0));
		passField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		passField.setBackground(new Color(255, 153, 255));
		passField.setBounds(117, 42, 128, 20);
		layeredPane.add(passField);
		passField.setColumns(10);
		
		showPass = new JCheckBox("Show password");
		showPass.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		showPass.setBackground(new Color(255, 255, 255));
		showPass.setForeground(new Color(0, 0, 0));
		showPass.setToolTipText("");
		showPass.setBounds(117, 63, 128, 23);
		layeredPane.add(showPass);
		
		submit = new JButton("Submit");
		submit.setBackground(new Color(255, 255, 51));
		submit.setForeground(new Color(0, 0, 0));
		submit.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		submit.setFocusable(false);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				joinClassCode(codeField.getText(),passField.getText());
				codeField.setText("");
				passField.setText("");
			}
		});
		submit.setBounds(94, 91, 89, 20);
		layeredPane.add(submit);
		
		JLabel classCode = new JLabel("Course code");
		classCode.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
		classCode.setBounds(0, 9, 118, 20);
		layeredPane.add(classCode);
		
		JLabel pass = new JLabel("Password");
		pass.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
		pass.setBounds(10, 40, 105, 20);
		layeredPane.add(pass);
		
		JButton studentClass = new JButton("My Course");
		studentClass.setBackground(new Color(255, 255, 51));
		studentClass.setForeground(new Color(0, 0, 0));
		studentClass.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
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
		joinClass.setBackground(new Color(255, 255, 51));
		joinClass.setForeground(new Color(0, 0, 0));
		joinClass.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		joinClass.setFocusable(false);
		joinClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.setVisible(true);
			}
		});
		joinClass.setBounds(589, 0, 97, 44);
		contentPane.add(joinClass);
		
		JButton logOut = new JButton("Log out");
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
		
		studentClassList = new StudentClass();
		studentClassList.setBackground(new Color(255, 255, 255));
		studentClassList.setLocation(2, 152);
		contentPane.add(studentClassList);
		
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
	
	public void joinClassCode(String classCode, String password) {
		CheckClassCode checkClass = new CheckClassCode();
		int classID = 0;
		String className = "";
		boolean check = false;
		
		try {
			check = checkClass.checkDatabase(classCode, password);
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
	
	
	public void addStudentToClass(String classCode,int classID,String className) {
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
