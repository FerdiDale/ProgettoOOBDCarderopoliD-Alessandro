import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextField;
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
	private JTextField dataAssunzione;
	private JTextField dataLicenziamento;
	private JButton setDataAssunzione;
	private JButton setDataLicenziamento;
	private JButton RiassumiCameriere;
	private JButton LicenziaCameriere;
	private JButton AggiuntaCameriere;
	private SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
	private int indiceListaAssunti = -1;
	private int indiceListaLicenziati = -1;
	private Date dataAssunzioneStringa;
	private Date dataLicenziamentoStringa;
	
	
	public InterfacciaGestioneCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Gestione camerieri di "+ristorante.getNome());
		setLayout(null);
		setBounds(100, 100, 450, 350);
		this.theController=theController;
		
		JScrollPane scrollPaneAssunti = new JScrollPane();
		scrollPaneAssunti.setBounds(10, 35, 225, 105);
		add(scrollPaneAssunti);
		
		
		scrollPaneAssunti.setViewportView(listaAssunti);
		
		JScrollPane scrollPaneLicenziati = new JScrollPane();
		scrollPaneLicenziati.setBounds(10, 182, 225, 105);
		add(scrollPaneLicenziati);
		
		scrollPaneLicenziati.setViewportView(listaLicenziati);
		
		JLabel Assunti = new JLabel("Correntemente assunti");
		Assunti.setBounds(10, 10, 129, 14);
		add(Assunti);
		
		JLabel Licenziati = new JLabel("Licenziati");
		Licenziati.setBounds(10, 157, 99, 14);
		add(Licenziati);
		
		AggiuntaCameriere = new JButton("Aggiungi Cameriere");
		AggiuntaCameriere.setBounds(287, 33, 135, 23);
		add(AggiuntaCameriere);
		
		RiassumiCameriere = new JButton("Riassumi Cameriere");
		RiassumiCameriere.setBounds(287, 72, 135, 23);
		RiassumiCameriere.setEnabled(false);
		add(RiassumiCameriere);
		
		dataAssunzione = new JTextField();
		dataAssunzione.setBounds(287, 133, 135, 23);
		add(dataAssunzione);
		dataAssunzione.setColumns(10);
		
		LicenziaCameriere = new JButton("Licenzia Cameriere");
		LicenziaCameriere.setBounds(287, 200, 135, 23);
		LicenziaCameriere.setEnabled(false);
		add(LicenziaCameriere);
		
		dataLicenziamento = new JTextField();
		dataLicenziamento.setBounds(287, 261, 135, 23);
		add(dataLicenziamento);
		dataLicenziamento.setColumns(10);
		
		setDataAssunzione = new JButton("^");
		setDataAssunzione.setBounds(311, 101, 89, 23);
		add(setDataAssunzione);
		
		setDataLicenziamento = new JButton("^");
		setDataLicenziamento.setBounds(311, 229, 89, 23);
		add(setDataLicenziamento);
		
		arrayListAssunti = theController.EstraiCamerieriInServizioC(ristorante);
		arrayListLicenziati = theController.EstraiCamerieriLicenziatiC(ristorante);
		
		modelloListaAssunti.removeAllElements();
		modelloListaLicenziati.removeAllElements();
		
		modelloListaAssunti.addAll(arrayListAssunti);
		modelloListaLicenziati.addAll(arrayListLicenziati);
		
		setResizable(false);
		
		
	}
	
	private class GestioneBottoniETesti implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == setDataAssunzione)
			{
				if(dataAssunzione.getText().isBlank())
				{
					JOptionPane.showMessageDialog(null, "Non si può inserire una data vuota.", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
				else 
				{
					try
					{
						dataAssunzioneStringa = formatoData.parse(dataAssunzione.getText());
					}
					catch(ParseException p)
					{
						JOptionPane.showMessageDialog(null,"La data non e' del formato corretto, riprovare.","Errore!",JOptionPane.ERROR_MESSAGE);
					}
					//se l'indice della lista assunzioni non è -1 allora puoi far premere il bottone di assunzione, altrimenti mostra un messaggio "selezionare il cameriere da riassumere"
				}
			}
		}
	}
	
	private class DateP extends Date
	{
		public String toString()
		{
			return 
		}
	}
}
