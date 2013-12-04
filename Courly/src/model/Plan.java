package model;

import java.util.ArrayList;
import java.util.Vector;

public class Plan {
	
	protected ArrayList<Troncon> troncons;
	protected Vector<Noeud> noeuds;

	public Plan() {

	}
	
	public Plan(ArrayList<Troncon> troncons, Vector<Noeud> noeuds) {
		this.troncons = troncons;
		this.noeuds = noeuds;
	}
	
	public Vector<Noeud> getNoeuds() {
		return this.noeuds;
	}
	
	public void setNoeuds(Vector<Noeud> noeuds) {
		this.noeuds = noeuds;
	}

}
