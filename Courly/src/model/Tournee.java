package model;

import java.util.ArrayList;

public class Tournee {
	
	protected ArrayList<Lieu> lieux;
	protected ArrayList<Trajet> trajets;
	protected ArrayList<PlageHoraire> plagesHoraire;
	
	public ArrayList<Lieu> getLieux() {
		return lieux;
	}

	public void setLieux(ArrayList<Lieu> lieux) {
		this.lieux = lieux;
	}

	public ArrayList<Trajet> getTrajets() {
		return trajets;
	}

	public void setTrajets(ArrayList<Trajet> trajets) {
		this.trajets = trajets;
	}
<<<<<<< HEAD

		
	public ArrayList<Trajet> getTrajet() {
		return trajets;
	}
	
	public void setTrajet(ArrayList<Trajet> trajet) {
		this.trajets = trajet;
	}
=======
>>>>>>> 4055d6d483de6a4166cc7045d0bd9e61075610f4
	
	public ArrayList<PlageHoraire> getPlagesHoraire() {
		return plagesHoraire;
	}
	
	public void setPlagesHoraire(ArrayList<PlageHoraire> plagesHoraire) {
		this.plagesHoraire = plagesHoraire;	
	}
	
	public void addLivraison(Livraison livraison){
		lieux.add(livraison);	
	}

}
