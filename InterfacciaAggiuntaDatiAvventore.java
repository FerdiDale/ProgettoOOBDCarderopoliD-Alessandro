import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
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
	private boolean intero;
	private Controller theController;
	private ArrayList<Tavolo> tavoli;
	private int tavoloScelto;
	private String data;
	private JLabel contaNome;
	private JLabel contaCognome;
	private JLabel contaCid;
	private JLabel contaNtel;
	private JButton bottoneIndietro;
	private int dimTot;
	private boolean diVisualizzazione = false;
	

	public InterfacciaAggiuntaDatiAvventore(Controller controller, int indice, ArrayList<Tavolo> tavoli, int tavoloScelto, String data) 
	{
		super("Inserimento dati avventore "+ (indice+1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 220);
		getContentPane().setLayout(null);
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
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
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 147, 89, 23);
		getContentPane().add(bottoneIndietro);
		
		contaNome = new JLabel("0");
		contaNome.setBounds(124, 40, 46, 23);
		getContentPane().add(contaNome);
		
		contaCognome = new JLabel("0");
		contaCognome.setBounds(124, 103, 46, 14);
		getContentPane().add(contaCognome);
		
		contaCid = new JLabel("0");
		contaCid.setBounds(276, 43, 46, 14);
		getContentPane().add(contaCid);
		
		contaNtel = new JLabel("0");
		contaNtel.setBounds(276, 103, 46, 14);
		getContentPane().add(contaNtel);
		bottoneIndietro.addActionListener(new GestioneBottoni());
		prossimoAvventore.addActionListener(new GestioneBottoni());
		avventorePrecedente.addActionListener(new GestioneBottoni());
	
		if(indice == 0 ) 
		{
			avventorePrecedente.setEnabled(false);
			avventorePrecedente.setVisible(false);
		}
		
		
		nome.addKeyListener(new GestoreConta());
		cognome.addKeyListener(new GestoreConta());
		cid.addKeyListener(new GestoreConta());
		ntel.addKeyListener(new GestoreConta());
		nome.setFocusable(true);
		cognome.setFocusable(true);
		ntel.setFocusable(true);
		cid.setFocusable(true);
		
		setResizable(false);
	}
	
	public void impostaBottoniCorretti(int dimTot)
	{
		if (indice == dimTot-1)
		{
			this.dimTot = dimTot;
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
				try
				{
					if(!ntel.getText().isBlank())
					{
						Integer.parseInt(ntel.getText());
						
						if(ntel.getText().length() == 10)	intero = true;
						else intero = false;
					}
					else
					{
						intero = true;
					}
				}
				catch(NumberFormatException n)
				{
					intero = false;
				}
				if(cid.getText().length() != 9 || nome.getText().length() >30 || cognome.getText().length() > 30 || !intero) JOptionPane.showMessageDialog(null, "Si controllino le dimensioni dei campi (nome e cognome devono essere lunghi al piu 30, mentre CID deve essere lungo esattamente 9 caratteri, numero di telefono esattamente 10 caratteri decimali OPPURE vuoto");
					
				else
					{
						if(ultima && !diVisualizzazione)
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
						else if(!ultima)
							{
								theController.bottoneAvventoreSuccessivoPremuto(indice);
							}
					
						else if (ultima && diVisualizzazione)
							{
								try 
								{
									theController.bottoneConfermaAggiuntaAvventoreDiVisualizzazionePremuto(tavoli, tavoloScelto, data);
								} catch (CampiNonCorrettiException e2) 
								{
									e2.stampaMessaggio();
								}
							}
					}

			}
			else if(e.getSource() == avventorePrecedente)
			{
				theController.bottoneAvventorePrecedentePremuto(indice);
			}
			else if(e.getSource() == bottoneIndietro)
			{
				if(diVisualizzazione) theController.bottoneIndietroAggiuntaAvventoreDiVisualizzazionePremuto(tavoli, tavoloScelto, data);
				else theController.bottoneIndietroAggiuntaAvventorePremuto(tavoli, data, indice);
			}
		}
		
	}
	
	private class GestoreConta implements KeyListener
	{
		public void keyTyped(KeyEvent e) 
		{
			if(e.getSource() == nome)
			{
				contaNome.setText(String.format("%d", nome.getText().length()));
			}
			else if(e.getSource() == cognome)
			{	
				contaCognome.setText(String.format("%d", cognome.getText().length()));
			}
			else if(e.getSource() == cid)
			{
				contaCid.setText(String.format("%d", cid.getText().length()));
			}
			else if(e.getSource() == ntel)
			{
				contaNtel.setText(String.format("%d", ntel.getText().length()));
			}
		}

		
		public void keyPressed(KeyEvent e)
		{
			if(e.getSource() == nome)
			{
				contaNome.setText(String.format("%d", nome.getText().length()));
			}
			else if(e.getSource() == cognome)
			{	
				contaCognome.setText(String.format("%d", cognome.getText().length()));
			}
			else if(e.getSource() == cid)
			{
				contaCid.setText(String.format("%d", cid.getText().length()));
			}
			else if(e.getSource() == ntel)
			{
				contaNtel.setText(String.format("%d", ntel.getText().length()));
			}
		}

		
		public void keyReleased(KeyEvent e) 
		{
			if(e.getSource() == nome)
			{
				contaNome.setText(String.format("%d", nome.getText().length()));
			}
			else if(e.getSource() == cognome)
			{	
				contaCognome.setText(String.format("%d", cognome.getText().length()));
			}
			else if(e.getSource() == cid)
			{
				contaCid.setText(String.format("%d", cid.getText().length()));
			}
			else if(e.getSource() == ntel)
			{
				contaNtel.setText(String.format("%d", ntel.getText().length()));
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

	public ArrayList<Tavolo> getTavoli() {
		return tavoli;
	}

	public int getTavoloScelto() {
		return tavoloScelto;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public JButton getProssimoAvventore() {
		return prossimoAvventore;
	}

	public void setProssimoAvventore(JButton prossimoAvventore) {
		this.prossimoAvventore = prossimoAvventore;
	}

	public boolean isDiVisualizzazione() {
		return diVisualizzazione;
	}

	public void setDiVisualizzazione(boolean diVisualizzazione) {
		this.diVisualizzazione = diVisualizzazione;
	}
	
}
	

