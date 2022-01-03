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
			ResultSet rs = DB_Connection.getInstance().getConnection().createStatement().executeQuery("SELECT T.id_tavolo,T.numero,P.posx,P.posy,P.dimx,P.dimy "
																									+ "FROM Tavolo AS T, Posizioni as P "
																									+ "WHERE T.id_tavolo = P.id_tavolo AND T.Id_sala = "+ salaScelta.getId_Sala()+";");
			while(rs.next())
			{
				risultato.add(new Tavolo(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
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
	
	public void inserisciNuovoTavolo(Tavolo tavoloNuovo) throws OperazioneFallitaException
	{
		try
		{
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("INSERT INTO tavolo(Capacita,id_sala,Numero) VALUES("+tavoloNuovo.getCapacita()+","+tavoloNuovo.getSala_App().getId_Sala()+","+tavoloNuovo.getNumero()+");");
			ResultSet rs = DB_Connection.getInstance().getConnection().createStatement().executeQuery("select id_tavolo from tavolo where id_sala = "+tavoloNuovo.getSala_App().getId_Sala()+" AND numero = "+ tavoloNuovo.getNumero()+";");
			rs.next();
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("INSERT INTO posizioni VALUES("+rs.getInt(1)+","+tavoloNuovo.getPosX()+","+tavoloNuovo.getPosY()+","+tavoloNuovo.getDimX()+","+tavoloNuovo.getDimY()+");");			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e);
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			throw ecc;
		}
	}
	
	public ArrayList<Tavolo> tavoliOccupatiInData(String data, Sala sala)
	{
	//Query per ricavare i tavoli occupati nella data in input, nota: basta fare un join tra tavolo e tavolata dove l'id coincide e la data è quella in input.
	}
}