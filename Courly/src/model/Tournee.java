package model;

import java.util.ArrayList;

public class Tournee {
	
	protected ArrayList<Livraison> livraisons;
	protected ArrayList<Trajet> trajet;
	protected ArrayList<PlageHoraire> plagesHoraire;
	
	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}
	
	public void setLivraisons(ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}
	
	public ArrayList<Trajet> getTrajet() {
		return trajet;
	}
	
	public void setTrajet(ArrayList<Trajet> trajet) {
		this.trajet = trajet;
	}
	
	public ArrayList<PlageHoraire> getPlagesHoraire() {
		return plagesHoraire;
	}
	
	public void setPlagesHoraire(ArrayList<PlageHoraire> plagesHoraire) {
		this.plagesHoraire = plagesHoraire;
	}
	
	public void addLivraison(Livraison livraison)
	{
		//TODO
	}

}
