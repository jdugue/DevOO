package model;

public class Troncon {
	
	protected Noeud origine;
	protected Noeud destination;
	protected String nomRue;
	protected double longueur;
	protected double vitesse;

	public Troncon(Noeud origine,Noeud destination, String nomRue, double longueur, double vitesse) {
		this.origine = origine;
		this.destination = destination;
		this.nomRue = nomRue;
		this.longueur = longueur;
		this.vitesse = vitesse;
	}

}
