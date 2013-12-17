/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import javax.xml.parsers.ParserConfigurationException;
import model.Lieu;

import model.Livraison;
import model.Noeud;
import model.Plan;
import model.Tournee;
import model.Troncon;
import org.xml.sax.SAXException;
import view.VueFenetrePrincipale;
import view.VueLieu;

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
    
    private Tournee selectedTournee;
    private ArrayList<Tournee> tournees = new ArrayList<Tournee>();
    private UndoManager undoManager = new UndoManager();
    
    private String lastUsedFolder = null;
    
    private final String TOURNEE_WRITTEN = "Fichier de tournee généré.";
    private final String TOURNEE_NOT_WRITTEN = "Le fichier de tournée n'a pas pu être " +
    		"généré suite à un problème.";
    
    
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
    	JFileChooser fChooser = FileChooserFactory.createFileChooser("xml", "Fichier livraison", 
				getCurrentDirectory());
        
        int returnVal = fChooser.showOpenDialog(this.fenetre);
        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            String file = fChooser.getSelectedFile().getAbsolutePath();
            lastUsedFolder = fChooser.getSelectedFile().getParent();
            ParseurXML p = new ParseurXML();

            try {
                    Plan tempPlan = p.construirePlanXML(file);
                    if (tempPlan !=null) {
                        this.plan = tempPlan;
                        this.controleurPlan.loadVuePlanFromModel(plan);
                        this.controleurInspecteur.setVueFromNoeud(null);
                        this.fenetre.setMessage("Plan chargé avec succès", VueFenetrePrincipale.MessageType.MessageTypeSuccess);
                    } else {
                        this.fenetre.setMessage("Impossible de charger le plan", VueFenetrePrincipale.MessageType.MessageTypeError);
                    }
            } catch (NumberFormatException e) {
                    this.fenetre.setMessage("Fichier XML incorrect", VueFenetrePrincipale.MessageType.MessageTypeError);
            } catch (FileNotFoundException e) {
            	this.fenetre.setMessage("Fichier inexistant", VueFenetrePrincipale.MessageType.MessageTypeError);
            } catch (SAXException e) {
            	this.fenetre.setMessage("Fichier XML incorrect", VueFenetrePrincipale.MessageType.MessageTypeError);
            }

        }
        
    }
    
    public void shouldLoadLivraison() { 
        if( this.plan!=null) {
        	try {
                JFileChooser fChooser = new JFileChooser();
                fChooser.setCurrentDirectory( lastUsedFolder == null ? new java.io.File(".") : new File(lastUsedFolder));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier livraison", "xml");
                fChooser.setFileFilter(filter);
                int returnVal = fChooser.showOpenDialog(this.fenetre);
                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                    this.fenetre.setMessage("Calcul de la tournée...", VueFenetrePrincipale.MessageType.MessageTypeLog);
                        String file = fChooser.getSelectedFile().getAbsolutePath();
                        lastUsedFolder = fChooser.getSelectedFile().getParent();
                        ParseurXML p = new ParseurXML();

                        Tournee tournee = p.construireTourneeXML(file);
                        if (tournee != null) {
                            this.tournees.add(tournee);
                        }
                        p.setNoeudsFromTournee(tournee, plan);
                        
                        traitementDijkstra(tournee);
                        System.out.println(tournee.getPlagesHoraire().size());
                        this.selectTournee(tournee);
                        this.fenetre.setMessage("Livraisons chargées avec succès", VueFenetrePrincipale.MessageType.MessageTypeSuccess);
                }
        	}
        	catch (FileNotFoundException e) {
        		this.fenetre.setMessage("Fichier inexistant", VueFenetrePrincipale.MessageType.MessageTypeError);
        	} catch (SAXException e) {
        		this.fenetre.setMessage("Fichier XML incorrect", VueFenetrePrincipale.MessageType.MessageTypeError);
        	} catch (ParseException e) {
        		this.fenetre.setMessage("Fichier XML incorrect", VueFenetrePrincipale.MessageType.MessageTypeError);
			} catch (ParserConfigurationException e) {
        		this.fenetre.setMessage("Fichier XML incorrect", VueFenetrePrincipale.MessageType.MessageTypeError);
			} catch (IOException e) {
        		this.fenetre.setMessage("Probleme de lecture du fichier", VueFenetrePrincipale.MessageType.MessageTypeError);
			}
        }
        else {
                this.fenetre.setMessage("Vous devez d\'abord charger un plan", VueFenetrePrincipale.MessageType.MessageTypeLog);
        }
    }
    
    private void selectTournee(Tournee tournee) { 
        this.controleurInspecteur.setPlagesHoraires(tournee.getPlagesHoraire());
        this.controleurPlan.setTournee(tournee);
        this.selectedTournee=tournee;
        this.controleurPlan.paint();
    }
    
    private void traitementDijkstra (Tournee tournee)
    {
    	 Dijkstra dijkstra = new Dijkstra();
         dijkstra.initTournee(plan, tournee);
                                 
         this.controleurPlan.setTournee(tournee);
         this.controleurPlan.paint();
    }

    
    public void undo(){
    	System.out.println("Can undo: " + undoManager.canUndo());
    	undoManager.undo();
    }
    public void redo(){

    	System.out.println("Can undo: " + undoManager.canUndo());
    	undoManager.redo();
    }
    public void shouldAddLivraisonAndReload(Livraison livraison)
    {
    	AjouterLivraisonEdit addEdit = new AjouterLivraisonEdit(livraison, this);
    	addEdit.execute();
    	undoManager.addEdit(addEdit);
    }
    
    public void addLivraisonAndReload(Livraison livraison){
    	selectedTournee.addLivraison(livraison);
    	Tournee tournee = new Tournee(selectedTournee);

    	selectedTournee = tournee;
    	
    	traitementDijkstra(tournee);
    }
    
    
    public void shouldRemoveLivraisonAndReload(Livraison livraison)
    {    	
    	EnleverLivraisonEdit removeEdit = new EnleverLivraisonEdit(livraison, this);
    	removeEdit.execute();
    	undoManager.addEdit(removeEdit);
    }
    
    public void removeLivraisonAndReload(Livraison livraison)
    {    	
    	selectedTournee.removeLivraison(livraison);
    	Tournee tournee = new Tournee(selectedTournee);
    	selectedTournee = tournee;
    	traitementDijkstra(tournee);
    }
    
    public void shouldExportTournee() {
        ControleurTournee controleurTournee = new ControleurTournee();
        JFileChooser fChooser = FileChooserFactory.createFileChooser( controleurTournee.getTourneeFileExt()
        		, controleurTournee.getFiletypeName(), getCurrentDirectory() );
       
        int returnVal = fChooser.showOpenDialog(this.fenetre);
		if( returnVal == JFileChooser.APPROVE_OPTION ) {
			String file = fChooser.getSelectedFile().getAbsolutePath();
			lastUsedFolder = fChooser.getSelectedFile().getParent();
	        boolean worked = controleurTournee.tourneeToTxt(this.selectedTournee, file);
	        
	        if ( worked ){
	        	this.fenetre.setMessage(TOURNEE_WRITTEN, VueFenetrePrincipale.MessageType.MessageTypeSuccess);
	        } else {
	        	this.fenetre.setMessage(TOURNEE_NOT_WRITTEN, VueFenetrePrincipale.MessageType.MessageTypeError);
	        }        
		}
		
		
    }

    
    public void didSelectNoeud(Noeud noeud) {
        this.controleurInspecteur.setVueFromNoeud(noeud);
    }
    
    public void didDeselectNoeud(Noeud noeud) {
        this.controleurInspecteur.setVueFromNoeud(null);
    }
    
    public void didSelectLieu(Lieu lieu) {
        this.controleurInspecteur.setVueFromLieu(lieu);
    }
    
    public void didDeselectLieu(Lieu lieu) {
        this.controleurInspecteur.setVueFromLieu(null);
    }
    
    private File getCurrentDirectory(){
		return lastUsedFolder == null ? new java.io.File(".") : new File(lastUsedFolder);
		
    }
}
