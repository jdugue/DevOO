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
            if (this.destination.equals(otherTroncon.destination) && this.origine.equals(otherTroncon.origine)) {
                
                return true;
            } else if (this.destination.equals(otherTroncon.origine) && this.origine.equals(otherTroncon.destination)) {
                return true; 
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int hash = 7;
            int hash1 = 89 * hash + (this.origine != null ? this.origine.getId().hashCode() : 0);
            int hash2 = 89 * hash + (this.destination != null ? this.destination.getId().hashCode() : 0);
            return hash1 + hash2;
            
            // Troncon hascode do not depends wether Noeud are Origine or Destination
        }

        
        @Override
        public boolean equals(Object obj) {
            return (this.hashCode() == ((Troncon)obj).hashCode());
        }
}
