import java.sql.*;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Controller {
  InterfacciaRistoranti frameRistoranti;
	InterfacciaAggiuntaRistorante frameAggiuntaRistorante;
	InterfacciaModificaDatiRistorante frameModificaRistorante;
	RistoranteDAOImplPostgres ristoranteDao = new RistoranteDAOImplPostgres();
    NumeroAvventoriMeseDAOImplPostgres numeroAvventoriMeseDao = new NumeroAvventoriMeseDAOImplPostgres();
	InterfacciaSale frameSale;
	InterfacciaCreazioneSala frameCreateS;
	InterfacciaGestioneCamerieri frameGestioneCamerieri;
	InterfacciaStatistichePerAnno frameStatistichePerAnno; 
	InterfacciaAggiuntaCamerieri frameAggiuntaCamerieri;
	InterfacciaStatistichePerMese frameStatistichePerMese;
	NumeroAvventoriGiornoDAOImplPostgres numeroAvventoriGiornoDao = new NumeroAvventoriGiornoDAOImplPostgres();
	InterfacciaTavoli frameTavoli;
	
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
	}
	
	public void modificaRistoranteOkPremuto(Integer id_Ristorante, String nome, String via, Integer n_Civico, String citta) {
		try
		{
			ristoranteDao.modificaRistorante(id_Ristorante, nome, via, n_Civico, citta);
			frameModificaRistorante.setVisible(false);
			frameRistoranti = new InterfacciaRistoranti(this);
		}
		catch (OperazioneFallitaException e)
		{
			JOptionPane.showMessageDialog(null, "C'e' stato un errore di connnessione, riprovare l'operazione.",
					"Errore!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
  
	public void bottoneRimozioneSalaPremuto(Sala c)
	{
			SalaDAOImplPostgres SDAO = new SalaDAOImplPostgres();
			SDAO.RimuoviSalaRistorante(c);
	}
	
	public void bottoneAggiuntaSalaPremuto(Ristorante ristorante)
	{		
		frameCreateS = new InterfacciaCreazioneSala(ristorante,this);
		frameSale.setVisible(false);
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
		frameSale = new InterfacciaSale(this,ristorante);
	}
	
	public void bottoneGestioneCamerieriPremuto(Ristorante ristorante)
	{
		frameSale.setVisible(false);
		frameGestioneCamerieri = new InterfacciaGestioneCamerieri(ristorante, this);
	}
	
	public void bottoneOkGestioneCamerieriPremuto(Ristorante ristorante)
	{
		frameGestioneCamerieri.setVisible(false);
		frameSale = new InterfacciaSale(this,ristorante);
	}
	
	public void bottoneTornaIndietroSalePremuto()
	{
		frameSale.setVisible(false);
		frameRistoranti = new InterfacciaRistoranti(this);
	}
	
	public void bottoneTornaIndietroGestioneCamerieriPremuto(Ristorante ristorante)
	{
		frameGestioneCamerieri.setVisible(false);
		frameSale = new InterfacciaSale(this,ristorante);
	}
	
	public void bottoneTornaIndietroInterfacciaCreazioneSalaPremuto(Ristorante ristorante)
	{
		frameCreateS.setVisible(false);
		frameSale = new InterfacciaSale(this,ristorante);
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
	
	public boolean bottoneRiassumiCamerierePremuto(Cameriere c,String data)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		 return CDAO.riassumiCameriereLicenziato(c,data);
	}
	
	public String bottoneLicenziaCamerierePremuto(Cameriere c, String data)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		return CDAO.licenziaCameriereAssunto(c, data);
	}
	
	public void bottoneAggiungiCamerierePremuto(Ristorante r)
	{
		frameGestioneCamerieri.setVisible(false);
		frameAggiuntaCamerieri = new InterfacciaAggiuntaCamerieri(r, this); 
	}
	
	public void bottoneTornaIndietroAggiuntaCamerieriPremuto(Ristorante ristorante)
	{
		frameAggiuntaCamerieri.setVisible(false);
		frameGestioneCamerieri = new InterfacciaGestioneCamerieri(ristorante, this);
	}
	
	public void bottoneOkAggiuntaCamerieriPremutoSuccessful(Cameriere cameriere) throws SQLException
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		CDAO.assumiNuovoCameriere(cameriere);
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
		frameStatistichePerAnno = new InterfacciaStatistichePerAnno(this, ristoranteCorrente, java.time.LocalDateTime.now().getYear());
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

	public DefaultCategoryDataset ricavaStatistiche(Integer anno, Ristorante ristorante) {
		ArrayList<NumeroAvventoriMese> listaCorrenteStatistiche = new ArrayList<NumeroAvventoriMese>();
		boolean errore = false;
		
		do {
			try
			{
				errore= false;
				listaCorrenteStatistiche = numeroAvventoriMeseDao.estraiStatisticheAnno(anno, ristorante);
			}
			catch (OperazioneFallitaException e)
			{
				errore = true;
			}
			
		} while(errore);
		
		DefaultCategoryDataset risultato = new DefaultCategoryDataset();
		
		for (NumeroAvventoriMese numeroCorrente : listaCorrenteStatistiche) {
			risultato.setValue(numeroCorrente.getNumAvventori(), "Numero di avventori", numeroCorrente.getMese().toString());
		}
		
		return risultato;
	}
	
	public DefaultCategoryDataset ricavaStatistiche(Integer anno, Integer mese, Ristorante ristorante) {
		ArrayList<NumeroAvventoriGiorno> listaCorrenteStatistiche = new ArrayList<NumeroAvventoriGiorno>();
		boolean errore = false;
		
		do {
			try
			{
				errore= false;
				listaCorrenteStatistiche = numeroAvventoriGiornoDao.estraiStatisticheMese(anno, mese, ristorante);
			}
			catch (OperazioneFallitaException e)
			{
				errore = true;
			}
			
		} while(errore);
		
		DefaultCategoryDataset risultato = new DefaultCategoryDataset();
		
		for (NumeroAvventoriGiorno numeroCorrente : listaCorrenteStatistiche) {
			risultato.setValue(numeroCorrente.getNumAvventori(), "Numero di avventori", numeroCorrente.getGiorno());
		}
		
		return risultato;
	}

	public void passaggioAMesePremuto(Integer annoScelto, Ristorante ristoranteScelto) {
		frameStatistichePerAnno.setVisible(false);
		frameStatistichePerMese = new InterfacciaStatistichePerMese(this, annoScelto, 1, ristoranteScelto);
	}

	public void passaggioAdAnnoPremuto(Integer annoCorrente, Ristorante ristoranteScelto) {
		frameStatistichePerMese.setVisible(false);
		frameStatistichePerAnno = new InterfacciaStatistichePerAnno(this, ristoranteScelto, annoCorrente);
		
	}

	public void bottoneIndietroStatistichePremuto(Ristorante ristoranteCorrente) {
		frameStatistichePerAnno.setVisible(false);
		frameRistoranti = new InterfacciaRistoranti(this);
	}

	public void bottoneIndietroAggiungiRistorantePremuto() {
		frameAggiuntaRistorante.setVisible(false);
		frameRistoranti = new InterfacciaRistoranti(this);
	}

	public void bottoneIndietroModificaRistorantePremuto() {
		frameModificaRistorante.setVisible(false);
		frameRistoranti = new InterfacciaRistoranti (this);
  }
	public void bottoneIndietroGestioneTavoliPremuto(Sala salaScelta, Ristorante ristoranteScelto) {
		frameTavoli.setVisible(false);
		frameSale = new InterfacciaSale(this, ristoranteScelto);
	}

	public void bottoneVediTavoliPremuto(Ristorante ristoranteScelto, Sala salaScelta) {
		frameSale.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, salaScelta, ristoranteScelto);
	}
}  
