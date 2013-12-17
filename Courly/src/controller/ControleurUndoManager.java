package controller;

import java.util.HashMap;

import javax.swing.undo.UndoManager;

import model.Tournee;

public class ControleurUndoManager {

	public ControleurUndoManager() {
		// TODO Auto-generated constructor stub
	}
	
	HashMap<Tournee, UndoManager> undoManagerTournee = new HashMap<Tournee, UndoManager>();

	public UndoManager getUndoManagerForTournee (Tournee tournee){
		UndoManager uMan = undoManagerTournee.get(tournee);
		if ( uMan == null ){
			uMan = new UndoManager();
			undoManagerTournee.put(tournee, uMan);
		}
		return uMan;
	}
	
	public void cleanAll(){
		undoManagerTournee.clear();
	}
}
