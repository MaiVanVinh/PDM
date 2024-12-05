package updateRes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionSQL.MyConnection;
import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;
import uploadQaA.MainQuiz;

public class LoadQuiz {

	private String code;
	private ArrayList<String> quizName;
	private ArrayList<MainQuiz> masterList;
	private ArrayList<MainQuestion> list;
	private ArrayList<MainAnswer> ans;
	private ArrayList<ArrayList<MainAnswer>> answers;
	

	
	public LoadQuiz(String c) {
		this.code = c;	
		masterList = new ArrayList<>();
		answers = new ArrayList<>();
		list = new ArrayList<>();
		ans = new ArrayList<>();
		quizName = new ArrayList<>();
	}
	
	
	public void loadQuizName() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String query = "SELECT quiz_id,title FROM test.quiz WHERE class_code = ?;";
		
		try (Connection conn = MyConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
              while (rs.next()) {
            	  quizName.add(rs.getString("title"));

              }
    
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


	}
	
	public void loadOnlyQuiz(String title) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String query = "SELECT title,quiz_id, class_code FROM test.quiz WHERE title = '"+title+"' and class_code = ?;";

		try (Connection conn = MyConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                int id = rs.getInt("quiz_id");
                questions(id);
                masterList.add(new MainQuiz(rs.getInt("quiz_id"),rs.getString("title"),new ArrayList<>(list)));
                list.clear();
              }

            	    
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	
	}
	
	
	public void loadQA() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String query = "SELECT title, quiz_id, class_code FROM test.quiz WHERE class_code = ?;";
		
		try (Connection conn = MyConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
              while (rs.next()) {
                int id = rs.getInt("quiz_id");
                questions(id);
                masterList.add(new MainQuiz(rs.getInt("quiz_id"),rs.getString("title"),new ArrayList<>(list)));
                list.clear();
              }

            	    
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		

	}
	
	public void questions(int quizID) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String questionQuery = "SELECT question_id,question_text FROM test.question WHERE quiz_id = ?;";
		int i = 0;
		
		try (Connection conn = MyConnection.getConnection();
		        PreparedStatement stmt = conn.prepareStatement(questionQuery)) {
	            stmt.setInt(1, quizID);
	            try (ResultSet rs = stmt.executeQuery()) {
	            	while(rs.next()) {
                       int questionID = rs.getInt("question_id");
                       answer(questionID,conn);   
                       list.add(new MainQuestion(rs.getInt("question_id"),rs.getString("question_text"),answers.get(i++)));
	            	} 
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
        answers.clear();
	
	}
	
	private void answer(int ID, Connection conn) throws SQLException {
		String answerQuery = "SELECT answer_id, answer_text,is_correct FROM test.answer WHERE question_id = "+ID;
		ans.clear();
		String isCorrect = "";
		
		PreparedStatement st = conn.prepareStatement(answerQuery); 
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			if(rs.getBoolean("is_correct"))
				isCorrect = "TRUE";
			else
				isCorrect = "FALSE";
			ans.add(new MainAnswer(rs.getInt("answer_id"),rs.getString("answer_text"),isCorrect));
		}answers.add(new ArrayList<>(ans));

	}
	

	public void loadStudentQuizName() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String query = "SELECT quiz_id,title FROM test.quiz WHERE class_code = ?;";
		
		try (Connection conn = MyConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.code);
            try (ResultSet rs = stmt.executeQuery()) {
              while (rs.next()) {
            	  quizName.add(rs.getString("title"));

              }
    
            }
        } catch (SQLException e) {
            e.printStackTrace();
       }
	}
	
	public void loadStudentQuizContent() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "Select quiz_id, title from test.quiz where class_code = '"+code+"'";
		
		try (Connection conn = MyConnection.getConnection();
		        PreparedStatement stmt = conn.prepareStatement(sql)) {
	            try (ResultSet rs = stmt.executeQuery()) {
	              while (rs.next()) {
	                int id = rs.getInt("quiz_id");
	                questions(id);
	                masterList.add(new MainQuiz(rs.getInt("quiz_id"),rs.getString("title"),new ArrayList<>(list)));
	                list.clear();
	              }

	            	    
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
	}
	
	public int getClassID(String classCode) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "Select id_class from test.class where class_code = '"+classCode+"'";
		int id = 0;
		try (Connection conn = MyConnection.getConnection();
		        PreparedStatement stmt = conn.prepareStatement(sql)) {
	            try (ResultSet rs = stmt.executeQuery()) {
	              if(rs.next()) 
	                id = rs.getInt("id_class");    
	            }
	           } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return id;
	}
	
	
	public ArrayList<MainQuestion> getQuestions(){
		return list;
	}

	public ArrayList<String> getQuizName(){
		return quizName;
	}
	
	public ArrayList<MainQuiz> getQuiz(){
		return masterList;
	}
	
	public String getCode() {
		return code;
	}
	
	public void clear() {
		answers.clear();
		quizName.clear();
		masterList.clear();
	}
	

	
}

