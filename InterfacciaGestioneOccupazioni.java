import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import javax.swing.JButton;

public class InterfacciaGestioneOccupazioni extends JFrame 
{
	private int numeroTavoloSelezionato;
	private ArrayList<JLabel> numeri;
	private JLayeredPane areaDiDisegno;
	private JLabel background;
	private Controller theController;
	private ArrayList<Tavolo> tavoli;
	private JButton bottoneIndietro;
	public InterfacciaGestioneOccupazioni(Controller controller,ArrayList<Tavolo> tavoli, String data) 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 515);
		getContentPane().setLayout(null);
		this.theController = controller;
		this.tavoli = tavoli;
		
		areaDiDisegno = new JLayeredPane();
		areaDiDisegno.setBounds(10, 11, 659, 362);
		getContentPane().add(areaDiDisegno);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 442, 105, 23);
		getContentPane().add(bottoneIndietro);
		
		JButton vediOccupazione = new JButton("Vedi occupazione");
		vediOccupazione.setBounds(10, 384, 130, 23);
		getContentPane().add(vediOccupazione);
		background = new JLabel();
		background.setBounds(0, 0, 659, 362);
		background.setBackground(Color.white);
		background.setOpaque(true);
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 659, 362);
		
		gestoreIcone handler = new gestoreIcone();
		
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
		
		//controllo dei tavoli occupati nella data scelta + bordi settati a rosso per quei tavoli
		
		areaDiDisegno.add(background,0,-1);
		
		setVisible(true);
		setResizable(true);
		
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
		controllo = 0;
		while(!tavolo && controllo < numeri.size())
			{
				if(e.getSource() == numeri.get(controllo))
				{
					tavolo = true;
					numeroTavoloSelezionato = Integer.parseInt(numeri.get(controllo).getText());
					numeri.get(controllo).setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
				}
				controllo++;
			}
			if(tavolo)
			{
				
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

