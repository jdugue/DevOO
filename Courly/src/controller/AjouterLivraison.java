package controller;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class AjouterLivraison extends AbstractUndoableEdit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AjouterLivraison() {
		// TODO Auto-generated constructor stub
	}
	
	public void undo() throws CannotUndoException {
		// TODO 
		// Undo code
	}
	
	public void redo() throws CannotRedoException {
		//TODO
		//Redo code
	}
	
	public boolean canUndo() {
		// TODO
		// Code pour tester si on peut Undo l'action
		return true;
	}
	
	public boolean canRedo() { 
		// TODO
		// Code pour tester si on peut Redo l'action
		 return true; 
	}
	
	public String getPresentationName() { 
		
		return "AjouterLivraison"; 
	}
	
	

}
