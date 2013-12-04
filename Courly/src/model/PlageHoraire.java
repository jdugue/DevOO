package model;

import java.util.ArrayList;

public class PlageHoraire {
	
	protected String heureDebut;
	protected String heureFin;
	ArrayList<Livraison> livraisons;
	
	public String getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}

	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public PlageHoraire() {
		// TODO Auto-generated constructor stub
	}

}
