package studentAccount;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.formdev.flatlaf.FlatDarkLaf;
import updateRes.LoadQuiz;
import uploadQaA.MainQuiz;
import uploadQaA.UploadToDatabase;

import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Font;

public class ShowStudentQuiz extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<MainQuiz> studentQuizContent;
    private ArrayList<String> studentQuizName;
    private ArrayList<Integer> deadlineCheck;
    private GridBagConstraints gbc;
    private int iJSrollPane = 0;
    private JLabel nameOfClass;
    private LoadQuiz loadQuiz;
    private String courseCode;
    private JPanel panel;
    
    private JButton backPage;
    public static int CLASS_ID;
    private DoTheQuizPane doTheQuizPane;
   

    
	public ShowStudentQuiz(String code,String name) {
		
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		this.courseCode = code;
		studentQuizName = new ArrayList<>();
		studentQuizContent = new ArrayList<>();
		deadlineCheck = new ArrayList<>();
		
        setLayout(null); 
        setBounds(0, 100, 684, 264);
	
	    panel = new JPanel(new GridBagLayout());
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(90, 53, 500, 200);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	
		add(scrollPane);
		
		backPage = new JButton("Back");
		backPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Student_UI.switchBack();
			}
		});
		backPage.setFocusable(false);
		backPage.setBounds(87, 30, 112, 23);
		add(backPage);
		
		nameOfClass = new JLabel("");
		nameOfClass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nameOfClass.setBounds(277, 9, 89, 44);
		add(nameOfClass);



		gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        loadQuiz = new LoadQuiz(courseCode);
        try {
        	CLASS_ID = loadQuiz.getClassID(courseCode);
			loadQuiz.loadStudentQuizName();
			loadQuiz.loadStudentQuizContent();
			studentQuizName = new ArrayList<>(loadQuiz.getQuizName());
			studentQuizContent = new ArrayList<>(loadQuiz.getQuiz());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
        
           
        for(String s : studentQuizName) {
        	loadQuiz(s);
        	nameOfClass.setText("Class: "+name);
        }

        studentQuizContent.size();
        setVisible(false);
	}
	
	
	
	private void loadQuiz(String n) {
		JLabel classCode_label = new JLabel("");
        JButton button = new JButton(n);

        button.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) { 
            	if(checkValidDeadline(n))
            	   checkStudentDoTheQuiz(button.getText(),classCode_label);
            	else {
            	   classCode_label.setText("You miss this exam");
            	   handleExpireDeadline(n);
            	}
            	deadlineCheck.clear();
        }});

    	
        gbc.gridx = 0; 
        gbc.gridy = iJSrollPane++;
        
        gbc.gridx = 1; 
        panel.add(button, gbc);

        
        gbc.gridx = 2; 
        panel.add(classCode_label, gbc);

        panel.revalidate();
        panel.repaint();
	}
	
	private boolean checkValidDeadline(String examName) {
        for(MainQuiz q : studentQuizContent) {
        	if(examName.equals(q.getName())) {
        		deadlineCheck.add(q.getYear());
        		deadlineCheck.add(q.getMonth());
        		deadlineCheck.add(q.getDay());
        	}
        		
        }
		Calendar current = Calendar.getInstance();
        int currentDay = current.get(Calendar.DAY_OF_MONTH);
        int currentMonth = current.get(Calendar.MONTH) + 1;
        int currentYear = current.get(Calendar.YEAR);
  
        if(deadlineCheck.get(0) > currentYear) 
        	return true;
        if(deadlineCheck.get(1) > currentMonth && deadlineCheck.get(0) == currentYear)
        	return true;
        if(deadlineCheck.get(2) > currentDay && deadlineCheck.get(1) < currentMonth && deadlineCheck.get(0) == currentYear)
        	return true;
        return false;
	}
	
	private void handleExpireDeadline(String examName) {
        try {
			UploadToDatabase.uploadStudentQuizScore(0, examName,courseCode,1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void displayJFrame(String quizName,boolean isDone) {
		Student_UI.Studentframe.setGlassPane(new JPanel() {
	    private static final long serialVersionUID = -5643729088768657875L;
        {
            setOpaque(false); 
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            addMouseListener(new java.awt.event.MouseAdapter() {}); 
        }});
		Student_UI.Studentframe.getGlassPane().setVisible(true);
        doTheQuizPane = new DoTheQuizPane(isDone,courseCode,quizName);
        doTheQuizPane.setVisible(true);
	}
	
	private void checkStudentDoTheQuiz(String n,JLabel classCode_label) {
		int score = -1;
		try {
			score = CheckStudentDoTheQuiz.checkStudentQuiz(Student_UI.STUDENT_ID, CLASS_ID, n,courseCode);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		if(score < 0) {
    	    displayJFrame(n,false);
		}else {
			displayJFrame(n,true);
			classCode_label.setText("Done");
			JOptionPane.showMessageDialog(null, "You have already done this quiz \nYour score :"+score, "Warning!", JOptionPane.WARNING_MESSAGE);
		}
	}
	

		

	
}

