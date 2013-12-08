package model;

import java.util.ArrayList;

public class Plan {
	
	protected ArrayList<Troncon> troncons;
	protected ArrayList<Noeud> noeuds;

	public Plan() {

	}
	
	public Plan(ArrayList<Troncon> troncons, ArrayList<Noeud> noeuds) {
		this.troncons = troncons;
		this.noeuds = noeuds;
	}
	
	public ArrayList<Noeud> getNoeuds() {
		return this.noeuds;
	}
	
	public void setNoeuds(ArrayList<Noeud> noeuds) {
		this.noeuds = noeuds;
	}
	
	public void setTroncons(ArrayList<Troncon> troncons) {
		this.troncons = troncons;
	}

	public ArrayList<Troncon> getTroncons() {
		return troncons;
	}
	
	public void addTroncon(Troncon troncon) {
		this.getTroncons().add(troncon);
	}
	
	public void addNoeud(Noeud noeud) {
		this.getNoeuds().add(noeud);
	}

}
