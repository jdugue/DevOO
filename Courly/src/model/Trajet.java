package model;

import java.util.ArrayList;

public class Trajet {

	protected ArrayList<Troncon> troncons;

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
