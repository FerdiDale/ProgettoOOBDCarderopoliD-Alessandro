import javax.swing.JOptionPane;

public class OperazioneFallitaException extends Throwable {

	public void StampaMessaggio(){

		JOptionPane.showMessageDialog(null, "C'e' stato un errore di connnessione, riprovare l'operazione.",
				"Errore!", JOptionPane.ERROR_MESSAGE);
	}
	
}
