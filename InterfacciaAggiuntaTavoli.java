
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.postgresql.jdbc.PgResultSet.CursorResultHandler;

import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;

import java.util.ArrayList;
import javax.swing.JButton;

public class InterfacciaAggiuntaTavoli extends JFrame {

	private JLabel background;
	private JLayeredPane areaDiDisegno;
	private ArrayList<JLabel> numeri = new ArrayList<JLabel>();
	private boolean paintTable = false;
	private int posXInizialeT;
	private int posYInizialeT;
	private JLabel nuovoTavolo;
	private JLayeredPane riferimento;
	private boolean tavoloDisegnato = false;
	private int posXInizialeS;
	private int posYInizialeS;
	public int dimXF;
	private int dimYF;
	private JLabel nuovaEtichettaInBasso;
	private JLabel nuovaEtichettaInBassoADestra;
	private JLabel nuovaEtichettaADestra;
	private JButton bottoneOk;
	private JButton bottoneIndietro;
	private Controller theController;
	private Tavolo tavoloDaAggiungere = new Tavolo();
	private InterfacciaAggiuntaTavoli riferimentoF = this;
	
	public InterfacciaAggiuntaTavoli(Tavolo tavoloNuovo,Sala salaCurr, ArrayList<Tavolo> tavoliGiaPresenti, Controller controller) 
	{
		super("Aggiunta tavolo alla sala "+ salaCurr.getNome());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 515);
		getContentPane().setLayout(null);
		this.theController = controller;
		tavoloDaAggiungere.setCapacita(tavoloNuovo.getCapacita());
		tavoloDaAggiungere.setNumero(tavoloNuovo.getNumero());
		
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 659, 362);
		
		areaDiDisegno = new JLayeredPane();
		riferimento = areaDiDisegno;
		areaDiDisegno.setBounds(10, 11, 659, 362);
		getContentPane().add(areaDiDisegno);
		areaDiDisegno.setLayout(null);
		
		bottoneOk = new JButton("Ok");
		bottoneOk.setBounds(10, 384, 89, 35);
		getContentPane().add(bottoneOk);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(580, 384, 89, 23);
		getContentPane().add(bottoneIndietro);
		
		bottoneOk.addActionListener(new GestoreBottoni());
		bottoneIndietro.addActionListener(new GestoreBottoni());
		
		background = new JLabel();
		background.setBounds(0, 0, 659, 362);
		background.setBackground(Color.white);
		background.setOpaque(true);
		
		GestioneDisegnoSecondoClick handlerDD = new GestioneDisegnoSecondoClick();
		InizializzazzioneDisegno handlerD = new InizializzazzioneDisegno();
		
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
		areaDiDisegno.add(panel, 0, 1);
		areaDiDisegno.add(background,0,-1);
		
		setResizable(false);
		setVisible(true);
		
	}
	
	private class GestoreBottoni implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource()==bottoneOk)
			{
				tavoloDaAggiungere.setPosX(posXInizialeT);
				tavoloDaAggiungere.setPosY(posYInizialeT);
				tavoloDaAggiungere.setDimX(dimXF);
				tavoloDaAggiungere.setDimY(dimYF);
				
				//theController.bottoneOkInterfacciaAggiuntaTavoliPremuto()
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
	
	private class InizializzazzioneDisegno implements MouseListener
	{
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getSource() == background)
			{
				if(paintTable == false && tavoloDisegnato == false) 
					{
						paintTable = true;
						posXInizialeT = e.getX();
						posYInizialeT = e.getY();
						System.out.println(posXInizialeT+" "+ posYInizialeT);
						nuovoTavolo = new JLabel();
						nuovoTavolo.setBounds(posXInizialeT,posYInizialeT,0,0);
						nuovoTavolo.setOpaque(true);
						nuovoTavolo.setBackground(Color.black);
						nuovoTavolo.addMouseMotionListener(new GestioneDisegnoSecondoClick());
						nuovoTavolo.addMouseListener(new InizializzazzioneDisegno());
						nuovaEtichettaADestra = new JLabel();
						nuovaEtichettaInBasso = new JLabel();
						nuovaEtichettaInBassoADestra = new JLabel();
						
						nuovaEtichettaInBassoADestra.addMouseMotionListener(new GestioneDisegnoSecondoClick());
						nuovaEtichettaInBassoADestra.addMouseListener(new InizializzazzioneDisegno());
						nuovaEtichettaADestra.addMouseMotionListener(new GestioneDisegnoSecondoClick());
						nuovaEtichettaADestra.addMouseListener(new InizializzazzioneDisegno());
						nuovaEtichettaInBasso.addMouseMotionListener(new GestioneDisegnoSecondoClick());
						nuovaEtichettaInBasso.addMouseListener(new InizializzazzioneDisegno());
						nuovaEtichettaADestra.setBackground(Color.red);
						nuovaEtichettaInBasso.setBackground(Color.red);
						nuovaEtichettaInBassoADestra.setBackground(Color.red);
						nuovaEtichettaInBassoADestra.setOpaque(true);
						nuovaEtichettaADestra.setOpaque(true);
						nuovaEtichettaInBasso.setOpaque(true);
						riferimento.add(nuovoTavolo,0,1);
						riferimento.add(nuovaEtichettaInBassoADestra,0,0);
						riferimento.add(nuovaEtichettaADestra,0,0);
						riferimento.add(nuovaEtichettaInBasso,0,0);
					}
				else if (paintTable)
					{
						paintTable = false;
						tavoloDisegnato = true;
						nuovaEtichettaADestra.setBounds(posXInizialeT+dimXF -3, posYInizialeT+dimYF/2 -3,6,6);
						nuovaEtichettaInBasso.setBounds(posXInizialeT+dimXF/2 -3,posYInizialeT+dimYF -3,6,6);
						int somma = posYInizialeT + dimYF -3;
						System.out.println(somma);
						nuovaEtichettaInBassoADestra.setBounds(posXInizialeT+dimXF -3, posYInizialeT + dimYF -3,6,6);
						System.out.println(posXInizialeT+" "+ posYInizialeT);
					}
			}	
	
		}


		public void mousePressed(MouseEvent e) 
		{
			if(tavoloDisegnato && e.getSource() == nuovoTavolo )
			{
				posXInizialeS = e.getX();
				posYInizialeS = e.getY();
			}
			else if(e.getSource() == nuovaEtichettaADestra)
			{
				posXInizialeS = e.getX();
			}
			else if(e.getSource()== nuovaEtichettaInBasso)
			{
				posYInizialeS = e.getY();
			}
			else if(e.getSource()== nuovaEtichettaInBassoADestra)
			{
				posXInizialeS=e.getX();
				posYInizialeS=e.getY();
			}

		}


		public void mouseReleased(MouseEvent e) 
		{

		}


		public void mouseEntered(MouseEvent e) 
		{
			if(e.getSource()==nuovoTavolo)
			{
				riferimentoF.setCursor(Cursor.MOVE_CURSOR);
			}
		}


		public void mouseExited(MouseEvent e) 
		{
			if(e.getSource()== nuovoTavolo)
			{
				riferimentoF.setCursor(Cursor.DEFAULT_CURSOR);
			}
			
		}
		
		
	}
	
	private class GestioneDisegnoSecondoClick implements MouseMotionListener
	{

		public void mouseDragged(MouseEvent e) 
		{
			if(e.getSource()==nuovoTavolo)
			{
				nuovoTavolo.setLocation(posXInizialeT =  posXInizialeT+(e.getX()-posXInizialeS),posYInizialeT =  posYInizialeT +(e.getY() - posYInizialeS));		
				nuovaEtichettaInBassoADestra.setLocation(posXInizialeT+dimXF -3, posYInizialeT+dimYF -3);
				nuovaEtichettaADestra.setLocation(posXInizialeT+dimXF-3,posYInizialeT+dimYF/2 -3);
				nuovaEtichettaInBasso.setLocation(posXInizialeT+dimXF/2 -3, posYInizialeT+dimYF -3);
			}
			else if(e.getSource() == nuovaEtichettaADestra)
			{
				nuovoTavolo.setSize(new Dimension(dimXF= dimXF + (e.getX()-posXInizialeS),dimYF));
				nuovaEtichettaADestra.setLocation(posXInizialeT+dimXF-3,posYInizialeT+dimYF/2 -3);
				nuovaEtichettaInBassoADestra.setLocation(posXInizialeT+dimXF -3, posYInizialeT+dimYF -3);
				nuovaEtichettaInBasso.setLocation(posXInizialeT+dimXF/2 -3, posYInizialeT+dimYF -3);
			}
			else if (e.getSource() == nuovaEtichettaInBasso)
			{
				nuovoTavolo.setSize(new Dimension(dimXF,dimYF = dimYF + (e.getY()-posYInizialeS)));
				nuovaEtichettaADestra.setLocation(posXInizialeT+dimXF-3,posYInizialeT+dimYF/2 -3);
				nuovaEtichettaInBassoADestra.setLocation(posXInizialeT+dimXF -3, posYInizialeT+dimYF -3);
				nuovaEtichettaInBasso.setLocation(posXInizialeT+dimXF/2 -3, posYInizialeT+dimYF -3);
			}
			else if(e.getSource()==nuovaEtichettaInBassoADestra)
			{
				nuovoTavolo.setSize(new Dimension(dimXF= dimXF + (e.getX()-posXInizialeS),dimYF = dimYF + (e.getY()-posYInizialeS)));
				nuovaEtichettaADestra.setLocation(posXInizialeT+dimXF-3,posYInizialeT+dimYF/2 -3);
				nuovaEtichettaInBassoADestra.setLocation(posXInizialeT+dimXF -3, posYInizialeT+dimYF -3);
				nuovaEtichettaInBasso.setLocation(posXInizialeT+dimXF/2 -3, posYInizialeT+dimYF -3);
			}
		}

		public void mouseMoved(MouseEvent e) 
		{
			
			if(paintTable && (e.getSource()==background || e.getSource()==nuovoTavolo || e.getSource()==nuovaEtichettaInBassoADestra))
			{
				nuovoTavolo.setBounds(posXInizialeT,posYInizialeT,dimXF = e.getX()-posXInizialeT,dimYF = e.getY()-posYInizialeT);
				System.out.println(dimXF+ " "+ dimYF);

			}
			
			
		}
		
	}
}
