package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Plan {
	
	protected ArrayList<Troncon> troncons = new ArrayList<Troncon>();
	protected ArrayList<Noeud> noeuds = new ArrayList<Noeud>();

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
	
	public void trierNoeudsParY(){
		Collections.sort(noeuds, Collections.reverseOrder(new CustomNoeudYComparator()));
	}
	
	public static class CustomNoeudYComparator implements Comparator<Noeud> {
	    @Override
	    public int compare(Noeud n1, Noeud n2) {
	        return n1.getY().compareTo(n2.getY());
	    }
	}

}
