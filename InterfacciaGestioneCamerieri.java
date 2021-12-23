import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
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
	
	
	public InterfacciaGestioneCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Gestione camerieri di "+ristorante.getNome());
		getContentPane().setLayout(null);
		setBounds(100, 100, 450, 350);
		this.theController=theController;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		AggiuntaCameriere.setBounds(287, 33, 135, 23);
		getContentPane().add(AggiuntaCameriere);
		
		RiassumiCameriere = new JButton("Riassumi Cameriere");
		RiassumiCameriere.setBounds(287, 188, 135, 23);
		RiassumiCameriere.setEnabled(false);
		getContentPane().add(RiassumiCameriere);
		
		dataRiassunzione = new JTextField();
		dataRiassunzione.setBounds(287, 249, 135, 23);
		getContentPane().add(dataRiassunzione);
		dataRiassunzione.setColumns(10);
		
		LicenziaCameriere = new JButton("Licenzia Cameriere");
		LicenziaCameriere.setBounds(287, 67, 135, 23);
		LicenziaCameriere.setEnabled(false);
		getContentPane().add(LicenziaCameriere);
		
		dataLicenziamento = new JTextField();
		dataLicenziamento.setBounds(287, 128, 135, 23);
		getContentPane().add(dataLicenziamento);
		dataLicenziamento.setColumns(10);
		
		setDataRiassunzione = new JButton("^");
		setDataRiassunzione.setBounds(311, 217, 89, 23);
		getContentPane().add(setDataRiassunzione);
		
		setDataLicenziamento = new JButton("^");
		setDataLicenziamento.setBounds(311, 96, 89, 23);
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
		
		dataLicenziamento.addKeyListener(handlerC);
		dataRiassunzione.addKeyListener(handlerC);
		setDataRiassunzione.addActionListener(handler);
		dataRiassunzione.addActionListener(handler);
		listaLicenziati.addListSelectionListener(handlerL);
		RiassumiCameriere.addActionListener(handler);
		
		setDataLicenziamento.addActionListener(handler);
		dataLicenziamento.addActionListener(handler);
		LicenziaCameriere.addActionListener(handler);
		listaAssunti.addListSelectionListener(handlerL);
		
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
			if(e.getSource() == setDataLicenziamento)
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
				theController.bottoneLicenziaCamerierePremuto(arrayListAssunti.get(indiceListaAssunti),dataLicenziamentoStringa);
				arrayListLicenziati.add(arrayListAssunti.get(indiceListaAssunti));
				arrayListAssunti.remove(indiceListaAssunti);
				modelloListaAssunti.removeAllElements();
				modelloListaAssunti.addAll(arrayListAssunti);
				modelloListaLicenziati.removeAllElements();
				modelloListaLicenziati.addAll(arrayListLicenziati);
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




//codice per la riassunzione del cameriere 
/*				theController.bottoneRiassumiCamerierePremuto(arrayListLicenziati.get(indiceListaLicenziati),dataAssunzioneStringa);
				arrayListAssunti.add(arrayListLicenziati.get(indiceListaLicenziati));
				arrayListLicenziati.remove(indiceListaLicenziati);
				modelloListaLicenziati.removeAllElements();
				modelloListaLicenziati.addAll(arrayListLicenziati);
				modelloListaAssunti.removeAllElements();
				modelloListaAssunti.addAll(arrayListAssunti);*/
