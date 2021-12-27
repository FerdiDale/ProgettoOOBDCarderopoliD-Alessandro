import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class InterfacciaCreazioneSala extends JFrame
{
	private Ristorante ristorante;
	private JTextField textField;
	private JTextField NomeSala;
	private Controller theController;
	private int id_ristorante;
	
	public InterfacciaCreazioneSala(Ristorante ristorante, Controller theController) 
	{
		super("Aggiunta Sala");
		setBounds(10, 20, 200, 200);
		setResizable(false);
		getContentPane().setLayout(null);
		GestoreBottoneOk GestoreNomeSala = new GestoreBottoneOk();
		this.theController = theController;
		this.ristorante = ristorante;
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		NomeSala = new JTextField();
		NomeSala.setBounds(10, 30, 86, 20);
		NomeSala.setColumns(40);
		NomeSala.addActionListener(GestoreNomeSala);
	    add(NomeSala);
		
		JLabel EtichettaInserisciNome = new JLabel("Nome della sala");
		EtichettaInserisciNome.setBounds(10, 11, 86, 14);
		add(EtichettaInserisciNome);
		
		JButton BottoneOk = new JButton("Ok");
		BottoneOk.setBounds(127, 30, 53, 20);
		add(BottoneOk);
		BottoneOk.addActionListener(GestoreNomeSala);
		
		setVisible(true);
		
	}
	
	private class GestoreBottoneOk implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			boolean nomeGiaPreso = false;
			if(NomeSala.getText().isBlank())
			{
				JOptionPane.showMessageDialog(null,"Non puoi inserire una sala senza nome!","Errore", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				nomeGiaPreso = theController.interfacciaCreazioneSalaOkPremuto1(NomeSala.getText(),id_ristorante);
			}
			if (nomeGiaPreso)
			{
				JOptionPane.showMessageDialog(null,"Il nome inserito e' gia' in uso per un'altra sala.", "Errore!", JOptionPane.ERROR_MESSAGE);
				NomeSala.selectAll();
				NomeSala.replaceSelection("");
			}
			else
			{
				theController.interfacciaCreazioneSalaOkPremuto2(NomeSala.getText(), ristorante);
			}
		}
	}
}
