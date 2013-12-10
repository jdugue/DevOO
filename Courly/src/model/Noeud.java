package model;

import java.util.ArrayList;

import org.w3c.dom.Element;

public class Noeud implements Comparable<Noeud>{
	
	protected Integer id;
	protected Integer x;
	protected Integer y;
	protected Lieu lieu;
	protected ArrayList<Troncon> tronconsSortants;
	
	//Attributs utiles pour Dijkstra
	protected double minTemps = Double.POSITIVE_INFINITY;
	protected Noeud previous;

	
	public Noeud() {

	}
	
	public Noeud(Integer id, Integer x, Integer y, Lieu lieu,ArrayList<Troncon> tronconsSortants) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.lieu = lieu;
		this.tronconsSortants = tronconsSortants;
	}
	
	public String toString() {
		String idString = Integer.toString(id);
		return idString;
	}
	
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

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}
	
	public Integer getX() {
		return this.x;
	}
	
	public Integer getY() {
		return this.y;
	}
	
	public Lieu getLieu() {
		return this.lieu;
	}
	
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

}
