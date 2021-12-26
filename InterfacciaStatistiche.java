import java.awt.BorderLayout;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class InterfacciaStatistiche extends JFrame {

	JTabbedPane tabbedPane;
	private Controller theController;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public InterfacciaStatistiche(Controller c, Ristorante ristoranteScelto, Integer annoScelto) {
		
		theController = c;
		
		ImageIcon icona = new ImageIcon("res/Icona progetto oobd.png");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 500);
		getContentPane().setLayout(null);

		inizializzaInterfaccia(annoScelto, ristoranteScelto);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 824, 461);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton bottoneAumentaAnno = new JButton(">");
		bottoneAumentaAnno.setBounds(534, 407, 48, 43);
		panel.add(bottoneAumentaAnno);
		
		JButton bottoneDiminuisciAnno = new JButton("<");
		bottoneDiminuisciAnno.setBounds(217, 407, 48, 43);
		panel.add(bottoneDiminuisciAnno);
		
		JLabel immagineStatistiche = new JLabel("");
		immagineStatistiche.setBounds(46, 62, 720, 334);
		panel.add(immagineStatistiche);
		
		JButton bottonePassaggioAMese = new JButton("Passa alla visualizzazione delle statistiche per mese");
		bottonePassaggioAMese.setBounds(441, 11, 373, 32);
		panel.add(bottonePassaggioAMese);
		
		textField = new JTextField();
		textField.setBounds(264, 407, 271, 43);
		panel.add(textField);
		textField.setText(annoScelto.toString());
		textField.setColumns(10);

		setVisible(true);
	}
	
	public void inizializzaInterfaccia(Integer annoScelto, Ristorante ristoranteScelto) {
		
		DefaultCategoryDataset statisticheCorrenti = new DefaultCategoryDataset();
		statisticheCorrenti = theController.stampaStatistiche(annoScelto, ristoranteScelto);
		JFreeChart grafico = creaGrafico(statisticheCorrenti);
		ChartPanel pannelloGrafico = new ChartPanel(grafico);
		pannelloGrafico.setVisible(true);
		pannelloGrafico.setBounds(50, 50, 500, 300);
	    pannelloGrafico.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    pannelloGrafico.setBackground(ChartColor.white);
	    add(pannelloGrafico);
	}
	
	public JFreeChart creaGrafico(DefaultCategoryDataset datiStatistiche) {
		 JFreeChart barChart = ChartFactory.createBarChart(
	                "",
	                "",
	                "Numero di avventori",
	                datiStatistiche,
	                PlotOrientation.VERTICAL,
	                false, true, false);

	        return barChart;
	}
}
