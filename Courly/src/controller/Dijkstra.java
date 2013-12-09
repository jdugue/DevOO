package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.xml.sax.SAXException;

import model.Noeud;
import model.Plan;
import model.Tournee;
import model.Trajet;
import model.Troncon;


public class Dijkstra {
	
	public void computePaths(Noeud noeudSource) {
		noeudSource.setMinTemps(0);
		PriorityQueue<Noeud> nodeQueue = new PriorityQueue<Noeud>();
		nodeQueue.add(noeudSource);
		
		while(!nodeQueue.isEmpty()) {
			Noeud head = nodeQueue.poll();
			
			for (Troncon t : head.getTronconsSortants()) {
				Noeud next = t.getDestination();
				//System.out.println("Next : " + next + " next.previous : " + next.getPrevious());
				double cost = t.getCost();
				double distanceTotale = head.getMinTemps()+cost;
				
				if (distanceTotale<next.getMinTemps()) {
					nodeQueue.remove(next);
					next.setMinTemps(distanceTotale);
					next.setPrevious(head);
					nodeQueue.add(next);
				}
			}
		}
	}

	public List<Noeud> plusCourtCheminVers(Noeud destination) {
		List<Noeud> chemin = new ArrayList<Noeud>();
		
		for(Noeud noeud=destination;noeud!=null;noeud=noeud.getPrevious()) {
			chemin.add(noeud);
		}
		Collections.reverse(chemin);
		
		return chemin;
		
	}
	
	public void resetPlan(Plan plan) {
		for (Noeud n : plan.getNoeuds()) {
			n.setPrevious(null);
			n.setMinTemps(Double.POSITIVE_INFINITY);
		}
	}

	public Integer trouverArcMini(List<Trajet> trajets) {
		Integer ret=(int)Double.POSITIVE_INFINITY;
		
		for(Trajet t : trajets) {
			if (t.getTempsTrajet()<ret) {
				ret = t.getTempsTrajet();
			}
		}
		return ret;
	}
	
	public Integer trouverArcMaxi(List<Trajet> trajets) {
		Integer ret=0;
		
		for(Trajet t : trajets) {
			if (t.getTempsTrajet()>ret) {
				ret = t.getTempsTrajet();
			}
		}
		return ret;
	}
	
	public static void main (String[] args) throws NumberFormatException, FileNotFoundException, SAXException{
		Dijkstra d = new Dijkstra();
		ParseurXML parseur = new ParseurXML();
		
		Plan plan = parseur.construirePlanXML("../XML Examples/plan10x10.xml");		
		Tournee tournee = parseur.construireTourneeXML("../XML Examples/livraison10x10-1.xml");
		
		for (int i=0;i< tournee.getLivraisons().size();i++) {
			Integer adresse = tournee.getLivraisons().get(i).getAdresse();
			tournee.getLivraisons().get(i).setNoeud(plan.getNoeuds().get(adresse));
		}
		
		List<Trajet> trajets = new ArrayList<Trajet>();
		
		for(int i=0;i<tournee.getLivraisons().size();i++){
			d.resetPlan(plan);
			Noeud noeudLivraison = tournee.getLivraisons().get(i).getNoeud();
			
			System.out.println("\nOrigine : " + noeudLivraison);
			d.computePaths(noeudLivraison);
			for (int j=0;j<tournee.getLivraisons().size();j++) {
				if(j!=i) {
					System.out.println("Cible : " +tournee.getLivraisons().get(j).getNoeud());
					List<Noeud> chemin = d.plusCourtCheminVers(tournee.getLivraisons().get(j).getNoeud());
					
					Trajet trajet = new Trajet(chemin);
					trajets.add(trajet);
				}
			}
		}
		
		//Param Choco
		Integer nbLivraisons = tournee.getLivraisons().size();
		Integer arcMini = d.trouverArcMini(trajets);
		Integer arcMaxi = d.trouverArcMaxi(trajets);
		//TODO Cost et succ
	}
}
