package model;

import java.util.Date;

import org.w3c.dom.Element;

/**
 * @author Ianic
 *  Une visite à une adresse d’une durée de 10 minutes pour délivrer une commande à un client.
 */
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


	/**
	 * @param noeud Le {@link Noeud} auquel appartien cette livraison.
	 * @param adresse L'adresse de la livraison.
	 * @param client Le client de la livraison.
	 * @param plageHoraire La {@link PlageHoraire} à laquelle cette livraison appartient.
	 */
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

	/**
	 * Set la plage horaire de cette livraison, et ajoute cette livraison à la plage horaire.
	 * @param plageHoraire La {@link PlageHoraire} de cette livraison.
	 */
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

	/**
	 * Set les attributs de la livraison à partir d'un element XML.
	 * @param noeudDOMRacine L'element qui contient les informations de la livraison.
	 * @param plage La {@link PlageHoraire} de cette livraison.
	 */
	public void construireAPartirDeDOMXML(Element noeudDOMRacine, PlageHoraire plage) {
		super.construireAPartirDeDOMXML(noeudDOMRacine);
		client = Integer.parseInt(noeudDOMRacine.getAttribute(TAG_CLIENT));
		id =  Integer.parseInt(noeudDOMRacine.getAttribute(TAG_ID));
		MAX_ID = MAX_ID < id ? id : MAX_ID;
		plageHoraire = plage;		
	}

	/**
	 * @return Vrai si l'heure d'arrivé est anterieur a l'heure de fin de la {@link PlageHoraire} pour cette livraison.
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Livraison o) {
		return getHeureArrivee().compareTo(o.getHeureArrivee());
	}

}
