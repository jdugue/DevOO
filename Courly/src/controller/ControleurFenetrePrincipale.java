/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.awt.Color;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Livraison;
import model.Noeud;
import model.Plan;
import model.Tournee;
import model.Troncon;
import org.xml.sax.SAXException;
import view.VueFenetrePrincipale;

/**
 *
 * @author tanguyhelesbeux
 */
public class ControleurFenetrePrincipale {
    
    
    private VueFenetrePrincipale fenetre;
    private ControleurPlan controleurPlan;
    private ControleurInspecteur controleurInspecteur;
    
    private Plan plan;
    
    private double zoomScale = 1.0;
    private static final double zoomDelta = 0.05;
    private static final double zoomMin = 0.1;
    private static final double zoomMax = 2.0;
    
    private Tournee parsedTournee;
    private ArrayList<Tournee> tournees = new ArrayList<Tournee>();


    public ControleurFenetrePrincipale(VueFenetrePrincipale aFenetre) {
        
        this.fenetre = aFenetre;
        this.controleurPlan = new ControleurPlan(this.fenetre.getScrollPanePlan(), this);
        this.controleurInspecteur = new ControleurInspecteur(this.fenetre.getScrollPaneInspecteur(), this);
        
        this.testVues();
    }
    
    private void testVues() {
        System.out.println("Do not call testVues(), ControleurFenetrePrincipale line 50");
        
        Noeud noeud1 = new Noeud();
        noeud1.setX(410);
        noeud1.setY(200);
        
        Noeud noeud2 = new Noeud();
        noeud2.setX(400);
        noeud2.setY(410);
        
        Troncon troncon1 = new Troncon();
        troncon1.setOrigine(noeud1);
        troncon1.setDestination(noeud2);
        
        ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
        noeuds.add(noeud1);
        noeuds.add(noeud2);
        this.controleurPlan.addAllNoeuds(noeuds);
        this.controleurPlan.addTroncon(troncon1);
    }

    public void setZoomScale(double zoomScale) {
        
        if (zoomScale > zoomMax) {
            zoomScale = zoomMax;
        } else if (zoomScale < zoomMin) {
            zoomScale = zoomMin;
        }
        this.zoomScale = zoomScale;
        this.controleurPlan.setZoomScale(zoomScale);
    }
    
    
    
    public void shouldZoomOut() {
        this.setZoomScale(zoomScale - zoomDelta);
    }
    
    public void shouldZoomIn() {
        this.setZoomScale(zoomScale + zoomDelta);
    }
    
    public void shouldLoadPlan() {
        JFileChooser fChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier plan", "xml");
        fChooser.setFileFilter(filter);
        int returnVal = fChooser.showOpenDialog(this.fenetre);
        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            String file = fChooser.getSelectedFile().getAbsolutePath();
            ParseurXML p = new ParseurXML();

            try {
                    Plan tempPlan = p.construirePlanXML(file);
                    if (tempPlan !=null) {
                        this.plan = tempPlan;
                        this.controleurPlan.loadVuePlanFromModel(plan);
                        this.controleurInspecteur.setVueFromNoeud(null);
                        this.fenetre.setMessage("Plan chargé avec succés", VueFenetrePrincipale.MessageType.MessageTypeSuccess);
                    } else {
                        this.fenetre.setMessage("Impossible de charger le plan", VueFenetrePrincipale.MessageType.MessageTypeError);
                    }
            } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    //textFieldError.setForeground(Color.RED);
                    //textFieldError.setText("Fichier XML incorrect");
                        this.fenetre.setMessage("Fichier XML incorrect", VueFenetrePrincipale.MessageType.MessageTypeError);
                System.out.print("Fichier XML incorrect");
            } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    //textFieldError.setForeground(Color.RED);
                    //textFieldError.setText("Fichier inexistant");
                        this.fenetre.setMessage("Fichier inexistant", VueFenetrePrincipale.MessageType.MessageTypeError);
                System.out.print("Fichier inexistant");
            } catch (SAXException e) {
                    // TODO Auto-generated catch block
                    //textFieldError.setForeground(Color.RED);
                    //textFieldError.setText("Fichier XML incorrect");
                        this.fenetre.setMessage("Fichier XML incorrect", VueFenetrePrincipale.MessageType.MessageTypeError);
                System.out.print("Fichier XML incorrect");
            }

        }
        
    }
    
    public void shouldLoadLivraison() { 

        if( this.plan!=null) {
                JFileChooser fChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier livraison", "xml");
                fChooser.setFileFilter(filter);
                int returnVal = fChooser.showOpenDialog(this.fenetre);
                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                    this.fenetre.setMessage("Calcul de la tournée...", VueFenetrePrincipale.MessageType.MessageTypeLog);
                        String file = fChooser.getSelectedFile().getAbsolutePath();
                        ParseurXML p = new ParseurXML();

                        Tournee tournee = p.construireTourneeXML(file);
                        if (tournee != null) {
                            this.tournees.add(tournee);
                        }
                        p.setTrajetsFromTournee(tournee, plan);
                        
                        traitementDijkstra(tournee);
                        
                        this.selectTournee(tournee);
                        this.fenetre.setMessage("Livraisons chargées avec succès", VueFenetrePrincipale.MessageType.MessageTypeSuccess);
                }
        }
        else {
                this.fenetre.setMessage("Vous devez d\'abord charger un plan", VueFenetrePrincipale.MessageType.MessageTypeLog);
        }
    }
    
    private void selectTournee(Tournee tournee) { 
        this.controleurInspecteur.setPlagesHoraires(tournee.getPlagesHoraire());
        this.controleurPlan.setTournee(tournee);
        this.controleurPlan.paint();
    }
    
    private void traitementDijkstra (Tournee tournee)
    {
    	 Dijkstra dijkstra = new Dijkstra();
         dijkstra.initTournee(plan, tournee);
                                 
         this.controleurPlan.setTournee(tournee);
         this.controleurPlan.paint();
    }

    
        public void shouldAddLivraisonAndReload(Livraison livraison)
    {
    	parsedTournee.addLivraison(livraison);
    	Tournee tournee = new Tournee(parsedTournee);
    	
    	tournee = parsedTournee;
    	
    	traitementDijkstra(tournee);
    }
    
    public void shouldRemoveLivraisonAndReload(Livraison livraison)
    {    	
    	parsedTournee.removeLivraison(livraison);
    	Tournee tournee = new Tournee(parsedTournee);
    	
    	traitementDijkstra(tournee);
    }
    
    public void shouldExportTournee() {
        
    }

    
    public void didSelectNoeud(Noeud noeud) {
        this.controleurInspecteur.setVueFromNoeud(noeud);
    }
    
    public void didDeselectNoeud(Noeud noeud) {
        this.controleurInspecteur.setVueFromNoeud(null);
    }
}
