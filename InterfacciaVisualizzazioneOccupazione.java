import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InterfacciaVisualizzazioneOccupazione extends JFrame {

	private ArrayList<Tavolo> tavoli;
	private Controller theController;
	private DefaultListModel<Avventori> modelloListaAvventori = new DefaultListModel<Avventori>();
	private ArrayList<Avventori> arrayAvventori;
	private JList<Avventori> listaAvventori = new JList<Avventori>(modelloListaAvventori);
	private DefaultListModel<Cameriere> modelloListaCameriere = new DefaultListModel<Cameriere>();
	private ArrayList<Cameriere> arrayCameriere;
	private JList<Cameriere> listaCamerieri = new JList<Cameriere>(modelloListaCameriere);
	private JButton bottoneIndietro;
	private String dataScelta;
	private int tavoloScelto;
	private JButton bottoneRimuoviCameriere;
	private int indiceListaCamerieri = -1;
	private int indiceListaAvventori = -1;
	private int contaNTel = 0;
	private JButton bottoneRimuoviAvventore;
	private JButton bottoneAggiungiAvventore;
	private JButton bottoneAggiungiCameriere;
	
	public InterfacciaVisualizzazioneOccupazione(Controller controller, ArrayList<Tavolo> tavoli, int tavoloScelto, String dataScelta) 
	{
		super("Tavolata del tavolo "+ tavoli.get(tavoloScelto).getNumero()+" in data "+ dataScelta+".");
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 330);
		getContentPane().setLayout(null);
		this.theController=controller;
		this.dataScelta= dataScelta;
		this.tavoloScelto = tavoloScelto;
		this.tavoli = tavoli;
		JScrollPane scrollPaneAvventori = new JScrollPane();
		scrollPaneAvventori.setBounds(10, 45, 170, 100);
		getContentPane().add(scrollPaneAvventori);

		scrollPaneAvventori.setViewportView(listaAvventori);
		
		JScrollPane scrollPaneCamerieri = new JScrollPane();
		scrollPaneCamerieri.setBounds(310, 45, 164, 100);
		getContentPane().add(scrollPaneCamerieri);
		
		scrollPaneCamerieri.setViewportView(listaCamerieri);
		
		JLabel indicazioneAvventori = new JLabel("Avventori");
		indicazioneAvventori.setBounds(10, 20, 170, 14);
		getContentPane().add(indicazioneAvventori);
		
		JLabel indicazioneCamerieri = new JLabel("Camerieri che hanno servito");
		indicazioneCamerieri.setBounds(310, 20, 164, 14);
		getContentPane().add(indicazioneCamerieri);
		
		bottoneAggiungiCameriere = new JButton("Aggiungi cameriere");
		bottoneAggiungiCameriere.setBounds(310, 156, 164, 23);
		getContentPane().add(bottoneAggiungiCameriere);
		bottoneAggiungiCameriere.addActionListener(new GestioneBottoni());
		
		bottoneRimuoviCameriere = new JButton("Rimuovi cameriere");
		bottoneRimuoviCameriere.setBounds(310, 190, 164, 23);
		getContentPane().add(bottoneRimuoviCameriere);
		bottoneRimuoviCameriere.setEnabled(false);
		bottoneRimuoviCameriere.addActionListener(new GestioneBottoni());
		
		bottoneAggiungiAvventore = new JButton("Aggiungi avventore");
		bottoneAggiungiAvventore.setBounds(10, 156, 170, 23);
		getContentPane().add(bottoneAggiungiAvventore);
		bottoneAggiungiAvventore.addActionListener(new GestioneBottoni());
		
		bottoneRimuoviAvventore = new JButton("Rimuovi avventore");
		bottoneRimuoviAvventore.setBounds(10, 190, 170, 23);
		getContentPane().add(bottoneRimuoviAvventore);
		bottoneRimuoviAvventore.addActionListener(new GestioneBottoni());
	
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 257, 89, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.addActionListener(new GestioneBottoni());
		
		arrayAvventori = theController.estrazioneAvventoriDelTavoloInData(tavoli.get(tavoloScelto), dataScelta);
		modelloListaAvventori.removeAllElements();
		modelloListaAvventori.addAll(arrayAvventori);
		
		arrayCameriere = theController.estrazioneCamerieriInServizioAlTavoloinData(tavoli.get(tavoloScelto).getId_Tavolo(), dataScelta);
		modelloListaCameriere.removeAllElements();
		modelloListaCameriere.addAll(arrayCameriere);
		
		for (int i = 0; i<arrayAvventori.size();i++)
		{
			if(Pattern.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]",arrayAvventori.get(i).getN_tel())) contaNTel++;
		}
		
		listaAvventori.addListSelectionListener(new GestioneListe());
		listaCamerieri.addListSelectionListener(new GestioneListe());
		
		if(tavoli.get(tavoloScelto).getCapacita() == arrayAvventori.size()) 
		{
		
			bottoneAggiungiAvventore.setEnabled(false);
			bottoneAggiungiAvventore.setToolTipText("Capacita' massima del tavolo raggiunta");
		}
		
		setVisible(true);
		setResizable(false);
		
	}
	
	private class GestioneBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e )
		{
			if(e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroVisualizzazioneOccupazione(tavoli, dataScelta);
			}
			else if(e.getSource() == bottoneRimuoviCameriere)
			{
				theController.bottoneRimuoviCameriereVisualizzazioneOccupazione(dataScelta, tavoli.get(tavoloScelto).getId_Tavolo(), arrayCameriere.get(indiceListaCamerieri));
				arrayCameriere.remove(indiceListaCamerieri);
				modelloListaCameriere.removeAllElements();
				modelloListaCameriere.addAll(arrayCameriere);
			
			}
			else if(e.getSource() == bottoneRimuoviAvventore)
			{
				theController.bottoneRimuoviAvventoreVisualizzazioneOccupazione(dataScelta,tavoli.get(tavoloScelto).getId_Tavolo(), arrayAvventori.get(indiceListaAvventori));
				if(!arrayAvventori.get(indiceListaAvventori).getN_tel().isBlank()) contaNTel--;
				arrayAvventori.remove(indiceListaAvventori);
				modelloListaAvventori.removeAllElements();
				modelloListaAvventori.addAll(arrayAvventori);
				
			}
			else if(e.getSource() == bottoneAggiungiAvventore)
			{
				ArrayList<String> avventoriAlTavolo = new ArrayList<String>();
				for (int i = 0; i<arrayAvventori.size();i++) avventoriAlTavolo.add(arrayAvventori.get(i).getN_CID());
				System.out.println(avventoriAlTavolo);
				theController.bottoneAggiungiAvventoreVisualizzazioneOccupazione(dataScelta, tavoli, tavoloScelto,avventoriAlTavolo);
			}
			else if(e.getSource() == bottoneAggiungiCameriere)
			{
				ArrayList<Integer> idCamerieriPresenti = new ArrayList<Integer>();
				for(int i = 0;i < arrayCameriere.size(); i++)
				{
					idCamerieriPresenti.add(arrayCameriere.get(i).getId_Cameriere());
				}
				theController.bottoneAggiungiCameriereInterfacciaVisualizzazioneOccupazione(tavoli, tavoloScelto, dataScelta,idCamerieriPresenti);
			}
		}
	}
	
	private class GestioneListe implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			if(e.getSource() == listaCamerieri)
			{
				if(listaCamerieri.getSelectedIndex() == -1)
				{
					bottoneRimuoviCameriere.setEnabled(false);
					indiceListaCamerieri = -1;
				}
				else
				{
					bottoneRimuoviCameriere.setEnabled(true);
					indiceListaCamerieri = listaCamerieri.getSelectedIndex();
					listaAvventori.clearSelection();
					indiceListaAvventori = -1;
					bottoneRimuoviAvventore.setEnabled(false);
				}
				
			}
			else if (e.getSource() == listaAvventori)
			{
				if(listaAvventori.getSelectedIndex() == -1)
				{
					bottoneRimuoviAvventore.setEnabled(false);
					indiceListaAvventori = -1;
				}
				else
				{
					listaCamerieri.clearSelection();;
					bottoneRimuoviAvventore.setEnabled(true);
					bottoneRimuoviAvventore.setToolTipText("");
					indiceListaAvventori = listaAvventori.getSelectedIndex();
					indiceListaCamerieri = -1;
					bottoneRimuoviCameriere.setEnabled(false);
				}
			}
			if(arrayCameriere.size() == 1) bottoneRimuoviCameriere.setEnabled(false);
			if(arrayAvventori.size() == 1) bottoneRimuoviAvventore.setEnabled(false);
			if(!arrayAvventori.get(indiceListaAvventori!=-1 ? indiceListaAvventori : 0).getN_tel().isBlank() && contaNTel == 1) 
				{
					bottoneRimuoviAvventore.setEnabled(false);
					bottoneRimuoviAvventore.setToolTipText("Non si può rimuovere un avventore con un numero di telefono!");
				}
			if(tavoli.get(tavoloScelto).getCapacita() == arrayAvventori.size()) 
				{
				
					bottoneAggiungiAvventore.setEnabled(false);
					bottoneAggiungiAvventore.setToolTipText("Capacita' massima del tavolo raggiunta");
				}
			else
				{
					bottoneAggiungiAvventore.setEnabled(true);
					bottoneAggiungiAvventore.setToolTipText("");
				}
		}
	}
	
}
