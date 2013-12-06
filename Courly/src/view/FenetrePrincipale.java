package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import java.awt.Button;
import java.awt.ScrollPane;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import model.Noeud;
import model.Plan;

import controller.ControleurPlan;
import controller.ParseurXML;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

import javax.swing.JScrollPane;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 344);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 488, 21);
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
						if ( plan !=null && contPlan != null ) {
	                		contPlan.addAllNoeuds(plan.getNoeuds());               
	                		contPlan.addAllTroncons(plan.getTroncons());
	                	}   
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
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
		scrollPane.setBounds(0, 21, 488, 284);
		contentPane.add(scrollPane);
		
		contPlan = new ControleurPlan(scrollPane, FenetrePrincipale.this);
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
