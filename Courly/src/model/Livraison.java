package model;

public class Livraison {
	
	protected Integer id;
	protected Integer client;
	protected Noeud noeudAdresse;
	protected PlageHoraire plageHoraire;
	protected String heurePassage;

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

	public Noeud getNoeudAdresse() {
		return noeudAdresse;
	}

	public void setNoeudAdresse(Noeud noeudAdresse) {
		this.noeudAdresse = noeudAdresse;
	}

	public PlageHoraire getPlageHoraire() {
		return plageHoraire;
	}

	public void setPlageHoraire(PlageHoraire plageHoraire) {
		this.plageHoraire = plageHoraire;
	}

	public String getHeurePassage() {
		return heurePassage;
	}

	public void setHeurePassage(String heurePassage) {
		this.heurePassage = heurePassage;
	}

	public Livraison() {
		// TODO Auto-generated constructor stub
	}

}
