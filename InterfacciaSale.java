import java.awt.BorderLayout;
import javax.swing.event.*;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import java.util.ArrayList;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class InterfacciaSale extends JFrame 
{
	private Ristorante ristorante;
	private ArrayList<Sala> listaSale = new ArrayList<Sala>();
	private DefaultListModel<Sala> modelloLista = new DefaultListModel<Sala>();
	private JList<Sala> listaVisibile = new JList<>(modelloLista);
	private int elementoSelezionato = -1;
	private JButton GestioneCamerieri;
	private JButton RimuoviSala ;
	private JButton VediTavoli ;
	private JButton AggiuntaSala;
	private JScrollPane scorrimentoPerlistavisibile;
	private Controller theController;
	private JButton tornaIndietro;
	
	public InterfacciaSale(Controller c, Ristorante ristorante) 
	{
		super("Sale del ristorante "+ ristorante.getNome());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		theController = c;
		this.ristorante = ristorante;
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		AggiuntaSala = new JButton("Aggiungi sala");
		AggiuntaSala.setBounds(290, 9, 120, 23);
		getContentPane().add(AggiuntaSala);
		
		GestioneCamerieri = new JButton("Gestisci camerieri");
		GestioneCamerieri.setBounds(273, 43, 151, 23);
	    getContentPane().add(GestioneCamerieri);
		
		RimuoviSala = new JButton("Rimuovi sala selezionata");
		RimuoviSala.setBounds(234, 195, 176, 55);
		if (elementoSelezionato == -1) RimuoviSala.setEnabled(false);
		getContentPane().add(RimuoviSala);
		
		VediTavoli = new JButton("Vedi i tavoli della sala selezionata");
		VediTavoli.setBounds(10, 195, 205, 55);
		getContentPane().add(VediTavoli);
		if (elementoSelezionato == -1) VediTavoli.setEnabled(false);
		
		scorrimentoPerlistavisibile = new JScrollPane();
		scorrimentoPerlistavisibile.setBounds(10, 11, 253, 170);
	    getContentPane().add(scorrimentoPerlistavisibile);
		
	    scorrimentoPerlistavisibile.setViewportView(listaVisibile);
	    
	    tornaIndietro = new JButton("Indietro");
	    tornaIndietro.setBounds(290, 136, 120, 17);
	    getContentPane().add(tornaIndietro);
	    GestoreClickMouse handler = new GestoreClickMouse();
		
		GestoreSelezioneLista selezione = new GestoreSelezioneLista();
		listaVisibile.addListSelectionListener(selezione);
		
		tornaIndietro.addActionListener(handler);
		RimuoviSala.addActionListener(handler);
		AggiuntaSala.addActionListener(handler);
		GestioneCamerieri.addActionListener(handler);
		VediTavoli.addActionListener(handler);

		listaSale.clear();
		listaSale = theController.EstraiSaleRistorante(ristorante);
		
		modelloLista.removeAllElements();
		modelloLista.addAll(listaSale);
		
		setResizable(false);
		setVisible(true);
	}

	private class GestoreClickMouse implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == RimuoviSala)
			{
				Sala corrente = listaSale.get(elementoSelezionato);
				listaSale.remove(elementoSelezionato);
				theController.bottoneRimozioneSalaPremuto(corrente);
				modelloLista.removeAllElements();
				modelloLista.addAll(listaSale);
			}
			else if (e.getSource() == AggiuntaSala)
			{
				theController.bottoneAggiuntaSalaPremuto(ristorante);
			}
			else if (e.getSource() == GestioneCamerieri)
			{
				theController.bottoneGestioneCamerieriPremuto(ristorante);
			}
			else if (e.getSource() == tornaIndietro)
			{
				theController.bottoneTornaIndietroSalePremuto();
			}
			else if (e.getSource() == VediTavoli)
			{
				Sala corrente = listaSale.get(elementoSelezionato);
				theController.bottoneVediTavoliPremuto(corrente);
			}
		}
	}
		
	private class GestoreSelezioneLista implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			elementoSelezionato = listaVisibile.getSelectedIndex();
			RimuoviSala.setEnabled(true);
			VediTavoli.setEnabled(true);
		}
	}
}
