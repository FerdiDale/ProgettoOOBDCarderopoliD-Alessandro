import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class InterfacciaModificaDatiTavolo extends JFrame {

	private JPanel contentPane;
	Controller theController;
	Tavolo tavoloScelto;
	private JTextField numeroTavolo;
	private JButton bottoneOk;
	private JButton bottoneIndietro;
	int numeroCorrente;

	/**
	 * Create the frame.
	 */
	public InterfacciaModificaDatiTavolo(Controller c, Tavolo tavoloCorrente) {
		
		theController = c;
		tavoloScelto = tavoloCorrente;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		numeroTavolo = new JTextField();
		numeroTavolo.setBounds(10, 30, 86, 20);
		getContentPane().add(numeroTavolo);
		numeroTavolo.setText(((Integer)tavoloScelto.getNumero()).toString());
		
		bottoneOk = new JButton("Ok");
		bottoneOk.setBounds(10, 106, 89, 23);
		getContentPane().add(bottoneOk);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(200, 106, 89, 23);
		getContentPane().add(bottoneIndietro);
		
		bottoneOk.addActionListener(new GestoreBottoni());
		bottoneIndietro.addActionListener(new GestoreBottoni());
		
		JLabel etichettaNumeroTavolo = new JLabel("Numero del tavolo");
		etichettaNumeroTavolo.setBounds(10, 11, 163, 14);
		getContentPane().add(etichettaNumeroTavolo);
		
		setVisible(true);
		setResizable(false);
	}
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource()==bottoneOk)
			{
				boolean valoriValidi = false;
				
				if(numeroTavolo.getText().isBlank()== false)
				{
					try 
					{
						numeroCorrente = Integer.parseInt(numeroTavolo.getText());
						
						if (numeroCorrente>0)
							valoriValidi = true;
						else
							valoriValidi = false;
					}
					catch(NumberFormatException c)
					{
						valoriValidi = false;
					}
				}
			
				if(valoriValidi) theController.bottoneConfermaModificheDatiTavoloPremuto(tavoloScelto, numeroCorrente);
				else JOptionPane.showMessageDialog(null,"Non sono stati inseriti dei numeri validi. Riprovare.","Errore!", JOptionPane.ERROR_MESSAGE);
			}
			
			else if (e.getSource() == bottoneIndietro) {
				theController.bottoneIndietroModificaDatiTavoloPremuto(tavoloScelto.getSala_App());
			}
		}
	}

}
