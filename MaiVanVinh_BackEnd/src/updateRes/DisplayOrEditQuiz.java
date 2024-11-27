package updateRes;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatDarkLaf;

import general.Teacher_Class;
import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;
import uploadQaA.MainQuiz;
import uploadQaA.UploadToDatabase;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;


public class DisplayOrEditQuiz extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton nextQuestion;
	private JButton previous;
	
	private JLayeredPane subLayer;
	private JLayeredPane generalLayerPane; 
	private int numOfQuestion = 0;
	private int numOfAnswer = 0;
	private int check = 0;
	
 
	private ArrayList<JLayeredPane> JLayeredPane_List;
	private ArrayList<Integer> numOfAnsPane;  
	private int numOfJLayeredPane = 0;
	private int currentPage = 0;
	
	private ArrayList<String> QaAList;
	private ArrayList<String> isCorrectList;
	
    private String questionName;
    private UploadToDatabase uploadToDatabase;
	private ArrayList<String> QuestionList;
	private ArrayList<String> AnswerList;

	private ArrayList<MainQuiz> masterList;
    private String quizName;
    private int quizID;
    
    private LoadQuiz l = new LoadQuiz(Teacher_Class.classCode);
    private JButton saveChange;

    private MainQuestion editQuestion;
    private ArrayList<MainQuestion> questionList;
    
    
    private boolean checkEmptyQuestion;
    private boolean checkAddNewQuestion;
    private ArrayList<Integer> addNewQuestionListIndex;
    
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					DisplayOrEditQuiz frame = new DisplayOrEditQuiz();
//					frame.setVisible(true);
//					frame.setLocationRelativeTo(null);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}


	
	
	public DisplayOrEditQuiz(Teacher_Class teacherClass,String quizName) {

		
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		this.quizName = quizName; 
		JLayeredPane_List = new ArrayList<>();
		QaAList = new ArrayList<>();
		isCorrectList = new ArrayList<>();
		masterList = new ArrayList<>();
		
//		editAnswerList = new ArrayList<>();
//		editQuestionList = new ArrayList<>();

		numOfAnsPane = new ArrayList<>();
		QuestionList = new ArrayList<>();
		AnswerList = new ArrayList<>();
		uploadToDatabase = new UploadToDatabase();

		
		editQuestion = new MainQuestion();
		questionList = new ArrayList<>();
		
		addNewQuestionListIndex = new ArrayList<>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		
		subLayer = new JLayeredPane();
		subLayer.setBackground(Color.DARK_GRAY);
		subLayer.setOpaque(true);
		subLayer.setBounds(0, 6, 684, 126);
		subLayer.setVisible(true);
		contentPane.add(subLayer);
		
		saveChange = new JButton("Save Change");
		saveChange.setBounds(293, 51, 104, 23);
		subLayer.add(saveChange);
		

		
		saveChange.setVisible(false);
		saveChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveChange.setVisible(false);
                if(checkEmptyQuestion)
                	JOptionPane.showMessageDialog(null, "Your question is empty !!", "Warning!", JOptionPane.WARNING_MESSAGE);		
                else if(checkAddNewQuestion){
                	checkAddNewQuestion = false;
                	saveChange.setVisible(false);
				    updateAddNew();
                }else
                	updateQuiz(false);
			}
		});
		
		
		generalLayerPane = new JLayeredPane();
		generalLayerPane.setBackground(Color.DARK_GRAY);
		generalLayerPane.setBounds(0, 131, 684, 255);
		generalLayerPane.setOpaque(true);
		generalLayerPane.setVisible(true);
		generalLayerPane.setLayout(null);
		contentPane.add(generalLayerPane);
		
