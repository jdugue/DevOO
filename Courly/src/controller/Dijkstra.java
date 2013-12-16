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
import model.Lieu;
import model.Livraison;
import model.Noeud;
import model.PlageHoraire;
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
				//System.out.println(i + " " + j);
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

	public int[][] generateMatriceSucc(Tournee tournee) {
		int nbLivraisons = tournee.getLivraisons().size();
		int[][] ret = new int[nbLivraisons+1][nbLivraisons+1];
		PlageHoraire first = tournee.getFirstPlageHoraire();
		
		//successeurs de Depot : premiere plage horaire
		int[] succDepot = new int[first.getLivraisons().size()];
		for(int i=0; i<first.getLivraisons().size(); i++) {
			
			succDepot[i]=tournee.getLivraisons().indexOf(first.getLivraisons().get(i)) + 1;
		}
		ret[0] = succDepot;
		
		//successeurs de livraison i+1
		for (int i=0;i<nbLivraisons;i++){
			PlageHoraire currentTime = tournee.getLivraisons().get(i).getPlageHoraire();
			PlageHoraire next = new PlageHoraire();
			if (tournee.getPlagesHoraire().indexOf(currentTime) < tournee.getPlagesHoraire().size()-1) {
				next = tournee.getPlagesHoraire().get(tournee.getPlagesHoraire().indexOf(currentTime)+1);
			}			
			int[] current = new int[currentTime.getLivraisons().size() + next.getLivraisons().size()];
			current[0] = 0;
			int index=1;
			for(int j=0;j<nbLivraisons;j++) {
				if(i!=j && ( tournee.getLivraisons().get(j).getPlageHoraire().equals(currentTime) || tournee.getLivraisons().get(j).getPlageHoraire().equals(next)) ) {
					current[index++]=j+1;
				}
			}
			ret[i+1]=current;
		}		
		return ret;
	}

	public List<ArrayList<Trajet>> genererMatriceTrajets(Plan plan, Tournee tournee) {
		List<ArrayList<Trajet>> trajets = new ArrayList<ArrayList<Trajet>>();

		
		for(int i=0;i<tournee.getLivraisons().size()+1;i++){
			resetPlan(plan);
			ArrayList<Trajet> trajetsCourants = new ArrayList<Trajet>();
			if(i==0) {
				Noeud noeudDepot = tournee.getDepot().getNoeud();
				computePaths(noeudDepot);
			} else {
				Noeud noeudLivraison = tournee.getLivraisons().get(i-1).getNoeud();
				computePaths(noeudLivraison);
			}
			for (int j=0;j<tournee.getLivraisons().size()+1;j++) {
				if(j!=i) {
					if(j==0) {
						List<Noeud> chemin = plusCourtCheminVers(tournee.getDepot().getNoeud());
						
						Trajet trajet = new Trajet(chemin);
						trajetsCourants.add(j,trajet);
					} else {
						List<Noeud> chemin = plusCourtCheminVers(tournee.getLivraisons().get(j-1).getNoeud());

						Trajet trajet = new Trajet(chemin);
						trajetsCourants.add(j,trajet);
					}
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
		for(Trajet t : tournee.getTrajets()) {
			for (Troncon tr : t.getTroncons()) {
				tr.getTrajets().add(t);
			}
		}
	}
	
	public void choco(Tournee tournee,List<ArrayList<Trajet>> trajets,int bound) {
		//Param Choco
		Integer nbLivraisons = tournee.getLivraisons().size()+1;
		Integer arcMini = trouverArcMini(trajets);
		Integer arcMaxi = trouverArcMaxi(trajets);
		//TODO Cost et succ
		int[][] matriceCosts = generateMatriceCost(trajets,nbLivraisons);
		int[][] matriceSucc = generateMatriceSucc(tournee);
		
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
			PlageHoraire plage;
			if(ord==0) {
				plage = tournee.getLivraisons().get(abs-1).getPlageHoraire();
			} else {
				plage = tournee.getLivraisons().get(ord-1).getPlageHoraire();
			}
			trajetCourant.setPlage(plage);
			trajetsTournee.add(trajetCourant);

			abs=ord;
		}
		//System.out.println(trajetsTournee);
		tournee.setTrajets(trajetsTournee);
		//System.out.println(xTotalCost.getValue());
		initTrajetsTroncons(tournee);
	}
	
	public void initTournee(Plan plan, Tournee tournee){
		List<ArrayList<Trajet>> trajets = genererMatriceTrajets(plan, tournee);

		System.out.println(trajets);
		System.out.println();
		
		choco(tournee,trajets,-1);
	}
	
	public static void main (String[] args) throws NumberFormatException, FileNotFoundException, SAXException {
		
		Dijkstra d = new Dijkstra();
		ParseurXML parseur = new ParseurXML();
		Plan plan = parseur.construirePlanXML("../XML Examples/plan10x10.xml");
		
		Tournee tournee = parseur.construireTourneeXML("../XML Examples/livraison10x10-2.xml");
		
		Integer adresseDepot = tournee.getDepot().getAdresse();
		tournee.getDepot().setNoeud(plan.getNoeuds().get(adresseDepot));
		for (int i=0;i< tournee.getLivraisons().size();i++) {
			Integer adresse = tournee.getLivraisons().get(i).getAdresse();
			tournee.getLivraisons().get(i).setNoeud(plan.getNoeuds().get(adresse));
		}
		
		List<ArrayList<Trajet>> trajets = d.genererMatriceTrajets(plan, tournee);
		
		d.choco(tournee, trajets, -1);
		
		for(int i=0; i<tournee.getTrajets().size(); i++) {
			ArrayList<Troncon> troncons = tournee.getTrajets().get(i).getTroncons();
			for (int j=0; j< troncons.size(); j++) {
				System.out.print("De " + troncons.get(j).getOrigine());
				System.out.println(" � " + troncons.get(j).getDestination());
			}
		}
		
	}
}