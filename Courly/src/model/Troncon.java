package model;

import java.util.ArrayList;

import org.w3c.dom.Element;

public class Troncon {
	
	protected Noeud origine;
	protected Noeud destination;
	protected String nomRue;
	protected Double longueur;
	protected Double vitesse;

	public Troncon(Noeud origine,Noeud destination, String nomRue, Double longueur, Double vitesse) {
		this.origine = origine;
		this.destination = destination;
		this.nomRue = nomRue;
		this.longueur = longueur;
		this.vitesse = vitesse;
	}
	
	public Troncon() {
		
	}
	
	public String getNomRue() {
		return this.nomRue;
	}
	
	public void construireAPartirDeDOMXML(Element noeudDOMRacine, Integer idNoeudCourant, ArrayList<Noeud> vectNoeuds) {
		
		nomRue = noeudDOMRacine.getAttribute("nomRue");
		longueur = Double.parseDouble(noeudDOMRacine.getAttribute("longueur").replaceAll(",", "."));
    	vitesse = Double.parseDouble(noeudDOMRacine.getAttribute("vitesse").replaceAll(",", "."));
    	
    	origine = vectNoeuds.get(idNoeudCourant);
    	
    	Integer idDestination = Integer.parseInt(noeudDOMRacine.getAttribute("destination"));
    	destination = vectNoeuds.get(idDestination);
    	
	}

}
