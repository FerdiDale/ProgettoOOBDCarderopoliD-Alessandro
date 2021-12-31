import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseMotionListener;



import java.util.ArrayList;

public class InterfacciaAggiuntaTavoli extends JFrame {

	private JLabel background;
	private JLayeredPane areaDiDisegno;
	private ArrayList<JLabel> numeri = new ArrayList<JLabel>();
	private boolean paintTable = false;
	private int posXIniziale;
	private int posYIniziale;
	private int posYFinale;
	private JLabel nuovoTavolo;
	private JLayeredPane riferimento;
	
	public InterfacciaAggiuntaTavoli(Sala salaCurr, ArrayList<Tavolo> tavoliGiaPresenti) 
	{
		super("Aggiunta tavolo alla sala "+ salaCurr.getNome());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 515);
		setLayout(null);
		

		
		
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 659, 362);
		
		areaDiDisegno = new JLayeredPane();
		riferimento = areaDiDisegno;
		areaDiDisegno.setBounds(10, 11, 659, 362);
		this.add(areaDiDisegno);
		areaDiDisegno.setLayout(null);
		areaDiDisegno.add(panel, 0, 1);
		
		background = new JLabel();
		background.setBounds(0, 0, 659, 362);
		background.setBackground(Color.white);
		background.setOpaque(true);
		areaDiDisegno.add(background,0,-1);
		
		gestioneDisegnoSecondoClick handlerDD = new gestioneDisegnoSecondoClick();
		inizializzazioneDisegno handlerD = new inizializzazioneDisegno();
		
		background.addMouseMotionListener(handlerDD);
		background.addMouseListener(handlerD);
		
		
		for (int i = 0; i<tavoliGiaPresenti.size(); i++)
		{
			JLabel tavoloCurr = new JLabel(String.format("%d",tavoliGiaPresenti.get(i).getNumero()));
			tavoloCurr.setBackground(new Color(129,116,37));
			tavoloCurr.setOpaque(true);
			tavoloCurr.setBounds(tavoliGiaPresenti.get(i).getPosX(), tavoliGiaPresenti.get(i).getPosY(), tavoliGiaPresenti.get(i).getDimX(), tavoliGiaPresenti.get(i).getDimY());
			JLabel etichettaADestra= new JLabel();
			JLabel etichettaInBasso = new JLabel();
			JLabel etichettaInBassoADestra = new JLabel();
			etichettaADestra.setBounds(tavoliGiaPresenti.get(i).getPosX()+tavoliGiaPresenti.get(i).getDimX()-3, (tavoliGiaPresenti.get(i).getPosY()+tavoliGiaPresenti.get(i).getDimY()/2) -3, 6, 6);
			etichettaADestra.setOpaque(true);
			etichettaADestra.setBackground(Color.black);
			etichettaInBasso.setBounds((tavoliGiaPresenti.get(i).getPosX()+tavoliGiaPresenti.get(i).getDimX()/2)-3,tavoliGiaPresenti.get(i).getPosY()+tavoliGiaPresenti.get(i).getDimY()-3,6,6);
			etichettaInBassoADestra.setBounds(tavoliGiaPresenti.get(i).getPosX()+tavoliGiaPresenti.get(i).getDimX()-3,tavoliGiaPresenti.get(i).getPosY()+tavoliGiaPresenti.get(i).getDimY()-3,6,6);
			etichettaInBasso.setOpaque(true);
			etichettaInBassoADestra.setOpaque(true);
			etichettaInBasso.setBackground(Color.black);
			etichettaInBassoADestra.setBackground(Color.black);
			areaDiDisegno.add(tavoloCurr,0,1);
			areaDiDisegno.add(etichettaInBasso,0,0);
			areaDiDisegno.add(etichettaInBassoADestra,0,0);
			areaDiDisegno.add(etichettaADestra,0,0);
			numeri.add(tavoloCurr);
		}
		
		
		setResizable(false);
		setVisible(true);
		
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
	
	private class inizializzazioneDisegno implements MouseListener
	{


		private int posXFinale;


		public void mouseClicked(MouseEvent e) 
		{
			if(e.getSource() == background)
			{
				if(!paintTable) 
					{
						paintTable = true;
						posXIniziale = e.getX();
						posYIniziale = e.getY();
						nuovoTavolo = new JLabel();
						nuovoTavolo.setBounds(posXIniziale,posYIniziale,0,0);
						nuovoTavolo.setOpaque(true);
						nuovoTavolo.setBackground(Color.black);
						riferimento.add(nuovoTavolo,0,1);
					}
				else 
					{
						paintTable = false;
					}
			}
		}


		public void mousePressed(MouseEvent e) 
		{
			
		}


		public void mouseReleased(MouseEvent e) 
		{
		
		}


		public void mouseEntered(MouseEvent e) 
		{
		
		}


		public void mouseExited(MouseEvent e) 
		{

			
		}
		
		
	}
	
	private class gestioneDisegnoSecondoClick implements MouseMotionListener
	{

		@Override
		public void mouseDragged(MouseEvent e) 
		{
			
			
		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{
			
			if(paintTable)
			{
				nuovoTavolo.setBounds(posXIniziale,posYIniziale,e.getX()-posXIniziale,e.getY()-posYIniziale);
			}
			
			
		}
		
	}

}
