import java.util.ArrayList;

public interface SalaDAO 
{
	public ArrayList<Sala> EstraiSaleRistorante(Ristorante ristoranteCorrente);
	
	public void RimuoviSalaRistorante(Sala s);
	
	public void AggiuntaSalaRistorante(String nomeSala, int id_ristorante);
	
}
