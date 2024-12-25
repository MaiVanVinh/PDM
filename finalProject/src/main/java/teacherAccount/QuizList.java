package teacherAccount;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import com.formdev.flatlaf.FlatLightLaf;

import updateRes.DeleteQuiz;
import updateRes.DisplayOrEditQuiz;
import updateRes.LoadQuiz;
import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;
import uploadQaA.MainQuiz;

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

public class QuizList extends JPanel {

	private static final long serialVersionUID = 1L;
    private ArrayList<JCheckBox> checkBox_Class;
    private ArrayList<JButton> button_Class;
    private ArrayList<JLabel> labelCode_Class;
    private ArrayList<String> deleteList;
    private GridBagConstraints gbc;
    private JPanel panel;
    private int iJSrollPane = 0;
    private JButton removeQuiz;
    private LoadQuiz l = new LoadQuiz(Teacher_Class.CLASSCODE);
    private ArrayList<MainQuiz> masterList;
    private ArrayList<String> quizList;
    
    private DeleteQuiz d;
    private DisplayOrEditQuiz displayOrEditQuiz;
    private Teacher_Class teacherClass;
    
	public QuizList(Teacher_Class teacherClass) {
		setBackground(new Color(255, 255, 255));
		
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.teacherClass = teacherClass;
	    checkBox_Class = new ArrayList<>();
	    button_Class = new ArrayList<>();
	    labelCode_Class = new ArrayList<>();
	    quizList = new ArrayList<>();
	    masterList = new ArrayList<>();
	    deleteList = new ArrayList<>();
        d = new DeleteQuiz();

        setLayout(null); 
        setBounds(0, 100, 684, 255);


				
				panel = new JPanel(new GridBagLayout());
				panel.setBackground(new Color(255, 255, 255));
		
				JScrollPane scrollPane = new JScrollPane(panel);
				scrollPane.setBounds(92, 27, 500, 200);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	
				add(scrollPane);

		
		removeQuiz = new JButton("Delete Exam");
		removeQuiz.setBackground(new Color(255, 255, 51));
		removeQuiz.setForeground(new Color(0, 0, 0));
		removeQuiz.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		removeQuiz.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
               d.getReady(deleteList, Teacher_Class.CLASSCODE);   
               try {
				d.deleteSQL();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}});
	
		removeQuiz.setBounds(90, 0, 112, 23);
		removeQuiz.setVisible(false);
		add(removeQuiz);

		gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        try {
			l.loadQuizName();
			l.loadQA();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        quizList   = l.getQuizName();
        masterList = l.getQuiz();
        
        for(int i = 0; i < quizList.size(); i++) {
        	loadQuiz(quizList.get(i));
        }
        
        setVisible(false);
	}
	
	
	public void addQuiz(String n) {
		JLabel classCode_label = new JLabel("Hello");
        JButton button = new JButton(n);
        JCheckBox box = new JCheckBox();
        
        box.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) {  
            	checkSelectedBox();
        		removeQuiz.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {  		          
        					 if(box.isSelected()) 
        						 deleteQuiz(box,button,classCode_label);
  	
        			}});

        }});
        
        button.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) {  
            	displayJFrame(button.getText()); 
            	displayQA(button.getText());
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
	
	private void loadQuiz(String n) {
		JLabel classCode_label = new JLabel("Hello");
        JButton button = new JButton(n);
        JCheckBox box = new JCheckBox();
        
        box.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) {  
            	checkSelectedBox();
        		removeQuiz.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {  		          
        					 if(box.isSelected()) 
        						 deleteQuiz(box,button,classCode_label);
  	
        			}});

        }});
        
        button.addActionListener(new ActionListener() {        
            public void actionPerformed(ActionEvent e) { 
            	displayJFrame(button.getText()); 
            	displayQA(button.getText());
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
	
	private void displayJFrame(String quizName) {
        teacherClass.setGlassPane(new JPanel() {
	    private static final long serialVersionUID = -5643729088768657875L;
        {
            setOpaque(false); 
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            addMouseListener(new java.awt.event.MouseAdapter() {}); 
        }});
        teacherClass.getGlassPane().setVisible(true);
    	displayOrEditQuiz = new DisplayOrEditQuiz(teacherClass,quizName);
    	displayOrEditQuiz.setVisible(true);
	}
	
	private void checkSelectedBox() {
		for(JCheckBox b : checkBox_Class) {
			if(b.isSelected()) {
				removeQuiz.setVisible(true);
				return;
			}else
				removeQuiz.setVisible(false);
		}
	}
	
	private void deleteQuiz(JCheckBox box,JButton button,JLabel label) {
		deleteList.add(button.getText());
		panel.remove(box);
		panel.remove(button);
		panel.remove(label);
		checkBox_Class.remove(box);
		button_Class.remove(button);
		labelCode_Class.remove(label);
		panel.revalidate();
		panel.repaint();
		removeQuiz.setVisible(false);
		
		
	}
	
	public void addMasterList(ArrayList<MainQuiz> masterList) {
		this.masterList = masterList;
	}
	
	private void displayQA(String name) {
        for(MainQuiz m : masterList) {
        	if(name.equals(m.getName())) {
        		for(MainQuestion q : m.getQuestions()) {
        			System.out.println(q.getQuestion());
        			for(MainAnswer a : q.getAns()) {
        				System.out.println(a.getOption());
        			}
        		}
        		
        	}
        }
		

	}


	
	
}

