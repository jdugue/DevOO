package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.xml.sax.SAXException;

import solver.ResolutionPolicy;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

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

	public int[][] generateMatriceCost(List<Trajet> trajets,Integer nbLivraisons) {
		int[][] ret = new int[nbLivraisons][nbLivraisons];
		int indexTrajet=0;

		for(int i=0;i<nbLivraisons;i++){
			int[] current = new int[nbLivraisons];

			for(int j=0;j<nbLivraisons;j++) {
				if(j==i) {
					current[j]=(int)Double.POSITIVE_INFINITY;
				}
				else {
					current[j]=trajets.get(indexTrajet++).getTempsTrajet();
				}
			}
			ret[i]=current;
		}		
		return ret;
	}

	public int[][] generateMatriceSucc(Integer nbLivraisons) {
		int[][] ret = new int[nbLivraisons][nbLivraisons];

		for (int i=0;i<nbLivraisons;i++){
			int[] current = new int[nbLivraisons];
			int index=0;
			for(int j=0;j<nbLivraisons;j++) {
				if(i!=j) {
					current[index++]=j;
				}
			}
			ret[i]=current;
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

			//System.out.println("\nOrigine : " + noeudLivraison);
			d.computePaths(noeudLivraison);
			for (int j=0;j<tournee.getLivraisons().size();j++) {
				if(j!=i) {
					//System.out.println("Cible : " +tournee.getLivraisons().get(j).getNoeud());
					List<Noeud> chemin = d.plusCourtCheminVers(tournee.getLivraisons().get(j).getNoeud());

					Trajet trajet = new Trajet(chemin);
					trajets.add(trajet);
				}
			}
		}
		//System.out.println(trajets.get(0).getTempsTrajet());

		//Param Choco
		Integer nbLivraisons = tournee.getLivraisons().size();
		Integer arcMini = d.trouverArcMini(trajets);
		Integer arcMaxi = d.trouverArcMaxi(trajets);
		//TODO Cost et succ
		int[][] matriceCosts = d.generateMatriceCost(trajets,nbLivraisons);
		int[][] matriceSucc = d.generateMatriceSucc(nbLivraisons);

		// Création du solveur
		Solver solver = new Solver();

		// Déclaration des variables
		IntVar[] xNext = new IntVar[nbLivraisons];
		for (int i = 0; i < nbLivraisons; i++) {
			xNext[i] = VariableFactory.enumerated("Next " + i, matriceSucc[i], solver);
		}
		IntVar[] xCost = VariableFactory.boundedArray("Cost ", nbLivraisons, arcMini, arcMaxi, solver);
		int bound=150000;
		IntVar xTotalCost = VariableFactory.bounded("Total cost ", nbLivraisons*arcMini, bound - 1, solver);

		// Déclaration des contraintes
		for (int i = 0; i < nbLivraisons; i++) {
			solver.post(IntConstraintFactory.element(xCost[i], matriceCosts[i], xNext[i], 0, "none"));
		}
		solver.post(IntConstraintFactory.circuit(xNext,0));
		solver.post(IntConstraintFactory.sum(xCost, xTotalCost));

		// Résolution
		solver.set(IntStrategyFactory.firstFail_InDomainMin(xNext));
		solver.findOptimalSolution(ResolutionPolicy.MINIMIZE,xTotalCost);
		System.out.println(xNext[3]);


	}
}
