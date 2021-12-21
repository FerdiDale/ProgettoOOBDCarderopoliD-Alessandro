import java.sql.*;

import javax.swing.JOptionPane;

public class Controller {
	
	InterfacciaRistoranti frameRistoranti;
	InterfacciaAggiuntaRistorante frameAggiuntaRistorante;
	InterfacciaModificaDatiRistorante frameModificaRistorante;
	RistoranteDAOImplPostgres ristoranteDao = new RistoranteDAOImplPostgres();
	
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
	
	
}
