import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
				Cameriere attuale = new Cameriere(risultatoQuery.getInt(1),risultatoQuery.getString(2),risultatoQuery.getString(3),risultatoQuery.getString(4),
						risultatoQuery.getInt(5),risultatoQuery.getString(6),risultatoQuery.getString(7));
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
			ResultSet risultatoQuery= stmt.executeQuery("SELECT distinct CID_Cameriere,Nome,Cognome,Id_Ristorante FROM Cameriere WHERE Id_Ristorante = "+ristorante.getId_Ristorante()+" AND Data_Licenziamento IS NOT NULL AND CID_Cameriere not in (SELECT CID_Cameriere FROM Cameriere WHERE Data_Licenziamento IS NULL);");
			while(risultatoQuery.next())
			{
				Cameriere attuale = new Cameriere(risultatoQuery.getString(1),risultatoQuery.getString(2),risultatoQuery.getString(3),risultatoQuery.getInt(4));
				Risultato.add(attuale);
			}
		}
		catch(SQLException e)
		{
			
		}
		return Risultato;
	}
	
	public void riassumiCameriereLicenziato(Cameriere c,String data)
	{
		try
		{
			System.out.println(data);
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO Cameriere(CID_Cameriere,Nome,Cognome,Id_Ristorante,Data_Ammissione) VALUES ('"+c.getCID_Cameriere()+"','"+c.getNome()+"','"+c.getCognome()+"',"+c.getId_Ristorante()+",DATE'"+data+"');");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void licenziaCameriereAssunto(Cameriere c,String data)
	{
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			stmt.executeUpdate("UPDATE Cameriere SET Data_Licenziamento ='"+data+"' WHERE Id_Cameriere = "+c.getId_Cameriere()+";");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String assumiNuovoCameriere(Cameriere c)
	{
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO Cameriere(CID_Cameriere,Nome,Cognome,Id_Ristorante,Data_Ammissione) "
			+ "VALUES ('"+c.getCID_Cameriere()+"','"+c.getNome()+"','"+c.getCognome()+"',"+c.getId_Ristorante()+",DATE '"+c.getData_Ammissione()+"');");
			return "Nessun_Errore";
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("23514"))
			{
				return "CID_Non_Valido";
			}
			else
			{
				return "Data_Non_Valida";
			}
		}
	}
}
