

import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.sql.*;

public class SalaDAOImplPostgres implements SalaDAO 
{
public ArrayList<Sala> EstraiSaleRistorante(int id_ristorante)
		{
			ArrayList<Sala> risultato = new ArrayList<Sala>();
			try 
			{	
				Connection c = DB_Connection.getInstance().getConnection();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Sala WHERE Id_Ristorante = " + id_ristorante +";");  
				while(rs.next())
				{
					Sala casellasingola = new Sala();
					casellasingola.setId_Sala(rs.getInt(1));
					casellasingola.setNome(rs.getString(2));
					casellasingola.setId_Ristorante(rs.getInt(3));
					risultato.add(casellasingola);
				}
			}
			catch(SQLException e)
			{
				System.out.println(e); 
			}
			JOptionPane.showMessageDialog(null,risultato.toString());
			return risultato;
		}
	public void RimuoviSalaRistorante(Sala s)
	{
		try
		{
			Connection c = DB_Connection.getInstance().getConnection();
			Statement stmt = c.createStatement();
			stmt.executeUpdate("DELETE FROM Sala WHERE Id_Ristorante = " + s.getId_Ristorante()+"AND Id_Sala ="+s.getId_Sala()+";");  
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	
	public void AggiuntaSalaRistorante(int id_ristorante)
	{
		
	}
}
