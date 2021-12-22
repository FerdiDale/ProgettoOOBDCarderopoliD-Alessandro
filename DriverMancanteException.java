import javax.swing.JOptionPane;

public class DriverMancanteException extends ErrorePersonalizzato {

	public void stampaMessaggio() {
		JOptionPane.showMessageDialog(null, "C'e' stato un errore, il driver del DBMS potrebbe"
				+ " non essere stato installato correttamente.\n"
				+ "Riprovare l'installazione"
				+ " e riavviare l'applicativo.", "Errore!", JOptionPane.ERROR_MESSAGE);
	}
	
}