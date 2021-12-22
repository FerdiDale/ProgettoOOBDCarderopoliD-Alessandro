
import java.sql.*;
import javax.swing.JOptionPane;

public class Controller {
	
	InterfacciaRistoranti frameRistoranti;
	InterfacciaAggiuntaRistorante frameAggiuntaRistorante;
	InterfacciaModificaDatiRistorante frameModificaRistorante;
	RistoranteDAOImplPostgres ristoranteDao = new RistoranteDAOImplPostgres();
	InterfacciaSale frameSale;
	InterfacciaStatistiche frameStatistiche;
	//InterfacciaCreazioneSala frameCreateS;
	
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
  
  	public void bottoneAggiungiRistorantePremuto() {
		frameRistoranti.setVisible(false);
		frameAggiuntaRistorante = new InterfacciaAggiuntaRistorante(this);
		frameAggiuntaRistorante.setVisible(true);
	}
	
	public void aggiuntaRistoranteOkPremuto(String nome, String via, Integer n_Civico, String citta) {
		try
		{
			ristoranteDao.inserisciRistorante(nome, via, n_Civico, citta);
			frameAggiuntaRistorante.setVisible(false);
			frameRistoranti = new InterfacciaRistoranti(this);
			frameRistoranti.setVisible(true);
		}
		catch (OperazioneFallitaException e)
		{
			e.StampaMessaggio();
		}
	}
	
	public void bottoneModificaRistorantePremuto(Ristorante ristoranteCorrente) {
		frameRistoranti.setVisible(false);
		frameModificaRistorante = new InterfacciaModificaDatiRistorante(this, ristoranteCorrente);
		frameModificaRistorante.setVisible(true);
	}
	
	public void modificaRistoranteOkPremuto(Integer id_Ristorante, String nome, String via, Integer n_Civico, String citta) {
		try
		{
			ristoranteDao.modificaRistorante(id_Ristorante, nome, via, n_Civico, citta);
			frameModificaRistorante.setVisible(false);
			frameRistoranti = new InterfacciaRistoranti(this);
			frameRistoranti.setVisible(true);
		}
		catch (OperazioneFallitaException e)
		{
			e.StampaMessaggio();
		}
	}
	
  
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
		frameSale.setVisible(false);
	}

	public void bottoneEliminaRistorantePremuto(Ristorante ristoranteCorrente) {
		try
		{
			ristoranteDao.eliminaRistorante(ristoranteCorrente);
		}
		catch (OperazioneFallitaException e)
		{
			e.StampaMessaggio();
		}		
	}

	public void bottoneVisualizzaSalePremuto(Ristorante ristoranteCorrente) {
		frameRistoranti.setVisible(false);
		frameSale = new InterfacciaSale(this, ristoranteCorrente.getNome(), ristoranteCorrente.getId_Ristorante());
	}

	public void bottoneVisualizzaStatistichePremuto(Ristorante ristoranteCorrente) {
		frameRistoranti.setVisible(false);
		frameStatistiche = new InterfacciaStatistiche(this, ristoranteCorrente);
	}

}