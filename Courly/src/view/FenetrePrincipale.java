package view;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import model.Noeud;
import model.Plan;
import model.Tournee;

import controller.ControleurPlan;
import controller.ParseurXML;

import java.io.FileNotFoundException;

import javax.swing.JScrollPane;

import org.xml.sax.SAXException;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class FenetrePrincipale extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private ControleurPlan contPlan;
	private Plan plan;
	private JTextField textFieldID;
	private JTextField textFieldX;
	private JTextField textFieldY;
	private JSpinner zoomSpinner;
	private JTextField textFieldError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetrePrincipale frame = new FenetrePrincipale();
					frame.setVisible(true);
					
					SpinnerNumberModel model = new SpinnerNumberModel(100.0, 5.0, 200.0, 5.0);
	                frame.zoomSpinner.setModel(model);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetrePrincipale() {
		setTitle("Courly");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1184, 21);
		contentPane.add(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		
		JMenuItem mntmChargerPlan = new JMenuItem("Charger plan...");
		mntmChargerPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				plan = null;
				
				textFieldError.setText("");
				JFileChooser fChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier plan", "xml");
				fChooser.setFileFilter(filter);
				int returnVal = fChooser.showOpenDialog(FenetrePrincipale.this);
			    if( returnVal == JFileChooser.APPROVE_OPTION ) {
			    	String file = fChooser.getSelectedFile().getAbsolutePath();
			    	ParseurXML p = new ParseurXML();
					
					try {
						plan = p.construirePlanXML(file);
						if (plan !=null) {
							//contPlan.addAllNoeuds(plan.getNoeuds());               
	                		//contPlan.addAllTroncons(plan.getTroncons());
	                		contPlan.loadVuePlanFromModel(plan);
	                	} 
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						textFieldError.setForeground(Color.RED);
						textFieldError.setText("Fichier XML incorrect");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						textFieldError.setForeground(Color.RED);
						textFieldError.setText("Fichier inexistant");
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						textFieldError.setForeground(Color.RED);
						textFieldError.setText("Fichier XML incorrect");
					}
					
			    }
				
			}
		});
		mnFichier.add(mntmChargerPlan);
		
		JMenuItem mntmChargerLivraison = new JMenuItem("Charger livraison...");
		mntmChargerLivraison.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldError.setText("");

				if( plan!=null) {
					JFileChooser fChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier livraison", "xml");
					fChooser.setFileFilter(filter);
					int returnVal = fChooser.showOpenDialog(FenetrePrincipale.this);
					if( returnVal == JFileChooser.APPROVE_OPTION ) {
						String file = fChooser.getSelectedFile().getAbsolutePath();
						ParseurXML p = new ParseurXML();

						Tournee tournee = p.construireTourneeXML(file);
						for (int i=0;i< tournee.getLivraisons().size();i++) {
							Integer adresse = tournee.getLivraisons().get(i).getAdresse();
							tournee.getLivraisons().get(i).setNoeud(plan.getNoeuds().get(adresse));
						}
						contPlan.paint();
					}
				}
				else {
					textFieldError.setForeground(Color.RED);
					textFieldError.setText("Aucun plan en mémoire !");
				}
			}
		});
		mnFichier.add(mntmChargerLivraison);
		
		JMenu mnEditer = new JMenu("Editer");
		menuBar.add(mnEditer);
		
		JMenuItem mntmAnnuler = new JMenuItem("Annuler");
		mnEditer.add(mntmAnnuler);
		
		JMenuItem mntmRfaire = new JMenuItem("Restaurer");
		mnEditer.add(mntmRfaire);
		
		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);
		
		JMenuItem mntmAPropos = new JMenuItem("A propos...");
		mnAide.add(mntmAPropos);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 899, 614);
		contentPane.add(scrollPane);
		
		contPlan = new ControleurPlan(scrollPane, FenetrePrincipale.this);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(910, 21, 276, 98);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textFieldID = new JTextField();
		textFieldID.setBounds(21, 5, 114, 19);
		textFieldID.setText("ID");
		panel.add(textFieldID);
		textFieldID.setColumns(10);
		
		textFieldY = new JTextField();
		textFieldY.setBounds(21, 60, 114, 19);
		textFieldY.setText("Y");
		textFieldY.setColumns(10);
		panel.add(textFieldY);
		
		textFieldX = new JTextField();
		textFieldX.setBounds(21, 29, 114, 19);
		textFieldX.setText("X");
		textFieldX.setColumns(10);
		panel.add(textFieldX);
		
		zoomSpinner = new JSpinner();
		zoomSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				zoomSpinnerStateChangedHandler(e);
			}
		});
		zoomSpinner.setBounds(945, 147, 73, 21);
		contentPane.add(zoomSpinner);
		
		textFieldError = new JTextField();
		textFieldError.setBounds(12, 669, 385, 19);
		contentPane.add(textFieldError);
		textFieldError.setColumns(10);
	}
	
	private void zoomSpinnerStateChangedHandler(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zoomSpinnerStateChangedHandler
        // TODO add your handling code here:
        contPlan.setZoomScale((Double) this.zoomSpinner.getValue()/100);
    }
	
	
    public void didSelectNoeud(Noeud noeud) {
        this.textFieldX.setText(Integer.toString(noeud.getX()));
        this.textFieldID.setText(Integer.toString(noeud.getId()));
        this.textFieldY.setText(Integer.toString(noeud.getY())); 
    }
    
    public void didDeselectNoeud(Noeud noeud) {
    	this.textFieldX.setText("");
        this.textFieldID.setText("");
        this.textFieldY.setText(""); 
    }
}
