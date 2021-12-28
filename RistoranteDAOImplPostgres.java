

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RistoranteDAOImplPostgres implements RistoranteDAO {
	
	public ArrayList<Ristorante> estraiTuttiRistoranti() throws OperazioneFallitaException{
		
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
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			throw ecc;
		}
	}
	
	public void inserisciRistorante(String nome, String via, Integer n_Civico, String citta) throws OperazioneFallitaException{
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO Ristorante (Nome, Via, N_Civico, Citta) VALUES ("
					+ "'" + nome + "'" + ", " + "'" + via+ "'" + ", " 
					+ n_Civico + ", " + "'"+ citta +"'" + " );");
			
		}
		catch (SQLException e)
		{
			OperazioneFallitaException ecc= new OperazioneFallitaException();
			throw ecc;
		}
	}

	public void modificaRistorante(Integer id_Ristorante, String nome, String via, Integer n_Civico, String citta) throws OperazioneFallitaException {
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			stmt.executeUpdate("UPDATE Ristorante AS R SET "
					+ "Nome = " + "'" + nome + "'" + ", " + "Via = " + "'" + via+ "'" + ", " 
					+ "N_Civico = " + n_Civico + ", " + "Citta = " + "'" + citta +"'" +
					"WHERE Id_Ristorante = " + id_Ristorante + ";");
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			OperazioneFallitaException ecc= new OperazioneFallitaException();
			throw ecc;
		}
	}
	
	public void eliminaRistorante(Ristorante ristoranteCorrente) throws OperazioneFallitaException {
		try
		{
		Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
		stmt.executeUpdate("DELETE FROM RISTORANTE WHERE "
		+ "Id_Ristorante = " + ristoranteCorrente.getId_Ristorante() + ";");
		}
		catch (SQLException e)
		{
		System.out.println(e.getMessage());
		OperazioneFallitaException ecc= new OperazioneFallitaException();
		throw ecc;
		}



		}
	
}