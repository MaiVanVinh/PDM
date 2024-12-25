package connectionSQL;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.DriverManager;

public class MyConnectionSingleton {
    private static final String username = "root";
    private static final String password = "maytinhcasio580";
    private static final String url = "jdbc:mysql://localhost:3306/test";
    public static MyConnectionSingleton instance = null;
    public static Connection con;
    
    public static MyConnectionSingleton getInstance() {
        if (instance == null) {
            instance = new MyConnectionSingleton();
        }
        return instance;
    }
    
    public Connection getConnection(){
    	try {
    	     Class.forName("com.mysql.cj.jdbc.Driver");
    	     con = DriverManager.getConnection(url, username, password);
    	} catch (Exception ex) {
    	        JOptionPane.showMessageDialog(null, "" + ex, "", JOptionPane.WARNING_MESSAGE);
    	}
    	return con;
    }
    
    
}