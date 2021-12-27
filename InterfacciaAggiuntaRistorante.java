import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfacciaAggiuntaRistorante extends JFrame {

	private JPanel contentPane;
	private JTextField inputNomeRistorante;
	private JTextField inputCittaRistorante;
	private JTextField inputViaRistorante;
	private JTextField inputN_CivicoRistorante;
	private Controller theController;

	/**
	 * Create the frame.
	 */
	public InterfacciaAggiuntaRistorante(Controller c) {
		setTitle("Nuovo ristorante");
		theController = c;
		
		ImageIcon icona = new ImageIcon("res/Icona progetto oobd.png");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel promptLabel = new JLabel("Inserisci i dati del nuovo ristorante");
		promptLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		promptLabel.setBounds(10, 11, 520, 26);
		contentPane.add(promptLabel);

		inputNomeRistorante = new JTextField();
		inputNomeRistorante.setBounds(270, 55, 260, 20);
		contentPane.add(inputNomeRistorante);
		inputNomeRistorante.setColumns(10);
		
		JLabel labelNomeRistorante = new JLabel("Nome");
		labelNomeRistorante.setBounds(10, 55, 235, 20);
		contentPane.add(labelNomeRistorante);
		
		inputCittaRistorante = new JTextField();
		inputCittaRistorante.setBounds(270, 86, 260, 20);
		contentPane.add(inputCittaRistorante);
		inputCittaRistorante.setColumns(10);
	
		JLabel labelCittaRistorante = new JLabel("Citta'");
		labelCittaRistorante.setBounds(10, 86, 235, 20);
		contentPane.add(labelCittaRistorante);
		
		inputViaRistorante = new JTextField();
		inputViaRistorante.setText("");
		inputViaRistorante.setBounds(270, 117, 260, 20);
		contentPane.add(inputViaRistorante);
		inputViaRistorante.setColumns(10);
	
		JLabel labelViaRistorante = new JLabel("Via");
		labelViaRistorante.setBounds(10, 117, 235, 20);
		contentPane.add(labelViaRistorante);
		
		inputN_CivicoRistorante = new JTextField();
		inputN_CivicoRistorante.setText("");
		inputN_CivicoRistorante.setBounds(270, 148, 260, 20);
		contentPane.add(inputN_CivicoRistorante);
		inputN_CivicoRistorante.setColumns(10);
		
		JLabel labelN_CivicoRistorante = new JLabel("Numero Civico");
		labelN_CivicoRistorante.setBounds(10, 148, 235, 20);
		contentPane.add(labelN_CivicoRistorante);
		
		JButton bottoneConfermaAggiuntaRistorante = new JButton("Ok");
		bottoneConfermaAggiuntaRistorante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(inputNomeRistorante.getText().isBlank()||
						inputCittaRistorante.getText().isBlank()||
						inputViaRistorante.getText().isBlank()||
						inputN_CivicoRistorante.getText().isBlank()) {
					
							JOptionPane.showMessageDialog(null, "I campi di input non possono essere vuoti!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
					
				}
				else {
					try
					{
						String nomeCurr = inputNomeRistorante.getText();
						String cittaCurr = inputCittaRistorante.getText();
						String viaCurr = inputViaRistorante.getText();
						Integer n_CivicoCurr = Integer.parseInt(inputN_CivicoRistorante.getText());
						if (nomeCurr.length()>40) {
							JOptionPane.showMessageDialog(null, "Il nome del ristorante puo' contenere al massimo 40 caratteri!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
						}
						else if (cittaCurr.length()>40) {
							JOptionPane.showMessageDialog(null, "Il nome della citta' puo' contenere al massimo 40 caratteri!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
						}
						else if (viaCurr.length()>40) {
							JOptionPane.showMessageDialog(null, "Il nome della via puo' contenere al massimo 40 caratteri!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
						}
						else if (n_CivicoCurr<1) {
							JOptionPane.showMessageDialog(null, "Il numero civico deve essere un numero valido!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
						}
						else theController.aggiuntaRistoranteOkPremuto(nomeCurr, viaCurr, n_CivicoCurr, cittaCurr);
					}
					catch (NumberFormatException ecc)
					{
						JOptionPane.showMessageDialog(null, "Il numero civico deve essere un numero!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		bottoneConfermaAggiuntaRistorante.setBounds(467, 179, 63, 23);
		contentPane.add(bottoneConfermaAggiuntaRistorante);
		
		JButton bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.bottoneIndietroAggiungiRistorantePremuto();
			}
		});
		bottoneIndietro.setBounds(10, 179, 89, 23);
		contentPane.add(bottoneIndietro);
		
		setVisible(true);
	}
}