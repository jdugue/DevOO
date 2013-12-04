package model;

import org.w3c.dom.Element;

public class Noeud {
	
	protected Integer id;
	protected Integer x;
	protected Integer y;
	protected Lieu lieu;
	
	public Noeud() {

	}
	
	public Noeud(Integer id, Integer x, Integer y, Lieu lieu) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.lieu = lieu;
	}
	
	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}
	
	public Integer getID() {
		return this.id;
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

}
