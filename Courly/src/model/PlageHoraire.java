package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author mael
 *
 */
public class PlageHoraire {

	/**
	 * L'heure de début de la plage
	 */
	protected Date heureDebut;

	/**
	 * L'heure de fin de la plage
	 */
	protected Date heureFin;

	/**
	 * La liste des livraisons inclues dans cette plage horaire
	 */
	protected ArrayList<Livraison> livraisons = new ArrayList<Livraison>();

	/**
	 * Le formateur de dates
	 */
	static final SimpleDateFormat FORMATTER = new SimpleDateFormat("HH:mm:s");


	public PlageHoraire() {}

	public String toString() {
		return "Entre " + timeToString(heureDebut) + " et " + timeToString(heureFin);
	}

	/**
	 * @param date : La date demandée
	 * @return Un string contenant uniquement le temps, sans la date
	 */
	private String timeToString (Date date){
		return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
	}

	public void sortLivraisons() {
		Collections.sort(this.livraisons);
	}

	public Date getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(Date heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Date getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(Date heureFin) {
		this.heureFin = heureFin;
	}

	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public void addLivraison(Livraison livraison) {
		this.livraisons.add(livraison);
	}

	/**
	 * Cette méthode instancie une PlageHoraire grâce à un objet tiré du XML
	 * @param noeudDOMRacine : l'objet tiré du XML
	 * @return La liste des livraisons de la plage horaire instanciée
	 * @throws ParseException Si les attributs heureDebut et heureFin ne sont pas correctement parsés en Date
	 */
	public ArrayList<Livraison> construireAPartirDeDOMXML(Element noeudDOMRacine) throws ParseException {		
		heureDebut = FORMATTER.parse(noeudDOMRacine.getAttribute("heureDebut"));	
		heureFin = FORMATTER.parse(noeudDOMRacine.getAttribute("heureFin"));

		//Traitement des livraisons associées
		ArrayList<Livraison> livraisons = new ArrayList<Livraison>();
		NodeList listeLivraisons = noeudDOMRacine.getElementsByTagName("Livraison");
		for(int i=0;i<listeLivraisons.getLength();i++) {
			Element livraisonElement = (Element) listeLivraisons.item(i);
			Livraison livraison = new Livraison();
			livraison.construireAPartirDeDOMXML(livraisonElement,this);
			livraisons.add(livraison);
		}
		this.livraisons = livraisons;
		return livraisons;
	}
}
