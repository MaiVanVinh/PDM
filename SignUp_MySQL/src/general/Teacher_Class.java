package general;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

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

public class Teacher_Class extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	
	private JLayeredPane subLayer;
	private JLayeredPane generalLayerPane; 
	private int numOfQuestion = 1;
	private int numOfAnswer = 0;
	private int check = 0;
	

	private ArrayList<JLayeredPane> list;
	private int numOfJLayeredPane = 0;
	private JButton test;

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


	
	
	public Teacher_Class() {

		
		 try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		 
		list = new ArrayList<>();
		
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
		
		textField = new JTextField();
		textField.setBounds(24, 54, 149, 27);
		subLayer.add(textField);
		
		JButton quizCreation = new JButton("Create Quiz");
		quizCreation.setBounds(580, 11, 104, 48);
		quizCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subLayer.setVisible(true);
			}
		});
		
		JButton summitName = new JButton("Summit");
		summitName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addQuestion();
			}});
		
		summitName.setBounds(183, 54, 89, 27);
		subLayer.add(summitName);
		contentPane.add(quizCreation);

		JButton listQuiz = new JButton("Quiz List");
		listQuiz.setBounds(580, 70, 104, 48);
		contentPane.add(listQuiz);
		
		generalLayerPane = new JLayeredPane();
		generalLayerPane.setBackground(Color.DARK_GRAY);
		generalLayerPane.setBounds(0, 131, 684, 255);
		generalLayerPane.setOpaque(true);
		generalLayerPane.setVisible(true);
		generalLayerPane.setLayout(null);
		contentPane.add(generalLayerPane);
		
		test = new JButton("test");
		test.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showQuestion_Answer();
			}
		});
		test.setBounds(10, 69, 89, 23);
		contentPane.add(test);



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
		nextQuestion.setBounds(580, 210, 79, 23);
		nextQuestion.setFocusable(false);
		nextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				chooseQuestion(2);
			}});
		quizPane.add(nextQuestion);
		
		
		JButton previous = new JButton("Previous");
		previous.setBounds(502, 210, 79, 23);
		previous.setFocusable(false);
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				chooseQuestion(1);
			}});
		quizPane.add(previous);	
		list.add(quizPane);
		list.add(null);
		showLayeredPane(quizPane);

	}
	
	private void chooseQuestion(int i) {
		
        if (numOfJLayeredPane > 0 && i == 1) {
        	numOfJLayeredPane--;
            showLayeredPane(list.get(numOfJLayeredPane));  
        }else if(numOfJLayeredPane < (numOfQuestion - 1) && i == 2) {
        	numOfJLayeredPane++;
            showLayeredPane(list.get(numOfJLayeredPane)); 
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
		
        
		
		JButton selectionButton = new JButton("Correct");
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
//		for(JLayeredPane check : list) {
//			for (Component comp : check.getComponents()) {
//				if(comp instanceof JTextField) {
//					JTextField text = (JTextField) comp;
//                    System.out.println(text.getText());
//				}
//			}
//		}
		
		
		
		for(JLayeredPane check : list) {
			for (Component comp : check.getComponents()) {
				if(comp instanceof JButton) {
					JButton text = (JButton) comp;
					if(text.getText().equals("Correct") || text.getText().equals("Wrong"))
                        System.out.println(text.getText());
				}
			}
		}
	}
	
	
	
	
}
