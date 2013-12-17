/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.ParseException;
import java.util.ArrayList;
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
    private ControleurUndoManager controleurUndoManager = new ControleurUndoManager();
    
    private String lastUsedFolder = null;
    
    private static final String TOURNEE_WRITTEN = "Fichier de tournee généré.";
    private static final String TOURNEE_NOT_WRITTEN = "Le fichier de tournée n'a pas pu être " +
    		"généré suite à un problème.";
    private static final String FILENAME_NOT_PERMITTED = "Le nom du fichier contient des caractères illegaux.";
    private static final String FILETYPE_NAME = "Fichier texte";
	private static final String FILETYPE = "xml";
	private static final String PLAN_CHARGE_SUCCESS = "Plan chargé avec succès";
	private static final String PLAN_NOT_CHARGED = "Impossible de charger le plan";
	private static final String INCORRECT_XML_FILE = "Fichier XML incorrect";
	private static final String FILE_NOT_FOUND = "Fichier inexistant";
	private static final String CALCULATING_TOURNEE = "Calcul de la tournée...";
	private static final String LIVRAISON_FILE_CHARGED = "Livraisons chargées avec succès";
	private static final String COUDNT_READ_FILE = "Probleme de lecture du fichier";
	private static final String ERR_PLAN_LIV = "Fichier livraison non conforme au plan chargé";
	private static final String NO_PLAN = "Vous devez d\'abord charger un plan";
    
    public ControleurFenetrePrincipale(VueFenetrePrincipale aFenetre) {
        
        this.fenetre = aFenetre;
        this.controleurPlan = new ControleurPlan(this.fenetre.getScrollPanePlan(), this);
        this.controleurInspecteur = new ControleurInspecteur(this.fenetre.getScrollPaneInspecteur(), this);

    	setUndoRedoButtons();
        //this.testVues();
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
    	JFileChooser fChooser = FileChooserFactory.createFileChooser(FILETYPE, FILETYPE_NAME, 
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
                        this.fenetre.canExportTournee(false);
                        this.fenetre.canLoadLivraison(true);
                        this.fenetre.removeAllTournee();
                        this.showMessage(PLAN_CHARGE_SUCCESS, VueFenetrePrincipale.MessageType.MessageTypeSuccess);
                    } else {
                        this.showMessage(PLAN_NOT_CHARGED, VueFenetrePrincipale.MessageType.MessageTypeError);
                    }
            } catch (NumberFormatException e) {
                    this.showMessage(INCORRECT_XML_FILE, VueFenetrePrincipale.MessageType.MessageTypeError);
            } catch (FileNotFoundException e) {
            	this.showMessage(FILE_NOT_FOUND, VueFenetrePrincipale.MessageType.MessageTypeError);
            } catch (SAXException e) {
            	this.showMessage(INCORRECT_XML_FILE, VueFenetrePrincipale.MessageType.MessageTypeError);
            }

        }
        
    }
    
    public void shouldLoadLivraison() { 
        if( this.plan != null) {
        	try {
                JFileChooser fChooser = FileChooserFactory.createFileChooser( FILETYPE
                		, FILETYPE_NAME , getCurrentDirectory() );
               
                int returnVal = fChooser.showOpenDialog(this.fenetre);
                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                        String file = fChooser.getSelectedFile().getAbsolutePath();
                        lastUsedFolder = fChooser.getSelectedFile().getParent();
                        ParseurXML p = new ParseurXML();

                        this.showMessage(CALCULATING_TOURNEE, VueFenetrePrincipale.MessageType.MessageTypeLog);
                        Tournee tournee = p.construireTourneeXML(file);
                        if (tournee != null) {
                            p.setNoeudsFromTournee(tournee, plan);
                            traitementDijkstra(tournee);
                            this.fenetre.addTournee(tournee, file, true);
                            this.fenetre.canExportTournee(true);
                            this.showMessage(LIVRAISON_FILE_CHARGED, VueFenetrePrincipale.MessageType.MessageTypeSuccess);
                        }
                }
        	}
        	catch (FileNotFoundException e) {
        		this.showMessage(FILE_NOT_FOUND, VueFenetrePrincipale.MessageType.MessageTypeError);
        	} catch (SAXException e) {
        		this.showMessage(INCORRECT_XML_FILE, VueFenetrePrincipale.MessageType.MessageTypeError);
        	} catch (ParseException e) {
        		this.showMessage(INCORRECT_XML_FILE, VueFenetrePrincipale.MessageType.MessageTypeError);
			} catch (ParserConfigurationException e) {
        		this.showMessage(INCORRECT_XML_FILE, VueFenetrePrincipale.MessageType.MessageTypeError);
			} catch (IOException e) {
        		this.showMessage(COUDNT_READ_FILE, VueFenetrePrincipale.MessageType.MessageTypeError);
			} catch (IndexOutOfBoundsException e) {
        		this.showMessage(ERR_PLAN_LIV, VueFenetrePrincipale.MessageType.MessageTypeError);
			}
        }
        else {
                this.showMessage(NO_PLAN, VueFenetrePrincipale.MessageType.MessageTypeLog);
        }
    }
    
    public void selectTournee(Tournee tournee) { 
        this.controleurInspecteur.setPlagesHoraires(tournee.getPlagesHoraire());
        this.controleurPlan.setTournee(tournee);
        this.selectedTournee=tournee;

    	setUndoRedoButtons();
    }
    
    private void traitementDijkstra (Tournee tournee)
    {
    	Dijkstra dijkstra = new Dijkstra();
    	if(tournee.getLivraisons().size()>0){
    		dijkstra.initTournee(plan, tournee);
    	}
    	else {
    		tournee.getTrajets().clear();
    	}
    	this.controleurPlan.setTournee(tournee);
    }

    
    public void undo(){
    	UndoManager current = controleurUndoManager.getUndoManagerForTournee(selectedTournee);
    	if ( current.canUndo() ){
    		current.undo();	
    	} 

    	setUndoRedoButtons();
    }
    public void redo(){
    	UndoManager current = controleurUndoManager.getUndoManagerForTournee(selectedTournee);
    	if ( current.canRedo() ){
    		current.redo();	
    	} 
    	setUndoRedoButtons();
    }
    
    public void setUndoRedoButtons(){
    	fenetre.canRedo(controleurUndoManager.getUndoManagerForTournee(selectedTournee).canRedo());
    	fenetre.canUndo(controleurUndoManager.getUndoManagerForTournee(selectedTournee).canUndo());
    }
    
    public void shouldAddLivraisonAndReload(Livraison livraison)
    {
    	AjouterLivraisonEdit addEdit = new AjouterLivraisonEdit(livraison, this);
    	addEdit.execute();
    	UndoManager current = controleurUndoManager.getUndoManagerForTournee(selectedTournee);
    	current.addEdit(addEdit);
    	

    	setUndoRedoButtons();
    }
    
    public void addLivraisonAndReload(Livraison livraison){
    	selectedTournee.addLivraison(livraison);    	
    	traitementDijkstra(selectedTournee);
    }
    
    
    public void shouldRemoveLivraisonAndReload(Livraison livraison)
    {    	
    	EnleverLivraisonEdit removeEdit = new EnleverLivraisonEdit(livraison, this);
    	removeEdit.execute();
    	UndoManager current = controleurUndoManager.getUndoManagerForTournee(selectedTournee);
    	current.addEdit(removeEdit);

    	setUndoRedoButtons();
    }
    
    public void removeLivraisonAndReload(Livraison livraison)
    {    	
    	selectedTournee.removeLivraison(livraison);
    	traitementDijkstra(selectedTournee);
    }
    
    public void shouldExportTournee() {
        ControleurTournee controleurTournee = new ControleurTournee();
        JFileChooser fChooser = FileChooserFactory.createFileChooser( controleurTournee.getTourneeFileExt()
        		, controleurTournee.getFiletypeName(), getCurrentDirectory() );
       
        int returnVal = fChooser.showSaveDialog(this.fenetre);
        if( returnVal == JFileChooser.APPROVE_OPTION ) {
                String file = fChooser.getSelectedFile().getAbsolutePath();
                lastUsedFolder = fChooser.getSelectedFile().getParent();

                boolean worked = false;
                boolean namePermitted =  controleurTournee.isFilenamePermitted(fChooser.getSelectedFile().getName());
                if ( namePermitted ){
                worked = controleurTournee.tourneeToTxt(this.selectedTournee, file);
                }else{
                        this.showMessage(FILENAME_NOT_PERMITTED, VueFenetrePrincipale.MessageType.MessageTypeError);
                }

        if ( worked ){
                this.showMessage(TOURNEE_WRITTEN, VueFenetrePrincipale.MessageType.MessageTypeSuccess);
        } else {
                this.showMessage(TOURNEE_NOT_WRITTEN, VueFenetrePrincipale.MessageType.MessageTypeError);
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
    
    public void showMessage(String message, VueFenetrePrincipale.MessageType type) {
        this.fenetre.setMessage(message, type);
    }
}
