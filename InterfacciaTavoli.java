import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLayeredPane;

public class InterfacciaTavoli extends JFrame { 
	private Controller theController;
	private Sala sala;
	private ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
	private ArrayList<JLabel> numeri = new ArrayList<JLabel>();
	private JButton bottoneAggiuntaTavolo;
	private JButton bottoneIndietro;
	private JButton bottoneModificaLayout;
	private JButton bottoneGestisciAdiacenze;
	private JButton bottoneGestisciOccupazione;
	private int numeroTavoloSelezionato;
	private JLabel background;
	private JLayeredPane areaDiDisegno;
	/**
	* Create the frame.
	*/
	public InterfacciaTavoli(Controller c, Sala salaCorrente) {
		super("Visualizzazione tavoli di "+ salaCorrente.getNome());
		getContentPane().setLayout(null);
		theController = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 515);
		this.sala = salaCorrente;
		setLayout(null);
		GestioneBottoni gestoreBottoni = new GestioneBottoni();
		bottoneGestisciOccupazione = new JButton("Gestisci occupazioni del tavolo selezionato");
		bottoneGestisciOccupazione.setBounds(337, 384, 332, 23);
		add(bottoneGestisciOccupazione);
		bottoneModificaLayout = new JButton("Modifica layout");
		bottoneModificaLayout.setBounds(10, 418, 317, 23);
		add(bottoneModificaLayout);
		bottoneModificaLayout.addActionListener(gestoreBottoni);
		bottoneGestisciAdiacenze = new JButton("Gestisci tavoli adiacenti a quello selezionato");
		bottoneGestisciAdiacenze.setBounds(337, 418, 332, 23);
		add(bottoneGestisciAdiacenze);
		bottoneIndietro = new JButton("Indietro");
		
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			theController.bottoneIndietroGestioneTavoliPremuto(salaCorrente);
		}
		});
		
		bottoneIndietro.setBounds(10, 449, 89, 23);
		add(bottoneIndietro);
		bottoneAggiuntaTavolo = new JButton("Aggiungi tavolo");
		bottoneAggiuntaTavolo.setBounds(10, 384, 317, 23);
		add(bottoneAggiuntaTavolo);
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 659, 362);
		areaDiDisegno = new JLayeredPane();
		areaDiDisegno.setBounds(10, 11, 659, 362);
		add(areaDiDisegno);
		areaDiDisegno.add(panel, 0);
		background = new JLabel();
		background.setBounds(0, 0, 659, 362);
		background.setBackground(Color.white);
		background.setOpaque(true);
		areaDiDisegno.add(background, -30000);
		gestoreIcone handler = new gestoreIcone();
		tavoli = theController.EstrazioneTavoliSala(salaCorrente);
		for (int i = 0; i<tavoli.size(); i++)
		{
			JLabel tavoloCurr = new JLabel(String.format("%d",tavoli.get(i).getNumero()));
			tavoloCurr.setBackground(new Color(129,116,37));
			tavoloCurr.setOpaque(true);
			tavoloCurr.setBounds(tavoli.get(i).getPosX(), tavoli.get(i).getPosY(), tavoli.get(i).getDimX(), tavoli.get(i).getDimY());
			tavoloCurr.addMouseListener(handler);
			areaDiDisegno.add(tavoloCurr,0,1);
			numeri.add(tavoloCurr);
		}
		setVisible(true);
		setResizable(false);
	}

	private class GestioneBottoni implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == bottoneAggiuntaTavolo)
				{
					theController.bottoneAggiuntaTavoloPremuto(sala);
				}
				else if(e.getSource() == bottoneGestisciOccupazione)
				{
				}
				else if (e.getSource() == bottoneGestisciAdiacenze)
				{
				}
				else if(e.getSource() == bottoneModificaLayout)
				{
					theController.bottoneModificaLayoutPremuto(sala);
				}
			}
		}
	
	private class pannelloTavoli extends JPanel
	{
		public void paintComponent (Graphics g)
		{
		g.drawLine(0,0,this.getBounds().width,0);
		g.drawLine(0,0,0,this.getBounds().height);
		g.drawLine(this.getBounds().width -1,0,this.getBounds().width -1,this.getBounds().height -1);
		g.drawLine(0,this.getBounds().height -1,this.getBounds().width -1,this.getBounds().height -1);
		}
	}
	
	private class gestoreIcone implements MouseListener
	{ 
	public void mouseClicked(MouseEvent e)
	{
	boolean tavolo = false;
	int controllo = -1;
	while(!tavolo && controllo < numeri.size())
		{
		controllo++;
		if(e.getSource() == numeri.get(controllo))
		{
			tavolo = true;
			numeroTavoloSelezionato = Integer.parseInt(numeri.get(controllo).getText());
			System.out.println("HEy");
		}
		}
		if(tavolo)
		{
			bottoneGestisciOccupazione.setEnabled(true);
			bottoneGestisciAdiacenze.setEnabled(true);
		}
		} public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		}
		public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		} public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		} public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		}
	}
}