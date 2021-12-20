import java.sql.*;

import javax.swing.JOptionPane;

public class DB_Builder 
{
	
	public DB_Builder() throws CreazioneErrataDatabaseException, DriverMancanteException
	{
		boolean preesistente = false;
		try 
		{	
			//Connessione con url del server senza database in caso il database non sia presente
			//(La connessione con accesso al database è gestita dalla classe singleton DB_Connection)
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "");	
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE DATABASE ristorantidb;");
			//Nota: ogni volta che bisogna connettersi al db i caratteri 
			//devono essere tutti minuscoli, altrimenti darà errore
			//(database non esistente)
			conn.close();
		}
		catch(ClassNotFoundException e)
		{
			DriverMancanteException ecc = new DriverMancanteException();
			throw ecc;
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("42P04")) preesistente = true; //Stato di SQL in caso di Database già esistente
			else 
			{
				JOptionPane.showMessageDialog(null,"C'è stato un errore, il database non è stato creato correttamente\n"
						+ "Riprovare a riavviare l'applicativo.", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(!preesistente)
		{
			try
			{
				Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
				
				stmt.executeUpdate("CREATE TABLE Ristorante"
								+ "(Id_Ristorante SERIAL,"
								+ "Nome VARCHAR(40) NOT NULL,"
								+ "Via VARCHAR(40) NOT NULL,"
								+ "N_Civico INTEGER NOT NULL,"
								+ "Citta VARCHAR(40) NOT NULL,"
								+ "PRIMARY KEY(Id_Ristorante),"
								+ "CONSTRAINT UnicaLocalita UNIQUE(Via, N_Civico, Citta));");
				
				stmt.executeUpdate("CREATE TABLE Sala"
								+ "(Id_Sala SERIAL,"
								+ "Nome VARCHAR(40) NOT NULL,"
								+ "Id_Ristorante INTEGER NOT NULL,"
								+ "PRIMARY KEY(Id_Sala),"
								+ "CONSTRAINT Appartenenza FOREIGN KEY(Id_Ristorante) REFERENCES Ristorante(Id_Ristorante)"
								+ "                            ON DELETE CASCADE                    ON UPDATE CASCADE,"
								+ "CONSTRAINT NomeUnicoSalaDelRistorante UNIQUE(Nome,Id_Ristorante));");
			
				stmt.executeUpdate("CREATE TABLE Tavolo"
								+ "(Id_Tavolo SERIAL,"
								+ "Capacita INTEGER NOT NULL,"
								+ "Id_Sala INTEGER NOT NULL,"
								+ "Numero INTEGER NOT NULL,"
								+ "PRIMARY KEY(Id_Tavolo),"
								+ "CONSTRAINT DellaSala FOREIGN KEY(Id_Sala) REFERENCES Sala(Id_Sala)"
								+ "			 ON DELETE CASCADE              ON UPDATE CASCADE,"
								+ "CONSTRAINT TavoloPerSala UNIQUE(Id_Sala,Numero));");
			
				stmt.executeUpdate("CREATE TABLE Adiacenza"
								+ "(Id_Tavolo1 INTEGER NOT NULL,"
								+ "Id_Tavolo2 INTEGER NOT NULL,"
								+ "CONSTRAINT Tavolo1 FOREIGN KEY(Id_Tavolo1) REFERENCES Tavolo(Id_Tavolo),"
								+ "CONSTRAINT Tavolo2 FOREIGN KEY(Id_Tavolo2) REFERENCES Tavolo(Id_Tavolo),"
								+ "CONSTRAINT AntiRiflessivo CHECK (Id_Tavolo1 <> Id_Tavolo2));");
			
				stmt.executeUpdate("CREATE TABLE Tavolata"
								+ "(Id_Tavolata SERIAL PRIMARY KEY,"
								+ "Data DATE NOT NULL,"
								+ "Id_Tavolo INTEGER NOT NULL,"
								+ "CONSTRAINT Del FOREIGN KEY (Id_tavolo) REFERENCES Tavolo(Id_Tavolo)"
								+ "                   ON DELETE CASCADE                 ON UPDATE CASCADE,"
								+ "CONSTRAINT UnicoPerGiorno UNIQUE(Data,Id_Tavolo));");
			
				stmt.executeUpdate("CREATE TABLE Avventori"
								+ "(Nome VARCHAR(30) NOT NULL,"
								+ "Cognome VARCHAR(30) NOT NULL,"
								+ "N_CID CHAR(9) NOT NULL PRIMARY KEY CHECK (N_CID LIKE 'C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]'),"
								+ "N_Tel CHAR(10) CHECK (N_Tel LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'));");
			
				stmt.executeUpdate("CREATE TABLE Elenco_Avventori"
								+ "(Id_Tavolata INTEGER NOT NULL,"
								+ "N_CID CHAR(9) NOT NULL PRIMARY KEY CHECK (N_CID LIKE 'C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]'),"
								+ "CONSTRAINT InTavolata FOREIGN KEY(Id_Tavolata) REFERENCES Tavolata(Id_Tavolata),"
								+ "CONSTRAINT DiAvventore FOREIGN KEY(N_CID) REFERENCES Avventori(N_CID));");
			
				stmt.executeUpdate("CREATE VIEW N_Avventori AS "
								+ "SELECT T.Id_Tavolata, COUNT (EA.N_CID) AS Num "
								+ "FROM Tavolata AS T NATURAL JOIN Elenco_Avventori AS EA "
								+ "GROUP BY T.Id_Tavolata;");
			
				stmt.executeUpdate("CREATE TABLE Cameriere"
								+ "(Id_Cameriere SERIAL PRIMARY KEY,"
								+ "CID_Cameriere CHAR(9) NOT NULL CHECK (CID_Cameriere LIKE 'C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]'),"
								+ "Nome VARCHAR(30) NOT NULL,"
								+ "Cognome VARCHAR(30) NOT NULL,"
								+ "Id_Ristorante INTEGER NOT NULL,"
								+ "Data_Ammissione DATE NOT NULL,"
								+ "Data_Licenziamento DATE DEFAULT NULL,"
								+ "CONSTRAINT LavoraNel FOREIGN KEY(Id_Ristorante) REFERENCES Ristorante(Id_Ristorante)"
								+ "                         ON DELETE CASCADE                    ON UPDATE CASCADE,"
								+ "CONSTRAINT ConsistenzaTemporale CHECK (Data_Ammissione<=Data_Licenziamento));");
						
				stmt.executeUpdate("CREATE TABLE Servizio "
								+ "(Id_Cameriere INTEGER NOT NULL, "
								+ "Id_Tavolata INTEGER NOT NULL, "
								+ "CONSTRAINT Cameriere FOREIGN KEY(Id_Cameriere) REFERENCES Cameriere(Id_Cameriere)"
								+ "                         ON DELETE CASCADE                   ON UPDATE CASCADE,"
								+ "CONSTRAINT Tavolata FOREIGN KEY(Id_Tavolata) REFERENCES Tavolata(Id_Tavolata)"
								+ "                        ON DELETE CASCADE                  ON UPDATE CASCADE);");
				
				stmt.executeUpdate("CREATE TABLE Posizioni"
						          +"(PosX INTEGER NOT NULL,"
						          +"PosY INTEGER NOT NULL,"
						          +"DimX INTEGER NOT NULL,"
						          +"DimY INTEGER NOT NULL);");
				
				stmt.executeUpdate ("CREATE FUNCTION InserisciSimmetrico() RETURNS TRIGGER\r"
								+" LANGUAGE plpgsql AS $$\r"
								+ "DECLARE\r"
								+ "CheckConto INTEGER;\r"
								+ "BEGIN\r"
								+ "SELECT COUNT(*) INTO CheckConto\r"
								+ "FROM Adiacenza AS A\r"
								+ "WHERE A.Id_Tavolo1 = NEW.Id_Tavolo2 AND A.Id_Tavolo2 = NEW.Id_Tavolo1;\r"
								+ "IF (CheckConto=0) THEN\r"
								+ "	INSERT INTO Adiacenza\r"
								+ "	VALUES (NEW.Id_Tavolo2, NEW.Id_Tavolo1);\r"
								+ "END IF;\r"
								+ "END;\r"
								+ "$$\r");
					
				stmt.executeUpdate("CREATE TRIGGER SimmetriaInserimento "
								+ "AFTER INSERT ON Adiacenza "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION InserisciSimmetrico();"); 
				
				stmt.executeUpdate ("CREATE FUNCTION CancellaSimmetrico() RETURNS TRIGGER\r"
								+ "LANGUAGE plpgsql AS $$\r"
								+ "DECLARE\r"
								+ "CheckConto INTEGER;\r"
								+ "BEGIN\r"
								+ "SELECT COUNT(*) INTO CheckConto\r"
								+ "FROM Adiacenza AS A\r"
								+ "WHERE A.Id_Tavolo1 = OLD.Id_Tavolo2 AND A.Id_Tavolo2 = OLD.Id_Tavolo1;\r"
								+ "IF (CheckConto>0) THEN\r"
								+ "	DELETE FROM Adiacenza\r"
								+ "	WHERE A.Id_Tavolo1 = OLD.Id_Tavolo2 AND A.Id_Tavolo2 = OLD.Id_Tavolo1;\r"
								+ "END IF;\r"
								+ "END;\r"
								+ "$$\r");
				
				stmt.executeUpdate("CREATE TRIGGER SimmetriaCancellazione "
								+ "AFTER DELETE ON Adiacenza "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION CancellaSimmetrico();"); 
						
				stmt.executeUpdate ("CREATE FUNCTION ModificaSimmetrico() RETURNS TRIGGER\r"
								+ "LANGUAGE plpgsql AS $$\r"
								+ "DECLARE\r"
								+ "CheckConto INTEGER;\r"
								+ "BEGIN\r"
								+ "SELECT COUNT(*) INTO CheckConto\r"
								+ "FROM Adiacenza AS A\r"
								+ "WHERE A.Id_Tavolo1 = OLD.Id_Tavolo2 AND A.Id_Tavolo2 = OLD.Id_Tavolo1;\r"
								+ "IF (CheckConto>0) THEN\r"
								+ "UPDATE Adiacenza AS A\r"
								+ "SET A.Id_Tavolo1 = NEW.Id_Tavolo2, A.Id_Tavolo2 = NEW.Id_Tavolo1\r"
								+ "WHERE A.Id_Tavolo1 = OLD.Id_Tavolo2 AND A.Id_Tavolo2 = OLD.Id_Tavolo1;\r"
								+ "END IF;\r"
								+ "END;\r"
								+ "$$\r"); 
				
				stmt.executeUpdate("CREATE TRIGGER SimmetriaModifica "
								+ "AFTER UPDATE ON Adiacenza "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION ModificaSimmetrico();"); 
			
				stmt.executeUpdate("CREATE FUNCTION ConsistenzaServizioTavolataInserimento() RETURNS TRIGGER\r"
								+ "LANGUAGE plpgsql as $$\r"
								+ "DECLARE\r"
								+ "CheckConto INTEGER;\r"
								+ "BEGIN\r"
								+ "SELECT COUNT(*) INTO CheckConto\r"
								+ "FROM Cameriere AS C, Tavolata AS T\r"
								+ "WHERE C.Id_Cameriere=NEW.Id_Cameriere AND T.Id_Tavolata = NEW.Id_Tavolata AND (C.Data_Ammissione>T.Data OR C.Data_Licenziamento <T.Data);\r"
								+ "IF (CheckConto > 0) THEN\r"
								+ "DELETE FROM Servizio\r"
								+ "WHERE Id_Cameriere=NEW.Id_Cameriere AND Id_Tavolata = NEW.Id_Tavolata;\r"
								+ "END IF;\r"
								+ "END;\r"
								+ "$$;\r");
				
				stmt.executeUpdate("CREATE TRIGGER ConsistenzaServizioInserimento "
								+ "AFTER INSERT ON Servizio "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION ConsistenzaServizioTavolataInserimento(); ");
			
				stmt.executeUpdate("CREATE FUNCTION ConsistenzaServizioTavolataModificaServizio() RETURNS TRIGGER\r"
								+ "LANGUAGE plpgsql as $$\r"
								+ "DECLARE\r"
								+ "CheckConto INTEGER;\r"
								+ "BEGIN\r\n"
								+ "SELECT COUNT(*) INTO CheckConto\r"
								+ "FROM Cameriere AS C, Tavolata AS T\r"
								+ "WHERE C.Id_Cameriere=NEW.Id_Cameriere AND T.Id_Tavolata = NEW.Id_Tavolata AND (C.Data_Ammissione>T.Data OR C.Data_Licenziamento <T.Data);\r"
								+ "IF (CheckConto>0) THEN\r"
								+ "UPDATE Servizio \r"
								+ "SET Id_Cameriere = OLD.Id_Cameriere, Id_Tavolata = OLD.Id_Tavolata\r"
								+ "WHERE Id_Cameriere = NEW.Id_Cameriere AND Id_Tavolata = NEW.Id_Tavolata;\r"
								+ "END IF;\r"
								+ "END;\r"
								+ "$$;\r");
				
				stmt.executeUpdate("CREATE TRIGGER ConsistenzaServizioUpdate "
								+ "AFTER UPDATE ON Servizio "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION  ConsistenzaServizioTavolataModificaServizio(); ");
				
				stmt.executeUpdate("CREATE FUNCTION ConsistenzaServizioTavolataModificaAmmissioneLicenziamento() RETURNS TRIGGER \r"
								+ "LANGUAGE plpgsql AS $$ \r"
								+ "DECLARE \r"
								+ "Check_AConto INTEGER; \r"
								+ "Check_LConto INTEGER; \r"
								+ "BEGIN \r"
								+ "SELECT COUNT(*) INTO Check_AConto\r"
								+ "FROM Servizio AS S, Tavolata AS T\r"
								+ "WHERE S.Id_Cameriere = NEW.Id_Cameriere AND S.Id_Tavolata=T.Id_Tavolata AND NEW.Data_Ammissione>T.Data; \r"
								+ "SELECT COUNT(*) INTO Check_LConto \r"
								+ "FROM Servizio AS S, Tavolata AS T \r"
								+ "WHERE S.Id_Cameriere = NEW.Id_Cameriere AND S.Id_Tavolata=T.Id_Tavolata AND NEW.Data_Licenziamento<T.Data; \r"
								+ "IF (Check_AConto > 0) THEN \r"
								+ "UPDATE Cameriere \r"
								+ "SET Data_Ammissione = OLD.Data_Ammissione \r"
								+ "WHERE Id_Cameriere = NEW.Id_Cameriere; \r"
								+ "END IF; \r"
								+ "IF (Check_LConto > 0) THEN \r"
								+ "UPDATE Cameriere \r"
								+ "SET Data_Licenziamento = OLD.Data_Licenziamento \r"
								+ "WHERE Id_Cameriere = NEW.Id_Cameriere; \r"
								+ "END IF; \r"
								+ "END; \r"
								+ "$$; \r");
				
				stmt.executeUpdate("CREATE TRIGGER ConsistenzaServizioAggiornamentoAmmissioneLicenziamento "
								+ "AFTER UPDATE ON Cameriere "
								+ "FOR EACH ROW "
								+ "WHEN (OLD.Data_Ammissione <> NEW.Data_Ammissione OR OLD.Data_Licenziamento <> NEW.Data_Licenziamento) "
								+ "EXECUTE FUNCTION  ConsistenzaServizioTavolataModificaAmmissioneLicenziamento(); ");
			}
			catch(SQLException e)
			{
				CreazioneErrataDatabaseException ecc = new CreazioneErrataDatabaseException();
				ecc.StampaMessaggio();
			}
		}
	}
}
