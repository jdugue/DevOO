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

	public Integer trouverArcMini(List<ArrayList<Trajet>> trajets) {
		Integer ret=(int)Double.POSITIVE_INFINITY;

		for(ArrayList<Trajet> l : trajets) {
			for(Trajet t : l) {
				if (t!=null && t.getTempsTrajet()<ret) {
					ret = t.getTempsTrajet();
				}
			}
		}
		return ret;
	}

	public Integer trouverArcMaxi(List<ArrayList<Trajet>> trajets) {
		Integer ret=0;

		for(ArrayList<Trajet> l : trajets) {
			for(Trajet t : l) {
				if (t!=null && t.getTempsTrajet()>ret) {
					ret = t.getTempsTrajet();
				}
			}
		}
		return ret;
	}

	public int[][] generateMatriceCost(List<ArrayList<Trajet>> trajets,Integer nbLivraisons) {
		int[][] ret = new int[nbLivraisons][nbLivraisons];

		for(int i=0;i<nbLivraisons;i++){
			int[] current = new int[nbLivraisons];

			for(int j=0;j<nbLivraisons;j++) {
				if(j==i) {
					current[j]=(int)Double.POSITIVE_INFINITY;
				}
				else {
					current[j]=trajets.get(i).get(j).getTempsTrajet();
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

	public List<ArrayList<Trajet>> genererMatriceTrajets(Plan plan, Tournee tournee) {
		List<ArrayList<Trajet>> trajets = new ArrayList<ArrayList<Trajet>>();

		for(int i=0;i<tournee.getLivraisons().size();i++){
			resetPlan(plan);
			ArrayList<Trajet> trajetsCourants = new ArrayList<Trajet>();
			Noeud noeudLivraison = tournee.getLivraisons().get(i).getNoeud();

			//System.out.println("\nOrigine : " + noeudLivraison);
			computePaths(noeudLivraison);
			for (int j=0;j<tournee.getLivraisons().size();j++) {
				if(j!=i) {
					//System.out.println("Cible : " +tournee.getLivraisons().get(j).getNoeud());
					List<Noeud> chemin = plusCourtCheminVers(tournee.getLivraisons().get(j).getNoeud());

					Trajet trajet = new Trajet(chemin);
					trajetsCourants.add(j,trajet);
				}
				else {
					trajetsCourants.add(j,null);
				}
			}
			trajets.add(trajetsCourants);
		}

		return trajets;
	}

	public void initTrajetsTroncons(Tournee tournee){
		for(Trajet t : tournee.getTrajet()) {
			for (Troncon tr : t.getTroncons()) {
				tr.getTrajets().add(t);
			}
		}
	}
	
	public void choco(Tournee tournee,List<ArrayList<Trajet>> trajets,int bound) {
		//Param Choco
		Integer nbLivraisons = tournee.getLivraisons().size();
		Integer arcMini = trouverArcMini(trajets);
		Integer arcMaxi = trouverArcMaxi(trajets);
		//TODO Cost et succ
		int[][] matriceCosts = generateMatriceCost(trajets,nbLivraisons);
		int[][] matriceSucc = generateMatriceSucc(nbLivraisons);

		// Création du solveur
		Solver solver = new Solver();

		// Déclaration des variables
		IntVar[] xNext = new IntVar[nbLivraisons];
		for (int i = 0; i < nbLivraisons; i++) {
			xNext[i] = VariableFactory.enumerated("Next " + i, matriceSucc[i], solver);
		}
		IntVar[] xCost = VariableFactory.boundedArray("Cost ", nbLivraisons, arcMini, arcMaxi, solver);
		if(bound==-1) {
			bound=nbLivraisons*arcMaxi - 1;
		}
		//bound : cpmax*nbliv si bloq mettre derniere valeur
		IntVar xTotalCost = VariableFactory.bounded("Total cost ", nbLivraisons*arcMini, bound , solver);

		// Déclaration des contraintes
		for (int i = 0; i < nbLivraisons; i++) {
			solver.post(IntConstraintFactory.element(xCost[i], matriceCosts[i], xNext[i], 0, "none"));
		}
		solver.post(IntConstraintFactory.circuit(xNext,0));
		solver.post(IntConstraintFactory.sum(xCost, xTotalCost));

		// Résolution
		solver.set(IntStrategyFactory.firstFail_InDomainMin(xNext));
		solver.findOptimalSolution(ResolutionPolicy.MINIMIZE,xTotalCost);

		ArrayList<Trajet> trajetsTournee = new ArrayList<Trajet>();
		int abs = 0;
		for (int i =0; i<nbLivraisons;i++) {		
			int ord = xNext[abs].getValue();			

			Trajet trajetCourant = trajets.get(abs).get(ord);
			trajetsTournee.add(trajetCourant);

			abs=ord;
		}
		tournee.setTrajet(trajetsTournee);
		initTrajetsTroncons(tournee);
		System.out.println(xTotalCost.getValue());
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

		List<ArrayList<Trajet>> trajets = d.genererMatriceTrajets(plan, tournee);

		d.choco(tournee,trajets,-1);
		
		System.out.println(tournee.getTrajet().size());
	}
}
