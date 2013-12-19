package controller;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Livraison;

/**
 * @author Ianic
 * Classe responsable pour enlever une livraison d'un plan.
 * Aussi pour annuler et réfaire cette action.
 */
public class EnleverLivraisonEdit extends AbstractUndoableEdit {

	private static final long serialVersionUID = 659051521156766166L;
	private static final String presentationName = "Enlever livraison";
	private Livraison livraison = null;
	private ControleurFenetrePrincipale controleur = null;

	/**
	 * @param livraison {@link Livraison} a être enlever par l'edit.
	 * @param cFenetrePrincipale L'instance du {@link ControleurFenetrePrincipale} à laquelle l'edit sera appliqué.
	 */
	public EnleverLivraisonEdit(Livraison livraison, ControleurFenetrePrincipale cFenetrePrincipale
			) {
		this.livraison = livraison;
		this.controleur = cFenetrePrincipale;
	}

	/**
	 * Enlève une livraison au plan selectioné dans le {@link ControleurFenetrePrincipale}
	 * 
	 */
	public void execute(){
		//Ajouter livraison
		controleur.removeLivraisonAndReload(livraison);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		//TODO
		controleur.addLivraisonAndReload(livraison);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		//TODO
		controleur.removeLivraisonAndReload(livraison);
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