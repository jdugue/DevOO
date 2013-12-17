package controller;

import java.util.HashMap;

import javax.swing.undo.UndoManager;

import model.Tournee;

public class ControleurUndoManager {

	public ControleurUndoManager() {
		// TODO Auto-generated constructor stub
	}
	
	HashMap<Tournee, UndoManager> undoManagerTournee = new HashMap<Tournee, UndoManager>();
	
	public void addTournee (Tournee toAdd){
		undoManagerTournee.put(toAdd, new UndoManager());
	}
	
	public UndoManager getUndoManagerForTournee (Tournee tournee){
		return undoManagerTournee.get(tournee);
	}
}
