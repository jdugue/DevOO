/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
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
	}

	/**
	 * 
	 * @param zoomScale 
	 */
	public void setZoomScale(double zoomScale) {

		if (zoomScale > zoomMax) {
			zoomScale = zoomMax;
		} else if (zoomScale < zoomMin) {
			zoomScale = zoomMin;
		}
		this.zoomScale = zoomScale;
		this.controleurPlan.setZoomScale(zoomScale);
	}


	/**
	 * Diminue le zoom sur le plan.
	 */
	public void shouldZoomOut() {
		this.setZoomScale(zoomScale - zoomDelta);
	}

	/**
	 * Augmente le zoom sur le plan.
	 */
	public void shouldZoomIn() {
		this.setZoomScale(zoomScale + zoomDelta);
	}


	/**
	 * @return Vrai si il y a une tournée sélectionné.
	 */
	public boolean canCreateLivraison() {
		return (this.selectedTournee != null);
	}


	/**
	 * Demande à l'utilisateur le fichier du plan à être chargé.
	 * Charge le fichier si possible et affiche le plan.
	 */
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
					this.selectedTournee = null;
					this.deselectAllNoeuds();
					controleurUndoManager.cleanAll();
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

	/**
	 * Demande à l'utilisateur le fichier des livraisons à être chargé.
	 * Charge le fichier si possible et affiche les livraisons.
	 * Calcule la feuille de route automatiquement si demandé par l'utilisateur.
	 */
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
						if ( fenetre.shouldAutoCalculateTournee()) {
							traitementDijkstra(tournee);
						}else{
							this.controleurPlan.setTournee(tournee);
						}
						this.fenetre.addTournee(tournee, file, true);
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
			}catch (IndexOutOfBoundsException e) {
				this.showMessage(ERR_PLAN_LIV, VueFenetrePrincipale.MessageType.MessageTypeError);
			}
		}
		else {
			this.showMessage(NO_PLAN, VueFenetrePrincipale.MessageType.MessageTypeLog);
		}
	}

	/**
	 * Selectione une tournée, rafraîchit l'état des boutons Exporter, Annuler et Réfaire, désélectionne tous les noeuds,
	 * set les plages horaires de l'inspecteur.
	 * @param tournee La tournée à être sélectionné.
	 */
	public void selectTournee(Tournee tournee) { 
		this.controleurInspecteur.setPlagesHoraires(tournee.getPlagesHoraire());
		this.controleurPlan.setTournee(tournee);
		this.selectedTournee = tournee;
		this.deselectAllNoeuds();
		this.fenetre.canExportTournee(this.selectedTournee != null);

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
		this.deselectAllNoeuds();
	}



	/**
	 * Annule la dernière commande faite si possible.
	 */
	public void undo(){
		UndoManager current = controleurUndoManager.getUndoManagerForTournee(selectedTournee);
		if ( current.canUndo() ){
			current.undo();	
		} 

		setUndoRedoButtons();
	}

	/**
	 * Réfait la dernière commande faite si possible.
	 */
	public void redo(){
		UndoManager current = controleurUndoManager.getUndoManagerForTournee(selectedTournee);
		if ( current.canRedo() ){
			current.redo();	
		} 
		setUndoRedoButtons();
	}

	/**
	 * Rafraichi l'état des boutons annuler et réfaire.
	 */
	public void setUndoRedoButtons(){
		fenetre.canRedo(controleurUndoManager.getUndoManagerForTournee(selectedTournee).canRedo());
		fenetre.canUndo(controleurUndoManager.getUndoManagerForTournee(selectedTournee).canUndo());
	}


	/**
	 * Ajouté une livraison au plan, crée un edit et rafraichi l'état des boutons annuler et réfaire.
	 * @param livraison La livraison à être ajouté plan.
	 */
	public void shouldAddLivraisonAndReload(Livraison livraison)
	{
		AjouterLivraisonEdit addEdit = new AjouterLivraisonEdit(livraison, this);
		addEdit.execute();
		UndoManager current = controleurUndoManager.getUndoManagerForTournee(selectedTournee);
		current.addEdit(addEdit);


		setUndoRedoButtons();
	}

	/**
	 * Ajoute une livraison au plan.
	 * @param livraison La livraison à être ajouté au plan.
	 */
	public void addLivraisonAndReload(Livraison livraison){
		selectedTournee.addLivraison(livraison);    
		if ( fenetre.shouldAutoCalculateTournee()) {
			traitementDijkstra(selectedTournee);
		}else{
			this.controleurPlan.setTournee(selectedTournee);
		}
		this.deselectAllNoeuds();
	}


	/**
	 * Enléve une livraison du plan, crée un edit et rafraichi l'état des boutons annuler et réfaire.
	 * @param livraison La livraison à être enlevé du plan.
	 */
	public void shouldRemoveLivraisonAndReload(Livraison livraison)
	{    	
		EnleverLivraisonEdit removeEdit = new EnleverLivraisonEdit(livraison, this);
		removeEdit.execute();
		UndoManager current = controleurUndoManager.getUndoManagerForTournee(selectedTournee);
		current.addEdit(removeEdit);

		setUndoRedoButtons();
	}


	/**
	 * Calcule la feuille de route pour la tournée sélectionnée. 
	 */
	public void shouldRecalculateTournee() {
		this.traitementDijkstra(selectedTournee);
	}

	/**
	 * Enlève une livraison de la feuille de route et recalcule la feuille de route si 
	 * demandé par l'utilisateur.
	 * @param livraison Livraison à enlever de la feuille de route.
	 */
	public void removeLivraisonAndReload(Livraison livraison)
	{    	
		selectedTournee.removeLivraison(livraison);
		if ( fenetre.shouldAutoCalculateTournee()) {
			traitementDijkstra(selectedTournee);
		}else{
			this.controleurPlan.setTournee(selectedTournee);
		}
		this.controleurPlan.setTournee(selectedTournee);
		this.deselectAllNoeuds();
	}

	/**
	 * Demande a l'utilisateur le dossier et nom de fichier pour enregistrer la feuille de route courante.
	 * Enregistre la feuille de route ensuite.
	 */
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

	/**
	 * Désélectionne tous les noeuds du plan.
	 */
	public void deselectAllNoeuds() {
		this.controleurPlan.deselectAll();
		this.controleurInspecteur.setVueFromNoeud(null);
	}

	/**
	 * Selectione sur l'inspecteur le noeud donné.
	 * @param noeud Le noeud selectioné par l'utilisateur.
	 */
	public void didSelectNoeud(Noeud noeud) {
		this.controleurInspecteur.setVueFromNoeud(noeud);
	}

	/**
	 * Selectione sur l'inspecteur le lieu donné.
	 * @param lieu Le lieu selectioné par l'utilisateur.
	 */
	public void didSelectLieu(Lieu lieu) {
		this.controleurInspecteur.setVueFromLieu(lieu);
	}

	/**
	 * @return Le dossier racine du jar ou le dernier dossier ouvert dans un {@link JFileChooser}.
	 */
	private File getCurrentDirectory(){
		return lastUsedFolder == null ? new java.io.File(".") : new File(lastUsedFolder);		
	}

	/**
	 * @param message Message à être affiché sur l'écran principale.
	 * @param type Le type de la mesage affiché.
	 */
	public void showMessage(String message, VueFenetrePrincipale.MessageType type) {
		this.fenetre.setMessage(message, type);
	}
}
