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

//    private Teacher_Class teacherQuiz;

	public Teacher_UI(SignIn_Window signin) {
		
		 try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		 
		 
		frame = new JFrame();
		
//		teacherQuiz = new Teacher_Class();
		
	    checkBox_Class = new ArrayList<>();
	    button_Class = new ArrayList<>();
	    labelCode_Class = new ArrayList<>();
	    deleteList = new ArrayList<>();
	    
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
//		contentPane.add(teacherQuiz);
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
		
		
		teacher_class = new JLabel("Your class");
		teacher_class.setBounds(10, 86, 157, 30);
		contentPane.add(teacher_class);
		
		createClass = new JButton("Create class");
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
		
		removeClass = new JButton("Delete Class");
		removeClass.setVisible(false);

		removeClass.setBounds(517, 78, 157, 38);
		contentPane.add(removeClass);

	}
	
	
	private void summitAction() {

		
	try {
		    
			if(!checkDuplicateClass(textField.getText()))
				JOptionPane.showMessageDialog(null, "Your class name is invalid", "Warning!", JOptionPane.WARNING_MESSAGE);
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
                       System.out.println(button.getText());
//                       openTeacherClass();
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
                       System.out.println(button.getText());
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
		StringBuilder sql = new StringBuilder("delete from class where class_name in (");
		
        for(int i = 0; i < deleteList.size(); i++) {
        	sql.append("'"+deleteList.get(i)+"',");
        	
        }
        sql.append("'')"+"and teacher_id = "+teacherID);

		
		
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
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
	

//   private void openTeacherClass() {
//	    contentPane.setVisible(false);	
//		teacherQuiz.setVisible(true);
//		frame.setContentPane(teacherQuiz);
//   }
//   
   public static void returnPage(){
	   contentPane.setVisible(true);
	   frame.setContentPane(contentPane);
   }
	
	
}
