import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.ArrayList;
public class InterfacciaAggiuntaDatiAvventore extends JFrame 
{
	private JTextField nome;
	private JTextField cognome;
	private JTextField cid;
	private JTextField ntel;
	private JButton prossimoAvventore;
	private JButton avventorePrecedente;
	private boolean ultima = false;
	private int indice;
	private Controller theController;
	private ArrayList<Tavolo> tavoli;
	private int tavoloScelto;
	private String data;
	
	public InterfacciaAggiuntaDatiAvventore(Controller controller, int indice, ArrayList<Tavolo> tavoli, int tavoloScelto, String data) 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 220);
		getContentPane().setLayout(null);
		this.tavoloScelto = tavoloScelto;
		this.tavoli = tavoli;
		this.theController = controller;
		this.indice = indice;
		this.data = data;
		nome = new JTextField();
		nome.setBounds(30, 40, 86, 20);
		getContentPane().add(nome);
		nome.setColumns(10);
		
		JLabel etichettaNome = new JLabel("Nome");
		etichettaNome.setBounds(30, 15, 61, 14);
		getContentPane().add(etichettaNome);
		
		cognome = new JTextField();
		cognome.setBounds(30, 100, 86, 20);
		getContentPane().add(cognome);
		cognome.setColumns(10);
		
		JLabel etichettaCognome = new JLabel("Cognome");
		etichettaCognome.setBounds(30, 71, 79, 14);
		getContentPane().add(etichettaCognome);
		
		cid = new JTextField();
		cid.setBounds(180, 40, 86, 20);
		getContentPane().add(cid);
		cid.setColumns(10);
		
		JLabel etichettaCid = new JLabel("Numero CID");
		etichettaCid.setBounds(180, 15, 72, 14);
		getContentPane().add(etichettaCid);
		
		ntel = new JTextField();
		ntel.setBounds(180, 100, 86, 20);
		getContentPane().add(ntel);
		ntel.setColumns(10);
		
		JLabel etichettaNTel = new JLabel("Numero di telefono");
		etichettaNTel.setBounds(180, 71, 119, 14);
		getContentPane().add(etichettaNTel);
		
		prossimoAvventore = new JButton("Prossimo");
		prossimoAvventore.setBounds(237, 147, 117, 23);
		getContentPane().add(prossimoAvventore);
		
		avventorePrecedente = new JButton("Precedente");
		avventorePrecedente.setBounds(109, 147, 105, 23);
		getContentPane().add(avventorePrecedente);
		
		JButton bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 147, 89, 23);
		getContentPane().add(bottoneIndietro);
		prossimoAvventore.addActionListener(new GestioneBottoni());
	
		if(indice == 0 ) 
		{
			avventorePrecedente.setEnabled(false);
			avventorePrecedente.setVisible(false);
		}
		
		setResizable(false);
	}
	
	public void impostaBottoniCorretti(int dimTot)
	{
		if (indice == dimTot-1)
		{
			prossimoAvventore.setText("Conferma");
			ultima = true;
		}
	}
	
	private class GestioneBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == prossimoAvventore)
			{
				if(ultima)
				{
					try
					{
						theController.bottoneConfermaAggiuntaAvventoriPremuto(tavoli, tavoloScelto, data);
					}
					catch(CampiNonCorrettiException c)
					{
						c.stampaMessaggio();
					}
				}
				else
				{
					theController.bottoneAvventoreSuccessivoPremuto(indice);
				}
			}
			else if(e.getSource() == avventorePrecedente)
			{
				theController.bottoneAvventorePrecedentePremuto(indice);
			}
		}
	}

	public JTextField getNome() {
		return nome;
	}

	public void setNome(JTextField nome) {
		this.nome = nome;
	}

	public JTextField getCognome() {
		return cognome;
	}

	public void setCognome(JTextField cognome) {
		this.cognome = cognome;
	}

	public JTextField getCid() {
		return cid;
	}

	public void setCid(JTextField cid) {
		this.cid = cid;
	}

	public JTextField getNtel() {
		return ntel;
	}

	public void setNtel(JTextField ntel) {
		this.ntel = ntel;
	}
	
	

}
