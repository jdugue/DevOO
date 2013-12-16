package model;

import java.util.ArrayList;

public class Tournee {
	
	protected Depot depot;
	protected ArrayList<Livraison> livraisons;
	protected ArrayList<Trajet> trajets;
	protected ArrayList<PlageHoraire> plagesHoraire;

        public Tournee() {
        }

        public Tournee(Tournee tournee) {
            this.livraisons = new ArrayList<Livraison>(tournee.getLivraisons());
            this.trajets = new ArrayList<Trajet>(tournee.getTrajets());
            this.plagesHoraire = new ArrayList<PlageHoraire>(tournee.getPlagesHoraire());
            this.depot = tournee.getDepot();
        }       
	
	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}
	
	public void setLivraisons (ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}
	
	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot){
		this.depot = depot;
	}

	public ArrayList<Trajet> getTrajets() {
		return trajets;
	}

	public void setTrajets(ArrayList<Trajet> trajets) {
		this.trajets = trajets;
	}
	
	public ArrayList<PlageHoraire> getPlagesHoraire() {
		return plagesHoraire;
	}
	
	public void setPlagesHoraire(ArrayList<PlageHoraire> plagesHoraire) {
		this.plagesHoraire = plagesHoraire;	
	}
        
        public void removeLivraison(Livraison livraison) {
            livraison.getPlageHoraire().livraisons.remove(livraison);
            this.livraisons.remove(livraison);
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

}
