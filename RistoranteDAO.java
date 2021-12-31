

import java.util.ArrayList;

public interface RistoranteDAO {

	public ArrayList<Ristorante> estraiTuttiRistoranti() throws OperazioneFallitaException;
	public void inserisciRistorante(String nome, String via, Integer n_Civico, String citta) throws OperazioneFallitaException, RistoranteUgualeException;
	public void modificaRistorante(Integer id_Ristorante, String nome, String via, Integer n_Civico, String citta) throws OperazioneFallitaException, RistoranteUgualeException;
	public void eliminaRistorante(Ristorante ristoranteCorrente) throws OperazioneFallitaException;
}