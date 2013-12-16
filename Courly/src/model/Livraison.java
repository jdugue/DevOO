package model;

import java.util.Date;

import org.w3c.dom.Element;

public class Livraison extends Lieu {
	
	protected Integer id;
	protected Integer client;
	protected PlageHoraire plageHoraire;
	protected Date heurePassage;
	
	private final String TAG_ID = "id";
	private final String TAG_CLIENT = "client";
	
	public static Livraison createLivraison( String newId , String newClient, String newAdresse, PlageHoraire plageHoraire ){
		//FIXME
		try {
			Integer id = Integer.parseInt(newId);
			Integer client = Integer.parseInt ( newClient );
			Integer adresse = Integer.parseInt(newAdresse);
			Livraison livraison = null;
			if ( plageHoraire != null ){
				
				livraison = new Livraison();
				livraison.setAdresse(adresse);
				livraison.setClient(client);
				livraison.setPlageHoraire(plageHoraire);
				livraison.setId(id);
				
				return livraison;
			}
		}catch (NumberFormatException nfe){
			return null;
		}
		return null;

	}
	
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

	public Date getHeurePassage() {
		return heurePassage;
	}

	public void setHeurePassage(Date heurePassage) {
		this.heurePassage = heurePassage;
	}

	public void construireAPartirDeDOMXML(Element noeudDOMRacine, PlageHoraire plage) {
		super.construireAPartirDeDOMXML(noeudDOMRacine);
		client = Integer.parseInt(noeudDOMRacine.getAttribute(TAG_CLIENT));
		id =  Integer.parseInt(noeudDOMRacine.getAttribute(TAG_ID));
		plageHoraire = plage;		
	}
	
	public boolean estValide() {
		return heurePassage.before(plageHoraire.getHeureFin());
	}

	public Livraison() {
		// TODO Auto-generated constructor stub
	}

}
