package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Depot;
import model.Livraison;
import model.Noeud;
import model.Plan;

import org.xml.sax.SAXException;

import controller.ControleurPlan;
import controller.ParseurXML;
import javax.swing.JTabbedPane;

public class FenetrePrincipale extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private ControleurPlan contPlan;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetrePrincipale frame = new FenetrePrincipale();
					frame.setVisible(true);
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
				JFileChooser fChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier plan", "xml");
				fChooser.setFileFilter(filter);
				int returnVal = fChooser.showOpenDialog(FenetrePrincipale.this);
			    if( returnVal == JFileChooser.APPROVE_OPTION ) {
			    	String file = fChooser.getSelectedFile().getAbsolutePath();
			    	ParseurXML p = new ParseurXML();
					Plan plan = null;
					
					try {
						plan = p.construirePlanXML(file);
						if (plan !=null) {
	                		
	                		Depot depot = new Depot();
	                		depot.setNoeud(plan.getNoeuds().get(0));
	                		
	                		Livraison livraison1 = new Livraison();
	                		livraison1.setNoeud(plan.getNoeuds().get(4));
	                		
	                		Livraison livraison2 = new Livraison();
	                		livraison2.setNoeud(plan.getNoeuds().get(8));
	                		
	                		Livraison livraison3 = new Livraison();
	                		livraison3.setNoeud(plan.getNoeuds().get(16));
	                		
	                		Livraison livraison4 = new Livraison();
	                		livraison4.setNoeud(plan.getNoeuds().get(32));
	                		
	                		Livraison livraison5 = new Livraison();
	                		livraison5.setNoeud(plan.getNoeuds().get(64));
	                		
	                		Livraison livraison6 = new Livraison();
	                		livraison6.setNoeud(plan.getNoeuds().get(50));
	                		
	                		
	                		contPlan.addAllNoeuds(plan.getNoeuds());               
	                		contPlan.addAllTroncons(plan.getTroncons());
	                	} 
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			    }
				
			}
		});
		mnFichier.add(mntmChargerPlan);
		
		JMenuItem mntmChargerLivraison = new JMenuItem("Charger livraison...");
		mnFichier.add(mntmChargerLivraison);
		
		JMenu mnEditer = new JMenu("Editer");
		menuBar.add(mnEditer);
		
		JMenuItem mntmAnnuler = new JMenuItem("Annuler");
		mnEditer.add(mntmAnnuler);
		
		JMenuItem mntmRfaire = new JMenuItem("R\u00E9faire");
		mnEditer.add(mntmRfaire);
		
		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);
		
		JMenuItem mntmAPropos = new JMenuItem("A propos...");
		mnAide.add(mntmAPropos);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 21, 909, 640);
		contentPane.add(scrollPane);
		
		contPlan = new ControleurPlan(scrollPane, FenetrePrincipale.this);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(1187, 21, -277, 640);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
	}
	
	
    public void didSelectNoeud(Noeud noeud) {
       /* this.jTextField1.setText(noeud.name);
        this.jTextField2.setText(Integer.toString(noeud.x));
        this.jTextField3.setText(Integer.toString(noeud.y)); */
    }
    
    public void didDeselectNoeud(Noeud noeud) {
       /* this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.jTextField3.setText(""); */
    }
}
