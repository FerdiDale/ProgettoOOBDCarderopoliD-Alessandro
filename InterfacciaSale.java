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
	private JList<Sala> listavisibile = new JList<>(modelloLista);
	private int elementoSelezionato = -1;
	private JButton GestioneCamerieri;
	private JButton RimuoviSala ;
	private JButton VediTavoli ;
	private JButton AggiuntaSala;
	private JScrollPane scorrimentoPerlistavisibile;
	private Controller theController;
	private JTextField textField; 
	private JTextField textField_1;
	
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
		GestioneCamerieri.setBounds(290, 43, 120, 23);
	    getContentPane().add(GestioneCamerieri);
		
		RimuoviSala = new JButton("Rimuovi sala selezionata");
		RimuoviSala.setBounds(234, 195, 176, 55);
		if (elementoSelezionato == -1) RimuoviSala.setEnabled(false);
		getContentPane().add(RimuoviSala);
		
		VediTavoli = new JButton("Vedi i tavoli della sala selezionata");
		VediTavoli.setBounds(10, 195, 205, 55);
		getContentPane().add(VediTavoli);
		
		scorrimentoPerlistavisibile = new JScrollPane();
		scorrimentoPerlistavisibile.setBounds(10, 11, 253, 170);
	    getContentPane().add(scorrimentoPerlistavisibile);
		
	    scorrimentoPerlistavisibile.setViewportView(listavisibile);
	    
	    textField = new JTextField("Nome sala");
	    textField.setBounds(290, 77, 86, 20);
	    getContentPane().add(textField);
	    textField.setColumns(10);
	    
	    textField_1 = new JTextField("ID sala");
	    textField_1.setBounds(290, 108, 86, 20);
	    getContentPane().add(textField_1);
	    textField_1.setColumns(10);
	    GestoreClickMouse handler = new GestoreClickMouse();
	    textField.addActionListener(handler);
		
		GestoreSelezioneLista selezione = new GestoreSelezioneLista();
		listavisibile.addListSelectionListener(selezione);
		
		GestoreClickMouse click = new GestoreClickMouse();
		RimuoviSala.addActionListener(click);
		
		listaSale.clear();
		listaSale = theController.EstraiSaleRistorante(ristorante);
		
		modelloLista.removeAllElements();
		modelloLista.addAll(listaSale);
	
		AggiuntaSala.addActionListener(handler);

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
		}
	}
		
	private class GestoreSelezioneLista implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			elementoSelezionato = e.getFirstIndex();
			RimuoviSala.setEnabled(true);
		}
	}
}

//Ancora da fare:
//Gestione eventi sulla jlist e sui vari bottoni (collegamenti con le varie interfacce)