import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import java.awt.Insets;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfacciaRistoranti extends JFrame {

	private Controller theController;
	private JList<Ristorante> listaVisibile;
	ArrayList<Ristorante> listaRistoranti = new ArrayList<Ristorante>();
	DefaultListModel<Ristorante> modelloLista = new DefaultListModel<Ristorante>();
	/**
	 * Create the frame.
	 */
	public InterfacciaRistoranti(Controller c) {
		setTitle("Ristoranti");
		theController = c;
		
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 581, 470);
		getContentPane().setLayout(null);

		creaLista();

		listaVisibile.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listaVisibile.setLayoutOrientation(JList.VERTICAL);

		JButton bottoneAggiungiRistorante = new JButton("Aggiungi un ristorante");
		bottoneAggiungiRistorante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				theController.bottoneAggiungiRistorantePremuto();		
			}
		});
		bottoneAggiungiRistorante.setBounds(10, 270, 551, 23);
		getContentPane().add(bottoneAggiungiRistorante);
		bottoneAggiungiRistorante.setVisible(true);
		
		JButton bottoneModificaRistorante = new JButton("Modifica i dati del ristorante selezionato");
		bottoneModificaRistorante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bottoneModificaRistorante.isEnabled()) {
					Ristorante ristoranteSelezionato = listaRistoranti.get(listaVisibile.getSelectedIndex());
					theController.bottoneModificaRistorantePremuto(ristoranteSelezionato);
				}
			}
		});
		bottoneModificaRistorante.setBounds(10, 300, 551, 23);
		getContentPane().add(bottoneModificaRistorante);
		bottoneModificaRistorante.setVisible(true);
		if (listaVisibile.getSelectedIndex()==-1) bottoneModificaRistorante.setEnabled(false);
		
		JButton bottoneEliminaRistorante = new JButton("Elimina il ristorante selezionato");
		bottoneEliminaRistorante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bottoneEliminaRistorante.isEnabled()) {
					Ristorante ristoranteSelezionato = listaRistoranti.get(listaVisibile.getSelectedIndex());
					theController.bottoneEliminaRistorantePremuto(ristoranteSelezionato);
					listaRistoranti.remove(listaVisibile.getSelectedIndex());
					modelloLista.removeAllElements();
					modelloLista.addAll(listaRistoranti);
				}
			}
		});
		bottoneEliminaRistorante.setBounds(10, 333, 551, 23);
		getContentPane().add(bottoneEliminaRistorante);
		bottoneEliminaRistorante.setVisible(true);
		if (listaVisibile.getSelectedIndex()==-1) bottoneEliminaRistorante.setEnabled(false);
	
		JButton bottoneVisualizzaStatisticheRistorante = new JButton("Visualizza statistiche del ristorante selezionato");
		bottoneVisualizzaStatisticheRistorante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bottoneVisualizzaStatisticheRistorante.isEnabled()) {
					Ristorante ristoranteSelezionato = listaRistoranti.get(listaVisibile.getSelectedIndex());
					theController.bottoneVisualizzaStatistichePremuto(ristoranteSelezionato);
				}
			}
		});
		bottoneVisualizzaStatisticheRistorante.setBounds(10, 400, 551, 23);
		getContentPane().add(bottoneVisualizzaStatisticheRistorante);
		bottoneVisualizzaStatisticheRistorante.setVisible(true);
		if (listaVisibile.getSelectedIndex()==-1) bottoneVisualizzaStatisticheRistorante.setEnabled(false);
		
		JButton bottoneVisualizzaSaleRistorante = new JButton("Visualizza sale del ristorante selezionato");
		bottoneVisualizzaSaleRistorante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bottoneVisualizzaSaleRistorante.isEnabled()) {
					Ristorante ristoranteSelezionato = listaRistoranti.get(listaVisibile.getSelectedIndex());
					theController.bottoneVisualizzaSalePremuto(ristoranteSelezionato);
				}
			}
		});
		bottoneVisualizzaSaleRistorante.setBounds(10, 366, 551, 23);
		getContentPane().add(bottoneVisualizzaSaleRistorante);
		bottoneVisualizzaSaleRistorante.setVisible(true);
		if (listaVisibile.getSelectedIndex()==-1) bottoneVisualizzaSaleRistorante.setEnabled(false);
		
		listaVisibile.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (listaVisibile.getSelectedIndex()==-1){
					bottoneEliminaRistorante.setEnabled(false);
					bottoneVisualizzaStatisticheRistorante.setEnabled(false);
					bottoneVisualizzaSaleRistorante.setEnabled(false);
					bottoneModificaRistorante.setEnabled(false);
				}
				else {
					bottoneEliminaRistorante.setEnabled(true);
					bottoneVisualizzaStatisticheRistorante.setEnabled(true);
					bottoneVisualizzaSaleRistorante.setEnabled(true);
					bottoneModificaRistorante.setEnabled(true);
				}
			}
		});
		
		setVisible(true);
		setResizable(false);
		
	}

	public void creaLista() {

		listaRistoranti = theController.inizializzazioneRistoranti();
		modelloLista.addAll(listaRistoranti);
		listaVisibile = new JList<Ristorante>(modelloLista);
		listaVisibile.setBounds(10, 10, 275, 425);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 574, 259);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(listaVisibile);
	
	}
}