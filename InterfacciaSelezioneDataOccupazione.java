import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.components.JLocaleChooser;
import com.toedter.calendar.JYearChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JDayChooser;
import javax.swing.JTextField;
import java.awt.Color;
public class InterfacciaSelezioneDataOccupazione extends JFrame
{

	private JButton bottoneSet;
	private JTextField textFieldData;
	private JCalendar calendar; 
	private JLabel istruzioni2;
	private JLabel istruzioni3;
	private JLabel istruzioni4;
	private JButton goNext;
	private JButton bottoneIndietro;
	private ArrayList<Tavolo> tavoli;
	private Controller theController;
	public InterfacciaSelezioneDataOccupazione(Controller controller, ArrayList<Tavolo> tavoli)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 268);
		getContentPane().setLayout(null);
		this.tavoli= tavoli;
		this.theController = controller;
		bottoneSet = new JButton("Set");
		bottoneSet.setBounds(194, 63, 66, 23);
		getContentPane().add(bottoneSet);
		calendar = new JCalendar();
		calendar.setBounds(0, 30, 184, 153);
		getContentPane().add(calendar);
		
		
		textFieldData = new JTextField();
		textFieldData.setBounds(4, 203, 172, 23);
		textFieldData.setBackground(Color.white);
		textFieldData.setEditable(false);
		textFieldData.setOpaque(true);
		textFieldData.setColumns(10);
		
		getContentPane().add(textFieldData);
		
		JLabel istruzioni = new JLabel("Scegliere una data dal calendario.");
		istruzioni.setBounds(10, 5, 235, 14);
		getContentPane().add(istruzioni);
		
		istruzioni2 = new JLabel("Poi, premere");
		istruzioni2.setBounds(194, 30, 82, 14);
		getContentPane().add(istruzioni2);
		
		istruzioni3 = new JLabel("\"Set\"");
		istruzioni3.setBounds(194, 42, 82, 14);
		getContentPane().add(istruzioni3);
		
		istruzioni4 = new JLabel("Dopo aver impostato la data, premere la freccia");
		istruzioni4.setBounds(2, 185, 284, 14);
		getContentPane().add(istruzioni4);
		
		goNext = new JButton("->");
		goNext.setBounds(194, 203, 56, 23);
		getContentPane().add(goNext);
		
		goNext.addActionListener(new GestioneBottone());
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(187, 111, 89, 23);
		getContentPane().add(bottoneIndietro);
		
		bottoneIndietro.addActionListener(new GestioneBottone());
		
		bottoneSet.addActionListener(new GestioneBottone());
		
		setResizable(false);
		setVisible(true);
	}

	private class GestioneBottone implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == bottoneSet)
			{
					textFieldData.setText(String.format("%d-%d-%d",calendar.getDate().getYear()+1900, calendar.getDate().getMonth()+1,calendar.getDayChooser().getDay()));
			}
			else if(e.getSource()== goNext)
			{
				if(textFieldData.getText().isBlank()) JOptionPane.showMessageDialog(null, "Scegliere prima una data dal calendario.");
				//else metodo per andare all'interfacciaGestioneOccupazione
					
			}
			else if(e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroInterfacciaSelezioneDataGestioneOccupazionePremuto(tavoli.get(1).getSala_App());
			}
		}
	}
}