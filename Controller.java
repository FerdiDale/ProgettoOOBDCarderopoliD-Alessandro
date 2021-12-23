
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Date;
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
		catch(ErrorePersonalizzato e)
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
		catch (ErrorePersonalizzato e)
		{
			e.stampaMessaggio();
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
		catch (ErrorePersonalizzato e)
		{
			e.stampaMessaggio();
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
		catch (ErrorePersonalizzato e)
		{
			e.stampaMessaggio();
		}		
	}

	public void bottoneVisualizzaSalePremuto(Ristorante ristoranteCorrente) {
		frameRistoranti.setVisible(false);
		frameSale = new InterfacciaSale(this, ristoranteCorrente);
	}

	public void bottoneVisualizzaStatistichePremuto(Ristorante ristoranteCorrente) {
		frameRistoranti.setVisible(false);
		frameStatistiche = new InterfacciaStatistiche(this, ristoranteCorrente);
	}

	public ArrayList<Ristorante> inizializzazioneRistoranti() {
		
		ArrayList<Ristorante> listaRistoranti = new ArrayList<Ristorante>();
		boolean errore = false;
		
		do {
			try
			{
				listaRistoranti = ristoranteDao.estraiTuttiRistoranti();
				errore = false;
			}
			catch (OperazioneFallitaException ecc)
			{
				errore = true;
			}
		} while (errore);
		
		return listaRistoranti;
	}
	
	public void bottoneRiassumiCamerierePremuto(Cameriere c,String data)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		CDAO.
	}
	
}