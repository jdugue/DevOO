package controller;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Livraison;

/**
 * @author Ianic
 * Classe responsable pour ajouter une livraison au plan.
 * Aussi pour annuler et réfaire cette action.
 */
public class AjouterLivraisonEdit extends AbstractUndoableEdit {

	private static final String presentationName = "Ajouter livraison";
	private Livraison livraison = null;
	private ControleurFenetrePrincipale controleur = null;

	/**
	 * @param livraison La {@link Livraison} a être ajouté par cet edit.
	 * @param cFenetrePrincipale L'instance de {@link ControleurFenetrePrincipale} à l'aquelle l'edit sera appliqué.
	 */
	public AjouterLivraisonEdit(Livraison livraison, ControleurFenetrePrincipale cFenetrePrincipale) {
		this.livraison = livraison;
		this.controleur = cFenetrePrincipale;
	}
	
	
	/**
	 * Ajoute une livraison au plan selectioné dans le {@link ControleurFenetrePrincipale}.
	 * @throws CannotUndoException
	 */
	public void execute() throws CannotUndoException {
		controleur.addLivraisonAndReload(livraison);
	}


	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		//Enlever livraison
		controleur.removeLivraisonAndReload(livraison);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		//Ajouter livraison
		controleur.addLivraisonAndReload(livraison);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		//TODO
		if( livraison == null || controleur == null){
			return false;
		}
		return true; 
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		//TODO
		if( livraison == null || controleur == null){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#getPresentationName()
	 */
	@Override
	public String getPresentationName() {
		return presentationName;
	}
}