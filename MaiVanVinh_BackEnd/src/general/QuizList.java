package general;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import updateRes.DeleteQuiz;
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

public class QuizList extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizList frame = new QuizList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
    private ArrayList<JCheckBox> checkBox_Class;
    private ArrayList<JButton> button_Class;
    private ArrayList<JLabel> labelCode_Class;
    private ArrayList<String> deleteList;
    private GridBagConstraints gbc;
    private JPanel panel;
    private int iJSrollPane = 0;
    private JButton removeQuiz;
    private LoadQuiz l = new LoadQuiz("ZYGVHGZH");
    private ArrayList<MainQuiz> masterList;
    private ArrayList<String> quizList;
    
    private DeleteQuiz d;
    
	public QuizList() {
		
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
	    checkBox_Class = new ArrayList<>();
	    button_Class = new ArrayList<>();
	    labelCode_Class = new ArrayList<>();
	    quizList = new ArrayList<>();
	    masterList = new ArrayList<>();
	    deleteList = new ArrayList<>();
        d = new DeleteQuiz();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel(new GridBagLayout());

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10, 144, 664, 233);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane);
		
		removeQuiz = new JButton("Delete Quiz");
		removeQuiz.setBounds(573, 107, 101, 32);
		removeQuiz.setVisible(false);
		contentPane.add(removeQuiz);
		
		JButton checkDetList = new JButton("Button");
		checkDetList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                   d.getReady(deleteList, "ZYGVHGZH");   
                   try {
					d.deleteSQL();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		checkDetList.setBounds(10, 110, 89, 23);
		contentPane.add(checkDetList);
		
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        try {
			l.loadQuizName();
			l.loadQA();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        quizList = l.getQuizName();
        masterList = l.getQuiz();
        for(int i = 0; i < quizList.size(); i++) {
        	addQuiz(quizList.get(i));
        }
	}
	
	
	private void addQuiz(String n) {
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
            	displayQA(button.getText());
//            	System.out.println(masterList.size());
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
