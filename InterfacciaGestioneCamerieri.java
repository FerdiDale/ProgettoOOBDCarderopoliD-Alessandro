import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import java.awt.Color;
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
	private JButton bottoneRiassumiCameriere;
	private JButton bottoneLicenziaCameriere;
	private JButton bottoneAggiuntaCameriere;
	private int indiceListaAssunti = -1;
	private int indiceListaLicenziati = -1;
	private GestioneBottoniETesti handlerB = new GestioneBottoniETesti();
	private	GestioneListe handlerL = new GestioneListe();
	private JScrollPane scrollPaneAssunti;
	private JScrollPane scrollPaneLicenziati;
	private JLabel etichettaLicenziati;
	private JLabel etichettaAssunti;
	private JButton tornaIndietro;
	
	
	public InterfacciaGestioneCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Gestione camerieri di "+ristorante.getNome());
		getContentPane().setLayout(null);
		setBounds(100, 100, 444, 370);
		this.theController=theController;
		this.ristorante = ristorante;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		scrollPaneAssunti = new JScrollPane();
		scrollPaneAssunti.setBounds(10, 35, 225, 105);
		getContentPane().add(scrollPaneAssunti);
		
		
		scrollPaneAssunti.setViewportView(listaAssunti);
		
		scrollPaneLicenziati = new JScrollPane();
		scrollPaneLicenziati.setBounds(10, 182, 225, 105);
		getContentPane().add(scrollPaneLicenziati);
		
		scrollPaneLicenziati.setViewportView(listaLicenziati);
		
		etichettaAssunti = new JLabel("Correntemente assunti");
		etichettaAssunti.setBounds(10, 10, 167, 14);
		getContentPane().add(etichettaAssunti);
		
		etichettaLicenziati = new JLabel("Licenziati");
		etichettaLicenziati.setBounds(10, 157, 99, 14);
		getContentPane().add(etichettaLicenziati);
		
		bottoneAggiuntaCameriere = new JButton("Aggiungi Cameriere");
		bottoneAggiuntaCameriere.setBounds(245, 33, 160, 23);
		getContentPane().add(bottoneAggiuntaCameriere);
		
		bottoneRiassumiCameriere = new JButton("Riassumi Cameriere");
		bottoneRiassumiCameriere.setBounds(245, 180, 160, 23);
		bottoneRiassumiCameriere.setEnabled(false);
		getContentPane().add(bottoneRiassumiCameriere);
		
		bottoneLicenziaCameriere = new JButton("Licenzia Cameriere");
		bottoneLicenziaCameriere.setBounds(245, 62, 160, 23);
		bottoneLicenziaCameriere.setEnabled(false);
		getContentPane().add(bottoneLicenziaCameriere);
		
		arrayListAssunti = theController.EstraiCamerieriInServizioC(ristorante);
		arrayListLicenziati = theController.EstraiCamerieriLicenziatiC(ristorante);
		
		modelloListaAssunti.removeAllElements();
		modelloListaLicenziati.removeAllElements();
		
		modelloListaAssunti.addAll(arrayListAssunti);
		modelloListaLicenziati.addAll(arrayListLicenziati);
		
		tornaIndietro = new JButton("Indietro");
		tornaIndietro.setBounds(10, 298, 89, 23);
		getContentPane().add(tornaIndietro);
		
		listaLicenziati.addListSelectionListener(handlerL);
		bottoneRiassumiCameriere.addActionListener(handlerB);
		tornaIndietro.addActionListener(handlerB);
		bottoneLicenziaCameriere.addActionListener(handlerB);
		listaAssunti.addListSelectionListener(handlerL);
		bottoneAggiuntaCameriere.addActionListener(handlerB);

		
		setVisible(true);
		setResizable(false);

	}
	
	private class GestioneBottoniETesti implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == bottoneRiassumiCameriere)
			{
				theController.generaCalendarioAssunzioneCameriere(arrayListLicenziati.get(indiceListaLicenziati));
			}
			else if(e.getSource() == bottoneLicenziaCameriere)
			{
				theController.generaCalendarioLicenziamentoCameriere(arrayListAssunti.get(indiceListaAssunti));
			}
			else if (e.getSource() == bottoneAggiuntaCameriere)
			{
				theController.bottoneAggiungiCamerierePremuto(ristorante);
				ripresaInterfaccia();
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
				
				if (indiceListaLicenziati == -1) {
					
					bottoneRiassumiCameriere.setEnabled(false);
					
				}
				else {
				
					bottoneRiassumiCameriere.setEnabled(true);
					bottoneLicenziaCameriere.setEnabled(false);
					listaAssunti.clearSelection();
					
				}
			}
			else if (e.getSource() == listaAssunti)
			{
				indiceListaAssunti = listaAssunti.getSelectedIndex();
				
				if (indiceListaAssunti == -1) 
				{
					bottoneLicenziaCameriere.setEnabled(false);
				}
				else 
				{
					bottoneLicenziaCameriere.setEnabled(true);
					bottoneRiassumiCameriere.setEnabled(false);
					listaLicenziati.clearSelection();
						
				}
			}
		}	
	}
	
	
	public void ripresaInterfaccia() {
		arrayListAssunti = theController.EstraiCamerieriInServizioC(ristorante);
		arrayListLicenziati = theController.EstraiCamerieriLicenziatiC(ristorante);
		listaAssunti.removeListSelectionListener(handlerL);
		listaLicenziati.removeListSelectionListener(handlerL);
		modelloListaAssunti.removeAllElements();
		modelloListaAssunti.addAll(arrayListAssunti);
		modelloListaLicenziati.removeAllElements();
		modelloListaLicenziati.addAll(arrayListLicenziati);
		listaAssunti.addListSelectionListener(handlerL);
		listaLicenziati.addListSelectionListener(handlerL);
		bottoneLicenziaCameriere.setEnabled(false);
		bottoneRiassumiCameriere.setEnabled(false);
	}
}
