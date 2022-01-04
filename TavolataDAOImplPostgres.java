import java.sql.*;

import javax.swing.JOptionPane;

public class TavolataDAOImplPostgres implements TavolataDAO
{
	public void inserimentoTavolata(Tavolata tavolata)
	{
		try
		{
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("INSERT INTO TAVOLATA(id_tavolo,data) VALUES ("+tavolata.getId_tavolo()+",'"+tavolata.getData()+"');");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
