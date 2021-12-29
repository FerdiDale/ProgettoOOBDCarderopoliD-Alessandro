import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfacciaGestioneCamerieri extends JFrame 
{
	private Ristorante ristorante;
	private Controller theController;
	private DefaultListModel<Cameriere> modelloListaAssunti = new  DefaultListModel<Cameriere>();
	private	DefaultListModel<Cameriere> modelloListaLicenziati = new DefaultListModel<Cameriere>();
	private JList<Cameriere> listaAssunti = new JList<Cameriere>(modelloListaAssunti);
	private JList<Cameriere> listaLicenziati = new JList<Cameriere>(modelloListaLicenziati);
	private ArrayList<Cameriere> arrayListAssunti = new ArrayList<Cameriere>();
	private ArrayList<Cameriere> arrayListLicenziati = new ArrayList<Cameriere>();
	private JTextField dataRiassunzione;
	private JTextField dataLicenziamento;
	private JButton setDataRiassunzione;
	private JButton setDataLicenziamento;
	private JButton RiassumiCameriere;
	private JButton LicenziaCameriere;
	private JButton AggiuntaCameriere;
	private SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
	private int indiceListaAssunti = -1;
	private int indiceListaLicenziati = -1;
	private String dataAssunzioneStringa;
	private String dataLicenziamentoStringa;
	private int buttonunlockA = -2;
	private int buttonunlockL = -2;
	private JLabel etichettaFormatoDataLicenziamento;
	private JLabel eFDL2;
	private JLabel etichettaFormatoDataLicenziamento_1;
	private JLabel eFDL2_1;
	
	
	public InterfacciaGestioneCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Gestione camerieri di "+ristorante.getNome());
		getContentPane().setLayout(null);
		setBounds(100, 100, 540, 404);
		this.theController=theController;
		this.ristorante = ristorante;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		JScrollPane scrollPaneAssunti = new JScrollPane();
		scrollPaneAssunti.setBounds(10, 35, 225, 105);
		getContentPane().add(scrollPaneAssunti);
		
		
		scrollPaneAssunti.setViewportView(listaAssunti);
		
		JScrollPane scrollPaneLicenziati = new JScrollPane();
		scrollPaneLicenziati.setBounds(10, 182, 225, 105);
		getContentPane().add(scrollPaneLicenziati);
		
		scrollPaneLicenziati.setViewportView(listaLicenziati);
		
		JLabel Assunti = new JLabel("Correntemente assunti");
		Assunti.setBounds(10, 10, 350, 14);
		getContentPane().add(Assunti);
		
		JLabel Licenziati = new JLabel("Licenziati");
		Licenziati.setBounds(10, 157, 99, 14);
		getContentPane().add(Licenziati);
		
		AggiuntaCameriere = new JButton("Aggiungi Cameriere");
		AggiuntaCameriere.setBounds(287, 33, 160, 23);
		getContentPane().add(AggiuntaCameriere);
		
		RiassumiCameriere = new JButton("Riassumi Cameriere");
		RiassumiCameriere.setBounds(287, 234, 160, 23);
		RiassumiCameriere.setEnabled(false);
		getContentPane().add(RiassumiCameriere);
		
		dataRiassunzione = new JTextField();
		dataRiassunzione.setBounds(296, 336, 135, 23);
		getContentPane().add(dataRiassunzione);
		dataRiassunzione.setColumns(10);
		
		LicenziaCameriere = new JButton("Licenzia Cameriere");
		LicenziaCameriere.setBounds(287, 67, 160, 23);
		LicenziaCameriere.setEnabled(false);
		getContentPane().add(LicenziaCameriere);
		
		dataLicenziamento = new JTextField();
		dataLicenziamento.setBounds(296, 162, 135, 23);
		getContentPane().add(dataLicenziamento);
		dataLicenziamento.setColumns(10);
		
		setDataRiassunzione = new JButton("^");
		setDataRiassunzione.setBounds(320, 276, 89, 23);
		getContentPane().add(setDataRiassunzione);
		
		setDataLicenziamento = new JButton("^");
		setDataLicenziamento.setBounds(320, 101, 89, 23);
		getContentPane().add(setDataLicenziamento);
		
		arrayListAssunti = theController.EstraiCamerieriInServizioC(ristorante);
		arrayListLicenziati = theController.EstraiCamerieriLicenziatiC(ristorante);
		
		modelloListaAssunti.removeAllElements();
		modelloListaLicenziati.removeAllElements();
		
		modelloListaAssunti.addAll(arrayListAssunti);
		modelloListaLicenziati.addAll(arrayListLicenziati);
		
		GestioneBottoniETesti handler = new GestioneBottoniETesti();
		GestioneListe handlerL = new GestioneListe();
		ConsistenzaData handlerC = new ConsistenzaData();
		
		dataLicenziamento.setFocusable(true);
		dataRiassunzione.setFocusable(true);
		
		etichettaFormatoDataLicenziamento = new JLabel("Inserire la data nel formato");
		etichettaFormatoDataLicenziamento.setBounds(295, 299, 184, 14);
		getContentPane().add(etichettaFormatoDataLicenziamento);
		
		eFDL2 = new JLabel("anno/mese/giorno e poi premere la freccetta");
		eFDL2.setBounds(254, 311, 280, 14);
		getContentPane().add(eFDL2);
		
		etichettaFormatoDataLicenziamento_1 = new JLabel("Inserire la data nel formato");
		etichettaFormatoDataLicenziamento_1.setBounds(295, 131, 184, 14);
		getContentPane().add(etichettaFormatoDataLicenziamento_1);
		
		eFDL2_1 = new JLabel("anno/mese/giorno e poi premere la freccetta");
		eFDL2_1.setBounds(254, 143, 280, 14);
		getContentPane().add(eFDL2_1);
		
		JButton tornaIndietro = new JButton("Indietro");
		tornaIndietro.setBounds(10, 331, 89, 23);
		getContentPane().add(tornaIndietro);
		
		dataLicenziamento.addKeyListener(handlerC);
		dataRiassunzione.addKeyListener(handlerC);
		setDataRiassunzione.addActionListener(handler);
		listaLicenziati.addListSelectionListener(handlerL);
		RiassumiCameriere.addActionListener(handler);
		tornaIndietro.addActionListener(handler);
		setDataLicenziamento.addActionListener(handler);
		LicenziaCameriere.addActionListener(handler);
		listaAssunti.addListSelectionListener(handlerL);
		AggiuntaCameriere.addActionListener(handler);
		
		setVisible(true);
		setResizable(false);
		
		
	}
	
	private class GestioneBottoniETesti implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == setDataRiassunzione)
			{
				if(dataRiassunzione.getText().isBlank())
				{
					JOptionPane.showMessageDialog(null, "Non si può inserire una data vuota.", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
				else if(dataRiassunzione.getText().length()<10)
				{
					JOptionPane.showMessageDialog(null, "Data non valida, riprovare.", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
				else 
				{
					try
					{
						formatoData.parse(dataRiassunzione.getText());
						dataAssunzioneStringa = dataRiassunzione.getText();
						if(buttonunlockA >= -1 && indiceListaLicenziati!= -1)
						{
							RiassumiCameriere.setEnabled(true);
						}
						else
						{
							buttonunlockA =-1;
							JOptionPane.showMessageDialog(null, "Si selezioni ora un cameriere dalla lista dei licenziati per riassumerlo.", "Informazione",JOptionPane.INFORMATION_MESSAGE);
						}
					}
					catch(ParseException p)
					{
						JOptionPane.showMessageDialog(null,"La data non e' del formato corretto, riprovare.","Errore!",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else if(e.getSource() == RiassumiCameriere)
			{
				theController.bottoneRiassumiCamerierePremuto(arrayListLicenziati.get(indiceListaLicenziati),dataAssunzioneStringa);
				arrayListAssunti.add(arrayListLicenziati.get(indiceListaLicenziati));
				arrayListLicenziati.remove(indiceListaLicenziati);
				modelloListaLicenziati.removeAllElements();
				modelloListaLicenziati.addAll(arrayListLicenziati);
				modelloListaAssunti.removeAllElements();
				modelloListaAssunti.addAll(arrayListAssunti);
			}
			else if(e.getSource() == setDataLicenziamento)
			{
				if(dataLicenziamento.getText().isBlank())
				{
					JOptionPane.showMessageDialog(null, "Non si può inserire una data vuota.", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
				else if(dataLicenziamento.getText().length()<10)
				{
					JOptionPane.showMessageDialog(null, "Data non valida, riprovare.", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
				else 
				{
					try
					{
						formatoData.parse(dataLicenziamento.getText());
						dataLicenziamentoStringa = dataLicenziamento.getText();
						if(buttonunlockL >= -1 && indiceListaAssunti != -1)
						{
							LicenziaCameriere.setEnabled(true);
						}
						else
						{
							buttonunlockL =-1;
							JOptionPane.showMessageDialog(null, "Si selezioni ora un cameriere dalla lista degli assunti per licenziarlo.", "Informazione",JOptionPane.INFORMATION_MESSAGE);
						}
					}
					catch(ParseException p)
					{
						JOptionPane.showMessageDialog(null,"La data non e' del formato corretto, riprovare.","Errore!",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else if(e.getSource() == LicenziaCameriere)
			{
				String esito = theController.bottoneLicenziaCamerierePremuto(arrayListAssunti.get(indiceListaAssunti),dataLicenziamentoStringa);
				if(esito.equals("Tutto_Bene"))
				{
					arrayListLicenziati.add(arrayListAssunti.get(indiceListaAssunti));
					arrayListAssunti.remove(indiceListaAssunti);
					modelloListaAssunti.removeAllElements();
					modelloListaAssunti.addAll(arrayListAssunti);
					modelloListaLicenziati.removeAllElements();
					modelloListaLicenziati.addAll(arrayListLicenziati);
				}
				else if(esito.equals("Operazione_Fallita"))
				{
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Non si può licenziare un cameriere prima della sua data di assunzione!("+arrayListAssunti.get(indiceListaAssunti).getData_Ammissione()+")", "Informazione", JOptionPane.INFORMATION_MESSAGE);
				}
				LicenziaCameriere.setEnabled(false);
			}
			else if (e.getSource() == AggiuntaCameriere)
			{
				theController.bottoneAggiungiCamerierePremuto(ristorante);
				AggiuntaCameriere.setEnabled(false);
			}
			else
			{
				theController.bottoneTornaIndietroGestioneCamerieriPremuto(ristorante);
			}
		}
	}
	private class GestioneListe implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			if(e.getSource() == listaLicenziati)
			{
				indiceListaLicenziati = listaLicenziati.getSelectedIndex();
				if(buttonunlockA >= -1 && dataRiassunzione.getText().isBlank() == false)
				{
					RiassumiCameriere.setEnabled(true);
				}
				else
				{
					buttonunlockA = -1;
					JOptionPane.showMessageDialog(null, "Si inserisca ora una data per riassumere il cameriere selezionato.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else if (e.getSource() == listaAssunti)
			{
				indiceListaAssunti = listaAssunti.getSelectedIndex();
				if(buttonunlockL >= -1 && dataLicenziamento.getText().isBlank() == false)
				{
					LicenziaCameriere.setEnabled(true);
				}
				else
				{
					buttonunlockL = -1;
					JOptionPane.showMessageDialog(null, "Si inserisca ora una data per licenziare il cameriere selezionato.","Informazione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	private class ConsistenzaData implements KeyListener
	{
		public void keyReleased(KeyEvent e)
		{
			
		}
		public void keyPressed(KeyEvent e)
		{
			if(e.getSource() == dataRiassunzione )
			{
				RiassumiCameriere.setEnabled(false);
			}
			else
			{
				LicenziaCameriere.setEnabled(false);
			}
		}
		public void keyTyped(KeyEvent e)
		{
			
		}
	}
}


