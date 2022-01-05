import java.util.ArrayList;

public interface AvventoriDAO 
{
	public ArrayList<Avventori> estraiAvventoriDelTavoloInData(Tavolo tavolo,String data);
	
	public void inserimentoMultiploAvventori(ArrayList<InterfacciaAggiuntaDatiAvventore> lista);
	
	public void rimuoviAvventoreDaElencoAvventori(int id_tavolo, String data, Avventori cliente);
	
	public void aggiungiNuovoavventoreAllaTavolata(int id_tavolo,String data, Avventori avventore);
}
