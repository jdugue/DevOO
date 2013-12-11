package model;

import java.util.ArrayList;

public class Tournee {
	
	protected ArrayList<Livraison> livraisons;
	protected ArrayList<Trajet> trajets;
	protected ArrayList<PlageHoraire> plagesHoraire;

        public Tournee() {
        }

        public Tournee(Tournee tournee) {
            this.lieux = new ArrayList<Lieu>(tournee.getLieux());
            this.trajets = new ArrayList<Trajet>(tournee.getTrajets());
            this.plagesHoraire = new ArrayList<PlageHoraire>(tournee.getPlagesHoraire());
        }       
	
	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}
	
	public void setLivraisons(ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}
	
	public ArrayList<Trajet> getTrajet() {
		return trajets;
	}
	
	public void setTrajet(ArrayList<Trajet> trajet) {
		this.trajets = trajet;
	}
	
	public ArrayList<PlageHoraire> getPlagesHoraire() {
		return plagesHoraire;
	}
	
	public void setPlagesHoraire(ArrayList<PlageHoraire> plagesHoraire) {
		this.plagesHoraire = plagesHoraire;
	}
	
	public void addLivraison(Livraison livraison){
		livraisons.add(livraison);
		
	}

}
