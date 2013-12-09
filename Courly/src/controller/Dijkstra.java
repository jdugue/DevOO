package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.xml.sax.SAXException;

import model.Noeud;
import model.Plan;
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
	
	public static void main (String[] args) throws NumberFormatException, FileNotFoundException, SAXException{
		ParseurXML parseur = new ParseurXML();
		
		Plan plan = parseur.construirePlanXML("../XML Examples/plan10x10.xml");
		
		//System.out.println(plan.getTroncons().size());
		Dijkstra d = new Dijkstra();
		d.computePaths(plan.getNoeuds().get(0));
		
		for(Noeud n : plan.getNoeuds()) {
			System.out.println("Temps to " + n.getId() + " : " + n.getMinTemps());
			List<Noeud> chemin = d.plusCourtCheminVers(n);
			System.out.println("Chemin : " + chemin);
		}
	}
}
