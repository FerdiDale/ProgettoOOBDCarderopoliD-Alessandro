import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class InterfacciaAdiacenze extends JFrame {

	private Controller theController;
	private Sala sala;
	private Tavolo tavoloProtagonista;
	private ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
	private ArrayList<JLabel> numeri = new ArrayList<JLabel>();
	private JLabel background;
	private JLayeredPane areaDiDisegno;
	private JLabel tavoloSelezionato;
	private Tavolo tavoloEntitaSelezionato;
	
	/**
	 * Create the frame.
	 */
	public InterfacciaAdiacenze(Controller c, Tavolo tavoloCorrente) {
		super("Adiacenze del tavolo numero " + tavoloCorrente.getNumero() + " della sala "+ tavoloCorrente.getSala().getNome());
		getContentPane().setLayout(null);
		theController = c;
		tavoloProtagonista = tavoloCorrente;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 452);
		this.sala = tavoloCorrente.getSala();
		getContentPane().setLayout(null);
		
		areaDiDisegno = new JLayeredPane();
		areaDiDisegno.setBounds(10, 11, 659, 362);
		getContentPane().add(areaDiDisegno);
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 659, 362);
		areaDiDisegno.add(panel, 0);
		
		JButton bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.adiacenzeIndietroPremuto(sala);
			}
		});
		bottoneIndietro.setBounds(10, 384, 89, 23);
		getContentPane().add(bottoneIndietro);
		
		gestoreIcone handler = new gestoreIcone();
		
		JButton bottoneConferma = new JButton("Conferma");
		bottoneConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.bottoneConfermaModificheAdiacensePremuto(tavoloProtagonista);
			}
		});
		bottoneConferma.setBounds(580, 384, 89, 23);
		getContentPane().add(bottoneConferma);
		background = new JLabel();
		background.setBounds(0, 0, 659, 362);
		background.setBackground(Color.white);
		background.setOpaque(true);
		areaDiDisegno.add(background, -30000);
		
		tavoli = theController.EstrazioneTavoliSala(sala);
		tavoloProtagonista.setTavoliAdiacenti(theController.estraiTavoliAdiacenti (tavoloProtagonista));
		for (Tavolo tavolo: tavoli)
		{
			JLabel tavoloCurr = new JLabel(String.format("%d",tavolo.getNumero()));
			tavoloCurr.addMouseListener(handler);
			tavoloCurr.setBackground(new Color(129,116,37));
			tavoloCurr.setOpaque(true);
			tavoloCurr.setBounds(tavolo.getPosX(), tavolo.getPosY(), tavolo.getDimX(), tavolo.getDimY());
			tavoloCurr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			tavoloCurr.setHorizontalAlignment(SwingConstants.CENTER);
			
			
			for (Tavolo tavoloAdiacente : tavoloProtagonista.getTavoliAdiacenti()) {
				if (tavoloAdiacente.getNumero() == (tavolo.getNumero()))
					tavoloCurr.setBackground(Color.YELLOW);
			}
			
			areaDiDisegno.add(tavoloCurr,0,1);
	
			numeri.add(tavoloCurr);
			
		}
		
		setVisible(true);
		setResizable(false);
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
		public void mousePressed (MouseEvent e)
		{
			boolean tavolo = false;
			int controllo = -1;
			while(!tavolo && controllo < numeri.size())
				{
					controllo++;
					if(e.getSource() == numeri.get(controllo))
					{
						tavolo = true;
						tavoloSelezionato = numeri.get(controllo);
						tavoloEntitaSelezionato = trovaTavoloAssociato(Integer.parseInt(tavoloSelezionato.getText()));
					}
				}
			
				if(tavolo)
				{
					if (Integer.parseInt(tavoloSelezionato.getText()) == tavoloProtagonista.getNumero()) {
						JOptionPane.showMessageDialog(null, "Un tavolo non puo' essere adiacente di se stesso!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
					}
					else {
						if (tavoloSelezionato.getBackground().equals(Color.YELLOW)) {
							//Deseleziona
							boolean trovato = false;
							tavoloSelezionato.setBackground(new Color(129,116,37));
							for(Tavolo tavoloAdiacente : tavoloProtagonista.getTavoliAdiacenti()) {
								if (tavoloAdiacente.getNumero() == (tavoloEntitaSelezionato.getNumero())) {
									tavoloEntitaSelezionato = tavoloAdiacente;
									trovato = true;
								}
							}

							if (trovato)
								tavoloProtagonista.getTavoliAdiacenti().remove(tavoloEntitaSelezionato);

						}
						else {
							//Seleziona
							tavoloSelezionato.setBackground(Color.YELLOW);
							tavoloProtagonista.getTavoliAdiacenti().add(tavoloEntitaSelezionato);
						}
					}
					
				}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			
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
}