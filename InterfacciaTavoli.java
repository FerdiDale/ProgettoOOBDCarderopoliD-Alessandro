import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLayeredPane;

public class InterfacciaTavoli extends JFrame 
{ 

	private Controller theController;
	private Sala sala;
	private ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
	private ArrayList<JLabel> numeri = new ArrayList<JLabel>();
	private JButton bottoneAggiuntaTavolo;
	private JButton bottoneIndietro;
	private JButton bottoneModificaLayout;
	private JButton bottoneGestisciAdiacenze;
	private JButton bottoneGestisciOccupazione;
	private int numeroTavoloSelezionato = 0;
	private JLabel background;
	private JLayeredPane areaDiDisegno;
	private JButton bottoneModificaDatiTavolo;
	private JButton bottoneEliminaTavolo;

	public InterfacciaTavoli(Controller c, Sala salaCorrente) {
  
		super("Visualizzazione tavoli di "+ salaCorrente.getNome());
		getContentPane().setLayout(null);
		theController = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 555);
		this.sala = salaCorrente;
		getContentPane().setLayout(null);
		
		bottoneGestisciOccupazione = new JButton("Gestisci occupazioni del tavolo selezionato");
		bottoneGestisciOccupazione.setBounds(337, 384, 332, 23);
		getContentPane().add(bottoneGestisciOccupazione);
		
		bottoneModificaLayout = new JButton("Modifica layout");
		bottoneModificaLayout.setBounds(10, 418, 317, 23);
		getContentPane().add(bottoneModificaLayout);
		
		bottoneGestisciAdiacenze = new JButton("Gestisci tavoli adiacenti a quello selezionato");
		bottoneGestisciAdiacenze.setBounds(337, 418, 332, 23);
		getContentPane().add(bottoneGestisciAdiacenze);
		bottoneGestisciAdiacenze.setEnabled(false);
		
		bottoneIndietro = new JButton("Indietro");

		bottoneIndietro.setBounds(10, 482, 89, 23);
		getContentPane().add(bottoneIndietro);
		
