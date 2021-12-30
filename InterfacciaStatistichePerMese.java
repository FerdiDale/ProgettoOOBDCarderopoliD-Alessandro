import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class InterfacciaStatistichePerMese extends JFrame {

	private Integer annoCorrente;
	private Mese meseCorrente = new Mese();
	private Controller theController;
	private JTextField textFieldMeseCorrente;
	private JTextField textFieldAnnoCorrente;
	
	/**
	 * Create the frame.
	 */
	public InterfacciaStatistichePerMese(Controller c, Integer annoScelto, Integer meseScelto, Ristorante ristoranteScelto) {
		
		theController = c;
		annoCorrente = annoScelto;
		meseCorrente.setValoreNumerico(meseScelto);
		
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 933, 551);
		
		modellaStatistiche (ristoranteScelto);

		JPanel panel =  new JPanel();
		panel.setBounds(0, 0, 824, 461);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton bottoneAumentaMese = new JButton(">");
		bottoneAumentaMese.setBounds(534, 407, 48, 43);
		panel.add(bottoneAumentaMese);
		
		JButton bottoneDiminuisciMese = new JButton("<");
		bottoneDiminuisciMese.setBounds(217, 407, 48, 43);
		panel.add(bottoneDiminuisciMese);
		
		JLabel immagineStatistiche = new JLabel("");
		immagineStatistiche.setBounds(46, 62, 720, 334);
		panel.add(immagineStatistiche);
		
		JButton bottonePassaggioAdAnno = new JButton("Torna alla visualizzazione delle statistiche per anno");
		bottonePassaggioAdAnno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				theController.passaggioAdAnnoPremuto(annoCorrente, ristoranteScelto);
			}
		});
		bottonePassaggioAdAnno.setBounds(441, 11, 373, 32);
		panel.add(bottonePassaggioAdAnno);
		
		textFieldMeseCorrente = new JTextField();
		textFieldMeseCorrente.setBounds(264, 407, 271, 43);
		panel.add(textFieldMeseCorrente);
		textFieldMeseCorrente.setText(meseCorrente.toString());
		textFieldMeseCorrente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(textFieldMeseCorrente.getText().isBlank()) {
								JOptionPane.showMessageDialog(null, "Inserire un mese valido, in forma litterale o numerica!",
										"Attenzione!", JOptionPane.WARNING_MESSAGE);
								textFieldMeseCorrente.setText(meseCorrente.toString());
					}
					else {
						try
						{
							Integer possibileMese = Integer.parseInt(textFieldMeseCorrente.getText());
							if(possibileMese>=1 && possibileMese<=12) {
								meseCorrente.setValoreNumerico(possibileMese);
								textFieldMeseCorrente.setText(meseCorrente.toString());
								modellaStatistiche(ristoranteScelto);
							}
							else {
								JOptionPane.showMessageDialog(null, "Inserire un mese valido, in forma litterale o numerica!",
										"Attenzione!", JOptionPane.WARNING_MESSAGE);
								textFieldMeseCorrente.setText(meseCorrente.toString());
							}
						}
						catch (NumberFormatException ecc)
						{
							try {
								meseCorrente.setDaStringa(textFieldMeseCorrente.getText());
								textFieldMeseCorrente.setText(meseCorrente.toString());
								modellaStatistiche(ristoranteScelto);
							} catch (MeseErratoException e1) {
								e1.stampaMessaggio();
								textFieldMeseCorrente.setText(meseCorrente.toString());
							}
						}
					}
				}
			}
		});
		
		JButton bottoneDiminuisciAnno = new JButton("<");
		bottoneDiminuisciAnno.setBounds(217, 458, 48, 43);
		panel.add(bottoneDiminuisciAnno);
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
		
		textFieldAnnoCorrente = new JTextField();
		textFieldAnnoCorrente.setText((String) null);
		textFieldAnnoCorrente.setBounds(264, 458, 271, 43);
		textFieldAnnoCorrente.setText(annoCorrente.toString());
		panel.add(textFieldAnnoCorrente);
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
		
		JButton bottoneAumentaAnno = new JButton(">");
		bottoneAumentaAnno.setBounds(534, 458, 48, 43);
		panel.add(bottoneAumentaAnno);
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
		
		bottoneAumentaMese.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				meseCorrente.setValoreNumerico(meseCorrente.getValoreNumerico()+1);
				if (meseCorrente.getValoreNumerico().equals(13)) {
					Integer possibileAnno = annoCorrente+1;
					if (possibileAnno>=0) {
						meseCorrente.setValoreNumerico(1);
						annoCorrente = possibileAnno;
						modellaStatistiche(ristoranteScelto);
					}
					else {
						meseCorrente.setValoreNumerico(12);
						JOptionPane.showMessageDialog(null, "Hai raggiunto il numero intero massimo!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
					}
				}
				textFieldMeseCorrente.setText(meseCorrente.toString());
				textFieldAnnoCorrente.setText(annoCorrente.toString());
				modellaStatistiche(ristoranteScelto);
			}
		});
		
		bottoneDiminuisciMese.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				meseCorrente.setValoreNumerico(meseCorrente.getValoreNumerico()-1);
				if (meseCorrente.getValoreNumerico().equals(0)) {
					Integer possibileAnno = annoCorrente-1;
					if (possibileAnno>=0) {
						annoCorrente = possibileAnno;
						meseCorrente.setValoreNumerico(12);
						modellaStatistiche(ristoranteScelto);
					}
					else {
						JOptionPane.showMessageDialog(null, "Non penso ci sia interesse per statistiche precedenti all'anno 0!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
						meseCorrente.setValoreNumerico(1);
					}
				}
				textFieldMeseCorrente.setText(meseCorrente.toString());
				textFieldAnnoCorrente.setText(annoCorrente.toString());
				modellaStatistiche(ristoranteScelto);
			}
		});
		
		
		setResizable(false);
		setVisible(true);
	}

	public void modellaStatistiche(Ristorante ristoranteScelto) {
		
		DefaultCategoryDataset statisticheCorrenti = new DefaultCategoryDataset();
		statisticheCorrenti = theController.ricavaStatistiche(annoCorrente, meseCorrente.getValoreNumerico(), ristoranteScelto);
		JFreeChart grafico = creaGrafico(statisticheCorrenti);
		ChartPanel pannelloGrafico = new ChartPanel(grafico);
		pannelloGrafico.setVisible(true);
		pannelloGrafico.setBounds(31, 50, 756, 357);
	    pannelloGrafico.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    pannelloGrafico.setBackground(ChartColor.white);
	    getContentPane().add(pannelloGrafico);
	    pannelloGrafico.setLayout(null);
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
