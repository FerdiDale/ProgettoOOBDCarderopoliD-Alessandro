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
}