		bottoneAggiuntaTavolo = new JButton("Aggiungi tavolo");
		bottoneAggiuntaTavolo.setBounds(10, 384, 317, 23);
		getContentPane().add(bottoneAggiuntaTavolo);
		
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 659, 362);
	
		
		areaDiDisegno = new JLayeredPane();
		areaDiDisegno.setBounds(10, 11, 659, 362);
		getContentPane().add(areaDiDisegno);
		
		bottoneModificaDatiTavolo = new JButton("Modifica dati del tavolo selezionato");
		bottoneModificaDatiTavolo.setBounds(10, 452, 317, 23);
		getContentPane().add(bottoneModificaDatiTavolo);
		bottoneModificaDatiTavolo.setEnabled(false);
		
		bottoneEliminaTavolo = new JButton("Elimina tavolo selezionato");
		bottoneEliminaTavolo.setBounds(337, 452, 332, 23);
		getContentPane().add(bottoneEliminaTavolo);
		areaDiDisegno.add(panel, 0,1);
		bottoneEliminaTavolo.setEnabled(false);
		
		background = new JLabel();
		background.setBounds(0, 0, 659, 362);
		background.setBackground(Color.white);
		background.setOpaque(true);

		areaDiDisegno.add(background, 0,-1);
		
		gestoreIcone handler = new gestoreIcone();
		tavoli = theController.EstrazioneTavoliSala(salaCorrente);
		
		for (int i = 0; i<tavoli.size(); i++)
		{
			JLabel tavoloCurr = new JLabel(String.format("%d",tavoli.get(i).getNumero()),SwingConstants.CENTER);
			tavoloCurr.setBackground(new Color(129,116,37));
			tavoloCurr.setOpaque(true);
			tavoloCurr.setBounds(tavoli.get(i).getPosX(), tavoli.get(i).getPosY(), tavoli.get(i).getDimX(), tavoli.get(i).getDimY());
			tavoloCurr.addMouseListener(handler);

			tavoloCurr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			areaDiDisegno.add(tavoloCurr,0,1);

			numeri.add(tavoloCurr);
		}
		
		GestioneBottoni handlerB = new GestioneBottoni();
		
		bottoneAggiuntaTavolo.addActionListener(handlerB);
		bottoneModificaLayout.addActionListener(handlerB);
		bottoneGestisciOccupazione.addActionListener(handlerB);
		bottoneGestisciAdiacenze.addActionListener(handlerB);
		bottoneIndietro.addActionListener(handlerB);
		bottoneModificaDatiTavolo.addActionListener(handlerB);
		bottoneEliminaTavolo.addActionListener(handlerB);

		setVisible(true);
		setResizable(false);
	}

	private class GestioneBottoni implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == bottoneAggiuntaTavolo)
				{
					theController.bottoneAggiuntaTavoloPremuto(sala, tavoli);
				}
				else if(e.getSource() == bottoneGestisciOccupazione)
				{
					
				}
				else if (e.getSource() == bottoneGestisciAdiacenze)
				{
					theController.bottoneGestisciAdiacenze(trovaTavoloAssociato(numeroTavoloSelezionato));
				}
				else if(e.getSource() == bottoneModificaLayout)
				{
					theController.bottoneModificaLayoutPremuto(sala);
				}
				else if(e.getSource() == bottoneModificaDatiTavolo)
				{
					theController.bottoneModificaDatiPremuto(trovaTavoloAssociato(numeroTavoloSelezionato));
				}
				else if(e.getSource() == bottoneEliminaTavolo)
				{
					theController.bottoneEliminaTavoloPremuto(trovaTavoloAssociato(numeroTavoloSelezionato));
					bottoneGestisciAdiacenze.setEnabled(false);
					bottoneEliminaTavolo.setEnabled(false);
					bottoneModificaDatiTavolo.setEnabled(false);
					tavoli.remove(trovaTavoloAssociato(numeroTavoloSelezionato));
					areaDiDisegno.remove(trovaLabelTavoloAssociato(numeroTavoloSelezionato));	
					areaDiDisegno.validate();
					areaDiDisegno.repaint();
				}
				else if(e.getSource() == bottoneIndietro)
				{
					theController.bottoneIndietroGestioneTavoliPremuto(sala);
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
		boolean aggiustato = false;
		int controllo = 0;
		if(numeroTavoloSelezionato!= 0)
		{
			while(controllo<numeri.size() && !aggiustato)
			{
				
				if(numeri.get(controllo).getText().equals(String.format("%d", numeroTavoloSelezionato)))
				{
					aggiustato = true;
					numeri.get(controllo).setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				}
				controllo++;
			}
		}
		controllo = -1;
		while(!tavolo && controllo < numeri.size())
			{
				controllo++;
				if(e.getSource() == numeri.get(controllo))
				{
					tavolo = true;
					numeroTavoloSelezionato = Integer.parseInt(numeri.get(controllo).getText());
					numeri.get(controllo).setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
				}
			}
			if(tavolo)
			{
				bottoneGestisciAdiacenze.setEnabled(true);
				bottoneEliminaTavolo.setEnabled(true);
				bottoneModificaDatiTavolo.setEnabled(true);
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
	
	public Tavolo trovaTavoloAssociato (int numeroTavolo) {
		
		Tavolo tavoloTrovato = null;
		
		for (Tavolo tavolo : tavoli) {
			if (tavolo.getNumero() == numeroTavolo) {
				tavoloTrovato = tavolo;
			}
		}
		
		return tavoloTrovato;		
		
	}
	
	public JLabel trovaLabelTavoloAssociato (int numeroTavolo) {
		
		JLabel tavoloTrovato = null;
		
		for (JLabel tavoloGrafico : numeri) {
			if (Integer.parseInt(tavoloGrafico.getText()) == numeroTavolo) {
				tavoloTrovato = tavoloGrafico;
			}
		}
		
		return tavoloTrovato;	
	}
}