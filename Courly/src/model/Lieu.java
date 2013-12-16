package model;

import org.w3c.dom.Element;

public class Lieu {
	
	protected Integer adresse;
    protected Noeud noeud;

	private final String TAG_ADRESSE = "adresse";

	public Lieu() {
		
	}
	
	public Lieu(Integer adresse) {
		this.adresse = adresse;
	}
	
	public void construireAPartirDeDOMXML(Element noeudDOMRacine) {		
		adresse = Integer.parseInt(noeudDOMRacine.getAttribute(TAG_ADRESSE));
	}

    public Noeud getNoeud() {
        return noeud;
    }

    public void setNoeud(Noeud noeud) {
        this.noeud = noeud;
        this.noeud.setLieu(this);
    }

	public Integer getAdresse() {
		return adresse;
	}

	public void setAdresse(Integer adresse) {
		this.adresse = adresse;
	}

        
	
	

}
