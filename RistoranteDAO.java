

import java.util.ArrayList;

public interface RistoranteDAO {

	public ArrayList<Ristorante> estraiTuttiRistoranti() throws OperazioneFallitaException;
	
}