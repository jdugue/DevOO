package model;

import java.util.ArrayList;

public class Tournee {
	
	protected Lieu depot;
	protected ArrayList<Livraison> livraisons;
	protected ArrayList<Trajet> trajets;
	protected ArrayList<PlageHoraire> plagesHoraire;

        public Tournee() {
        }

        public Tournee(Tournee tournee) {
            this.livraisons = new ArrayList<Livraison>(tournee.getLivraisons());
            this.trajets = new ArrayList<Trajet>(tournee.getTrajets());
            this.plagesHoraire = new ArrayList<PlageHoraire>(tournee.getPlagesHoraire());
        }       
	
	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}
	
	public void setLivraisons (ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}
	
	public Lieu getDepot() {
		return depot;
	}

	public void setDepot(Lieu depot){
		this.depot = depot;
	}

	public ArrayList<Trajet> getTrajets() {
		return trajets;
	}

	public void setTrajets(ArrayList<Trajet> trajets) {
		this.trajets = trajets;
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
	
	public PlageHoraire getFirstPlageHoraire () {
		PlageHoraire plage = plagesHoraire.get(0);
		if (plagesHoraire.size()>1){
			for(int i=1; i<plagesHoraire.size();i++) {
				if(plagesHoraire.get(i).getHeureDebut().before(plage.getHeureDebut())) {
					plage = plagesHoraire.get(i);
				}
			}
		}
		return plage;
	}
	
	public void addLivraison(Livraison livraison){
		livraisons.add(livraison);	
	}
	
	public void removeLivraison(Livraison livraison)
	{
		livraisons.remove(livraison);
	}

}
