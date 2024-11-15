package loadRes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;

public class LoadQuiz {

	private String code;
	private ArrayList<MainQuestion> list;
	private ArrayList<MainAnswer> ans;
	private ArrayList<ArrayList<MainAnswer>> answers;

	
	public LoadQuiz(String c) {
		this.code = c;	
		answers = new ArrayList<>();
		list = new ArrayList<>();
		ans = new ArrayList<>();
	}
	
	public boolean loadQuiz() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String query = "SELECT quiz_id, class_code FROM test.quiz WHERE class_code = ?;";
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
	        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
              if (rs.next()) {
                int id = rs.getInt("quiz_id");
                questions(id);
              }else
            	  return false;

            	    
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; 
	}
	
	public void questions(int quizID) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String questionQuery = "SELECT question_id,question_text FROM test.question WHERE quiz_id = ?;";
		int i = 0;
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
		        PreparedStatement stmt = conn.prepareStatement(questionQuery)) {
	            stmt.setInt(1, quizID);
	            try (ResultSet rs = stmt.executeQuery()) {
	            	while(rs.next()) {
                       int questionID = rs.getInt("question_id");
                       answer(questionID,conn);   
                       list.add(new MainQuestion(rs.getString("question_text"),answers.get(i++)));
	            	} 
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
        for(MainQuestion question : list) {
       	   System.out.println(question.getQuestion());
       	     for(MainAnswer anss : question.getAns()) {
                System.out.println(anss.getOption());
       	     }  
        }
	
	}
	
	private void answer(int ID, Connection conn) throws SQLException {
		String answerQuery = "SELECT answer_text,is_correct FROM test.answer WHERE question_id = "+ID;
		ans.clear();
		PreparedStatement st = conn.prepareStatement(answerQuery); 
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			ans.add(new MainAnswer(rs.getString("answer_text"),rs.getString("is_correct")));
		}answers.add(new ArrayList<>(ans));

	}
	


	public String getCode() {
		return code;
	}
}
