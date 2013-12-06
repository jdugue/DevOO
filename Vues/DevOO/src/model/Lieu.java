package model;

import java.util.ArrayList;

import org.w3c.dom.Element;

public class Lieu {
	
	protected Integer adresse;
        protected Noeud noeud;

	public Lieu() {
		
	}
	
	public Lieu(Integer adresse) {
		this.adresse = adresse;
	}
	
	public void construireAPartirDeDOMXML(Element noeudDOMRacine) {		
		adresse = Integer.parseInt(noeudDOMRacine.getAttribute("adresse"));
	}

    public Noeud getNoeud() {
        return noeud;
    }

    public void setNoeud(Noeud noeud) {
        this.noeud = noeud;
        this.noeud.setLieu(this);
    }

        
	
	

}
