package model;

import java.util.ArrayList;

import org.w3c.dom.Element;

/**
 * @author Ianic
 * Intersection de plusieurs arrêtes du graphe.
 */
public class Noeud implements Comparable<Noeud>{

	protected int id;
	protected Integer x;
	protected Integer y;
	protected ArrayList<Troncon> tronconsSortants;

	//Attributs utiles pour Dijkstra
	protected double minTemps = Double.POSITIVE_INFINITY;
	protected Noeud previous;


	public Noeud() {

	}

	/**
	 * @param id L'id du noeud.
	 * @param x La coordonnée x du noeud.
	 * @param y La coordonnée y du noeud.
	 * @param tronconsSortants Les tronçons sortant de ce noeud.
	 */
	public Noeud(Integer id, Integer x, Integer y, ArrayList<Troncon> tronconsSortants) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.tronconsSortants = tronconsSortants;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String idString = Integer.toString(id);
		return idString;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Noeud o) {
		return Double.compare(minTemps, o.minTemps);
	}

	public double getMinTemps() {
		return minTemps;
	}

	public void setMinTemps(double minTemps) {
		this.minTemps = minTemps;
	}

	public Noeud getPrevious() {
		return previous;
	}

	public void setPrevious(Noeud previous) {
		this.previous = previous;
	}

	public Integer getX() {
		return this.x;
	}

	public Integer getY() {
		return this.y;
	}

	/**
	 * Set les attributs id, x, y de ce objet en accord avec le xml donné.
	 * @param noeudDOMRacine L'élement xml qui contient les attributs de ce noeud.
	 * 
	 */
	public void construireAPartirDeDOMXML(Element noeudDOMRacine) {
		id = Integer.parseInt(noeudDOMRacine.getAttribute("id"));
		x = Integer.parseInt(noeudDOMRacine.getAttribute("x"));
		y = Integer.parseInt(noeudDOMRacine.getAttribute("y"));	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ArrayList<Troncon> getTronconsSortants() {
		return tronconsSortants;
	}

	public void setTronconsSortants(ArrayList<Troncon> tronconsSortants) {
		this.tronconsSortants = tronconsSortants;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 71 * hash + this.id;
		hash = 71 * hash + (this.x != null ? this.x.hashCode() : 0);
		hash = 71 * hash + (this.y != null ? this.y.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return (this.hashCode() == ((Noeud)obj).hashCode());
	}

}
