import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfacciaModificaDatiRistorante extends JFrame {
	private JPanel contentPane;
	private Controller theController;
	private JTextField inputNomeRistorante;
	private JTextField inputCittaRistorante;
	private JTextField inputViaRistorante;
	private JTextField inputN_CivicoRistorante;

	private JButton bottoneConfermaModificaRistorante;

	private JLabel labelN_CivicoRistorante;
	private JLabel labelViaRistorante;
	private JLabel labelCittaRistorante;
	private JLabel labelNomeRistorante;

	private JLabel nCaratteriNomeRistorante;
	private JLabel nCaratteriCitta;	
	private JLabel nCaratteriVia;

	/**
	 * Create the frame.
	 */
	public InterfacciaModificaDatiRistorante(Controller c, Ristorante ristoranteCorrente) {
		theController = c;
		
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel("Modifica i dati desiderati");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 11, 520, 26);
		contentPane.add(lblNewLabel);

		inputNomeRistorante = new JTextField();
		inputNomeRistorante.setBounds(206, 55, 260, 20);
		contentPane.add(inputNomeRistorante);
		inputNomeRistorante.setColumns(10);
		inputNomeRistorante.setText(ristoranteCorrente.getNome());
		
		JLabel labelNomeRistorante = new JLabel("Nome");
		labelNomeRistorante.setBounds(10, 55, 235, 20);
		contentPane.add(labelNomeRistorante);

		inputCittaRistorante = new JTextField();
		inputCittaRistorante.setBounds(206, 86, 260, 20);
		contentPane.add(inputCittaRistorante);
		inputCittaRistorante.setColumns(10);
		inputCittaRistorante.setText(ristoranteCorrente.getCitta());
	
		JLabel labelCittaRistorante = new JLabel("Citta'");
		labelCittaRistorante.setBounds(10, 86, 235, 20);
		contentPane.add(labelCittaRistorante);

		inputViaRistorante = new JTextField();
		inputViaRistorante.setText("");
		inputViaRistorante.setBounds(206, 117, 260, 20);
		contentPane.add(inputViaRistorante);
		inputViaRistorante.setColumns(10);
		inputViaRistorante.setText(ristoranteCorrente.getVia());
	
		JLabel labelViaRistorante = new JLabel("Via");
		labelViaRistorante.setBounds(10, 117, 235, 20);
		contentPane.add(labelViaRistorante);

		inputN_CivicoRistorante = new JTextField();
		inputN_CivicoRistorante.setText("");
		inputN_CivicoRistorante.setBounds(206, 148, 260, 20);
		contentPane.add(inputN_CivicoRistorante);
		inputN_CivicoRistorante.setColumns(10);
		inputN_CivicoRistorante.setText(ristoranteCorrente.getN_Civico().toString());
		
		JLabel labelN_CivicoRistorante = new JLabel("Numero Civico");
		labelN_CivicoRistorante.setBounds(10, 148, 235, 20);
		contentPane.add(labelN_CivicoRistorante);

		bottoneConfermaModificaRistorante = new JButton("Ok");
		bottoneConfermaModificaRistorante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bottoneConfermaModificaRistorante.isEnabled()) {

					try
					{
						Integer idCurr = ristoranteCorrente.getId_Ristorante();
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
						else theController.modificaRistoranteOkPremuto(ristoranteCorrente, nomeCurr, viaCurr, n_CivicoCurr, cittaCurr);
					}
					catch (NumberFormatException ecc)
					{
						JOptionPane.showMessageDialog(null, "Il numero civico deve essere un numero!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
					}

				}
			}
		});
		bottoneConfermaModificaRistorante.setBounds(467, 179, 63, 23);
		contentPane.add(bottoneConfermaModificaRistorante);

		JButton bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.bottoneIndietroModificaRistorantePremuto();
			}
		});
		bottoneIndietro.setBounds(10, 179, 89, 23);
		contentPane.add(bottoneIndietro);

		nCaratteriNomeRistorante = new JLabel(String.format("%d", inputNomeRistorante.getText().length()));
		nCaratteriNomeRistorante.setBounds(494, 58, 36, 14);
		contentPane.add(nCaratteriNomeRistorante);

		nCaratteriCitta = new JLabel(String.format("%d", inputCittaRistorante.getText().length()));
		nCaratteriCitta.setBounds(494, 89, 36, 14);
		contentPane.add(nCaratteriCitta);

		nCaratteriVia = new JLabel(String.format("%d", inputViaRistorante.getText().length()));
		nCaratteriVia.setBounds(494, 120, 36, 14);
		contentPane.add(nCaratteriVia);

		inputNomeRistorante.getDocument().addDocumentListener(new GestoreTesti());
		inputCittaRistorante.getDocument().addDocumentListener(new GestoreTesti());
		inputViaRistorante.getDocument().addDocumentListener(new GestoreTesti());
		inputN_CivicoRistorante.getDocument().addDocumentListener(new GestoreTesti());

		setVisible(true);
		setResizable(false);
	}

	private class GestoreTesti implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {

			if (e.getDocument() == inputNomeRistorante.getDocument())
			{
				nCaratteriNomeRistorante.setText(String.format("%d", inputNomeRistorante.getText().length()));
			}
			else if(e.getDocument() == inputCittaRistorante.getDocument())
			{
				nCaratteriCitta.setText(String.format("%d", inputCittaRistorante.getText().length()));
			}
			else if(e.getDocument() == inputViaRistorante.getDocument())
			{
				nCaratteriVia.setText(String.format("%d", inputViaRistorante.getText().length()));
			}
			if(inputNomeRistorante.getText().isBlank() || inputCittaRistorante.getText().isBlank() || 
					inputViaRistorante.getText().isBlank() || inputN_CivicoRistorante.getText().isBlank())
			{
				bottoneConfermaModificaRistorante.setEnabled(false);
			}
			else
				bottoneConfermaModificaRistorante.setEnabled(true);

		}

		@Override
		public void removeUpdate(DocumentEvent e) {

			if (e.getDocument() == inputNomeRistorante.getDocument())
			{
				nCaratteriNomeRistorante.setText(String.format("%d", inputNomeRistorante.getText().length()));
			}
			else if(e.getDocument() == inputCittaRistorante.getDocument())
			{
				nCaratteriCitta.setText(String.format("%d", inputCittaRistorante.getText().length()));
			}
			else if(e.getDocument() == inputViaRistorante.getDocument())
			{
				nCaratteriVia.setText(String.format("%d", inputViaRistorante.getText().length()));
			}
			if(inputNomeRistorante.getText().isBlank() || inputCittaRistorante.getText().isBlank() || 
					inputViaRistorante.getText().isBlank() || inputN_CivicoRistorante.getText().isBlank())
			{
				bottoneConfermaModificaRistorante.setEnabled(false);
			}
			else
				bottoneConfermaModificaRistorante.setEnabled(true);

		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
