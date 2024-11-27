package updateRes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteQuiz {
	
	private ArrayList<String> deleteList;
	private ArrayList<Integer> quizId;
	private ArrayList<Integer> questionId;
	private String classCode;
	
	public DeleteQuiz() {
		deleteList = new ArrayList<>();
		quizId = new ArrayList<>();
		questionId = new ArrayList<>();
	}
	
	public void getReady(ArrayList<String> d, String c) {
		this.deleteList = d;
		this.classCode = c;
	}
	
	public void deleteSQL() throws ClassNotFoundException, SQLException {
		
		 Class.forName("com.mysql.cj.jdbc.Driver"); 
		 StringBuilder sql = new StringBuilder("delete from answer where question_id in (");
		 StringBuilder sqlQuestion = new StringBuilder("delete from question where quiz_id in (");
		 StringBuilder sqlQuiz= new StringBuilder("delete from quiz where class_code = '"+classCode+"' and title in ('");
	     getQuizID();
	     
          for(int i = 0; i < questionId.size(); i++) {
        	 sql.append(questionId.get(i));
        	 if(i < questionId.size() - 1)
        		 sql.append(",");
          }sql.append(")");
          
          
          for(int i = 0; i < quizId.size(); i++) {
        	  sqlQuestion.append(quizId.get(i));
        	 if(i < quizId.size() - 1)
        		 sqlQuestion.append(",");
          }sqlQuestion.append(")");

		  for(int i = 0; i < deleteList.size(); i++) {
			  sqlQuiz.append(deleteList.get(i));
			  if(i < deleteList.size() - 1) {
				  sqlQuiz.append("', '");
			  }
		  }sqlQuiz.append("')");
	    
        System.out.println(sql);
        System.out.println(sqlQuestion);
        System.out.println(sqlQuiz);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580")) {	             
        	 
        	try(PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        	 ps.executeUpdate(); 
        	 System.out.println("ok");
	         }
                           
             try(PreparedStatement ps = conn.prepareStatement(sqlQuestion.toString())) {
             ps.executeUpdate(); 
             System.out.println("ok");
      	     }
             
             try(PreparedStatement ps = conn.prepareStatement(sqlQuiz.toString())) {
             ps.executeUpdate(); 
             System.out.println("ok");
             ps.close();
      	     conn.close(); 
      	     }
        }


	}
	
	public void deleteQuestion(int questionID) throws ClassNotFoundException, SQLException {
		
		 Class.forName("com.mysql.cj.jdbc.Driver"); 
		 StringBuilder sql = new StringBuilder("delete from answer where question_id in ("+questionID+")");
		 StringBuilder sqlQuestion = new StringBuilder("delete from question where question_id in ("+questionID+")");

	   
       System.out.println(sql);
       System.out.println(sqlQuestion);

       try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580")) {	             
       	 
       	    try(PreparedStatement ps = conn.prepareStatement(sql.toString())) {
       	    ps.executeUpdate(); 
       	    System.out.println("ok");
	        }
                          
            try(PreparedStatement ps = conn.prepareStatement(sqlQuestion.toString())) {
            ps.executeUpdate(); 
            System.out.println("ok");
            ps.close();
       	    conn.close(); 
     	    }
            
       }
	}
	
	private void getQuizID() throws ClassNotFoundException, SQLException {
		  StringBuilder sql = new StringBuilder();
		  sql.append("Select quiz_id from test.quiz where class_code = '"+classCode+"' and title in ('");
		
		  for(int i = 0; i < deleteList.size(); i++) {
			  sql.append(deleteList.get(i));
			  if(i < deleteList.size() - 1) {
				  sql.append("', '");
			  }
		  }sql.append("')");
	    
		  
		  try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
	         PreparedStatement ps = conn.prepareStatement(sql.toString())) {
	         ResultSet rs = ps.executeQuery();	  
	         while(rs.next()) {
                quizId.add(rs.getInt("quiz_id")); 
	         }
	         getQuestionID(conn);
	    
	    } 

	}
	
	
	private void getQuestionID(Connection c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("Select question_id from test.question where quiz_id in (");
		
		for(int i = 0; i < quizId.size(); i++) {
			sql.append(quizId.get(i));
			if(i < quizId.size() - 1) 
				sql.append(", ");
			  
		}sql.append(")");
		    
	    PreparedStatement ps = c.prepareStatement(sql.toString()); {
	       ResultSet rs = ps.executeQuery();	  
	          while(rs.next()) {
	        	  questionId.add(rs.getInt("question_id")); 
	          }
	      ps.close();
	      c.close(); 

	    }
	    
	    
	}
	
	
	
	
	

}
