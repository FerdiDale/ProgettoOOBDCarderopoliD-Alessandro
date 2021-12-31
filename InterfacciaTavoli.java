import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfacciaTavoli extends JFrame {

	private JPanel contentPane;
	private Controller theController;
	/**
	 * Create the frame.
	 */
	public InterfacciaTavoli(Controller c, Sala salaCorrente) {
		theController = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Gestisci occupazioni del tavolo selezionato");
		btnNewButton.setBounds(337, 384, 332, 23);
		contentPane.add(btnNewButton);
		
		JButton bottoneModificaLayout = new JButton("Modifica layout");
		bottoneModificaLayout.setBounds(10, 418, 317, 23);
		contentPane.add(bottoneModificaLayout);
		
		JButton bottoneGestisciAdiacenze = new JButton("Gestisci tavoli adiacenti a quello selezionato");
		bottoneGestisciAdiacenze.setBounds(337, 418, 332, 23);
		contentPane.add(bottoneGestisciAdiacenze);
		
		JButton bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.bottoneIndietroGestioneTavoliPremuto(salaCorrente);
			}
		});
		bottoneIndietro.setBounds(10, 449, 89, 23);
		contentPane.add(bottoneIndietro);
		
		JButton bottoneAggiuntaTavolo = new JButton("Aggiungi tavolo");
		bottoneAggiuntaTavolo.setBounds(10, 384, 317, 23);
		contentPane.add(bottoneAggiuntaTavolo);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 679, 366);
		contentPane.add(panel);
		panel.setLayout(null);
		
		setVisible(true);
		setResizable(false);
	}
}
