import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextField;

public class InterfacciaGestioneOccupazioni extends JFrame 
{
	private int numeroTavoloSelezionato;
	private ArrayList<JLabel> numeri = new ArrayList<JLabel>();
	private JLayeredPane areaDiDisegno;
	private JLabel background;
	private Controller theController;
	private ArrayList<Tavolo> tavoli;
	private ArrayList<Integer> tavoliOccupati;
	private JButton bottoneIndietro;
	private JTextField textFieldNumeroAvventori;
	private JButton vediOccupazione;
	private JButton occupaTavolo;
	private String data;
	private int buttonUnlockOccupa = -2;
	private int indiceTavoloSelezionato;
	private boolean intero;
	private JLabel iconaInformazioni;
	public InterfacciaGestioneOccupazioni(Controller controller,ArrayList<Tavolo> tavoli, String data) 
	{
		super("Occupazioni della sala "+tavoli.get(0).getSala_App().getNome()+" del "+ data);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 515);
		getContentPane().setLayout(null);
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		this.theController = controller;
		this.tavoli = tavoli;
		this.data = data;
		
		areaDiDisegno = new JLayeredPane();
		areaDiDisegno.setBounds(10, 11, 659, 362);
		getContentPane().add(areaDiDisegno);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 442, 105, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.addActionListener(new GestoreBottoni());
		
		vediOccupazione = new JButton("Vedi occupazione");
		vediOccupazione.setBounds(10, 384, 130, 23);
		getContentPane().add(vediOccupazione);
		vediOccupazione.addActionListener(new GestoreBottoni());
		
		occupaTavolo = new JButton("Occupare");
		occupaTavolo.setBounds(195, 384, 105, 23);
		getContentPane().add(occupaTavolo);
		occupaTavolo.addActionListener(new GestoreBottoni());
		
		
		
		textFieldNumeroAvventori = new JTextField();
		textFieldNumeroAvventori.setBounds(195, 423, 105, 20);
		getContentPane().add(textFieldNumeroAvventori);
		textFieldNumeroAvventori.setColumns(10);
		textFieldNumeroAvventori.addKeyListener(new GestioneTesto());
		textFieldNumeroAvventori.setFocusable(true);
		
		JLabel numeroOccupanti = new JLabel("Numero di avventori");
		numeroOccupanti.setBounds(195, 408, 105, 14);
		getContentPane().add(numeroOccupanti);
		background = new JLabel();
		background.setBounds(0, 0, 659, 362);
		background.setBackground(Color.white);
		background.setOpaque(true);
		background.addMouseListener(new GestoreIcone());
		
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 659, 362);
		
		GestoreIcone handler = new GestoreIcone();
		
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
		
		tavoliOccupati = theController.estrazioneSaleOccupateInData(tavoli.get(0).getSala_App(), data);
		
		for (int i = 0; i<tavoliOccupati.size(); i++)
		{
			for (int j = 0; j<tavoli.size();j++)
			{
				if(tavoliOccupati.get(i)== tavoli.get(j).getNumero())
				{
					for (int k = 0; k<numeri.size(); k++)
					{
						if(Integer.parseInt(numeri.get(k).getText()) == tavoli.get(j).getNumero())
						{
							numeri.get(k).setBackground(Color.RED);
							numeri.get(k).setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
						}
					}
				}
			}
		}
		
		vediOccupazione.setEnabled(false);
		occupaTavolo.setEnabled(false);
		
		iconaInformazioni = new JLabel();
		iconaInformazioni.setIcon(UIManager.getIcon("OptionPane.informationIcon"));	
		iconaInformazioni.setBounds(606, 384, 32, 43);
		iconaInformazioni.setToolTipText("<html>In rosso: tavoli gia occupati;<br>In marrone tavoli da occupare.<br>Per occupare un tavolo bisogna inserire un numero di occupanti che rispetti la sua capacità.</html>");
	
		getContentPane().add(iconaInformazioni);
		areaDiDisegno.add(panel,0,1);
		areaDiDisegno.add(background,0,-1);
		
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
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==occupaTavolo)
			{
				System.out.println(indiceTavoloSelezionato);
				theController.bottoneOccupaGestioneOccupazionePremuto(Integer.parseInt(textFieldNumeroAvventori.getText()), tavoli,indiceTavoloSelezionato, data);
			}
			else if(e.getSource() == vediOccupazione)
			{
				theController.bottoneVisualizzaOccupazioneGestioneOccupazione(tavoli, data, indiceTavoloSelezionato);
			}
			else if(e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroGestioneOccupazioniPremuto(tavoli);
			}
		}
	}
	
	private class GestoreIcone implements MouseListener
	{ 
	public void mouseClicked(MouseEvent e)
	{
		boolean tavolo = false;
		boolean aggiustato = false;
		boolean occupato = false;
		int controllo = 0;
		if(e.getSource() == background)
		{
			vediOccupazione.setEnabled(false);
		}
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
					int controllo2 = 0;
					boolean trovato = false;
					tavolo = true;
					numeroTavoloSelezionato = Integer.parseInt(numeri.get(controllo).getText());
					while(!trovato && controllo2<tavoli.size())
					{
						if(tavoli.get(controllo2).getNumero() == numeroTavoloSelezionato)
						{
							trovato = true;
							indiceTavoloSelezionato = controllo2;
							if(tavoliOccupati.contains(tavoli.get(controllo2).getNumero()))
							{
								occupato = true;
							}
						}
						controllo2++;
					}
					numeri.get(controllo).setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
				}
				controllo++;
			}
		if(e.getSource() == background)
		{
			numeroTavoloSelezionato = 0;
			vediOccupazione.setEnabled(false);
		}
		
			try
			{
				if(!textFieldNumeroAvventori.getText().isBlank())
				{
					if(Integer.parseInt(textFieldNumeroAvventori.getText())>0 && Integer.parseInt(textFieldNumeroAvventori.getText())<= tavoli.get(indiceTavoloSelezionato).getCapacita()) intero = true;
					else intero = false;
				}
				else
				{
					intero = false;
				}
			}
			catch(NumberFormatException n)
			{
				intero = false;
			}
		
			if(numeroTavoloSelezionato != 0 && intero) occupaTavolo.setEnabled(true);
			
			else occupaTavolo.setEnabled(false);
			
			if(occupato) 
				{
					vediOccupazione.setEnabled(true);
					occupaTavolo.setEnabled(false);
				}
			else vediOccupazione.setEnabled(false);
			
			
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {} 
		public void mouseEntered(MouseEvent e) {} 
		public void mouseExited(MouseEvent e) {}
	}
	private class GestioneTesto implements KeyListener
	{

	
		public void keyTyped(KeyEvent e) {}

	
		public void keyPressed(KeyEvent e) {}

		
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(!textFieldNumeroAvventori.getText().isBlank())
				{
					if(Integer.parseInt(textFieldNumeroAvventori.getText())>0 &&  Integer.parseInt(textFieldNumeroAvventori.getText())<= tavoli.get(indiceTavoloSelezionato).getCapacita()) intero = true;
					else intero = false;
				}
				else
				{
					intero = false;
				}
			}
			catch(NumberFormatException n)
			{
				intero = false;
			}
			
			if(numeroTavoloSelezionato != 0 && intero) occupaTavolo.setEnabled(true);
			
			else occupaTavolo.setEnabled(false);
			
			System.out.println(intero+ " "+ numeroTavoloSelezionato+" "+textFieldNumeroAvventori.getText());
		}
		
	}
}

