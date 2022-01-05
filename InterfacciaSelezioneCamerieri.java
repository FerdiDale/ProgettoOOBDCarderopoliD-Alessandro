import java.awt.BorderLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;

public class InterfacciaSelezioneCamerieri extends JFrame 
{
	private DefaultListModel<Cameriere> modelloListaCameriere = new DefaultListModel<Cameriere>();
	private ArrayList<Cameriere> arrayCameriere;
	private JList<Cameriere> listaCamerieri = new JList<Cameriere>(modelloListaCameriere);
	private DefaultListModel<Cameriere> modelloListaCameriereSelezionati = new DefaultListModel<Cameriere>();
	private ArrayList<Cameriere> arrayCameriereSelezionati = new ArrayList<Cameriere>();
	private JList<Cameriere> listaCamerieriSelezionati = new JList<Cameriere>(modelloListaCameriereSelezionati);
	private Controller theController;
	private JButton bottoneIndietro;
	private JButton bottoneConfermaSelezione;
	private String data;
	private ArrayList<Tavolo> tavoli;
	private JLabel etichettaCamerieri;
	private int tavoloScelto;
	private int indiceDiSelezioneC;
	private int indiceDiSelezioneS;
	private JButton bottoneAggiungiAiSelezionati;
	private JButton bottoneRimuoviDaiSelezionati;
	private boolean diVisualizzazione = false;
	
	public InterfacciaSelezioneCamerieri(Controller controller, ArrayList<Tavolo> tavoli, int tavoloScelto, String data) 
	{
		super("Selezione camerieri disponibili in data "+ data);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 435, 250);
		getContentPane().setLayout(null);
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		this.data = data;
		this.tavoli = tavoli;
		this.theController = controller;
		this.tavoloScelto = tavoloScelto;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 170, 119);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(listaCamerieri);
		
		bottoneConfermaSelezione = new JButton("Conferma");
		bottoneConfermaSelezione.setBounds(274, 178, 101, 23);
		getContentPane().add(bottoneConfermaSelezione);
		bottoneConfermaSelezione.addActionListener(new GestoreBottoni());
	
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 178, 89, 23);
		getContentPane().add(bottoneIndietro);
		
		bottoneIndietro.addActionListener(new GestoreBottoni());
		etichettaCamerieri = new JLabel("Camerieri disponibili");
		etichettaCamerieri.setBounds(10, 16, 153, 14);
		getContentPane().add(etichettaCamerieri);
		
		JScrollPane scrollPaneSelezionati = new JScrollPane();
		scrollPaneSelezionati.setBounds(239, 41, 170, 119);
		getContentPane().add(scrollPaneSelezionati);
		
		scrollPaneSelezionati.setViewportView(listaCamerieriSelezionati);
		
		bottoneAggiungiAiSelezionati = new JButton(">");
		bottoneAggiungiAiSelezionati.setBounds(187, 73, 45, 23);
		getContentPane().add(bottoneAggiungiAiSelezionati);
		
		bottoneRimuoviDaiSelezionati = new JButton("<");
		bottoneRimuoviDaiSelezionati.setBounds(187, 115, 45, 23);
		getContentPane().add(bottoneRimuoviDaiSelezionati);
		
		bottoneAggiungiAiSelezionati.addActionListener(new GestoreBottoni());
		bottoneRimuoviDaiSelezionati.addActionListener(new GestoreBottoni());
		
		listaCamerieri.addListSelectionListener(new GestoreLista());
		listaCamerieriSelezionati.addListSelectionListener(new GestoreLista());
		
		bottoneAggiungiAiSelezionati.setEnabled(false);
		bottoneRimuoviDaiSelezionati.setEnabled(false);
		
		//dall'array cameriere devi toglierci quelli che già stanno nella lista, altrimenti si arrabbia tanto
		arrayCameriere = theController.estraiCamerieriAssegnabili(data,tavoli.get(tavoloScelto).getSala_App().getRistoranteDiAppartenenza());
		modelloListaCameriere.addAll(arrayCameriere);
		
		setVisible(true);
		setResizable(false);
	}
	
	private class GestoreLista implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			indiceDiSelezioneC = listaCamerieri.getSelectedIndex();
			indiceDiSelezioneS = listaCamerieriSelezionati.getSelectedIndex();
			
			if(indiceDiSelezioneC != -1) bottoneAggiungiAiSelezionati.setEnabled(true);
			else bottoneAggiungiAiSelezionati.setEnabled(false);	
			
			if(indiceDiSelezioneS != -1) bottoneRimuoviDaiSelezionati.setEnabled(true);
			else bottoneRimuoviDaiSelezionati.setEnabled(false);
		}
		
	}
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroSelezioneCamerieriPremuto(tavoli,data);
			}	
			else if(e.getSource() == bottoneConfermaSelezione)
			{
				if(diVisualizzazione) theController.bottoneConfermaSelezioneCamerieriPremuto(arrayCameriereSelezionati, data, tavoli, tavoloScelto);
				else theController.bottoneConfermaSelezioneCamerieriPremuto(arrayCameriereSelezionati, data, tavoli, tavoloScelto);
			}
			else if(e.getSource() == bottoneAggiungiAiSelezionati)
			{
				arrayCameriereSelezionati.add(arrayCameriere.get(indiceDiSelezioneC));
				modelloListaCameriereSelezionati.removeAllElements();
				modelloListaCameriereSelezionati.addAll(arrayCameriereSelezionati);
				arrayCameriere.remove(indiceDiSelezioneC);
				modelloListaCameriere.removeAllElements();
				modelloListaCameriere.addAll(arrayCameriere);
				bottoneAggiungiAiSelezionati.setEnabled(false);
				bottoneRimuoviDaiSelezionati.setEnabled(false);
			}
			else if(e.getSource() == bottoneRimuoviDaiSelezionati)
			{
				arrayCameriere.add(arrayCameriereSelezionati.get(indiceDiSelezioneS));
				modelloListaCameriere.removeAllElements();
				modelloListaCameriere.addAll(arrayCameriere);
				arrayCameriereSelezionati.remove(indiceDiSelezioneS);
				modelloListaCameriereSelezionati.removeAllElements();
				modelloListaCameriereSelezionati.addAll(arrayCameriereSelezionati);
				bottoneAggiungiAiSelezionati.setEnabled(false);
				bottoneRimuoviDaiSelezionati.setEnabled(false);
			}
		}
	}

	public boolean isDiVisualizzazione() {
		return diVisualizzazione;
	}

	public void setDiVisualizzazione(boolean diVisualizzazione) {
		this.diVisualizzazione = diVisualizzazione;
	}
	
	
}
