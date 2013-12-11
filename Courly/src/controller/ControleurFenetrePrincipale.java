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
import model.Noeud;
import model.Plan;
import model.Tournee;
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

    public ControleurFenetrePrincipale(VueFenetrePrincipale aFenetre) {
        
        this.fenetre = aFenetre;
        this.controleurPlan = new ControleurPlan(this.fenetre.getScrollPanePlan(), this);
        this.controleurInspecteur = new ControleurInspecteur(this.fenetre.getScrollPaneInspecteur(), this);
        
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
                        String file = fChooser.getSelectedFile().getAbsolutePath();
                        ParseurXML p = new ParseurXML();

                        Tournee tournee = p.construireTourneeXML(file);
                        p.setTrajetsFromTournee(tournee, plan);
                        
                        Dijkstra dijkstra = new Dijkstra();
                        dijkstra.initTournee(plan, tournee);
                                                
                        this.controleurPlan.setTournee(tournee);
                        this.controleurPlan.paint();
                }
        }
        else {
                System.out.print("Aucun plan en mémoire !");
        }
    }
    

    public void didSelectNoeud(Noeud noeud) {
        this.controleurInspecteur.setVueFromNoeud(noeud);
    }
    
    public void didDeselectNoeud(Noeud noeud) {
        this.controleurInspecteur.setVueFromNoeud(null);
    }
}
