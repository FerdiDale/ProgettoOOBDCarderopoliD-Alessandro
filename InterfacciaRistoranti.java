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

public class InterfacciaRistoranti extends JFrame {

	private JScrollPane scrollPane;
	private Controller theController;
	private RistoranteDAO ristDao = new RistoranteDAOImplPostgres();
	private JList<Ristorante> listaVisibile;

	/**
	 * Create the frame.
	 */
	public InterfacciaRistoranti(Controller c) {
		theController = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 484);
		getContentPane().setLayout(null);

		setVisible(true);
		
		creaLista();
		listaVisibile.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listaVisibile.setLayoutOrientation(JList.VERTICAL);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(347, 11, 203, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(347, 60, 203, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(347, 106, 203, 23);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(347, 192, 203, 23);
		getContentPane().add(btnNewButton_3);

		
	}
	
	public void creaLista() {
		
		boolean errore = false;
		ArrayList<Ristorante> listaRistoranti = new ArrayList<Ristorante>();
		DefaultListModel<Ristorante> modelloLista = new DefaultListModel<Ristorante>();
		
		do {
			try
			{
				listaRistoranti = ristDao.estraiTuttiRistoranti();
				errore = false;
			}
			catch (operazioneFallitaException ecc)
			{
				errore = true;
			}
		} while (errore);
		
		ListIterator<Ristorante> iteratore = listaRistoranti.listIterator();
		Ristorante ristcurr = new Ristorante();
		
		modelloLista.addAll(listaRistoranti);
		listaVisibile = new JList<Ristorante>(modelloLista);
		listaVisibile.setBounds(10, 10, 275, 425);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 0, 312, 434);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(listaVisibile);
		listaVisibile.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listaVisibile.setLayoutOrientation(JList.VERTICAL);
	
	}
}
