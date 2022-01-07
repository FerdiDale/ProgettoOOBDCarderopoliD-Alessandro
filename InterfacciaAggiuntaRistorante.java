import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
	private JButton bottoneConfermaAggiuntaRistorante;

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
	public InterfacciaAggiuntaRistorante(Controller c) {
		setTitle("Nuovo ristorante");
		theController = c;
		
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		inputNomeRistorante = new JTextField();
		inputNomeRistorante.setBounds(229, 55, 260, 20);
		contentPane.add(inputNomeRistorante);
		inputNomeRistorante.setColumns(10);
		inputNomeRistorante.getDocument().addDocumentListener(new GestoreTesti());

		labelNomeRistorante = new JLabel("Nome");
		labelNomeRistorante.setBounds(10, 55, 235, 20);
		contentPane.add(labelNomeRistorante);

		inputCittaRistorante = new JTextField();
		inputCittaRistorante.setBounds(229, 86, 260, 20);
		contentPane.add(inputCittaRistorante);
		inputCittaRistorante.setColumns(10);
		inputCittaRistorante.getDocument().addDocumentListener(new GestoreTesti());


		labelCittaRistorante = new JLabel("Citta'");
		labelCittaRistorante.setBounds(10, 86, 235, 20);
		contentPane.add(labelCittaRistorante);

		inputViaRistorante = new JTextField();
		inputViaRistorante.setText("");
		inputViaRistorante.setBounds(229, 117, 260, 20);
		contentPane.add(inputViaRistorante);
		inputViaRistorante.setColumns(10);
		inputViaRistorante.getDocument().addDocumentListener(new GestoreTesti());

		labelViaRistorante = new JLabel("Via");
		labelViaRistorante.setBounds(10, 117, 235, 20);
		contentPane.add(labelViaRistorante);

		inputN_CivicoRistorante = new JTextField();
		inputN_CivicoRistorante.setText("");
		inputN_CivicoRistorante.setBounds(229, 148, 260, 20);
		contentPane.add(inputN_CivicoRistorante);
		inputN_CivicoRistorante.setColumns(10);
		inputN_CivicoRistorante.getDocument().addDocumentListener(new GestoreTesti());

		labelN_CivicoRistorante = new JLabel("Numero Civico");
		labelN_CivicoRistorante.setBounds(10, 148, 235, 20);
		contentPane.add(labelN_CivicoRistorante);


		bottoneConfermaAggiuntaRistorante = new JButton("Ok");
		bottoneConfermaAggiuntaRistorante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (bottoneConfermaAggiuntaRistorante.isEnabled()) {

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
		bottoneConfermaAggiuntaRistorante.setEnabled(false);

		JButton bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.bottoneIndietroAggiungiRistorantePremuto();
			}
		});
		bottoneIndietro.setBounds(10, 179, 89, 23);
		contentPane.add(bottoneIndietro);

		nCaratteriNomeRistorante = new JLabel("0");
		nCaratteriNomeRistorante.setBounds(494, 58, 36, 14);
		contentPane.add(nCaratteriNomeRistorante);

		nCaratteriCitta = new JLabel("0");
		nCaratteriCitta.setBounds(494, 89, 36, 14);
		contentPane.add(nCaratteriCitta);

		nCaratteriVia = new JLabel("0");
		nCaratteriVia.setBounds(494, 120, 36, 14);
		contentPane.add(nCaratteriVia);

		setVisible(true);
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
				bottoneConfermaAggiuntaRistorante.setEnabled(false);
			}
			else
				bottoneConfermaAggiuntaRistorante.setEnabled(true);

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
				bottoneConfermaAggiuntaRistorante.setEnabled(false);
			}
			else
				bottoneConfermaAggiuntaRistorante.setEnabled(true);

		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
