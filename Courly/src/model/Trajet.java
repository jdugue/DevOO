package model;

import java.util.ArrayList;
import java.util.List;

public class Trajet {

	protected ArrayList<Troncon> troncons = new ArrayList<Troncon>();
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
