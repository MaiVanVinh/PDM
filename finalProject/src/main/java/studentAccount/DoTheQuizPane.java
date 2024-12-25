package studentAccount;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import updateRes.LoadQuiz;
import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;
import uploadQaA.MainQuiz;
import updateRes.UploadToDatabase;

import com.formdev.flatlaf.FlatLightLaf;




import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;


public class DoTheQuizPane extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JButton nextQuestion;
	private JButton previous;
	
	private JLayeredPane subLayer;
	private JLayeredPane generalLayerPane; 
	private int numOfQuestion = 0;
	private int check = 0;
	
 
	private ArrayList<JLayeredPane> JLayeredPane_List;
	private ArrayList<Integer> numOfAnsPane;  
	private int numOfJLayeredPane = 0;
	private int currentPage = 0;

	private ArrayList<MainQuiz> masterList;
    

    
    private LoadQuiz loadQuiz;
    private ArrayList<MainQuestion> questionList;
    private List<String> correctAnswer;

    public static int QUIZ_ID;
    private String quizName;
    private String courseCode;
    private boolean isDone;
    private boolean isClosing;
    

    private HashMap<String, List<String>> questionAnswers;
    private HashMap<String, List<String>> test;
    private double scorePerQuestion;
    
    private JLabel quizName_1;
    private JLabel totalQuestion;
    private JButton submit;
    
    private int duration;
    private final int[] elapsedTime = {0};
    private Timer timer;
    private JLabel countDown;

    
	public DoTheQuizPane(boolean isDone,String classCode, String quizName) {
		setForeground(new Color(0, 0, 0));
		setType(Type.UTILITY);
		setTitle("EduExam");

		
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		this.quizName = quizName; 
		this.isDone = isDone;
        this.courseCode = classCode;
		JLayeredPane_List = new ArrayList<>();
		masterList = new ArrayList<>();

		numOfAnsPane = new ArrayList<>();

		questionAnswers = new HashMap<>();
		test = new HashMap<>();

		correctAnswer = new ArrayList<>();
		questionList = new ArrayList<>();
		
		
		setSize(1000,800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		
		subLayer = new JLayeredPane();
		subLayer.setBackground(new Color(255, 255, 255));
		
		subLayer.setOpaque(true);
		subLayer.setBounds(0, 6, 684, 126);
		subLayer.setVisible(true);
		contentPane.add(subLayer);
		

		generalLayerPane = new JLayeredPane();
		generalLayerPane.setBackground(new Color(255, 255, 255));
		generalLayerPane.setBounds(0, 131, 773, 298);
		generalLayerPane.setOpaque(true);
		generalLayerPane.setVisible(true);
		generalLayerPane.setLayout(null);
		contentPane.add(generalLayerPane);
		
		
		nextQuestion = new JButton("Next ");
		nextQuestion.setForeground(new Color(0, 0, 0));
		nextQuestion.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		nextQuestion.setBackground(new Color(255, 255, 51));
		nextQuestion.setFocusable(false);
		nextQuestion.setVisible(false);
		nextQuestion.setBounds(623, 440, 150, 23);
		nextQuestion.setFocusable(false);
		nextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   changePage(2);
		}});

		
		
		previous = new JButton("Previous");
		previous.setBackground(new Color(255, 255, 51));
		previous.setForeground(new Color(0, 0, 0));
		previous.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		previous.setFocusable(false);
		previous.setVisible(false);
		previous.setBounds(463, 440, 150, 23);
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePage(1);
		}});
		contentPane.add(previous);
		contentPane.add(nextQuestion);
        setResizable(false);
        setLocationRelativeTo(null);
		
		countDown = new JLabel();
		countDown.setForeground(new Color(0, 0, 0));
		countDown.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
		countDown.setBounds(285, 75, 230, 40);
		subLayer.add(countDown);
        
        loadQuiz = new LoadQuiz(classCode);
        try {
			loadQuiz.loadOnlyQuiz(quizName);
			masterList = new ArrayList<>(loadQuiz.getQuiz());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}


        loadQuiz();
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime[0]++; 
                if(elapsedTime[0] >= 60) {
                	elapsedTime[0] = 0;
                	duration--;
                }
                if(duration <= 0)
                	submit.doClick();
                int timeLeft = (duration);
                countDown.setText("Time Left: "+timeLeft+" minutes");
                System.out.println(elapsedTime[0]);
            }
        });
        timer.start();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);      
        setVisible(true);
        aniCheating();
	}
	
	private void aniCheating() {
		
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	if(submit.isVisible() && !isDone)
            		submit.doClick();
            	Student_UI.Studentframe.getGlassPane().setVisible(false);
        		timer.stop();
                dispose(); 
                isClosing = true; 
            }
        });
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!isClosing) { 
                    JOptionPane.showMessageDialog(null, "Cheating", "Warning!", JOptionPane.INFORMATION_MESSAGE);	
                	System.out.println("Cheating");
                }
            }
        });


	}
	
	
	private void loadQuiz() {
		int total = 0;
		int totalQ = 0;
		String d ="";
		for(MainQuiz q : masterList) {
			if(quizName.equals(q.getName())) {
				QUIZ_ID = q.getID();
				d = q.getDuration();
				for(MainQuestion question : q.getQuestions()) {
					totalQ = q.getQuestions().size();
					for(MainAnswer a : question.getAns()) {
						if(a.isCorrect().equals("TRUE")) {
							correctAnswer.add(a.getOption());
						    total++;
						}
					}
					test.put(question.getQuestion(), new ArrayList<>(correctAnswer));
					correctAnswer.clear();
					
                    if(q.getQuestions().size() > 1)
                    	previous.setVisible(true);
                    
					questionList.add(question);
					loadQuestion(question.getQuestion(),question.getAns());
					check = 0;
				}
			}
		}
		
		String[] parts = d.split(" ");
        duration = Integer.parseInt(parts[0]);
        countDown.setText("Time Left: "+duration+" minutes");
        
		scorePerQuestion = 100.0/total;
		quizName_1 = new JLabel("Exam Name: "+quizName);
		quizName_1.setForeground(new Color(0, 0, 0));
		quizName_1.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
		quizName_1.setBounds(285, 0, 300, 39);
		
		totalQuestion = new JLabel("Total Questions: "+totalQ);
		totalQuestion.setForeground(new Color(0, 0, 0));
		totalQuestion.setFont(new Font("Dubai Medium", Font.PLAIN, 22));
		totalQuestion.setBounds(285, 34, 300, 39);
		
		subLayer.add(quizName_1);
		subLayer.add(totalQuestion);
		
		submit = new JButton("Submit");
		submit.setBackground(new Color(255, 255, 51));
		submit.setForeground(new Color(0, 0, 0));
		submit.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTheResult(); 
				submit.setVisible(false);
			}
		});
		submit.setFocusable(false);
		submit.setBounds(0, 0, 89, 23);
		if(isDone)
			submit.setVisible(false);
		subLayer.add(submit);
		

		

	}
	
	private void changePage(int n) {
		if(n == 2) {
			previous.setVisible(true);
			currentPage++;
			
			chooseQuestion(n);

			if(currentPage == numOfQuestion)
				nextQuestion.setVisible(false);
		    else
		    	nextQuestion.setVisible(true);
			
			
		}else {
			nextQuestion.setVisible(true);
			chooseQuestion(n);
			currentPage--;
				
			if(currentPage == 1)
			    previous.setVisible(false);
		    else
				previous.setVisible(true);
		}
		
	}
	


	
	
	private void loadQuestion(String questionName, ArrayList<MainAnswer> ans) {
		numOfJLayeredPane = currentPage;
		numOfAnsPane.add(ans.size());
		currentPage++;
		
		questionAnswers.put(questionName, new ArrayList<>());
		
		JLayeredPane quizPane = new JLayeredPane();
		quizPane.setBackground(Color.WHITE);
		quizPane.setBounds(10, 11, 664, 233);
		quizPane.setOpaque(true);
		quizPane.setVisible(true);
		generalLayerPane.add(quizPane);
		
		
		
		JTextField titleText = new JTextField(questionName);
		titleText.setBounds(68, 48, 292, 26);
		titleText.setEditable(false);
		titleText.setFocusable(false);
		quizPane.add(titleText);
     	


		JLabel question = new JLabel("Question "+(numOfQuestion+1));
		question.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
		question.setForeground(Color.BLACK);
		question.setBounds(10, 8, 347, 20);
		quizPane.add(question);
		numOfQuestion++;
		
		JLabel title = new JLabel("Title");
		title.setForeground(Color.BLACK);
		title.setFont(new Font("Dubai Medium", Font.PLAIN, 24));
		title.setBounds(10, 48, 54, 20);
		quizPane.add(title);
		

		JButton addAnswer = new JButton("Add Answer");
		addAnswer.setFocusable(false);
		addAnswer.setVisible(false);
		addAnswer.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
		addAnswer.setBounds(382, 48, 101, 26);
		quizPane.add(addAnswer);

		
		if(ans.size() > 0) {
		
		   for(int i = 0; i < 4; i++) {
			   int offSet = 0;
			   if(ans.size() < 4)
				   offSet = 4 - ans.size();
			   if(i < 4 - offSet)
			      loadAnswer(quizPane,ans.get(i).getOption(),questionName,ans.get(i).isCorrect());
			   else
			      loadAnswer(quizPane,"",questionName,ans.get(i).isCorrect());
		   }
		}

		
		JLayeredPane_List.add(quizPane);
		showLayeredPane(quizPane);

	}
	

    
    private JTextField createTextField(int yOffset,String n) {
        JTextField textField_2 = new JTextField(n);
        textField_2.setBounds(68, 85 + yOffset, 292, 26);
        return textField_2;
    }
    
	private void loadAnswer(JLayeredPane quizPane,String ans,String questionName,String isCorrect) {

		int originalYSelection = 85 + check;
		int originalYSelectionButton = 87 + check;

	    JTextField textField = createTextField(check, ans);
	    textField.setEditable(false);
	    textField.setFocusable(false);
	    
	    if(isCorrect.equals("TRUE") && isDone) {
	       JButton correctButton = new JButton(isCorrect);
	       correctButton.setFocusable(false);
	       correctButton.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
	       correctButton.setBounds(382, originalYSelectionButton, 101, 26);
	       quizPane.add(correctButton);
	    }
	    
	    if(!isDone) {
	      JCheckBox select = new JCheckBox();
	      select.setFocusable(false);
	      select.setBounds(40, originalYSelection, 20, 26);

	   
	      select.addItemListener(e -> {
	          List<String> selectedAnswers = questionAnswers.get(questionName);
	          if (e.getStateChange() == ItemEvent.SELECTED) {
	              selectedAnswers.add(ans); 
	          } else {
	              selectedAnswers.remove(ans); 
	          }
	      });
	      quizPane.add(select);
	    }
	    
	    quizPane.add(textField);	   
	    check += 35;
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
	

    private void showLayeredPane(JLayeredPane pane) {
    	generalLayerPane.removeAll(); 
    	generalLayerPane.add(pane);  
    	generalLayerPane.revalidate();        
    	generalLayerPane.repaint();            
    }
    
    private void printTheResult() {
    	int score = 0;
    	int result = 0;
        for (HashMap.Entry<String, List<String>> entry : questionAnswers.entrySet()) {
            String question = entry.getKey();
            List<String> answers = entry.getValue();
            
            if(answers.size() <= test.get(question).size()) {
               List<String> correct = test.get(question);
               List<String> temp = new ArrayList<>();
               for (String answer : correct) {
            	   temp.add(answer);     
               }

               for(String s : answers) {
            	   if(temp.contains(s))
            		 score += 1;
               }
               

               
            }   
        }
        
        result = (int)(scorePerQuestion * score);
        JOptionPane.showMessageDialog(null, "Your score is "+result, "Announcement!", JOptionPane.INFORMATION_MESSAGE);	
        submit.setVisible(false);
        try {
			UploadToDatabase.uploadStudentQuizScore(result, quizName,courseCode,0);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}timer.stop();
    }
}

