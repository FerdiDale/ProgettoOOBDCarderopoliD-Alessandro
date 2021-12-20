import java.util.ArrayList;
import java.sql.*;

public class SalaDAOImplPostgres implements SalaDAO 
{
public ArrayList<Sala> EstraiSaleRistorante(int r)
		{
			ArrayList<Sala> risultato = new ArrayList<Sala>();
			Sala casellasingola = new Sala();
			try 
			{	
				Connection c = DB_Connection.getInstance().getConnection();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Sala WHERE Id_Ristorante = " + r +";");  
				while(rs.next())
				{
					casellasingola.Id_Sala = rs.getInt(1);
					casellasingola.Nome = rs.getString(2);
					casellasingola.Id_Ristorante = rs.getInt(3);
					risultato.add(casellasingola);
				}
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
			return risultato;
		}
}
