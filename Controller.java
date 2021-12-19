import java.sql.*;

public class Controller {
	
	InterfacciaRistoranti frameRist;
	
	public static void main(String[] args) {

		try 
		{
			DB_Builder costruttore = new DB_Builder();
			Controller controllore = new Controller();

		}
		catch(ErroreIniziale e)
		{
			e.StampaMessaggio();
		}
		

	}
	
	public Controller() {
		frameRist = new InterfacciaRistoranti(this);
	}

}
