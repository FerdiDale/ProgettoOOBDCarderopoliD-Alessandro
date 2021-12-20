import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RistoranteDAOImplPostgres implements RistoranteDAO {
	
	public ArrayList<Ristorante> estraiTuttiRistoranti() throws operazioneFallitaException{
		
		try
		{
			ArrayList<Ristorante> listaRistoranti = new ArrayList<Ristorante>();
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultatoQuery;
			risultatoQuery = stmt.executeQuery("SELECT * FROM RISTORANTE");
			
			while(risultatoQuery.next()) {
				Ristorante ristoranteCurr = new Ristorante();
				Integer idCurr = risultatoQuery.getInt(1);
				String nomeCurr = risultatoQuery.getString(2);
				String viaCurr = risultatoQuery.getString(3);
				Integer n_CivicoCurr = risultatoQuery.getInt(4);
				String cittaCurr = risultatoQuery.getString(5);
				ristoranteCurr.setId_Ristorante(idCurr);
				ristoranteCurr.setNome(nomeCurr);
				ristoranteCurr.setVia(viaCurr);
				ristoranteCurr.setN_Civico(n_CivicoCurr);
				ristoranteCurr.setCitta(cittaCurr);
				listaRistoranti.add(ristoranteCurr);
			}
			
			return listaRistoranti;
		
		}
		catch (SQLException e)
		{
			operazioneFallitaException ecc = new operazioneFallitaException();
			throw ecc;
		}
	}
	
}
