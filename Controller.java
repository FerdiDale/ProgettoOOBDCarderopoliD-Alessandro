import java.sql.*;
import java.util.regex.*;

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
	private InterfacciaRistoranti frameRistoranti;
    private InterfacciaAggiuntaRistorante frameAggiuntaRistorante;
    private InterfacciaModificaDatiRistorante frameModificaRistorante;
	private RistoranteDAOImplPostgres ristoranteDao = new RistoranteDAOImplPostgres();
	private NumeroAvventoriMeseDAOImplPostgres numeroAvventoriMeseDao = new NumeroAvventoriMeseDAOImplPostgres();
    private InterfacciaSale frameSale;
	private InterfacciaCreazioneSala frameCreateS;
	private InterfacciaGestioneCamerieri frameGestioneCamerieri;
	private InterfacciaStatistichePerAnno frameStatistichePerAnno; 
	private InterfacciaAggiuntaCamerieri frameAggiuntaCamerieri;
	private InterfacciaStatistichePerMese frameStatistichePerMese;
	private NumeroAvventoriGiornoDAOImplPostgres numeroAvventoriGiornoDao = new NumeroAvventoriGiornoDAOImplPostgres();
	private InterfacciaTavoli frameTavoli;
	private InterfacciaModificaLayout frameModificaLayout;
	private TavoloDAOImplPostgres tavoloDao = new TavoloDAOImplPostgres();
	private InterfacciaAggiuntaTavoli frameAggiuntaTavoli;
	private InterfacciaAggiuntaCapienzaNumeroNuovoTavolo frameACNT;
	private InterfacciaSelezioneDataOccupazione frameSelezioneDataOccupazione;
	private InterfacciaGestioneOccupazioni frameGestioneOccupazioni;
	private InterfacciaAdiacenze frameAdiacenze;
	private InterfacciaModificaDatiTavolo frameModificaDatiTavolo;
	private InterfacciaVisualizzazioneOccupazione frameVisualizzaOccupazione;
	private ArrayList<InterfacciaAggiuntaDatiAvventore> framesAggiuntaAvventore;
	private InterfacciaSelezioneCamerieri frameSelezioneCamerieri;
	
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
		catch (ErrorePersonalizzato e)
		{
			e.stampaMessaggio();
		}
	}
	
	public void bottoneModificaRistorantePremuto(Ristorante ristoranteCorrente) {
		frameRistoranti.setVisible(false);
		frameModificaRistorante = new InterfacciaModificaDatiRistorante(this, ristoranteCorrente);
	}
	
	public void modificaRistoranteOkPremuto(Ristorante ristorante, String nome, String via, Integer n_Civico, String citta) {
		try
		{
			ristoranteDao.modificaRistorante(ristorante, nome, via, n_Civico, citta);
			frameModificaRistorante.setVisible(false);
			frameRistoranti = new InterfacciaRistoranti(this);
		}
		catch (ErrorePersonalizzato e)
		{
			e.stampaMessaggio();
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
	
	public void interfacciaCreazioneSalaOkPremuto(String nomeSala, Ristorante ristorante)
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
		return SDAO.EstraiSaleRistorante(ristorante);
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
	
	public String bottoneOkAggiuntaCamerieriPremutoSuccessful(Cameriere cameriere)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		return CDAO.assumiNuovoCameriere(cameriere);
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
	public void bottoneIndietroGestioneTavoliPremuto(Sala salaScelta) {
		frameTavoli.setVisible(false);
		frameSale = new InterfacciaSale(this, salaScelta.getRistoranteDiAppartenenza());
	}

	public void bottoneVediTavoliPremuto(Sala salaScelta) {
		frameSale.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, salaScelta);
	}
	
	public ArrayList<Tavolo> EstrazioneTavoliSala(Sala sala)
	{
		TavoloDAOImplPostgres TDAO = new TavoloDAOImplPostgres();
		return TDAO.EstraiTavoliSala(sala);
	}
  
	public void bottoneModificaLayoutPremuto(Sala sala) {
		frameTavoli.setVisible(false);
		frameModificaLayout = new InterfacciaModificaLayout(this, sala);
	}

	public void modificaLayoutIndietroPremuto(Sala sala) {
		frameModificaLayout.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, sala);
	}

	public void confermaModificheLayoutPremuto(ArrayList<Tavolo> tavoli, Sala sala) {
		
		try 
		{
			tavoloDao.modificaPosizioniTavoli (tavoli);
			frameModificaLayout.setVisible(false);
			frameTavoli = new InterfacciaTavoli(this, sala);
		} catch (OperazioneFallitaException e) 
		{
			e.stampaMessaggio();
		}
	
	}
	
	public void bottoneAggiuntaTavoloPremuto(Sala salaScelta, ArrayList<Tavolo> tavoliGiaEsistenti)
	{
		frameTavoli.setVisible(false);
		frameACNT = new InterfacciaAggiuntaCapienzaNumeroNuovoTavolo(this,salaScelta,tavoliGiaEsistenti);
	}
	
	public void bottoneOkInterfacciaAggiuntaCapienzaNumeroNuovoTavoloPremuto(Tavolo tavolo,Sala salaScelta, ArrayList<Tavolo> tavoliGiaEsistenti)
	{
		frameACNT.setVisible(false);
		frameAggiuntaTavoli = new InterfacciaAggiuntaTavoli(tavolo,salaScelta,tavoliGiaEsistenti,this);
	}
	
	public void bottoneIndietroInterfacciaAggiuntaCapienzaNumeroNuovoTavoloPremuto(Sala salaScelta)
	{
		frameACNT.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this,salaScelta);
	}
	
	public void bottoneOkInterfacciaAggiuntaTavoliPremuto(Tavolo nuovoTavolo)
	{
		try
		{
			TavoloDAOImplPostgres TDAO = new TavoloDAOImplPostgres();
			TDAO.inserisciNuovoTavolo(nuovoTavolo);
		}
		catch(OperazioneFallitaException e)
		{
			e.stampaMessaggio();
		}
		frameAggiuntaTavoli.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this,nuovoTavolo.getSala_App());
	}
	
	public void bottoneIndietroInterfacciaAggiuntaTavoliPremuto(Sala salaScelta)
	{
		frameAggiuntaTavoli.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this,salaScelta);
	}
	
	public void bottoneGestioneOccupazioneInterfacciaTavoliPremuto(ArrayList<Tavolo> tavoli)
	{
		frameTavoli.setVisible(false);
		frameSelezioneDataOccupazione = new InterfacciaSelezioneDataOccupazione(this, tavoli);
	}
	
	
	public void bottoneIndietroInterfacciaSelezioneDataGestioneOccupazionePremuto(Sala salaScelta)
	{
		frameSelezioneDataOccupazione.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, salaScelta);
	}
	
	public void bottoneGoNextInterfacciaSelezioneDataGestioneOccupazionePremuto(ArrayList<Tavolo> tavoli, String data)
	{
		frameSelezioneDataOccupazione.setVisible(false);
		frameGestioneOccupazioni = new InterfacciaGestioneOccupazioni(this,tavoli,data);
	}

	public ArrayList<Integer> estrazioneSaleOccupateInData(Sala sala, String data)
	{
		TavoloDAOImplPostgres TDAO= new TavoloDAOImplPostgres();
		return TDAO.tavoliOccupatiInData(data, sala);
	}
	
	public ArrayList<Avventori> estrazioneAvventoriDelTavoloInData(Tavolo tavolo, String data)
	{
		AvventoriDAOImplPostgres ADAO = new AvventoriDAOImplPostgres();
		return ADAO.estraiAvventoriDelTavoloInData(tavolo, data);
	}
	
	public void bottoneVisualizzaOccupazioneGestioneOccupazione(ArrayList<Tavolo> tavoli, String data, int tavoloScelto)
	{
		frameGestioneOccupazioni.setVisible(false);
		frameVisualizzaOccupazione = new InterfacciaVisualizzazioneOccupazione(this,tavoli,tavoloScelto,data);
	}
	
	public void bottoneIndietroVisualizzazioneOccupazione(ArrayList<Tavolo> tavoli, String data)
	{
		frameVisualizzaOccupazione.setVisible(false);
		frameGestioneOccupazioni = new InterfacciaGestioneOccupazioni(this,tavoli,data);
	}
	
	public void bottoneRimuoviCameriereVisualizzazioneOccupazione(String data,int idTavolo, Cameriere cameriereScelto)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		CDAO.rimuoviCameriereDalTavoloInData(cameriereScelto, data, idTavolo);
	}
	
	public ArrayList<Cameriere> estrazioneCamerieriInServizioAlTavoloinData(int idTavolo, String data)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		return CDAO.camerieriInServizioAlTavoloInData(idTavolo, data);
	}
	
	public void bottoneOccupaGestioneOccupazionePremuto(int numeroAvv, ArrayList<Tavolo> tavoli, int tavoloScelto, String data)
	{
		frameGestioneOccupazioni.setVisible(false);
		framesAggiuntaAvventore = new ArrayList<InterfacciaAggiuntaDatiAvventore>();
		for (int i = 0; i< numeroAvv; i++)
		{
			framesAggiuntaAvventore.add(new InterfacciaAggiuntaDatiAvventore(this,i,tavoli,tavoloScelto,data));
		}
		for (int i = 0; i<numeroAvv; i++)
		{
			framesAggiuntaAvventore.get(i).impostaBottoniCorretti(framesAggiuntaAvventore.size());
			
		}
		System.out.println(framesAggiuntaAvventore.size());
		framesAggiuntaAvventore.get(0).setVisible(true);
	}
	
	public void bottoneConfermaAggiuntaAvventoriPremuto(ArrayList<Tavolo> tavoli,int tavoloScelto, String data) throws CampiNonCorrettiException
	{
		int i=0;
		boolean controlloCampiVuoti = true;
		boolean controlloAlmenoUnTelefono = false;
		boolean formatoCidGiusto = true;
		boolean doppieCID= false;
		while(i<framesAggiuntaAvventore.size() && controlloCampiVuoti && formatoCidGiusto )
		{
			if(framesAggiuntaAvventore.get(i).getCid().getText().isBlank() || framesAggiuntaAvventore.get(i).getCognome().getText().isBlank() || framesAggiuntaAvventore.get(i).getNome().getText().isBlank()) controlloCampiVuoti = false;
			if(!framesAggiuntaAvventore.get(i).getNtel().getText().isBlank()) controlloAlmenoUnTelefono = true;
			if(!Pattern.matches("C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]",framesAggiuntaAvventore.get(i).getCid().getText())) formatoCidGiusto =false;
			i++;
		}
		i = 0;
		while(i<framesAggiuntaAvventore.size() && !doppieCID)
		{
			for (int j = i+1 ; j< framesAggiuntaAvventore.size(); j++)
			{
				if (framesAggiuntaAvventore.get(i).getCid().getText().equals(framesAggiuntaAvventore.get(j).getCid().getText())) doppieCID = true;
			}
			i++;
		}
		
		System.out.println("Campi vuoti ="+controlloCampiVuoti+" telefono = "+controlloAlmenoUnTelefono+" CID = "+formatoCidGiusto);
		if(controlloCampiVuoti && controlloAlmenoUnTelefono && formatoCidGiusto && !doppieCID)
		{
			framesAggiuntaAvventore.get(framesAggiuntaAvventore.size()-1).setVisible(false);
			frameSelezioneCamerieri = new InterfacciaSelezioneCamerieri(this,tavoli,tavoloScelto,data);
		}
		else
		{
		    throw new CampiNonCorrettiException();
		}
		
	}
	
	public ArrayList<Cameriere> estraiCamerieriAssegnabili(String data)
	{
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		return CDAO.camerieriAssegnabiliAlTavoloInData(data);
	}
	
	public void bottoneIndietroSelezioneCamerieriPremuto(ArrayList<Tavolo> tavoli, String data)
	{
		frameSelezioneCamerieri.setVisible(false);
		frameGestioneOccupazioni = new InterfacciaGestioneOccupazioni(this,tavoli,data);
	}
	
	public void bottoneAvventoreSuccessivoPremuto(int indice)
	{
		framesAggiuntaAvventore.get(indice).setVisible(false);
		framesAggiuntaAvventore.get(indice + 1).setVisible(true);
	}
	
	public void bottoneAvventorePrecedentePremuto(int indice)
	{
		framesAggiuntaAvventore.get(indice).setVisible(false);
		framesAggiuntaAvventore.get(indice-1).setVisible(true);
	}
	
	public void bottoneConfermaSelezioneCamerieriPremuto(int[] indiciScelti, ArrayList<Cameriere> camerieriDisponibili, String data, ArrayList<Tavolo> tavoli, int tavoloScelto)
	{
		TavolataDAOImplPostgres TAVDAO = new TavolataDAOImplPostgres();
		TAVDAO.inserimentoTavolata(new Tavolata(tavoli.get(tavoloScelto).getId_Tavolo(),data));
		
		AvventoriDAOImplPostgres ADAO = new AvventoriDAOImplPostgres();
		ADAO.inserimentoMultiploAvventori(framesAggiuntaAvventore);
		
		
		CameriereDAOImplPostgres CDAO = new CameriereDAOImplPostgres();
		CDAO.inserimentoMultiploCamerieriInServizio(indiciScelti, camerieriDisponibili, data, tavoli.get(tavoloScelto));
		
		frameSelezioneCamerieri.setVisible(false);
		frameGestioneOccupazioni = new InterfacciaGestioneOccupazioni(this,tavoli,data);
	}
	
	public void bottoneGestisciAdiacenze(Tavolo tavoloScelto) 
	{
		frameTavoli.setVisible(false);
		frameAdiacenze = new InterfacciaAdiacenze(this, tavoloScelto);
	}

	public void adiacenzeIndietroPremuto(Sala sala) {
		frameAdiacenze.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this,sala);
		
	}

	public ArrayList<Tavolo> estraiTavoliAdiacenti(Tavolo tavoloScelto) {
		
		try {
			return tavoloDao.ricavaTavoliAdiacenti(tavoloScelto);
		}
		catch (OperazioneFallitaException e) {
			e.stampaMessaggio();
		}
		
		return null;
		
	}

	public void bottoneConfermaModificheAdiacensePremuto(Tavolo tavoloProtagonista) {
		try {
			tavoloDao.rimpiazzaAdiacenze(tavoloProtagonista);
			frameAdiacenze.setVisible(false);
			frameTavoli = new InterfacciaTavoli(this,tavoloProtagonista.getSala_App());
		} catch (OperazioneFallitaException e) {
			e.stampaMessaggio();
		}
	}

	public void bottoneModificaDatiPremuto(Tavolo tavolo) {
		frameTavoli.setVisible(false);
		frameModificaDatiTavolo = new InterfacciaModificaDatiTavolo(this, tavolo);
	}

	public void bottoneEliminaTavoloPremuto(Tavolo tavolo) {
		try {
			tavoloDao.eliminaTavolo(tavolo);
		} catch (OperazioneFallitaException e) {
			e.stampaMessaggio();
		}
	}

	public void bottoneConfermaModificheDatiTavoloPremuto(Tavolo tavoloScelto, int numeroCorrente,
			int capacitaCorrente) {
		try {
			
			tavoloDao.modificaDatiTavolo(tavoloScelto, numeroCorrente, capacitaCorrente);
			frameModificaDatiTavolo.setVisible(false);
			frameTavoli = new InterfacciaTavoli(this, tavoloScelto.getSala_App());
			
		} catch (ErrorePersonalizzato e) {
			e.stampaMessaggio();
		}
		
	}

	public void bottoneIndietroModificaDatiTavoloPremuto(Sala salaCorrente) {
		frameModificaDatiTavolo.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, salaCorrente);
	}

	public ArrayList<InterfacciaAggiuntaDatiAvventore> getFramesAggiuntaAvventore()
	{
		return framesAggiuntaAvventore;
	}
}  
