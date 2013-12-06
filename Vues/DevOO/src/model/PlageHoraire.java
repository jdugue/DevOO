package model;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PlageHoraire {
	
	protected String heureDebut;
	protected String heureFin;
	ArrayList<Livraison> livraisons;
	
	public String getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}

	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}
	
	public ArrayList<Livraison> construireAPartirDeDOMXML(Element noeudDOMRacine) {		
		heureDebut = noeudDOMRacine.getAttribute("heureDebut");	
		heureFin = noeudDOMRacine.getAttribute("heureFin");
		
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
