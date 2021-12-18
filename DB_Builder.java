import java.sql.*;

public class DB_Builder 
{
	public DB_Builder()
	{
		//Variabile per controllare che il database non esista già
		boolean preesistente = false;
		//Classe di connessione al db
		Connection c = null;
		try 
		{	
			Class.forName("org.postgresql.Driver");
			
			//Connessione al db come utente postgres
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432", "postgres", "Antonio22");		
			//Creazione classe per statement di definizione tabelle e vincoli
			Statement stmt = c.createStatement();
			//Creazione DB
			stmt.executeUpdate("CREATE DATABASE ristoranti;");
			//Nota: ogni volta che bisogna connettersi al db i caratteri 
			//devono essere tutti minuscoli, altrimenti darà errore
			//(database non esistente)
		}
		catch(ClassNotFoundException e)
		{
			
		}
		catch(SQLException e)
		{
			preesistente = true;
		}
		if(!preesistente)
		{
			try
			{
				Class.forName("org.postgresql.Driver");
				//Connessione al database appena creato
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ristoranti", "postgres", "Antonio22");
				//Creazione delle varie tabelle
				Statement stmt = c.createStatement();
				
				stmt.executeUpdate("CREATE TABLE Ristorante"
								+ "(Id_Ristorante SERIAL,"
								+ "Nome VARCHAR(40) NOT NULL,"
								+ "Via VARCHAR(40) NOT NULL,"
								+ "N_Civico INTEGER NOT NULL,"
								+ "CAP CHAR(5) NOT NULL,"
								+ "PRIMARY KEY(IdRistorante),"
								+ "ADD CONSTRAINT UnicaLocalita UNIQUE(Via, NCivico, CAP));");
				
				stmt.executeUpdate("CREATE TABLE Sala"
								+ "(Id_Sala SERIAL,"
								+ "Nome VARCHAR(40) NOT NULL,"
								+ "Id_Ristorante INTEGER NOT NULL,"
								+ "PRIMARY KEY(IdSala),"
								+ "ADD CONSTRAINT Appartenenza FOREIGN KEY(Id_Ristorante) REFERS TO Ristorante(Id_Ristorante)"
								+ "                            ON DELETE CASCADE                    ON UPDATE CASCADE,"
								+ "ADD CONSTRAINT NomeUnicoSalaDelRistorante UNIQUE(Nome,Id_Ristorante));");
				
				stmt.executeUpdate("CREATE TABLE Tavolo"
								+ "(Id_Tavolo SERIAL,"
								+ "Capacita INTEGER NOT NULL,"
								+ "Id_Sala INTEGER NOT NULL,"
								+ "Numero INTEGER NOT NULL"
								+ "PRIMARY KEY(Id_Tavolo),"
								+ "ADD CONSTRAINT DellaSala FOREIGN KEY(Id_Sala) REFERS TO Sala(Id_Sala)"
								+ "			 ON DELETE CASCADE              ON UPDATE CASCADE),"
								+ "ADD CONSTRAINT TavoloPerSala UNIQUE(Id_Sala,Numero));");
			
				stmt.executeUpdate("CREATE TABLE Adiacenza"
								+ "(Id_Tavolo1 INTEGER NOT NULL,"
								+ "Id_Tavolo2 INTEGER NOT NULL,"
								+ "ADD CONSTRAINT Tavolo1 FOREIGN KEY(Id_Tavolo1) REFERS TO Tavolo(Id_Tavolo),"
								+ "ADD CONSTRAINT Tavolo2 FOREIGN KEY(Id_Tavolo2) REFERS TO Tavolo(Id_Tavolo),"
								+ "ADD CONSTRAINT AntiRiflessivo CHECK Id_Tavolo1 <> Id_Tavolo2);");
			
				stmt.executeUpdate("CREATE TABLE Tavolata"
								+ "(Id_Tavolata SERIAL PRIMARY KEY,"
								+ "Data DATE NOT NULL,"
								+ "Id_Tavolo INTEGER NOT NULL,"
								+ "ADD CONSTRAINT Del FOREIGN KEY (Id_tavolo) REFERS TO Tavolo(Id_Tavolo)"
								+ "                   ON DELETE CASCADE                 ON UPDATE CASCADE,"
								+ "ADD CONSTRAINT UnicoPerGiorno UNIQUE(Data,Id_Tavolo));");
			
				stmt.executeUpdate("CREATE TABLE Avventori"
								+ "(Nome VARCHAR(30) NOT NULL,"
								+ "Cognome VARCHAR(30) NOT NULL,"
								+ "N_CID CHAR(9) NOT NULL PRIMARY KEY CHECK N_CID LIKE 'C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]',"
								+ "N_Tel CHAR(10) CHECK N_Tel LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]');");

				stmt.executeUpdate("CREATE TABLE Elenco_Avventori"
								+ "(Id_Tavolata INTEGER NOT NULL,"
								+ "N_CID CHAR(9) NOT NULL PRIMARY KEY CHECK N_CID LIKE 'C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]',"
								+ "ADD CONSTRAINT Tavolata FOREIGN KEY(Id_Tavolata) REFERS TO Tavolata(Id_Tavolata),"
								+ "ADD CONSTRAINT Avventore FOREIGN KEY(N_CID) REFERS TO Avventori(N_CID));");
				
				stmt.executeUpdate("CREATE VIEW N_Avventori AS"
								+ "SELECT T.Id_Tavolata, COUNT (N_CID) AS Num"
								+ "FROM Tavolata AS T INNER JOIN Elenco_Avventori AS EA"
								+ "GROUP BY T.Id_Tavolata;");
				
				stmt.executeUpdate("CREATE TABLE Cameriere"
								+ "(Id_Cameriere SERIAL PRIMARY KEY,"
								+ "CID_Cameriere CHAR(9) NOT NULL CHECK N_CID LIKE 'C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]',"
								+ "Nome VARCHAR(30) NOT NULL,"
								+ "Cognome VARCHAR(30) NOT NULL,"
								+ "Id_Ristorante INTEGER NOT NULL,"
								+ "Data_Ammissione DATE NOT NULL,"
								+ "Data_Licenziamento DATE DEFAULT = NULL,"
								+ "ADD CONSTRAINT LavoraNel FOREIGN KEY(Id_Ristorante) REFERS TO Ristorante(Id_Ristorante)"
								+ "                         ON DELETE CASCADE                    ON UPDATE CASCADE,"
								+ "ADD CONSTRAINT ConsistenzaTemporale CHECK Data_Ammissione<=Data_Licenziamento);");
						
				stmt.executeUpdate("CREATE TABLE Servizio"
								+ "(Id_Cameriere INTEGER NOT NULL,"
								+ "Id_Tavolata INTEGER NOT NULL,"
								+ "ADD CONSTRAINT Cameriere FOREIGN KEY(Id_Cameriere) REFERS TO Cameriere(Id_Cameriere)"
								+ "                         ON DELETE CASCADE                   ON UPDATE CASCADE,"
								+ "ADD CONSTRAINT Tavolata FOREIGN KEY(Id_Tavolata) REFERS TO Tavolata(Id_Tavolata)"
								+ "                        ON DELETE CASCADE                  ON UPDATE CASCADE);");
				
				stmt.executeUpdate("CREATE ASSERTION ConsistenzaServizio"
								+ "CHECK NOT EXIST(SELECT *"
								+ "		           FROM Servizio AS S, Cameriere AS C, Tavolata AS T"
								+ "		           WHERE S.IdCameriere=C.IdCameriere AND S.Id_Tavolata = T.Id_Tavolata"
								+ "                      AND ((C.Data_Licenziamento IS NOT NULL AND C.Data_Licenziamento < T.Data)"
								+ "                      OR C.Data_Assunzione > T.Data));");
				
				stmt.executeUpdate("");
				
			}
			catch(ClassNotFoundException e)
			{
				
			}
			catch(SQLException e)
			{
				
			}
		}
	}
}
