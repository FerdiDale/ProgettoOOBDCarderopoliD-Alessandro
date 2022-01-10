import java.util.ArrayList;
import java.util.regex.Pattern;

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

	
	public void inserimentoMultiploAvventori(ArrayList<InterfacciaAggiuntaDatiAvventore> lista, int mode /* 1 di visualizzazione, 0 aggiunta occupazione*/)
	{
		boolean presente;
		boolean ntel;
		boolean nontel;
		boolean valido = true;
		int counterAvventoriNtelIniziali = 0;
		int counterAvventoriNoNtelIniziali = 0;
		int removalNTel = 0; //per rimuovere dagli avventori inseribili con numero di telefono (inizialmente tutti) quelli che si trovano già nel db. Altrimenti la query risulterebbe errata
		int removalNoNTel = 0; //stesso ma per quelli senza numero di telefono
		int counterAvventoriNtel = 0;
		int counterAvventoriNoNtel = 0;
		String queryTotaleAvventoriNoNTel = "INSERT INTO avventori VALUES(";
		String queryTotaleAvventoriConNTel= "INSERT INTO avventori VALUES(";
		ArrayList<String> elencoAvventori = new ArrayList<String>();
		ArrayList<Integer> giaEseguiti = new ArrayList<Integer>();
		ResultSet prova;
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
						counterAvventoriNoNtelIniziali++;
						prova = DB_Connection.getInstance().getConnection().createStatement().executeQuery("Select Count(*) from Avventori WHERE N_CID = '"+lista.get(i).getCid().getText()+"';");
						prova.next();
						if(prova.getInt(1)!=0) removalNoNTel++;
					}
				else 
					{
						counterAvventoriNtelIniziali++;
						prova = DB_Connection.getInstance().getConnection().createStatement().executeQuery("Select Count(*) from Avventori WHERE N_CID = '"+lista.get(i).getCid().getText()+"';");
						prova.next();
						if(prova.getInt(1)!=0) removalNTel++;
					}
			}
			counterAvventoriNoNtel = counterAvventoriNoNtelIniziali - removalNoNTel;
			counterAvventoriNtel = counterAvventoriNtelIniziali - removalNTel;
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
						prova = DB_Connection.getInstance().getConnection().createStatement().executeQuery("Select Count(*) from Avventori WHERE N_CID = '"+lista.get(i).getCid().getText()+"';");
						prova.next();
						if(prova.getInt(1)<1) 
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
	            
						elencoAvventori.add("call unicotavperdata('"+lista.get(i).getCid().getText()+"',"+tavolata+");");	
						System.out.println("è stato aggiunto un nuovo elemento ad elenco avventori");
						
						 if(presente && !lista.get(i).getNtel().getText().isBlank()) DB_Connection.getInstance().getConnection().createStatement().executeUpdate("UPDATE avventori   SET n_tel = '"+lista.get(i).getNtel().getText()+"' WHERE n_cid = '"+lista.get(i).getCid().getText()+"';");
									
						
						if(counterAvventoriNtel>0 && ntel) queryTotaleAvventoriConNTel = queryTotaleAvventoriConNTel+" ,(";
						else if(counterAvventoriNtel == 0 && ntel) queryTotaleAvventoriConNTel = queryTotaleAvventoriConNTel+";";
						if(counterAvventoriNoNtel>0 && nontel) queryTotaleAvventoriNoNTel = queryTotaleAvventoriNoNTel + " ,(";
						else if(counterAvventoriNoNtel == 0 && nontel) queryTotaleAvventoriNoNTel = queryTotaleAvventoriNoNTel + ";\n";
						 if(i == lista.size()-1)
						 {
							 if(counterAvventoriNtelIniziali - removalNTel>0) DB_Connection.getInstance().getConnection().createStatement().executeUpdate(queryTotaleAvventoriConNTel);
							 if(counterAvventoriNoNtelIniziali - removalNoNTel>0) DB_Connection.getInstance().getConnection().createStatement().executeUpdate(queryTotaleAvventoriNoNTel);
						 }				
					}
				catch(SQLException e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
		}
			 for (int j = 0; j<elencoAvventori.size(); j++)
			 {
				if(!giaEseguiti.contains(j) && valido)	 
					 try
					 {
					 	DB_Connection.getInstance().getConnection().createStatement().executeUpdate(elencoAvventori.get(j));
					 }
					 catch(SQLException e)
					 {
						 if(Pattern.matches(".*L avventore si trova già in un altra tavolata nella data scelta!(.*\n.*)*", e.getMessage())) 
							 {
							 	JOptionPane.showMessageDialog(null, "L'avventore con CID "+ lista.get(j).getCid().getText()+" è presente in un altra tavolata in questa data. Non sarà inserito nella tavolata corrente.","Informazione",JOptionPane.INFORMATION_MESSAGE);
							 	if(lista.get(j).getNtel().getText().isBlank()) counterAvventoriNoNtelIniziali --;
							 	else 
							 	{//Se ci sta ancora qualche avventore con un numero di telefono, lo inserisco per non attivare il trigger e lo salvo nell'arraylist per non inserirlo 2 volte
								 	counterAvventoriNtelIniziali --;
							 		if(counterAvventoriNtelIniziali >0)
									 	for(int k = j+1; k<lista.size();k++)
									 	{
									 		if(!lista.get(k).getNtel().getText().isBlank()) 
										 		try
										 		{
										 			DB_Connection.getInstance().getConnection().createStatement().executeUpdate(elencoAvventori.get(k));
										 			giaEseguiti.add(k);
										 			System.out.println(giaEseguiti);
										 		}
										 		catch(SQLException l)
										 		{
										 			if(Pattern.matches(".*L avventore si trova già in un altra tavolata nella data scelta!(.*\n.*)*", e.getMessage())) 
													 {
													 	JOptionPane.showMessageDialog(null, "L'avventore con CID "+ lista.get(j).getCid().getText()+" è presente in un altra tavolata in questa data. Non sarà inserito nella tavolata corrente.","Informazione",JOptionPane.INFORMATION_MESSAGE);
													 	counterAvventoriNtelIniziali --;
													 }
										 			if(counterAvventoriNtelIniziali == 0)  JOptionPane.showMessageDialog(null, "La tavolata corrente non ha clienti con numero di telefono inseribili, per cui non verrà registrata. Si prega di riprovare.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
										 		}	 	
									 	}
							 		 else  if(counterAvventoriNtelIniziali == 0 && mode == 0)
									 	{
							 			 	valido = false;
							 			 	//gestisci la situazione dall'aggiunta dell'avventore singolo
								 			JOptionPane.showMessageDialog(null, "La tavolata corrente non ha clienti con numero di telefono inseribili, per cui non verrà registrata. Si prega di riprovare.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
											 try
											 {
												 DB_Connection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Tavolata WHERE id_tavolata = "+ tavolata+";");
										 
											 }
											 catch(SQLException p)
											 {
												 
											 }
									 	}
							 	}
							 }
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
