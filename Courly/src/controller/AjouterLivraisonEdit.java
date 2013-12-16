package controller;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Livraison;

public class AjouterLivraisonEdit extends AbstractUndoableEdit {

	private static final String presentationName = "Ajouter livraison";
	private Livraison livraison = null;
	private ControleurFenetrePrincipale controleur = null;

	public AjouterLivraisonEdit(Livraison l, ControleurFenetrePrincipale c) {
		this.livraison = l;
		this.controleur = c;
	}
	
	public void execute() throws CannotUndoException {
		//Ajouter livraison
		controleur.addLivraisonAndReload(livraison);
	}


	@Override
	public void undo() throws CannotUndoException {
		//Enlever livraison
		controleur.removeLivraisonAndReload(livraison);
	}

	@Override
	public void redo() throws CannotRedoException {
		//Ajouter livraison
		controleur.addLivraisonAndReload(livraison);
	}

	@Override
	public boolean canUndo() {
		//TODO
		if( livraison == null || controleur == null){
			return false;
		}
		return true; 
	}

	@Override
	public boolean canRedo() {
		//TODO
		if( livraison == null || controleur == null){
			return false;
		}
		return true;
	}

	@Override
	public String getPresentationName() {
		return presentationName;
	}
}