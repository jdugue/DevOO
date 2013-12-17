package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PlageHoraire {
	
	protected Date heureDebut;
	protected Date heureFin;
	protected ArrayList<Livraison> livraisons = new ArrayList<Livraison>();
	static final SimpleDateFormat FORMATTER = new SimpleDateFormat("HH:mm:s");
		
	public String toString() {
		return "Entre " + timeToString(heureDebut) + " et " + timeToString(heureFin);
	}

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

	public PlageHoraire() {
		// TODO Auto-generated constructor stub
	}

}
