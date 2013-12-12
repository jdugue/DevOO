package model;

import java.util.ArrayList;

import org.w3c.dom.Element;

public class Troncon {
	
	protected Noeud origine;
	protected Noeud destination;
	protected String nomRue;
	protected Double longueur;
	protected Double vitesse;
	protected ArrayList<Trajet> trajets = new ArrayList<Trajet>();
	
	public Troncon(Noeud origine,Noeud destination, String nomRue, Double longueur, Double vitesse) {
		this.origine = origine;
		this.destination = destination;
		this.nomRue = nomRue;
		this.longueur = longueur;
		this.vitesse = vitesse;
	}
	
	public ArrayList<Trajet> getTrajets() {
		return trajets;
	}

	public void setTrajets(ArrayList<Trajet> trajets) {
		this.trajets = trajets;
	}

	public Troncon() {
		
	}
	
	public String toString(){
		return this.nomRue;
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

	public Noeud getOrigine() {
		return origine;
	}

	public void setOrigine(Noeud origine) {
		this.origine = origine;
	}

	public Noeud getDestination() {
		return destination;
	}

	public void setDestination(Noeud destination) {
		this.destination = destination;
	}

	public Double getLongueur() {
		return longueur;
	}

	public void setLongueur(Double longueur) {
		this.longueur = longueur;
	}

	public Double getVitesse() {
		return vitesse;
	}

	public void setVitesse(Double vitesse) {
		this.vitesse = vitesse;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}
	
	public Double getCost() {
		return this.longueur/this.vitesse;
	}
        
        public boolean isEqualToTroncon(Troncon otherTroncon) {
            if ((this.destination == otherTroncon.destination && this.origine == this.origine) || (this.destination == otherTroncon.origine && this.origine == otherTroncon.destination)) {
                return true;  
            } else {
                return false;
            }
        }

}
