import java.util.ArrayList;

public interface SalaDAO 
{
	public ArrayList<Sala> EstraiSaleRistorante(int r);
	
	public void RimuoviSalaRistorante(Sala s);
	
	public void AggiuntaSalaRistorante(String nomeSala, int id_ristorante);
	
	public boolean isNomeSalaTaken(String nomeSala, int id_ristorante);
}
