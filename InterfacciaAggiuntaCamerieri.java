import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfacciaAggiuntaCamerieri extends JFrame
{
	private Controller theController;
	private JTextField textFieldNome;
	private JTextField textFieldCognome;
	private JTextField textFieldCID;
	private JTextField textFieldDataAssunzione;
	public InterfacciaAggiuntaCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Modulo di assunzione di "+ristorante.getNome());
		this.theController = theController;
		getContentPane().setLayout(null);
		
		JLabel etichettaNome = new JLabel("New label");
		etichettaNome.setBounds(10, 11, 46, 14);
		getContentPane().add(etichettaNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(10, 36, 86, 20);
		getContentPane().add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel etichettaCognome = new JLabel("New label");
		etichettaCognome.setBounds(200, 11, 46, 14);
		getContentPane().add(etichettaCognome);
		
		textFieldCognome = new JTextField();
		textFieldCognome.setBounds(200, 36, 86, 20);
		getContentPane().add(textFieldCognome);
		textFieldCognome.setColumns(10);
		
		JLabel etichettaCID = new JLabel("New label");
		etichettaCID.setBounds(10, 75, 46, 14);
		getContentPane().add(etichettaCID);
		
		textFieldCID = new JTextField();
		textFieldCID.setBounds(10, 100, 86, 20);
		getContentPane().add(textFieldCID);
		textFieldCID.setColumns(10);
		
		JLabel etichettaDataAssunzione = new JLabel("New label");
		etichettaDataAssunzione.setBounds(200, 75, 46, 14);
		getContentPane().add(etichettaDataAssunzione);
		
		textFieldDataAssunzione = new JTextField();
		textFieldDataAssunzione.setBounds(200, 100, 86, 20);
		getContentPane().add(textFieldDataAssunzione);
		textFieldDataAssunzione.setColumns(10);
		
		JButton bottoneOk = new JButton("Ok");
		bottoneOk.setBounds(255, 152, 89, 23);
		getContentPane().add(bottoneOk);
		
	}
}
