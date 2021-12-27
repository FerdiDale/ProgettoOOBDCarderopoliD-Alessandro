import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class prova 
{
	public static void main (String[] args) throws ParseException
	{
		Controller c = new Controller();
		Ristorante ristorante = new Ristorante();
		ristorante.setCitta("Napoli");
		ristorante.setId_Ristorante(1);
		ristorante.setN_Civico(128);
		ristorante.setVia("Cilea");
		ristorante.setNome("F&K");
		InterfacciaGestioneCamerieri a = new InterfacciaGestioneCamerieri(ristorante,c);
		a.setVisible(true);

	}
	
}
