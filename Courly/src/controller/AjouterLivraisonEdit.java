package controller;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class AjouterLivraisonEdit extends AbstractUndoableEdit {
        
        private static final String presentationName = "Ajouter livraison";

        public AjouterLivraisonEdit() {
                // TODO Auto-generated constructor stub
        }
        
        @Override
        public void undo() throws CannotUndoException {
                //TODO
        }
        
        @Override
        public void redo() throws CannotRedoException {
                //TODO
        }
        
        @Override
        public boolean canUndo() {
                 //TODO
                 return true; 
        }
        
        @Override
        public boolean canRedo() {
                //TODO
                return true;
        }
        
        @Override
        public String getPresentationName() {
                return presentationName;
        }
}