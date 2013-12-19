package model;

import java.util.ArrayList;

/**
 * @author Ianic
 * Classe qui contient les informations relatives au plan dessiné.
 * Les tronçons et noeuds.
 */
public class Plan {

	protected ArrayList<Troncon> troncons = new ArrayList<Troncon>();
	protected ArrayList<Noeud> noeuds = new ArrayList<Noeud>();

	public Plan() {
	}

	/**
	 * @param troncons Les tronçons du plan.
	 * @param noeuds Les noeuds du plan.
	 */
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
