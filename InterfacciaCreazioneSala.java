import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class InterfacciaCreazioneSala extends JFrame
{
	private JButton BottoneOk;
	private JButton tornaIndietro;
	private Ristorante ristorante;
	private JTextField NomeSala;
	private Controller theController;
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
		
		
		ContaCaratteri key = new ContaCaratteri();
		NomeSala.getDocument().addDocumentListener(new ContaCaratteri());
		NomeSala.setFocusable(true);
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
				boolean nomeTroppoLungo = false;
				if(NomeSala.getText().isBlank())
				{
					JOptionPane.showMessageDialog(null,"Non puoi inserire una sala senza nome!","Errore", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(NomeSala.getText().length()>40)
						nomeTroppoLungo = true;
				
					if (nomeTroppoLungo)
					{
						JOptionPane.showMessageDialog(null,"Il nome inserito e'  troppo lungo (piu' di 40 caratteri). Si prega di riprovare.", "Errore!", JOptionPane.ERROR_MESSAGE);
						NomeSala.selectAll();
						NomeSala.replaceSelection("");
					}
					else
					{
						theController.interfacciaCreazioneSalaOkPremuto(NomeSala.getText(), ristorante);
					}
				}
			}
			else
			{
				theController.bottoneTornaIndietroInterfacciaCreazioneSalaPremuto(ristorante);
			}
		}
	}
	private class ContaCaratteri implements DocumentListener{
		@Override
		public void insertUpdate(DocumentEvent e) {
			contaCaratteri.setText(String.format("%d", NomeSala.getText().length()));
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			contaCaratteri.setText(String.format("%d", NomeSala.getText().length()));
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
}
