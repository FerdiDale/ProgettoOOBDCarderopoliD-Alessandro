import java.sql.*;
import java.util.ArrayList;

public class CameriereDAOImplPostgres 
{
	public ArrayList<Cameriere> EstraiCamerieriInServizio(Ristorante ristorante)
	{
		ArrayList<Cameriere> Risultato = new ArrayList<Cameriere>();
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultatoQuery= stmt.executeQuery("SELECT * FROM Cameriere WHERE Id_Ristorante = "+ristorante.getId_Ristorante()+" AND Data_Licenziamento IS NULL;");
			while(risultatoQuery.next())
			{
				Cameriere attuale = new Cameriere(risultatoQuery.getInt(1),risultatoQuery.getString(2),risultatoQuery.getString(3),risultatoQuery.getString(4),risultatoQuery.getInt(5),risultatoQuery.getString(6),risultatoQuery.getString(7));
				Risultato.add(attuale);
			}
		}
		catch(SQLException e)
		{
			
		}
		return Risultato;
	}
	public ArrayList<Cameriere> EstraiCamerieriLicenziati(Ristorante ristorante)
	{
		ArrayList<Cameriere> Risultato = new ArrayList<Cameriere>();
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultatoQuery= stmt.executeQuery("SELECT * FROM Cameriere WHERE Id_Ristorante = "+ristorante.getId_Ristorante()+" AND Data_Licenziamento IS NOT NULL;");
			while(risultatoQuery.next())
			{
				Cameriere attuale = new Cameriere(risultatoQuery.getInt(1),risultatoQuery.getString(2),risultatoQuery.getString(3),risultatoQuery.getString(4),risultatoQuery.getInt(5),risultatoQuery.getString(6),risultatoQuery.getString(7));
				Risultato.add(attuale);
			}
		}
		catch(SQLException e)
		{
			
		}
		return Risultato;
	}
}