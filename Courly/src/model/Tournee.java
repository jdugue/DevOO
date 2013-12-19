package model;

import java.util.ArrayList;
import java.util.Collections;

public class Tournee {

	/**
	 * Le dépôt de départ de la Tournée 
	 */
	protected Depot depot;

	/**
	 * La liste des livraisons de la Tournee
	 */
	protected ArrayList<Livraison> livraisons;

	/**
	 * La liste des trajets de la Tournee 
	 */
	protected ArrayList<Trajet> trajets;

	/**
	 * La liste des PlageHoraire de la Tournee 
	 */
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

	/**
	 * Supprime une livraison de la Tournee et la supprime aussi de la liste des livraisons de la plage horaire correspondante
	 * @param livraison La livraison à supprimer
	 */
	public void removeLivraison(Livraison livraison) {
		livraison.getPlageHoraire().livraisons.remove(livraison);
		this.livraisons.remove(livraison);
	}

	/**
	 * Permet d'obtenir la première plage horaire de la Tournee
	 * @return La première plage horaire de la tournée
	 */
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

	/**
	 * Ajoute une livraison à la Tournee et l'ajoute aussi à la bonne plage horaire
	 * @param livraison : la livraison à ajouter
	 */
	public void addLivraison(Livraison livraison){
		livraisons.add(livraison);	
		livraison.getPlageHoraire().addLivraison(livraison);
	}

}
