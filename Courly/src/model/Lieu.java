package model;

import java.util.ArrayList;

import org.w3c.dom.Element;

public class Lieu {
	
	protected Integer adresse;

	public Lieu() {
		
	}
	
	public Lieu(Integer adresse) {
		this.adresse = adresse;
	}
	
	public void construireAPartirDeDOMXML(Element noeudDOMRacine) {		
		adresse = Integer.parseInt(noeudDOMRacine.getAttribute("adresse"));
	}

	
	
	

}
