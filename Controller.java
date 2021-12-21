import java.sql.*;

public class Controller {
	
	InterfacciaRistoranti frameRist;
	InterfacciaSale frameSala;
	InterfacciaCreazioneSala frameCreateS;
	
	/*public static void main(String[] args) {

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
		frameRist = new InterfacciaRistoranti(this);
	}*/
	public void bottoneRimozioneSalaPremuto(Sala c)
	{
		try
		{
			SalaDAOImplPostgres SDAO = new SalaDAOImplPostgres();
			SDAO.RimuoviSalaRistorante(c);
		}finally {}
	}
	
	public void bottoneAggiuntaSalaPremuto()
	{
		frameSala.setVisible(false);
		
	}

}