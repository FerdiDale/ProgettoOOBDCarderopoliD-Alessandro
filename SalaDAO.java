

import java.util.ArrayList;

public interface SalaDAO 
{
	public ArrayList<Sala> EstraiSaleRistorante(int r);
	
	public void RimuoviSalaRistorante(Sala s);
	
	public void AggiuntaSalaRistorante(int r);
}
