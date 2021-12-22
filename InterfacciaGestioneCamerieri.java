import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import javax.swing.JButton;

public class InterfacciaGestioneCamerieri extends JFrame 
{
	Ristorante ristorante;
	Controller theController;
	private DefaultListModel<Cameriere> modelloListaAssunti = new  DefaultListModel<Cameriere>();
	private	DefaultListModel<Cameriere> modelloListaLicenziati = new DefaultListModel<Cameriere>();
	private JList<Cameriere> listaAssunti = new JList<Cameriere>(modelloListaAssunti);
	private JList<Cameriere> listaLicenziati = new JList<Cameriere>(modelloListaLicenziati);
	private ArrayList<Cameriere> arrayListAssunti = new ArrayList<Cameriere>();
	private ArrayList<Cameriere> arrayListLicenziati = new ArrayList<Cameriere>();
	
	public InterfacciaGestioneCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Gestione camerieri di "+ristorante.getNome());
		getContentPane().setLayout(null);
		this.theController=theController;
		
		JScrollPane scrollPaneAssunti = new JScrollPane();
		scrollPaneAssunti.setBounds(10, 35, 225, 105);
		getContentPane().add(scrollPaneAssunti);
		
		
		scrollPaneAssunti.setViewportView(listaAssunti);
		
		JScrollPane scrollPaneLicenziati = new JScrollPane();
		scrollPaneLicenziati.setBounds(10, 182, 225, 105);
		getContentPane().add(scrollPaneLicenziati);
		
		scrollPaneLicenziati.setViewportView(listaLicenziati);
		
		JLabel Assunti = new JLabel("Correntemente assunti");
		Assunti.setBounds(10, 10, 129, 14);
		getContentPane().add(Assunti);
		
		JLabel Licenziati = new JLabel("Licenziati");
		Licenziati.setBounds(10, 157, 99, 14);
		getContentPane().add(Licenziati);
		
		JButton AggiuntaCameriere = new JButton("Aggiungi Cameriere");
		AggiuntaCameriere.setBounds(288, 33, 135, 23);
		getContentPane().add(AggiuntaCameriere);
		
		JButton RiassumiCameriere = new JButton("Riassumi Cameriere");
		RiassumiCameriere.setBounds(288, 72, 135, 23);
		add(RiassumiCameriere);
		
		arrayListAssunti = theController.EstraiCamerieriInServizioC(ristorante);
		arrayListLicenziati = theController.EstraiCamerieriLicenziatiC(ristorante);
		
		modelloListaAssunti.removeAllElements();
		modelloListaLicenziati.removeAllElements();
		
		modelloListaAssunti.addAll(arrayListAssunti);
		modelloListaLicenziati.addAll(arrayListLicenziati);
		
		setResizable(false);
		
	}
}
