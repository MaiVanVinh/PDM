package general;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Test extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	
	private JLayeredPane subLayer;
	private JLayeredPane generalLayerPane; 
	private int numOfQuestion = 1;
	private int numOfAnswer = 0;
	private int check = 0;
	

	private  ArrayList<JLayeredPane> JLayeredPane_List;
	private int numOfJLayeredPane = 0;
	private JButton saveAndBack;
	
	private ArrayList<String> QaAList;
	private ArrayList<String> isCorrectList;
	
    private String questionName;
	private ArrayList<String> QuestionList;
	private ArrayList<String> AnswerList;
	


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teacher_Class frame = new Teacher_Class();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	
	
	public Test() {

		
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		 
		JLayeredPane_List = new ArrayList<>();
		QaAList = new ArrayList<>();
		isCorrectList = new ArrayList<>();
		
		

		QuestionList = new ArrayList<>();
		AnswerList = new ArrayList<>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		
		subLayer = new JLayeredPane();
		subLayer.setBackground(Color.DARK_GRAY);
		subLayer.setOpaque(true);
		subLayer.setBounds(183, 11, 294, 107);
		subLayer.setVisible(false);
		contentPane.add(subLayer);
		

		JLabel label = new JLabel("Quiz Name");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 30));
		label.setBounds(75, 0, 149, 49);
		subLayer.add(label);
		
		/*****************************************/
		textField = new JTextField();
		textField.setBounds(24, 54, 149, 27);
		subLayer.add(textField);
		/*****************************************/
		
		
		JButton quizCreation = new JButton("Create Quiz");
		quizCreation.setBounds(580, 11, 104, 48);
		quizCreation.setFocusable(false);
		quizCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subLayer.setVisible(true);
			}
		});
		
		JButton summitName = new JButton("Summit");
		summitName.setFocusable(false);
		summitName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().isEmpty()) {
					 questionName = textField.getText();
					 generalLayerPane.setVisible(true);
					 summitName.setVisible(false);
					 textField.setVisible(false);
					 textField.setText(""); 
				     addQuestion();		     
				}     
			}});
		System.out.println(questionName);
		
		summitName.setBounds(183, 54, 89, 27);
		subLayer.add(summitName);
		contentPane.add(quizCreation);

		JButton listQuiz = new JButton("Quiz List");
		listQuiz.setBounds(580, 70, 104, 48);
		listQuiz.setFocusable(false);
		contentPane.add(listQuiz);
		
		generalLayerPane = new JLayeredPane();
		generalLayerPane.setBackground(Color.DARK_GRAY);
		generalLayerPane.setBounds(0, 131, 684, 255);
		generalLayerPane.setOpaque(true);
		generalLayerPane.setVisible(true);
		generalLayerPane.setLayout(null);
		contentPane.add(generalLayerPane);
		
		saveAndBack = new JButton("Save Everything");
		saveAndBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfQuestion++;
				numOfJLayeredPane = numOfQuestion - 1;
				numOfAnswer = 0;
				check = 0;
				addQuestion();
				generalLayerPane.setVisible(false);
				showQuestion_Answer();
				 
			}
		});
		saveAndBack.setBounds(0, 105, 150, 23);
		saveAndBack.setFocusable(false);
		contentPane.add(saveAndBack);



	}
	
	
	
	
	
	private void addQuestion() {
		
		JLayeredPane quizPane = new JLayeredPane();
		quizPane.setBackground(Color.DARK_GRAY);
		quizPane.setBounds(10, 11, 664, 233);
		quizPane.setOpaque(true);
		quizPane.setVisible(true);
		generalLayerPane.add(quizPane);
     	
     	JButton add = new JButton("Add");
     	add.setBounds(575, 11, 89, 23);
     	add.setFocusable(false);
     	add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				numOfQuestion++;
				numOfJLayeredPane = numOfQuestion - 1;
				numOfAnswer = 0;
				check = 0;
				addQuestion();		
			}});
     	quizPane.add(add);

		JLabel question = new JLabel("Question "+numOfQuestion);
		question.setFont(new Font("Tahoma", Font.PLAIN, 20));
		question.setForeground(Color.WHITE);
		question.setBounds(10, 11, 347, 20);
		quizPane.add(question);
		
		
		JLabel title = new JLabel("Title");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 48, 54, 20);
		quizPane.add(title);
		
		
		JTextField titleText = new JTextField();
		titleText.setBounds(68, 48, 292, 26);
		quizPane.add(titleText);
		

		JButton addAnswer = new JButton("Add Answer");
		addAnswer.setFocusable(false);
		addAnswer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		addAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(numOfAnswer < 4)
				   addAnswer(quizPane);
				numOfAnswer++;
			}});
		addAnswer.setBounds(382, 48, 101, 26);
		quizPane.add(addAnswer);
		
		
		JButton nextQuestion = new JButton("Next ");
		nextQuestion.setFocusable(false);
		nextQuestion.setBounds(580, 210, 79, 23);
		nextQuestion.setFocusable(false);
		nextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				chooseQuestion(2);
			}});
		quizPane.add(nextQuestion);
		
		
		JButton previous = new JButton("Previous");
		previous.setFocusable(false);
		previous.setBounds(502, 210, 79, 23);
		previous.setFocusable(false);
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				chooseQuestion(1);
			}});
		quizPane.add(previous);	
		JLayeredPane_List.add(quizPane);
		showLayeredPane(quizPane);

	}
	
	private void chooseQuestion(int i) {
		
        if (numOfJLayeredPane > 0 && i == 1) {
        	numOfJLayeredPane--;
            showLayeredPane(JLayeredPane_List.get(numOfJLayeredPane));  
        }else if(numOfJLayeredPane < (numOfQuestion - 1) && i == 2) {
        	numOfJLayeredPane++;
            showLayeredPane(JLayeredPane_List.get(numOfJLayeredPane)); 
        }
        
        
        
	}
	
    private void showLayeredPane(JLayeredPane pane) {
    	generalLayerPane.removeAll(); 
    	generalLayerPane.add(pane);  
    	generalLayerPane.revalidate();        
    	generalLayerPane.repaint();            
    }
    
	private void addAnswer(JLayeredPane quizPane) {
		
		JTextField textField_2 = new JTextField();
		textField_2.setBounds(68, 85 + check, 292, 26);
		quizPane.add(textField_2);
		
        
		
		JButton selectionButton = new JButton("Wrong");
		selectionButton.setFocusable(false);
		selectionButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		selectionButton.setBounds(382, 87 + check, 101, 26);
		selectionButton.setFocusable(false);
		selectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectionButton.getText().equals("Correct"))
				   selectionButton.setText("Wrong");
				else
				   selectionButton.setText("Correct");
			}});
		quizPane.add(selectionButton);
		
		check += 35;
	}
	
	
	private void showQuestion_Answer() {
	    int i = 0;
		QaAList.clear();
		isCorrectList.clear();
		QuestionList.clear();
		AnswerList.clear();
		
		for(JLayeredPane check : JLayeredPane_List) {
			for (Component comp : check.getComponents()) {	
				if(comp instanceof JTextField) {
					JTextField text = (JTextField) comp;
					QaAList.add(text.getText());
				}getJButton(comp);
			}
		}

	   
	   for(String list : QaAList) {
		   if(isCorrectList.get(i).equals("Title")) 
			   QuestionList.add(list);
		   else
			   AnswerList.add(list);
		   i++;	    
	   }getQuestion_Answer(isCorrectList);

	   
		
	}
	

	

	
    private void getQuestion_Answer(ArrayList<String> isCorrect) {
    	
    	     int ansIndex = 0;
    	     int firstCheck = 1;

   		     ArrayList<MainQuestion> que = new ArrayList<>();    	 	 	 
    		 ArrayList<MainAnswer> ans = new ArrayList<>();  
    	     ArrayList<ArrayList<MainAnswer>> answers = new ArrayList<>();

    	     
    	     for(String option : isCorrect) { 	
    	    	 
                 if(!option.equals("Title")) {
                    ans.add(new MainAnswer(AnswerList.get(ansIndex++),option));
                 }else if(option.equals("Title") && firstCheck > 1) { 
                	answers.add(new ArrayList<>(ans));
                 	ans.clear(); 	    
                  }
                firstCheck++;  
                
    	     }
    	     
    	     
            for(int j  = 0; j < QuestionList.size() - 1; j++) {
            	que.add(new MainQuestion(QuestionList.get(j), answers.get(j)));
            }
    	
             for(MainQuestion question : que) {
            	 System.out.println(question.getQuestion());
            	 for(MainAnswer anss : question.getAns()) {
                    System.out.println(anss.getOption());
            	 }
             }
             
             que.clear();
             ans.clear();
             answers.clear();
    }
    
    private void getJButton(Component c) {
    	if(c instanceof JButton) {
			JButton text = (JButton) c;
               if(text.getText().equals("Add Answer")) 
				   isCorrectList.add("Title");
   	
			   if(text.getText().equals("Correct") || text.getText().equals("Wrong")) 
				   isCorrectList.add(text.getText());
			   
		}
    	
    }
	
	
}
