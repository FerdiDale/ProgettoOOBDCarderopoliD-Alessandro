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
	private JButton RiassumiCameriere;
	private JButton LicenziaCameriere;
	private JButton AggiuntaCameriere;
	private int indiceListaAssunti = -1;
	private int indiceListaLicenziati = -1;
	private GestioneBottoniETesti handlerB = new GestioneBottoniETesti();
	private	GestioneListe handlerL = new GestioneListe();
	
	
	public InterfacciaGestioneCamerieri(Ristorante ristorante, Controller theController)
	{//BISOGNA GESTIRE QUANDO UN CAMERIERE VIENE LICENZIATO DOPO AVER OCCUPATO UN TAVOLO
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
		
		LicenziaCameriere = new JButton("Licenzia Cameriere");
		LicenziaCameriere.setBounds(287, 67, 160, 23);
		LicenziaCameriere.setEnabled(false);
		getContentPane().add(LicenziaCameriere);
		
		arrayListAssunti = theController.EstraiCamerieriInServizioC(ristorante);
		arrayListLicenziati = theController.EstraiCamerieriLicenziatiC(ristorante);
		
		modelloListaAssunti.removeAllElements();
		modelloListaLicenziati.removeAllElements();
		
		modelloListaAssunti.addAll(arrayListAssunti);
		modelloListaLicenziati.addAll(arrayListLicenziati);
		
		JButton tornaIndietro = new JButton("Indietro");
		tornaIndietro.setBounds(10, 331, 89, 23);
		getContentPane().add(tornaIndietro);
		
		listaLicenziati.addListSelectionListener(handlerL);
		RiassumiCameriere.addActionListener(handlerB);
		tornaIndietro.addActionListener(handlerB);
		LicenziaCameriere.addActionListener(handlerB);
		listaAssunti.addListSelectionListener(handlerL);
		AggiuntaCameriere.addActionListener(handlerB);

		
		setVisible(true);
		setResizable(false);

	}
	
	private class GestioneBottoniETesti implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == RiassumiCameriere)
			{
				theController.generaCalendarioAssunzioneCameriere(arrayListLicenziati.get(indiceListaLicenziati));
			}
			else if(e.getSource() == LicenziaCameriere)
			{
				theController.generaCalendarioLicenziamentoCameriere(arrayListAssunti.get(indiceListaAssunti));
			}
			else if (e.getSource() == AggiuntaCameriere)
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
					
					RiassumiCameriere.setEnabled(false);
					
				}
				else {
				
					RiassumiCameriere.setEnabled(true);
					LicenziaCameriere.setEnabled(false);
					listaAssunti.clearSelection();
					
				}
			}
			else if (e.getSource() == listaAssunti)
			{
				indiceListaAssunti = listaAssunti.getSelectedIndex();
				
				if (indiceListaAssunti == -1) 
				{
					LicenziaCameriere.setEnabled(false);
				}
				else 
				{
					LicenziaCameriere.setEnabled(true);
					RiassumiCameriere.setEnabled(false);
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
		LicenziaCameriere.setEnabled(false);
		RiassumiCameriere.setEnabled(false);
	}
}
