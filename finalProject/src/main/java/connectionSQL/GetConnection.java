package connectionSQL;
import java.sql.Connection;


public class GetConnection {

	private static MyConnectionSingleton con = MyConnectionSingleton.getInstance();
	
    public static Connection getConnection(){
       return con.getConnection();
    }
    
}
