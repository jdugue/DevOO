package model;

import java.util.Date;

import org.w3c.dom.Element;

public class Livraison extends Lieu implements Comparable<Livraison>{
	
	protected Integer id;
	protected Integer client;
	protected PlageHoraire plageHoraire;
	protected Date heureArrivee;
	protected Date heureDepart;
	
	public Date getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(Date heureDepart) {
		this.heureDepart = heureDepart;
	}

	private final String TAG_ID = "id";
	private final String TAG_CLIENT = "client";
	
	private static int MAX_ID = -1;
	
	public static Livraison createLivraison( String newClient, Integer newAdresse, PlageHoraire plageHoraire ){
		try {
			Integer client = Integer.parseInt ( newClient );
			Livraison livraison = null;
			if ( plageHoraire != null ){
				
				livraison = new Livraison();
				livraison.setAdresse(newAdresse);
				livraison.setClient(client);
				livraison.setPlageHoraire(plageHoraire);
				livraison.setId(++MAX_ID);
				
				return livraison;
			}
		}catch (NumberFormatException nfe){
			return null;
		}
		return null;

	}

        public Livraison( Noeud noeud, Integer adresse, Integer client, PlageHoraire plageHoraire) {
            super(adresse, noeud);
            this.client = client;
            this.setPlageHoraire(plageHoraire);
            this.setId(++MAX_ID);
        }
        
        
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		MAX_ID = MAX_ID < id ? id : MAX_ID;
		
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
                this.plageHoraire.addLivraison(this);
	}

	public Date getHeureArrivee() {
		return heureArrivee;
	}

	public void setHeureArrivee(Date heureArrivee) {
		this.heureArrivee = heureArrivee;
	}

	public void construireAPartirDeDOMXML(Element noeudDOMRacine, PlageHoraire plage) {
		super.construireAPartirDeDOMXML(noeudDOMRacine);
		client = Integer.parseInt(noeudDOMRacine.getAttribute(TAG_CLIENT));
		id =  Integer.parseInt(noeudDOMRacine.getAttribute(TAG_ID));
		MAX_ID = MAX_ID < id ? id : MAX_ID;
		plageHoraire = plage;		
	}
	
	public boolean estValide() {
		if (this.heureArrivee != null) {
			return heureArrivee.before(plageHoraire.getHeureFin());
		} else {
			return true;
		}
	}

	public Livraison() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(Livraison o) {
		return getHeureArrivee().compareTo(o.getHeureArrivee());
	}

}
