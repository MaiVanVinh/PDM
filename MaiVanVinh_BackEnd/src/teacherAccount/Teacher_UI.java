package teacherAccount;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatDarkLaf;

import connectionSQL.MyConnection;
import general.MainMenu;
import general.SignIn_Window;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    
    
    public  static JFrame frame;
	private static JPanel contentPane;
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
    private JButton removeClass;
    private JButton createClass;

    
    private ArrayList<JCheckBox> checkBox_Class;
    private ArrayList<JButton> button_Class;
    private ArrayList<JLabel> labelCode_Class;
    private ArrayList<String> deleteList;
    private int numCheckBox = 0;

    private Teacher_Class teacherQuiz;
    private String semester;
    private JButton studentList;

	public Teacher_UI(SignIn_Window signin,MainMenu mainmenu) {
		
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		 
		 
		frame = new JFrame();

	    checkBox_Class = new ArrayList<>();
	    button_Class = new ArrayList<>();
	    labelCode_Class = new ArrayList<>();
	    deleteList = new ArrayList<>();
	    
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);


		gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
		
		panel = new JPanel(new GridBagLayout());
		scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10, 127, 664, 248);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		frame.getContentPane().add(scrollPane);
		
		
		
		createClass_panel = new JLayeredPane();
		createClass_panel.setBackground(Color.DARK_GRAY);
		createClass_panel.setBounds(154, 11, 353, 105);
		createClass_panel.setOpaque(true);
		createClass_panel.setVisible(false);
		contentPane.add(createClass_panel);
		
		
		teacher_class = new JLabel("Your course");
		teacher_class.setBounds(10, 97, 157, 30);
		contentPane.add(teacher_class);
		
		createClass = new JButton("Create course");
		createClass.setFocusable(false);
		createClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewClassButton();
			}
		});
		createClass.setBounds(517, 11, 157, 47);
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
		
		
		name_CreateClass = new JLabel("Course Name");
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
		
		removeClass = new JButton("Delete Course");
		removeClass.setVisible(false);

		removeClass.setBounds(517, 78, 157, 38);
		contentPane.add(removeClass);
		
		studentList = new JButton("Student List");
		studentList.setFocusable(false);
		studentList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayStudentList();
			}
		});
		studentList.setBounds(10, 48, 145, 38);
		contentPane.add(studentList);
		
		JButton logOut = new JButton("Log out");
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				frame = null;
				mainmenu.setVisible(true);
				mainmenu.reset(0);
			}
		});
		logOut.setBounds(10, 11, 89, 23);
		contentPane.add(logOut);

	}
	
	private void displayStudentList() {
	    frame.setGlassPane(new JPanel() {
	   	private static final long serialVersionUID = -5643729088768657875L;
	    {
	     setOpaque(false); 
	     setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
	     addMouseListener(new java.awt.event.MouseAdapter() {}); 
	     }});
	    frame.getGlassPane().setVisible(true);
		StudentTableList studentListFrame = new StudentTableList();
		studentListFrame.setVisible(true);
		
	}
	

	
	private void summitAction() {

		
	try {
		    
			if(!checkDuplicateClass(textField.getText()))
				JOptionPane.showMessageDialog(null, "Your course name is invalid", "Warning!", JOptionPane.WARNING_MESSAGE);
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
        	
            JLabel classCode_label = new JLabel("Code: "+uniqueClassCode+"     Password: "+password);
            JButton button = new JButton(textField.getText());
            JCheckBox box = new JCheckBox();
            
            
            box.addActionListener(new ActionListener() {        
                public void actionPerformed(ActionEvent e) {  
                	if(box.isSelected()) {
                		removeClass.setVisible(true);
                		numCheckBox++;
                	}else {
                		removeClass.setVisible(false);
                		numCheckBox--;
                	}
            		removeClass.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent e) {
            				deleteClass(box,button,classCode_label);
            				countDown();
            			}});

            }});
            
            button.addActionListener(new ActionListener() {        
                public void actionPerformed(ActionEvent e) {  
                       Teacher_Class.CLASSCODE = uniqueClassCode;
               		   teacherQuiz = new Teacher_Class();
                       teacherQuiz.setVisible(true);
                       frame.setVisible(false);
            }});
            
            checkBox_Class.add(box);
            button_Class.add(button);
            labelCode_Class.add(classCode_label);
        	
            gbc.gridx = 0; 
            gbc.gridy = iJSrollPane++;
            panel.add(box, gbc);
            
            gbc.gridx = 1; 
            panel.add(button, gbc);

            
            gbc.gridx = 2; 
            panel.add(classCode_label, gbc);

            panel.revalidate();
            panel.repaint();
            
            
        
		
	}
	

	
	private void countDown() {
	    if(numCheckBox == deleteList.size()) {
			try {
				deleteDataSQL();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    }
	}
	
	
	public void initializeClass() {
		
        int j = 0;
        for (int i = 0; i < (componentClass / 3); i++) {
        	
        	String classCode = teacherOwnClass.get(j++);
        	String className = teacherOwnClass.get(j++);
        	String classPass = teacherOwnClass.get(j++);
        	
            JLabel classCode_label = new JLabel("Code: "+classCode);
            JButton button = new JButton(className);
            JCheckBox box = new JCheckBox();
            box.addActionListener(new ActionListener() {        
                public void actionPerformed(ActionEvent e) {  
                	if(box.isSelected()) {
                		removeClass.setVisible(true);
                		numCheckBox++;
                	}else {
                		removeClass.setVisible(false);
                		numCheckBox--;
                	}
            		removeClass.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent e) {
            				deleteClass(box,button,classCode_label);
            				countDown();
            			}});

            }});
            
            button.addActionListener(new ActionListener() {        
                public void actionPerformed(ActionEvent e) {  
                    Teacher_Class.CLASSCODE = classCode;
            		teacherQuiz = new Teacher_Class();
                    teacherQuiz.setVisible(true);
                    frame.setVisible(false);
            }});
            
            checkBox_Class.add(box);
            button_Class.add(button);
            labelCode_Class.add(classCode_label);
        	
        	if(classPass == null) 
        		classCode_label.setText("Code: "+classCode+"     Password: ");
        	else 
        		classCode_label.setText("Code: "+classCode+"     Password: "+classPass);

            gbc.gridx = 0; 
            gbc.gridy = iJSrollPane++;
            panel.add(box, gbc);
            
            gbc.gridx = 1;         
            panel.add(button, gbc);

            
            gbc.gridx = 2; 
            panel.add(classCode_label, gbc);



        }
        
        panel.revalidate();
        panel.repaint();
        
        
        
	}
	
	
	
	private void deleteClass(JCheckBox box,JButton button,JLabel label) {
		deleteList.add(button.getText());
		panel.remove(box);
		panel.remove(button);
		panel.remove(label);
		checkBox_Class.remove(box);
		button_Class.remove(button);
		labelCode_Class.remove(label);
		panel.revalidate();
		panel.repaint();
		removeClass.setVisible(false);
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
	
	private int autoCalculateAcademicYear() {
        LocalDate currentDate = LocalDate.now();
        semester = "";

        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        
        if( (month >= 9 && month <=12) || month == 1 ) 
        	semester = "Semester 1";
        else if(month >= 2 && month <= 6) 
        	semester = "Semester 2";
        else
        	semester = "Semester 3";
        
        return year;
	}
	
	
	private void pushInfotoSQL() throws ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		char[] pass = passwordField.getPassword();
		String nameClass = new String(textField.getText());
		String password  = new String(pass);
		String sql;
		int academicYear = autoCalculateAcademicYear();
		
		if(!password.equals(null)) {
		    sql = "INSERT INTO course(course_code,course_name,pass_word,teacher_id,academic_year,semester) VALUES (?,?,?,?,?,?)";
		}else {
		    sql = "INSERT INTO course(course_code,course_name,teacher_id,academic_year,semester) VALUES (?,?,?,?,?)";
		}
		

		
		
        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
    		     
        	           if(password.equals(null)) {
		                    ps.setString(1, uniqueClassCode);
		                    ps.setString(2, nameClass);
		                    ps.setString(3, teacherID);
		                    ps.setInt(4, academicYear);
		                    ps.setString(5, semester);
       	               }else {
		                    ps.setString(1, uniqueClassCode);
		                    ps.setString(2, nameClass);
		                    ps.setString(3, password);
		                    ps.setString(4, teacherID);
		                    ps.setInt(5, academicYear);
		                    ps.setString(6, semester);
       	               }
        	     ps.executeUpdate(); 
			     ps.close();
			     conn.close(); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
		
		textField.setVisible(false);
		showPass.setVisible(false);
		createClass_panel.setVisible(false);
		name_CreateClass.setVisible(false);
		textField.setVisible(false);
		passwordField.setVisible(false);
		pass_CreatClass.setVisible(false);
		
	}
	
	private void deleteDataSQL() throws ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		StringBuilder sql = new StringBuilder("delete from course where course_name in (");
		
        for(int i = 0; i < deleteList.size(); i++) {
        	sql.append("'"+deleteList.get(i)+"',");
        	
        }
        sql.append("'')"+"and teacher_id = "+teacherID);

		
		
        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        	     ps.executeUpdate(); 
			     ps.close();
			     conn.close(); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
        System.out.println(sql.toString());
        numCheckBox = 0;
        deleteList.clear();
	}
	
	private boolean checkUniqueCode(String codetoCheck) throws ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String sql = "SELECT course_code FROM test.course WHERE course_code = ?;";

		
		
        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
        	     ps.setString(1, codetoCheck);
	             ResultSet rs = ps.executeQuery();

	             if (rs.next()) { 	    
	            	 String classCode = rs.getString("course_code");
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
	
	
	private boolean checkDuplicateClass(String name) {
		
		if(textField.getText().equals(""))
			return false;
		
		int j = 1;
		for(int i = 0; i < teacherOwnClass.size() / 3; i++) {
			if(teacherOwnClass.get(j).equals(name)) 
				return false;	
			j += 3;
		}return true;
		
	}
	

   
   public static void returnPage(){
	   contentPane.setVisible(true);
	   frame.setContentPane(contentPane);
   }
}

