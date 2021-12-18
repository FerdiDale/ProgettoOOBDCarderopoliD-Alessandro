import java.sql.*;

public class DB_Connection {

	private static DB_Connection instance; 
	private Connection conn = null;
	
	private DB_Connection() {
		
		try 
		{	
			Class.forName("org.postgresql.Driver");
			
			//Connessione al db come utente postgres
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ristorantidb", "postgres", "1754Ggdf");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
	};
	
	public Connection getConnection() {
		return conn;
	}
	
	public static DB_Connection getInstance() {
		
		if (instance==null) {
			instance = new DB_Connection();
		} else
			try {
				if (instance.getConnection().isClosed()) {
					instance = new DB_Connection();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("hey3");
				e.printStackTrace();
			}
		
		return instance;
		
	}
	
	
}
