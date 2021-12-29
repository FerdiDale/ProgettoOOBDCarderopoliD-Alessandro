import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfacciaAggiuntaCamerieri extends JFrame
{
	private SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
	private Ristorante ristorante;
	private Controller theController;
	private JTextField textFieldNome;
	private JTextField textFieldCognome;
	private JTextField textFieldCID;
	private JTextField textFieldDataAssunzione;
	private JButton bottoneOk;
	private JButton tornaIndietro;
	private JLabel nCaratteriNome;
	private JLabel nCaratteriCognome;
	private JLabel nCaratteriCID;
	private JLabel nCaratteriData;
	public InterfacciaAggiuntaCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Modulo di assunzione di "+ristorante.getNome());
		this.theController = theController;
		this.ristorante = ristorante;
		getContentPane().setLayout(null);
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		setBounds(20,20,375,225);
		
		JLabel etichettaNome = new JLabel("Nome");
		etichettaNome.setBounds(10, 11, 46, 14);
		getContentPane().add(etichettaNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(10, 36, 86, 20);
		getContentPane().add(textFieldNome);
		textFieldNome.setColumns(30);
		
		JLabel etichettaCognome = new JLabel("Cognome");
		etichettaCognome.setBounds(200, 11, 149, 14);
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
		etichettaDataAssunzione.setBounds(200, 75, 149, 14);
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
		
		tornaIndietro = new JButton("Indietro");
		tornaIndietro.setBounds(10, 152, 89, 23);
		getContentPane().add(tornaIndietro);
		
		JLabel formatoDataLabel = new JLabel("anno-mese-giorno");
		formatoDataLabel.setBounds(200, 131, 149, 14);
		getContentPane().add(formatoDataLabel);
		
		nCaratteriNome = new JLabel("");
		nCaratteriNome.setBounds(106, 39, 46, 14);
		getContentPane().add(nCaratteriNome);
		
		nCaratteriCognome = new JLabel("");
		nCaratteriCognome.setBounds(296, 39, 46, 14);
		getContentPane().add(nCaratteriCognome);
		
		nCaratteriCID = new JLabel("");
		nCaratteriCID.setBounds(106, 103, 46, 14);
		getContentPane().add(nCaratteriCID);
		
		GestoreTesti handler = new GestoreTesti();
		
		textFieldDataAssunzione.addKeyListener(handler);
		textFieldNome.addKeyListener(handler);
		textFieldCognome.addKeyListener(handler);
		textFieldCID.addKeyListener(handler);
		
		GestoreBottoni handlerA = new GestoreBottoni();
		
		bottoneOk.addActionListener(handlerA);
		tornaIndietro.addActionListener(handlerA);
		
		setResizable(false);
		setVisible(true);
	}
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == bottoneOk)
			{
				String esito = "";
				boolean valido = true;
				try
				{
					formatoData.parse(textFieldDataAssunzione.getText());
				}
				catch(ParseException p)
				{
					valido = false;
				}
				if (valido && textFieldCognome.getText().length()<= 30 && textFieldNome.getText().length() <=30 && textFieldCID.getText().length()==9)
					{
						Cameriere inAggiunta = new Cameriere(textFieldCID.getText(),textFieldNome.getText(),textFieldCognome.getText(), ristorante.getId_Ristorante());
						inAggiunta.setData_Ammissione(textFieldDataAssunzione.getText());
						esito = theController.bottoneOkAggiuntaCamerieriPremutoSuccessful(inAggiunta);
						if(esito.equals("Nessun_Errore"))
						{
							textFieldNome.selectAll();
							textFieldNome.replaceSelection("");
							textFieldCognome.selectAll();
							textFieldCognome.replaceSelection("");
							textFieldCID.selectAll();
							textFieldCID.replaceSelection("");
							textFieldDataAssunzione.selectAll();
							textFieldDataAssunzione.replaceSelection("");
							nCaratteriCID.setText("");
							nCaratteriNome.setText("");
							nCaratteriCognome.setText("");
							JOptionPane.showMessageDialog(null,"Il cameriere è stato aggiunto correttamente al database","Conferma",JOptionPane.INFORMATION_MESSAGE);
						}
						else if (esito.equals("CID_Non_Valido"))
						{
							JOptionPane.showMessageDialog(null, "Il numero CID non risulta valido. Si prega di riinserirlo.", "Errore!", JOptionPane.ERROR_MESSAGE);
							textFieldCID.selectAll();
							textFieldCID.replaceSelection("");
							nCaratteriCID.setText("");
						}
						else if(esito.equals("Data_Non_Valida"))
						{
							JOptionPane.showMessageDialog(null, "La data non risulta valida. Si prega di riinserirla.", "Errore!", JOptionPane.ERROR_MESSAGE);
							textFieldDataAssunzione.selectAll();
							textFieldDataAssunzione.replaceSelection("");
						}			
				}
				else
				JOptionPane.showMessageDialog(null, "Si prega di controllare le dimensioni dei valori nelle caselle di testo (Nome, Cognome al più 30, CID deve essere esattamente 9. Ci si assicuri inoltre di aver rispettato il formato della data.", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
			else if (e.getSource() == tornaIndietro)
			{
				theController.bottoneTornaIndietroAggiuntaCamerieriPremuto(ristorante);
			}
		}
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
			if (e.getSource() == textFieldNome)
			{
				nCaratteriNome.setText(String.format("%d", textFieldNome.getText().length()));
			}
			else if(e.getSource() == textFieldCognome)
			{
				nCaratteriCognome.setText(String.format("%d", textFieldCognome.getText().length()));
			}
			else if(e.getSource() == textFieldCID)
			{
				nCaratteriCID.setText(String.format("%d", textFieldCID.getText().length()));
			}
			if(textFieldDataAssunzione.getText().isBlank() || textFieldNome.getText().isBlank() || 
					textFieldCognome.getText().isBlank() || textFieldCID.getText().isBlank())
			{
				bottoneOk.setEnabled(false);
			}
			else
				bottoneOk.setEnabled(true);
		}
	}
}
