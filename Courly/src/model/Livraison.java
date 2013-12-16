package model;

import org.w3c.dom.Element;

public class Livraison extends Lieu {
	
	protected Integer id;
	protected Integer client;
	protected PlageHoraire plageHoraire;
	protected String heurePassage;
	
	private final String TAG_ID = "id";
	private final String TAG_CLIENT = "client";
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public PlageHoraire getPlageHoraire() {
		return plageHoraire;
	}

	public void setPlageHoraire(PlageHoraire plageHoraire) {
		this.plageHoraire = plageHoraire;
	}

	public String getHeurePassage() {
		return heurePassage;
	}

	public void setHeurePassage(String heurePassage) {
		this.heurePassage = heurePassage;
	}

	public void construireAPartirDeDOMXML(Element noeudDOMRacine, PlageHoraire plage) {
		super.construireAPartirDeDOMXML(noeudDOMRacine);
		client = Integer.parseInt(noeudDOMRacine.getAttribute(TAG_CLIENT));
		id =  Integer.parseInt(noeudDOMRacine.getAttribute(TAG_ID));
		plageHoraire = plage;		
	}

	public Livraison() {
		// TODO Auto-generated constructor stub
	}

}
