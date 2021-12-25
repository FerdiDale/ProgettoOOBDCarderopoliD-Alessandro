
import java.sql.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Date;
public class Controller {
    InterfacciaRistoranti frameRistoranti;
	InterfacciaAggiuntaRistorante frameAggiuntaRistorante;
	InterfacciaModificaDatiRistorante frameModificaRistorante;
	RistoranteDAOImplPostgres ristoranteDao = new RistoranteDAOImplPostgres();
	InterfacciaSale frameSala;
	InterfacciaCreazioneSala frameCreateS;
	InterfacciaGestioneCamerieri frameGestioneCamerieri;
	InterfacciaAggiuntaCamerieri frameAggiuntaCamerieri;
	
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
		//frameRistoranti = new InterfacciaRistoranti(this);
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
			JOptionPane.showMessageDialog(null, "C'e' stato un errore di connnessione, riprovare l'operazione.",
					"Errore!", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "C'e' stato un errore di connnessione, riprovare l'operazione.",
					"Errore!", JOptionPane.ERROR_MESSAGE);
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
	
	public void bottoneAggiuntaSalaPremuto(Ristorante ristorante)
	{		
		frameCreateS = new InterfacciaCreazioneSala(ristorante,this);
		frameSala.setVisible(false);
	}
	
	public boolean interfacciaCreazioneSalaOkPremuto1(String nomeSala, int id_ristorante)
	{
		SalaDAOImplPostgres SDAO = new SalaDAOImplPostgres();
		return SDAO.isNomeSalaTaken(nomeSala, id_ristorante);
	}
	
	public void interfacciaCreazioneSalaOkPremuto2(String nomeSala, Ristorante ristorante)
	{
		frameCreateS.setVisible(false);
		SalaDAOImplPostgres SDAO = new SalaDAOImplPostgres();
		SDAO.AggiuntaSalaRistorante(nomeSala, ristorante.getId_Ristorante());
		frameSala = new InterfacciaSale(this,ristorante);
	}
	
	public ArrayList<Sala> EstraiSaleRistorante(Ristorante ristorante)
	{
		SalaDAOImplPostgres SDAO = new SalaDAOImplPostgres();
		return SDAO.EstraiSaleRistorante(ristorante.getId_Ristorante());
	}
	
	public ArrayList<Cameriere> EstraiCamerieriInServizioC(Ristorante ristorante)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		return CDAO.EstraiCamerieriInServizio(ristorante);
	}
	
	public ArrayList<Cameriere> EstraiCamerieriLicenziatiC(Ristorante ristorante)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		return CDAO.EstraiCamerieriLicenziati(ristorante);
	}
	
	public void bottoneRiassumiCamerierePremuto(Cameriere c,String data)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		CDAO.riassumiCameriereLicenziato(c,data);
	}
	
	public void bottoneLicenziaCamerierePremuto(Cameriere c, String data)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		CDAO.licenziaCameriereAssunto(c, data);
	}
	
	public void bottoneAggiungiCamerierePremuto(Ristorante r)
	{
		frameGestioneCamerieri.setVisible(false);
		frameAggiuntaCamerieri = new InterfacciaAggiuntaCamerieri(r, this); 
	}
}