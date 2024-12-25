package studentAccount;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.formdev.flatlaf.FlatLightLaf;

import updateRes.LoadCreatedClass;


import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class StudentClass extends JPanel {

	private static final long serialVersionUID = 1L;
    private ArrayList<JCheckBox> checkBox_Class;
    private ArrayList<JButton> button_Class;
    private ArrayList<JLabel> labelCode_Class;
    private ArrayList<String> deleteList;
    private GridBagConstraints gbc;
    private JPanel panel;
    private int iJSrollPane = 0;
    private JButton removeClass;

    
    private ArrayList<String> studentClass;
    private LoadCreatedClass loadClass;
    
    private HashMap<String,String> classes;
    
    
	public StudentClass() {
		setBackground(new Color(255, 255, 255));
		
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
	
	    checkBox_Class = new ArrayList<>();
	    button_Class = new ArrayList<>();
	    labelCode_Class = new ArrayList<>();

	    deleteList = new ArrayList<>();

	   
	    loadClass = new LoadCreatedClass();
	    classes = new HashMap<>();

        setLayout(null); 
        setBounds(0, 100, 684, 264);


				
	    panel = new JPanel(new GridBagLayout());
	    panel.setBackground(new Color(255, 255, 255));
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(90, 53, 500, 200);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	
		add(scrollPane);

		
		removeClass = new JButton("Leave Course");
		removeClass.setBackground(new Color(255, 255, 51));
		removeClass.setForeground(new Color(0, 0, 0));
		removeClass.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
	
		removeClass.setBounds(90, 0, 112, 23);
		removeClass.setVisible(false);
		add(removeClass);

		gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        try {
			studentClass = loadClass.loadStudentClass(Student_UI.STUDENT_ID);
			classes = new HashMap<>(loadClass.getHashMap());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
        
        
        for(String c : studentClass) {
        	loadClass(c);
        }
        
        setVisible(false);
	}
	
	
	public void addClass(String classCode, String n) {
		JLabel classCode_label = new JLabel("Hello");
        JButton button = new JButton(n);
        JCheckBox box = new JCheckBox();
        box.setFocusable(false);
        box.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) {  
            	checkSelectedBox();
            	removeClass.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {  		          
   					 if(box.isSelected() && confirmChange()) { 
						 deleteClass(box,button,classCode_label);
						 leaveClass();
					 }
  	
        			}});

        }});
        
        button.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) {  
            	System.out.println("StudentClass:"+classCode);
            	Student_UI.switchToQuizPane(button.getText(),classCode);
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
	
	private void loadClass(String n) {
		JLabel classCode_label = new JLabel("Hello");
        JButton button = new JButton(n);
        JCheckBox box = new JCheckBox();
        box.setFocusable(false);
        box.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) {  
            	checkSelectedBox();
            	removeClass.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {  		          
        					 if(box.isSelected() && confirmChange()) { 
        						 deleteClass(box,button,classCode_label);
        						 leaveClass();
        					 }
  	
        			}});

        }});
        
        button.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) { 
            	Student_UI.switchToQuizPane(button.getText(),classes.get(button.getText()));
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
	
	private void checkSelectedBox() {
		for(JCheckBox b : checkBox_Class) {
			if(b.isSelected()) {
				removeClass.setVisible(true);
				return;
			}else
				removeClass.setVisible(false);
		}
	}
	

	
	private boolean confirmChange() {
        int response = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure to leave ?", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE 
        );
        
        if (response == JOptionPane.YES_OPTION) 
            return true;
        else
            return false;
        

	}
	
	private void leaveClass() {
		try {
			DeleteStudentClass.deleteStudentClass(deleteList, Student_UI.STUDENT_ID);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
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

	
}

