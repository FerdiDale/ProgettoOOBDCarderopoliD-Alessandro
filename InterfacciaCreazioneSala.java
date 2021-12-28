import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class InterfacciaCreazioneSala extends JFrame
{
	private JButton BottoneOk;
	private JButton tornaIndietro;
	private Ristorante ristorante;
	private JTextField textField;
	private JTextField NomeSala;
	private Controller theController;
	private int id_ristorante;
	private JLabel contaCaratteri;
	
	public InterfacciaCreazioneSala(Ristorante ristorante, Controller theController) 
	{
		super("Aggiunta Sala");
		setResizable(false);
		getContentPane().setLayout(null);
		GestoreBottoni GestoreNomeSala = new GestoreBottoni();
		this.theController = theController;
		this.ristorante = ristorante;
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		setBounds(10,10,320,154);
		
		NomeSala = new JTextField();
		NomeSala.setBounds(10, 30, 86, 20);
		NomeSala.setColumns(40);
	    getContentPane().add(NomeSala);
		
		JLabel EtichettaInserisciNome = new JLabel("Nome della sala");
		EtichettaInserisciNome.setBounds(10, 11, 200, 14);
		getContentPane().add(EtichettaInserisciNome);
		
		BottoneOk = new JButton("Ok");
		BottoneOk.setBounds(107, 65, 53, 20);
		getContentPane().add(BottoneOk);
		
		tornaIndietro = new JButton("Indietro");
		tornaIndietro.setBounds(10, 64, 89, 23);
		getContentPane().add(tornaIndietro);
		
		contaCaratteri = new JLabel("0");
		contaCaratteri.setBounds(106, 33, 46, 14);
		getContentPane().add(contaCaratteri);
		BottoneOk.addActionListener(GestoreNomeSala);
		tornaIndietro.addActionListener(GestoreNomeSala);
		setVisible(true);
		
	}
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==BottoneOk)
			{	
				boolean nomeGiaPreso = false;
				if(NomeSala.getText().isBlank())
				{
					JOptionPane.showMessageDialog(null,"Non puoi inserire una sala senza nome!","Errore", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(NomeSala.getText().length()<=40)
						nomeGiaPreso = theController.interfacciaCreazioneSalaOkPremuto1(NomeSala.getText(),id_ristorante);
					else nomeGiaPreso = true;
				}
				if (nomeGiaPreso)
				{
					JOptionPane.showMessageDialog(null,"Il nome inserito e' gia' in uso per un'altra sala oppure � troppo lungo (pi� di 40 caratteri). Si prega di riprovare.", "Errore!", JOptionPane.ERROR_MESSAGE);
					NomeSala.selectAll();
					NomeSala.replaceSelection("");
				}
				else
				{
					theController.interfacciaCreazioneSalaOkPremuto2(NomeSala.getText(), ristorante);
				}
			}
			else
			{
				theController.bottoneTornaIndietroInterfacciaCreazioneSalaPremuto(ristorante);
			}
		}
	}
	private class ContaCaratteri implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			
		}
		
		public void keyReleased(KeyEvent e)
		{
			contaCaratteri.setText(String.format("%d", NomeSala.getText().length()));
		}
		public void keyTyped(KeyEvent e)
		{
			
		}
	}
}
