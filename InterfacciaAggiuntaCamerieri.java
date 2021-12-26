import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
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
	private JButton bottoneOk;
	public InterfacciaAggiuntaCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Modulo di assunzione di "+ristorante.getNome());
		this.theController = theController;
		getContentPane().setLayout(null);
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		JLabel etichettaNome = new JLabel("Nome");
		etichettaNome.setBounds(10, 11, 46, 14);
		getContentPane().add(etichettaNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(10, 36, 86, 20);
		getContentPane().add(textFieldNome);
		textFieldNome.setColumns(30);
		
		JLabel etichettaCognome = new JLabel("Cognome");
		etichettaCognome.setBounds(200, 11, 46, 14);
		getContentPane().add(etichettaCognome);
		
		textFieldCognome = new JTextField();
		textFieldCognome.setBounds(200, 36, 86, 20);
		getContentPane().add(textFieldCognome);
		textFieldCognome.setColumns(30);
		
		JLabel etichettaCID = new JLabel("Numero CID");
		etichettaCID.setBounds(10, 75, 86, 14);
		getContentPane().add(etichettaCID);
		
		textFieldCID = new JTextField();
		textFieldCID.setBounds(10, 100, 86, 20);
		getContentPane().add(textFieldCID);
		textFieldCID.setColumns(9);
		
		JLabel etichettaDataAssunzione = new JLabel("Data di assunzione");
		etichettaDataAssunzione.setBounds(200, 75, 104, 14);
		getContentPane().add(etichettaDataAssunzione);
		
		textFieldDataAssunzione = new JTextField();
		textFieldDataAssunzione.setBounds(200, 100, 86, 20);
		getContentPane().add(textFieldDataAssunzione);
		textFieldDataAssunzione.setColumns(10);
		
		bottoneOk = new JButton("Ok");
		bottoneOk.setBounds(255, 152, 89, 23);
		bottoneOk.setEnabled(false);
		getContentPane().add(bottoneOk);
		
		textFieldDataAssunzione.setFocusable(true);
		textFieldNome.setFocusable(true);
		textFieldCognome.setFocusable(true);
		textFieldCID.setFocusable(true);
		
		GestoreTesti handler = new GestoreTesti();
		
		textFieldDataAssunzione.addKeyListener(handler);
		textFieldNome.addKeyListener(handler);
		textFieldCognome.addKeyListener(handler);
		textFieldCID.addKeyListener(handler);
		
	}
	
	private class GestoreTesti implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			if(textFieldDataAssunzione.getText().isBlank() || textFieldNome.getText().isBlank() || 
					textFieldCognome.getText().isBlank() || textFieldCID.getText().isBlank())
			{
				bottoneOk.setEnabled(false);
			}
			else
				bottoneOk.setEnabled(true);
		}
		public void keyTyped(KeyEvent e)
		{
			
		}
		public void keyReleased(KeyEvent e)
		{
			
		}
	}
}
