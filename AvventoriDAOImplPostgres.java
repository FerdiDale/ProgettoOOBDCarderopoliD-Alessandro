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
		String queryTotaleAvventori= "INSERT INTO avventori VALUES(";
		String queryTotaleElencoAvventori = "INSERT INTO elenco_avventori VALUES(";
		ResultSet prova;
		int tavolata=-1;
		try
		{
			ResultSet tavolataDB =DB_Connection.getInstance().getConnection().createStatement().executeQuery("select id_tavolata from tavolata where id_tavolo = "+lista.get(0).getTavoli().get(lista.get(0).getTavoloScelto()).getId_Tavolo()+" AND data = '"+lista.get(0).getData()+"';");
			tavolataDB.next();
			tavolata = tavolataDB.getInt(1);
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Ricerca tavolata in avventori "+ e);
		}
		for(int i = 0; i< lista.size();i++)
			try
			{
				prova = DB_Connection.getInstance().getConnection().createStatement().executeQuery("Select Count(*) from Avventori WHERE N_CID = '"+lista.get(i).getCid().getText()+"';");
				prova.next();
				if(prova.getInt(1)<1) 
					{
						if(lista.get(i).getNtel().getText().isBlank()) queryTotaleAvventori = queryTotaleAvventori+"'"+lista.get(i).getNome().getText()+"','"+lista.get(i).getCognome().getText()+"','"+lista.get(i).getCid().getText()+"')";
						else queryTotaleAvventori = queryTotaleAvventori +"'"+lista.get(i).getNome().getText()+"','"+lista.get(i).getCognome().getText()+"','"+lista.get(i).getCid().getText()+"','"+lista.get(i).getNtel().getText()+"')";
					}
				 queryTotaleElencoAvventori = queryTotaleElencoAvventori +tavolata+",'"+lista.get(i).getCid().getText()+"')";
				 if(i == lista.size()-1)
				 {
					 queryTotaleElencoAvventori = queryTotaleElencoAvventori + ";";
					 queryTotaleAvventori = queryTotaleAvventori + ";";
					 DB_Connection.getInstance().getConnection().createStatement().executeUpdate(queryTotaleAvventori);
					 DB_Connection.getInstance().getConnection().createStatement().executeUpdate(queryTotaleElencoAvventori);
				 }
				 else
				 {
					 queryTotaleElencoAvventori = queryTotaleElencoAvventori + " ,(";
					 queryTotaleAvventori = queryTotaleAvventori+" ,(";
				 }
			}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Aggiunta degli avventori all'elenco avventori "+ e);
		}
		
	}

	public void rimuoviAvventoreDaElencoAvventori(int id_tavolo, String data, Avventori cliente) 
	{
		try
		{
			ResultSet tavolataDB =  DB_Connection.getInstance().getConnection().createStatement().executeQuery("select id_tavolata from tavolata where id_tavolo = "+id_tavolo+" AND data = '"+data+"';");
			tavolataDB.next();
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM elenco_avventori where id_tavolata = "+tavolataDB.getInt(1)+" AND n_cid = '"+cliente.getN_CID()+"';");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "rimozione elenco avventori "+ e);
		}
		//FAi un unico inserimento di tutte le righe in una volta
	}
	

	
	public void aggiungiNuovoavventoreAllaTavolata(int id_tavolo, String data, Avventori avventore) 
	{
		try
		{
			ResultSet tavolataDB = DB_Connection.getInstance().getConnection().createStatement().executeQuery("select id_tavolata from tavolata where id_tavolo = "+id_tavolo+" AND data = '"+data+"';");
			tavolataDB.next();
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("insert into avventori values('"+avventore.getNome()+"','"+avventore.getCognome()+"','"+avventore.getN_CID()+"','"+avventore.getN_tel()+"');");
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("insert into elenco_avventori values("+tavolataDB.getInt(1)+",'"+avventore.getN_CID()+"');");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Aggiunta avventore" +e);
		}
	}
}
