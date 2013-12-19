package model;

import java.util.ArrayList;
import java.util.List;

public class Trajet {

	/**
	 * La liste des tronçons composant le trajet
	 */
	protected ArrayList<Troncon> troncons = new ArrayList<Troncon>();

	/**
	 * La plage horaire du Trajet 
	 */
	protected PlageHoraire plage;


	public Trajet() {

	}

	public Trajet(List<Noeud> noeuds) {		
		for(int i=0;i<noeuds.size()-1;i++) {
			Noeud noeudOrigine = noeuds.get(i);
			for(Troncon t : noeudOrigine.getTronconsSortants()) {
				if(t.getDestination()==noeuds.get(i+1)) {
					troncons.add(t);
					break;
				}
			}
		}
	}
	
	public PlageHoraire getPlage() {
		return plage;
	}

	public void setPlage(PlageHoraire plage) {
		this.plage = plage;
	}

	public String toString(){
		String ret="";
		for (Troncon t : troncons) {
			ret+=t.toString()+" ";
		}
		return ret;
	}

	public ArrayList<Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(ArrayList<Troncon> troncons) {
		this.troncons = troncons;
	}

	/**
	 * Permet d'obtenir le temps total de parcours du trajet
	 * @return La somme des temps de parcours des tronçons du trajet
	 */
	public Integer getTempsTrajet ()
	{
		double tps = 0.0;

		for (int i = 0 ; i<troncons.size() ; i++ )
		{
			tps += troncons.get(i).getCost();
		}

		return (int) tps;
	}
}
