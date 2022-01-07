import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.*;
public class SalaDAOImplPostgres implements SalaDAO 
{
public ArrayList<Sala> EstraiSaleRistorante(Ristorante ristoranteCorrente)
		{
			ArrayList<Sala> risultato = new ArrayList<Sala>();
			try 
			{	
				Connection c = DB_Connection.getInstance().getConnection();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Sala WHERE Id_Ristorante = " 
												+ ristoranteCorrente.getId_Ristorante() +";");  
				while(rs.next())
				{
					Sala casellasingola = new Sala();
					casellasingola.setId_Sala(rs.getInt(1));
					casellasingola.setNome(rs.getString(2));
					casellasingola.setRistoranteDiAppartenenza(ristoranteCorrente);
					risultato.add(casellasingola);
				}
			}
			catch(SQLException e)
			{
				OperazioneFallitaException ecc = new OperazioneFallitaException();
				ecc.stampaMessaggio();
			}
			return risultato;
		}
	public void RimuoviSalaRistorante(Sala s)
	{
		try
		{
			Connection c = DB_Connection.getInstance().getConnection();
			Statement stmt = c.createStatement();
			stmt.executeUpdate("DELETE FROM Sala WHERE Id_Sala ="+s.getId_Sala()+";");  
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
		}
	}
	
	public void AggiuntaSalaRistorante(String nomeSala,int id_ristorante)
	{
		try 
		{
			Connection c = DB_Connection.getInstance().getConnection();
			Statement stmt = c.createStatement();
			stmt.executeUpdate("INSERT INTO Sala(Nome,Id_Ristorante) VALUES ('"+ nomeSala +"',"+id_ristorante+");");
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("23505")){
				NomeSalaUgualeException ecc = new NomeSalaUgualeException();
				ecc.stampaMessaggio();
			}
			else if (e.getSQLState().equals("42601")) 
			{
				StringheNonValideException ecc = new StringheNonValideException();
				ecc.stampaMessaggio();
			}
			else
				JOptionPane.showMessageDialog(null,"L'inserimento non e' andato a buon fine. Si prega di riprovare","Errore!",JOptionPane.ERROR_MESSAGE);
		}
	}
	public void modificaSala(String nome, Sala sala) {
		try 
		{
			Connection c = DB_Connection.getInstance().getConnection();
			Statement stmt = c.createStatement();
			stmt.executeUpdate("UPDATE SALA AS S SET Nome = '" + nome + "' WHERE S.Id_Sala = " + sala.getId_Sala() + " ;");
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("23505")) {
				NomeSalaUgualeException ecc = new NomeSalaUgualeException();
				ecc.stampaMessaggio();
			}
			else if (e.getSQLState().equals("42601")) 
			{
				StringheNonValideException ecc = new StringheNonValideException();
				ecc.stampaMessaggio();
			}
			else
				JOptionPane.showMessageDialog(null,"La modifica non e' andato a buon fine. Si prega di riprovare"+e.getSQLState(),"Errore!",JOptionPane.ERROR_MESSAGE);
		}

	}	
}
