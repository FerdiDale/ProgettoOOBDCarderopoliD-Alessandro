import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
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
	
	public InterfacciaVisualizzazioneOccupazione(Controller controller, ArrayList<Tavolo> tavoli, int tavoloScelto, String dataScelta) 
	{
		int scrematuraArray = 0;
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
		
		JButton bottoneAggiungiCameriere = new JButton("Aggiungi cameriere");
		bottoneAggiungiCameriere.setBounds(310, 156, 164, 23);
		getContentPane().add(bottoneAggiungiCameriere);
		
		bottoneRimuoviCameriere = new JButton("Rimuovi cameriere");
		bottoneRimuoviCameriere.setBounds(310, 190, 164, 23);
		getContentPane().add(bottoneRimuoviCameriere);
		bottoneRimuoviCameriere.setEnabled(false);
		bottoneRimuoviCameriere.addActionListener(new GestioneBottoni());
		
		JButton bottoneAggiungiAvventore = new JButton("Aggiungi avventore");
		bottoneAggiungiAvventore.setBounds(10, 156, 170, 23);
		getContentPane().add(bottoneAggiungiAvventore);
		
		JButton bottoneRimuoviAvventore = new JButton("Rimuovi avventore");
		bottoneRimuoviAvventore.setBounds(10, 190, 170, 23);
		getContentPane().add(bottoneRimuoviAvventore);
		
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
		
		listaCamerieri.addListSelectionListener(new GestioneListe());
		System.out.println(arrayAvventori+ " "+ arrayCameriere);
		
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
				}
				
			}
		}
	}
	
}
