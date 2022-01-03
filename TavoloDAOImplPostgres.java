import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.sql.*;

public class TavoloDAOImplPostgres implements TavoloDAO
{
	public ArrayList<Tavolo> EstraiTavoliSala(Sala salaScelta)
	{
		ArrayList<Tavolo> risultato = new ArrayList<Tavolo>();
		try
		{
			ResultSet rs = DB_Connection.getInstance().getConnection().createStatement().executeQuery("SELECT T.id_tavolo,T.numero, P.posx,P.posy,P.dimx,P.dimy , T.capacita "
																									+ "FROM Tavolo AS T, Posizioni as P "
																									+ "WHERE T.id_tavolo = P.id_tavolo AND T.Id_sala = "+ salaScelta.getId_Sala()+";");
			while(rs.next())
			{
				risultato.add(new Tavolo(rs.getInt(1), rs.getInt(2), rs.getInt(7), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), salaScelta));
			}																							
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		return risultato;
	}

	public void modificaPosizioniTavoli(ArrayList<Tavolo> tavoli) throws OperazioneFallitaException {
		
		try
		{
			for (Tavolo tavolo : tavoli) {
				DB_Connection.getInstance().getConnection().createStatement().executeUpdate("UPDATE Posizioni "
																							 + " SET posx = " + tavolo.getPosX() + " ,"
																							 + " posy = " + tavolo.getPosY() + " ,"
																							 + " dimx = " + tavolo.getDimX() + " ,"
																							 + " dimy = " + tavolo.getDimY() 
																							 + " WHERE id_tavolo = " + tavolo.getId_Tavolo() + " ;");
			}
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			throw ecc;
		}
		
	}

	public ArrayList<Tavolo> ricavaTavoliAdiacenti(Tavolo tavoloScelto) throws OperazioneFallitaException {
		
		try
		{
			ArrayList<Tavolo> adiacenti = new ArrayList<Tavolo>();
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultatoQuery;
			risultatoQuery = stmt.executeQuery("SELECT T.* "
					+ " FROM ADIACENZA AS A JOIN TAVOLO AS T ON A.Id_Tavolo2 = T.Id_Tavolo "
					+ " WHERE Id_Tavolo1 = " + tavoloScelto.getId_Tavolo() + " ;");
			
			while(risultatoQuery.next()) {
				Tavolo tavoloCurr = new Tavolo(risultatoQuery.getInt(1), risultatoQuery.getInt(2),
						 tavoloScelto.getSala() , risultatoQuery.getInt(4));
				
				adiacenti.add(tavoloCurr);
			}
			
			return adiacenti;
		
		}
		catch (SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			throw ecc;
		}
		
	}

	public void rimpiazzaAdiacenze(Tavolo tavoloProtagonista) throws OperazioneFallitaException {
		
		try
		{
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Adiacenza AS A "
					+ " WHERE A.Id_Tavolo1 = " + tavoloProtagonista.getId_Tavolo() + " ;");
			
			for (Tavolo tavolo : tavoloProtagonista.getTavoliAdiacenti()) {
				DB_Connection.getInstance().getConnection().createStatement().executeUpdate("INSERT INTO Adiacenza "
																							+ "VALUES (" + tavoloProtagonista.getId_Tavolo() + " , "
																							+ tavolo.getId_Tavolo() + " );");
			}
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			throw ecc;
		}
		
	}

	public void modificaDatiTavolo(Tavolo tavoloScelto, int numeroCorrente, int capacitaCorrente) throws OperazioneFallitaException, TavoloNumeroUgualeException {
		
		try
		{
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("UPDATE TAVOLO AS T "
															+ " SET Capacita = " + capacitaCorrente + " , "
															+ "Numero = " + numeroCorrente + " "
															+ "WHERE T.Id_Tavolo = " + tavoloScelto.getId_Tavolo() + " ;");
			
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("23505")){
				TavoloNumeroUgualeException ecc = new TavoloNumeroUgualeException();
				throw ecc;
			}
			else {
				OperazioneFallitaException ecc = new OperazioneFallitaException();
				throw ecc;
			}
		}
		
	}

	public void eliminaTavolo(Tavolo tavolo) throws OperazioneFallitaException {

		try
		{
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM TAVOLO AS T "
					+ " WHERE T.Id_Tavolo = " + tavolo.getId_Tavolo() + " ;");
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM POSIZIONI AS P "
					+ " WHERE P.Id_Tavolo = " + tavolo.getId_Tavolo() + " ;");
			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			throw ecc;
		}
	}
}