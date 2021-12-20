import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class InterfacciaSale extends JFrame 
{
	public ArrayList<Sala> lista = new ArrayList<Sala>();
	public DefaultListModel<Sala> modellolista = new DefaultListModel<Sala>();
	public JList<Sala> listavisibile = new JList<>(modellolista);
	public InterfacciaSale(String nomeristorante, int id_ristorante) 
	{
		super("Sale del ristorante "+ nomeristorante);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton AggiuntaSala = new JButton("Aggiungi sala");
		AggiuntaSala.setBounds(290, 35, 120, 23);
		getContentPane().add(AggiuntaSala);
		
		JButton GestioneCamerieri = new JButton("Gestisci camerieri");
		GestioneCamerieri.setBounds(290, 80, 120, 23);
	    getContentPane().add(GestioneCamerieri);
		
		JButton RimuoviSala = new JButton("Rimuovi sala selezionata");
		RimuoviSala.setBounds(234, 195, 176, 55);
		getContentPane().add(RimuoviSala);
		
		JButton VediTavoli = new JButton("Vedi i tavoli della sala selezionata");
		VediTavoli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		VediTavoli.setBounds(10, 195, 205, 55);
		add(VediTavoli);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 253, 170);
	    add(scrollPane);
		
		scrollPane.setViewportView(listavisibile);
		
		setResizable(false);
		
		try
		{
			SalaDAOImplPostgres SDAO = new SalaDAOImplPostgres();
			lista = SDAO.EstraiSaleRistorante(id_ristorante);
		}finally {}
		modellolista.addAll(lista);
	}
}
