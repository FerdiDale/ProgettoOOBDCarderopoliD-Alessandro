import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfacciaTavoli extends JFrame  {

	private JPanel contentPane;
	private Controller theController;
	private Sala sala;
	private ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
	private ArrayList<JLabel> numeri = new ArrayList<JLabel>();
	private gestoreIcone handlerI = new gestoreIcone();
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Gestisci occupazioni del tavolo selezionato");
		btnNewButton.setBounds(337, 384, 332, 23);
		contentPane.add(btnNewButton);
		
		JButton bottoneModificaLayout = new JButton("Modifica layout");
		bottoneModificaLayout.setBounds(10, 418, 317, 23);
		contentPane.add(bottoneModificaLayout);
		
		JButton bottoneGestisciAdiacenze = new JButton("Gestisci tavoli adiacenti a quello selezionato");
		bottoneGestisciAdiacenze.setBounds(337, 418, 332, 23);
		contentPane.add(bottoneGestisciAdiacenze);
		
		JButton bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.bottoneIndietroGestioneTavoliPremuto(salaCorrente);
			}
		});
		bottoneIndietro.setBounds(10, 449, 89, 23);
		contentPane.add(bottoneIndietro);
		
		JButton bottoneAggiuntaTavolo = new JButton("Aggiungi tavolo");
		bottoneAggiuntaTavolo.setBounds(10, 384, 317, 23);
		contentPane.add(bottoneAggiuntaTavolo);

		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(10, 11, 659, 362);
		contentPane.add(panel);
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
			tavoli = theController.EstrazioneTavoliSala(sala);
			for (int i = 0 ; i<tavoli.size() ; i++)
			{
				g.setColor(new Color(129,116,37));
				g.drawRect(tavoli.get(i).getPosX(),tavoli.get(i).getPosY(),tavoli.get(i).getDimX(),tavoli.get(i).getDimY());
				JLabel numero = new JLabel(String.format("%d", tavoli.get(i).getNumero()));
				numero.setBounds((tavoli.get(i).getPosX() + tavoli.get(i).getDimX())/2 +10,(tavoli.get(i).getPosY() + tavoli.get(i).getDimY())/2 +11,20,20);
				numero.addMouseListener(handlerI);
				numeri.add(numero);
				
			}
			for(int i = 0; i < numeri.size(); i++)
			{
				getContentPane().add(numeri.get(i));	
			}
		}
	}

	
	private class gestoreIcone implements MouseListener
	{

		public void mouseClicked(MouseEvent e) 
		{
			JOptionPane.showMessageDialog(null, "Hai cliccato");
			
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
