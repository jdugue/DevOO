package controller;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Livraison;

public class EnleverLivraisonEdit extends AbstractUndoableEdit {

        private static final String presentationName = "Enlever livraison";
        private Livraison livraison = null;
        private ControleurFenetrePrincipale controleur = null;
        
        public EnleverLivraisonEdit(Livraison l, ControleurFenetrePrincipale c) {
               this.livraison = l;
               this.controleur = c;
        }
        
        public void execute(){
        	
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