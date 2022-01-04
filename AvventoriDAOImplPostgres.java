import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.sql.*;

public class AvventoriDAOImplPostgres implements AvventoriDAO
{
	public ArrayList<Avventori> estraiAvventoriDelTavoloInData(Tavolo tavolo, String data)
	{
		ArrayList<Avventori> risultato = new ArrayList<Avventori>();
		try
		{
			ResultSet rs = DB_Connection.getInstance().getConnection().createStatement().executeQuery("select AV.Nome, AV.Cognome, AV.N_Cid, AV.N_tel "
																									+ "from  Avventori AS AV, Tavolata AS TA, Elenco_Avventori AS EA "
																									+ "WHERE TA.Id_Tavolo = "+tavolo.getId_Tavolo()+" AND TA.data = '"+data+"' AND EA.Id_Tavolata = TA.Id_Tavolata AND AV.N_Cid = EA.N_Cid;");
			while(rs.next())
			{
				risultato.add(new Avventori(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
			}
			return risultato;
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e);
			return risultato;
		}
	}

	
	public void inserimentoMultiploAvventori(ArrayList<InterfacciaAggiuntaDatiAvventore> lista)
	{
		ResultSet prova;
		for(int i = 0; i< lista.size();i++)
			try
			{
				prova = DB_Connection.getInstance().getConnection().createStatement().executeQuery("Select Count(*) from Avventori WHERE N_CID = '"+lista.get(i).getCid().getText()+"';");
				prova.next();
				if(prova.getInt(1)<1) 
					{
						if(lista.get(i).getNtel().getText().isBlank()) DB_Connection.getInstance().getConnection().createStatement().executeUpdate("INSERT INTO Avventori VALUES('"+lista.get(i).getNome().getText()+"','"+lista.get(i).getCognome().getText()+"','"+lista.get(i).getCid().getText()+"');");
						else DB_Connection.getInstance().getConnection().createStatement().executeUpdate("INSERT INTO Avventori VALUES('"+lista.get(i).getNome().getText()+"','"+lista.get(i).getCognome().getText()+"','"+lista.get(i).getCid().getText()+"','"+lista.get(i).getNtel().getText()+"');");
					}
			}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
}
