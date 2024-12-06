package updateRes;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatDarkLaf;

import teacherAccount.Teacher_Class;
import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;
import uploadQaA.MainQuiz;
import uploadQaA.UploadToDatabase;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JComboBox;


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
	private boolean checkTitleEmpty;
 
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
    
    private LoadQuiz l = new LoadQuiz(Teacher_Class.CLASSCODE);
    private JButton saveChange;

    private MainQuestion editQuestion;
    private ArrayList<MainQuestion> questionList;
    
    
    private boolean checkEmptyQuestion;
    private boolean checkAddNewQuestion;
    private ArrayList<Integer> addNewQuestionListIndex;
    private JLabel quizName_1;
    
    
    private JComboBox<String> duration;
    private String setExamDuration;
    private JButton durationButton; 
    private JComboBox<Integer> dayComboBox;
    private JComboBox<Integer> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JLabel quizName_2;
    private JButton saveDeadLine;


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
		saveChange.setBounds(10, 92, 104, 23);
		subLayer.add(saveChange);

		saveChange.setVisible(false);
		saveChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(durationButton.isVisible())
					durationButton.doClick();
                if(checkEmptyQuestion)
                	JOptionPane.showMessageDialog(null, "Your question is empty !!", "Warning!", JOptionPane.WARNING_MESSAGE);		
                else if(checkAddNewQuestion){
                	   if(!checkTitleEmpty) {
                	    checkAddNewQuestion = false;
                	    saveChange.setVisible(false);
				        updateAddNew();
                	   }
                }else {
                	saveChange.setVisible(false);
                	updateQuiz(false);
                }
			}
		});
		
		
		generalLayerPane = new JLayeredPane();
		generalLayerPane.setBackground(Color.DARK_GRAY);
		generalLayerPane.setBounds(0, 131, 684, 255);
		generalLayerPane.setOpaque(true);
		generalLayerPane.setVisible(true);
		generalLayerPane.setLayout(null);
		contentPane.add(generalLayerPane);
		
		dayComboBox = new JComboBox<>();
		dayComboBox.setBounds(472, 59, 49, 22);
		subLayer.add(dayComboBox);
		
		monthComboBox = new JComboBox<>();
		monthComboBox.setBounds(535, 59, 49, 22);
		subLayer.add(monthComboBox);
		
		yearComboBox = new JComboBox<>();		
		yearComboBox.setBounds(594, 59, 69, 22);
		subLayer.add(yearComboBox);
		
		monthComboBox.setModel(new DefaultComboBoxModel<>(new Integer[] {
			     1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
			}));

		dayComboBox.setModel(new DefaultComboBoxModel<>(new Integer[] {
			    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 
			    20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31
			}));

		yearComboBox.setModel(new DefaultComboBoxModel<>(new Integer[] {
			     2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 2033, 2034, 2035
			}));
		
		saveDeadLine = new JButton("Save DeadLine");
		saveDeadLine.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveDeadLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDeadLine();
			}
		});
		saveDeadLine.setVisible(false);
		saveDeadLine.setBounds(499, 92, 136, 23);
		subLayer.add(saveDeadLine);
		
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
		for(MainQuiz q : masterList) {
			if(quizName.equals(q.getName())) {
				quizID = q.getID();
				dayComboBox.setSelectedItem(q.getDay());
				monthComboBox.setSelectedItem(q.getMonth());
				yearComboBox.setSelectedItem(q.getYear());
				setExamDuration = q.getDuration();
				for(MainQuestion question : q.getQuestions()) {
                    if(q.getQuestions().size() > 1)
                    	previous.setVisible(true);
					questionList.add(question);
					loadQuestion(question.getQuestion(),question.getAns());
					check = 0;
				}
			}
		}
		checkChangeInDeadLine(yearComboBox);
		checkChangeInDeadLine(monthComboBox);
		checkChangeInDeadLine(dayComboBox);
		quizName_1 = new JLabel(quizName);
		quizName_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		quizName_1.setBounds(292, 0, 149, 39);
		subLayer.add(quizName_1);
		
		duration = new JComboBox<>();
		duration.setBounds(292, 59, 104, 22);
		duration.setModel(new DefaultComboBoxModel<String>(new String[] {"15 minutes", "30 minutes","45 minutes","60 minutes","90 minutes","120 minutes"}));
		duration.setSelectedItem(setExamDuration);
		subLayer.add(duration);
		
		durationButton = new JButton("Save Duration");
		durationButton.setBounds(292, 92, 104, 23);
		durationButton.setVisible(false); 
		subLayer.add(durationButton);
		
		comboBoxListener(duration,durationButton);

		quizName_2 = new JLabel("Dead Line");
		quizName_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		quizName_2.setBounds(472, 0, 149, 39);
		subLayer.add(quizName_2);


	}
	
	private void comboBoxListener(JComboBox<String> duration,JButton button) {
		
		duration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             
                if (!duration.getSelectedItem().equals(setExamDuration)) {
                    button.setVisible(true); 
                } else {
                    button.setVisible(false); 
                }
            }
        });
		
		button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setExamDuration = (String) duration.getSelectedItem();
                button.setVisible(false); 
                try {
					UpdateQuiz.updateDuration(quizID, setExamDuration);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
            }
        });
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
			   loadAnswer(quizPane,"","FALSE");
		}


		
		JLayeredPane_List.add(quizPane);
		showLayeredPane(quizPane);

	}
	
	

	
	private void addQuestion() {
	
		currentPage++;
		checkTitleEmpty = true;

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
		checkEmptyTitle(titleText);
     	
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
				   loadAnswer(quizPane,"","FALSE");
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
	
	private void checkChangeInDeadLine(JComboBox<Integer> c) {
		c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  saveDeadLine.setVisible(true);
            }
        });
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
	
	private void updateDeadLine() {
		int day = (int)dayComboBox.getSelectedItem();
		int month = (int)monthComboBox.getSelectedItem();
		int year = (int)yearComboBox.getSelectedItem();

		UpdateQuiz u = new UpdateQuiz();
		try {
			u.updateDeadLine(quizID, year, month, day);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveDeadLine.setVisible(false);
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
			uploadToDatabase.uploadQuestion(quizID,Teacher_Class.CLASSCODE,quizName,questions);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
      
      
	}
	
	private void showQuestion_Answer(int questionID) {
	    int i = 0;
	    int index = 1; 
	    String questionName = "";
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
        	   if(!list.isEmpty() && (index) < isCorrectList.size()) {
        	      AnswerList.add(list);
        	      ans.add(new MainAnswer(list,isCorrectList.get(index)));
        	   }index++;
        	   

           }i++;	 
           System.out.println("TEST:"+list);
	   }

//	   for(String s : isCorrectList) {
//		   System.out.println("TEST"+s);
//	   }

       editQuestion.setQuestion(questionID, questionName, new ArrayList<>(ans));


       
       
	     UpdateQuiz u = new UpdateQuiz();
	     u.getReady(editQuestion, questionID);
	   
	       try {
		      try {
			    u.updateData(questionName);
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
            
            if(text.getText().equals("TRUE") || text.getText().equals("FALSE")) 
				   isCorrectList.add(text.getText());
		}
    	
    }
}

