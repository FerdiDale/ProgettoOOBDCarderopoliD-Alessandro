import java.awt.BorderLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
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
	private Controller theController;
	private JButton bottoneIndietro;
	private JButton bottoneConfermaSelezione;
	private String data;
	private ArrayList<Tavolo> tavoli;
	private JLabel etichettaCamerieri;
	private int tavoloScelto;
	private int[] indiciDiSelezione;
	public InterfacciaSelezioneCamerieri(Controller controller, ArrayList<Tavolo> tavoli, int tavoloScelto, String data) 
	{
		super("Selezione camerieri disponibili in data "+ data);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 435, 250);
		getContentPane().setLayout(null);
		this.data = data;
		this.tavoli = tavoli;
		this.theController = controller;
		this.tavoloScelto = tavoloScelto;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 188, 119);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(listaCamerieri);

		listaCamerieri.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		bottoneConfermaSelezione = new JButton("Conferma");
		bottoneConfermaSelezione.setBounds(247, 39, 101, 23);
		getContentPane().add(bottoneConfermaSelezione);
		bottoneConfermaSelezione.addActionListener(new GestoreBottoni());
	
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 178, 89, 23);
		getContentPane().add(bottoneIndietro);
		
		bottoneIndietro.addActionListener(new GestoreBottoni());
		etichettaCamerieri = new JLabel("Camerieri disponibili");
		etichettaCamerieri.setBounds(10, 16, 153, 14);
		getContentPane().add(etichettaCamerieri);
		
		arrayCameriere = theController.estraiCamerieriAssegnabili(data);
		modelloListaCameriere.addAll(arrayCameriere);
		
		setVisible(true);
		setResizable(false);
	}
	
	private class GestoreLista implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			indiciDiSelezione = listaCamerieri.getSelectedIndices();
			//cambia metodo di selezione, uno alla volta messi in un altra jlist dalla quale si pigliano i camerieri da mettere nelle query
			
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
				theController.bottoneConfermaSelezioneCamerieriPremuto(indiciDiSelezione, arrayCameriere, data, tavoli, tavoloScelto);
			}
		}
	}
}