//		quiz = new QuizList();
//		contentPane.add(quiz);
		


		
		nextQuestion = new JButton("Next ");
		nextQuestion.setFocusable(false);
		nextQuestion.setVisible(false);
		nextQuestion.setBounds(535, 387, 150, 23);
		nextQuestion.setFocusable(false);
		nextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saveChange.isVisible()) {
				   if(confirmChange())	{
					   changePage(2);
					   saveChange.setVisible(false);
				   }
					
				}else
				   changePage(2);
		}});

		
		
		previous = new JButton("Previous");
		previous.setFocusable(false);
		previous.setVisible(false);
		previous.setBounds(385, 387, 150, 23);
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saveChange.isVisible()) {
					   if(confirmChange())	{
						   changePage(1);
						   saveChange.setVisible(false);
					   }
						
				}else
					   changePage(1);
		}});
		contentPane.add(previous);
		contentPane.add(nextQuestion);
		
        try {
			l.loadQuizName();
			l.loadQA();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        masterList = l.getQuiz();
        loadQuiz();
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(WindowEvent e) {
                teacherClass.getGlassPane().setVisible(false);
                dispose(); 
            }
        });
		
	}

	
	private void changePage(int n) {
		if(n == 2) {
			previous.setVisible(true);
			currentPage++;
			
			chooseQuestion(n);
			numOfAnswer = numOfAnsPane.get(numOfJLayeredPane);

			if(numOfAnsPane.get(numOfJLayeredPane) == 0)
				   check = 0;
			else
				   check = 35 * numOfAnsPane.get(numOfJLayeredPane);


			if(currentPage == numOfQuestion)
				nextQuestion.setVisible(false);
		    else
		    	nextQuestion.setVisible(true);
			
			
		}else {
			nextQuestion.setVisible(true);
			chooseQuestion(n);
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
		}
		
	}
	
	private boolean confirmChange() {
        int response = JOptionPane.showConfirmDialog(
                null, 
                "Do you want to save your change?", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE 
        );
        
        if (response == JOptionPane.YES_OPTION) 
            return true;
        else
            return false;
        

	}
	

	
	private void loadQuiz() {
		quizName = "Vinh";
		for(MainQuiz q : masterList) {
			if(quizName.equals(q.getName())) {
				quizID = q.getID();
				for(MainQuestion question : q.getQuestions()) {
                    if(q.getQuestions().size() > 1)
                    	previous.setVisible(true);
					questionList.add(question);
					loadQuestion(question.getQuestion(),question.getAns());
					check = 0;
				}
			}
		}
		

	}
	
	
	private void loadQuestion(String questionName, ArrayList<MainAnswer> ans) {
		numOfJLayeredPane = currentPage;
		numOfAnsPane.add(ans.size());
		currentPage++;

//		quiz.setVisible(false);
		JLayeredPane quizPane = new JLayeredPane();
		quizPane.setBackground(Color.DARK_GRAY);
		quizPane.setBounds(10, 11, 664, 233);
		quizPane.setOpaque(true);
		quizPane.setVisible(true);
		generalLayerPane.add(quizPane);
		
		
		
		JTextField titleText = new JTextField(questionName);
		showSaveChangeButton(titleText,0);
		titleText.setBounds(68, 48, 292, 26);
		quizPane.add(titleText);
     	
     	JButton add = new JButton("Add");
     	add.setBounds(575, 11, 89, 23);
     	add.setFocusable(false);
     	
     	add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAddNewQuestion = true;
			    if(currentPage == (numOfQuestion)) {
			    	saveChange.setVisible(true);
			    	previous.setVisible(true);
				    if(!titleText.getText().equals("")) {	
				       numOfJLayeredPane = numOfQuestion;
				       numOfAnsPane.add(0);
				       numOfAnswer = 0;
				       check = 0;
				       addNewQuestionListIndex.add(numOfJLayeredPane);
				       addQuestion();
				    }else 
					   JOptionPane.showMessageDialog(null, "Your question is empty !!", "Warning!", JOptionPane.WARNING_MESSAGE);		
			    }else
			    	JOptionPane.showMessageDialog(null, "Move to the question "+(numOfQuestion-1), "Warning!", JOptionPane.WARNING_MESSAGE);	
			}});
     	quizPane.add(add);
     	
     	
     	

     	JButton delete = new JButton("Delete");
     	delete.setBounds(480, 11, 89, 23);
     	delete.setFocusable(false);
     	delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteQuestion(quizPane);
			}});
     	quizPane.add(delete);
     	
     	
     	

		JLabel question = new JLabel("Question "+(numOfQuestion+1));
		question.setFont(new Font("Tahoma", Font.PLAIN, 20));
		question.setForeground(Color.WHITE);
		question.setBounds(10, 11, 347, 20);
		quizPane.add(question);
		numOfQuestion++;
		
		JLabel title = new JLabel("Title");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 48, 54, 20);
		quizPane.add(title);
		

		JButton addAnswer = new JButton("Add Answer");
		addAnswer.setFocusable(false);
		addAnswer.setVisible(false);
		addAnswer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		addAnswer.setBounds(382, 48, 101, 26);
		quizPane.add(addAnswer);

		
		for(int i = 0; i < 4; i++) {
			int offSet = 0;
			if(ans.size() < 4)
				offSet = 4 - ans.size();
			if(i < 4 - offSet)
			   loadAnswer(quizPane,ans.get(i).getOption(),ans.get(i).isCorrect());
			else
			   loadAnswer(quizPane,"","Wrong");
		}


		
		JLayeredPane_List.add(quizPane);
		showLayeredPane(quizPane);

	}
	
	

	
	private void addQuestion() {
	
		currentPage++;
		

//		quiz.setVisible(false);
		JLayeredPane quizPane = new JLayeredPane();
		quizPane.setBackground(Color.DARK_GRAY);
		quizPane.setBounds(10, 11, 664, 233);
		quizPane.setOpaque(true);
		quizPane.setVisible(true);
		generalLayerPane.add(quizPane);
		
		
		
		JTextField titleText = new JTextField();
		titleText.setBounds(68, 48, 292, 26);
		quizPane.add(titleText);
     	
     	JButton add = new JButton("Add");
     	add.setBounds(575, 11, 89, 23);
     	add.setFocusable(false);
     	
     	JButton delete = new JButton("Delete");
     	delete.setBounds(480, 11, 89, 23);
     	delete.setFocusable(false);
     	quizPane.add(delete);

     	
     	add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	

				if(saveChange.isVisible() ) {
					JOptionPane.showMessageDialog(null, "Wanna save ?", "Warning!", JOptionPane.WARNING_MESSAGE);		
					return;
				}
				
				if(currentPage == (numOfQuestion)) {
				    if(!titleText.getText().equals("")) {
				       checkAddNewQuestion = true;
                       saveChange.setVisible(true);
				       numOfJLayeredPane = numOfQuestion;
				       numOfAnsPane.add(0);
				       numOfAnswer = 0;
				       check = 0;
				       addNewQuestionListIndex.add(numOfJLayeredPane);
				       addQuestion();
				    }else 
					   JOptionPane.showMessageDialog(null, "Your question is empty !!", "Warning!", JOptionPane.WARNING_MESSAGE);		
			    }else
			    	JOptionPane.showMessageDialog(null, "Move to the question "+(numOfQuestion), "Warning!", JOptionPane.WARNING_MESSAGE);	
			}});
     	quizPane.add(add);

		JLabel question = new JLabel("Question "+(numOfQuestion+1));
		question.setFont(new Font("Tahoma", Font.PLAIN, 20));
		question.setForeground(Color.WHITE);
		question.setBounds(10, 11, 347, 20);
		quizPane.add(question);
		numOfQuestion++;
		
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
				   loadAnswer(quizPane,"","Wrong");
				}
				if(numOfAnsPane.get(numOfJLayeredPane) > 3)
					addAnswer.setVisible(false);


			}});
		addAnswer.setBounds(382, 48, 101, 26);
		quizPane.add(addAnswer);


		JLayeredPane_List.add(quizPane);
		showLayeredPane(quizPane);
		
	}
	
	
	
	private void chooseQuestion(int i) {
		
        if (numOfJLayeredPane > 0 && i == 1) {
        	numOfJLayeredPane--;
            showLayeredPane(JLayeredPane_List.get(numOfJLayeredPane));  
        }else if(numOfJLayeredPane < (numOfQuestion) && i == 2) {
        	numOfJLayeredPane++;
            showLayeredPane(JLayeredPane_List.get(numOfJLayeredPane)); 
        }
          
        
	}
	
	private void showSaveChangeButton(JTextField t,int i) {

		
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
	        	saveChange.setVisible(true); 
	        	if(t.getText().isEmpty() && i == 0) 
	        		checkEmptyQuestion = true;
	        	else if(!t.getText().isEmpty() && i == 0)
	        		checkEmptyQuestion = false;

	        }

	    });
	}
	
	
    private void showLayeredPane(JLayeredPane pane) {
    	generalLayerPane.removeAll(); 
    	generalLayerPane.add(pane);  
    	generalLayerPane.revalidate();        
    	generalLayerPane.repaint();            
    }
    
    private JTextField createTextField(int yOffset,String n) {
        JTextField textField_2 = new JTextField(n);
        textField_2.setBounds(68, 85 + yOffset, 292, 26);
        return textField_2;
    }
    
	private void loadAnswer(JLayeredPane quizPane,String ans,String isCorrect) {
        

		
	    int originalYTextField = 85 + check;
	    int originalYSelectionButton = 87 + check;
	    int originalYDeleteCheck = 85 + check;

	    JTextField textField_2 = createTextField(check,ans);
	    showSaveChangeButton(textField_2,1);
	    
	    JButton selectionButton = new JButton(isCorrect);
	    selectionButton.setFocusable(false);
	    selectionButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    selectionButton.setBounds(382, originalYSelectionButton, 101, 26);
	    selectionButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	 saveChange.setVisible(true);
             if(selectionButton.getText().equals("Correct"))
                selectionButton.setText("Wrong");
             else
                selectionButton.setText("Correct");
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
	        	if(!textField_2.getText().isEmpty())
	        	   saveChange.setVisible(true);
	        	else
	        	   saveChange.setVisible(false);
	            if (!quizPane.isAncestorOf(textField_2)) {
	
	                JTextField newTextField = createTextField(check,ans);
	                showSaveChangeButton(newTextField,1);
	                newTextField.setBounds(68, originalYTextField, 292, 26);
	                
	
	                JCheckBox newDeleteCheck = new JCheckBox();
	                newDeleteCheck.setBounds(40, originalYDeleteCheck, 20, 26);
	                
	
	                JButton newSelectionButton = new JButton(isCorrect);
	                newSelectionButton.setFocusable(false);
	                newSelectionButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
	                newSelectionButton.setBounds(382, originalYSelectionButton, 101, 26);
	                
	                newSelectionButton.addActionListener(new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                    	saveChange.setVisible(true);
	                        if(newSelectionButton.getText().equals("Correct"))
	                            newSelectionButton.setText("Wrong");
	                        else
	                            newSelectionButton.setText("Correct");
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
                        if(!textField.getText().isEmpty())
                            saveChange.setVisible(true);
                        else
                        	saveChange.setVisible(false);
                        quizPane.remove(textField);
                        quizPane.remove(deleteCheck);
                        quizPane.remove(selectionButton);
                        quizPane.revalidate();
                        quizPane.repaint();
                        });
    }
	
	private void deleteQuestion(JLayeredPane pane) {
		if( (numOfQuestion-1) > 0 ) {
		updateQuiz(true);
		previous.doClick();
		
		try {
		    if (JLayeredPane_List.get(numOfJLayeredPane + 2) != null) {
		        nextQuestion.setVisible(true);
		    }
		} catch (IndexOutOfBoundsException e) {
		    nextQuestion.setVisible(false);
		}

		
		JLayeredPane_List.remove(numOfJLayeredPane+1);
		numOfQuestion--;
		generalLayerPane.remove(pane);
		generalLayerPane.revalidate();
		generalLayerPane.repaint();
		}else
			System.out.println("You are not allowed to delete the last question");
	
		
	}
	
	private void updateQuiz(boolean deleteQuestion) {
		int questionID =  questionList.get(numOfJLayeredPane).getID();
		ArrayList<Integer> questionIDList = new ArrayList<>();
		UpdateQuiz u = new UpdateQuiz();
		
		try {
			questionIDList = u.getQuestionID(quizID);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		
        for(int i = 0; i < questionIDList.size(); i++) {
        	if(i == numOfJLayeredPane)
        		questionID = questionIDList.get(i);
        }
        
        if(deleteQuestion) {
     	   DeleteQuiz d = new DeleteQuiz();
     	   try {
 			  d.deleteQuestion(questionID);
 		   }catch (ClassNotFoundException | SQLException e) {
 			  e.printStackTrace();
 		   }
        }else
		   showQuestion_Answer(questionID);
	}
	
	
	private void updateAddNew() {
		QaAList.clear();
		isCorrectList.clear();
		QuestionList.clear();
		AnswerList.clear();
		
	    int i = 0;
	    int index = 1; 
	    
	    ArrayList<MainQuestion> questions = new ArrayList<>();
	    ArrayList<MainAnswer> ans = new ArrayList<>();
	    MainQuestion q; 
	    
		JLayeredPane check = JLayeredPane_List.get(numOfJLayeredPane);
		for (Component comp : check.getComponents()) {	
			if(comp instanceof JTextField) {
				JTextField text = (JTextField) comp; 
					QaAList.add(text.getText()); 
		    }
				    getJButton(comp);
		}
		
		for(String list : QaAList) {	
	           if(i==0)
	        	   questionName = list;
	           else {
	        	   if(!list.isEmpty() && index < isCorrectList.size()) {
	        	      AnswerList.add(list);  
	        	      ans.add(new MainAnswer(list,isCorrectList.get(index)));
	        	   }index++;
	           }i++;	    
		 }
		

		q = new MainQuestion(questionName,new ArrayList<>(ans));
		questions.add(q);
		questionList.add(q);
		
        try {
			uploadToDatabase.uploadQuestion(quizID,Teacher_Class.classCode,"Vinh",questions);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
      
      
	}
	
	private void showQuestion_Answer(int questionID) {
	    int i = 0;
	    int index = 0; 
	    String questionName = null;
	    ArrayList<MainAnswer> ans = new ArrayList<>();   

	    
		QaAList.clear();
		isCorrectList.clear();
		QuestionList.clear();
		AnswerList.clear();
		

		JLayeredPane check = JLayeredPane_List.get(numOfJLayeredPane);
		for (Component comp : check.getComponents()) {	
			if(comp instanceof JTextField) {
				JTextField text = (JTextField) comp; 
					QaAList.add(text.getText()); 
		    }
				    getJButton(comp);
		}
		

	

	   
	   for(String list : QaAList) {	
           if(i==0)
        	   questionName = list;
           else {
        	   if(!list.isEmpty() && index < isCorrectList.size()) {
        	      AnswerList.add(list);
        	      ans.add(new MainAnswer(list,isCorrectList.get(index+1)));
        	   }index++;
        	   

           }i++;	    
	   }


       editQuestion.setQuestion(questionID, questionName, new ArrayList<>(ans));

       
	     UpdateQuiz u = new UpdateQuiz();
	     u.getReady(editQuestion, questionID);
	   
	       try {
		      try {
			    u.updateData();
		      }catch (SQLException e) {
			    e.printStackTrace();
		      }
	       }catch (ClassNotFoundException e) {
		      e.printStackTrace();
	       }u.clear();
       
		
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

