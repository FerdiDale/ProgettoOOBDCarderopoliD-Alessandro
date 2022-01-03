import java.util.ArrayList;


public interface TavoloDAO 
{	
	public ArrayList<Tavolo> EstraiTavoliSala(Sala salaScelta);
	public void modificaPosizioniTavoli(ArrayList<Tavolo> tavoli) throws OperazioneFallitaException;
	public void inserisciNuovoTavolo(Tavolo nuovoTavolo) throws OperazioneFallitaException;
	public ArrayList<Tavolo> tavoliOccupatiInData(String data, Sala sala);
}
