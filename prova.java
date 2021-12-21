import java.sql.*;

public class prova 
{
	public static void main (String[] args)
	{
		Controller c = new Controller();
		InterfacciaSale is = new InterfacciaSale(c,"F&K's", 1);
		is.setVisible(true);
	}
	
}
