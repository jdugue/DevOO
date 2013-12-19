package model;

import org.w3c.dom.Element;

/**
 * @author Ianic
 * Objet qui represente un endroit visitable par le livreur.
 */
public abstract class Lieu {

	protected Integer adresse;
	protected Noeud noeud;

	private final String TAG_ADRESSE = "adresse";

	public Lieu() {
	}

	/**
	 * @param adresse Adresse du lieu.
	 * @param noeud Le noeud ou le lieu se trouve.
	 */
	public Lieu (Integer adresse, Noeud noeud) {
		this.adresse = adresse;
		this.noeud = noeud;            
	}

	public Lieu(Integer adresse) {
		this.adresse = adresse;
	}

	/**
	 * @param noeudDOMRacine L'élement XML qui contient l'adresse du lieu.
	 */
	public void construireAPartirDeDOMXML(Element noeudDOMRacine) {		
		adresse = Integer.parseInt(noeudDOMRacine.getAttribute(TAG_ADRESSE));
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	public Integer getAdresse() {
		return adresse;
	}

	public void setAdresse(Integer adresse) {
		this.adresse = adresse;
	}

}
