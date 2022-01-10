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
				
				risultato.add(new Avventori(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4) == null ? "" : rs.getString(4)));
			}
			return risultato;
		}
		catch(SQLException e)
		{ 
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
			return risultato;
		}
	}

	
	public void inserimentoMultiploAvventori(ArrayList<InterfacciaAggiuntaDatiAvventore> lista)
	{
		boolean presente;
		boolean ntel;
		boolean nontel;
		int counterAvventoriNtelIniziali = 0;
		int counterAvventoriNoNtelIniziali = 0;
		int counterAvventoriNtel = 0;
		int counterAvventoriNoNtel = 0;
		String queryTotaleAvventoriNoNTel = "INSERT INTO avventori VALUES(";
		String queryTotaleAvventoriConNTel= "INSERT INTO avventori VALUES(";
		String queryTotaleElencoAvventori = "INSERT INTO elenco_avventori VALUES(";
		ResultSet risultato;
		int tavolata=-1;
		try
		{
			ResultSet tavolataDB =DB_Connection.getInstance().getConnection().createStatement().executeQuery("select id_tavolata from tavolata where id_tavolo = "+lista.get(0).getTavoli().get(lista.get(0).getTavoloScelto()).getId_Tavolo()+" AND data = '"+lista.get(0).getData()+"';");
			tavolataDB.next();
			tavolata = tavolataDB.getInt(1);
			for(int i = 0; i<lista.size();i++)
			{
				if(lista.get(i).getNtel().getText().isBlank())
					{
						risultato = DB_Connection.getInstance().getConnection().createStatement().executeQuery("Select Count(*) from Avventori WHERE N_CID = '"+lista.get(i).getCid().getText()+"';");
						risultato.next();
						if(risultato.getInt(1)==0)
						counterAvventoriNoNtelIniziali++;
					}
				else 
					{
						risultato = DB_Connection.getInstance().getConnection().createStatement().executeQuery("Select Count(*) from Avventori WHERE N_CID = '"+lista.get(i).getCid().getText()+"';");
						risultato.next();
						if(risultato.getInt(1)==0)
						counterAvventoriNtelIniziali++;
					}
			}
			counterAvventoriNoNtel = counterAvventoriNoNtelIniziali;
			counterAvventoriNtel = counterAvventoriNtelIniziali;
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
		}
		for(int i = 0; i< lista.size();i++)
		{
				try
				{
					presente = true;
					ntel= false;
					nontel= false;
					risultato = DB_Connection.getInstance().getConnection().createStatement().executeQuery("Select Count(*) from Avventori WHERE N_CID = '"+lista.get(i).getCid().getText()+"';");
					risultato.next();
					if(risultato.getInt(1)<1) 
						{
							presente = false;
							if(lista.get(i).getNtel().getText().isBlank()) 
								{
									nontel= true;
									counterAvventoriNoNtel--;
									queryTotaleAvventoriNoNTel = queryTotaleAvventoriNoNTel+"'"+lista.get(i).getNome().getText()+"','"+lista.get(i).getCognome().getText()+"','"+lista.get(i).getCid().getText()+"')";
								}
							else 
								{
									ntel= true;
									counterAvventoriNtel--;
									queryTotaleAvventoriConNTel = queryTotaleAvventoriConNTel +"'"+lista.get(i).getNome().getText()+"','"+lista.get(i).getCognome().getText()+"','"+lista.get(i).getCid().getText()+"','"+lista.get(i).getNtel().getText()+"')";
								}
						}
            
					 if(presente && !lista.get(i).getNtel().getText().isBlank()) DB_Connection.getInstance().getConnection().createStatement().executeUpdate("UPDATE avventori SET n_tel = '"+lista.get(i).getNtel().getText()+"' WHERE n_cid = '"+lista.get(i).getCid().getText()+"';");
					 queryTotaleElencoAvventori = queryTotaleElencoAvventori +tavolata+",'"+lista.get(i).getCid().getText()+"')";						
					
					if(counterAvventoriNtel>0 && ntel) queryTotaleAvventoriConNTel = queryTotaleAvventoriConNTel+" ,(";
					else if(counterAvventoriNtel == 0 && ntel) queryTotaleAvventoriConNTel = queryTotaleAvventoriConNTel+";";
					if(counterAvventoriNoNtel>0 && nontel) queryTotaleAvventoriNoNTel = queryTotaleAvventoriNoNTel + " ,(";
					else if(counterAvventoriNoNtel == 0 && nontel) queryTotaleAvventoriNoNTel = queryTotaleAvventoriNoNTel + ";";
					 if(i == lista.size()-1)
					 {
						 queryTotaleElencoAvventori = queryTotaleElencoAvventori + ";";
						 if(counterAvventoriNtelIniziali>0) DB_Connection.getInstance().getConnection().createStatement().executeUpdate(queryTotaleAvventoriConNTel);
						 if(counterAvventoriNoNtelIniziali>0) DB_Connection.getInstance().getConnection().createStatement().executeUpdate(queryTotaleAvventoriNoNTel);
						 DB_Connection.getInstance().getConnection().createStatement().executeUpdate(queryTotaleElencoAvventori);
					 }
					 else queryTotaleElencoAvventori = queryTotaleElencoAvventori + " ,(";
				
				}
			catch(SQLException e)
			{
				OperazioneFallitaException ecc = new OperazioneFallitaException();
				ecc.stampaMessaggio();
			}
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
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
		}
	}
}
