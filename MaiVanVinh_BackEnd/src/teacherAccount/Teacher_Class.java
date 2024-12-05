package teacherAccount;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatDarkLaf;

import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;
import uploadQaA.UploadToDatabase;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Teacher_Class extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton summitName; 
	private JButton nextQuestion;
	private JButton previous;
	private JLabel label; 
	
	private JLayeredPane subLayer;
	private JLayeredPane generalLayerPane; 
	private int numOfQuestion = 1;
	private int numOfAnswer = 0;
	private int check = 0;
	
 
	private ArrayList<JLayeredPane> JLayeredPane_List;
	private ArrayList<Integer> numOfAnsPane;  
	private int numOfJLayeredPane = 0;
	private int currentPage = 0;
	private JButton saveAndBack;
	
	private ArrayList<String> QaAList;
	private ArrayList<String> isCorrectList;
	
    private String questionName;
    private UploadToDatabase uploadToDatabase;
	private ArrayList<String> QuestionList;
	private ArrayList<String> AnswerList;
	
	private QuizList quiz;
	public static String classCode;
	
	private boolean checkTitleEmpty;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teacher_Class frame = new Teacher_Class();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	
	public Teacher_Class() {
        System.out.println(classCode);
		
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JLayeredPane_List = new ArrayList<>();
		QaAList = new ArrayList<>();
		isCorrectList = new ArrayList<>();
//		masterList = new ArrayList<>();

		numOfAnsPane = new ArrayList<>();
		QuestionList = new ArrayList<>();
		AnswerList = new ArrayList<>();
		uploadToDatabase = new UploadToDatabase();
		numOfAnsPane.add(0);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,450);
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
		

		label = new JLabel("Quiz Name");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 30));
		label.setBounds(75, 0, 200, 49);
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
		
		summitName = new JButton("Summit");
		summitName.setFocusable(false);
		summitName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  checkDuplicatedQuiz(textField.getText());
			}});
		
		summitName.setBounds(183, 54, 89, 27);
		subLayer.add(summitName);
		contentPane.add(quizCreation);

		JButton listQuiz = new JButton("Quiz List");
		listQuiz.setBounds(580, 70, 104, 48);
		listQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quiz.setVisible(true);
				generalLayerPane.setOpaque(false);
				generalLayerPane.setVisible(false);
                 
	    }});

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
				if(checkTitleEmpty)
					System.out.println("Empty Title");
				else
				    clear();			 
			}});
		saveAndBack.setBounds(0, 105, 150, 23);
		saveAndBack.setFocusable(false);
		saveAndBack.setVisible(false);
		contentPane.add(saveAndBack);

		quiz = new QuizList(this);
		contentPane.add(quiz);

		
		nextQuestion = new JButton("Next ");
		nextQuestion.setFocusable(false);
		nextQuestion.setVisible(false);
		nextQuestion.setBounds(535, 387, 150, 23);
		nextQuestion.setFocusable(false);
		nextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previous.setVisible(true);
				currentPage++;
				
				chooseQuestion(2);
				numOfAnswer = numOfAnsPane.get(numOfJLayeredPane);

				if(numOfAnsPane.get(numOfJLayeredPane) == 0)
					   check = 0;
				else
					   check = 35 * numOfAnsPane.get(numOfJLayeredPane);
				
				if(currentPage == numOfQuestion)
					nextQuestion.setVisible(false);
			    else
			    	nextQuestion.setVisible(true);
		}});

		
		
		previous = new JButton("Previous");
		previous.setFocusable(false);
		previous.setVisible(false);
		previous.setBounds(385, 387, 150, 23);
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextQuestion.setVisible(true);
				chooseQuestion(1);
				numOfAnswer = numOfAnsPane.get(numOfJLayeredPane);
				currentPage--;
				if(numOfAnsPane.get(numOfJLayeredPane) == 0)
				   check = 0;
				else
				   check = 35 * numOfAnsPane.get(numOfJLayeredPane);
					
				if(currentPage == 1)
				    previous.setVisible(false);
			    else
					previous.setVisible(true);
		}});
		contentPane.add(previous);
		contentPane.add(nextQuestion);
		setLocationRelativeTo(null);
		
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
	       addWindowListener(new WindowAdapter() {
	        	@Override
	            public void windowClosing(WindowEvent e) {
	                Teacher_UI.frame.setVisible(true);
	                dispose(); 
	            }
	        });

	}
	
	private void checkDuplicatedQuiz(String quizName) {
		try {
			if(!CheckDuplicatedQuiz.checkDuplicated(classCode, quizName)) {
			   if(!textField.getText().isEmpty()) {
				    questionName = textField.getText();
				    generalLayerPane.setVisible(true);
				    saveAndBack.setVisible(true);
				    summitName.setVisible(false);
				    textField.setVisible(false);					 
				    label.setText(questionName);
				    textField.setText(""); 
			        addQuestion();		     
			    }  
			}else
				JOptionPane.showMessageDialog(null, "Your quiz name is already existed", "Warning!", JOptionPane.WARNING_MESSAGE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	
	private void clear() {

		quiz.addQuiz(label.getText());

		
		addQuestion();
		showQuestion_Answer();
		
		check = 0;
		numOfAnswer = 0;
		currentPage = 0;
		numOfQuestion = 1;
		
		label.setText("Quiz Name");
		numOfJLayeredPane = 0;
		generalLayerPane.removeAll();
    	generalLayerPane.revalidate();        
    	generalLayerPane.repaint();  
		
		nextQuestion.setVisible(false);
		previous.setVisible(false);
		
		numOfAnsPane.clear();
		numOfAnsPane.add(0);
		JLayeredPane_List.clear();
		textField.setVisible(true);
		subLayer.setVisible(false);
		summitName.setVisible(true);
		saveAndBack.setVisible(false);
		generalLayerPane.setVisible(false);
		
	}
	
	
	
	
	
	private void addQuestion() {
		currentPage++;
		checkTitleEmpty = true;
		quiz.setVisible(false);
		JLayeredPane quizPane = new JLayeredPane();
		quizPane.setBackground(Color.DARK_GRAY);
		quizPane.setBounds(10, 11, 664, 233);
		quizPane.setOpaque(true);
		quizPane.setVisible(true);
		generalLayerPane.add(quizPane);
		
		
		
		JTextField titleText = new JTextField("");
		titleText.setBounds(68, 48, 292, 26);
		quizPane.add(titleText);
		checkEmptyTitle(titleText);

     	
     	JButton add = new JButton("Add");
     	add.setBounds(575, 11, 89, 23);
     	add.setFocusable(false);

     	
     	add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(!titleText.getText().equals("") && currentPage == numOfQuestion) {	
				   numOfQuestion++;
				   numOfJLayeredPane = numOfQuestion - 1;
				   numOfAnsPane.add(0);
				   numOfAnswer = 0;
				   check = 0;
				   addQuestion();
				}else {
					 JOptionPane.showMessageDialog(null, "Your question is empty !!", "Warning!", JOptionPane.WARNING_MESSAGE);		
				 }	
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
		

		

		JButton addAnswer = new JButton("Add Answer");
		addAnswer.setFocusable(false);
		addAnswer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		addAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfAnswer++;
				
				if(numOfAnsPane.get(numOfJLayeredPane) < 4) {
				   numOfAnsPane.set(numOfJLayeredPane, numOfAnswer);
				   addAnswer(quizPane);
				}
				if(numOfAnsPane.get(numOfJLayeredPane) > 3)
					addAnswer.setVisible(false);


			}});
		addAnswer.setBounds(382, 48, 101, 26);
		quizPane.add(addAnswer);
				
		if(currentPage == 1)
		   previous.setVisible(false);
		else
		   previous.setVisible(true);

		
		JLayeredPane_List.add(quizPane);
		showLayeredPane(quizPane);

	}
	
	private void checkEmptyTitle(JTextField t) {
		 t.getDocument().addDocumentListener(new DocumentListener() {
		        @Override
		        public void insertUpdate(DocumentEvent e) {
		        	checkEmpty();
		        }

		        @Override
		        public void removeUpdate(DocumentEvent e) {
		        	checkEmpty();
		        }

		        @Override
		        public void changedUpdate(DocumentEvent e) {
		        	 checkEmpty();
		        }
		        
		        private void checkEmpty() {
		        	if(t.getText().isEmpty()) 
		        		checkTitleEmpty = true;
		        	else 
		        		checkTitleEmpty = false;

		        }

		    });
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
    
    private JTextField createTextField(int yOffset) {
        JTextField textField_2 = new JTextField();
        textField_2.setBounds(68, 85 + yOffset, 292, 26);
        return textField_2;
    }
    
	private void addAnswer(JLayeredPane quizPane) {
        
	    int originalYTextField = 85 + check;
	    int originalYSelectionButton = 87 + check;
	    int originalYDeleteCheck = 85 + check;

	    JTextField textField_2 = createTextField(check);
	    
	    JButton selectionButton = new JButton("FALSE");
	    selectionButton.setFocusable(false);
	    selectionButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    selectionButton.setBounds(382, originalYSelectionButton, 101, 26);
	    selectionButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
             if(selectionButton.getText().equals("TRUE"))
                selectionButton.setText("FALSE");
             else
                selectionButton.setText("TRUE");
        }});
        
	    
	
	    JButton recreateButton = new JButton("Recreate");
	    recreateButton.setFocusable(false);
	    recreateButton.setVisible(false);
	    recreateButton.setBounds(500, originalYSelectionButton, 101, 26);
	    
	    JCheckBox deleteCheck = new JCheckBox();
	    deleteCheck.setFocusable(false);
	    deleteCheck.setBounds(40, originalYDeleteCheck, 20, 26);

	   
	    addDeleteFunctionality(recreateButton,textField_2, deleteCheck,
        selectionButton, originalYTextField, originalYSelectionButton,quizPane);

	    
	    recreateButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {

	            if (!quizPane.isAncestorOf(textField_2)) {
	
	                JTextField newTextField = createTextField(check);
	                newTextField.setBounds(68, originalYTextField, 292, 26);
	                
	
	                JCheckBox newDeleteCheck = new JCheckBox();
	                newDeleteCheck.setBounds(40, originalYDeleteCheck, 20, 26);
	                
	
	                JButton newSelectionButton = new JButton("FALSE");
	                newSelectionButton.setFocusable(false);
	                newSelectionButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
	                newSelectionButton.setBounds(382, originalYSelectionButton, 101, 26);
	                
	                newSelectionButton.addActionListener(new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                        if(newSelectionButton.getText().equals("TRUE"))
	                            newSelectionButton.setText("FALSE");
	                        else
	                            newSelectionButton.setText("TRUE");
	                    }
	                });
	                
	                addDeleteFunctionality(recreateButton,newTextField, newDeleteCheck, 
	                newSelectionButton, originalYTextField, originalYSelectionButton,quizPane);
	                
	                recreateButton.setVisible(false);

	                quizPane.add(newTextField);
	                quizPane.add(newDeleteCheck);
	                quizPane.add(newSelectionButton);
	                
	                quizPane.revalidate();
	                quizPane.repaint();
	            }
	        }
	    });


	    quizPane.add(textField_2);
	    quizPane.add(selectionButton);
	    quizPane.add(recreateButton);
	    quizPane.add(deleteCheck);

	    check += 35;
	}
	
	private void addDeleteFunctionality(JButton recreateButton,JTextField textField, 
			JCheckBox deleteCheck, 
            JButton selectionButton, 
            int originalYTextField, 
            int originalYSelectionButton,JLayeredPane quizPane) {
            deleteCheck.addActionListener(event -> {
                        recreateButton.setVisible(true);
                        quizPane.remove(textField);
                        quizPane.remove(deleteCheck);
                        quizPane.remove(selectionButton);
                        quizPane.revalidate();
                        quizPane.repaint();
                        });
    }
	
	private void showQuestion_Answer() {
	    int i = 0;
	    
		QaAList.clear();
		isCorrectList.clear();
		QuestionList.clear();
		AnswerList.clear();
		
		ArrayList<Integer> emptyList = new ArrayList<>();

		
		for(JLayeredPane check : JLayeredPane_List) {
			for (Component comp : check.getComponents()) {	
				if(comp instanceof JTextField) {
					JTextField text = (JTextField) comp; 
					   QaAList.add(text.getText()); 
				}
				    getJButton(comp);
			}
		}



	   
	   for(String list : QaAList) {	
		   
		   if(isCorrectList.get(i).equals("Title")) 
			  QuestionList.add(list);
		   
		   if(list.isEmpty() && !isCorrectList.get(i).equals("Title"))
		    	emptyList.add(i-1);
		   else{
			  if(!isCorrectList.get(i).equals("Title")) 
				  AnswerList.add(list);
		   }i++;		   	    
	   }

	   getQuestion_Answer(isCorrectList,emptyList);
 
	   
		
	}
	

	

	
    private void getQuestion_Answer(ArrayList<String> isCorrect, ArrayList<Integer> emptyList) {
    	
    	     int ansIndex = 0;
    	     int firstCheck = 1;
    	     
    	     int n = 0;
    	     int index = 1;
    	     int num = -1;
    	     
    	     if(emptyList.size() > 0) {
    	        num = emptyList.get(0);
    	     }
    	     

   		     ArrayList<MainQuestion> que = new ArrayList<>();    	 	 	 
    		 ArrayList<MainAnswer> ans = new ArrayList<>();  
    	     ArrayList<ArrayList<MainAnswer>> answers = new ArrayList<>();

    	     
    	     for(String option : isCorrect) { 	
    	    	 
                 if(!option.equals("Title") && (n != num) && ansIndex < AnswerList.size()) { 
                    ans.add(new MainAnswer(AnswerList.get(ansIndex++),option));
                 }else if(option.equals("Title") && firstCheck > 1) { 
                	answers.add(new ArrayList<>(ans));
                 	ans.clear(); 	
                    n--;
                 }

                if(n == num && index < emptyList.size())
                	num = emptyList.get(index++);
                n++;
                firstCheck++;  
                
                
    	     }
    	     

    	     
            for(int j  = 0; j < QuestionList.size() - 1; j++) {
            	if(!QuestionList.get(j).equals(""))
            	   que.add(new MainQuestion(QuestionList.get(j), answers.get(j)));
            }
            
            
             for(MainQuestion question : que) {
            	 System.out.println(question.getQuestion());
            	 for(MainAnswer anss : question.getAns()) {
                    System.out.println(anss.isCorrect());
            	 }
             }
             

//            masterList.add(new MainQuiz(label.getText(), new ArrayList<>(que)));
//            quiz.addMasterList(masterList);
            
            try {
				uploadToDatabase.pushData(classCode,questionName,que);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
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
               

			   if(text.getText().equals("TRUE") || text.getText().equals("FALSE")) 
				   isCorrectList.add(text.getText());
   	          
		}
    	
    }


}
