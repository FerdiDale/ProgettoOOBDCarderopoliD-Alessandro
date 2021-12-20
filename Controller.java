import java.sql.*;

public class Controller {
	
	InterfacciaRistoranti frameRistoranti;
	
	public static void main(String[] args) {

		try 
		{
			DB_Builder costruttore = new DB_Builder();
			Controller controllore = new Controller();

		}
		catch(ErroreIniziale e)
		{
			e.stampaMessaggio();
		}

	}
	
	public Controller() {
		frameRistoranti = new InterfacciaRistoranti(this);
	}

}
