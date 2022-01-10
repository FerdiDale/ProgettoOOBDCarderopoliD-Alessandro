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
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterfacciaStatistichePerAnno extends JFrame {

	private Controller theController;
	private JTextField textFieldAnnoCorrente;
	private Integer annoCorrente;
	private ChartPanel pannelloGrafico;
	private JPanel pannello;
	private JButton bottoneAumentaAnno;
	private JButton bottoneDiminuisciAnno;
	private JButton bottonePassaggioAMese;
	private JButton bottoneIndietro;


	public InterfacciaStatistichePerAnno(Controller c, Ristorante ristoranteScelto, Integer annoScelto) {
		
		theController = c;
		annoCorrente = annoScelto;
		
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 500);
		getContentPane().setLayout(null);

		modellaStatistiche(ristoranteScelto);
		
		pannello =  new JPanel();
		pannello.setBounds(0, 0, 824, 461);
		getContentPane().add(pannello);
		pannello.setLayout(null);
		
		bottoneAumentaAnno = new JButton(">");
		bottoneAumentaAnno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer possibileAnno = annoCorrente+1;
				if (possibileAnno>=0) {
					annoCorrente = possibileAnno;
					modellaStatistiche(ristoranteScelto);
				}
				else {
					JOptionPane.showMessageDialog(null, "Hai raggiunto il numero intero massimo!",
							"Attenzione!", JOptionPane.WARNING_MESSAGE);
				}
				textFieldAnnoCorrente.setText(annoCorrente.toString());
			}
		});
		bottoneAumentaAnno.setBounds(534, 407, 48, 43);
		pannello.add(bottoneAumentaAnno);
		
		bottoneDiminuisciAnno = new JButton("<");
		bottoneDiminuisciAnno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer possibileAnno = annoCorrente-1;
				if (possibileAnno>=0) {
					annoCorrente = possibileAnno;
					modellaStatistiche(ristoranteScelto);
				}
				else {
					JOptionPane.showMessageDialog(null, "Non penso ci sia interesse per statistiche precedenti all'anno 0!",
							"Attenzione!", JOptionPane.WARNING_MESSAGE);
				}
				textFieldAnnoCorrente.setText(annoCorrente.toString());
			}
		});
		bottoneDiminuisciAnno.setBounds(217, 407, 48, 43);
		pannello.add(bottoneDiminuisciAnno);
		
		bottonePassaggioAMese = new JButton("Passa alla visualizzazione delle statistiche per mese");
		bottonePassaggioAMese.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				theController.passaggioAMesePremuto(annoCorrente, ristoranteScelto);
			}
		});
		bottonePassaggioAMese.setBounds(441, 11, 373, 32);
		pannello.add(bottonePassaggioAMese);
		
		textFieldAnnoCorrente = new JTextField();
		textFieldAnnoCorrente.setBounds(264, 407, 271, 43);
		pannello.add(textFieldAnnoCorrente);
		textFieldAnnoCorrente.setText(annoCorrente.toString());
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.bottoneIndietroStatistichePremuto(ristoranteScelto);
			}
		});
		bottoneIndietro.setBounds(10, 417, 137, 33);
		pannello.add(bottoneIndietro);
		textFieldAnnoCorrente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(textFieldAnnoCorrente.getText().isBlank()) {
								JOptionPane.showMessageDialog(null, "Inserire un anno valido!",
										"Attenzione!", JOptionPane.WARNING_MESSAGE);
								textFieldAnnoCorrente.setText(annoCorrente.toString());
					}
					else {
						try
						{
							Integer possibileAnno = Integer.parseInt(textFieldAnnoCorrente.getText());
							if (possibileAnno>=0) {
								annoCorrente = possibileAnno;
								modellaStatistiche(ristoranteScelto);
							}
							else {
								JOptionPane.showMessageDialog(null, "Non penso ci sia interesse per statistiche precedenti all'anno 0!",
										"Attenzione!", JOptionPane.WARNING_MESSAGE);
								textFieldAnnoCorrente.setText(annoCorrente.toString());
							}
						}
						catch (NumberFormatException ecc)
						{
							JOptionPane.showMessageDialog(null, "L'anno deve essere un numero valido!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
							textFieldAnnoCorrente.setText(annoCorrente.toString());
						}
					}
				}
			}
		});

		setResizable(false);
		setVisible(true);
	}
	
	public void modellaStatistiche(Ristorante ristoranteScelto) {
		
		DefaultCategoryDataset statisticheCorrenti = new DefaultCategoryDataset();
		statisticheCorrenti = theController.ricavaStatistiche(annoCorrente, ristoranteScelto);
		JFreeChart grafico = creaGrafico(statisticheCorrenti);
		ChartPanel pannelloGrafico = new ChartPanel(grafico);
		pannelloGrafico.setVisible(true);
		pannelloGrafico.setBounds(31, 50, 756, 357);
	    pannelloGrafico.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    pannelloGrafico.setBackground(ChartColor.white);
	    getContentPane().add(pannelloGrafico);
	    pannelloGrafico.repaint();
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
