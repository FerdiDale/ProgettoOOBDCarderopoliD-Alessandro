import java.util.ArrayList;


public interface TavoloDAO 
{	
	public ArrayList<Tavolo> EstraiTavoliSala(Sala salaScelta);
	public void modificaPosizioniTavoli(ArrayList<Tavolo> tavoli) throws OperazioneFallitaException;
}
